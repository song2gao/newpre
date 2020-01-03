package com.cic.pas;


import com.cic.pas.application.ChannelManageService;
import com.cic.pas.application.manage.PChannelService;
import com.cic.pas.application.manage.PDBManageService;
import com.cic.pas.application.manage.PSystemConfigService;
import com.cic.pas.thread.DataInsertThread;


public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) {
//        PRunningStatusService service = new PRunningStatusService();
//        Thread thread = new Thread(service);
//        thread.start();
        try {
            new PDBManageService();
            new PSystemConfigService();
            PChannelService pChannelService = new ChannelManageService();
            pChannelService.openChannel();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //new PTaskManageService();
        Thread datainsert = new DataInsertThread();
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
