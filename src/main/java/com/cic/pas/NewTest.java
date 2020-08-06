package com.cic.pas;

import com.cic.pas.common.util.CRC16M;
import com.cic.pas.common.util.ClassUtils;
import com.cic.pas.procotol.ByteHandler;
import com.cic.pas.procotol.Client2ClientHandler;
import com.cic.pas.procotol.TestHandler;
import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.math.BigDecimal;
import java.net.InetSocketAddress;

public class NewTest {
    public static void main(String[] args) {
//        String send="0300001F02F080320100000001000E00000401120A1002 00 0C 00 01 84 00 76 C0".replaceAll(" ","");
//        String addressStr=send.substring(send.length()-6);
        String addressStr="ac ed 00 05 ";
        byte[] addressbytes=CRC16M.HexString2Buf(addressStr);
//        int addressInteger=(addressbytes[0]&0xff)<<16|(addressbytes[1]&0xff)<<8|(addressbytes[2]&0xff);
        System.out.println(CRC16M.getBufHexStr(addressbytes));

    }

    /**
     * create by: 高嵩
     * description: 得到读取长度   MODBUS 用03 04读位操作时对长度进行处理  x.01代表X的第1位   x.10代表第10位
     * create time: 2019/11/17 13:44
     *
     * @return
     * @params
     */
    private static int getRealLen(BigDecimal lastAddress, int len, int lastfunction, int lastMMpType, BigDecimal address, int curlen) {
        if (lastMMpType == 1 && lastfunction != 1 && lastfunction != 2) {
            len = lastAddress.intValue() - address.intValue() + 1;
//            BigDecimal realLen = new BigDecimal(len).divide(new BigDecimal("16"));//1个字等于16位 得到需要读取几个字
//            BigDecimal currAddressPoint = address.subtract(new BigDecimal(address.intValue())).subtract(new BigDecimal("0.00"));//从【一个字第0位】到【起始地址开始】读取一个完整字所需要读取的位个数  举例  x.01 需要读取的位个数为   x.01-x.00 共1位
//            BigDecimal lastAddressPoint = new BigDecimal("0.15").subtract(lastAddress.subtract(new BigDecimal(lastAddress.intValue())));
//            len = realLen.add((currAddressPoint).add(lastAddressPoint).multiply(new BigDecimal("10"))).intValue();
        }
        return len;
    }
}
