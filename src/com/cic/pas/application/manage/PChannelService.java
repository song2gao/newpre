/**  
 * @Project: PreAcquisitionSystem3.0

 * @Title: ChanelInterface.java

 * @Package com.cic.pas.base.Interface

 * @Description: TODO

 * @author liuya7711@126.com

 * @date 2014-2-20 下午01:57:48

 * @Copyright: 2014 www.cic.cn

 * @version V3.0  
 */
package com.cic.pas.application.manage;

import org.apache.mina.transport.socket.SocketAcceptor;

import com.cic.pas.common.base.impl.PBaseClass;

/**
 * @ClassName: ChanelInterface
 * @Description: 通道各服务的管理类
 * @author lenovo
 * @date 2014-2-20 下午01:57:48
 * 
 */
public abstract class PChannelService  extends PBaseClass{
	 
	
	/**
	 * 从数据库访问服务中读取通道列表，并启动标识为1的通道
	 */
	public void openChannel(){}
	
	/** 
	 * @Title: close 
	 * @Description: TODO(ChannelManageService 中的关闭通道) 
	 * @param     设定文件 
	 * @return void    返回类型 
	 * @throws 
	 */
	public void close(SocketAcceptor server){}
	
	public void connectServer(){}
}
