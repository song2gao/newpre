package com.cic.httpclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import com.alibaba.fastjson.JSONObject;
import com.cic.pas.common.bean.Data;
import com.cic.pas.common.bean.DeviceData;

/**
 * 用来主动发起HTTP请求的类
 * 
 * @author Ajita
 */
public class HttpRequester {
	/**
	 * 请求的默认字符编码方式
	 */
	private String defaultContentEncoding;

	public String getDefaultContentEncoding() {
		return defaultContentEncoding;
	}

	public void setDefaultContentEncoding(String defaultContentEncoding) {
		this.defaultContentEncoding = defaultContentEncoding;
	}

	public HttpRequester() {
		this.defaultContentEncoding = Charset.defaultCharset().name();
	}

	/**
	 * 发送HTTP请求
	 * 
	 * @param urlString
	 *            HTTP请求的地址
	 * @param method
	 *            HTTP请求的方式，支持GET和POST
	 * @param parameters
	 *            HTTP请求内容的参数，放在GET的URL串中或者POST的请求流中
	 * @param properties
	 *            HTTP连接的属性
	 * @return 返回一个HTTP响应对象
	 * @throws IOException
	 */
	public HttpRespons send(String urlString, String method,
			Map<String, String> parameters, Map<String, String> properties)
			throws IOException {
		HttpURLConnection urlConnection = null;

		if (method.equalsIgnoreCase("GET") && parameters != null) {
			StringBuffer param = new StringBuffer();
			int i = 0;
			for (String key : parameters.keySet()) {
				if (i == 0)
					param.append("?");
				else
					param.append("&");
				param.append(key).append("=").append(parameters.get(key));
				i++;
			}
			urlString += param;
		}
		URL url = new URL(urlString);
		urlConnection = (HttpURLConnection) url.openConnection();

		urlConnection.setRequestMethod(method);
		urlConnection.setDoOutput(true);
		urlConnection.setDoInput(true);
		urlConnection.setUseCaches(false);
		//urlConnection.setRequestProperty("Charset", "UTF-8");
		//urlConnection.setRequestProperty("accept","application/json");

		if (properties != null)
			for (String key : properties.keySet()) {
				urlConnection.addRequestProperty(key, properties.get(key));
			}

		if (method.equalsIgnoreCase("POST") && parameters != null) {
			StringBuffer param = new StringBuffer();
			for (String key : parameters.keySet()) {
//				param.append("&");
//				param.append(key).append("=");
				param.append(parameters.get(key));
			}
			byte[] writebytes=param.toString().getBytes();
			urlConnection.setRequestProperty("Content-Length", String.valueOf(writebytes.length));
			urlConnection.getOutputStream().write(writebytes);
			// 设置文件长度
			
			urlConnection.getOutputStream().flush();
			urlConnection.getOutputStream().close();
		}

		return this.makeContent(urlString, urlConnection);
	}

	/**
	 * 得到响应对象
	 * 
	 * @param urlConnection
	 * @return 响应对象
	 * @throws IOException
	 */
	private HttpRespons makeContent(String urlString,
			HttpURLConnection urlConnection) throws IOException {
		HttpRespons httpResponser = new HttpRespons();
		try {
			InputStream in = urlConnection.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(in));
			httpResponser.contentCollection = new Vector<String>();
			StringBuffer temp = new StringBuffer();
			String line = bufferedReader.readLine();
			while (line != null) {
				httpResponser.contentCollection.add(line);
				temp.append(line).append("\r\n");
				line = bufferedReader.readLine();
			}
			bufferedReader.close();

			String ecod = urlConnection.getContentEncoding();
			if (ecod == null)
				ecod = this.defaultContentEncoding;

			httpResponser.urlString = urlString;

			httpResponser.defaultPort = urlConnection.getURL().getDefaultPort();
			httpResponser.file = urlConnection.getURL().getFile();
			httpResponser.host = urlConnection.getURL().getHost();
			httpResponser.path = urlConnection.getURL().getPath();
			httpResponser.port = urlConnection.getURL().getPort();
			httpResponser.protocol = urlConnection.getURL().getProtocol();
			httpResponser.query = urlConnection.getURL().getQuery();
			httpResponser.ref = urlConnection.getURL().getRef();
			httpResponser.userInfo = urlConnection.getURL().getUserInfo();

			httpResponser.content = new String(temp.toString().getBytes(), ecod);
			httpResponser.contentEncoding = ecod;
			httpResponser.code = urlConnection.getResponseCode();
			httpResponser.message = urlConnection.getResponseMessage();
			httpResponser.contentType = urlConnection.getContentType();
			httpResponser.method = urlConnection.getRequestMethod();
			httpResponser.connectTimeout = urlConnection.getConnectTimeout();
			httpResponser.readTimeout = urlConnection.getReadTimeout();

			return httpResponser;
		} catch (IOException e) {
			throw e;
		} finally {
			if (urlConnection != null)
				urlConnection.disconnect();
		}
	}
	public static void main(String[] args){
		try {
			DeviceData deviceData=new DeviceData();
			deviceData.setCollectorID("1");
			List<Data> list=new ArrayList<Data>();
			Data d1=new Data();
			d1.setCollectData("1");
			d1.setCollectTime("2018-05-28 10:00:00");
			d1.setDeviceName("测试");
			Data d2=new Data();
			d2.setCollectData("2");
			d2.setCollectTime("201-05-28 13:00:00");
			list.add(d1);
			list.add(d2);
			deviceData.setData(list);
			JSONObject json=new JSONObject();
			json.put("1", deviceData);
//			System.out.println(json.getString("1"));
			String deviceDataJson=json.getString("1");
			Map<String, String> parameters =new HashMap<String, String>();
			parameters.put("deviceDataJson", deviceDataJson);
			HttpRespons respons=new HttpRequester().send("http://localhost:8080/emcc-new/monitor/test", "POST", parameters, null);
			System.out.println(respons.getMessage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

