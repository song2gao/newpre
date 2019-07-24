package com.cic.socket;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import com.cic.domain.PomsCalculateTerminalDevice;
import com.cic.pas.common.bean.MeterDevice;
import com.cic.pas.common.bean.PointDevice;
import com.cic.pas.common.net.ReturnMessage;
import com.cic.pas.dao.BussinessConfig;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//请求前置机获取电表信息
		String ip = "127.0.0.1";
		int port = 7005;
		SenderFactory sf = new SenderFactory(ip,port);
		//List<PomsCalculateTerminalDevice> listid = failureToRecordDao.findJliangTabbyId(bId);
		SendingMessage sm = sf.getMeterDeviceMeterDataSender(BussinessConfig.terminalList.get(0).getMeterList());
		List<ReturnMessage> lists = new ArrayList<ReturnMessage>();
		//List<PomsCodeManagement> listCode = new ArrayList<PomsCodeManagement>();
		//List<TestFormatDomain> lijukkkk = new ArrayList<TestFormatDomain>();
		lists = sm.response();
		MeterDevice md = null;
		//TestFormatDomain tade = new TestFormatDomain();
		//List<TestFormatDomain> ks = new ArrayList<TestFormatDomain>();
		for(ReturnMessage rm:lists){
			md = (MeterDevice) rm.getObject();
			//(@)前置机采数
			if (md == null){
				//tade.setSe("该计量终端故障或离线");
			}else {
				//tade.setSe("该计量终端正常");
				List<PointDevice> listpd = md.getPointDevice();
				for (int i = 0; i < listpd.size(); i++) {
					String code = md.getPointDevice().get(i).getCode();
					//listCode = meaTermonDao.findCodeMana(code);
					//String cmname = listCode.get(0).getCmName();
					
					BigDecimal values = listpd.get(i).getValue();
					if (values == null) {
						values = new BigDecimal(0);
					}
					//TestFormatDomain tFdmd = new TestFormatDomain();
					//tFdmd.setSa(cmname);
					//tFdmd.setDa(values);
					//tFdmd.setIa(i+1);
					//tFdmd.setSb(listid.get(0).getCdtMeasureLoop());//回路名称
					//tFdmd.setSc(listid.get(0).getPomsAssembledTerminalDevice().getAsstdNames());//用能单位名称
					//tFdmd.setSd(listid.get(0).getCdtTerminalName());	//终端名称
					//lijukkkk.add(i,tFdmd);
					//ks.add(i, tFdmd);
				}
			}
		}

	}

}
