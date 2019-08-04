package com.cic.pas.common.net;

import java.io.Serializable;

/**
 * 外部接口命令 类
*    
* 项目名称：preAcquistionSystem.3.2   
* 类名称：Command   
* 类描述：   
* 创建人：lenovo   
* 创建时间：2014-3-12 下午02:56:25   
* 修改人：lenovo   
* 修改时间：2014-3-12 下午02:56:25   
* 修改备注：   
* @version    
*
 */

public enum Command implements Serializable{
	PreSystemStatus , //前置机状态 
	PreSystemStatusDetails ,//前置机详细信息
	CollectStatus,
	CollectStatusDetails ,
	MeterStatus, //表计状态
	MeterStatusDetails,//
	MeterData , //表数据
	MeterDataByCtdCode,//根据表计编码得到表计数据
	SystemData,//得到用能系统数据
	Disable ,   //
	Normal , 
	Trial , 
	Create , //新增表计
	Update , //表计更新
	Delete ,//停用、删除
	HistoryDataByMeter , 
	HistoryDataByCollect,
	ChannelOpen,//开启通道
	ChannelClose, //关闭通道
	MeterDataCurved,//曲线
	UpdatePrePointSet//修改前置机测点上下限
	
	
}
