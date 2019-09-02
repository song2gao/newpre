package com.cic.pas.application;

import java.io.IOException;
import java.util.List;

import com.cic.pas.dao.LogDao;
import com.cic.pas.dao.LogFactory;
import com.cic.pas.thread.*;
import org.apache.log4j.Logger;
import org.apache.mina.transport.socket.SocketAcceptor;


import com.cic.pas.application.manage.PChannelService;
import com.cic.pas.common.bean.Channel;
import com.cic.pas.common.bean.TerminalDevice;
import com.cic.pas.dao.BussinessConfig;
import com.cic.pas.service.ConnectorSocketFactory;
import com.cic.pas.service.ServerContext;
import com.cic.pas.service.ServerSocketFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

/**
 * @author lenovo
 * @ClassName: ChannelManageService
 * @Description: 通道管理服务
 * @date 2014-2-26 上午09:01:22
 */
public class ChannelManageService extends PChannelService {

    public static final Logger logger = Logger
            .getLogger(ChannelManageService.class);

    private static ServerSocketFactory serverSocketFactory = new ServerSocketFactory();

    NioSocketConnector socketConnector = new NioSocketConnector();

    public static ChannelConfigService channelConfigService;

    /*
     * public void startServer(List<Channel> list){
     *
     * open(list); }
     */
    static {

        new ServerContext();
        channelConfigService = new ChannelConfigService();
    }

    /*
     * 接收通道关闭的命令，执行此方法
     */
    public void close(SocketAcceptor server) {

        try {
            if (server != null) {
                server.dispose();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("通道关闭。。。");
    }

    /**
     * 通道开启方法1
     *
     * @param channelList
     */
    public void open(List<Channel> channelList) {

        if (channelList != null && !channelList.isEmpty()) {
            for (int i = 0; i < channelList.size(); i++) {
                Channel c = channelList.get(i);
                if (c.getCha_detectionMark().equals("1")) {
                    try {
                        String addressPort = c.getCha_addressPort();
                        String type = c.getCha_commMode();// 1 COM 2 SERVER 3
                        // CLIENT
                        if (type.equals("1")) {//串口
//                            for (TerminalDevice td : c.getTerminalList()) {
//                                SerialParameters params = new SerialParameters();
//                                params.setBaudRate(9600);
//                                params.setCommPortId(td.getMSA());
//                                params.setDataBits(8);
//                                params.setStopBits(1);
//                                params.setParity(0);
//                                ModbusMaster master = null;
//                                if (c.getCha_protocolCode().equals("1")) {
//                                    master = modbusFactory.createComTcpMaster(
//                                            params, 1);
//                                } else {
//                                    master = modbusFactory.createRtuMaster(
//                                            params, 1);
//                                }
//                                GetDataThread thread = new GetDataThread(td,
//                                        master);
//                                thread.setName(td.getMSA());
//                                thread.start();
//                                if (ConnectorContext.threadMap.containsKey(td
//                                        .getMSA())) {
//                                    BaseThread old = ConnectorContext.threadMap
//                                            .get(td.getMSA());
//                                    old.exit = true;
//                                    ConnectorContext.threadMap.remove(td
//                                            .getMSA());
//                                }
//                                ConnectorContext.threadMap.put(td.getMSA(),
//                                        thread);
//                            }
                        } else if (type.equals("2")) {// TCP server 外网 通过端口号识别采集器
                            for (TerminalDevice td : c.getTerminalList()) {
                                ServerAccept accept = new ServerAccept(td, c.getCha_protocolCode());
                                accept.start();
                            }
                        } else if (type.equals("3")) {// TCP CLIENT
                            for (TerminalDevice td : c.getTerminalList()) {
                                String tdAddressPort = td.getMSA();
                                int index = tdAddressPort.indexOf(":");
                                String ip = tdAddressPort.substring(0, index);
                                int port = Integer.parseInt(tdAddressPort.substring(index + 1));
                                ClientConnectThread thread = new ClientConnectThread(ip, port, c.getCha_protocolCode());
                                thread.start();
                            }
                        } else if (type.equals("4")) {
                            SocketAcceptor channel = serverSocketFactory.createServerSocket(c.getCha_addressPort(), c.getCha_protocolCode());
                            try {
                                channel.bind();
                                LogFactory.getInstance().saveLog("采集服务启动", LogDao.operation);
                            } catch (IOException e) {
                                channel.unbind();
                                channel.dispose();
                                e.printStackTrace();
                            }
                        }
                    } catch (Exception e) {
                        // TODO: handle exception
                        e.printStackTrace();
                    }
                } else {
                    logger.info("端口号未标识启用");
                }
            }
        } else {
            logger.info("通道信息集合中不存在信息");
        }

    }

    /**
     * 从数据库访问服务中读取通道列表，并启动标识为1的通道
     */
    public void openChannel() {
        List<Channel> list = channelConfigService.config.channelList;

        for (int i = 0; i < list.size(); i++) {
            Channel c = list.get(i);
            System.out.println("<通道号：>" + c.getCha_number() + "<地址：>"
                    + c.getCha_addressPort());
        }
        open(list);
    }

}
