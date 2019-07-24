package com.cic.pas.service;

import java.io.IOException;  
import java.io.InputStream;  
import java.io.OutputStream;  
import java.util.Enumeration;  
import java.util.HashSet;  
import java.util.Set;  
import java.util.TooManyListenersException;  
  
import gnu.io.CommPortIdentifier;  
import gnu.io.PortInUseException;  
import gnu.io.SerialPort;  
import gnu.io.SerialPortEvent;  
import gnu.io.SerialPortEventListener;  
import gnu.io.UnsupportedCommOperationException;  
  
public class SerialPortDao implements Runnable, SerialPortEventListener {  
      
    private static boolean isOpen=false;  
    static Set<CommPortIdentifier> portList=new HashSet<CommPortIdentifier>();  
    final static String appName="MyApp";  
    private static InputStream is;  
    private static OutputStream os;  
    private static SerialPort serialPort;  
    byte[] readBuffer=new byte[100];  
      
    public Set<CommPortIdentifier> getPortList(){  
        Enumeration tempPortList;  //枚举类  
        CommPortIdentifier portIp;  
        tempPortList=CommPortIdentifier.getPortIdentifiers();  
        /*不带参数的getPortIdentifiers方法获得一个枚举对象，该对象又包含了系统中管理每个端口的CommPortIdentifier对象。 
         * 注意这里的端口不仅仅是指串口，也包括并口。 
         * 这个方法还可以带参数。 
         * getPortIdentifiers(CommPort)获得与已经被应用程序打开的端口相对应的CommPortIdentifier对象。  
         * getPortIdentifier(String portName)获取指定端口名（比如“COM1”）的CommPortIdentifier对象。 
         */  
        while(tempPortList.hasMoreElements()){  
            //在这里可以调用getPortType方法返回端口类型，串口为CommPortIdentifier.PORT_SERIAL  
            portIp=(CommPortIdentifier) tempPortList.nextElement();  
            portList.add(portIp);  
        }  
        return portList;  
    }  
    public boolean openSerialPort(CommPortIdentifier portIp,int delay){  
        try {  
            serialPort=(SerialPort) portIp.open(appName, delay);  
            /* open方法打开通讯端口，获得一个CommPort对象。它使程序独占端口。 
             * 如果端口正被其他应用程序占用，将使用 CommPortOwnershipListener事件机制，传递一个PORT_OWNERSHIP_REQUESTED事件。 
             * 每个端口都关联一个 InputStream 和一个OutputStream。 
             * 如果端口是用open方法打开的，那么任何的getInputStream都将返回相同的数据流对象，除非有close 被调用。 
             * 有两个参数，第一个为应用程序名；第二个参数是在端口打开时阻塞等待的毫秒数。 
             */  
        } catch (PortInUseException e) {  
            return false;  
        }  
        try {  
            is=serialPort.getInputStream();/*获取端口的输入流对象*/  
            os=serialPort.getOutputStream();/*获取端口的输出流对象*/  
        } catch (IOException e) {  
            return false;  
        }  
        try {  
            serialPort.addEventListener(this);/*注册一个SerialPortEventListener事件来监听串口事件*/  
        } catch (TooManyListenersException e) {  
            return false;  
        }  
        serialPort.notifyOnDataAvailable(true);/*数据可用*/  
        try {  
            /*设置串口初始化参数，依次是波特率，数据位，停止位和校验*/  
            serialPort.setSerialPortParams(4800, SerialPort.DATABITS_8,SerialPort.STOPBITS_1 , SerialPort.PARITY_NONE);  
        } catch (UnsupportedCommOperationException e) {  
            return false;  
        }  
        return true;  
    }  
    public boolean closeSerialPort(){  
        if(isOpen){  
            try {  
                is.close();  
                os.close();  
                serialPort.notifyOnDataAvailable(false);  
                serialPort.removeEventListener();  
                serialPort.close();  
                isOpen = false;  
            } catch (IOException e) {  
                return false;  
            }  
        }  
        return true;  
    }  
    public boolean sendMessage(String message){  
        try {  
            os.write(message.getBytes());  
        } catch (IOException e) {  
            return false;  
        }  
        return true;  
    }  
    @Override  
    public void serialEvent(SerialPortEvent event) {  
        /* 
         * 此处省略一下事件，可酌情添加 
         *  SerialPortEvent.BI:/*Break interrupt,通讯中断 
         *  SerialPortEvent.OE:/*Overrun error，溢位错误 
         *  SerialPortEvent.FE:/*Framing error，传帧错误 
         *  SerialPortEvent.PE:/*Parity error，校验错误 
         *  SerialPortEvent.CD:/*Carrier detect，载波检测  
         *  SerialPortEvent.CTS:/*Clear to send，清除发送  
         *  SerialPortEvent.DSR:/*Data set ready，数据设备就绪 
         *  SerialPortEvent.RI:/*Ring indicator，响铃指示 
         *  SerialPortEvent.OUTPUT_BUFFER_EMPTY:/*Output buffer is empty，输出缓冲区清空 
         */  
        if(event.getEventType()==SerialPortEvent.DATA_AVAILABLE){     
            /*Data available at the serial port，端口有可用数据。读到缓冲数组，输出到终端*/  
            try {  
                while(is.available()>0){  
                    is.read(readBuffer);//收到的数据再此，可视情况处理  
                }  
                //SPCommandDao.startDoMessage(new String (readBuffer));//这一句是我的自定义类，处理接受到的信息，可删除  
            } catch (IOException e) {  
            }  
        }  
    }  
  
    @Override  
    public void run() {  
        try {  
            Thread.sleep(50);//每次收发数据完毕后线程暂停50ms  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        }  
    }  
  
}  