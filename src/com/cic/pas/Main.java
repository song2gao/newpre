package com.cic.pas;


import com.cic.pas.application.ChannelManageService;
import com.cic.pas.application.manage.PChannelService;
import com.cic.pas.application.manage.PDBManageService;
import com.cic.pas.application.manage.PRunningStatusService;
import com.cic.pas.application.manage.PSystemConfigService;
import com.cic.pas.thread.DataInsertThread;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		 PRunningStatusService service = new PRunningStatusService();
		 Thread thread = new Thread(service);
		 thread.start();
		 /* ArrayList<Channel> list = new ArrayList<Channel>();
		 Channel channel1 = new Channel();
		 channel1.setCha_addressPort("127.0.0.1:7777");
		 channel1.setCha_detectionMark("1");
		 list.add(channel1);
		 Channel channel2 = new Channel();
		 channel2.setCha_addressPort("127.0.0.1:8888");
		 channel2.setCha_detectionMark("1");
		 list.add(channel2);
				
		 Channel channel3 = new Channel();
		 channel3.setCha_addressPort("127.0.0.1:9999");
		 channel3.setCha_detectionMark("1");
		 list.add(channel3);*/
		 try {
			 new PDBManageService();
			 new PSystemConfigService();
			 PChannelService pChannelService = new ChannelManageService();
			 pChannelService.openChannel();
		 }catch (Exception e){
		 	e.printStackTrace();
		 }
		 //new PTaskManageService();
		 //new GetDataThread().start();
		Thread datainsert= new DataInsertThread();
		datainsert.setName("datainsert");
		datainsert.start();
//		DataCenterThread centerThread=new DataCenterThread();
//		centerThread.setName("DataCenter");
//		centerThread.start();
//		Thread coolData=new GetCoolDataThread();
//		coolData.setName("cooldata");
//		coolData.start();
		// new StandardServer();
//		 PreCurveRecordMapper service=new PreCurveRecordMapperImpl();
//		 List<PreCurveRecord> list=service.find();
//		 System.out.println(BussinessConfig.terminalList.size());

	}
}
