package com.cic.pas;

import com.cic.pas.common.util.CRC16M;

public class NewTest {
    public static void main(String[] args) {
        byte[] addressBytes=new byte[]{0,3,32};
        int address = (addressBytes[0] << 16) + (addressBytes[1] << 8) + (addressBytes[2]);
        System.out.println(address);
        System.out.println((int)addressBytes[1]<<8);
    }
}
