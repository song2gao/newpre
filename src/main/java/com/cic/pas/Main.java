package com.cic.pas;


import com.cic.pas.application.ChannelManageService;
import com.cic.pas.application.manage.PChannelService;
import com.cic.pas.application.manage.PDBManageService;
import com.cic.pas.application.manage.PSystemConfigService;
import com.cic.pas.thread.DataInsertThread;
import org.apache.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


public class Main {

    private static Logger logger=Logger.getLogger(Main.class);

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
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            e.printStackTrace(new PrintStream(baos));
            String exception = baos.toString();
            logger.error(exception);
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
