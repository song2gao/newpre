package com.cic.pas.procotol;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketAddress;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.cic.pas.common.util.CRC16M;
import com.cic.pas.common.util.StringUtils;
import com.cic.pas.service.ConnectorContext;
import com.cic.pas.thread.BaseThread;
import com.cic.pas.thread.Get3761DataThread;
import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.write.WriteRequest;

import com.cic.pas.common.bean.TerminalDevice;
import com.cic.pas.common.util.Util;
import com.cic.pas.dao.BussinessConfig;
import com.cic.pas.dao.LogDao;
import com.cic.pas.dao.LogFactory;
import com.cic.pas.service.ServerContext;

public class MyCheckFilter extends IoFilterAdapter {

    public static final Logger logger = Logger.getLogger(MyCheckFilter.class);
    public static byte PFC = 0;

    @SuppressWarnings("static-access")
    @Override
    /**
     * 验证
     */
    public void messageReceived(NextFilter nextFilter, IoSession session,
                                Object message) throws Exception {
        java.sql.Timestamp date = new java.sql.Timestamp(new Date().getTime());

    }

    private final void createLog(int id) {
        String root = System.getProperty("user.dir");
        File fileLog = new File("CycleLog\\" + id + ServerContext.getLogTail());
        try {
            if (!fileLog.exists()) {
                fileLog.createNewFile();
            }
            ServerContext.puTermianlLog(id, fileLog);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sessionIdle(NextFilter nextFilter, IoSession session,
                            IdleStatus status) throws Exception {
//		int terminal_id = (Integer)session.getAttribute("terminal_id");
//		LogFactory.getInstance().saveLog("采集设备  ["+terminal_id+" ] 未发送协议",LogDao.warnning);
//		for (TerminalDevice t :BussinessConfig.config.terminalList) {
//			if(t.getId() == terminal_id){
//				t.setIsOnline(0);
//			}
//		}
//		super.sessionIdle(nextFilter, session, status);
    }

    /**
     * 获取IP
     */
    private String getIP(IoSession session) {
        SocketAddress client = session.getRemoteAddress();
        String IP = client.toString().substring(1,
                client.toString().lastIndexOf(":"));
        return IP;
    }

    @Override
    public void sessionClosed(NextFilter nextFilter, IoSession session)
            throws Exception {
        int termial_id = (Integer) session.getAttribute("terminal_id");
        LogFactory.getInstance().saveLog("采集设备   [" + termial_id + "] 关闭", LogDao.warnning);
        logger.info("采集设备   [" + termial_id + "] 关闭");
        session.close(false);
        if (ServerContext.clientMap != null && ServerContext.clientMap.get(session.getId()) != null) {
            ServerContext.remove(session.getId());
        }
        //super.sessionClosed(nextFilter, session);
    }

    @Override
    public void messageSent(NextFilter nextFilter, IoSession session,
                            WriteRequest writeRequest) throws Exception {
//		Object message = writeRequest.getMessage();
//		if (message instanceof byte[]) {
//			byte[] bytes = (byte[]) message;
//			StringBuffer bStringBuffer = new StringBuffer();
//			for (int i = 0; i < bytes.length; i++) {
//				bStringBuffer.append((Util.toHex(bytes[i]) + " "));
//			}
//
//				PFC++;
//			logger.info("sent :=" + bStringBuffer);
//		} else {
//			logger.info("服务器发送消息不是字节数组，请检查");
//		}
        super.messageSent(nextFilter, session, writeRequest);
    }

    @Override
    public void sessionOpened(NextFilter nextFilter, IoSession session)
            throws Exception {
        String IP = getIP(session);
        LogFactory.getInstance().saveLog("采集设备  IP: [" + IP + "] 请求连接 [host:7002]", LogDao.warnning);
        System.out.println("采集设备  IP: [" + IP + "] 请求连接 [host:7002]");
        super.sessionOpened(nextFilter, session);
    }

    public void exceptionCaught(IoSession session, Throwable cause)
            throws Exception {
        cause.printStackTrace();
        session.close(false);
    }

}
