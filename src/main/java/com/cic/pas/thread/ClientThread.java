package com.cic.pas.thread;

import java.net.SocketAddress;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.mina.core.session.IoSession;

import com.cic.pas.common.bean.MeterDevice;
import com.cic.pas.common.bean.TerminalDevice;
import com.cic.pas.common.util.CRC16M;
import com.cic.pas.common.util.Util;
import com.cic.pas.service.ConnectorContext;

public class ClientThread extends Thread {

	// private Logger logger = Logger.getLogger(ClientThread.class);

	private volatile IoSession session;

	public static boolean isReviced = false;
	
	private volatile int currFlag = 0;

	public volatile boolean exit = false;

	private Map<String, String> map = new LinkedHashMap<String, String>();

	public ClientThread(IoSession session, TerminalDevice td) {
		this.session = session;
		for (MeterDevice meter : td.getMeterList()) {
			if (meter.getStatus() == 1) {
				getSendBuff(meter);
			}
		}
	}

	@Override
	public void run() {
		while (!exit) {
			for (String key : map.keySet()) {
				String toSend = map.get(key);
				int index = key.indexOf(":");
				String modAddress = key.substring(index + 1);
				session.setAttribute("modAddress", modAddress);
				byte[] b = CRC16M.hexStringToByte(Util.fill(Integer
						.toHexString(currFlag), 4, '0')
						+ "00000006" + toSend);
				int a = Util.bytesToInt(b, b.length - 2, b.length);
				session.setAttribute("count", a);
				isReviced = false;
				session.setAttribute("lastFlag", currFlag);
				currFlag+=1;
				session.write(b);
				String ipPort = getIP(session);
				ClientThread clientThread = null;
				ConnectorContext.threadMap
						.get(ipPort);
				if (clientThread != null) {
					synchronized (clientThread) {
						try {
							clientThread.wait(30000);
							if (!isReviced) {
								session.close(false);
							}
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							Thread.currentThread().interrupt();
							e.printStackTrace();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				try {
					if (key.indexOf("214:") >= 0) {
						Thread.sleep(100);
					}
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws SecurityException,
			NoSuchMethodException {
		Calendar ca = Calendar.getInstance();
		int calday = ca.get(Calendar.DATE);
		ca.set(Calendar.DATE, calday - 1);
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String datetime = format.format(ca.getTime());
		System.out.println(datetime);
	}

	public void getSendBuff(MeterDevice meter) {
//		String head = Util
//				.fill(Integer.toHexString(meter.getAddress()), 2, '0')
//				+ meter.getProduction_code();
//		List<PointDevice> points = meter.getPointDevice();
//		String str = "";
//		int lastAddress = 0;
//		int lastLen = 0;
//		int len = 0;
//		int i = 0;
//		String key = "";
//		if (points.size() == 1) {
//			PointDevice p = points.get(0);
//			String k = meter.getCode()
//					+ ":"
//					+ Util.fill(Integer.toHexString(Integer.parseInt(p
//							.getModAddress())), 4, '0');
//			String v = head + p.getModAddress();
//			v += Util.fill(Integer.toHexString(p.getPointLen()), 4, '0');
//			map.put(k, v);
//		} else {
//			for (PointDevice p : points) {
//				int address = Integer.parseInt(p.getModAddress());
//				int curlen = p.getPointLen();
//				String modAddress = Util.fill(Integer.toHexString(Integer
//						.parseInt(p.getModAddress())), 4, '0');
//				if (len == 0) {
//					len = curlen;
//					str = head + modAddress;
//					key = meter.getCode() + ":" + modAddress;
//				} else {
//					if (address == lastAddress + lastLen) {
//						if (len >= 95) {
//							str += Util.fill(Integer.toHexString(len), 4, '0');
//							map.put(key, str);
//							key = meter.getCode() + ":" + modAddress;
//							str = head + modAddress;
//							len = curlen;
//						} else {
//							len += curlen;
//							if (i == points.size() - 1) {
//								str += Util.fill(Integer.toHexString(len), 4,
//										'0');
//								map.put(key, str);
//							}
//						}
//					} else {
//						str += Util.fill(Integer.toHexString(len), 4, '0');
//						map.put(key, str);
//						if (i == points.size() - 1) {
//							str = head
//									+ modAddress
//									+ Util.fill(Integer.toHexString(curlen), 4,
//											'0');
//							key = meter.getCode() + ":" + modAddress;
//							map.put(key, str);
//						} else {
//							key = meter.getCode() + ":" + modAddress;
//							str = head + modAddress;
//							len = curlen;
//						}
//					}
//				}
//				lastAddress = address;
//				lastLen = curlen;
//				i++;
//			}
//		}
	}

	private String getIP(IoSession session) {
		SocketAddress client = session.getRemoteAddress();
		String IP = client.toString().substring(1);
		return IP;
	}

	private String getIpPort(IoSession session) {
		SocketAddress client = session.getLocalAddress();
		String ipPort = client.toString().substring(
				client.toString().indexOf(":") + 1);
		return ipPort;
	}
}
