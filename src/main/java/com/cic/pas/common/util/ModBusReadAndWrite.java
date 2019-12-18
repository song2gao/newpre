package com.cic.pas.common.util;

import java.math.BigDecimal;

public class ModBusReadAndWrite {
    public byte[] write(int slaveId, String mdType, int mdIncrease, int modWAddress, int wFunction, int wType, int wLength, int isBit, String wFormular, Double writeValue) {
        int resultLength = 9;
        byte writeFn = 15;
        switch (wFunction) {
            case 1:
            case 2:
                writeFn = 15;
                resultLength = 10;
                break;
            case 3:
            case 4:
                writeFn = 16;
                resultLength = 7 + wLength * 2;
                break;
        }
        short[] values = Util.RealValueToShorts(writeValue, wType, wLength);
        byte[] result = new byte[resultLength];
        result[0] = (byte) (slaveId & 0xff);
        result[1] = writeFn;
        result[2] = (byte) (modWAddress >> 8 & 0xff);
        result[3] = (byte) (modWAddress & 0xff);
        result[4] = (byte) (wLength >> 8 & 0xff);
        result[5] = (byte) (wLength & 0xff);
        if (isBit == 1) {
            String[] strs = wFormular.split(",");
            if (strs.length > 2) {
                values[0] = Short.parseShort(strs[values[0]]);
            }
            if (writeFn == 15) {
                result[6] = 1;
                result[7] = (byte) (values[0] & 0xff);
            } else {
                result[6] = (byte) (wLength * 2);
                result[7] = (byte) (values[0] >> 8 & 0xff);
                result[8] = (byte) (values[0] & 0xff);
            }
        } else {
            result[6] = (byte) (wLength * 2);
            for (int i = 0; i < values.length; i++) {
                values[i] = Util.manageData(new BigDecimal(values[i]), wFormular).shortValue();
                result[7 + i*2] = (byte) (values[i] >> 8 & 0xff);
                result[8 + i*2] = (byte) (values[i] & 0xff);
            }
        }
        return result;
    }

    public byte[] readData(int function, int slaveId, int start, int len) {
        byte[] result = new byte[6];
        result[0] = (byte) (slaveId & 0xff);
        result[1] = (byte) (function & 0xff);
        result[2] = (byte) (start >> 8 & 0xff);
        result[3] = (byte) (start & 0xff);
        result[4] = (byte) (len >> 8 & 0xff);
        result[5] = (byte) (len & 0xff);
        return result;
    }
}
