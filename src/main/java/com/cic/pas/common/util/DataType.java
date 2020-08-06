/*
 * ============================================================================
 * GNU General Public License
 * ============================================================================
 *
 * Copyright (C) 2006-2011 Serotonin Software Technologies Inc. http://serotoninsoftware.com
 * @author Matthew Lohbihler
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.cic.pas.common.util;

import org.apache.commons.lang.StringUtils;

import java.math.BigInteger;

/**
 * @author Matthew Lohbihler
 */
public class DataType {
    public static final int BINARY = 1;//位

    public static final int TWO_BYTE_INT_UNSIGNED = 2;//无符号短整型
    public static final int TWO_BYTE_INT_UNSIGNED_SWAPPED = 22;//无符号短整型反转
    public static final int TWO_BYTE_INT_UNSIGNED_SWAPPED_NOT = 30;//无符号短整型取非
    public static final int TWO_BYTE_INT_SIGNED = 3;//有符号短整型
    public static final int FOUR_BYTE_INT_UNSIGNED = 4;//无符号长整型
    public static final int FOUR_BYTE_INT_SIGNED = 5;//有符号长整型
    public static final int FOUR_BYTE_INT_UNSIGNED_SWAPPED = 6;//低位在前无符号长整型
    public static final int FOUR_BYTE_INT_SIGNED_SWAPPED = 7;//低位在前有符号长整型
    public static final int FOUR_BYTE_FLOAT = 8;//单精度浮点数
    public static final int FOUR_BYTE_FLOAT_SWAPPED = 9;//单精度浮点数 低位在前
    public static final int FOUR_BYTE_FLOAT_SWAPPED_ALL = 20;//单精度浮点数 全部反转 ll_lh_hl_hh
    public static final int EIGHT_BYTE_INT_UNSIGNED = 10;
    public static final int EIGHT_BYTE_INT_SIGNED = 11;
    public static final int EIGHT_BYTE_INT_UNSIGNED_SWAPPED = 12;
    public static final int EIGHT_BYTE_INT_SIGNED_SWAPPED = 13;
    public static final int EIGHT_BYTE_FLOAT = 14;
    public static final int EIGHT_BYTE_FLOAT_SWAPPED = 15;
    public static final int EIGHT_BYTE_FLOAT_SWAPPED_ALL = 21;//浮点数  Hll_Hlh_Hhl_Hhh Lll_Llh_Lhl_Lhh 高位*10000
    public static final int EIGHT_BYTE_FLOAT_SWAPPED_ALL_HIGHT_65536=23;
    public static final int TWO_BYTE_BCD = 16;//短BCD
    public static final int FOUR_BYTE_BCD = 17;//长BCD
    public static final int FIVE_BYTE_BCD_SWAPPED_ALL = 27;//长BCD
    public static final int SIX_BYTE_DATETIME = 25;//6字节时间
    public static final int TWO_BYTE_INT_UNSIGNED_240_15=26;// 15转换为1  240转为0
    public static final int CHAR = 18;//单字
    public static final int VARCHAR = 19;//双字
    public static final int SIX_BYTE_LONG=28;
    public static final int ONE_BYTE=29;//单字节

    public static int getRegisterCount(int id) {
        switch (id) {
            case BINARY:
            case TWO_BYTE_INT_UNSIGNED:
            case TWO_BYTE_INT_SIGNED:
            case TWO_BYTE_BCD:
            case TWO_BYTE_INT_UNSIGNED_SWAPPED:
                return 1;
            case FOUR_BYTE_INT_UNSIGNED:
            case FOUR_BYTE_INT_SIGNED:
            case FOUR_BYTE_INT_UNSIGNED_SWAPPED:
            case FOUR_BYTE_INT_SIGNED_SWAPPED:
            case FOUR_BYTE_FLOAT:
            case FOUR_BYTE_FLOAT_SWAPPED:
            case FOUR_BYTE_FLOAT_SWAPPED_ALL:
            case FOUR_BYTE_BCD:
                return 2;
            case EIGHT_BYTE_INT_UNSIGNED:
            case EIGHT_BYTE_INT_SIGNED:
            case EIGHT_BYTE_INT_UNSIGNED_SWAPPED:
            case EIGHT_BYTE_INT_SIGNED_SWAPPED:
            case EIGHT_BYTE_FLOAT:
            case EIGHT_BYTE_FLOAT_SWAPPED:
            case EIGHT_BYTE_FLOAT_SWAPPED_ALL:
            case    EIGHT_BYTE_FLOAT_SWAPPED_ALL_HIGHT_65536:
                return 4;
        }
        return 0;
    }

    public static Class getJavaType(int id) {
        switch (id) {
            case BINARY:
                return Boolean.class;
            case TWO_BYTE_INT_UNSIGNED:
                return Integer.class;
            case TWO_BYTE_INT_SIGNED:
                return Short.class;
            case FOUR_BYTE_INT_UNSIGNED:
                return Long.class;
            case FOUR_BYTE_INT_SIGNED:
                return Integer.class;
            case FOUR_BYTE_INT_UNSIGNED_SWAPPED:
                return Long.class;
            case FOUR_BYTE_INT_SIGNED_SWAPPED:
                return Integer.class;
            case FOUR_BYTE_FLOAT:
                return Float.class;
            case FOUR_BYTE_FLOAT_SWAPPED:
                return Float.class;
            case EIGHT_BYTE_INT_UNSIGNED:
                return BigInteger.class;
            case EIGHT_BYTE_INT_SIGNED:
                return Long.class;
            case EIGHT_BYTE_INT_UNSIGNED_SWAPPED:
                return BigInteger.class;
            case EIGHT_BYTE_INT_SIGNED_SWAPPED:
                return Long.class;
            case EIGHT_BYTE_FLOAT:
                return Double.class;
            case EIGHT_BYTE_FLOAT_SWAPPED:
                EIGHT_BYTE_FLOAT_SWAPPED_ALL:
                EIGHT_BYTE_FLOAT_SWAPPED_ALL_HIGHT_65536:
                return Double.class;
            case TWO_BYTE_BCD:
                return Short.class;
            case FOUR_BYTE_BCD:
                return Integer.class;
            case CHAR:
            case VARCHAR:
                return String.class;
        }
        return null;
    }

    public static void main(String[] args) {
        byte[] bytes=HexString2Buf(" 43 6d c5 49".replaceAll(" ",""));
        System.out.println(bytesToValueRealOffset(bytes,0,FOUR_BYTE_FLOAT));

    }
    public static byte[] HexString2Buf(String src) {
        src = src.replaceAll(" ", "");
        int len = src.length();
        byte[] ret = new byte[len / 2];
        byte[] tmp = src.getBytes();
        for (int i = 0; i < len; i += 2) {
            ret[i / 2] = uniteBytes(tmp[i], tmp[i + 1]);
        }
        return ret;
    }
    public static byte uniteBytes(byte src0, byte src1) {
        byte _b0 = Byte.decode("0x" + new String(new byte[]{src0}))
                .byteValue();
        _b0 = (byte) (_b0 << 4);
        byte _b1 = Byte.decode("0x" + new String(new byte[]{src1}))
                .byteValue();
        byte ret = (byte) (_b0 ^ _b1);
        return ret;
    }
    /**
     * create by: 高嵩
     * description: 根据数据类型得到真实数据
     * create time: 2019/8/29 12:16
     *
     * @return
     * @params
     */
    public static Object bytesToValueRealOffset(byte[] data, int offset, int dataType) {
        switch (dataType) {
            case DataType.BINARY:
                return data[data.length - 1 - offset] & 0xff;
            case DataType.ONE_BYTE:
                return new Integer(data[offset] & 0xff);
            case DataType.TWO_BYTE_INT_UNSIGNED:
                return new Integer(((data[offset] & 0xff) << 8) | (data[offset + 1] & 0xff));
            case DataType.TWO_BYTE_INT_SIGNED:
                return new Short((short) (((data[offset] & 0xff) << 8) | (data[offset + 1] & 0xff)));
            case DataType.TWO_BYTE_INT_UNSIGNED_SWAPPED:
                return new Short((short) (((data[offset + 1] & 0xff) << 8) | (data[offset] & 0xff)));
            case DataType.TWO_BYTE_INT_UNSIGNED_SWAPPED_NOT:
                Short value = new Short((short) (((data[offset + 1] & 0xff) << 8) | (data[offset] & 0xff)));
                if (value == 0) {
                    return 1;
                } else {
                    return 0;
                }
            case DataType.TWO_BYTE_BCD:
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < 2; i++) {
                    sb.append(bcdNibbleToInt(data[offset + i], true));
                    sb.append(bcdNibbleToInt(data[offset + i], false));
                }
                return new Short(Short.parseShort(sb.toString()));
            case DataType.FOUR_BYTE_INT_UNSIGNED:
                return new Long(((long) ((data[offset] & 0xff)) << 24) | ((long) ((data[offset + 1] & 0xff)) << 16)
                        | ((long) ((data[offset + 2] & 0xff)) << 8) | ((data[offset + 3] & 0xff)));
            case DataType.FOUR_BYTE_INT_SIGNED:
                return new Integer(((data[offset] & 0xff) << 24) | ((data[offset + 1] & 0xff) << 16)
                        | ((data[offset + 2] & 0xff) << 8) | (data[offset + 3] & 0xff));
            case DataType.FOUR_BYTE_INT_UNSIGNED_SWAPPED:
                return new Long(((long) ((data[offset + 2] & 0xff)) << 24) | ((long) ((data[offset + 3] & 0xff)) << 16)
                        | ((long) ((data[offset] & 0xff)) << 8) | ((data[offset + 1] & 0xff)));
            case DataType.FOUR_BYTE_INT_SIGNED_SWAPPED:
                return new Integer(((data[offset + 2] & 0xff) << 24) | ((data[offset + 3] & 0xff) << 16)
                        | ((data[offset] & 0xff) << 8) | (data[offset + 1] & 0xff));
            case DataType.FOUR_BYTE_FLOAT:
                return new Float(Float.intBitsToFloat(((data[offset] & 0xff) << 24) | ((data[offset + 1] & 0xff) << 16)
                        | ((data[offset + 2] & 0xff) << 8) | (data[offset + 3] & 0xff)));
            case DataType.FOUR_BYTE_FLOAT_SWAPPED:
                return new Float(Float.intBitsToFloat(((data[offset + 2] & 0xff) << 24) | ((data[offset + 3] & 0xff) << 16)
                        | ((data[offset] & 0xff) << 8) | (data[offset + 1] & 0xff)));
            case DataType.FOUR_BYTE_FLOAT_SWAPPED_ALL:
                return new Float(Float.intBitsToFloat(((data[offset + 3] & 0xff) << 24) | ((data[offset + 2] & 0xff) << 16)
                        | ((data[offset + 1] & 0xff) << 8) | (data[offset] & 0xff)));
            case DataType.FOUR_BYTE_BCD:
                sb = new StringBuffer();
                for (int i = 0; i < 4; i++) {
                    sb.append(bcdNibbleToInt(data[offset + i], true));
                    sb.append(bcdNibbleToInt(data[offset + i], false));
                }
                return new Integer(Integer.parseInt(sb.toString()));
            case DataType.EIGHT_BYTE_INT_UNSIGNED:
                byte[] b9 = new byte[9];
                System.arraycopy(data, offset, b9, 1, 8);
                return new BigInteger(b9);
            case DataType.EIGHT_BYTE_INT_SIGNED:
                return new Long(((long) ((data[offset] & 0xff)) << 56) | ((long) ((data[offset + 1] & 0xff)) << 48)
                        | ((long) ((data[offset + 2] & 0xff)) << 40) | ((long) ((data[offset + 3] & 0xff)) << 32)
                        | ((long) ((data[offset + 4] & 0xff)) << 24) | ((long) ((data[offset + 5] & 0xff)) << 16)
                        | ((long) ((data[offset + 6] & 0xff)) << 8) | ((data[offset + 7] & 0xff)));
            case DataType.EIGHT_BYTE_INT_UNSIGNED_SWAPPED:
                b9 = new byte[9];
                b9[1] = data[offset + 6];
                b9[2] = data[offset + 7];
                b9[3] = data[offset + 4];
                b9[4] = data[offset + 5];
                b9[5] = data[offset + 2];
                b9[6] = data[offset + 3];
                b9[7] = data[offset];
                b9[8] = data[offset + 1];
                return new BigInteger(b9);
            case DataType.EIGHT_BYTE_INT_SIGNED_SWAPPED:
                return new Long(((long) ((data[offset + 6] & 0xff)) << 56) | ((long) ((data[offset + 7] & 0xff)) << 48)
                        | ((long) ((data[offset + 4] & 0xff)) << 40) | ((long) ((data[offset + 5] & 0xff)) << 32)
                        | ((long) ((data[offset + 2] & 0xff)) << 24) | ((long) ((data[offset + 3] & 0xff)) << 16)
                        | ((long) ((data[offset] & 0xff)) << 8) | ((data[offset + 1] & 0xff)));
            case DataType.EIGHT_BYTE_FLOAT:
                return new Double(Double.longBitsToDouble(((long) ((data[offset] & 0xff)) << 56)
                        | ((long) ((data[offset + 1] & 0xff)) << 48) | ((long) ((data[offset + 2] & 0xff)) << 40)
                        | ((long) ((data[offset + 3] & 0xff)) << 32) | ((long) ((data[offset + 4] & 0xff)) << 24)
                        | ((long) ((data[offset + 5] & 0xff)) << 16) | ((long) ((data[offset + 6] & 0xff)) << 8)
                        | ((data[offset + 7] & 0xff))));
            case DataType.EIGHT_BYTE_FLOAT_SWAPPED:
                return new Double(Double.longBitsToDouble(((long) ((data[offset + 6] & 0xff)) << 56)
                        | ((long) ((data[offset + 7] & 0xff)) << 48) | ((long) ((data[offset + 4] & 0xff)) << 40)
                        | ((long) ((data[offset + 5] & 0xff)) << 32) | ((long) ((data[offset + 2] & 0xff)) << 24)
                        | ((long) ((data[offset + 3] & 0xff)) << 16) | ((long) ((data[offset] & 0xff)) << 8)
                        | ((data[offset + 1] & 0xff))));
            case DataType.EIGHT_BYTE_FLOAT_SWAPPED_ALL:
                float h = new Float(Float.intBitsToFloat(((data[offset + 3] & 0xff) << 24) | ((data[offset + 2] & 0xff) << 16)
                        | ((data[offset + 1] & 0xff) << 8) | (data[offset] & 0xff)));
                float l = new Float(Float.intBitsToFloat(((data[offset + 7] & 0xff) << 24) | ((data[offset + 6] & 0xff) << 16)
                        | ((data[offset + 5] & 0xff) << 8) | (data[4] & 0xff)));
                return h * 10000 + l;
            case DataType.EIGHT_BYTE_FLOAT_SWAPPED_ALL_HIGHT_65536:
                h = new Float(Float.intBitsToFloat(((data[offset + 3] & 0xff) << 24) | ((data[offset + 2] & 0xff) << 16)
                        | ((data[offset + 1] & 0xff) << 8) | (data[offset] & 0xff)));
                l = new Float(Float.intBitsToFloat(((data[offset + 7] & 0xff) << 24) | ((data[offset + 6] & 0xff) << 16)
                        | ((data[offset + 5] & 0xff) << 8) | (data[4] & 0xff)));
                return h * 65536 + l;
            case DataType.SIX_BYTE_DATETIME:
                int hour = new Integer(((data[offset] & 0xff) << 8) | (data[offset + 1] & 0xff));
                int min = new Integer(((data[offset + 2] & 0xff) << 8) | (data[offset + 3] & 0xff));
                int sec = new Integer(((data[offset + 4] & 0xff) << 8) | (data[offset + 5] & 0xff));
                return hour + org.apache.commons.lang.StringUtils.leftPad(min + "", 2, '0') + StringUtils.leftPad(sec + "", 2, '0');

            case DataType.TWO_BYTE_INT_UNSIGNED_240_15:
                int a = new Integer(((data[offset] & 0xff) << 8) | (data[offset + 1] & 0xff));
                if (a == 15) {
                    return 1;
                } else {
                    return 0;
                }
            case DataType.SIX_BYTE_LONG:
                Long high = new Long(((long) ((data[offset] & 0xff)) << 24) | ((long) ((data[offset + 1] & 0xff)) << 16)
                        | ((long) ((data[offset + 2] & 0xff)) << 8) | ((data[offset + 3] & 0xff)));
                Integer low = new Integer(((data[offset] & 0xff) << 8) | (data[offset + 1] & 0xff));
                return (high + low / 1000);
        }
        throw new RuntimeException("Unsupported data type: " + dataType);
    }
    /**
     * @函数功能: 10进制串转为BCD码
     * @输入参数: 10进制串
     * @输出结果: BCD码
     */
    public static byte[] str2Bcd(String asc) {
        int len = asc.length();
        int mod = len % 2;

        if (mod != 0) {
            asc = "0" + asc;
            len = asc.length();
        }

        byte abt[] = new byte[len];
        if (len >= 2) {
            len = len / 2;
        }

        byte bbt[] = new byte[len];
        abt = asc.getBytes();
        int j, k;

        for (int p = 0; p < asc.length() / 2; p++) {
            if ((abt[2 * p] >= '0') && (abt[2 * p] <= '9')) {
                j = abt[2 * p] - '0';
            } else if ((abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {
                j = abt[2 * p] - 'a' + 0x0a;
            } else {
                j = abt[2 * p] - 'A' + 0x0a;
            }

            if ((abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) {
                k = abt[2 * p + 1] - '0';
            } else if ((abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {
                k = abt[2 * p + 1] - 'a' + 0x0a;
            } else {
                k = abt[2 * p + 1] - 'A' + 0x0a;
            }

            int a = (j << 4) + k;
            byte b = (byte) a;
            bbt[p] = b;
        }
        return bbt;
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
}
