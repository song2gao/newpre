package com.cic.pas.process;


public class OperationFactory {

	 private static Operation operation = new Operation ();
	    
     private OperationFactory (){}
     
     public static Operation getInstance(){
    	 
    	 return operation;
    	 
     }
}
