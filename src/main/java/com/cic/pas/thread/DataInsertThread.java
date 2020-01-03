package com.cic.pas.thread;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.cic.pas.common.bean.*;
import org.apache.log4j.Logger;

import com.cic.pas.application.DBVisitService;
import com.cic.pas.common.util.Util;
import com.cic.pas.dao.BussinessConfig;

public class DataInsertThread extends Thread {
    private Logger logger = Logger.getLogger(DataInsertThread.class);

    public void run() {
        logger.info("数据存储线程启动");
        while (true) {
            Calendar ca = Calendar.getInstance();
            /**
             *存储周期  分钟
             * 默认 15分钟
             */
            int saveCycle = 1;
            int m = ca.get(Calendar.MINUTE);
            int h = ca.get(Calendar.HOUR_OF_DAY);
            int day = ca.get(Calendar.DAY_OF_MONTH);
            long sleepTime = 0;
            if (m % saveCycle == 0) {
                long start = System.currentTimeMillis();
                logger.info("准备生成插入数据" + new Date());
//                saveData(day, h, m, ca);
                buildAndSaveRealData(ca);
                long end = System.currentTimeMillis();
                logger.info("插入数据完成" + new Date() + ",用时:" + (end - start));
                Calendar current = Calendar.getInstance();
                int minute = current.get(Calendar.MINUTE);
                int second = current.get(Calendar.SECOND);
                int yu = minute % saveCycle;
                int sleepM = saveCycle - yu - 1;
                int sleepS = 60 - second;
                sleepTime = (sleepM * 60 + sleepS) * 1000;
                logger.info("当前时间：" + ca.getTime() + ",线程等待：" + sleepTime);
            } else {
                int second = ca.get(Calendar.SECOND);
                int yu = m % saveCycle;
                int sleepM = saveCycle - yu - 1;
                int sleepS = 60 - second;
                sleepTime = (sleepM * 60 + sleepS) * 1000;
                logger.info("当前时间：" + ca.getTime() + ",线程等待：" + sleepTime);
            }
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveData(int day, int h, int m, Calendar ca) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String datetime = format.format(new Date());
        try {
            int point = h * 4 + m / 15;
            if (point == 0) {
                int calday = ca.get(Calendar.DATE);
                ca.set(Calendar.DATE, calday - 1);
                datetime = format.format(ca.getTime());
                point = 96;
            }
            toBulidDayData(ca, format, datetime, point);
            toBulidSystemDayData(datetime, point);
            DBVisitService
                    .batchInsertTemp(BussinessConfig.config.daylist);
            DBVisitService.batchInsertSystemTemp(BussinessConfig.systemDayList);
            if (point == 96) {
                DBVisitService
                        .batchInsertDay(BussinessConfig.config.daylist);
                DBVisitService
                        .batchInsertMonth(BussinessConfig.config.monthlist);
                DBVisitService.batchInsertSystemDay(BussinessConfig.systemDayList);
                if (day == 1) {
                    DBVisitService
                            .batchInsertYear(BussinessConfig.config.yearlist);
                }
            }
            Thread.sleep(60000);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.info(e);
            e.printStackTrace();
        }
    }

    /**
     * create by: 高嵩
     * description: 准备生成日数据  计量系统
     * create time: 2019/8/8 12:29
     *
     * @return
     * @params
     */
    private void toBulidDayData(Calendar ca, SimpleDateFormat format, String datetime, int point) throws Exception {
        List<TerminalDevice> terminalDevices = BussinessConfig.config.terminalList;
        Class<EsmspSumMeasurOrganizationDay> dayClass = EsmspSumMeasurOrganizationDay.class;
        Class<EsmspSumMeasurOrganizationMonth> monthClass = EsmspSumMeasurOrganizationMonth.class;
        Class<EsmspSumMeasurOrganizationYear> yearClass = EsmspSumMeasurOrganizationYear.class;
        for (TerminalDevice t : terminalDevices) {
            for (MeterDevice md : t.getMeterList()) {
                String eusCode = md.getCode();
                for (PointDevice pd : md.getPointDevice()) {
                    if (pd.getIssave() == 1) {
                        BulidDayData(ca, format, datetime, point,
                                dayClass, monthClass, yearClass,
                                eusCode, pd);
                    }
                }
            }
        }
    }

    /**
     * create by: 高嵩
     * description: 准备生成用能系统日数据
     * create time: 2019/8/8 13:20
     *
     * @return
     * @params
     */
    private void toBulidSystemDayData(String datetime, int point) throws Exception {
        Class<EsmspSumMeasurSystemDay> dayClass = EsmspSumMeasurSystemDay.class;
        for (PomsEnergyUsingSystem system : BussinessConfig.systemList) {
            for (PomsEnergyUsingFacilities facility : system.getFacilitiyList()) {
                for (PomsEnergyUsingFacilitiesModelPoint pd : facility.getPointList()) {
                    if (pd.getIsSave() == 1) {
                        BulidSystemDayData(datetime, point,
                                dayClass, pd);
                    }
                }
            }
        }
    }

    /**
     * create by: 高嵩
     * description: 生成日数据   用能系统
     * create time: 2019/8/8 11:53
     *
     * @return
     * @params
     */
    private void BulidSystemDayData(
            String datetime, int point,
            Class<EsmspSumMeasurSystemDay> dayClass,
            PomsEnergyUsingFacilitiesModelPoint pd) throws Exception {
        boolean dayIsExi = false;
        String mmpCode = pd.getMmpCode();
        EsmspSumMeasurSystemDay organizationDay = new EsmspSumMeasurSystemDay();
        try {
            for (EsmspSumMeasurSystemDay d : BussinessConfig.systemDayList) {
                if (d.getSystemCode() != null && d.getMmpCode() != null) {
                    if (d.getSystemCode().equals(pd.getSystemCode()) && d.getFacilityCode().equals(pd.getFacilityCode())
                            && d.getMmpCode().equals(mmpCode)) {
                        if (point == 1) {// 新的一天 日累计 峰、谷、平、 最值重置
                            for (EsmspSumMeasurSystemDay item : BussinessConfig.systemDayList) {
                                if (item.equals(d)) {
                                    BussinessConfig.config.systemDayList.remove(item);
                                    break;
                                }
                            }
                        } else {
                            organizationDay = d;
                            organizationDay.setIsSave(pd.getIsSave());
                            dayIsExi = true;
                        }
                        break;
                    }
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        organizationDay.setEuiCode(Util.euo_code);
        organizationDay.setSystemCode(pd.getSystemCode());
        organizationDay.setFacilityCode(pd.getFacilityCode());
        organizationDay.setMmpCode(mmpCode);
        organizationDay.setDateCode(datetime);
        organizationDay.setIsSave(pd.getIsSave());
        try {
            if (pd.getMaxDate() != null) {
                organizationDay.setMaxValue(pd.getMaxValue().setScale(2, BigDecimal.ROUND_HALF_UP));
                organizationDay.setMaxDate(pd.getMaxDate());
            }
            if (pd.getMinDate() != null) {
                organizationDay.setMinValue(pd.getMinValue().setScale(2, BigDecimal.ROUND_HALF_UP));
                organizationDay.setMinDate(pd.getMinDate());
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        BigDecimal value = new BigDecimal(0);
        Method method = dayClass.getDeclaredMethod("setPoint" + point,
                BigDecimal.class);

        value = pd.getValue();
        method.invoke(organizationDay, value);
        organizationDay.setSumValue(organizationDay.getSumValue().add(value).setScale(2, BigDecimal.ROUND_HALF_UP));
        organizationDay.setAvgValue(organizationDay.getSumValue().divide(new BigDecimal(point), 2));
        if (!dayIsExi) {
            BussinessConfig.systemDayList.add(organizationDay);
        }
    }

    /**
     * create by: 高嵩
     * description: 生成日数据
     * create time: 2019/8/8 11:51
     *
     * @return
     * @params
     */
    private void BulidDayData(Calendar ca, SimpleDateFormat format,
                              String datetime, int point,
                              Class<EsmspSumMeasurOrganizationDay> dayClass,
                              Class<EsmspSumMeasurOrganizationMonth> monthClass,
                              Class<EsmspSumMeasurOrganizationYear> yearClass, String eusCode,
                              PointDevice pd) throws Exception {
        boolean dayIsExi = false;
        boolean day31IsExi = false;
        int day = ca.get(Calendar.DAY_OF_MONTH);
        int month = ca.get(Calendar.MONTH) + 1;
        String mmpCode = pd.getCode();
        EsmspSumMeasurOrganizationDay organizationDay = new EsmspSumMeasurOrganizationDay();
        EsmspSumMeasurOrganizationDay organizationDay31 = new EsmspSumMeasurOrganizationDay();
        try {
            for (EsmspSumMeasurOrganizationDay d : BussinessConfig.config.daylist) {
                if (d.getEusCode() != null && d.getMmpCode() != null) {
                    if (d.getEusCode().equals(eusCode)
                            && d.getMmpCode().equals(mmpCode)) {
                        if (point == 1) {// 新的一天 日累计 峰、谷、平、 最值重置
                            for (EsmspSumMeasurOrganizationDay item : BussinessConfig.config.daylist) {
                                if (item.equals(d)) {
                                    BussinessConfig.config.daylist.remove(item);
                                    break;
                                }
                            }
                        } else {
                            organizationDay = d;
                            organizationDay.setIsSave(pd.getIssave());
                            dayIsExi = true;
                        }
                        break;
                    }
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        organizationDay.setEuoCode(Util.euo_code);
        organizationDay.setEusCode(eusCode);
        organizationDay.setMmpCode(mmpCode);
        organizationDay.setDateCode(datetime);
        organizationDay.setIsSave(pd.getIssave());
        organizationDay.setNomalPrice(pd.getNormalPrice());
        organizationDay.setfPrice(pd.getfPrice());
        organizationDay.setpPrice(pd.getpPrice());
        organizationDay.setgPrice(pd.getgPrice());
        organizationDay.setjPrice(pd.getjPrice());
        try {
            if (pd.getMax_time() != null) {
                organizationDay.setMaxValue(pd.getMax_value().setScale(2, BigDecimal.ROUND_HALF_UP));
                organizationDay.setMaxDate(pd.getMax_time());
            }
            if (pd.getMin_time() != null) {
                organizationDay.setMinValue(pd.getMin_value().setScale(2, BigDecimal.ROUND_HALF_UP));
                organizationDay.setMinDate(pd.getMin_time());
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        BigDecimal value = new BigDecimal(0);
        Method method = dayClass.getDeclaredMethod("setPoint" + point,
                BigDecimal.class);
        if (pd.getIsCalculate() == 1) {
            try {
                for (EsmspSumMeasurOrganizationDay d : BussinessConfig.config.daylist) {
                    if (d.getEusCode() != null && d.getMmpCode() != null) {
                        if (d.getEusCode().equals(eusCode)
                                && d.getMmpCode().equals(mmpCode + "_")) {
                            if (point == 1) {
                                for (EsmspSumMeasurOrganizationDay item : BussinessConfig.config.daylist) {
                                    if (item.equals(d)) {
                                        BussinessConfig.config.daylist
                                                .remove(item);
                                        break;
                                    }
                                }
                            } else {
                                organizationDay31 = d;
                                day31IsExi = true;
                            }
                            break;
                        }
                    }
                }
                if (point == 1) {
                    organizationDay31 = new EsmspSumMeasurOrganizationDay();
                }
                organizationDay31.setEuoCode(Util.euo_code);
                organizationDay31.setEusCode(eusCode);
                organizationDay31.setMmpCode(mmpCode + "_");
                organizationDay31.setDateCode(datetime);
                organizationDay31.setIsSave(1);
                method.invoke(organizationDay31, pd.getValue().setScale(2, BigDecimal.ROUND_HALF_UP));
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
            if (pd.getLastPointValue().compareTo(new BigDecimal(0)) == 0) {
                value = new BigDecimal(0);
            } else {
                try {
                    value = pd.getValue().subtract(pd.getLastPointValue());
                    int type = Util.getFGP(point);
                    switch (type) {
                        case 1://

                            organizationDay.setfValue(organizationDay.getfValue()
                                    .add(value).setScale(2, BigDecimal.ROUND_HALF_UP));
                            if (organizationDay.getfPrice() != null && organizationDay.getfPrice() != BigDecimal.ZERO) {
                                organizationDay.setfAmount(organizationDay.getfValue().multiply(organizationDay.getfPrice()).setScale(2, BigDecimal.ROUND_HALF_UP));
                            }
                            break;
                        case 2:
                            organizationDay.setpValue(organizationDay.getpValue()
                                    .add(value).setScale(2, BigDecimal.ROUND_HALF_UP));
                            if (organizationDay.getpPrice() != null && organizationDay.getpPrice() != BigDecimal.ZERO) {
                                organizationDay.setpAmount(organizationDay.getpValue().multiply(organizationDay.getpPrice()).setScale(2, BigDecimal.ROUND_HALF_UP));
                            }
                            break;
                        case 3:
                            organizationDay.setgValue(organizationDay.getgValue()
                                    .add(value).setScale(2, BigDecimal.ROUND_HALF_UP));
                            if (organizationDay.getgPrice() != null && organizationDay.getgPrice() != BigDecimal.ZERO) {
                                organizationDay.setgAmount(organizationDay.getgValue().multiply(organizationDay.getgPrice()).setScale(2, BigDecimal.ROUND_HALF_UP));
                            }
                            break;
                        case 4:
                            organizationDay.setjValue(organizationDay.getgValue()
                                    .add(value).setScale(2, BigDecimal.ROUND_HALF_UP));
                            if (organizationDay.getjPrice() != null && organizationDay.getjPrice() != BigDecimal.ZERO) {
                                organizationDay.setfAmount(organizationDay.getjValue().multiply(organizationDay.getjPrice()).setScale(2, BigDecimal.ROUND_HALF_UP));
                            }
                            break;
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
            }
            pd.setLastPointValue(pd.getValue().setScale(2, BigDecimal.ROUND_HALF_UP));
        } else {
            value = pd.getValue();
        }
        organizationDay.setLastValue(pd.getValue().setScale(2, BigDecimal.ROUND_HALF_UP));
        method.invoke(organizationDay, value);
        organizationDay.setSumValue(organizationDay.getSumValue().add(value).setScale(2, BigDecimal.ROUND_HALF_UP));
        if (organizationDay.getfPrice() == null || organizationDay.getfPrice() == BigDecimal.ZERO) {
            if (organizationDay.getNomalPrice() != null && organizationDay.getNomalPrice() != BigDecimal.ZERO) {
                organizationDay.setSumAmount(organizationDay.getSumValue().multiply(organizationDay.getNomalPrice()));
            }
        } else {
            organizationDay.setSumAmount(organizationDay.getfAmount().add(organizationDay.getpAmount().add(organizationDay.getgAmount().add(organizationDay.getjAmount()))));
        }
        organizationDay.setAvgValue(organizationDay.getSumValue().divide(new BigDecimal(point), 2));
        if (!dayIsExi) {
            BussinessConfig.config.daylist.add(organizationDay);
        }
        if (!day31IsExi
                && pd.getIsCalculate() == 1) {
            BussinessConfig.config.daylist.add(organizationDay31);
        }
        if (point == 96
                && pd.getIsCalculate() == 1) {// 0点处理月数据
            BulidMonthData(datetime, monthClass, yearClass, day,
                    month, organizationDay, organizationDay31);
        }
    }

    /**
     * 方法名:
     * 描述: 生成月数据
     * 参数:
     * 返回值:
     * 作者:高嵩
     * 创建时间: 2020/1/3 10:51
     **/
    private void BulidMonthData(String datetime,
                                Class<EsmspSumMeasurOrganizationMonth> monthClass,
                                Class<EsmspSumMeasurOrganizationYear> yearClass,
                                int day, int month,
                                EsmspSumMeasurOrganizationDay organizationDay, EsmspSumMeasurOrganizationDay organizationDay31) throws Exception {
        boolean monthIsExi = false;
        boolean month31IsExi = false;
        Calendar currCal = Calendar.getInstance();
        int currDay = currCal.get(Calendar.DAY_OF_MONTH);
        EsmspSumMeasurOrganizationMonth organizationMonth = new EsmspSumMeasurOrganizationMonth();
        EsmspSumMeasurOrganizationMonth organizationMonth31 = new EsmspSumMeasurOrganizationMonth();
        for (EsmspSumMeasurOrganizationMonth m : BussinessConfig.config.monthlist) {
            if (m.getEusCode() != null && m.getMmpCode() != null) {
                if (m.getEusCode().equals(organizationDay.getEusCode())
                        && m.getMmpCode().equals(organizationDay.getMmpCode())) {
                    if (day == 1) {
                        for (EsmspSumMeasurOrganizationMonth item : BussinessConfig.config.monthlist) {
                            if (item.equals(m)) {
                                BussinessConfig.config.monthlist.remove(item);
                                break;
                            }
                        }
                    } else {
                        organizationMonth = m;
                        monthIsExi = true;
                    }
                    break;
                }
            }
        }
        for (EsmspSumMeasurOrganizationMonth m : BussinessConfig.config.monthlist) {
            if (m.getEusCode() != null && m.getMmpCode() != null) {
                if (m.getEusCode().equals(organizationDay31.getEusCode()) && m.getMmpCode().equals(organizationDay31.getMmpCode())
                        ) {
                    if (day == 1) {
                        for (EsmspSumMeasurOrganizationMonth item : BussinessConfig.config.monthlist) {
                            if (item.equals(m)) {
                                BussinessConfig.config.monthlist.remove(item);
                                break;
                            }
                        }
                    } else {
                        organizationMonth31 = m;
                        month31IsExi = true;
                    }
                    break;
                }
            }
        }
        organizationMonth.setEuoCode(Util.euo_code);
        organizationMonth.setEusCode(organizationDay.getEusCode());
        organizationMonth.setMmpCode(organizationDay.getMmpCode());
        organizationMonth.setDateCode(datetime.substring(0, 6));
        Method monthM = monthClass.getDeclaredMethod("setPoint" + day,
                BigDecimal.class);
        BigDecimal dayOfMonth = organizationDay.getSumValue();
        monthM.invoke(organizationMonth, dayOfMonth);
        organizationMonth.setSumValue(organizationMonth.getSumValue()
                .add(dayOfMonth));
        organizationMonth.setSumAmount(organizationMonth.getSumAmount()
                .add(organizationDay.getSumAmount()));
        organizationMonth.setAvgValue(organizationMonth.getSumValue().divide(new BigDecimal(day), 2));
        organizationMonth.setjValue(organizationMonth.getjValue()
                .add(organizationDay.getjValue()).setScale(2, BigDecimal.ROUND_HALF_UP));
        organizationMonth.setjAmount(organizationMonth.getgAmount()
                .add(organizationDay.getjAmount()).setScale(2, BigDecimal.ROUND_HALF_UP));
        organizationMonth.setfValue(organizationMonth.getfValue()
                .add(organizationDay.getfValue()).setScale(2, BigDecimal.ROUND_HALF_UP));
        organizationMonth.setfAmount(organizationMonth.getfAmount()
                .add(organizationDay.getfAmount()).setScale(2, BigDecimal.ROUND_HALF_UP));
        organizationMonth.setpValue(organizationMonth.getpValue()
                .add(organizationDay.getpValue()).setScale(2, BigDecimal.ROUND_HALF_UP));
        organizationMonth.setpAmount(organizationMonth.getpAmount()
                .add(organizationDay.getpAmount()).setScale(2, BigDecimal.ROUND_HALF_UP));
        organizationMonth.setgValue(organizationMonth.getgValue()
                .add(organizationDay.getgValue()).setScale(2, BigDecimal.ROUND_HALF_UP));
        organizationMonth.setgAmount(organizationMonth.getgAmount()
                .add(organizationDay.getgAmount()).setScale(2, BigDecimal.ROUND_HALF_UP));
        organizationMonth31.setEuoCode(Util.euo_code);
        organizationMonth31.setEusCode(organizationDay31.getEusCode());
        organizationMonth31.setMmpCode(organizationDay31.getMmpCode());
        organizationMonth31.setDateCode(datetime.substring(0, 6));
        monthM.invoke(organizationMonth31, organizationDay31.getPoint96());
        if (!month31IsExi) {
            BussinessConfig.config.monthlist.add(organizationMonth31);
        }
        if (!monthIsExi) {
            BussinessConfig.config.monthlist.add(organizationMonth);
        }
        if (currDay == 1) {// 每月1日处理年数据
            BulidYearData(datetime, monthClass, yearClass, day, month,
                    organizationMonth, organizationMonth31);
        }
    }

    /**
     * 方法名:
     * 描述: 生成年数据
     * 参数:
     * 返回值:
     * 作者:高嵩
     * 创建时间: 2020/1/3 10:51
     **/
    private void BulidYearData(String datetime,
                               Class<EsmspSumMeasurOrganizationMonth> monthClass,
                               Class<EsmspSumMeasurOrganizationYear> yearClass,
                               int day, int month,
                               EsmspSumMeasurOrganizationMonth organizationMonth,
                               EsmspSumMeasurOrganizationMonth organizationMonth31)
            throws Exception {
        boolean yearIsExi = false;
        boolean year31IsExi = false;
        EsmspSumMeasurOrganizationYear organizationYear = new EsmspSumMeasurOrganizationYear();
        EsmspSumMeasurOrganizationYear organizationYear31 = new EsmspSumMeasurOrganizationYear();
        for (EsmspSumMeasurOrganizationYear y : BussinessConfig.config.yearlist) {
            if (y.getEusCode() != null && y.getMmpCode() != null) {
                if (y.getEusCode().equals(organizationMonth.getEusCode())
                        && y.getMmpCode().equals(organizationMonth.getMmpCode())) {
                    if (month == 1) {
                        for (EsmspSumMeasurOrganizationYear item : BussinessConfig.config.yearlist) {
                            if (item.equals(y)) {
                                BussinessConfig.config.yearlist.remove(item);
                                break;
                            }
                        }
                    } else {
                        organizationYear = y;
                        yearIsExi = true;
                    }
                    break;
                }
            }
        }
        for (EsmspSumMeasurOrganizationYear y : BussinessConfig.config.yearlist) {
            if (y.getEusCode() != null && y.getMmpCode() != null) {
                if (y.getEusCode().equals(organizationMonth31.getEusCode()) && y.getMmpCode().equals(organizationMonth31.getMmpCode())) {
                    if (month == 1) {
                        for (EsmspSumMeasurOrganizationYear item : BussinessConfig.config.yearlist) {
                            if (item.equals(y)) {
                                BussinessConfig.config.yearlist.remove(item);
                                break;
                            }
                        }
                    } else {
                        organizationYear31 = y;
                        year31IsExi = true;
                    }
                    break;
                }
            }
        }
        if (month == 1) {// 新的一年 年累计重置
            organizationYear = new EsmspSumMeasurOrganizationYear();
            organizationYear31 = new EsmspSumMeasurOrganizationYear();
        }
        organizationYear.setEuoCode(Util.euo_code);
        organizationYear.setEusCode(organizationMonth.getEusCode());
        organizationYear.setMmpCode(organizationMonth.getMmpCode());
        organizationYear.setDateCode(datetime.substring(0, 4));
        Method yearM = yearClass.getDeclaredMethod("setPoint" + month,
                BigDecimal.class);
        yearM.invoke(organizationYear, organizationMonth.getSumValue());
        organizationYear.setSumValue(organizationYear.getSumValue()
                .add(organizationMonth.getSumValue()));
        organizationYear.setSumAmount(organizationYear.getSumAmount()
                .add(organizationMonth.getSumAmount()));
        organizationYear.setAvgValue(organizationYear.getSumValue().divide(new BigDecimal(month)).setScale(2, BigDecimal.ROUND_HALF_UP));
        organizationYear.setjValue(organizationYear.getjValue()
                .add(organizationMonth.getjValue()));
        organizationYear.setfAmount(organizationYear.getfAmount()
                .add(organizationMonth.getfAmount()));
        organizationYear.setfValue(organizationYear.getfValue()
                .add(organizationMonth.getfValue()));
        organizationYear.setfAmount(organizationYear.getfAmount()
                .add(organizationMonth.getfAmount()));
        organizationYear.setpValue(organizationYear.getpValue()
                .add(organizationMonth.getpValue()));
        organizationYear.setpAmount(organizationYear.getpAmount()
                .add(organizationMonth.getpAmount()));
        organizationYear.setgValue(organizationYear.getgValue()
                .add(organizationMonth.getgValue()));
        organizationYear.setgAmount(organizationYear.getgAmount()
                .add(organizationMonth.getgAmount()));
        organizationYear31.setEuoCode(Util.euo_code);
        organizationYear31.setEusCode(organizationMonth31.getEusCode());
        organizationYear31.setMmpCode(organizationMonth31.getMmpCode());
        organizationYear31.setDateCode(datetime.substring(0, 4));
        Method mMethod = monthClass.getDeclaredMethod("getPoint" + day);
        yearM.invoke(organizationYear31, mMethod.invoke(organizationMonth31));
        if (!yearIsExi) {
            BussinessConfig.config.yearlist.add(organizationYear);
        }
        if (!year31IsExi) {
            BussinessConfig.config.yearlist.add(organizationYear31);
        }
    }

    public static void main(String[] args) {
        String str = "insert into poms_consume_energy_terminal_ref (cal_id,consume_energy_type,consume_energy_id)VALUES";
        for (int i = 61; i < 131; i++) {
            int ctd_code = (int) (i - 1) / 5 + 194;
            str += "(" + i + ",4," + ctd_code + "),\n";
        }
        System.out.println(str);
    }

    private void buildAndSaveRealData(Calendar ca) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date=format.format(ca.getTime());
        List<EsmspSumMeasurSystemReal> list = new ArrayList<>();
        for (PomsEnergyUsingSystem system : BussinessConfig.systemList) {
            for (PomsEnergyUsingFacilities facility : system.getFacilitiyList()) {
                for (PomsEnergyUsingFacilitiesModelPoint pd : facility.getPointList()) {
                    if (pd.getIsSave() == 1) {
                        EsmspSumMeasurSystemReal real = new EsmspSumMeasurSystemReal();
                        real.setSystemCode(system.getSystemCode());
                        real.setFacilityCode(facility.getFacilitiesCode());
                        real.setMmpCode(pd.getMmpCode());
                        real.setDateCode(date);
                        real.setEuiCode(Util.euo_code);
                        real.setIsSave(pd.getIsSave());
                        real.setValue(pd.getValue());
                        list.add(real);
                    }
                }
            }
        }
        DBVisitService.batchInsertRealData(list);
    }
}