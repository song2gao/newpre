package com.cic.socket.sender;

import com.cic.pas.common.net.Command;
import com.cic.pas.common.net.Message;
import com.cic.socket.AbstractConnectSender;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 版权所有(C)2013-2014 CIC
 * 公司名称：众智盈鑫能源科技（北京）有限公司
 * 版本:V 1.0
 * 文件名：com.cic.pas.procotol @NAME: ByteModBusRtuDecoder
 * 作者: 高嵩
 * 创建时间: 2019/8/3 11:38
 * 修改时间：2019/8/3 11:38
 * 功能描述
 */
public class SystemDataSender extends AbstractConnectSender {
    private Map map;

    public SystemDataSender(String ip, int port, Map<String, Object> map) {
        super(ip, port);
        this.map = map;
    }

    public List<Message> getMessages() {
        List<Message> messages = new ArrayList<Message>();
        Message m = new Message();
        m.setC(Command.SystemData);
        m.setConfigMap(map);
        messages.add(m);
        return messages;
    }
}
