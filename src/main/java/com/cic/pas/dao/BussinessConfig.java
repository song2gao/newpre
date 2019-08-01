package com.cic.pas.dao;

import java.math.BigDecimal;
import java.net.SocketAddress;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import javax.annotation.Resource;

import com.cic.pas.common.bean.*;
import com.cic.pas.common.util.DateUtils;
import org.apache.mina.core.session.IoSession;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.cic.pas.mapper.PreCurveRecordMapper;
import com.cic.pas.service.ServerContext;
import com.sun.istack.internal.FinalArrayList;

/**
 * @author Administrator
 */
@SuppressWarnings("unchecked")
public final class BussinessConfig {

    public static JdbcTemplate jdbcTemplate = DBConfigDao.jdbcTemplate;

    public static BussinessConfig config = new BussinessConfig();

    public static int a;

    public static int b;

    public static List<Channel> channelList = Collections
            .synchronizedList(new ArrayList<Channel>());

//    public static List<TerminalDevice> terminalList = Collections
//            .synchronizedList(new ArrayList<TerminalDevice>());
    /**
     * 只会有一个线程对TerminalDevice进行修改，其它只是读取  所以不做线程安全定义
     */
    public static List<TerminalDevice> terminalList = new ArrayList<TerminalDevice>();

    public static List<TerminalDevice> terminalInfo = Collections
            .synchronizedList(new ArrayList<TerminalDevice>());

    public static List<EsmspSumMeasurOrganizationDay> daylist = Collections
            .synchronizedList(new ArrayList<EsmspSumMeasurOrganizationDay>());

    public static List<EsmspSumMeasurOrganizationMonth> monthlist = Collections
            .synchronizedList(new ArrayList<EsmspSumMeasurOrganizationMonth>());

    public static List<EsmspSumMeasurOrganizationYear> yearlist = Collections
            .synchronizedList(new ArrayList<EsmspSumMeasurOrganizationYear>());


    public static List<SystemParams> systemParams = Collections
            .synchronizedList(new ArrayList<SystemParams>());

    public static final int AMMETER_Parameter_SIZE = 31;

    public static final int METER_Parameter_SIZE = 1;

    public static List<BrokenData> brokenDataList = new ArrayList<BrokenData>();

    private BussinessConfig() {

    }

    static {
//        /**
//         * 断缓数据
//         */
//        brokenDataList = jdbcTemplate
//                .query(
//                        "select date_time,dataStr from broken_data;",
//                        new RowMapper() {
//
//                            @Override
//                            public BrokenData mapRow(
//                                    ResultSet rs, int index)
//                                    throws SQLException {
//                                // TODO Auto-generated method stub
//                                BrokenData brokenData = new BrokenData();
//                                brokenData.setDateTime(rs.getString("date_time"));
//                                brokenData.setDataStr(rs.getString("dataStr"));
//                                return brokenData;
//                            }
//
//                        });
        /**
         * 年数据
         */
        yearlist = jdbcTemplate.query(
                "select euo_code, MMP_CODE,EUS_CODE,DATE_CODE,point1,point2,point3,"
                        + "point4,point5,point6,point7,point8,point9,point10,point11,"
                        + "point12,AVG_VALUE,SUM_VALUE,F_VALUE,G_VALUE,P_VALUE FROM "
                        + "esmsp_sum_measur_organization_year where "
                        + "DATE_CODE='" + DateUtils.getYear() + "';",
                new RowMapper() {

                    @Override
                    public EsmspSumMeasurOrganizationYear mapRow(
                            ResultSet rs, int index)
                            throws SQLException {
                        // TODO Auto-generated method stub
                        EsmspSumMeasurOrganizationYear year = new EsmspSumMeasurOrganizationYear();
                        year.setEuoCode(rs.getString("euo_code"));
                        year.setEusCode(rs.getString("eus_code"));
                        year.setMmpCode(rs.getString("mmp_code"));
                        year.setDateCode(rs.getString("date_code"));
                        year.setPoint1(rs.getBigDecimal("point1"));
                        year.setPoint2(rs.getBigDecimal("point2"));
                        year.setPoint3(rs.getBigDecimal("point3"));
                        year.setPoint4(rs.getBigDecimal("point4"));
                        year.setPoint5(rs.getBigDecimal("point5"));
                        year.setPoint6(rs.getBigDecimal("point6"));
                        year.setPoint7(rs.getBigDecimal("point7"));
                        year.setPoint8(rs.getBigDecimal("point8"));
                        year.setPoint9(rs.getBigDecimal("point9"));
                        year.setPoint10(rs.getBigDecimal("point10"));
                        year.setPoint11(rs.getBigDecimal("point11"));
                        year.setPoint12(rs.getBigDecimal("point12"));
                        year.setSumValue(rs.getBigDecimal("sum_value"));
                        year.setfValue(rs.getBigDecimal("f_value"));
                        year.setpValue(rs.getBigDecimal("p_value"));
                        year.setgValue(rs.getBigDecimal("g_value"));
                        return year;
                    }

                });
        /**
         * 月数据
         */
        monthlist = jdbcTemplate
                .query(
                        "select euo_code, MMP_CODE,EUS_CODE,DATE_CODE,point1,point2,point3,"
                                + "point4,point5,point6,point7,point8,point9,point10,point11,"
                                + "point12,point13,point14,point15,point16,point17,point18,"
                                + "point19,point20,point21,point22,point23,point24,point25,"
                                + "point26,point27,point28,point29,point30,point31,MIN_VALUE,"
                                + "AVG_VALUE,SUM_VALUE,F_VALUE,G_VALUE,P_VALUE FROM "
                                + "esmsp_sum_measur_organization_month where "
                                + "DATE_CODE='" + DateUtils.getYearMonth1() + "';",
                        new RowMapper() {
                            @Override
                            public EsmspSumMeasurOrganizationMonth mapRow(
                                    ResultSet rs, int index)
                                    throws SQLException {
                                // TODO Auto-generated method stub
                                EsmspSumMeasurOrganizationMonth month = new EsmspSumMeasurOrganizationMonth();
                                month.setEuoCode(rs.getString("euo_code"));
                                month.setEusCode(rs.getString("eus_code"));
                                month.setMmpCode(rs.getString("mmp_code"));
                                month.setDateCode(rs.getString("date_code"));
                                month.setPoint1(rs.getBigDecimal("point1"));
                                month.setPoint2(rs.getBigDecimal("point2"));
                                month.setPoint3(rs.getBigDecimal("point3"));
                                month.setPoint4(rs.getBigDecimal("point4"));
                                month.setPoint5(rs.getBigDecimal("point5"));
                                month.setPoint6(rs.getBigDecimal("point6"));
                                month.setPoint7(rs.getBigDecimal("point7"));
                                month.setPoint8(rs.getBigDecimal("point8"));
                                month.setPoint9(rs.getBigDecimal("point9"));
                                month.setPoint10(rs.getBigDecimal("point10"));
                                month.setPoint11(rs.getBigDecimal("point11"));
                                month.setPoint12(rs.getBigDecimal("point12"));
                                month.setPoint13(rs.getBigDecimal("point13"));
                                month.setPoint14(rs.getBigDecimal("point14"));
                                month.setPoint15(rs.getBigDecimal("point15"));
                                month.setPoint16(rs.getBigDecimal("point16"));
                                month.setPoint17(rs.getBigDecimal("point17"));
                                month.setPoint18(rs.getBigDecimal("point18"));
                                month.setPoint19(rs.getBigDecimal("point19"));
                                month.setPoint20(rs.getBigDecimal("point20"));
                                month.setPoint21(rs.getBigDecimal("point21"));
                                month.setPoint22(rs.getBigDecimal("point22"));
                                month.setPoint23(rs.getBigDecimal("point23"));
                                month.setPoint24(rs.getBigDecimal("point24"));
                                month.setPoint25(rs.getBigDecimal("point25"));
                                month.setPoint26(rs.getBigDecimal("point26"));
                                month.setPoint27(rs.getBigDecimal("point27"));
                                month.setPoint28(rs.getBigDecimal("point28"));
                                month.setPoint29(rs.getBigDecimal("point29"));
                                month.setPoint30(rs.getBigDecimal("point30"));
                                month.setPoint31(rs.getBigDecimal("point31"));
                                month.setSumValue(rs.getBigDecimal("sum_value"));
                                month.setfValue(rs.getBigDecimal("f_value"));
                                month.setpValue(rs.getBigDecimal("p_value"));
                                month.setgValue(rs.getBigDecimal("g_value"));
                                return month;
                            }

                        });
        /**
         * 日数据
         */
        daylist = jdbcTemplate
                .query(
                        " select euo_code, MMP_CODE,EUS_CODE,DATE_CODE,point1,point2,"
                                + "point3,point4,point5,point6,point7,point8,point9,"
                                + "point10,point11,point12,point13,point14,point15,"
                                + "point16,point17,point18,point19,point20,point21,"
                                + "point22,point23,point24,point25,point26,point27,"
                                + "point28,point29,point30,point31,point32,point33,"
                                + "point34,point35,point36,point37,point38,point39,"
                                + "point40,point41,point42,point43,point44,point45,"
                                + "point46,point47,point48,point49,point50,point51,"
                                + "point52,point53,point54,point55,point56,point57,"
                                + "point58,point59,point60,point61,point62,point63,"
                                + "point64,point65,point66,point67,point68,point69,"
                                + "point70,point71,point72,point73,point74,point75,"
                                + "point76,point77,point78,point79,point80,point81,"
                                + "point82,point83,point84,point85,point86,point87,"
                                + "point88,point89,point90,point91,point92,point93,"
                                + "point94,point95,point96,MAX_VALUE,MAX_DATE,"
                                + "MIN_VALUE,MIN_DATE,AVG_VALUE,SUM_VALUE,F_VALUE,"
                                + "G_VALUE,P_VALUE,last_value FROM esmsp_sum_measur_organization_day_temp ",
                        new RowMapper() {
                            @Override
                            public EsmspSumMeasurOrganizationDay mapRow(
                                    ResultSet rs, int index)
                                    throws SQLException {
                                // TODO Auto-generated method stub
                                EsmspSumMeasurOrganizationDay day = new EsmspSumMeasurOrganizationDay();
                                day.setEuoCode(rs.getString("euo_code"));
                                day.setEusCode(rs.getString("eus_code"));
                                day.setMmpCode(rs.getString("mmp_code"));
                                day.setDateCode(rs.getString("date_code"));
                                day.setPoint1(rs.getBigDecimal("point1"));
                                day.setPoint2(rs.getBigDecimal("point2"));
                                day.setPoint3(rs.getBigDecimal("point3"));
                                day.setPoint4(rs.getBigDecimal("point4"));
                                day.setPoint5(rs.getBigDecimal("point5"));
                                day.setPoint6(rs.getBigDecimal("point6"));
                                day.setPoint7(rs.getBigDecimal("point7"));
                                day.setPoint8(rs.getBigDecimal("point8"));
                                day.setPoint9(rs.getBigDecimal("point9"));
                                day.setPoint10(rs.getBigDecimal("point10"));
                                day.setPoint11(rs.getBigDecimal("point11"));
                                day.setPoint12(rs.getBigDecimal("point12"));
                                day.setPoint13(rs.getBigDecimal("point13"));
                                day.setPoint14(rs.getBigDecimal("point14"));
                                day.setPoint15(rs.getBigDecimal("point15"));
                                day.setPoint16(rs.getBigDecimal("point16"));
                                day.setPoint17(rs.getBigDecimal("point17"));
                                day.setPoint18(rs.getBigDecimal("point18"));
                                day.setPoint19(rs.getBigDecimal("point19"));
                                day.setPoint20(rs.getBigDecimal("point20"));
                                day.setPoint21(rs.getBigDecimal("point21"));
                                day.setPoint22(rs.getBigDecimal("point22"));
                                day.setPoint23(rs.getBigDecimal("point23"));
                                day.setPoint24(rs.getBigDecimal("point24"));
                                day.setPoint25(rs.getBigDecimal("point25"));
                                day.setPoint26(rs.getBigDecimal("point26"));
                                day.setPoint27(rs.getBigDecimal("point27"));
                                day.setPoint28(rs.getBigDecimal("point28"));
                                day.setPoint29(rs.getBigDecimal("point29"));
                                day.setPoint30(rs.getBigDecimal("point30"));
                                day.setPoint31(rs.getBigDecimal("point31"));
                                day.setPoint32(rs.getBigDecimal("point32"));
                                day.setPoint33(rs.getBigDecimal("point33"));
                                day.setPoint34(rs.getBigDecimal("point34"));
                                day.setPoint35(rs.getBigDecimal("point35"));
                                day.setPoint36(rs.getBigDecimal("point36"));
                                day.setPoint37(rs.getBigDecimal("point37"));
                                day.setPoint38(rs.getBigDecimal("point38"));
                                day.setPoint39(rs.getBigDecimal("point39"));
                                day.setPoint40(rs.getBigDecimal("point40"));
                                day.setPoint41(rs.getBigDecimal("point41"));
                                day.setPoint42(rs.getBigDecimal("point42"));
                                day.setPoint43(rs.getBigDecimal("point43"));
                                day.setPoint44(rs.getBigDecimal("point44"));
                                day.setPoint45(rs.getBigDecimal("point45"));
                                day.setPoint46(rs.getBigDecimal("point46"));
                                day.setPoint47(rs.getBigDecimal("point47"));
                                day.setPoint48(rs.getBigDecimal("point48"));
                                day.setPoint49(rs.getBigDecimal("point49"));
                                day.setPoint50(rs.getBigDecimal("point50"));
                                day.setPoint51(rs.getBigDecimal("point51"));
                                day.setPoint52(rs.getBigDecimal("point52"));
                                day.setPoint53(rs.getBigDecimal("point53"));
                                day.setPoint54(rs.getBigDecimal("point54"));
                                day.setPoint55(rs.getBigDecimal("point55"));
                                day.setPoint56(rs.getBigDecimal("point56"));
                                day.setPoint57(rs.getBigDecimal("point57"));
                                day.setPoint58(rs.getBigDecimal("point58"));
                                day.setPoint59(rs.getBigDecimal("point59"));
                                day.setPoint60(rs.getBigDecimal("point60"));
                                day.setPoint61(rs.getBigDecimal("point61"));
                                day.setPoint62(rs.getBigDecimal("point62"));
                                day.setPoint63(rs.getBigDecimal("point63"));
                                day.setPoint64(rs.getBigDecimal("point64"));
                                day.setPoint65(rs.getBigDecimal("point65"));
                                day.setPoint66(rs.getBigDecimal("point66"));
                                day.setPoint67(rs.getBigDecimal("point67"));
                                day.setPoint68(rs.getBigDecimal("point68"));
                                day.setPoint69(rs.getBigDecimal("point69"));
                                day.setPoint70(rs.getBigDecimal("point70"));
                                day.setPoint71(rs.getBigDecimal("point71"));
                                day.setPoint72(rs.getBigDecimal("point72"));
                                day.setPoint73(rs.getBigDecimal("point73"));
                                day.setPoint74(rs.getBigDecimal("point74"));
                                day.setPoint75(rs.getBigDecimal("point75"));
                                day.setPoint76(rs.getBigDecimal("point76"));
                                day.setPoint77(rs.getBigDecimal("point77"));
                                day.setPoint78(rs.getBigDecimal("point78"));
                                day.setPoint79(rs.getBigDecimal("point79"));
                                day.setPoint80(rs.getBigDecimal("point80"));
                                day.setPoint81(rs.getBigDecimal("point81"));
                                day.setPoint82(rs.getBigDecimal("point82"));
                                day.setPoint83(rs.getBigDecimal("point83"));
                                day.setPoint84(rs.getBigDecimal("point84"));
                                day.setPoint85(rs.getBigDecimal("point85"));
                                day.setPoint86(rs.getBigDecimal("point86"));
                                day.setPoint87(rs.getBigDecimal("point87"));
                                day.setPoint88(rs.getBigDecimal("point88"));
                                day.setPoint89(rs.getBigDecimal("point89"));
                                day.setPoint90(rs.getBigDecimal("point90"));
                                day.setPoint91(rs.getBigDecimal("point91"));
                                day.setPoint92(rs.getBigDecimal("point92"));
                                day.setPoint93(rs.getBigDecimal("point93"));
                                day.setPoint94(rs.getBigDecimal("point94"));
                                day.setPoint95(rs.getBigDecimal("point95"));
                                day.setPoint96(rs.getBigDecimal("point96"));
                                day.setMaxValue(rs.getBigDecimal("max_value"));
                                day.setMaxDate(rs.getString("max_date"));
                                day.setMinValue(rs.getBigDecimal("min_value"));
                                day.setMinDate(rs.getString("min_date"));
                                day.setSumValue(rs.getBigDecimal("sum_value"));
                                day.setfValue(rs.getBigDecimal("f_value"));
                                day.setpValue(rs.getBigDecimal("p_value"));
                                day.setgValue(rs.getBigDecimal("g_value"));
                                day.setLastValue(rs.getBigDecimal("last_value"));
                                return day;
                            }

                        });
        /*
         * 用能系统参数
         * ↓ ↓ ↓ ↓ ↓ PointDevice 测点 ↓
         */
//        systemParams = jdbcTemplate.query(
//                "select * from systemparam;",
//                new RowMapper() {
//                    @Override
//                    public SystemParams mapRow(ResultSet rs, int index)
//                            throws SQLException {
//                        SystemParams param = new SystemParams();
//                        param.setEusCode(rs.getString("eus_code"));
//                        param.setEusName(rs.getString("eus_names"));
//                        param.setEulId(rs.getString("eul_id"));
//                        param.setEulName(rs.getString("eul_Name"));
//                        param.setParamCode(rs.getString("param_code"));
//                        param.setParamName(rs.getString("param_name"));
//                        param.setParamUnit(rs.getString("param_unit"));
//                        param.setCalCode(rs.getString("cal_id"));
//                        param.setMmpCode(rs.getString("mmp_code"));
//                        param.setParamType(rs.getInt("param_type"));
//                        return param;
//                    }
//                });

        /*
         * 数据结构 channel 通道 ↓ ↓ ↓ ↓ ↓ terminalDevice 采集器 ↓ ↓ ↓ ↓ ↓ MeterDevice 表计
         * ↓ ↓ ↓ ↓ ↓ PointDevice 测点 ↓
         */
        channelList = jdbcTemplate.query(
                "select * from pre_channel_info where cha_runStatus=1",
                new RowMapper() {
                    @Override
                    public Channel mapRow(ResultSet rs, int index)
                            throws SQLException {
                        Channel c = new Channel();
                        c.setPrimaryKey(rs.getString("id"));
                        c.setCha_number(rs.getString("cha_number"));
                        c.setCha_addressPort(rs.getString("cha_addressPort"));
                        c.setCha_useMark(rs.getInt("cha_USEMARK"));
                        c.setCha_detectionMark(rs
                                .getString("cha_DETECTIONMARK"));
                        c.setCha_commMode(rs.getString("cha_COMMMODE"));
                        c.setCha_protocolCode(rs.getString("cha_PROTOCOLCODE"));
                        c.setCha_runStatus(rs.getString("CHA_RUNSTATUS"));
                        c.setCha_reserveChannel(rs
                                .getString("cha_RESERVECHANNEL"));

                        return c;
                    }
                });

        for (final Channel c : channelList) {
            List<TerminalDevice> tList = jdbcTemplate
                    .query(
                            "select * from poms_assembled_terminal_device where CHANNEL_ID=? and asstd_run_status=1",
                            new Object[]{c.getPrimaryKey()},
                            new RowMapper() {
                                @Override
                                public TerminalDevice mapRow(ResultSet rs,
                                                             int index) throws SQLException {
                                    TerminalDevice t = new TerminalDevice();
                                    t.setPrimaryKey(rs.getString("id"));
                                    t.setCode(rs.getString("asstd_code"));
                                    t.setName(rs.getString("asstd_names"));
                                    t.setDeviceModel(rs.getString("asstd_model"));
                                    t.setProduction(rs
                                            .getString("asstd_production"));
                                    t.setMSA(rs.getString("asstd_assemble_ip"));
                                    t.setRoundCycle(rs.getInt("ASSTD_ROUND_CYCLE"));
                                    t.setTimeOut(rs.getInt("ASSTD_TIMEOUT"));
                                    t.setCalculateCycle(rs.getInt("ASSTD_CALCULATE_CYCLE"));
                                    t.setNoticeAccord(c.getCha_protocolCode());
                                    t.setLocation(rs.getString("ASSTD_BACKUPS"));
                                    t.setProduction(rs.getString("FRO_ID"));
                                    // t.setCode(null);
                                    return t;
                                }
                            });
            for (final TerminalDevice t : tList) {
                String sql = "select * from poms_calculate_terminal_device where CDT_TERMINAL_STATUS in(1,2) and cdt_assembleid = ?  ";
                if (DBConfigDao.dbType.equals("Microsoft SQL Server")) {
                    sql += "order by CAST(ctd_addr AS  INTEGER) ";
                } else {
                    sql += "order by CAST(ctd_addr AS SIGNED INTEGER) ";
                }
                List<MeterDevice> list = jdbcTemplate
                        .query(sql,
                                new Object[]{t.getPrimaryKey()},
                                new RowMapper() {
                                    @Override
                                    public MeterDevice mapRow(ResultSet rs,
                                                              int index) throws SQLException {
                                        MeterDevice p = new MeterDevice();
                                        p.setId(rs.getString("id"));
                                        p.setType(rs.getString("ctm_id"));
                                        p.setAddress(rs.getInt("CTD_ADDR"));
                                        p.setCode(rs.getString("CTD_CODES"));
                                        p.setPt(rs.getInt("CDT_FORMULARID"));
                                        p.setCt(rs.getInt("CDT_FORMULAR"));
                                        //p.setStatus(rs.getInt("cdt_terminal_status"));
                                        p.setCdt_assembleid(rs.getString("cdt_assembleid"));
                                        p.setIncrease(rs.getInt("ctd_increase"));
                                        p.setMeasure_loop(rs.getString("cdt_measure_loop"));
                                        p.setLinetype(rs.getInt("cdt_init_value"));
                                        p.setName(rs.getString("cdt_terminal_name"));
                                        p.setProduction(rs.getString("cdt_terminal_industry"));
                                        p.setProduction_code(rs.getString("cdt_production_code"));
                                        p.setPcpcEnergyType(rs.getString("PCLC_ENERGY_TYPE"));
                                        // 实时
                                        if (rs.getString("cdt_protocal_fun_code") != null
                                                && !rs
                                                .getString(
                                                        "cdt_protocal_fun_code")
                                                .isEmpty()) {
                                            String oldV = rs
                                                    .getString("CDT_PRODUCTION_CODE");
                                            p.setFnsOne(oldV);
                                        } else {

                                            p.setFnsOne(rs.getString("cdt_protocal_fun_code"));
                                        }
                                        // 曲线
                                        if (rs.getString("cdt_protocal_sec_fun_code") != null
                                                && !rs.getString(
                                                "cdt_protocal_sec_fun_code")
                                                .isEmpty()) {

                                            String oldV = rs
                                                    .getString("cdt_protocal_sec_fun_code");
                                            String newVal = combinationFN(oldV);
                                            p.setFnsTwo(newVal);
                                        } else {
                                            p.setFnsTwo(rs.getString("cdt_protocal_sec_fun_code"));
                                        }
                                        // 日冻结
                                        if (rs.getString("cdt_protocal_secday_fun_code") != null
                                                && !rs.getString("cdt_protocal_secday_fun_code")
                                                .isEmpty()) {
                                            String oldV = rs.getString("cdt_protocal_secday_fun_code");
                                            String newVal = combinationFN(oldV);
                                            p.setDaysFreezingFunCode(newVal);
                                        } else {
                                            p.setDaysFreezingFunCode(rs.getString("cdt_protocal_secday_fun_code"));
                                        }
                                        // 月冻结
                                        if (rs.getString("cdt_protocal_secmon_fun_code") != null
                                                && !rs.getString(
                                                "cdt_protocal_secmon_fun_code")
                                                .isEmpty()) {
                                            String oldV = rs.getString("cdt_protocal_secmon_fun_code");
                                            String newVal = combinationFN(oldV);
                                            p.setMonthssFreezingFunCode(newVal);
                                        } else {
                                            p.setMonthssFreezingFunCode(rs
                                                    .getString("cdt_protocal_secmon_fun_code"));
                                        }
                                        return p;
                                    }
                                });

                for (final MeterDevice p : list) {
                    // final String ctd_id=p.getId();
                    sql = "select * from poms_modle_measur_point where ctm_id = ? and mmp_isuse=1 order by MMP_STORAGE_TYPE,";
                    if (DBConfigDao.dbType.equals("Microsoft SQL Server")) {
                        sql += "cast(MOD_ADDRESS as  integer) ";
                    } else {
                        sql += "cast(MOD_ADDRESS as signed integer) ";
                    }
                    sql += ",mmp_type";
                    List<PointDevice> ls = jdbcTemplate
                            .query(
                                    sql,
                                    new Object[]{p.getType()},
                                    new RowMapper() {
                                        @Override
                                        public PointDevice mapRow(ResultSet rs,
                                                                  int index) throws SQLException {
                                            PointDevice pd = new PointDevice();
                                            pd.setCtdCode(p.getCode());
                                            pd.setCtdName(p.getName());
                                            pd.setId(rs.getString("id"));
                                            pd.setCode(rs.getString("mmp_codes"));
                                            pd.setName(rs.getString("mmp_names"));
                                            pd.setStorageType(rs.getInt("MMP_STORAGE_TYPE"));
                                            pd.setDbIndex(rs.getInt("DB_INDEX"));
                                            pd.setModAddress(rs.getBigDecimal("mod_address"));
                                            pd.setPointLen(rs.getInt("mmp_length"));
                                            pd.setModWFunction(rs.getInt("MMP_W_FUNCTION"));
                                            pd.setModWAddress(rs.getBigDecimal("MOD_WADDRESS"));
                                            pd.setModWlength(rs.getInt("MMP_WLENGTH"));
                                            pd.setModWType(rs.getInt("MMP_W_TYPE"));
                                            pd.setModWFormular(rs.getString("MMP_W_FORMULAR"));
                                            pd.setUnits(rs .getString("mmp_units"));
                                            pd.setIsPlcAddress(rs.getInt("mmp_isplc"));
                                            pd.setRwType(rs.getInt("MMP_RWTYPE"));
                                            pd.setIsBit(rs.getInt("MMP_ISBIT"));
                                            pd.setIssave(rs.getInt("MMP_ISSAVE"));
                                            pd.setIsCalculate(rs.getInt("mmp_iscalculate"));
                                            pd.setFormular(rs .getString("mmp_formular"));
                                            pd.setOrders(rs.getInt("MMP_ORDER"));
                                            pd.setIsCt(rs.getInt("MMP_ISCT"));
                                            pd.setIsPt(rs.getInt("MMP_ISPT"));
                                            pd.setUp_line(rs.getDouble("mmp_up_value"));
                                            pd.setDown_line(rs.getDouble("mmp_down_value"));
                                            pd.setMmpIsAlarm(rs.getInt("MMP_IS_ALARM"));
                                            if (pd.getIsCalculate() == 1) {
                                                BigDecimal lastValue = getLastValue(
                                                        p.getCode(),
                                                        pd.getCode());
                                                if (lastValue != null && lastValue.compareTo(BigDecimal.ZERO) != 0) {
                                                    pd.setLastPointValue(lastValue);
                                                    pd.setValue(lastValue);
                                                    pd.setShowValue(lastValue + "");
                                                }
                                            }
                                            pd.setIsCollect(1);
                                            pd.setCtmType(p.getPcpcEnergyType());
                                            pd.setMmpType(rs.getInt("mmp_type"));
                                            pd.setSystemCode(rs.getString("MMP_BACKUPS"));
                                            return pd;
                                        }
                                    });
                    sql = "select * from poms_device_measur_point where device_id = ?  order by MMP_STORAGE_TYPE,";
                    if (DBConfigDao.dbType.equals("Microsoft SQL Server")) {
                        sql += "cast(MOD_ADDRESS as  integer)";
                    } else {
                        sql += "cast(MOD_ADDRESS as signed integer)";
                    }
                    sql += ",mmp_type";
                    List<PointDevice> devicePoints = jdbcTemplate
                            .query(
                                    sql,
                                    new Object[]{p.getCode()},
                                    new RowMapper() {
                                        @Override
                                        public PointDevice mapRow(ResultSet rs,
                                                                  int index) throws SQLException {
                                            PointDevice pd = new PointDevice();
                                            pd.setId(rs.getString("id"));
                                            pd.setStorageType(rs.getInt("MMP_STORAGE_TYPE"));
                                            pd.setModWFunction(rs.getInt("MMP_W_FUNCTION"));
                                            pd.setModWAddress(rs.getBigDecimal("MOD_WADDRESS"));
                                            pd.setModWlength(rs.getInt("MMP_WLENGTH"));
                                            pd.setModWType(rs.getInt("MMP_W_TYPE"));
                                            pd.setModWFormular(rs.getString("MMP_W_FORMULAR"));
                                            pd.setName(rs
                                                    .getString("mmp_names"));
                                            pd.setIsCalculate(rs.getInt("mmp_iscalculate"));
                                            pd.setCtdCode(p.getCode());
                                            pd.setUnits(rs
                                                    .getString("mmp_units"));
                                            pd.setCode(rs
                                                    .getString("mmp_codes"));
                                            pd.setIsPlcAddress(rs.getInt("mmp_isplc"));
                                            pd.setIsBit(rs.getInt("MMP_ISBIT"));
                                            pd.setRwType(rs.getInt("MMP_RWTYPE"));
                                            pd.setModAddress(rs
                                                    .getBigDecimal("mod_address"));
                                            pd.setPointLen(rs
                                                    .getInt("mmp_length"));
                                            pd.setFormular(rs
                                                    .getString("mmp_formular"));
                                            pd.setIsCt(rs.getInt("MMP_ISCT"));
                                            pd.setIsPt(rs.getInt("MMP_ISPT"));
                                            pd.setIssave(rs
                                                    .getInt("MMP_ISSAVE"));
                                            pd.setUp_line(rs.getDouble("mmp_up_value"));
                                            pd.setDown_line(rs.getDouble("mmp_down_value"));
                                            pd.setMmpIsAlarm(rs.getInt("MMP_IS_ALARM"));
                                            pd.setIsCollect(rs.getInt("MMP_ISCOLLECT"));
                                            pd.setShowValue(rs.getString("MMP_SET_VALUE"));
                                            pd.setCtmType(p.getPcpcEnergyType());
                                            pd.setMmpType(rs.getInt("mmp_type"));
                                            // pd.setUp_line(rs.getBigDecimal("ctdp_up_line"));
                                            // pd.setDown_line(rs.getBigDecimal("ctdp_down_line"));
                                            // pd.setStandard_val(rs.getBigDecimal("ctdp_standard_val"));
                                            return pd;
                                        }
                                    });
                    ls.addAll(devicePoints);
                    p.setPointDevice(ls);
                }
                t.setMeterList(list);
            }
            c.setTerminalList(tList);
            terminalList.addAll(tList);
            //terminalList=tList;
        }

        terminalInfo = terminalList;
    }

    public static BussinessConfig getConfig() {
        return config;
    }

    public static final boolean MSAExist(int id) {
        boolean result = false;
        for (TerminalDevice termial : terminalList) {
            if (Integer.parseInt(termial.getLocation()) == id) {
                return true;
            }
        }
        return result;
    }

    public static final boolean PnExist(int MSAid, String id) {
        boolean result = false;
        for (TerminalDevice termial : terminalList) {
            if (termial.getId() != MSAid) {
                continue;
            } else {
                for (MeterDevice md : termial.getMeterList()) {
                    if (md.getId().equals(id)) {
                        return true;
                    }
                }
            }
        }

        return result;
    }

    public static final String getLogIOName(String IP) {
        String name = null;
        for (TerminalDevice t : terminalList) {
            if (t.getMSA().equals(IP)) {
                name = "CycleLog\\" + IP + ServerContext.getLogTail();
                break;
            }
        }
        return name;
    }

    public static final TerminalDevice getTerminalByIP(int id) throws Exception {
        for (TerminalDevice terminal : terminalList) {
            if (id == terminal.getId()) {
                return terminal;
            }
        }
        throw new Exception("终端地址不存在 id为" + id + "的终端");
    }

    /**
     * @param terminalID
     * @return
     */
    public static int getPortNumber(int terminalID) {
        int number = 0;
        for (TerminalDevice t : terminalList) {
            if (t.getId() == terminalID) {
                number = t.getMeterList().size();
                break;
            }
        }

        return number;
    }

    public static List<MeterDevice> getPortListByTerminalID(int terminalID) {
        for (TerminalDevice t : terminalList) {
            if (t.getId() == terminalID) {
                return t.getMeterList();
            }
        }
        return null;
    }

    /**
     * 将F转换0
     */
    public static String combinationFN(String oldV) {
        // String oldV=rs.getString("cdt_protocal_sec_fun_code");
        StringBuffer sbuffer = new StringBuffer();
        if (oldV.contains(",")) {
            String[] arrCode = oldV.split(",");
            String newVal = null;
            for (String str : arrCode) {

                String val = str.substring(1, str.length());
                if (val.length() == 2) {
                    newVal = "00" + val;
                } else if (val.length() == 3) {
                    newVal = "0" + val;
                } else if (val.length() == 1) {
                    newVal = "000" + val;
                }
                if (arrCode[arrCode.length - 1] == str) {
                    sbuffer.append(newVal);

                } else {

                    sbuffer.append(newVal + ",");
                }
            }
        } else {
            String val = oldV.substring(1, oldV.length());
            String newVal = null;
            if (val.length() == 2) {
                newVal = "00" + val;
            } else if (val.length() == 3) {
                newVal = "0" + val;
            } else if (val.length() == 1) {
                newVal = "000" + val;
            }
            sbuffer.append(newVal);
        }
        return sbuffer.toString();
    }

    public static final TerminalDevice getTerminalByIP(String ip) {
        if (ip.indexOf("/") > -1) {
            ip = ip.substring(ip.indexOf("/") + 1);
        }
//        if (ip.indexOf(":") > -1) {
//            ip = ip.substring(0, ip.indexOf(":"));
//        }
        TerminalDevice termianl = null;
        for (TerminalDevice td : terminalList) {
            if (td.getMSA().equals(ip)) {
                termianl = td;
                break;
            }
        }
        return termianl;
    }

    public static final TerminalDevice getTerminalByCode(String code)
            throws Exception {
        for (TerminalDevice termianl : terminalList) {
            if (termianl.getCode().equals(code)) {
                return termianl;
            }
        }
        throw new Exception("不存在编码为" + code + "的采集器");
    }

    public static BigDecimal getLastValue(String meterCode, String mmpCode) {
        BigDecimal lastValue = null;
        for (EsmspSumMeasurOrganizationDay day : daylist) {
            if (day.getEusCode().equals(meterCode)
                    && day.getMmpCode().equals(mmpCode)) {
                lastValue = day.getLastValue();
                break;
            }
        }
        return lastValue;
    }

    public static String getIpPort(IoSession session) {
        SocketAddress client = session.getRemoteAddress();
        String port = client.toString().substring(client.toString().indexOf("/") + 1);
        return port;
    }

    public static MeterDevice getMeterByTerminalCodeAndCtdCode(String terminalCode, String ctdCode) {
        for (TerminalDevice t : BussinessConfig.config.terminalList) {
            if (t.getCode().equals(terminalCode)) {
                for (MeterDevice meter : t.getMeterList()) {
                    if (meter.getCode().equals(ctdCode)) {
                        return meter;
                    }
                }
            }
        }
        return null;
    }

    public static MeterDevice getPointsByTerCodeAndMeterCode(String terminalCode, String ctdCode) {
        MeterDevice meter = null;
        for (TerminalDevice td : terminalList) {
            if (td.getCode().equals(terminalCode)) {
                for (MeterDevice md : td.getMeterList()) {
                    if (md.getCode().equals(ctdCode)) {
                        meter = md;
                        break;
                    }
                }
                break;
            }
        }
        return meter;
    }

    public static MeterDevice getMeterByTerIpAndAddress(String ip, int address) {
        TerminalDevice t = BussinessConfig.getTerminalByIP(ip);
        for (MeterDevice meter : t.getMeterList()) {
            if (meter.getAddress() == address) {
                return meter;
            }
        }
        return null;
    }

    public static TerminalDevice getTerminalByMSA(String msa) {
        for (TerminalDevice td : terminalList) {
            if (td.getMSA().equals(msa)) {
                return td;
            }
        }
        return null;
    }

    public static TerminalDevice getTerminalByIpOrPort(String ip, String port) {
        for (TerminalDevice td : terminalList) {
            if (td.getMSA().indexOf(".") > -1) {//IP地址 即内网  通过IP地址识别
                int index = td.getMSA().indexOf(":");
                if (index > -1) {
                    if (td.getMSA().substring(0, index).equals(ip)) {
                        return td;
                    }
                } else {
                    if (td.getMSA().equals(ip)) {
                        return td;
                    }
                }
            } else {//端口号
                if (td.getMSA().equals(port)) {
                    return td;
                }
            }
        }
        return null;
    }
}
