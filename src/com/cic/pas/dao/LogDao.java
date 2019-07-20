package com.cic.pas.dao;

import java.util.Date;

import org.springframework.jdbc.core.JdbcTemplate;

import com.cic.pas.common.util.Util;

/**
 * 日志持久化对象
*    
* 项目名称：preAcquistionSystem.3.2   
* 类名称：LogDao   
* 类描述：   
* 创建人：lenovo   
* 创建时间：2014-3-18 下午04:04:19   
* 修改人：lenovo   
* 修改时间：2014-3-18 下午04:04:19   
* 修改备注：   
* @version    
*
 */
public class LogDao {
	public static final int warnning = 400 ;
	public static final int exception = 500 ;
	public static final int operation = 600 ;
	
	public  JdbcTemplate jdbcTemplate = DBConfigDao.jdbcTemplate;
	
	public void saveLog(String inform,int codes ){
		java.sql.Timestamp date = new java.sql.Timestamp(new Date().getTime()); 
		jdbcTemplate.update("insert into poms_operation_system_log (id,osl_bug_time,osl_bug_content)values (?,?,?)", 
								new Object [] {Util.getKey(),date,inform});
	}
	public void saveLog(String inform){
		java.sql.Timestamp date = new java.sql.Timestamp(new Date().getTime()); 
		jdbcTemplate.update("insert into poms_operation_system_log (id,osl_bug_time,osl_bug_content)values (?,?,?)", 
								new Object [] {Util.getKey(),date,inform});
	}
	
	/**
	 * 保存日志信息
	 */
	private void saveLog() {

	}
}
