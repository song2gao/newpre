package com.cic.pas.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.*;

import com.cic.pas.thread.BaseThread;
import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.springframework.context.ApplicationContext;

import com.cic.pas.common.net.ErrorMessage;
import com.cic.pas.common.util.LogUtil;
import com.cic.pas.dao.DBConfigDao;

/**
 * @author Administrator
 * 服务器上下文
 */


public class ServerContext {

    public static int a;

    /**
     * 定界符
     */
    public static int delimiter;

    public static Logger logger = Logger.getLogger(ServerContext.class);

    public static Map<String, Object> attribute;

    public static Date runtime;
    /**
     * 系统配置文件(服务器端属性集)
     */
    public static Properties serverConfigPro;
    /**
     * 错误信息描述属性集
     */
    public static Map<Integer, ErrorMessage> errorMap;
    /**
     * 保存所有连接到此服务器的终端的长连接session
     */
    public static Map<Long, IoSession> clientMap;
    public static Map<String,Map<Long,IoSession>> client2clientMap;
    /**
     * 所有GetDataThread线程
     */
    public static Map<String, BaseThread> threadMap;

    public static Map<String,BaseThread> transmitThreadMap=new HashMap<>();

    /**
     * 保存所有连接到此服务器的终端长连接session（用于取得历史数据）
     */
    public static Map<Integer, IoSession> historyClientMap;

    /**
     * 保存通讯日志输出流
     */
    public static Map<Integer, FileOutputStream> terminalCommunicationsLog;

    public static ApplicationContext ctx;

    /**
     * 保存所有的建立的serverSocket监听
     * 服务器套接字(连接下层终端,取实时数据)
     */
    public static Map<String, SocketAcceptor> serverSocketList;


    static {
        runtime = new Date();
        clientMap = new HashMap<Long, IoSession>();
        client2clientMap=new HashMap<>();
        threadMap=new HashMap<String, BaseThread>();
        terminalCommunicationsLog = new HashMap<Integer, FileOutputStream>();
        serverSocketList = new HashMap<String, SocketAcceptor>();

        serverConfigPro = DBConfigDao.serverConfigPro;
        logger.info("属性配置文件加载完成");

        delimiter = ServerContext.getWapDelimiter();

        logger.info("日志记录启动开始");
        LogUtil.openLog();
        logger.info("日志记录启动结束");
    }


    /**
     * 保存全局会话session
     *
     * @param session
     */
    public final static void addSession(IoSession session) {
        if (clientMap == null) {
            clientMap = new HashMap<Long, IoSession>();
        }
        clientMap.put(session.getId(), session);
    }
    public final static void addThread(String terminalCode,BaseThread thread){
        if(thread!=null){
            if(threadMap.containsKey(terminalCode)){
                threadMap.remove(terminalCode);
            }
            threadMap.put(terminalCode,thread);
        }
    }
    public final  static void removeThread(String terminalCode){
        threadMap.remove(terminalCode);
    }
    public final static void remove(long sessionId) {
        clientMap.remove(sessionId);
    }

    public final static FileOutputStream getTerminalLog(int id) {
        return terminalCommunicationsLog.get(id);
    }

    public final static void puTermianlLog(int id, File file) throws FileNotFoundException {
        FileOutputStream fos = new FileOutputStream(file);
        terminalCommunicationsLog.put(id, fos);
    }

    public final static String getLogTail() {
        return serverConfigPro.getProperty("Tail");
    }

    public final static String getOpeServerIp() {
        return serverConfigPro.getProperty("opeServerIp");
    }

    public final static int getOpeServerPort() {
        return Integer.parseInt(serverConfigPro.getProperty("opeServerPort"));
    }

    public final static int getPort() {
        return Integer.parseInt(serverConfigPro.getProperty("port"));
    }

    public final static int getPortCurrent() {
        return Integer.parseInt(serverConfigPro.getProperty("portCurrent"));
    }

    public final static int getOpePort() {
        return Integer.parseInt(serverConfigPro.getProperty("operatePort"));
    }

    public final static int getWapDelimiter() {
        return Integer.parseInt(serverConfigPro.getProperty("wapDelimiter"));
    }

    public final static int getPortHistory() {
        return Integer.parseInt(serverConfigPro.getProperty("portHistory"));
    }

    public final static int getIdleTime() {
        String idle = serverConfigPro.getProperty("sessionIdle");
        try {
            return Integer.parseInt(idle);
        } catch (Exception e) {
            return 10;
        }
    }

    public final static int getPoolSize() {
        return Integer.parseInt(serverConfigPro.getProperty("size"));
    }

    public final static int getBufferClearCycle() {
        return Integer.parseInt(serverConfigPro.getProperty("bufferClearCycle"));
    }

    public final static int getDataCycle() {
        return Integer.parseInt(serverConfigPro.getProperty("bufferAddCycle"));
    }

    public final static int getBufferSize() {
        return Integer.parseInt(serverConfigPro.getProperty("bufferSize"));
    }

    public final static String getOperationLog() {
        return serverConfigPro.getProperty("OperationLog");
    }

    public final static String getErrorLog() {
        return serverConfigPro.getProperty("ErrorLog");
    }

    public final static String getBussinessFile() {
        return serverConfigPro.getProperty("BussinessFile");
    }

    public final static String getBufferDataFile() {
        return serverConfigPro.getProperty("BufferData");
    }

    public final static String getErrorMessageFile() {
        return serverConfigPro.getProperty("ErrorMessageFile");
    }

    public final static String getApplicationContext() {
        return serverConfigPro.getProperty("ApplicationContext");
    }

    public static final Integer getExtremumCyclePersist() {
        return Integer.parseInt(serverConfigPro.getProperty("extremumCycle"));
    }

    public static final Integer getChangeCyclePersist() {
        return Integer.parseInt(serverConfigPro.getProperty("changeCycle"));
    }

    public final static boolean isNet() {
        String str = serverConfigPro.getProperty("isNet");
        if ("true".equals(str)) {
            return true;
        } else {
            return false;
        }
    }

    public final static void addHistorySession(int ID, IoSession session) {
        if (historyClientMap == null) {
            historyClientMap = new HashMap<Integer, IoSession>();
        }
        historyClientMap.put(ID, session);
    }

    public final static void removeHistory(int ID) {
        historyClientMap.remove(ID);
    }

    public final static ErrorMessage getErrorMessage(int code) {
        return errorMap.get(code);
    }

    public static void addAttribute(String key, Object value) {
        attribute.put(key, value);
    }

    public static void removeAttribute(String key) {
        attribute.remove(key);
    }


}