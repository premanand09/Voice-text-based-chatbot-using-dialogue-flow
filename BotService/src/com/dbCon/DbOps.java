package com.dbCon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.failData.FailData;
import com.failData.Jobs_status;
import com.loadPojo.DMRpojo;
import com.loadPojo.LoadJobPojo;
import com.shipDelayPojo.ShipmentDelayPojo;
import com.shipStatusPojo.Pod;
import com.shipStatusPojo.ShipStatusPojo;

public class DbOps {

	Connection getCon() {

		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/HP?autoReconnect=true&useSSL=false", "prem", "root");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return con;
	}

	public ShipStatusPojo getShipmentStatus(String shipmentid) {
		String shipStatus=null;
		String carrier=null;
		String eta=null;
		ShipStatusPojo shipStatusPojo = new ShipStatusPojo();
		try {
			Connection con = getCon();
			Statement stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery("select shipmentid,status,carrier,eta from ship_status where shipmentid = '"+shipmentid+"'");
			
			System.out.println("resultset");
			
			
			if(!rs.next()) {
				System.out.println("resultset");
				//System.out.println(rs.getString("shipmentid"));
				shipStatusPojo.setShipmentid(null);
				shipStatusPojo.setCarrier(null);
				shipStatusPojo.setEta(null);
				shipStatusPojo.setShipStatus(null);
				return shipStatusPojo;
			}
			
			rs.beforeFirst();
			while (rs.next()) {
				System.out.println("Shipment status : "+rs.getString("status"));
			shipmentid=rs.getString("shipmentid");
			shipStatus=rs.getString("status");
			carrier=rs.getString("carrier");
			eta=rs.getString("eta");
			}
			
			shipStatusPojo.setShipmentid(shipmentid);
			shipStatusPojo.setCarrier(carrier);
			shipStatusPojo.setEta(eta);
			shipStatusPojo.setShipStatus(shipStatus);
			
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return shipStatusPojo;
	}

	public List<ShipmentDelayPojo> getAllDelayShipments(String region) {
		
		
		List<ShipmentDelayPojo> ShipmentDelayPojoList = new ArrayList<ShipmentDelayPojo>();
		 String shipmentid = null;;
		 String DelayDays=null;
		 
		try {
			Connection con = getCon();
			Statement stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery("select shipmentid,delayDays from HP.delayShipment where delayDays>0 and region like '"+region+"'");
			
			System.out.println("resultset");
			
			
			if(!rs.next()) {
				System.out.println("resultset");
				ShipmentDelayPojo ShipmentDelayPojo = new ShipmentDelayPojo();
				ShipmentDelayPojo.setShipmentid(null);
				ShipmentDelayPojo.setDelayDays(null);
				ShipmentDelayPojoList.add(ShipmentDelayPojo);
				return ShipmentDelayPojoList;
			}
			
			rs.beforeFirst();
			
			while (rs.next()) {
			
				ShipmentDelayPojo ShipmentDelayPojo = new ShipmentDelayPojo();
			shipmentid=rs.getString("shipmentid");
			DelayDays = rs.getString("DelayDays");
			ShipmentDelayPojo.setShipmentid(shipmentid);
			ShipmentDelayPojo.setDelayDays(DelayDays);			
			ShipmentDelayPojoList.add(ShipmentDelayPojo);
			
			}
			
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		
		
		
		return ShipmentDelayPojoList;
	}

public List<Jobs_status> getLoadStatus(String status_pass_fail, String day) {
		
		
		List<Jobs_status> LoadJobPojoList = new ArrayList<Jobs_status>();

		 
		try {
			Connection con = getCon();
			Statement stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery("select job_name,status,record_count from hp.job_status where status = '"+status_pass_fail+"' and DATE(load_date) = '"+day+"'");
			
			System.out.println("resultset");
			
			
			if(!rs.next()) {
				System.out.println("resultset");
				Jobs_status Jobs_status = new Jobs_status();
				Jobs_status.setJob_name(null);
				Jobs_status.setStatus(null);
				Jobs_status.setRows(0);
				LoadJobPojoList.add(Jobs_status);
				return LoadJobPojoList;
			}
			
			rs.beforeFirst();
			
			while (rs.next()) {
			
				Jobs_status Jobs_status = new Jobs_status();
				Jobs_status.setJob_name(rs.getString("job_name"));
				Jobs_status.setStatus(status_pass_fail);
				Jobs_status.setRows(rs.getInt("record_count"));
				LoadJobPojoList.add(Jobs_status);			
			}
			
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		
		
		
		return LoadJobPojoList;
	}


public Pod getShipmentPodLink(String shipmentid) {
	String podlink=null;
	
	Pod pod = new Pod();
	try {
		Connection con = getCon();
		Statement stmt = con.createStatement();
		
		ResultSet rs = stmt.executeQuery("select pod from ship_status where shipmentid = '"+shipmentid+"'");
		
		System.out.println("resultset");
		
		
		if(!rs.next()) {
			System.out.println("resultset");
			//System.out.println(rs.getString("shipmentid"));
			pod.setPodlink(null);
			
			return pod;
		}
		
		rs.beforeFirst();
		while (rs.next()) {
			System.out.println("pod : "+rs.getString("pod"));
		podlink=rs.getString("pod");
	
		}
		
		pod.setPodlink(podlink);
		
		con.close();
	} catch (Exception e) {
		System.out.println(e);
	}
	
	return pod;
}

public String getTicketNo() {
	
	try {
		Connection con = getCon();
		Statement stmt = con.createStatement();
		
		ResultSet rs = stmt.executeQuery("select id+1 as ticketno from HP.incident");
		
		System.out.println("resultset");
		
		
		if(rs.next()) {
			
			String ticketno = rs.getString("ticketno");
			System.out.println(ticketno);
			return ticketno;
		}
		
		
		con.close();
	} catch (Exception e) {
		System.out.println(e);
	}
	return null;
	
	
}

public DMRpojo getDMRStatus(String load_date) {
	String dmrPath=null;
	
	DMRpojo dmr = new DMRpojo();
	try {
		Connection con = getCon();
		Statement stmt = con.createStatement();
		
		ResultSet rs = stmt.executeQuery("select report_file_path from hp.reportLog where report_name='DMR' and date(run_date)='"+load_date+"'");
		
		System.out.println("resultset");
		
		
		if(!rs.next()) {
			System.out.println("resultset");
			//System.out.println(rs.getString("shipmentid"));
			dmr.setDmrPath(null);
			
			return dmr;
		}
		
		rs.beforeFirst();
		while (rs.next()) {
			System.out.println("dmr path : "+rs.getString("report_file_path"));
			dmrPath=rs.getString("report_file_path");
	
		}
		
		dmr.setDmrPath(dmrPath);
		
		con.close();
	} catch (Exception e) {
		System.out.println(e);
	}
	
	return dmr;
}

public List<FailData> getJobStatus(String load_date) {
	
	
	
	List<FailData> listofdata = new ArrayList<FailData>();
	
	try {
		Connection con = getCon();
		Statement stmt = con.createStatement();
		
		System.out.println("select job_name,status from hp.job_status where status = 'fail' and DATE(load_date) = '"+load_date+"'");
		ResultSet rs = stmt.executeQuery("select job_name,status from hp.job_status where status = 'fail' and DATE(load_date) = '"+load_date+"'");
		
		System.out.println("resultset");
		
		
		if(!rs.next()) {
			FailData data = new FailData();
			System.out.println("resultset");
			//System.out.println(rs.getString("shipmentid"));
			data.setReport_name(null);
			data.setStatus(null);
			listofdata.add(data);
			return listofdata;
		}
		
		rs.beforeFirst();
		while (rs.next()) {
			FailData data = new FailData();	
			data.setReport_name(rs.getString("job_name"));
			data.setStatus(rs.getString("status"));
		
			listofdata.add(data);
	
		}
	
		con.close();
	} catch (Exception e) {
		System.out.println(e);
	}
	
	return listofdata;
}


}
