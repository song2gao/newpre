package com.cic.pas;

import com.cic.pas.common.util.CRC16M;

public class NewTest {
    public static void main(String[] args) {
        String hexStr="030300000002";
        byte[] sends=CRC16M.HexString2Buf(hexStr);
        byte[] bytes=CRC16M.getSendBuf(sends);
        System.out.println(CRC16M.getBufHexStr(bytes));
    }
}
