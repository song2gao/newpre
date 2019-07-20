package com.cic.pas.common.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.mina.filter.codec.ProtocolEncoder;

import testGson.Person;

import com.cic.pas.common.bean.MeterDevice;
import com.cic.pas.common.bean.TerminalDevice;

@SuppressWarnings(value = { "all" })
public class ProtocolTool {
	
	public static Class obj;
	
	static {
		obj=ClassUtils.getClass("com.cic.pas.procotol.GetCodeUtils");
	}

	/**
	 * 从协议中获取实时数据code码
	 * @param list
	 * @return
	 */
	public static List<TerminalDevice> getTerminalList(List<TerminalDevice> tlist){
		
		//String str= gson.toJson(tlist);
		//System.out.println(tlist);
		Method methodFromClass = ClassUtils.getMethodFromClass(obj, "makeCodecByMeter",List.class);
		
		Object invokeMethod = null;
		try {
			invokeMethod = ClassUtils.invokeMethod(obj.newInstance(), methodFromClass, tlist);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("组成的报文返回完毕："+invokeMethod.toString());
		
		List<TerminalDevice> list = new ArrayList<TerminalDevice>();//gson.fromJson(invokeMethod.toString(), new TypeToken<List<TerminalDevice>>(){}.getType());
		
	/*	for (int i = 0; i < list.size(); i++) {
			TerminalDevice t = list.get(i);
			for (int j = 0; j < t.getMeterList().size(); j++) {
				MeterDevice m =t.getMeterList().get(j);
				System.out.println("表计名称："+m.getName()+"表计编码："+m.getCode()+"表计code:"+m.getFnsOne()+"组织报文"+m.getFnCodeString());
				
			}
			
		}*/
		
		return list;
	}
	
	/**
	 * 从协议中获取曲线历史日冻结报文
	 * @param list
	 * @param time
	 * @param a
	 * @param b
	 * @return
	 */
	public static List<TerminalDevice> getTerminalList(List<TerminalDevice> list ,long time, Integer a, Integer b){
		
//		String listForGson =gson.toJson(list);
//		Method methodFromClass = ClassUtils.getMethodFromClass(obj, "makeCodecHistory",String.class,long.class,Integer.class,Integer.class);
//		
//		Object invokeMethod = null;
//		List<TerminalDevice> returnlist=null;
//		try {
//			invokeMethod = ClassUtils.invokeMethod(obj.newInstance(), methodFromClass, listForGson,time,a,b);
//			
//			returnlist = gson.fromJson(invokeMethod.toString(), new TypeToken<List<TerminalDevice>>(){}.getType());
//			
//		} catch (InstantiationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		return returnlist;
		return new ArrayList<TerminalDevice>();
	}
	/**
	 * 从协议中获取历史日冻结报文
	 * @param list
	 * @param time
	 * @return
	 */
	public static List<TerminalDevice> getCodeForDay(List<TerminalDevice> list,long time){
		
//		String listForJson= gson.toJson(list);
//		
//		Method methodFromClass = ClassUtils.getMethodFromClass(obj, "makeCodecHistoryForDaysFreezing",String.class,long.class);
//		
//		Object invokeMethod = null;
//		List<TerminalDevice> returnList=null;
//		try {
//			invokeMethod = ClassUtils.invokeMethod(obj.newInstance(), methodFromClass, listForJson,time);
//			
//			returnList = gson.fromJson(invokeMethod.toString(), new TypeToken<List<TerminalDevice>>(){}.getType());
//		} catch (InstantiationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		return returnList;
		return new ArrayList<TerminalDevice>();
	}

	/**
	 * 从协议中获取历史月冻结报文
	 * @param list
	 * @param time
	 * @return
	 */
	public static List<TerminalDevice> getCodeForMonth(List<TerminalDevice> list,long time){
		
//		String listForGson= gson.toJson(list);
//		
//		Method methodFromClass = ClassUtils.getMethodFromClass(obj, "makeCodecHistoryForMonthsFreezing",String.class,long.class);
//		
//		Object invokeMethod = null;
//		List<TerminalDevice> returnList=null;
//		try {
//			invokeMethod = ClassUtils.invokeMethod(obj.newInstance(), methodFromClass, listForGson,time);
//			
//			returnList = gson.fromJson(invokeMethod.toString(), new TypeToken<List<TerminalDevice>>(){}.getType());
//		} catch (InstantiationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		return returnList;
		return new ArrayList<TerminalDevice>();
	}
	
	

/*	public static void getGson(List<Person> plist){
		
		
		Class obj=ClassUtils.getClass("com.cee.testGson.GsonMain");  
		
		Gson gson =new Gson();
		String  str = gson.toJson(plist);
		System.out.println("传入前："+str);
		
		Method methodFromClass = ClassUtils.getMethodFromClass(obj, "test",String.class);
		
		Object invokeMethod = null;
		try {
			invokeMethod = ClassUtils.invokeMethod(obj.newInstance(), methodFromClass,str);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("返回："+invokeMethod.toString());
		
	}*/
	
	
	
	
}
