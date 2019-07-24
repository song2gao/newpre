package com.cic.pas.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import com.cic.pas.common.bean.MeterDevice;
import com.cic.pas.common.bean.PointDevice;
import com.cic.pas.common.bean.TerminalDevice;
import com.cic.pas.dao.BussinessConfig;
import com.cic.pas.service.ServerContext;



/**
 * @author Administrator
 * 
 */
public final class LogUtil {

	public static FileOutputStream error;

	public static FileOutputStream operation;

	public static FileOutputStream bufferData;

	public static byte[] b = System.getProperty("line.separator").getBytes();

	
	public static void openLog() {
		try {
			Date date = new Date();
			String dataString = date.toLocaleString().split(" ")[0];
			String errorFileStr = ServerContext.getErrorLog().replace("*",dataString);
			String root = System.getProperty("user.dir");
			File build = new File(root).getParentFile();
			File errFile = new File(build,errorFileStr);
			if (!errFile.exists()) {
				errFile.createNewFile();
			}
			String errorStr = errFile.getPath();
			error = new FileOutputStream(new File(errorStr));

			String operationFileStr = ServerContext.getOperationLog().replace("*", dataString);

			File operationFile = new File(build,operationFileStr);

			if (!operationFile.exists()) {
				operationFile.createNewFile();
			}
			String operationStr = operationFile.getPath();
			operation = new FileOutputStream(new File(operationStr));
			
			String bufferFileStr = ServerContext.getBufferDataFile().replace("*", dataString);
			File bufferFile = new File(build,bufferFileStr);
			if (!bufferFile.exists()) {
				bufferFile.createNewFile();
			}
			String bufferDataStr = bufferFile.getPath();
			bufferData = new FileOutputStream(new File(bufferDataStr));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized static final void writeError(String str) {
		try {
			Date date = new Date();
			error.write(date.toString().getBytes());
			error.write(":".getBytes());
			error.write(str.getBytes());
			error.write(b);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public synchronized static final void writeOperation(String str) {
		try {
			Date date = new Date();
			operation.write(date.toString().getBytes());
			operation.write(":".getBytes());
			operation.write(str.getBytes());
			operation.write(b);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public synchronized static final void writeBuffer(int id) {
			TerminalDevice  t = null;
			for (TerminalDevice terminal : BussinessConfig.terminalList) {
				if(id==terminal.getId()){
					t = terminal ;
				}
			}
			StringBuffer sBuffer = new StringBuffer();
			sBuffer.append("终端号:" + t.getId() + ";"+System.getProperty("line.separator"));
			for (MeterDevice port : t.getMeterList()) {
				sBuffer.append("设备号:" + port.getId());
				sBuffer.append("(");
				for (PointDevice pd : port.getPointDevice()) {
					sBuffer.append(pd.getName() + ":" + pd.getValue() + ":"+" previous ="+pd.getPreviousValue());
				}
				sBuffer.append(")");
			}
		try {
			bufferData.write(sBuffer.toString().getBytes());
			bufferData.write(b);
			bufferData.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public synchronized static final void clearBuffer() {
		try {
			Date date = new Date();
			bufferData.write(date.toString().getBytes());
			bufferData.write(":缓存清除！".getBytes());
			bufferData.write(b);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public synchronized static final void writeCommunicationLog(int id,String message) {
		FileOutputStream fos = ServerContext.getTerminalLog(id);
		PrintWriter pw = new PrintWriter(fos);
		pw.write(new Date() + ":" + message+ System.getProperty("line.separator"));
		pw.flush();
	}

	public static void closeLog() {
		try {
			if (error != null) {
				error.close();
			}
			if (operation != null) {
				operation.close();
			}
			if (bufferData != null) {
				bufferData.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
