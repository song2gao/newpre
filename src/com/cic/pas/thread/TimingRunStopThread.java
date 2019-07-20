package com.cic.pas.thread;

import com.cic.domain.AutoOpenStop;
import com.cic.domain.StartStopTime;
import com.cic.pas.common.bean.MeterDevice;
import com.cic.pas.common.bean.PointDevice;
import com.cic.pas.common.util.DateUtils;
import com.cic.pas.common.util.ModBusReadAndWrite;
import com.cic.pas.service.ConnectorContext;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

public class TimingRunStopThread extends BaseThread {
    private MeterDevice md;
    private Logger logger = Logger.getLogger(TimingRunStopThread.class);
    private Map<String, List<StartStopTime>> map;
    private PointDevice pd;
    private boolean auto = false;
    int slaveId;
    String mdType;
    int mdIncrease;
    int modWAddress;
    int wFunction;
    int wType;
    int wLength;
    int isBit;
    String wFormular;
    private GetDataThread thread;
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

    public TimingRunStopThread(MeterDevice md, GetDataThread thread) {
        this.slaveId = md.getAddress();
        this.mdType = md.getType();
        this.mdIncrease=md.getIncrease();
        this.thread = thread;
        for (PointDevice p : md.getPointDevice()) {
            update(p, null);
        }
    }

    public void update(PointDevice p, String value) {
        try {
            String rwValue = "";
            if (value == null) {
                rwValue = p.getShowValue();
            } else {
                rwValue = value;
            }
            switch (p.getCode()) {
                case "311":
                    this.pd=p;
                    this.modWAddress = p.getModWAddress()+mdIncrease;
                    this.wFunction = p.getModWFunction();
                    this.wType = p.getModWType();
                    this.wLength = p.getModWlength();
                    this.isBit = p.getIsBit();
                    this.wFormular = p.getModWFormular();
                    break;
                case "1001":
                case "1002":
                case "1003":
                    if (rwValue != null && !rwValue.equals("")) {
                        rwValue = rwValue.replaceAll("#", "");
                        JSONObject json = JSONObject.fromObject(rwValue);
                        AutoOpenStop autoOpenStop = (AutoOpenStop) JSONObject.toBean(json, AutoOpenStop.class);
                        getStartStopTime(autoOpenStop, p.getCode());
                    }
                    break;
                case "1100":
                    this.auto = rwValue.equals("1") ? true : false;
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        logger.info(this.getName() + "自控线程启动");
        while (!exit) {
            if (auto) {
                try {
                    Date mydate = new Date();
                    String dayOfWeek = DateUtils.getDayOfWeek(mydate);
                    Date current = sdf.parse(sdf.format(mydate));
                    if (map.containsKey(dayOfWeek)) {//今天是否有控制逻辑
                        if (checkCurrentInList(dayOfWeek, current)) {
                            if (pd.getValue().intValue() == 0) {
                                boolean result = thread.write(md,pd, 1.0);
                                logger.info("系统启动主机:" + result + "==>定时控制");
                            }
                        } else {
                            if (pd.getValue().intValue() == 1) {
                                boolean result = thread.write(md,pd, 0.0);
                                logger.info("系统关闭主机:" + result + "==>定时控制");
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private void getStartStopTime(AutoOpenStop autoOpenStop, String timeCode) {
        if (map == null) {
            map = new HashMap<String, List<StartStopTime>>();
        }
        String[] dates = autoOpenStop.getDates();
        for (String date : dates) {
            try {
                if (map.containsKey(date)) {
                    List<StartStopTime> list = map.get(date);
                    boolean isFind = true;
                    for (StartStopTime time : list) {
                        if (time.getTimeCode().equals(timeCode)) {
                            time.setStartTime(sdf.parse(autoOpenStop.getStarttime()));
                            time.setStopTime(sdf.parse(autoOpenStop.getStoptime()));
                            isFind = true;
                            break;
                        }
                    }
                    if (!isFind) {
                        StartStopTime time = new StartStopTime();
                        time.setTimeCode(timeCode);
                        time.setStartTime(sdf.parse(autoOpenStop.getStarttime()));
                        time.setStopTime(sdf.parse(autoOpenStop.getStoptime()));
                        list.add(time);
                    }
                } else {
                    List<StartStopTime> list = new ArrayList<StartStopTime>();
                    StartStopTime time = new StartStopTime();
                    time.setTimeCode(timeCode);
                    time.setStartTime(sdf.parse(autoOpenStop.getStarttime()));
                    time.setStopTime(sdf.parse(autoOpenStop.getStoptime()));
                    list.add(time);
                    map.put(date, list);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /***
     * 判断当前时间是否在设定时间段内
     * @param dayOfWeek 星期X
     * @param  current 当前时间
     * @return
     */
    private boolean checkCurrentInList(String dayOfWeek, Date current) {
        List<StartStopTime> list = map.get(dayOfWeek);//今天时间段集合
        for (StartStopTime time : list) {
            if (current.after(time.getStartTime()) && current.before(time.getStopTime())) {
                return true;
            }
        }
        return false;
    }

}
