package com.cic.pas.process;

public class ProcesserFactory {
    
	 private static Processer processer = new Processer ();
    
     private ProcesserFactory (){}
     
     public static Processer getInstance(){
    	 
    	 return processer;
    	 
     }
}
