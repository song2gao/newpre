/**
 * 
 */
package com.cic.pas.common.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 计量表
 */
public class MeterDevice implements Serializable {

	private static final long serialVersionUID = 6528019442861692363L;
	/** 表计主键 */
	private String id;
	/** 表计编码 */
	private String code;
	/** 表计类型 */
	private String type;
	/** 表计地址 */
	private int linetype;

    public String getLastCollectDate() {
        return lastCollectDate;
    }

    public void setLastCollectDate(String lastCollectDate) {
        this.lastCollectDate = lastCollectDate;
    }

    /**
     * 上次采集时间
     */
	private String lastCollectDate;
	public int getLinetype() {
		return linetype;
	}

	public void setLinetype(int linetype) {
		this.linetype = linetype;
	}

	private String pcpcEnergyType;

	public String getPcpcEnergyType() {
		return pcpcEnergyType;
	}

	public void setPcpcEnergyType(String pcpcEnergyType) {
		this.pcpcEnergyType = pcpcEnergyType;
	}

	private int address;
	private double ct;
	private int pt;
	//电压小数位
	private int uPoint=0;
	//电流小数位
	private int iPoint=0;

	public int getuPoint() {
		return uPoint;
	}

	public void setuPoint(int uPoint) {
		this.uPoint = uPoint;
	}

	public int getiPoint() {
		return iPoint;
	}

	public void setiPoint(int iPoint) {
		this.iPoint = iPoint;
	}

	public int getpPoint() {
		return pPoint;
	}

	public void setpPoint(int pPoint) {
		this.pPoint = pPoint;
	}

	//功率小数位
	private int pPoint=0;

	public double getCt() {
		return ct;
	}

	public void setCt(double ct) {
		this.ct = ct;
	}

	public int getPt() {
		return pt;
	}

	public void setPt(int pt) {
		this.pt = pt;
	}

	public int getAddress() {
		return address;
	}

	public void setAddress(int address) {
		this.address = address;
	}

	/** 表号Pn */
	private String mark;
	/** 数据异常回否认帧 */
	private String denied_that_frame;
	/** 表计状态 */
	private int status=0;
	/** 计量设备所计量的回路名称 */
	private String measure_loop;
	/** 表计更换数量 量程之和 */
	private String cdt_init_value;
	/** 计量设备名称 */
	private String name;
	/** 计量终端生产厂家 */
	private String production;
	/** 计量终端出厂编号 */
	private String production_code;
	/** 计量表量程 */
	private String scale;
	/** 是否是虚表 */
	private int isinvented;
	/** 计量公式 */
	private String cdt_formular;
	/** 计量测点集合 */
	private List<PointDevice> pointDevice;
	/** 1类数据Fn用逗号隔开 */
	private String FnsOne;
	/** 2类数 曲线 据Fn用逗号隔开 */
	private String FnsTwo;
	/** 日冻结Fn */
	private String daysFreezingFunCode;
	/** 月冻结Fn */
	private String monthssFreezingFunCode;
	/** 采集终端设备外键 */
	private String cdt_assembleid;
	/** 曲线历史报文按照255 */
	private List<String> fnCodes = new ArrayList<String>();
	/** 日冻结报文 */
	private List<String> fnCodesForDayFreezing = new ArrayList<String>();
	/** 月冻结报文 */
	private List<String> fnCodesForMonthsFreezing = new ArrayList<String>();
	/** 报文按表 */
	private String fnCodeString;
	/** 历史数据是否发过一次 */
	private int time;
	/** 是否发实时报文标志 */
	private boolean flag;
	/**寄存器地址递增系数*/

	private int increase=0;
	public int getIncrease() {
		return increase;
	}

	public void setIncrease(int increase) {
		this.increase = increase;
	}
	private int operation_status = 0;

	public static final int OPERATE_STATUS_UPDATE = 1;

	public static final int OPERATE_STATUS_DELETE = 2;

	public static final int OPERATE_STATUS_CREATE = 3;

	public static final int SELF_STATUS_DISABLE = 4;
	public static final int SELF_STATUS_NORMAL = 3;
	public static final int SELF_STATUS_TRIAL = 1;

	/** 是否测试 */
	private int isTrial = 0;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMark() {
		return this.mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "id=" + id + ";" + "type=" + type + ";" + "mark=" + mark;
	}

	public List<PointDevice> getPointDevice() {
		return pointDevice;
	}

	public void setPointDevice(List<PointDevice> pointDevice) {
		this.pointDevice = pointDevice;
	}

	public String getDenied_that_frame() {
		return denied_that_frame;
	}

	public void setDenied_that_frame(String deniedThatFrame) {
		denied_that_frame = deniedThatFrame;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMeasure_loop() {
		return measure_loop;
	}

	public void setMeasure_loop(String measureLoop) {
		measure_loop = measureLoop;
	}

	public String getCdt_init_value() {
		return cdt_init_value;
	}

	public void setCdt_init_value(String cdtInitValue) {
		cdt_init_value = cdtInitValue;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProduction() {
		return production;
	}

	public void setProduction(String production) {
		this.production = production;
	}

	public String getProduction_code() {
		return production_code;
	}

	public void setProduction_code(String productionCode) {
		production_code = productionCode;
	}

	public String getScale() {
		return scale;
	}

	public void setScale(String scale) {
		this.scale = scale;
	}
    /**
     * create by: 高嵩
     * description: 是否为虚拟表  0 实表需采集  1 实表非直接采集 2 虚拟表需采集
     * create time: 2019/12/12 14:13
     * @params
     * @return
     */
	public int getIsinvented() {
		return isinvented;
	}

	public void setIsinvented(int isinvented) {
		this.isinvented = isinvented;
	}

	public String getCdt_formular() {
		return cdt_formular;
	}

	public void setCdt_formular(String cdtFormular) {
		cdt_formular = cdtFormular;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCdt_assembleid() {
		return cdt_assembleid;
	}

	public void setCdt_assembleid(String cdtAssembleid) {
		cdt_assembleid = cdtAssembleid;
	}

	public int getOperation_status() {
		return operation_status;
	}

	public void setOperation_status(int operationStatus) {
		operation_status = operationStatus;
	}

	public List<String> getFnCodes() {
		return fnCodes;
	}

	public void setFnCodes(List<String> fnCodes) {
		this.fnCodes = fnCodes;
	}

	public String getFnCodeString() {
		return fnCodeString;
	}

	public void setFnCodeString(String fnCodeString) {
		this.fnCodeString = fnCodeString;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public String getFnsTwo() {
		return FnsTwo;
	}

	public void setFnsTwo(String fnsTwo) {
		FnsTwo = fnsTwo;
	}

	public String getFnsOne() {
		return FnsOne;
	}

	public void setFnsOne(String fnsOne) {
		FnsOne = fnsOne;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getDaysFreezingFunCode() {
		return daysFreezingFunCode;
	}

	public void setDaysFreezingFunCode(String daysFreezingFunCode) {
		this.daysFreezingFunCode = daysFreezingFunCode;
	}

	public List<String> getFnCodesForDayFreezing() {
		return fnCodesForDayFreezing;
	}

	public void setFnCodesForDayFreezing(List<String> fnCodesForDayFreezing) {
		this.fnCodesForDayFreezing = fnCodesForDayFreezing;
	}

	public String getMonthssFreezingFunCode() {
		return monthssFreezingFunCode;
	}

	public void setMonthssFreezingFunCode(String monthssFreezingFunCode) {
		this.monthssFreezingFunCode = monthssFreezingFunCode;
	}

	public List<String> getFnCodesForMonthsFreezing() {
		return fnCodesForMonthsFreezing;
	}

	public void setFnCodesForMonthsFreezing(
			List<String> fnCodesForMonthsFreezing) {
		this.fnCodesForMonthsFreezing = fnCodesForMonthsFreezing;
	}

	public int getIsTrial() {
		return isTrial;
	}

	public void setIsTrial(int isTrial) {
		this.isTrial = isTrial;
	}

}
