package com.cic.socket.sender;

import com.cic.pas.common.net.Command;
import com.cic.pas.common.net.Message;
import com.cic.socket.AbstractConnectSender;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AlarmDataSender extends AbstractConnectSender {
    private Map map;
    public AlarmDataSender(String ip, int port, Map<String, Object> map) {
        super(ip, port);
        this.map = map;
    }
    @Override
    protected List<Message> getMessages() {
        List<Message> messages = new ArrayList<Message>();
            Message m = new Message();
            m.setC(Command.AlarmData);
            m.setConfigMap(map);
            messages.add(m);
        return messages;
    }
}
