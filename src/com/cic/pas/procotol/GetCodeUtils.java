package com.cic.pas.procotol;

import java.util.List;

import com.cic.pas.common.bean.MeterDevice;
import com.cic.pas.common.bean.TerminalDevice;
import com.cic.pas.common.util.CRC16M;
import com.cic.pas.common.util.Util;


public class GetCodeUtils  {
	
	public String makeCodecByMeter(List<TerminalDevice> list){
		//List<TerminalDevice> list=new Gson().fromJson(str,List<TerminalDevice>);
		for(TerminalDevice t : list){
			List<MeterDevice> mds=t.getMeterList();
			for(MeterDevice md : mds){
				md.setStatus(3);
				String str=Util.fill(Integer.toHexString(md.getAddress()), 2, '0');
				str=CRC16M.getBufHexStr(CRC16M.getSendBuf(str));
				md.setFnCodeString(str);
			}
		}
		//return new Gson().toJson(list);
		return "";
	}

}
