package com.cic.pas.common.net;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;


/**
 * 
 * 
 */
public class Request implements Serializable {

	private static final long serialVersionUID = 8582340427607041677L;
	/**
	 * 当前A相有功功率(KW)
	 */
	public static final int APHASE_ACTIVEPOWER = 1;
	/**
	 * 当前B相有功功率(KW)
	 */
	public static final int BPHASE_ACTIVEPOWER = 2;
	/**
	 * 当前C相有功功率(KW)
	 */
	public static final int CPHASE_ACTIVEPOWER = 3;
	/**
	 * 当前总无功功率(KW)
	 */
	public static final int TOTAL_NON_REACTIVEPOWER = 5;
	/**
	 * 当前A相无功功率(KW)
	 */
	public static final int APHASE_NON_ACTIVEPOWER = 6;
	/**
	 * 当前B相无功功率(KW)
	 */
	public static final int BPHASE_NON_ACTIVEPOWER = 7;
	/**
	 * 当前C相无功功率(KW)
	 */
	public static final int CPHASE_NON_ACTIVEPOWER = 8;
	/**
	 * 当前总功率因数(%)
	 */
	public static final int TOTLE_POWERFACTOR = 9;
	/**
	 * 当前A相功率因数(%)
	 */
	public static final int APHASE_POWERFACTOR = 10;
	/**
	 * 当前B相功率因数(%)
	 */
	public static final int BPHASE_POWERFACTOR = 11;
	/**
	 * 当前C相功率因数(%)
	 */
	public static final int CPHASE_POWERFACTOR = 12;
	/**
	 * 当前A相电压(V)
	 */
	public static final int APHASE_VOLTAGE = 13;
	/**
	 * 当前B相电压(V)
	 */
	public static final int BPHASE_VOLTAGE = 15;
	/**
	 * 当前C相电压(V)
	 */
	public static final int CPHASE_VOLTAGE = 16;
	/**
	 * 当前A相电流(A)
	 */
	public static final int APHASE_CURRENT = 17;
	/**
	 * 当前B相电流(A)
	 */
	public static final int BPHASE_CURRENT = 18;
	/**
	 * 当前C相电流(A)
	 */
	public static final int CPHASE_CURRENT = 19;
	/**
	 * 当前零序电流(A)
	 */
	public static final int CURRENT_0_CURRENT = 20;
	/**
	 * 得到所有数据
	 */
	public static final int ALLDATA = 21;
	/**
	 * 当前总有功功率
	 */
	public static final int TOTAL_ACTIVEPOWER = 22;
	/**
	 * 当前总视在功率
	 */
	public static final int All_VIEWPOWER = 23;
	/**
	 * 当前A相视在功率
	 */
	public static final int A_VIEWPOWER = 24;
	/**
	 * 当前B相视在功率
	 */
	public static final int B_VIEWPOWER = 25;
	/**
	 * 当前C相视在功率
	 */
	public static final int C_VIEWPOWER = 26;
	/**
	 * 当前水表累积流量
	 */
	public static final int POSITIVE_ACTIVEPOWER_INDICATION = 27;
	/**
	 * 当前线电压Uab
	 */
	public static final int VOLTAGE_Uab = 28;
	/**
	 * 当前线电压Ubc
	 */
	public static final int VOLTAGE_Ubc = 29;
	/**
	 * 当前线电压Uca
	 */
	public static final int VOLTAGE_Uca = 30;
	/**
	 * 正向总有功电量
	 */
	public static final int POSITIVE_TOTAL_ACTIVE_POWER = 31;
	/**
	 * 尖(5byte)
	 */
	public static final int TIP = 32;
	/**
	 * 峰(5byte)
	 */
	public static final int PEAK = 33;
	/**
	 * 平(5byte)
	 */
	public static final int LEVEL =34;
	/**
	 * 谷(5byte)
	 */
	public static final int VALLEY = 35;
	
	/**
	 * 谐波次数N（N≤19）
	 */
	public static final int N = 100;
	/**
	 * A相总谐波电压含有率
	 */
	public static final int Ua = 101;
	/**
	 * A相2次谐波电压含有率
	 */
	public static final int Ua2 = 102;
	
	public static final int Ua3 = 103;
	
	public static final int Ua4 = 104;
	
	public static final int Ua5 = 105;
	
	public static final int Ua6 = 106;
	
	public static final int Ua7 = 107;
	
	public static final int Ua8 = 108;
	
	public static final int Ua9 = 109;
	
	public static final int Ua10 = 110;
	
	public static final int Ua11 = 111;
	
	public static final int Ua12 = 112;
	
	public static final int Ua13 = 113;
	
	public static final int Ua14 = 114;
	
	public static final int Ua15 = 115;
	
	
	/**
	 * B相总谐波电压含有率
	 */
	public static final int Ub = 121;
	/**
	 * B相2次谐波电压含有率
	 */
	public static final int Ub2 = 122;
	
	public static final int Ub3 = 123;
	
	public static final int Ub4 = 124;
	
	public static final int Ub5 = 125;
	
	public static final int Ub6 = 126;
	
	public static final int Ub7 = 127;
	
	public static final int Ub8 = 128;
	
	public static final int Ub9 = 129;
	
	public static final int Ub10 = 130;
	
	public static final int Ub11 = 131;
	
	public static final int Ub12 = 132;
	
	public static final int Ub13 = 133;
	
	public static final int Ub14 = 134;
	
	public static final int Ub15 = 135;
	
	/**
	 * C相总谐波电压含有率
	 */
	public static final int Uc = 141;
	/**
	 * C相2次谐波电压含有率
	 */
	public static final int Uc2 = 142;
	
	public static final int Uc3 = 143;
	
	public static final int Uc4 = 144;
	
	public static final int Uc5 = 145;
	
	public static final int Uc6 = 146;
	
	public static final int Uc7 = 147;
	
	public static final int Uc8 = 148;
	
	public static final int Uc9 = 149;
	
	public static final int Uc10 = 150;
	
	public static final int Uc11 = 151;
	
	public static final int Uc12 = 152;
	
	public static final int Uc13 = 153;
	
	public static final int Uc14 = 154;
	
	public static final int Uc15 = 155;
	
	
	/**
	 * A相总谐波电流含有率
	 */
	public static final int Ia = 201;
	/**
	 * A相2次谐波电流含有率
	 */
	public static final int Ia2 = 202;
	
	public static final int Ia3 = 203;
	
	public static final int Ia4 = 204;
	
	public static final int Ia5 = 205;
	
	public static final int Ia6 = 206;
	
	public static final int Ia7 = 207;
	
	public static final int Ia8 = 208;
	
	public static final int Ia9 = 209;
	
	public static final int Ia10 = 210;
	
	public static final int Ia11 = 211;
	
	public static final int Ia12 = 212;
	
	public static final int Ia13 = 213;
	
	public static final int Ia14 = 214;
	
	public static final int Ia15 = 215;
	
	
	/**
	 * B相总谐波电压含有率
	 */
	public static final int Ib = 221;
	/**
	 * B相2次谐波电压含有率
	 */
	public static final int Ib2 = 222;
	
	public static final int Ib3 = 223;
	
	public static final int Ib4 = 224;
	
	public static final int Ib5 = 225;
	
	public static final int Ib6 = 226;
	
	public static final int Ib7 = 227;
	
	public static final int Ib8 = 228;
	
	public static final int Ib9 = 229;
	
	public static final int Ib10 = 230;
	
	public static final int Ib11 = 231;
	
	public static final int Ib12 = 232;
	
	public static final int Ib13 = 233;
	
	public static final int Ib14 = 234;
	
	public static final int Ib15 = 235;
	

	public static final int Ic = 241;
	
	public static final int Ic2 = 242;
	
	public static final int Ic3 = 243;
	
	public static final int Ic4 = 244;
	
	public static final int Ic5 = 245;
	
	public static final int Ic6 = 246;
	
	public static final int Ic7 = 247;
	
	public static final int Ic8 = 248;
	
	public static final int Ic9 = 249;
	
	public static final int Ic10 = 250;
	
	public static final int Ic11 = 251;
	
	public static final int Ic12 = 252;
	
	public static final int Ic13 = 253;
	
	public static final int Ic14 = 254;
	
	public static final int Ic15 = 255;
	/*history F101  测量点正向有功总电能示值曲线*/
	public static final int HISTORY_POSITIVE_TOTAL_ACTIVE_POWER  = 1031;
	/*history F100 测量点反向无功总电能量曲线*/
	public static final int INVERSE_REACTIVE_TOTAL_ELECTRICAL_ENERGY  = 1030;
	//F3 逐个否认
	public static final int isNo = -2 ;
	//F1全部确认
	public static final int allC = -3 ;

	
	
	private int type;

	private Map<String, Object> parameters;

	public Request(int type) {
		this.type = type;
		parameters = new HashMap<String, Object>();
		init();
	}

	public Request() {
		this.type = ALLDATA;
		parameters = new HashMap<String, Object>();
		init();
	}
	/**
	 * DateFormat dateFormatterChina = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM);
	 * 格式化输出 TimeZone timeZoneChina = TimeZone.getTimeZone("Asia/Shanghai");
	 * 获取时区 这句加上，很关键。 dateFormatterChina.setTimeZone(timeZoneChina);
	 * 设置系统时区 Date curDate = new Date();//获取系统时间
	*/
	private void init() {
		DateFormat dateFormatterChina = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM);
		TimeZone timeZoneChina = TimeZone.getTimeZone("Asia/Shanghai");
		dateFormatterChina.setTimeZone(timeZoneChina);
		parameters.put("date", new Date());
	}
	public void addParameter(String name, Object value) {
		parameters.put(name, value);
	}

	public Object getParameter(String name) {
		return parameters.get(name);
	}

	public Map<String, Object> getparameters() {
		return parameters;
	}

	public int getType() {
		return this.type;
	}
}
