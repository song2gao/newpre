package com.cic.pas.dao;

public class LogFactory {
    
	 private static LogDao log = new LogDao ();
    
     private LogFactory (){}
     
     public static LogDao getInstance(){
    	 
    	 return log;
    	 
     }
}
