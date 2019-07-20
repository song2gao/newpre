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

import java.math.BigInteger;

/**
 * @author Matthew Lohbihler
 */
public class DataType {
    public static final int BINARY = 1;//位

    public static final int TWO_BYTE_INT_UNSIGNED = 2;//无符号短整型
    public static final int TWO_BYTE_INT_UNSIGNED_SWAPPED = 22;//无符号短整型反转
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
        Double test=24.2;
        int a=Float.floatToIntBits(test.floatValue());
        System.out.println((int)a/65536);

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
}
