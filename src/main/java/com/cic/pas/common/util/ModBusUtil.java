package com.cic.pas.common.util;

public class ModBusUtil {
    public static int getFullAddress(int function, int address, int isPlcAddress) {
        int result = address;
        switch (function) {
            case 2:
                result += 10000;
                break;
            case 3:
                result += 40000;
                break;
            case 4:
                result += 30000;
                break;
        }
        if (isPlcAddress == 1) {
            result += 1;
        }
        return result;
    }

    //    public static Integer[] getProtocolCodeAndAddress(int address,int isPlcAddress){
//        Integer[] result=new Integer[2];
//        int a=address/10000;
//        switch (a){
//            case 0:
//                result[0]=1;
//                result[1]=address;
//                break;
//            case 1:
//                result[0]=2;
//                result[1]=address-10000;
//                break;
//            case 3:
//                result[0]=4;
//                result[1]=address-30000;
//                break;
//            case 4:
//            case 5:
//            case 7:
//                result[0]=3;
//                result[1]=address-40000;
//                break;
//                default:
//
//        }
//        if(isPlcAddress==1){
//            result[1]=result[1]-1;
//        }
//        return  result;
//    }
    public static int getProtocolCodeAndAddress(int address, int isPlcAddress) {
        if (isPlcAddress == 1) {
            return address - 1;
        } else {
            return address;
        }
    }

    public static byte[] getProtocol(int slaveId, int address, int len, int isPlcAddress, int storgeType) {
        byte[] val = new byte[6];
        val[0] = (byte)slaveId;
        if (isPlcAddress == 1) {
            address = address - 1;
        }
        val[1] = (byte)storgeType;
        val[2] = (byte)(address>>8&0xff);
        val[3] = (byte)(address&0xff);
        val[4]=(byte)(len>>8&0xff);
        val[5] = (byte)(len&0xff);
        return val;
    }
}
