/**
 * 
 */
package com.cic.pas.thread;

/**
 * @author Administrator
 * 
 */
public class DuishiTask  {

	public void executeSent() {
		
		getData();
				
	}
	/**
	 * 协议码按照终端来发送.
	 */
	public void getData() {
			/*try {
				Set<Entry<Long,IoSession>>  set = ServerContext.clientMap.entrySet();
				for (Entry<Long, IoSession> entry : set) {
					IoSession session = entry.getValue();
					Integer id = (Integer)session.getAttribute("terminal_id");//得到采集器编号
						String resultStr = ProtocolUtil.makeCode(id+"", new Date().getTime());
						byte[] bytes = Util.getByte(resultStr);
								synchronized (session) {
									session.write(bytes);
								}
							}
						}catch(Exception e){
						LogFactory.getInstance().saveLog("GB/376.1对时协议发送异常",LogDao.exception);
						}*/
		System.out.println("对时线程");
	}
}
