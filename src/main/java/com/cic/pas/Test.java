//package com.cic.pas;
//
//import java.math.BigDecimal;
//import java.util.Arrays;
//import java.util.List;
//
//import com.cic.domain.AutoOpenStop;
//import com.cic.pas.common.bean.TerminalDevice;
//import com.cic.pas.common.util.Util;
//
//import net.sf.json.JSONObject;
//
//public class Test {
//
//	/**
//	 * @param args
//	 */
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		int[] codes=new int[]{371,372,373,374,375,376,377,378,379,380,381,382,383,384,385,386,387,388,389,390,391,392,393,448,449,450,451,452,453,454,455,456,457,458,459,460,461,462,463,464,465,466,467,468,469,470,471,472,473,474,475,476,477,478,479,480,481,482,483,484,485,502,503,504,505,506,507,508,509,510,511,512,513,514,515,918,919,920,921,922,923,858,859};
//		for(int code:codes){
//			System.out.println("insert into poms_device_measur_point(device_id,MMP_CODES,MMP_NAMES,MMP_FORMULAR,MMP_SET_VALUE)" +
//					"select "+code+",MMP_CODES,MMP_NAMES,MMP_FORMULAR,MMP_SET_VALUE from poms_device_measur_point where DEVICE_ID in(394);");
//		}
////		AutoOpenStop autoOpenStop=new AutoOpenStop();
////		autoOpenStop.setStarttime("08:00:00");
////		autoOpenStop.setStoptime("17:00:00");
////		autoOpenStop.setDates(new String[]{"1","2","3","4","5","6"});
////		JSONObject json=JSONObject.fromObject(autoOpenStop);
////
////		System.out.println(json.toString());
////		String str="";
////		for(int i=1;i<97;i++){
////			str+="point"+i+"+";
////		}
////		System.out.println(str);
//	}
//	public static void modbusWTCP(String ip, int port, int slaveId, int start,
//			short[] values) {
//		ModbusFactory modbusFactory = new ModbusFactory();
//		// 设备ModbusTCP的Ip与端口，如果不设定端口则默认为502
//		IpParameters params = new IpParameters();
//		params.setHost(ip);
//		params.setEncapsulated(true);
//		if (502 != port) {
//			params.setPort(port);
//		}// 设置端口，默认502
//		ModbusMaster tcpMaster = null;
//		// 参数1：IP和端口信息 参数2：保持连接激活
//		tcpMaster = modbusFactory.createTcpMaster(params, true);
//		try {
//			tcpMaster.init();
//			System.out.println("===============" + 1111111);
//		} catch (ModbusInitException e) {
//			// System.out.println("11111111111111=="+"此处出现问题了啊!");
//			// 如果出现了通信异常信息，则保存到数据库中
//			// CommunityExceptionRecord cer = new CommunityExceptionRecord();
//			// cer.setDate(new Date());
//			// cer.setIp(ip);
//			// cer.setRemark(bgName+"出现连接异常");
//			// batteryGroupRecordService.saveCommunityException(cer);
//		}
//		try {
//			WriteRegistersRequest request = new WriteRegistersRequest(slaveId,
//					start, values);
//			WriteRegistersResponse response = (WriteRegistersResponse) tcpMaster
//					.send(request);
//			if (response.isException())
//				System.out.println("Exception response: message="
//						+ response.getExceptionMessage());
//			else
//				System.out.println("Success");
//		} catch (ModbusTransportException e) {
//			e.printStackTrace();
//		}
//	}
//
//	public static ByteQueue modbusTCP(String ip, int port, int start,
//			int readLenth) {
//		ModbusFactory modbusFactory = new ModbusFactory();
//		// 设备ModbusTCP的Ip与端口，如果不设定端口则默认为502
//		IpParameters params = new IpParameters();
//		params.setHost(ip);
//		if (502 != port) {
//			params.setPort(port);
//		}// 设置端口，默认502
//		ModbusMaster tcpMaster = null;
//		// tcpMaster = modbusFactory.createTcpMaster(params, true);
//		tcpMaster = modbusFactory.createTcpMaster(params, true);
//		try {
//			tcpMaster.init();
//			System.out.println("===============" + 1111111);
//		} catch (ModbusInitException e) {
//			return null;
//		}
//		ModbusRequest modbusRequest = null;
//		try {
//			modbusRequest = new ReadHoldingRegistersRequest(1, start, readLenth);// 功能码03
//		} catch (ModbusTransportException e) {
//			e.printStackTrace();
//		}
//		ModbusResponse modbusResponse = null;
//		try {
//			modbusResponse = tcpMaster.send(modbusRequest);
//		} catch (ModbusTransportException e) {
//			e.printStackTrace();
//		}
//		ByteQueue byteQueue = new ByteQueue(12);
//		modbusResponse.write(byteQueue);
//		System.out.println("功能码:" + modbusRequest.getFunctionCode());
//		System.out.println("从站地址:" + modbusRequest.getSlaveId());
//		System.out.println("收到的响应信息大小:" + byteQueue.size());
//		System.out.println("收到的响应信息值:" + byteQueue);
//		return byteQueue;
//	}
//
//	public static ByteQueue modbusTCPServerMaster(int port,
//			List<TerminalDevice> list) {
//		ModbusFactory modbusFactory = new ModbusFactory();
//		ModbusMaster tcpMaster = null;
//		// tcpMaster = modbusFactory.createTcpMaster(params, true);
//		//tcpMaster = modbusFactory.createTcpServerMaster(4001, true);
//		tcpMaster.setTimeout(10000);
//		try {
//			tcpMaster.init();
//			System.out.println("===============" + 1111111);
//		} catch (ModbusInitException e) {
//			return null;
//		}
//
//		return null;
//	}
//
//	public static ByteQueue modbusRtu(ModbusMaster rtuMaster, int function,
//			int start, int readLenth) {
//		try {
//			rtuMaster.init();
//			System.out.println("===============" + 1111111);
//		} catch (ModbusInitException e) {
//			e.printStackTrace();
//		}
//		ModbusRequest modbusRequest = null;
//		try {
//			modbusRequest = new ReadHoldingRegistersRequest(1, start, readLenth);// 功能码03
//		} catch (ModbusTransportException e) {
//			e.printStackTrace();
//		}
//		ReadHoldingRegistersResponse modbusResponse = null;
//		try {
//			modbusResponse = (ReadHoldingRegistersResponse) rtuMaster
//					.send(modbusRequest);
//		} catch (ModbusTransportException e) {
//			e.printStackTrace();
//		}
//		System.out.println(Arrays.toString(modbusResponse.getShortData()));
//		return null;
//		// ByteQueue byteQueue = new ByteQueue(12);
//		// modbusResponse.write(byteQueue);
//		// System.out.println("功能码:" + modbusRequest.getFunctionCode());
//		// System.out.println("从站地址:" + modbusRequest.getSlaveId());
//		// System.out.println("收到的响应信息大小:" + byteQueue.size());
//		// System.out.println("收到的响应信息值:" + byteQueue);
//		// return byteQueue;
//	}
//	public static boolean[] readCoils(ModbusMaster master, int slaveId,
//			int start, int len) {
//		try {
//			ReadCoilsRequest request = new ReadCoilsRequest(slaveId, start, len);
//			ReadCoilsResponse response = (ReadCoilsResponse) master
//					.send(request);
//			if (response == null) {
//				return null;
//			} else {
//				System.out.println(Arrays.toString(response.getShortData()));
//				return response.getBooleanData();
//			}
//		} catch (ModbusTransportException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	public static boolean[] readDiscreteInputs(ModbusMaster master,
//			int slaveId, int start, int len) {
//		// 读取外围设备输入的开关量
//		try {
//			ReadDiscreteInputsRequest request = new ReadDiscreteInputsRequest(
//					slaveId, start, len);
//			ReadDiscreteInputsResponse response = (ReadDiscreteInputsResponse) master
//					.send(request);
//			if (response == null) {
//				return null;
//			} else {
//				return response.getBooleanData();
//			}
//		} catch (ModbusTransportException e) {
//			e.printStackTrace();
//		}
//		return null;
//
//	}
//
//	public static byte[] readHoldingRegisters(ModbusMaster master,
//			int slaveId, int start, int len) {
//		// 读取保持寄存器数据
//		try {
//			ReadHoldingRegistersRequest request = new ReadHoldingRegistersRequest(
//					slaveId, start, len);
//			ReadHoldingRegistersResponse response = (ReadHoldingRegistersResponse) master
//					.send(request);
//			if (response == null) {
//				return null;
//			} else {
//				return response.getData();
//			}
//		} catch (ModbusTransportException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	public static byte[] readInputRegisters(ModbusMaster master, int slaveId,
//			int start, int len) {
//		// 读取外围设备输入的数据
//		try {
//			ReadInputRegistersRequest request = new ReadInputRegistersRequest(
//					slaveId, start, len);
//			ReadInputRegistersResponse response = (ReadInputRegistersResponse) master
//					.send(request);
//			if (response == null) {
//				return null;
//			} else {
//				return response.getData();
//			}
//		} catch (ModbusTransportException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//}
