package com.cic.pas.test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.cic.httpserver.HttpHandler;
import com.cic.httpserver.HttpRequestMessage;
import com.cic.httpserver.HttpResponseMessage;
import com.cic.httpserver.HttpServer;
import com.cic.httpserver.RequestData;
import com.cic.pas.mapper.PreCurveRecordMapper;

public class Test {
	@Autowired
	private PreCurveRecordMapper mapper;

	/**
	 * @param args
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws IOException,
			InterruptedException {
		// TODO Auto-generated method stub
		String str="";
		for(int i=1;i<97;i++){
			str+="point"+i+"+";
		}
		System.out.println(str);
//		HttpServer server = new HttpServer();
//		server.setEncoding("UTF-8");
//		server.setHttpHandler(new HttpHandler() {
//			public HttpResponseMessage handle(HttpRequestMessage request) {
//				String level = request.getParameter("level");
//				String method = request.getHeader("Method")[0];
//				String content = request.getContext();
//				createData(method, content);
//				HttpResponseMessage response = new HttpResponseMessage();
//				response.setContentType("text/plain");
//				response
//						.setResponseCode(HttpResponseMessage.HTTP_STATUS_SUCCESS);
//				response.appendBody("CONNECTED\n");
//				response.appendBody(level);
//				return response;
//			}
//		});
//		server.run();
//		Thread.sleep(10000);
	}

	public void test() {
		mapper.find();
	}

	public static void createData(String method, String content) {
		if (method.equals("devices")) {
		} else if (method.equals("data")) {
			JSONObject jobject = JSONObject.fromObject(content);
			JSONArray array = JSONArray.fromObject(jobject.getString("rts"));
			List<RequestData> devices = (List<RequestData>) JSONArray
					.toCollection(array, RequestData.class);
			System.out.println("---------------接收数据开始-------------------");
			for (RequestData data : devices) {
				String out="采集器名称："+data.getBnm()+"\r\n"+
				"点位名称："+data.getTds()+"\r\n"+
				"采集时间："+data.getTs()+"\r\n"+
				"采集值："+data.getVal();
				System.out.println(out);
			}
			System.out.println("---------------接收数据结束-------------------");
		} else {
		}
	}

}
