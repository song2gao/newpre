package com.cic.pas.common.net;

import java.io.Serializable;

public class ReturnMessage implements Serializable{
	/**
	 * 返回对象
	 */
	private static final long serialVersionUID = -6351922818936502492L;
	int status = 0; // 0 失败 1成功
	Object object ;
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
}
