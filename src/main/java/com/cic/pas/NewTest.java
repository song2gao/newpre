package com.cic.pas;

import com.cic.pas.common.util.CRC16M;

import java.math.BigDecimal;

public class NewTest {
    public static void main(String[] args) {
       for(int i=1;i<32;i++){
           System.out.print("point"+i+"+");
       }

    }
    /**
     * create by: 高嵩
     * description: 得到读取长度   MODBUS 用03 04读位操作时对长度进行处理  x.01代表X的第1位   x.10代表第10位
     * create time: 2019/11/17 13:44
     * @params
     * @return
     */
    private static int getRealLen(BigDecimal lastAddress, int len, int lastfunction, int lastMMpType, BigDecimal address, int curlen) {
        if (lastMMpType == 1&&lastfunction!=1&&lastfunction!=2) {
            len=lastAddress.intValue()-address.intValue()+1;
//            BigDecimal realLen = new BigDecimal(len).divide(new BigDecimal("16"));//1个字等于16位 得到需要读取几个字
//            BigDecimal currAddressPoint = address.subtract(new BigDecimal(address.intValue())).subtract(new BigDecimal("0.00"));//从【一个字第0位】到【起始地址开始】读取一个完整字所需要读取的位个数  举例  x.01 需要读取的位个数为   x.01-x.00 共1位
//            BigDecimal lastAddressPoint = new BigDecimal("0.15").subtract(lastAddress.subtract(new BigDecimal(lastAddress.intValue())));
//            len = realLen.add((currAddressPoint).add(lastAddressPoint).multiply(new BigDecimal("10"))).intValue();
        }
        return len;
    }
}
