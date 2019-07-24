/**
 *
 */
package com.cic.pas.common.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import com.cic.pas.common.bean.MeterDevice;
import com.cic.pas.common.bean.PointData;
import com.cic.pas.common.bean.PointDevice;
import com.cic.pas.common.bean.TerminalDevice;
import com.cic.pas.dao.BussinessConfig;
import com.cic.pas.dao.DBConfigDao;

/**
 * @author Administrator
 */
public class Util {
    private static final Logger logger = Logger.getLogger(Util.class);

    public static List<Object[]> list = new ArrayList<Object[]>();

    public static Map<String, PointData> pointMap = new HashMap<String, PointData>();

    public final static String euo_code = "bcjt";

    public static String mmp_code = "";

    public static String modString = "";

    public static boolean isinsert = false;

    public static String insertDate;

    public static final int bytesToInt(byte[] bytes, int begin, int end) {
        int size = end - begin;
        int value = 0;
        try {
            switch (size) {
                case 1:
                    value = bytes[begin] & 0xFF;
                    break;
                case 2:
                    value = bytes[begin + 1] & 0xFF | (bytes[begin] & 0xFF) << 8;
                    break;
                case 3:
                    value = bytes[begin + 2] & 0xFF | (bytes[begin + 1] & 0xFF) << 8
                            | (bytes[begin + 0] & 0xFF) << 16;
                    break;
                case 4:
                    value = bytes[begin + 3] & 0xFF | (bytes[begin + 2] & 0xFF) << 8
                            | (bytes[begin + 1] & 0xFF) << 16
                            | (bytes[begin + 0] & 0xFF) << 24;
                    break;
            }
        } catch (Exception e) {
            logger.error(e.toString() + "\r\n" + bytes2Hex(bytes, 0, bytes.length) + "\r\n开始位置：" + begin + "\r\n结束位置:" + end);
        }
        return value;
    }

    /**
     * byte数组转hex字符串
     * 一个byte转为2个hex字符
     *
     * @param src
     * @return
     */
    public static String bytes2Hex(byte[] src, int begin, int end) {
        char[] res = new char[(end - begin) * 2];
        final char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8',
                '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        for (int i = begin, j = 0; i < end; i++) {
            res[j++] = hexDigits[src[i] >>> 4 & 0x0f];
            res[j++] = hexDigits[src[i] & 0x0f];
        }
        return new String(res);
    }

    /**
     * 将字节转换2进制字符串
     */
    public static final String byteToBinary(byte b) {
        StringBuffer sb = new StringBuffer();
        for (int i = 7; i >= 0; i--) {
            sb.append(((b & (1 << i)) != 0) ? '1' : '0');
        }
        return sb.toString();
    }

    /**
     * 将一个字节转换成16进制的字符串
     */
    public static final String toHex(byte b) {
        return ("" + "0123456789ABCDEF".charAt(0xf & b >> 4) + "0123456789ABCDEF"
                .charAt(b & 0xf));
    }

    /**
     * 十进制字符串转化为16进制字符串
     *
     * @param s
     * @return
     */
    public static final String strToHexStr(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str + s4;
        }
        return str;
    }

    /**
     * 位数不足补位
     *
     * @param input
     * @param size
     * @param symbol
     * @return
     */
    public static final String fill(String input, int size, char symbol) {

        while (input.length() < size) {
            input = symbol + input;
        }
        return input;
    }

    /**
     * 将一个16进制的字符串数据转换成字节数组
     */
    public static final byte[] convertString(String data) throws Exception {
        int size = data.length() / 2;
        byte[] bArray = new byte[size];
        for (int i = 0; i < size; i++) {
            bArray[i] = (byte) (0xff & Integer.parseInt(data.substring(i * 2,
                    i * 2 + 2), 16));
        }
        return bArray;
    }

    /**
     * 将一个2进制的字符串转换成一个10进制数
     */
    public static final int binaryToTen(String binStr) {
        return Integer.valueOf(binStr, 2);
    }

    /**
     * 将字节的2进制表示形式按位相加
     */
    public static final String CS(byte[] bytes) {
        byte b = 0x00;
        for (int i = 0; i < bytes.length; i++) {
            b = (byte) (b ^ bytes[i]);
        }
        return byteToBinary(b);
    }

    /**
     * 将一个Int转为BCD字符串(或16进制)
     */
    public static final String BCDString(int src) {
        String des = null;
        switch (src) {
            case 0:
                des = "0000";
                break;
            case 1:
                des = "0001";
                break;
            case 2:
                des = "0010";
                break;
            case 3:
                des = "0011";
                break;
            case 4:
                des = "0100";
                break;
            case 5:
                des = "0101";
                break;
            case 6:
                des = "0110";
                break;
            case 7:
                des = "0111";
                break;
            case 8:
                des = "1000";
                break;
            case 9:
                des = "1001";
                break;
        }
        return des;
    }

    /**
     * 2进制字符串转为16进制字符串
     */
    public final static String binToHex(String hex) {
        StringBuffer sBuffer = new StringBuffer();
        String pre = hex.substring(0, 4);
        String tail = hex.substring(4, 8);
        if (pre.equals("0000")) {
            sBuffer.append("0");
        } else if (pre.equals("0001")) {
            sBuffer.append("1");
        } else if (pre.equals("0010")) {
            sBuffer.append("2");
        } else if (pre.equals("0011")) {
            sBuffer.append("3");
        } else if (pre.equals("0100")) {
            sBuffer.append("4");
        } else if (pre.equals("0101")) {
            sBuffer.append("5");
        } else if (pre.equals("0110")) {
            sBuffer.append("6");
        } else if (pre.equals("0111")) {
            sBuffer.append("7");
        } else if (pre.equals("1000")) {
            sBuffer.append("8");
        } else if (pre.equals("1001")) {
            sBuffer.append("9");
        } else if (pre.equals("1010")) {
            sBuffer.append("A");
        } else if (pre.equals("1011")) {
            sBuffer.append("B");
        } else if (pre.equals("1100")) {
            sBuffer.append("C");
        } else if (pre.equals("1101")) {
            sBuffer.append("D");
        } else if (pre.equals("1110")) {
            sBuffer.append("E");
        } else if (pre.equals("1111")) {
            sBuffer.append("F");
        }

        if (tail.equals("0000")) {
            sBuffer.append("0");
        } else if (tail.equals("0001")) {
            sBuffer.append("1");
        } else if (tail.equals("0010")) {
            sBuffer.append("2");
        } else if (tail.equals("0011")) {
            sBuffer.append("3");
        } else if (tail.equals("0100")) {
            sBuffer.append("4");
        } else if (tail.equals("0101")) {
            sBuffer.append("5");
        } else if (tail.equals("0110")) {
            sBuffer.append("6");
        } else if (tail.equals("0111")) {
            sBuffer.append("7");
        } else if (tail.equals("1000")) {
            sBuffer.append("8");
        } else if (tail.equals("1001")) {
            sBuffer.append("9");
        } else if (tail.equals("1010")) {
            sBuffer.append("A");
        } else if (tail.equals("1011")) {
            sBuffer.append("B");
        } else if (tail.equals("1100")) {
            sBuffer.append("C");
        } else if (tail.equals("1101")) {
            sBuffer.append("D");
        } else if (tail.equals("1110")) {
            sBuffer.append("E");
        } else if (tail.equals("1111")) {
            sBuffer.append("F");
        }
        return sBuffer.toString();
    }

    /**
     * 2进制字符转成16进制
     */
    public static String binToHex1(String bin) {
        if (bin.equals("0000")) {
            return "0";
        } else if (bin.equals("0001")) {
            return "1";
        } else if (bin.equals("0001")) {
            return "1";
        } else if (bin.equals("0010")) {
            return "2";
        } else if (bin.equals("0011")) {
            return "3";
        } else if (bin.equals("0100")) {
            return "4";
        } else if (bin.equals("0101")) {
            return "5";
        } else if (bin.equals("0110")) {
            return "6";
        } else if (bin.equals("0111")) {
            return "7";
        } else if (bin.equals("1000")) {
            return "8";
        } else if (bin.equals("1001")) {
            return "9";
        } else if (bin.equals("1010")) {
            return "A";
        } else if (bin.equals("1011")) {
            return "B";
        } else if (bin.equals("1100")) {
            return "C";
        } else if (bin.equals("1101")) {
            return "D";
        } else if (bin.equals("1110")) {
            return "E";
        } else if (bin.equals("1111")) {
            return "F";
        }
        return null;
    }

    /**
     * @param code 16进制的字符串
     * @return byte[]
     * @throws Exception
     */
    public static byte[] getByte(String code) throws Exception {
        int length = code.length();
        //System.out.println("TCPTX:"+code);
        //System.out.println("OKok:" + length);
        if (length % 2 != 0) {
            throw new Exception("发送字符应该为偶数!" + code);
        }
        byte[] result = new byte[length / 2];
        for (int i = 0; i < length / 2; i++) {
            // result[i] = (byte) Integer.parseInt(code.substring(i*2,i*2+2),
            // 16);
            result[i] = (byte) Integer.parseInt(code
                    .substring(i * 2, i * 2 + 2), 16);

        }
        return result;
    }

    /**
     * 将一个字符串之间的空格去掉
     */
    public static String cancelSpace(String srcString) {
        StringBuffer result = new StringBuffer();
        String[] str = srcString.split(" ");
        for (int i = 0; i < str.length; i++) {
            result.append(str[i]);
        }
        return result.toString();
    }

    /**
     * 校验求和 所有8位 位组的算数和 不考虑溢出位
     *
     * @param bArray 输入位组
     * @return
     */
    public static byte CSExt(byte[] bArray) {
        byte result = 0x00;
        for (int i = 0; i < bArray.length; i++) {
            result += bArray[i];
        }
        return result;
    }

    /**
     * 存储测点
     *
     * @param terminal_id 采集终端编号
     * @param meterNumber 表终端编号
     * @param value       测点值
     * @return
     * @parampointNumber 测点编号
     */
    public static final boolean saveThePoint(int terminal_id, int meterNumber,
                                             String code, String value, String date, int type) {
        boolean flag = false;
        for (TerminalDevice tl : BussinessConfig.config.terminalList) {
            if (tl.getId() == terminal_id) {
                for (MeterDevice port : tl.getMeterList()) {
                    if (port.getAddress() == meterNumber) {
                        for (PointDevice pd : port.getPointDevice()) {
                            if (pd.getCode().equals(code)) {
                                if (type == 1) {
                                    swap(pd);
                                    BigDecimal temp = new BigDecimal(parseDb(value));
                                    pd.setValue(temp);
                                    pd.setTime(date);
                                    flag = true;
                                    break;
                                } else if (type == 2) {
                                    logger.info("saveThePoint方法---"
                                            + terminal_id + ">" + meterNumber
                                            + ">" + code + ">" + value + ">"
                                            + date + ">" + type);
                                    Double temp = parseDb(value);
                                    pd.setDayValue(temp);
                                    pd.setDayTime(date);
                                    flag = true;
                                    break;
                                } else if (type == 3) {
                                    Double temp = parseDb(value);
                                    pd.setMonthValue(temp);
                                    pd.setMonthTime(date);
                                    flag = true;
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        return flag;
    }

    private static final Double parseDb(String str) {
        Double d = null;
        if (str != null && !str.equals("null")) {
            d = Double.parseDouble(str);
        }
        return d;
    }

    public static final boolean saveThePoint(int terminal_id, int meterNumber,
                                             String value) {
        boolean flag = false;
        for (TerminalDevice tl : BussinessConfig.config.terminalList) {
            if (tl.getId() == terminal_id) {
                for (MeterDevice port : tl.getMeterList()) {
                    if (port.getAddress() == meterNumber) {
                        port.setDenied_that_frame(value);
                        flag = true;
                        break;
                    }
                }
            }
        }
        return flag;
    }

    public static final boolean saveThePoint(int terminal_id, int meterNumber,
                                             String code, List<Object> value, String date) {
        boolean flag = false;
        for (TerminalDevice tl : BussinessConfig.config.terminalList) {
            if (tl.getId() == terminal_id) {
                for (MeterDevice port : tl.getMeterList()) {
                    if (port.getAddress() == meterNumber) {
                        for (PointDevice pd : port.getPointDevice()) {
                            if (pd.getCode().equalsIgnoreCase(code)) {

                                if (pd.getCurveData() != null) {
                                    pd.getCurveData().addAll(value);
                                } else {
                                    pd.setCurveData(value);
                                    pd.setTime(date);
                                }
                                flag = true;
                                break;
                            }
                        }
                    }
                }
            }
        }
        return flag;
    }

    private static final void swap(PointDevice pd) {
        BigDecimal temp = pd.getValue();
        pd.setValue(pd.getPreviousValue());
        pd.setPreviousValue(temp);
    }

    public void batchInsertRows(String sql, final List<Object[]> dataSet)
            throws Exception {
        BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter() {
            public int getBatchSize() {
                return dataSet.size();
            }

            public void setValues(PreparedStatement ps, int i) {

                Object[] obj = dataSet.get(i);
                try {
                    ps.setObject(1, (Integer) obj[0]);
                    ps.setObject(2, (Integer) obj[1]);
                    ps.setObject(3, (Integer) obj[2]);
                    ps.setObject(4, obj[3]);
                    ps.setTimestamp(5, (Timestamp) obj[4]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        DBConfigDao.jdbcTemplate.batchUpdate(sql, setter);
    }

    public static final Timestamp getDate(String year, String month)
            throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Date date = sdf.parse(year + "-" + month);
        Timestamp time = new Timestamp(date.getTime());
        return time;
    }

    public static final Timestamp getDate(String year) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date = sdf.parse(year);
        Timestamp time = new Timestamp(date.getTime());
        return time;
    }

    // 或驱主键
    public static final String getKey() {
        UUID uuid = UUID.randomUUID();
        if (null == uuid || "".equals(uuid.toString())) {
            throw new NullPointerException("uuid is null");
        }
        String primaryKey = String.valueOf(uuid);

        if (null != primaryKey && primaryKey.contains("-")) {
            primaryKey = primaryKey.replaceAll("-", "");
        }
        return primaryKey;
    }

    public static final void cleanBuffer(Object[] obj) {
        list.add(obj);
        if (list.size() == 5) {
            int len = list.size();
            int separate = len / 3;
            List clearList = new ArrayList();
            for (int i = 0; i < separate; i++) {
                clearList.add(list.get(i));
            }
            list.removeAll(clearList);
        }
    }

    public static String hexpadleft(int a) {
        String s = Integer.toHexString(a);
        if (s.length() < 2) {
            s = "0" + s;
        }
        return s;
    }

    /**
     * 通过公式得到结果
     *
     * @param
     * @param
     * @return结果
     */
    public static BigDecimal manageData(BigDecimal value,
                                        String formulae) {
        if (formulae.equals("") || formulae == null) {
            return value;
        } else {
            ScriptEngine se = new ScriptEngineManager()
                    .getEngineByName("JavaScript");
            String str = value + formulae;
            BigDecimal res = new BigDecimal(0);
            try {
                res = new BigDecimal(se.eval(str).toString()).setScale(2, BigDecimal.ROUND_HALF_UP);
            } catch (Exception e) {
                // TODO: handle exception
                System.out.println(e);
            }
            return res;
        }
    }

    public static BigDecimal reManageData(BigDecimal value, String format) {
        format = format.replaceAll("/", "*");
        format = format.replaceAll("\\*", "/");
        format = format.replaceAll("\\+", "-");
        format = format.replaceAll("-", "+");
        return manageData(value, format);
    }

    /**
     * 通过公式得到结果
     *
     * @param原value值
     * @param反向formulae算法
     * @return结果 计算过后的值
     */
    public static String decManageData(double value,
                                       String formulae) {
        DecimalFormat format = new DecimalFormat("0.00");
        if (formulae.equals("") || formulae == null) {
            return Integer.toHexString((int) value);
        } else if (formulae.equals("FLOAT")) {
            //return HEXToIEEE.getFloat(hexValue);
            return "";
        } else if (formulae.equals("REAL")) {
            //return HEXToIEEE.getFloat(hexValue);
            return "";
        } else if (formulae.indexOf(",") > 0) {
            return Integer.toHexString((int) value);
        } else {
            ScriptEngine se = new ScriptEngineManager()
                    .getEngineByName("JavaScript");
            formulae = formulae.replaceAll("/", "*");
            //formulae=formulae.replaceAll("*", "/");
            String str = value + formulae;
            double res = value;
            try {
                res = Double.parseDouble(se.eval(str).toString());
                res = Double.parseDouble(format.format(res));
            } catch (Exception e) {
                // TODO: handle exception
                System.out.println(e);
            }
            String result = Integer.toHexString((int) res);
            return result;
        }
    }

    /*
     * public synchronized static final void sentUDPMessage(String
     * sendStr)throws Exception{ byte[] sendBuf; sendBuf = sendStr.getBytes();
     * InetAddress addr = InetAddress.getByName("127.0.0.1"); int port = 5050;
     * DatagramPacket sendPacket = new DatagramPacket(sendBuf ,sendBuf.length ,
     * addr , port); StandardServer.client.send(sendPacket);
     *
     * }
     */
    public static byte[] createPLCHexBytes(int address, int length)
            throws Exception {
        String hex = "0300001f02f08032010000ccc1000e00000401120a100200"
                + Integer.toHexString(length) + "00018400"
                + Integer.toHexString(address * 8);
        System.out.println("发送数据：" + hex);
        return getByte(hex);
    }

    public static String toTwoRight(String str) {
        int i = str.length();
        switch (i) {
            case 3:
                ;
                break;
            case 1:
                str = "00" + str;
                break;
            case 2:
                str = "0" + str;
                break;
            default:
                break;
        }
        return str;
    }

    public static int getFGP(int point) {// 3 谷 2 平 1 峰
        if (point <= 28) {// 0-7谷
            return 3;
        } else if (point > 29 && point <= 32) {// 7-8平
            return 2;
        } else if (point > 32 && point <= 44) {// 8-11峰
            return 1;
        } else if (point > 44 && point <= 72) {// 11-18平
            return 2;
        } else if (point > 72 && point <= 92) {// 18-23峰
            return 1;
        } else { // 23-24谷
            return 3;
        }
    }

    /**
     * @param num 十六进制有符号字符串
     * @return 10进制整数
     */
    public static short parseHex4(String num) {
        if (num.length() != 4) {
            throw new NumberFormatException("Wrong length: " + num.length() + ", must be 4.");
        }
        int ret = Integer.parseInt(num, 16);
        ret = ((ret & 0x8000) > 0) ? (ret - 0x10000) : (ret);
        return (short) ret;
    }

    /**
     * @param size     字符串长度（不含包头）
     * @param function 命令
     * @param count    读写寄存器个数
     * @param length   数据长度
     * @return 是否为合法消息
     */
    public static boolean checkReceiveHexStr(int size, int function, int count, int length) {
        if (count != 0) {
            if (function == 3) {
                if (size == 6 && count != 0 && length == 0) {
                    return true;
                } else {
                    return false;
                }
            } else if (function == 16) {
                if (count * 2 == length && size == length + 7) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }


    //Override
    public static Object bytesToValueRealOffset(byte[] data, int offset, int dataType) {
        // offset *= 2;

        // 2 bytes
        if (dataType == DataType.BINARY) {
            return data[data.length - 1 - offset] & 0xff;
        } else if (dataType == DataType.TWO_BYTE_INT_UNSIGNED) {
            return new Integer(((data[offset] & 0xff) << 8) | (data[offset + 1] & 0xff));
        } else if (dataType == DataType.TWO_BYTE_INT_SIGNED) {
            return new Short((short) (((data[offset] & 0xff) << 8) | (data[offset + 1] & 0xff)));
        } else if (dataType == DataType.TWO_BYTE_INT_UNSIGNED_SWAPPED) {
            return new Short((short) (((data[offset + 1] & 0xff) << 8) | (data[offset] & 0xff)));
        } else if (dataType == DataType.TWO_BYTE_BCD) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < 2; i++) {
                sb.append(bcdNibbleToInt(data[offset + i], true));
                sb.append(bcdNibbleToInt(data[offset + i], false));
            }
            return new Short(Short.parseShort(sb.toString()));
        } else if (dataType == DataType.FOUR_BYTE_INT_UNSIGNED) {
            return new Long(((long) ((data[offset] & 0xff)) << 24) | ((long) ((data[offset + 1] & 0xff)) << 16)
                    | ((long) ((data[offset + 2] & 0xff)) << 8) | ((data[offset + 3] & 0xff)));
        } else if (dataType == DataType.FOUR_BYTE_INT_SIGNED) {
            return new Integer(((data[offset] & 0xff) << 24) | ((data[offset + 1] & 0xff) << 16)
                    | ((data[offset + 2] & 0xff) << 8) | (data[offset + 3] & 0xff));
        } else if (dataType == DataType.FOUR_BYTE_INT_UNSIGNED_SWAPPED) {
            return new Long(((long) ((data[offset + 2] & 0xff)) << 24) | ((long) ((data[offset + 3] & 0xff)) << 16)
                    | ((long) ((data[offset] & 0xff)) << 8) | ((data[offset + 1] & 0xff)));
        } else if (dataType == DataType.FOUR_BYTE_INT_SIGNED_SWAPPED) {
            return new Integer(((data[offset + 2] & 0xff) << 24) | ((data[offset + 3] & 0xff) << 16)
                    | ((data[offset] & 0xff) << 8) | (data[offset + 1] & 0xff));
        } else if (dataType == DataType.FOUR_BYTE_FLOAT) {
            return new Float(Float.intBitsToFloat(((data[offset] & 0xff) << 24) | ((data[offset + 1] & 0xff) << 16)
                    | ((data[offset + 2] & 0xff) << 8) | (data[offset + 3] & 0xff)));
        } else if (dataType == DataType.FOUR_BYTE_FLOAT_SWAPPED) {
            return new Float(Float.intBitsToFloat(((data[offset + 2] & 0xff) << 24) | ((data[offset + 3] & 0xff) << 16)
                    | ((data[offset] & 0xff) << 8) | (data[offset + 1] & 0xff)));
        } else if (dataType == DataType.FOUR_BYTE_FLOAT_SWAPPED_ALL) {
            return new Float(Float.intBitsToFloat(((data[offset + 3] & 0xff) << 24) | ((data[offset + 2] & 0xff) << 16)
                    | ((data[offset + 1] & 0xff) << 8) | (data[offset] & 0xff)));
        } else if (dataType == DataType.FOUR_BYTE_BCD) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < 4; i++) {
                sb.append(bcdNibbleToInt(data[offset + i], true));
                sb.append(bcdNibbleToInt(data[offset + i], false));
            }
            return new Integer(Integer.parseInt(sb.toString()));
        } else if (dataType == DataType.EIGHT_BYTE_INT_UNSIGNED) {
            byte[] b9 = new byte[9];
            System.arraycopy(data, offset, b9, 1, 8);
            return new BigInteger(b9);
        } else if (dataType == DataType.EIGHT_BYTE_INT_SIGNED) {
            return new Long(((long) ((data[offset] & 0xff)) << 56) | ((long) ((data[offset + 1] & 0xff)) << 48)
                    | ((long) ((data[offset + 2] & 0xff)) << 40) | ((long) ((data[offset + 3] & 0xff)) << 32)
                    | ((long) ((data[offset + 4] & 0xff)) << 24) | ((long) ((data[offset + 5] & 0xff)) << 16)
                    | ((long) ((data[offset + 6] & 0xff)) << 8) | ((data[offset + 7] & 0xff)));
        } else if (dataType == DataType.EIGHT_BYTE_INT_UNSIGNED_SWAPPED) {
            byte[] b9 = new byte[9];
            b9[1] = data[offset + 6];
            b9[2] = data[offset + 7];
            b9[3] = data[offset + 4];
            b9[4] = data[offset + 5];
            b9[5] = data[offset + 2];
            b9[6] = data[offset + 3];
            b9[7] = data[offset];
            b9[8] = data[offset + 1];
            return new BigInteger(b9);
        } else if (dataType == DataType.EIGHT_BYTE_INT_SIGNED_SWAPPED) {
            return new Long(((long) ((data[offset + 6] & 0xff)) << 56) | ((long) ((data[offset + 7] & 0xff)) << 48)
                    | ((long) ((data[offset + 4] & 0xff)) << 40) | ((long) ((data[offset + 5] & 0xff)) << 32)
                    | ((long) ((data[offset + 2] & 0xff)) << 24) | ((long) ((data[offset + 3] & 0xff)) << 16)
                    | ((long) ((data[offset] & 0xff)) << 8) | ((data[offset + 1] & 0xff)));
        } else if (dataType == DataType.EIGHT_BYTE_FLOAT) {
            return new Double(Double.longBitsToDouble(((long) ((data[offset] & 0xff)) << 56)
                    | ((long) ((data[offset + 1] & 0xff)) << 48) | ((long) ((data[offset + 2] & 0xff)) << 40)
                    | ((long) ((data[offset + 3] & 0xff)) << 32) | ((long) ((data[offset + 4] & 0xff)) << 24)
                    | ((long) ((data[offset + 5] & 0xff)) << 16) | ((long) ((data[offset + 6] & 0xff)) << 8)
                    | ((data[offset + 7] & 0xff))));
        } else if (dataType == DataType.EIGHT_BYTE_FLOAT_SWAPPED) {
            return new Double(Double.longBitsToDouble(((long) ((data[offset + 6] & 0xff)) << 56)
                    | ((long) ((data[offset + 7] & 0xff)) << 48) | ((long) ((data[offset + 4] & 0xff)) << 40)
                    | ((long) ((data[offset + 5] & 0xff)) << 32) | ((long) ((data[offset + 2] & 0xff)) << 24)
                    | ((long) ((data[offset + 3] & 0xff)) << 16) | ((long) ((data[offset] & 0xff)) << 8)
                    | ((data[offset + 1] & 0xff))));
        } else if (dataType == DataType.EIGHT_BYTE_FLOAT_SWAPPED_ALL) {
            float h = new Float(Float.intBitsToFloat(((data[offset + 3] & 0xff) << 24) | ((data[offset + 2] & 0xff) << 16)
                    | ((data[offset + 1] & 0xff) << 8) | (data[offset] & 0xff)));
            float l = new Float(Float.intBitsToFloat(((data[offset + 7] & 0xff) << 24) | ((data[offset + 6] & 0xff) << 16)
                    | ((data[offset + 5] & 0xff) << 8) | (data[4] & 0xff)));
            return h * 10000 + l;
        } else if (dataType == DataType.EIGHT_BYTE_FLOAT_SWAPPED_ALL_HIGHT_65536) {
            float h = new Float(Float.intBitsToFloat(((data[offset + 3] & 0xff) << 24) | ((data[offset + 2] & 0xff) << 16)
                    | ((data[offset + 1] & 0xff) << 8) | (data[offset] & 0xff)));
            float l = new Float(Float.intBitsToFloat(((data[offset + 7] & 0xff) << 24) | ((data[offset + 6] & 0xff) << 16)
                    | ((data[offset + 5] & 0xff) << 8) | (data[4] & 0xff)));
            return h * 65536 + l;
        } else if (dataType == DataType.SIX_BYTE_DATETIME) {
            int hour = new Integer(((data[offset] & 0xff) << 8) | (data[offset + 1] & 0xff));
            int min = new Integer(((data[offset + 2] & 0xff) << 8) | (data[offset + 3] & 0xff));
            int sec = new Integer(((data[offset + 4] & 0xff) << 8) | (data[offset + 5] & 0xff));
            return hour + StringUtils.leftPad(min + "", 2, '0') + StringUtils.leftPad(sec + "", 2, '0');

        } else if (dataType == DataType.TWO_BYTE_INT_UNSIGNED_240_15) {
            int a = new Integer(((data[offset] & 0xff) << 8) | (data[offset + 1] & 0xff));
            if (a == 15) {
                return 1;
            } else {
                return 0;
            }
        }else if(dataType==DataType.SIX_BYTE_LONG){
            Long high= new Long(((long) ((data[offset] & 0xff)) << 24) | ((long) ((data[offset + 1] & 0xff)) << 16)
                    | ((long) ((data[offset + 2] & 0xff)) << 8) | ((data[offset + 3] & 0xff)));
            Integer low=new Integer(((data[offset] & 0xff) << 8) | (data[offset + 1] & 0xff));
            return (high+low/1000);

        }

        throw new RuntimeException("Unsupported data type: " + dataType);
    }

    public static short[] RealValueToShorts(Double value, int dataType, int length) {
        short[] values = new short[length];
        switch (dataType) {
            case DataType.BINARY:
            case DataType.TWO_BYTE_INT_UNSIGNED:
            case DataType.TWO_BYTE_INT_SIGNED:
                values[0] = value.shortValue();
                break;
            case DataType.FOUR_BYTE_INT_SIGNED:
            case DataType.FOUR_BYTE_INT_UNSIGNED:
                int re = value.intValue();
                values[0] = (short) (re / 65536);
                values[1] = (short) (re % 65536);
                break;
            case DataType.FOUR_BYTE_FLOAT:
                re = Float.floatToIntBits(value.floatValue());
                values[0] = (short) (re / 65536);
                values[1] = (short) (re % 65536);
                break;
            case DataType.TWO_BYTE_INT_UNSIGNED_SWAPPED:
                short intValue = value.shortValue();
                byte[] bytes = new byte[2];
                bytes[0] = (byte) (intValue >> 8);
                bytes[1] = (byte) (intValue >> 0);
                values[0] = (short) (((bytes[1] << 8) | bytes[0] & 0xff));
                break;
            case DataType.SIX_BYTE_DATETIME:
                String str = value.intValue() + "";
                str = StringUtils.leftPad(str, 6, '0');
                values[2] = Short.parseShort(str.substring(str.length() - 2, str.length()));
                values[1] = Short.parseShort(str.substring(str.length() - 4, str.length() - 2));
                values[0] = Short.parseShort(str.substring(0, str.length() - 4));
                break;
            case DataType.TWO_BYTE_INT_UNSIGNED_240_15:
                short a = value.shortValue();
                if (a == 1) {
                    values[0] = 15;
                } else {
                    values[0] = 240;
                }
        }

        return values;
    }

    private static int bcdNibbleToInt(byte b, boolean high) {
        int n;
        if (high)
            n = (b >> 4) & 0xf;
        else
            n = b & 0xf;
        if (n > 9)
            n = 0;
        return n;
    }

    /***
     * 截取数组
     * @param bytes 原数组
     * @param begin 开始位置
     * @param length 长度
     * @return 新数组
     */
    public static byte[] bytesSub(byte[] bytes, int begin, int length) {
        byte[] result = new byte[length];
        System.arraycopy(bytes, begin, result, 0, length);
        return result;
    }

    public static BigDecimal bytesHexStrToBIgDecimal(byte[] src, int begin, int length) {
        byte[] bytes = bytesSub(src, begin, length);
        String hex = CRC16M.bytes2Hex(bytes);
        StringBuffer buffer = new StringBuffer();
        buffer.append(com.cic.pas.common.util.StringUtils.toSwapStr(hex));
        buffer.insert(buffer.length() - 4, ".");
        return new BigDecimal(buffer.toString());
    }

    /**
     * 二进制字符串转换为byte数组
     **/
    public static byte[] binStrToByteArr(String binStr) {
        byte[] b = new byte[binStr.length()];
        for (int i = 0; i < b.length; i++) {
            b[i] = Long.valueOf(binStr.substring(i, i + 1), 2).byteValue();
        }
        return b;
    }

    public static void main(String[] args) {
        //byte[] bytes=bytesSub(src,begin,length);
//        int i = 23;
//        int high=(int)((i-1)/8)+1;
//        int ab=i%8;
//        if(ab==0){
//            ab=8;
//        }
//        int low=(int)Math.pow(2,ab-1);
////        System.out.println(high+":"+low);
//
//        int high=2;
//        int ab=7;
//        int low=(int)Math.pow(2,ab);
//        int resultHigh=(high-1)*8;
//        int resultLow=(int)(Math.log(low)/Math.log(2))+1;
//        System.out.println(resultHigh+resultLow);
    }

}
