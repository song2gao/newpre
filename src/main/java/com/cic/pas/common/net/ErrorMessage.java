package com.cic.pas.common.net;

public class ErrorMessage implements java.io.Serializable{
	
	
	
	private static final long serialVersionUID=7714918001204577L;
	
	
	public static final int CODE_UPDATE = 1101 ;
	public static final int CODE_CREATE = 1102 ;
	public static final int CODE_DELETE = 1103 ;
	public static final int CODE_DISABLE = 1104 ;
	/**
	 * 错误编码
	 */
	private int code;
	
	/**
	 * 错误名
	 */
	private String name;
	
	/**
	 * 错误消息
	 */
	private String message;
	
	
	/**
	 * 错误详情
	 */
	private String details;
	
	
	public ErrorMessage(int code,String name,String mesg,String details){
		this.code=code;
		this.name=name;
		this.message=mesg;
		this.details=details;
	}
	
	public ErrorMessage(String message){
		this.message=message;
	}
	
	public ErrorMessage(String mesg,String details){
		this.message=mesg;
		this.details=details;
	}
	
	
	public ErrorMessage(){
		
	}
	

	public String getDetails() {
		return details;
	}


	public void setDetails(String details) {
		this.details = details;
	}


	public int getCode() {
		return code;
	}



	public void setCode(int code) {
		this.code = code;
	}



	public String getMessage() {
		return message;
	}



	public void setMessage(String message) {
		this.message = message;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}

	public String getSimpleString(){
		return null;
	}

	public String toString(){
		String str=
			"系统编码："+code+"\n"+
			"信息名称:"+name+"\n"+
			"信息描述:"+message+"\n"+
			"详情信息:"+details+"\n";
		
		return str;
		
	}
	

}
