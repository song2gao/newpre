package com.cic.pas.application;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.cic.domain.PomsCalculateAlterRecord;
import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.cic.domain.EsmspCalSumRule;
import com.cic.domain.EsmspSumMonth;
import com.cic.pas.dao.BussinessConfig;
import com.cic.pas.dao.DBConfigDao;
import com.cic.pas.dao.LogDao;
import com.cic.pas.dao.LogFactory;
import com.cic.pas.service.ServerContext;
import com.cic.pas.application.manage.PDBManageService;
import com.cic.pas.common.bean.*;
import com.cic.pas.common.util.DateUtils;
import com.cic.pas.common.util.Util;

/**
 * 版权所有(C)2013-2014 CIC
 * 公司名称：北京中诚盛源技术发展有限公司
 * 版本:V3.0
 * 文件名：com.cic.pas.base.serviceInterface.DBVisitService.java
 * 作者: 刘亚
 * 创建时间: 2014-2-26上午09:52:18
 * 修改时间：2014-2-26上午09:52:18
 */

/**
 * @ClassName: DBVisitService
 * @Description: 数据库访问服务
 * @author lenovo
 * @date 2014-2-26 上午09:52:18
 *
 */
public class DBVisitService {

    public static final Logger logger = Logger
            .getLogger(ChannelConfigService.class);

    // 得到连接数据库的模板
    public static JdbcTemplate jdbcTemplate = DBConfigDao.jdbcTemplate;

    public static String args;

    public DBVisitService() {
        // TODO Auto-generated constructor stub

        logger.info("数据库访问服务启动");
    }

    static {
        StringBuffer sb = new StringBuffer();
        for (int i = 1; i <= 96; i++) {
            sb.append(",point" + i);
        }
        sb.append(") values (?,?,?,?,? ");
        for (int i = 0; i < 96; i++) {
            sb.append(",?");
        }
        sb.append(")");
        args = sb.toString();
    }

    /**
     * @Title: selectProtocolCfg
     * @Description: TODO(读取协议配置表)
     * @param
     * @return void 返回类型
     * @throws
     */
    private void selectProtocolCfg() {

        // TODO Auto-generated method stub

        System.out.println("读取协议配置表信息");
    }

    /**
     * @Title: selectChannel
     * @Description: TODO(读取通道信息表)
     * @param
     * @return void 返回类型
     * @throws
     */
    private void selectChannel() {
        // TODO Auto-generated method stub
        System.out.println("读取所有通道信息");
    }

    /**
     * @return
     * @Title: selectProtocol
     * @Description: TODO(读取通讯协议表)
     * @param
     * @return void 返回类型
     * @throws
     */
    public String selectProtocol(String id) {
        // TODO 读取协议表的某个字段
        String result = (String) jdbcTemplate.queryForObject(
                "SELECT PRO_PROCLASS FROM PRE_PROTOCOL WHERE PRO_PROCODE = "
                        + id, String.class);
        return result;
    }

    /**
     * @Title: selectTask
     * @Description: TODO(读取任务方法)
     * @param
     * @return void 返回类型
     * @throws
     */
    private void selectTask() {

        System.out.println("读取所有任务信息");
    }

    /**
     * @Title: update
     * @Description: 更新库表方法
     * @param
     * @return void 返回类型
     * @throws
     */
    public void update() {

    }

    private void addHistoryData() {

    }

    /**
     * @Title: mathHandle
     * @Description: (测点公式处理)
     * @param
     * @return void 返回类型
     * @throws
     */
    private void mathHandle() {

    }

    private void dbConfig() {

    }

    /**
     * @Description: TODO(终端预警)
     * @throws
     */
    private void saveAlert(String[] args) {

        String sql = "";
        jdbcTemplate.update(sql, args);
    }

    /**
     * @Description: TODO(终端故障)
     * @throws
     */
    private void saveFault(String[] args) {

        String sql = "";
        jdbcTemplate.update(sql, args);
    }

    /**
     * （历史数据）批量执行日曲线数据、日冻结数据
     */
    public void execute() {
        logger.info("执行---------曲线、日冻结数据保存START-----------");
        List<Object[]> list = new ArrayList<Object[]>();
        List<TerminalDevice> myTerminal = new ArrayList<TerminalDevice>();
        for (TerminalDevice t : BussinessConfig.config.terminalList) {
            Set<Entry<Long, IoSession>> set = ServerContext.clientMap
                    .entrySet();
            for (Entry<Long, IoSession> entry : set) {
                IoSession is = entry.getValue();
                System.out.println(is.getAttribute("terminal_id"));
                int terminal_id = (Integer) is.getAttribute("terminal_id");
                if (t.getId() == terminal_id) {
                    myTerminal.add(t);
                }
            }
        }
        if (myTerminal.size() == 0)
            return;
        for (TerminalDevice t : myTerminal) {
            for (MeterDevice md : t.getMeterList()) {
                if (md.getStatus() == MeterDevice.SELF_STATUS_NORMAL) {
                    for (PointDevice pd : md.getPointDevice()) {
                        if (pd.getValue() == null)
                            continue;
                        Object[] o = new Object[4];
                        // o[0] = t.getId();
                        o[0] = md.getCode();
                        o[1] = pd.getCode();
                        SimpleDateFormat sdf = new SimpleDateFormat(
                                "yyyy-MM-dd HH:mm:ss");
                        o[2] = sdf.format(pd.getTime());
                        o[3] = pd.getValue();
                        list.add(o);
                    }
                }
            }
        }
        try {
            if (list.size() != 0) {
                batchInsertRows(
                        // 终端编号 计量点编号 测点编码 存储时间
                        "insert into point_data (ctd_code,mmp_code,date_code,value)values(?,?,?,?)",
                        list);
                System.out.println("曲线冻结执行存储完成......");
            }
            // 日冻结数据存储
            // forDaysFreezing(myTerminal);
        } catch (Exception e) {
            e.printStackTrace();
            LogFactory.getInstance().saveLog("曲线冻结执行存储异常", LogDao.exception);
        }

        // Set<Entry<Long, IoSession>> set = ServerContext.clientMap.entrySet();
        // for (Entry<Long, IoSession> entry : set) {
        // IoSession session = entry.getValue();
        // Iterator<TerminalDevice> it = BussinessConfig.terminalList
        // .iterator();
        // Integer id = (Integer) session.getAttribute("terminal_id");// 得到采集器编号
        // while (it.hasNext()) {
        // TerminalDevice t = it.next();
        // if (t.getId() == id) {
        // for (MeterDevice d : t.getMeterList()) {
        // d.setFlag(false);
        // }
        // }
        // }
        // }
        // logger.info("执行---------曲线、日冻结数据保存END-----------");
    }

    /**
     * 执行批量添加日冻结数据
     *
     * @param myTerminal
     */
    private void forDaysFreezing(List<TerminalDevice> myTerminal) {
        List<Object[]> list = new ArrayList<Object[]>();
        for (TerminalDevice t : myTerminal) {
            for (MeterDevice md : t.getMeterList()) {
                if (md.getStatus() == MeterDevice.SELF_STATUS_NORMAL) {
                    for (PointDevice pd : md.getPointDevice()) {
                        if (pd.getDayValue() == null) {
                            continue;
                        }
                        Object[] o = new Object[5];
                        o[0] = t.getId();
                        o[1] = md.getCode();
                        o[2] = pd.getCode();
                        o[3] = pd.getDayTime();
                        o[4] = pd.getDayValue();
                        list.add(o);
                    }
                }
            }
        }
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -1);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        if (list.size() == 0)
            return;
        try {
            if (day == 1) {
                batchInsertRowsForDaysFreezing(
                        "insert into pre_days_freezing (days_freezing_msa_codes,days_freezing_equipment_codes,days_freezing_point_codes,days_freezing_storage_date,p"
                                + day + ") values (?,?,?,?,?)", list);
            } else {
                int count = getCount(
                        "select count(*)  from pre_days_freezing where days_freezing_msa_codes = ? and days_freezing_equipment_codes = ? and days_freezing_point_codes = ? and days_freezing_storage_date = ?",
                        list.get(0));
                if (count == 0) {
                    batchInsertRowsForDaysFreezing(
                            "insert into pre_days_freezing (days_freezing_msa_codes,days_freezing_equipment_codes,days_freezing_point_codes,days_freezing_storage_date,p"
                                    + day + ") values (?,?,?,?,?)", list);
                } else {
                    batchUpdateRowsForDaysFreezing(
                            "update  pre_days_freezing set p"
                                    + day
                                    + " = ?,days_freezing_storage_date = ? where days_freezing_msa_codes = ? and days_freezing_equipment_codes = ? and days_freezing_point_codes = ? and days_freezing_storage_date = ?",
                            list);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogFactory.getInstance().saveLog("日冻结执行存储异常", LogDao.exception);
        }
    }

    /**
     * 批量插入数据（日冻结数据）
     *
     * @param sql
     * @param dataSet
     * @throws Exception
     */
    private void batchInsertRowsForDaysFreezing(String sql,
                                                final List<Object[]> dataSet) throws Exception {
        BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter() {
            public int getBatchSize() {
                return dataSet.size();
            }

            public void setValues(PreparedStatement ps, int i) {

                Object[] obj = dataSet.get(i);
                try {
                    ps.setObject(1, obj[0], Types.VARCHAR);
                    ps.setObject(2, obj[1], Types.VARCHAR);
                    ps.setObject(3, obj[2], Types.VARCHAR);
                    ps.setObject(4, obj[3], Types.TIMESTAMP);
                    ps.setObject(5, obj[4], Types.DOUBLE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        jdbcTemplate.batchUpdate(sql, setter);
        LogFactory.getInstance().saveLog("日冻结数据插入成功", LogDao.operation);
    }

    /**
     * 批量更新数据（日冻结数据）
     *
     * @param sql
     * @param dataSet
     * @throws Exception
     */
    private void batchUpdateRowsForDaysFreezing(String sql,
                                                final List<Object[]> dataSet) throws Exception {
        BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter() {
            public int getBatchSize() {
                return dataSet.size();
            }

            public void setValues(PreparedStatement ps, int i) {

                Object[] obj = dataSet.get(i);
                try {
                    ps.setObject(1, obj[4], Types.DOUBLE);
                    ps.setObject(2, obj[3], Types.TIMESTAMP);
                    ps.setObject(3, obj[0], Types.VARCHAR);
                    ps.setObject(4, obj[1], Types.VARCHAR);
                    ps.setObject(5, obj[2], Types.VARCHAR);
                    ps.setObject(6, obj[3], Types.TIMESTAMP);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        jdbcTemplate.batchUpdate(sql, setter);
        LogFactory.getInstance().saveLog("日冻结数据更新成功", LogDao.operation);
    }

    /**
     * 为了提高效率{批量插入曲线数据}
     * */
    public static void batchInsertRows(String sql, final List<Object[]> dataSet)
            throws Exception {
        BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter() {
            public int getBatchSize() {
                return dataSet.size();
            }

            public void setValues(PreparedStatement ps, int i) {

                Object[] obj = dataSet.get(i);
                try {
                    ps.setObject(1, obj[0], Types.VARCHAR);
                    ps.setObject(2, obj[1], Types.VARCHAR);
                    ps.setObject(3, obj[2], Types.VARCHAR);
                    ps.setObject(4, obj[3], Types.VARCHAR);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        try {

            DBConfigDao.jdbcTemplate.batchUpdate(sql, setter);
            System.out.println("批量执行SQL>......");
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e);
        }

        LogFactory.getInstance().saveLog("曲线记录存储成功", LogDao.operation);
    }

    /**
     * 插入断缓数据
     *
     * @param
     * @throws Exception
     */
    public static void batchInsertBroken(BrokenData brokenData) {
        String sql = "insert into broken_data(date_time,dataStr) values('"+brokenData.getDateTime()+"','"+brokenData.getDataStr()+"')";


        DBConfigDao.jdbcTemplate.update(sql);
        logger.info("插入断缓数据" + brokenData.getDateTime() + "成功......");

    }

    /**
     * 批量插入临时数据
     *
     * @param list
     * @throws Exception
     */
    public static void batchInsertTemp(List<EsmspSumMeasurOrganizationDay> list)
            throws Exception {
        String dateCode = "";
        String head = "insert into esmsp_sum_measur_organization_day_temp "
                + "(euo_code,EUS_CODE,MMP_CODE,DATE_CODE,point1,point2,point3,point4,"
                + "point5,point6,point7,point8,point9,point10,point11,point12,"
                + "point13,point14,point15,point16,point17,point18,point19,"
                + "point20,point21,point22,point23,point24,point25,point26,"
                + "point27,point28,point29,point30,point31,point32,point33,"
                + "point34,point35,point36,point37,point38,point39,point40,"
                + "point41,point42,point43,point44,point45,point46,point47,"
                + "point48,point49,point50,point51,point52,point53,point54,"
                + "point55,point56,point57,point58,point59,point60,point61,"
                + "point62,point63,point64,point65,point66,point67,point68,"
                + "point69,point70,point71,point72,point73,point74,point75,"
                + "point76,point77,point78,point79,point80,point81,point82,"
                + "point83,point84,point85,point86,point87,point88,point89,"
                + "point90,point91,point92,point93,point94,point95,point96,"
                + "MAX_VALUE,MAX_DATE,MIN_VALUE,MIN_DATE,AVG_VALUE,SUM_VALUE,"
                + "F_VALUE,P_VALUE,G_VALUE,last_value)values";
        if (list.size() > 0) {
            DBConfigDao.jdbcTemplate
                    .execute("delete from esmsp_sum_measur_organization_day_temp");
            Map<String, List<EsmspSumMeasurOrganizationDay>> map = subList(
                    list, 1000);
            for (String key : map.keySet()) {
                List<EsmspSumMeasurOrganizationDay> sublist = map.get(key);
                StringBuffer buffer = new StringBuffer();
                buffer.append(head);
                buffer = getBuffByDayTempList(sublist, dateCode, buffer);
                DBConfigDao.jdbcTemplate.update(buffer.toString());
                logger.info("批量执行插入临时数据" + key + "成功,共" + list.size()
                        + "条数据<SQL>......");
            }
            LogFactory.getInstance().saveLog("曲线记录存储成功", LogDao.operation);
        }
    }
    public static boolean updatePointValue(PointDevice pd,String value){
        String sql="update poms_device_measur_point set MMP_SET_VALUE='"+value+"' where ID='"+pd.getId()+"'";
        int result=jdbcTemplate.update(sql);
        if(result>0){
            pd.setShowValue(value);
            if(pd.getCode().equals("1100")){
                pd.setValue(new BigDecimal(value));
            }
            return true;
        }else{
            return false;
        }
    }
    private static StringBuffer getBuffByDayTempList(
            List<EsmspSumMeasurOrganizationDay> list, String dateCode,
            StringBuffer buffer) {
        for (int i = 0; i < list.size(); i++) {
            EsmspSumMeasurOrganizationDay day = list.get(i);
            // if (day.getIsSave() == 1) {
            if (dateCode.equals("")) {
                dateCode = day.getDateCode();
            }
            buffer.append(" ( '" + day.getEuoCode() + "','" + day.getEusCode()
                    + "','" + day.getMmpCode() + "','" + day.getDateCode()
                    + "'," + day.getPoint1() + "," + day.getPoint2() + ","
                    + day.getPoint3() + "," + day.getPoint4() + ","
                    + day.getPoint5() + "," + day.getPoint6() + ","
                    + day.getPoint7() + "," + day.getPoint8() + ","
                    + day.getPoint9() + "," + day.getPoint10() + ","
                    + day.getPoint11() + "," + day.getPoint12() + ","
                    + day.getPoint13() + "," + day.getPoint14() + ","
                    + day.getPoint15() + "," + day.getPoint16() + ","
                    + day.getPoint17() + "," + day.getPoint18() + ","
                    + day.getPoint19() + "," + day.getPoint20() + ","
                    + day.getPoint21() + "," + day.getPoint22() + ","
                    + day.getPoint23() + "," + day.getPoint24() + ","
                    + day.getPoint25() + "," + day.getPoint26() + ","
                    + day.getPoint27() + "," + day.getPoint28() + ","
                    + day.getPoint29() + "," + day.getPoint30() + ","
                    + day.getPoint31() + "," + day.getPoint32() + ","
                    + day.getPoint33() + "," + day.getPoint34() + ","
                    + day.getPoint35() + "," + day.getPoint36() + ","
                    + day.getPoint37() + "," + day.getPoint38() + ","
                    + day.getPoint39() + "," + day.getPoint40() + ","
                    + day.getPoint41() + "," + day.getPoint42() + ","
                    + day.getPoint43() + "," + day.getPoint44() + ","
                    + day.getPoint45() + "," + day.getPoint46() + ","
                    + day.getPoint47() + "," + day.getPoint48() + ","
                    + day.getPoint49() + "," + day.getPoint50() + ","
                    + day.getPoint51() + "," + day.getPoint52() + ","
                    + day.getPoint53() + "," + day.getPoint54() + ","
                    + day.getPoint55() + "," + day.getPoint56() + ","
                    + day.getPoint57() + "," + day.getPoint58() + ","
                    + day.getPoint59() + "," + day.getPoint60() + ","
                    + day.getPoint61() + "," + day.getPoint62() + ","
                    + day.getPoint63() + "," + day.getPoint64() + ","
                    + day.getPoint65() + "," + day.getPoint66() + ","
                    + day.getPoint67() + "," + day.getPoint68() + ","
                    + day.getPoint69() + "," + day.getPoint70() + ","
                    + day.getPoint71() + "," + day.getPoint72() + ","
                    + day.getPoint73() + "," + day.getPoint74() + ","
                    + day.getPoint75() + "," + day.getPoint76() + ","
                    + day.getPoint77() + "," + day.getPoint78() + ","
                    + day.getPoint79() + "," + day.getPoint80() + ","
                    + day.getPoint81() + "," + day.getPoint82() + ","
                    + day.getPoint83() + "," + day.getPoint84() + ","
                    + day.getPoint85() + "," + day.getPoint86() + ","
                    + day.getPoint87() + "," + day.getPoint88() + ","
                    + day.getPoint89() + "," + day.getPoint90() + ","
                    + day.getPoint91() + "," + day.getPoint92() + ","
                    + day.getPoint93() + "," + day.getPoint94() + ","
                    + day.getPoint95() + "," + day.getPoint96() + ","
                    + day.getMaxValue() + ",'" + day.getMaxDate() + "',"
                    + day.getMinValue() + ",'" + day.getMinDate() + "',"
                    + day.getAvgValue() + "," + day.getSumValue() + ","
                    + day.getfValue() + "," + day.getpValue() + ","
                    + day.getgValue() + "," + day.getLastValue() + ")");
            if (i != list.size() - 1) {
                buffer.append(",");
            }
            // }
        }
        return buffer;
    }

    private static StringBuffer getBuffByDayList(
            List<EsmspSumMeasurOrganizationDay> list, String dateCode,
            StringBuffer buffer) {
        for (int i = 0; i < list.size(); i++) {
            EsmspSumMeasurOrganizationDay day = list.get(i);
            // if (day.getIsSave() == 1) {
            if (dateCode.equals("")) {
                dateCode = day.getDateCode();
            }
            buffer.append(" ( '" + day.getEuoCode() + "','" + day.getEusCode()
                    + "','" + day.getMmpCode() + "','" + day.getDateCode()
                    + "'," + day.getPoint1() + "," + day.getPoint2() + ","
                    + day.getPoint3() + "," + day.getPoint4() + ","
                    + day.getPoint5() + "," + day.getPoint6() + ","
                    + day.getPoint7() + "," + day.getPoint8() + ","
                    + day.getPoint9() + "," + day.getPoint10() + ","
                    + day.getPoint11() + "," + day.getPoint12() + ","
                    + day.getPoint13() + "," + day.getPoint14() + ","
                    + day.getPoint15() + "," + day.getPoint16() + ","
                    + day.getPoint17() + "," + day.getPoint18() + ","
                    + day.getPoint19() + "," + day.getPoint20() + ","
                    + day.getPoint21() + "," + day.getPoint22() + ","
                    + day.getPoint23() + "," + day.getPoint24() + ","
                    + day.getPoint25() + "," + day.getPoint26() + ","
                    + day.getPoint27() + "," + day.getPoint28() + ","
                    + day.getPoint29() + "," + day.getPoint30() + ","
                    + day.getPoint31() + "," + day.getPoint32() + ","
                    + day.getPoint33() + "," + day.getPoint34() + ","
                    + day.getPoint35() + "," + day.getPoint36() + ","
                    + day.getPoint37() + "," + day.getPoint38() + ","
                    + day.getPoint39() + "," + day.getPoint40() + ","
                    + day.getPoint41() + "," + day.getPoint42() + ","
                    + day.getPoint43() + "," + day.getPoint44() + ","
                    + day.getPoint45() + "," + day.getPoint46() + ","
                    + day.getPoint47() + "," + day.getPoint48() + ","
                    + day.getPoint49() + "," + day.getPoint50() + ","
                    + day.getPoint51() + "," + day.getPoint52() + ","
                    + day.getPoint53() + "," + day.getPoint54() + ","
                    + day.getPoint55() + "," + day.getPoint56() + ","
                    + day.getPoint57() + "," + day.getPoint58() + ","
                    + day.getPoint59() + "," + day.getPoint60() + ","
                    + day.getPoint61() + "," + day.getPoint62() + ","
                    + day.getPoint63() + "," + day.getPoint64() + ","
                    + day.getPoint65() + "," + day.getPoint66() + ","
                    + day.getPoint67() + "," + day.getPoint68() + ","
                    + day.getPoint69() + "," + day.getPoint70() + ","
                    + day.getPoint71() + "," + day.getPoint72() + ","
                    + day.getPoint73() + "," + day.getPoint74() + ","
                    + day.getPoint75() + "," + day.getPoint76() + ","
                    + day.getPoint77() + "," + day.getPoint78() + ","
                    + day.getPoint79() + "," + day.getPoint80() + ","
                    + day.getPoint81() + "," + day.getPoint82() + ","
                    + day.getPoint83() + "," + day.getPoint84() + ","
                    + day.getPoint85() + "," + day.getPoint86() + ","
                    + day.getPoint87() + "," + day.getPoint88() + ","
                    + day.getPoint89() + "," + day.getPoint90() + ","
                    + day.getPoint91() + "," + day.getPoint92() + ","
                    + day.getPoint93() + "," + day.getPoint94() + ","
                    + day.getPoint95() + "," + day.getPoint96() + ","
                    + day.getMaxValue() + ",'" + day.getMaxDate() + "',"
                    + day.getMinValue() + ",'" + day.getMinDate() + "',"
                    + day.getAvgValue() + "," + day.getSumValue() + ","
                    + day.getfValue() + "," + day.getpValue() + ","
                    + day.getgValue() + ")");
            if (i != list.size() - 1) {
                buffer.append(",");
            }
        }
        // }
        return buffer;
    }

    /**
     * 为了提高效率{批量插入日数据}
     * */
    public static void batchInsertDay(List<EsmspSumMeasurOrganizationDay> list)
            throws Exception {
        String dateCode = "";
        String head = "insert into esmsp_sum_measur_organization_day "
                + "(euo_code,EUS_CODE,MMP_CODE,DATE_CODE,point1,point2,point3,point4,"
                + "point5,point6,point7,point8,point9,point10,point11,point12,"
                + "point13,point14,point15,point16,point17,point18,point19,"
                + "point20,point21,point22,point23,point24,point25,point26,"
                + "point27,point28,point29,point30,point31,point32,point33,"
                + "point34,point35,point36,point37,point38,point39,point40,"
                + "point41,point42,point43,point44,point45,point46,point47,"
                + "point48,point49,point50,point51,point52,point53,point54,"
                + "point55,point56,point57,point58,point59,point60,point61,"
                + "point62,point63,point64,point65,point66,point67,point68,"
                + "point69,point70,point71,point72,point73,point74,point75,"
                + "point76,point77,point78,point79,point80,point81,point82,"
                + "point83,point84,point85,point86,point87,point88,point89,"
                + "point90,point91,point92,point93,point94,point95,point96,"
                + "MAX_VALUE,MAX_DATE,MIN_VALUE,MIN_DATE,AVG_VALUE,SUM_VALUE,"
                + "F_VALUE,P_VALUE,G_VALUE)values";
        if (list.size() > 0) {
            Map<String, List<EsmspSumMeasurOrganizationDay>> map = subList(
                    list, 1000);
            for (String key : map.keySet()) {
                List<EsmspSumMeasurOrganizationDay> sublist = map.get(key);
                StringBuffer buffer = new StringBuffer();
                buffer.append(head);
                buffer = getBuffByDayList(sublist, dateCode, buffer);
                DBConfigDao.jdbcTemplate.update(buffer.toString());

                logger.info("批量执行插入日数据" + key + "成功,共" + list.size()
                        + "条数据<SQL>......");
            }
            DBConfigDao.jdbcTemplate
                    .execute("delete from esmsp_sum_measur_organization_day_temp");
            LogFactory.getInstance().saveLog("曲线记录存储成功", LogDao.operation);
        }
        // } else {
        // /**
        // * part1
        // */
        // List<EsmspSumMeasurOrganizationDay> daya = new
        // ArrayList<EsmspSumMeasurOrganizationDay>();
        // int i = 0;
        // for (; i < 1000; i++) {
        // daya.add(list.get(i));
        // }
        // StringBuffer buffera = new StringBuffer();
        // buffera.append(head);
        // buffera = getBuffByDayList(daya, dateCode, buffera);
        // DBConfigDao.jdbcTemplate
        // .execute("delete from esmsp_sum_measur_organization_day_temp");
        // DBConfigDao.jdbcTemplate.execute(buffera.toString());
        // logger.info("批量执行插入临时数据成功PART1<SQL>......sql:" + buffera);
        // LogFactory.getInstance().saveLog("曲线记录存储成功", LogDao.operation);
        // /**
        // * part2
        // */
        // List<EsmspSumMeasurOrganizationDay> dayb = new
        // ArrayList<EsmspSumMeasurOrganizationDay>();
        // for (; i < 2000; i++) {
        // if (i == list.size()) {
        // break;
        // }
        // dayb.add(list.get(i));
        // }
        // StringBuffer bufferb = new StringBuffer();
        // bufferb.append(head);
        // bufferb = getBuffByDayList(dayb, dateCode, bufferb);
        // if (dayb.size() > 0) {
        // DBConfigDao.jdbcTemplate.execute(bufferb.toString());
        // logger.info("批量执行插入临时数据成功PART2<SQL>......sql:" + bufferb);
        // LogFactory.getInstance().saveLog("曲线记录存储成功", LogDao.operation);
        // }
        // /**
        // * part3
        // */
        // List<EsmspSumMeasurOrganizationDay> dayc = new
        // ArrayList<EsmspSumMeasurOrganizationDay>();
        // for (; i < 3000; i++) {
        // if (i == list.size()) {
        // break;
        // }
        // dayc.add(list.get(i));
        // }
        // StringBuffer bufferc = new StringBuffer();
        // bufferc.append(head);
        // bufferc = getBuffByDayList(dayc, dateCode, bufferc);
        // if (dayc.size() > 0) {
        // DBConfigDao.jdbcTemplate.execute(bufferc.toString());
        // logger.info("批量执行插入临时数据成功PART3<SQL>......sql:" + bufferc);
        // LogFactory.getInstance().saveLog("曲线记录存储成功", LogDao.operation);
        // }
        // }

    }

    /**
     * 为了提高效率{批量插入月数据}
     * */
    public static void batchInsertMonth(
            List<EsmspSumMeasurOrganizationMonth> list) throws Exception {
        String dateCode = "";
        String sql = "insert into esmsp_sum_measur_organization_month "
                + "(euo_code,EUS_CODE,MMP_CODE,DATE_CODE,point1,point2,point3,point4,"
                + "point5,point6,point7,point8,point9,point10,point11,point12,"
                + "point13,point14,point15,point16,point17,point18,point19,"
                + "point20,point21,point22,point23,point24,point25,point26,"
                + "point27,point28,point29,point30,point31,"
                + "AVG_VALUE,SUM_VALUE," + "F_VALUE,P_VALUE,G_VALUE)values";
        for (int i = 0; i < list.size(); i++) {
            EsmspSumMeasurOrganizationMonth month = list.get(i);
            if (dateCode.equals("")) {
                dateCode = month.getDateCode();
            }
            sql += " ('" + month.getEuoCode() + "','" + month.getEusCode()
                    + "','" + month.getMmpCode() + "','" + month.getDateCode()
                    + "'," + month.getPoint1() + "," + month.getPoint2() + ","
                    + month.getPoint3() + "," + month.getPoint4() + ","
                    + month.getPoint5() + "," + month.getPoint6() + ","
                    + month.getPoint7() + "," + month.getPoint8() + ","
                    + month.getPoint9() + "," + month.getPoint10() + ","
                    + month.getPoint11() + "," + month.getPoint12() + ","
                    + month.getPoint13() + "," + month.getPoint14() + ","
                    + month.getPoint15() + "," + month.getPoint16() + ","
                    + month.getPoint17() + "," + month.getPoint18() + ","
                    + month.getPoint19() + "," + month.getPoint20() + ","
                    + month.getPoint21() + "," + month.getPoint22() + ","
                    + month.getPoint23() + "," + month.getPoint24() + ","
                    + month.getPoint25() + "," + month.getPoint26() + ","
                    + month.getPoint27() + "," + month.getPoint28() + ","
                    + month.getPoint29() + "," + month.getPoint30() + ","
                    + month.getPoint31() + "," + month.getAvgValue() + ","
                    + month.getSumValue() + "," + month.getfValue() + ","
                    + month.getpValue() + "," + month.getgValue() + ")";
            if (i != list.size() - 1) {
                sql += ",";
            }
        }
        if (list.size() > 0) {
            DBConfigDao.jdbcTemplate
                    .execute("delete from esmsp_sum_measur_organization_month where date_code='"
                            + dateCode + "'");
            DBConfigDao.jdbcTemplate.execute(sql);
            logger.info("批量执行插入月数据成功SQL>......");
            LogFactory.getInstance().saveLog("曲线记录存储成功", LogDao.operation);
        }
    }

    /**
     * 为了提高效率{批量插入年数据}
     * */
    public static void batchInsertYear(List<EsmspSumMeasurOrganizationYear> list)
            throws Exception {
        String dateCode = "";
        String sql = "insert into esmsp_sum_measur_organization_year "
                + "(euo_code,EUS_CODE,MMP_CODE,DATE_CODE,point1,point2,point3,point4,"
                + "point5,point6,point7,point8,point9,point10,point11,point12,"
                + "AVG_VALUE,SUM_VALUE," + "F_VALUE,P_VALUE,G_VALUE) values";
        for (int i = 0; i < list.size(); i++) {
            EsmspSumMeasurOrganizationYear year = list.get(i);
            if (dateCode.equals("")) {
                dateCode = year.getDateCode();
            }
            sql += "( '" + year.getEuoCode() + "','" + year.getEusCode()
                    + "','" + year.getMmpCode() + "','" + year.getDateCode()
                    + "'," + year.getPoint1() + "," + year.getPoint2() + ","
                    + year.getPoint3() + "," + year.getPoint4() + ","
                    + year.getPoint5() + "," + year.getPoint6() + ","
                    + year.getPoint7() + "," + year.getPoint8() + ","
                    + year.getPoint9() + "," + year.getPoint10() + ","
                    + year.getPoint11() + "," + year.getPoint12() + ","
                    + year.getAvgValue() + "," + year.getSumValue() + ","
                    + year.getfValue() + "," + year.getpValue() + ","
                    + year.getgValue() + ")";
            if (i != list.size() - 1) {
                sql += ",";
            }
        }
        if (list.size() > 0) {
            DBConfigDao.jdbcTemplate
                    .execute("delete from esmsp_sum_measur_organization_year where date_code='"
                            + dateCode + "'");
            DBConfigDao.jdbcTemplate.execute(sql);
            System.out.println("批量插入年数据成功SQL>......");
            LogFactory.getInstance().saveLog("曲线记录存储成功", LogDao.operation);
        }
    }

    /**
     * 根据sql查询数量
     *
     * @param sql
     * @param obj
     * @return
     */
    private int getCount(String sql, Object[] obj) {
        return jdbcTemplate.queryForInt(sql, new Object[]{obj[0], obj[1],
                obj[2], obj[3]});
    }

    public static void batchInsertMonthReport() {
        List<EsmspSumMonth> currentmonthlist = makeCurrentMonthReport();
        StringBuffer buff = new StringBuffer();
        buff.append("insert into esmsp_sum_month "
                + "(date_code,ctd_code,ctd_name,last_value,"
                + "curr_value,value,value_change,code_order)values");
        int i = 0;
        for (EsmspSumMonth month : currentmonthlist) {
            buff.append("('" + month.getDateCode() + "','" + month.getEusCode()
                    + "','" + month.getEusName() + "'," + month.getLastValue()
                    + "," + month.getCurrentValue() + "," + month.getValue()
                    + "," + month.getValueChange() + "," + month.getCodeOrder()
                    + ")");
            i++;
            if (i != currentmonthlist.size()) {
                buff.append(",");
            }
        }
        DBConfigDao.jdbcTemplate.execute(buff.toString());
        logger.info("月度报表数据插入成功");
    }
    public static void batchInsertAlarm(PomsCalculateAlterRecord record) {
        StringBuffer buff = new StringBuffer();
        buff.append("insert into poms_calculate_alter_record "
                + "(SYSTEM_CODE,EUS_CODES,EUS_NAME,MMP_CODES,MMP_NAME,ALTER_LEVEL,ALTER_TYPE,ALTER_VALUE,SET_VALUE,DATE_TIME"
                + ")values");
            buff.append("('" +record.getSystemCode()+"','"+record.getEusCodes() + "','" + record.getEusName()
                    + "','" + record.getMmpCodes() + "','" + record.getMmpName()
                    + "'," + record.getAlterLevel() + "," + record.getAlterType()
                    + "," + record.getAlterValue() + "," + record.getSetValue()+",'"
                    + record.getDateTime()+"')");
        DBConfigDao.jdbcTemplate.execute(buff.toString());
        logger.info(record.getEusName()+"===>"+record.getMmpName()+"报警信息插入成功");
    }
    private static List<EsmspSumMonth> makeCurrentMonthReport() {
        String dateCode = new SimpleDateFormat("yyyyMM").format(new Date());
        List<EsmspSumMonth> lastmonthlist = getLastMonthReport();
        List<EsmspCalSumRule> rulelist = getCalSumRule();
        List<EsmspSumMonth> currentmonthlist = new ArrayList<EsmspSumMonth>();
        for (EsmspSumMonth m : lastmonthlist) {
            BigDecimal lastvalue =  new BigDecimal(0);
            BigDecimal currentvalue = new BigDecimal(0);
            BigDecimal value =  new BigDecimal(0);
            BigDecimal valuechange =  new BigDecimal(0);
            boolean isExistData = false;
            boolean isSumRuleCode = false;
            for (EsmspSumMeasurOrganizationDay day : BussinessConfig.config.daylist) {
                if (day.getMmpCode().equals("31_")) {
                    if (day.getEusCode().equals(m.getEusCode())) {
                        isExistData = true;
                        currentvalue = day.getPoint40();
                        break;
                    }
                }
            }
            EsmspSumMonth currentmonth = new EsmspSumMonth();
            if (!isExistData) {// 当月无能耗数据
                currentvalue = m.getCurrentValue();
                for (EsmspCalSumRule rule : rulelist) {
                    if (m.getEusCode().equals(rule.getCtd_code())) {
                        isSumRuleCode = true;
                        break;
                    }
                }
            }
            lastvalue = m.getCurrentValue();
            value = currentvalue.subtract(lastvalue);
            valuechange = value.subtract(m.getValue());
            currentmonth.setEusCode(m.getEusCode());
            currentmonth.setEusName(m.getEusName());
            currentmonth.setDateCode(dateCode);
            currentmonth.setLastValue(lastvalue);
            currentmonth.setCurrentValue(currentvalue);
            currentmonth.setValue(value);
            if (isSumRuleCode) {
                currentmonth.setValueChange(m.getValue());
            } else {
                currentmonth.setValueChange(valuechange);
            }
            currentmonth.setCodeOrder(m.getCodeOrder());
            currentmonthlist.add(currentmonth);
        }
        return ruleSumHandle(currentmonthlist, rulelist);
    }

    private static List<EsmspSumMonth> ruleSumHandle(
            List<EsmspSumMonth> months, List<EsmspCalSumRule> rules) {
        Map<String, BigDecimal> map = new HashMap<String, BigDecimal>();
        for (EsmspCalSumRule rule : rules) {
            String[] plus = new String[0];
            String[] minus = new String[0];
            if (rule.getValue_plus() != null) {
                plus = rule.getValue_plus().split(",");
            }
            if (rule.getValue_minus() != null) {
                minus = rule.getValue_minus().split(",");
            }
            BigDecimal value = new BigDecimal(0);
            for (EsmspSumMonth month : months) {
                for (String p : plus) {// 加法运算
                    if (month.getEusCode().equals(p)) {
                        value.add(month.getValue());
                        break;
                    }
                }
                for (String m : minus) {// 减法运算
                    if (month.getEusCode().equals(m)) {
                        value.subtract(
                        month.getValue());
                        break;
                    }
                }
            }
            map.put(rule.getCtd_code(), value);
        }
        for (String key : map.keySet()) {
            BigDecimal value = map.get(key);
            for (EsmspSumMonth m : months) {
                if (m.getEusCode().equals(key)) {
                    BigDecimal lastvalue = m.getValueChange();
                    m.setValue(value);
                    m.setValueChange(value .subtract( lastvalue));
                }
            }
        }
        return months;
    }

    private static List<EsmspSumMonth> getLastMonthReport() {
        String lastDateCode = DateUtils.getLastMonth();
        List<EsmspSumMonth> monthlist = new ArrayList<EsmspSumMonth>();
        monthlist = DBConfigDao.jdbcTemplate.query(
                "select * from esmsp_sum_month where date_code='"
                        + lastDateCode + "' order by code_order",
                new RowMapper() {
                    @Override
                    public EsmspSumMonth mapRow(ResultSet rs, int index)
                            throws SQLException {
                        EsmspSumMonth month = new EsmspSumMonth();
                        month.setDateCode(rs.getString("date_code"));
                        month.setEusCode(rs.getString("ctd_code"));
                        month.setEusName(rs.getString("ctd_name"));
                        month.setLastValue(rs.getBigDecimal("last_value"));
                        month.setCurrentValue(rs.getBigDecimal("curr_value"));
                        month.setValue(rs.getBigDecimal("value"));
                        month.setCodeOrder(rs.getInt("code_order"));
                        return month;
                    }
                });
        return monthlist;
    }

    private static List<EsmspCalSumRule> getCalSumRule() {
        List<EsmspCalSumRule> monthlist = new ArrayList<EsmspCalSumRule>();
        monthlist = DBConfigDao.jdbcTemplate
                .query(
                        "SELECT  ctd_code, ctd_name, value_plus, value_minus, "
                                + "code_order FROM  esmsp_cal_sum_value ORDER BY ctd_code",
                        new RowMapper() {
                            @Override
                            public EsmspCalSumRule mapRow(ResultSet rs,
                                                          int index) throws SQLException {
                                EsmspCalSumRule rule = new EsmspCalSumRule();
                                rule.setCtd_code(rs.getString("ctd_code"));
                                rule.setCtd_name(rs.getString("ctd_name"));
                                rule.setValue_plus(rs.getString("value_plus"));
                                rule
                                        .setValue_minus(rs
                                                .getString("value_minus"));
                                return rule;
                            }
                        });
        return monthlist;
    }

    /**
     * 实现java 中 list集合中有几十万条数据,每100条为一组取出
     *
     * @param list
     *            可穿入几十万条数据的List
     * @return map 每一Kye中有100条数据的List
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static Map<String, List<EsmspSumMeasurOrganizationDay>> subList(
            List<EsmspSumMeasurOrganizationDay> list, int size) {

        int listSize = list.size();
        int toIndex = size;
        Map map = new LinkedHashMap<String, List<EsmspSumMeasurOrganizationDay>>(); // 用map存起来新的分组后数据
        int keyToken = 0;
        for (int i = 0; i < list.size(); i += size) {
            if (i + size > listSize) { // 作用为toIndex最后没有100条数据则剩余几条newList中就装几条
                toIndex = listSize - i;
            }
            List newList = list.subList(i, i + toIndex);
            map.put(i + "-" + (i + toIndex) + "条数据", newList);
            keyToken++;
        }

        return map;
    }

}
