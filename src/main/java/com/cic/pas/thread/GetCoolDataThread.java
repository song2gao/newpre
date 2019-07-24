package com.cic.pas.thread;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.cic.pas.common.bean.CoolHotData;
import com.cic.pas.common.bean.MeterDevice;
import com.cic.pas.common.bean.PointDevice;
import com.cic.pas.common.bean.TerminalDevice;
import com.cic.pas.dao.BussinessConfig;

/**
 * @author: gaosong
 * @date:2017-12-9 下午02:52:15
 * @version :1.0.0
 * 
 */
public class GetCoolDataThread extends Thread {

	public Connection conn = null;
	public PreparedStatement pst = null;
	public static final String url = "jdbc:sqlserver://10.3.72.235:1433;databaseName=EMMS_BCG";
	public static final String name = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	public static final String user = "BC";
	public static final String password = "BC2017";

	public GetCoolDataThread() {

	}

	public void run() {
		while (true) {
			Calendar ca = Calendar.getInstance();
			int minute = ca.get(Calendar.MINUTE);
			if (minute% 5 == 0) {
				getConnection();
				String sql = "SELECT METERNO,rDate,rtime,HEATQU,Hunit,ColdQu,Cunit,VOLUME,"
						+ "ColdVolume,Flow,FLTEMP,RETEMP,TempDiff FROM heatData;";
				try {
					pst = conn.prepareStatement(sql);
					ResultSet rs = pst.executeQuery();
					List<CoolHotData> list = new ArrayList<CoolHotData>();
					while (rs.next()) {
						CoolHotData data = new CoolHotData();
						data.setCode(rs.getString("METERNO"));
						data.setDateCode(rs.getString("rDate"));
						data.setTimeCode(rs.getString("rtime"));
						data.setHotValue(rs.getBigDecimal("HEATQU"));
						data.setHotUnit(rs.getString("Hunit"));
						data.setCoolValue(rs.getBigDecimal("ColdQu"));
						data.setCoolUnit(rs.getString("Cunit"));
						data.setVolumeHot(rs.getBigDecimal("VOLUME"));
						data.setVolumeCool(rs.getBigDecimal("ColdVolume"));
						data.setSpeed(rs.getBigDecimal("Flow"));
						data.setFlTemp(rs.getBigDecimal("FLTEMP"));
						data.setRetTemp(rs.getBigDecimal("RETEMP"));
						list.add(data);
					}
					for (CoolHotData data : list) {
						for (TerminalDevice t : BussinessConfig.config.terminalList) {
							if (t.getCode().equals("9")) {
								for (MeterDevice m : t.getMeterList()) {
									if (m.getCode().equals(data.getCode())) {
										for (PointDevice p : m.getPointDevice()) {
											if (p.getCode().equals("201")) {
												//p.setValue(data.getSpeed());
											} else if (p.getCode()
													.equals("202")) {
												// p.setValue(data.get)
											} else if (p.getCode()
													.equals("203")) {
												p.setValue(data.getSpeed());
											} else if (p.getCode()
													.equals("204")) {
												p.setValue(data.getFlTemp());
											} else if (p.getCode()
													.equals("205")) {
												p.setValue(data.getRetTemp());
											} else if (p.getCode().equals("65")) {
												p.setValue(data.getHotValue());
											} else if (p.getCode().equals("66")) {
												p.setValue(data.getCoolValue());
											}
											p.setTime(data.getDateCode()+" "+data.getTimeCode());
										}
										break;
									}
								}
								break;
							}
						}
					}
					rs.close();
					close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					Thread.sleep(60000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public void getConnection() {
		try {
			if (conn == null||conn.isClosed()) {
				try {
					Class.forName(name);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					conn = DriverManager.getConnection(url, user, password);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			this.conn.close();
			this.pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		GetCoolDataThread cool = new GetCoolDataThread();
		cool.run();
	}
}
