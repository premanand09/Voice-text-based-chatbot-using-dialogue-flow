package com.mkyong;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.dbCon.DbOps;
import com.failData.FailData;
import com.failData.Jobs_status;
import com.hawkeye.utils.BaseUtils;
import com.loadPojo.DMRpojo;
import com.loadPojo.LoadJobPojo;
import com.shipDelayPojo.ShipmentDelayPojo;
import com.shipStatusPojo.Pod;
import com.shipStatusPojo.ShipStatusPojo;
 
@Path("/hp")
public class HpWebhook {
    
/*    @GET
    @Path("/{cusNo}")
    @Produces(MediaType.APPLICATION_JSON)
    public Customer produceCustomerDetailsinJSON(@PathParam("cusNo") int no) {
 
        
         * I AM PASSING CUST.NO AS AN INPUT, SO WRITE SOME BACKEND RELATED STUFF AND
         * FIND THE CUSTOMER DETAILS WITH THAT ID. AND FINALLY SET THOSE RETRIEVED VALUES TO
         * THE CUSTOMER OBJECT AND RETURN IT, HOWEVER IT WILL RETURN IN JSON FORMAT :-)
         * 
        
        Customer cust = new Customer();        
            cust .setCustNo(no);
            cust .setCustName("Java4s");
            cust .setCustCountry("India");
        return cust;
    }
*/
	

	@POST
	@Path("/shipmentStatus")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response shipmentStatusJsonResponse(JSONObject request) throws JSONException, InterruptedException {

	
		
		
		if(request.getJSONObject("result").getJSONObject("metadata").getString("intentName").equals("shipment status"))
		{
			
			
			
		//String jsonResponse = "{\"speech\":\"This is simple response \",\"displayText\":\"thank you banking\",\"data\":{\"test\":\"success\"},\"contextOut\":[{\"name\":\"weather\",\"lifespan\":2,\"parameter\":{\"city\":\"Rome\"}}],\"source\":\"agent\"}";
		//return Response.status(201).entity(track.getResult().getParameters()).build();

		ShipStatusPojo shipStatus = new ShipStatusPojo();
		String shipmentid = request.getJSONObject("result").getJSONObject("parameters").getString("shipmentid");
		System.out.println(shipmentid);
		DbOps dbQuery = new DbOps();
		//System.out.println(request.getResult().getParameters().getShipmentid());
		
		shipStatus = dbQuery.getShipmentStatus(shipmentid);
		System.out.println(shipStatus.getShipmentid());
		
		
		
		
		if(shipStatus.getShipmentid()!=null) {
			
		
		JsonResponseDialogueFlow response = new JsonResponseDialogueFlow();
		Messages message = new Messages();
		Messages message1 = new Messages();
		List<Messages> listOfMessages = new ArrayList<Messages>();
		
		String speech = "Your shipment is "+shipStatus.getShipStatus()+". Shipment has reached on "+shipStatus.getEta()+" date.";
		String speech1 = "What is the POD link for this shipment ?";
		
		message.setSpeech(speech);
		message.setType(0);
		
		
		listOfMessages.add(message);
		
		message1.setSpeech(speech1);
		message1.setType(0);

		listOfMessages.add(message1);

		
		response.setSpeech(speech);
		response.setMessages(listOfMessages);
		response.setSource("agent");
		
		System.out.println(response.getMessages());
		return Response.status(201).entity(response).build();

		/*	return Response.status(201).entity("{\r\n" + 
				"\"messages\":[\r\n" + 
				"{\"speech\":\"hi\",\r\n" + 
				"\"type\":0\r\n" + 
				"},{\r\n" + 
				"\"speech\":\"hello\",\r\n" + 
				"\"type\":0\r\n" + 
				"}\r\n" + 
				"]\r\n" + 
				",\"source\":\"agent\"\r\n" + 
				"}").build();
		
		*/

		}
		else {
			
			
			JsonResponseDialogueFlow response = new JsonResponseDialogueFlow();
			Messages message = new Messages();
			List<Messages> listOfMessages = new ArrayList<Messages>();
			
			String speech = "Sorry! \nShipment id "+shipmentid+" is not found in our records.";
			message.setSpeech(speech);
			message.setType(0);
			listOfMessages.add(message);
			
			response.setMessages(listOfMessages);
			response.setSource("agent");
			
			return Response.status(201).entity(response).build();
			
				
		}
		
		}
		
		if (request.getJSONObject("result").getJSONObject("metadata").getString("intentName").equals("delay_shipment")) {
			
			List<ShipmentDelayPojo> shipStatus = new ArrayList<ShipmentDelayPojo>();
			List<Messages> messageList = new ArrayList<Messages>();
			DbOps dbQuery = new DbOps();
			
			String region = request.getJSONObject("result").getJSONObject("parameters").getString("region");
			
			//System.out.println(request.getResult().getParameters().getShipmentid());
			
			
			shipStatus = dbQuery.getAllDelayShipments(region);
		/*	
			for (ShipmentDelayPojo s1 : shipStatus) {
				
				System.out.println(s1.getShipmentid() + "------"+s1.getDelayDays());
				
			}
			*/
			
			JsonResponseDialogueFlow responseDialogueFlow  = new JsonResponseDialogueFlow();
			for (ShipmentDelayPojo shipmentDelayPojo : shipStatus) {
				
				if(shipmentDelayPojo.getShipmentid()!=null) {
					
					Messages message = new Messages();
					System.out.println(shipmentDelayPojo.getShipmentid());
					message.setSpeech("Your shipment "+shipmentDelayPojo.getShipmentid()+" in region "+region+" would be delayed by "+shipmentDelayPojo.getDelayDays()+" days.");
					message.setType(0);
					messageList.add(message);
					
				}

				else {

					String speech = "Sorry! \n No delayed shipments are present for region "+ region;
					Messages message = new Messages();
					message.setSpeech(speech);
					message.setType(0);
					messageList.add(message);
					
					System.out.println("hello");
				}
				
				
				
			}
			
			responseDialogueFlow.setMessages(messageList);
			responseDialogueFlow.setSpeech("hi");
			responseDialogueFlow.setSource("agent");
			
			/*return Response.status(201).entity("{\r\n" + 
					"\"messages\":[\r\n" + 
					"{\"speech\":\"hi\",\r\n" + 
					"\"type\":0\r\n" + 
					"},{\r\n" + 
					"\"speech\":\"hello\",\r\n" + 
					"\"type\":0\r\n" + 
					"}\r\n" + 
					"]\r\n" + 
					",\"source\":\"agent\"\r\n" + 
					"}").build();*/
			
			return Response.status(201).entity(responseDialogueFlow).build();
			
		}
	
		

		if (request.getJSONObject("result").getJSONObject("metadata").getString("intentName").equals("shipment status - pod") && request.getJSONObject("result").getString("resolvedQuery").equalsIgnoreCase("yes")) {
			

			Pod pod = new Pod();
			String shipmentid = request.getJSONObject("result").getJSONArray("contexts").getJSONObject(0).getJSONObject("parameters").getString("shipmentid");
			DbOps dbQuery = new DbOps();
			//System.out.println(request.getResult().getParameters().getShipmentid());
			
			pod = dbQuery.getShipmentPodLink(shipmentid);
			System.out.println(pod.getPodlink());
			
			
			if(pod.getPodlink()!=null) {
				
			
			JsonResponseDialogueFlow response = new JsonResponseDialogueFlow();
			Messages message = new Messages();
			List<Messages> listOfMessages = new ArrayList<Messages>();
			
			String speech = "POD link for shipment id "+shipmentid+" is "+pod.getPodlink();
			message.setSpeech(speech);
			message.setType(0);
			listOfMessages.add(message);
			response.setSpeech(speech);
			response.setMessages(listOfMessages);
			response.setSource("agent");
			
			System.out.println(response.getMessages());
			return Response.status(201).entity(response).build();


			}
			else {
				
				
				JsonResponseDialogueFlow response = new JsonResponseDialogueFlow();
				Messages message = new Messages();
				Messages message1 = new Messages();
				List<Messages> listOfMessages = new ArrayList<Messages>();
				
				String speech = "Sorry! \n No POD link is found for shipment id "+shipmentid+" in our records.";
				String speech1 = "Would you like to request new one ?";
				message.setSpeech(speech);
				message.setType(0);
				//message1.setSpeech(speech1);
				//message1.setType(0);
				
				listOfMessages.add(message);
				//listOfMessages.add(message1);
				
				response.setMessages(listOfMessages);
				response.setSource("agent");
				
				return Response.status(201).entity(response).build();
				
					
			}
						
		}


		if (request.getJSONObject("result").getJSONObject("metadata").getString("intentName").equals("DMR alert")) {
			

			DMRpojo dmrpojo = new DMRpojo();
			String day = request.getJSONObject("result").getJSONObject("parameters").getString("day");
			
			DbOps dbQuery = new DbOps();
			//System.out.println(request.getResult().getParameters().getShipmentid());
			
			dmrpojo = dbQuery.getDMRStatus(day);
			System.out.println(dmrpojo.getDmrPath());
			
			
			
			
			if(dmrpojo.getDmrPath()!=null) {
				
			
			JsonResponseDialogueFlow response = new JsonResponseDialogueFlow();
			Messages message = new Messages();
			Messages message1 = new Messages();
			List<Messages> listOfMessages = new ArrayList<Messages>();
			List<ContextOut> listOfContextOut = new ArrayList<ContextOut>();
			ContextOut contextOut = new ContextOut();
			Parameters parameters = new Parameters();
			
			String speech = "Yes. DMR was run on "+day+".";
			String speech1 = "Send this DMR report in an email";

			message.setSpeech(speech);
			message.setType(0);
			message1.setSpeech(speech1);
			message1.setType(0);
			
			listOfMessages.add(message);
			listOfMessages.add(message1);
			contextOut.setName("DMRParams");
			contextOut.setLifespan("50");
			contextOut.setParameters(parameters);
			parameters.setReportPath(dmrpojo.getDmrPath());
			listOfContextOut.add(contextOut);
			response.setSpeech(speech);
			response.setMessages(listOfMessages);
			response.setSource("agent");
			response.setContextOut(listOfContextOut);
			
			System.out.println(response.getMessages());
			return Response.status(201).entity(response).build();


			}
			else {
				
				
				JsonResponseDialogueFlow response = new JsonResponseDialogueFlow();
				Messages message = new Messages();
				List<Messages> listOfMessages = new ArrayList<Messages>();
				
				String speech = "Sorry! \n DMR report was not run on "+day+".";
				message.setSpeech(speech);
				message.setType(0);
				listOfMessages.add(message);
				
				response.setMessages(listOfMessages);
				response.setSource("agent");
				
				return Response.status(201).entity(response).build();
				
					
			}
						
		}


		
	if (request.getJSONObject("result").getJSONObject("metadata").getString("intentName").equals("DMR alert - yes")) {
			


			
			String mailids = request.getJSONObject("result").getJSONObject("parameters").getString("email").replace("[", "").replace("]", "").replace("\"", "");

			String dmrPath = request.getJSONObject("result").getJSONArray("contexts").getJSONObject(1).getJSONObject("parameters").getString("reportPath");
			
			
			
			String[] emailArray = mailids.split(",");
			for (int i = 0; i < emailArray.length; i++) {
			
				if(!(emailArray[i].contains("@accenture.com"))) {

					System.out.println(emailArray[i]);
					JsonResponseDialogueFlow response = new JsonResponseDialogueFlow();
					Messages message = new Messages();
					List<Messages> listOfMessages = new ArrayList<Messages>();
					List<ContextOut> listOfContextOut = new ArrayList<ContextOut>();
					ContextOut contextOut = new ContextOut();
					Parameters parameters = new Parameters();
					
					String speech = "Invalid email address. Please enter valid email address ending with accenture.com domain";
					message.setSpeech(speech);
					message.setType(0);
					listOfMessages.add(message);
		/*			contextOut.setName("DMRParams");
					contextOut.setLifespan("5");
					contextOut.setParameters(parameters);
					parameters.setReportPath(dmrpojo.getDmrPath());
					listOfContextOut.add(contextOut);
		*/			response.setSpeech(speech);
					response.setMessages(listOfMessages);
					response.setSource("agent");
//					response.setContextOut(listOfContextOut);
					
					System.out.println(response.getMessages());
					return Response.status(201).entity(response).build();

				}				
			}
			
			
			System.out.println("send emails");
			System.out.println(mailids);
			
			
			System.out.println(dmrPath);
			String mailStatus = BaseUtils.mailSendWithAttachment("premanand.naik09@gmail.com","LUVTOCRY",mailids,"hi","hello",dmrPath);

			System.out.println(mailStatus);
			if(mailStatus.equals("message sent successfully")) {
			JsonResponseDialogueFlow response = new JsonResponseDialogueFlow();
			Messages message = new Messages();
			List<Messages> listOfMessages = new ArrayList<Messages>();
			List<ContextOut> listOfContextOut = new ArrayList<ContextOut>();
			ContextOut contextOut = new ContextOut();
			Parameters parameters = new Parameters();
			
			String speech = "DMR report sent successfully.";
			message.setSpeech(speech);
			message.setType(0);
			listOfMessages.add(message);
/*			contextOut.setName("DMRParams");
			contextOut.setLifespan("5");
			contextOut.setParameters(parameters);
			parameters.setReportPath(dmrpojo.getDmrPath());
			listOfContextOut.add(contextOut);
*/			response.setSpeech(speech);
			response.setMessages(listOfMessages);
			response.setSource("agent");
//			response.setContextOut(listOfContextOut);
			
			System.out.println(response.getMessages());
			return Response.status(201).entity(response).build();


			}
			else {
				
				
				JsonResponseDialogueFlow response = new JsonResponseDialogueFlow();
				Messages message = new Messages();
				List<Messages> listOfMessages = new ArrayList<Messages>();
				
				String speech = "Sorry! \n DMR report mail sending is failed due network failure.";
				message.setSpeech(speech);
				message.setType(0);
				listOfMessages.add(message);
				
				response.setMessages(listOfMessages);
				response.setSource("agent");
				
				return Response.status(201).entity(response).build();
				
					
			}
						
		}

	//redshift incident creation
	
	if (request.getJSONObject("result").getJSONObject("metadata").getString("intentName").equals("redshift query - next - next - next - yes - yes")) {
		
		

		DbOps db = new DbOps();
		String ticektno = db.getTicketNo();
		
		JsonResponseDialogueFlow response = new JsonResponseDialogueFlow();
		Messages message = new Messages();
		Messages message1 = new Messages();
		Messages message2 = new Messages();
		Messages message3 = new Messages();
		List<Messages> listOfMessages = new ArrayList<Messages>();
		List<ContextOut> listOfContextOut = new ArrayList<ContextOut>();
		ContextOut contextOut = new ContextOut();
		Parameters parameters = new Parameters();
		
		String speech = "Incident raised successfully.";
		String speech1 = "Incident number is HP"+ticektno+". sending email to concerned department...";
		String speech2 = "For further queries please contact hawkeye.support@accenture.com";
		
		String sub = "ticket no - HP"+ticektno; 
		String msg = "Hi, \n \t \t Incident is raised for redshift issue. Your ticket no is HP"+ticektno+". \n\n\n Thanks.";
		System.out.println("sending email");
		BaseUtils.mailSend("premanand.naik09@gmail.com","LUVTOCRY","premanand.naik@accenture.com,hawkeye.support@accenture.com",sub,msg);
		
		message.setSpeech(speech);
		message.setType(0);
		message1.setSpeech(speech1);
		message1.setType(0);
		message2.setSpeech(speech2);
		message2.setType(0);
		
		
		listOfMessages.add(message);
		listOfMessages.add(message1);
		listOfMessages.add(message2);
		
/*			contextOut.setName("DMRParams");
		contextOut.setLifespan("5");
		contextOut.setParameters(parameters);
		parameters.setReportPath(dmrpojo.getDmrPath());
		listOfContextOut.add(contextOut);
*/			response.setSpeech(speech);
		response.setMessages(listOfMessages);
		response.setSource("agent");
//		response.setContextOut(listOfContextOut);
		
		System.out.println(response.getMessages());
		return Response.status(201).entity(response).build();


		}
					

	
if (request.getJSONObject("result").getJSONObject("metadata").getString("intentName").equals("failed reports")) {
		

		//String dmrPath = request.getJSONObject("result").getJSONArray("contexts").getJSONObject(1).getJSONObject("parameters").getString("reportPath");
		
//		String mailStatus = BaseUtils.mailSendWithAttachment("premanand.naik09@gmail.com","LUVTOCRY",mailids,"hi","hello",dmrPath);

	String load_date= request.getJSONObject("result").getJSONObject("parameters").getString("date");
	
	DbOps db = new DbOps() ;
	
		FailData faildata = new FailData();
		List<FailData> reportdata = new ArrayList<FailData>();
	
		reportdata = db.getJobStatus(load_date);
		
		JsonResponseDialogueFlow response = new JsonResponseDialogueFlow();
		Messages message = new Messages();
		List<Messages> listOfMessages = new ArrayList<Messages>();
		List<ContextOut> listOfContextOut = new ArrayList<ContextOut>();
		ContextOut contextOut = new ContextOut();
		Parameters parameters = new Parameters();
		Data data = new Data();
		data.setListofdata(new ArrayList<Object>(reportdata));
		String speech = "Report sent.";
		message.setSpeech(speech);
		message.setType(0);
		listOfMessages.add(message);
/*			contextOut.setName("DMRParams");
		contextOut.setLifespan("5");
		contextOut.setParameters(parameters);
		parameters.setReportPath(dmrpojo.getDmrPath());
		listOfContextOut.add(contextOut);
*/		//	response.setSpeech(speech);
		
		response.setMessages(listOfMessages);
		response.setData(data);
		response.setSource("agent");
//		response.setContextOut(listOfContextOut);
		
		System.out.println(response.getMessages());
		return Response.status(201).entity(response).build();


						
	}

if (request.getJSONObject("result").getJSONObject("metadata").getString("intentName").equals("show tableau reports")) {
	

	//String reportname = request.getJSONObject("result").getJSONArray("contexts").getJSONObject(1).getJSONObject("parameters").getString("reportname");
	
//	String mailStatus = BaseUtils.mailSendWithAttachment("premanand.naik09@gmail.com","LUVTOCRY",mailids,"hi","hello",dmrPath);

String reportname= request.getJSONObject("result").getJSONObject("parameters").getString("reportname");
String date= request.getJSONObject("result").getJSONObject("parameters").getString("date");

	JsonResponseDialogueFlow response = new JsonResponseDialogueFlow();
	Messages message = new Messages();
	List<Messages> listOfMessages = new ArrayList<Messages>();
	List<ContextOut> listOfContextOut = new ArrayList<ContextOut>();
	ContextOut contextOut = new ContextOut();
	Parameters parameters = new Parameters();
	
	String speech;  
	
	if (reportname.equalsIgnoreCase("Data Quality")) {
		speech = "click on above image to download "+reportname+" tableau report.";
	}
	else {
		
		speech = "Please tell correct report name.";
	}
	message.setSpeech(speech);
	message.setType(0);
	listOfMessages.add(message);
/*			contextOut.setName("DMRParams");
	contextOut.setLifespan("5");
	contextOut.setParameters(parameters);
	parameters.setReportPath(dmrpojo.getDmrPath());
	listOfContextOut.add(contextOut);
*/		//	response.setSpeech(speech);
	
	response.setMessages(listOfMessages);
	
	response.setSource("agent");
//	response.setContextOut(listOfContextOut);
	
	System.out.println(response.getMessages());
	return Response.status(201).entity(response).build();


					
}


if (request.getJSONObject("result").getJSONObject("metadata").getString("intentName").equals("sr_report_download")) {
	

	//String reportname = request.getJSONObject("result").getJSONArray("contexts").getJSONObject(1).getJSONObject("parameters").getString("reportname");
	
//	String mailStatus = BaseUtils.mailSendWithAttachment("premanand.naik09@gmail.com","LUVTOCRY",mailids,"hi","hello",dmrPath);

String reportname= request.getJSONObject("result").getJSONObject("parameters").getString("reportname");
String date= request.getJSONObject("result").getJSONObject("parameters").getString("date");

	JsonResponseDialogueFlow response = new JsonResponseDialogueFlow();
	Messages message = new Messages();
	List<Messages> listOfMessages = new ArrayList<Messages>();
	List<ContextOut> listOfContextOut = new ArrayList<ContextOut>();
	ContextOut contextOut = new ContextOut();
	Parameters parameters = new Parameters();
	
	String speech;  
	
	if (reportname.equalsIgnoreCase("Data Quality")) {
		speech = "click on above image to download "+reportname+" tableau report.";
	}
	else {
		
		speech = "Please tell correct report name.";
	}
	message.setSpeech(speech);
	message.setType(0);
	listOfMessages.add(message);
/*			contextOut.setName("DMRParams");
	contextOut.setLifespan("5");
	contextOut.setParameters(parameters);
	parameters.setReportPath(dmrpojo.getDmrPath());
	listOfContextOut.add(contextOut);
*/		//	response.setSpeech(speech);
	
	response.setMessages(listOfMessages);
	
	response.setSource("agent");
//	response.setContextOut(listOfContextOut);
	
	System.out.println(response.getMessages());
	return Response.status(201).entity(response).build();
					
}


if (request.getJSONObject("result").getJSONObject("metadata").getString("intentName").equals("Job_Status_success")) {
	

	String status_pass_fail = request.getJSONObject("result").getJSONObject("parameters").getString("status_pass_fail");
	String day = request.getJSONObject("result").getJSONObject("parameters").getString("date");

	
	DbOps db = new DbOps() ;

	Jobs_status faildata = new Jobs_status();
	List<Jobs_status> Jobs_status = new ArrayList<Jobs_status>();

	Jobs_status = db.getLoadStatus(status_pass_fail, day);

	
	JsonResponseDialogueFlow_jobs response = new JsonResponseDialogueFlow_jobs();
	Messages message = new Messages();
	List<Messages> listOfMessages = new ArrayList<Messages>();
	List<ContextOut> listOfContextOut = new ArrayList<ContextOut>();
	ContextOut contextOut = new ContextOut();
	Parameters parameters = new Parameters();
	Data data = new Data();
	data.setListofdata(new ArrayList<Object>(Jobs_status));
	String speech = "Report sent.";
	message.setSpeech(speech);
	message.setType(0);
	listOfMessages.add(message);
/*			contextOut.setName("DMRParams");
	contextOut.setLifespan("5");
	contextOut.setParameters(parameters);
	parameters.setReportPath(dmrpojo.getDmrPath());
	listOfContextOut.add(contextOut);
*/		//	response.setSpeech(speech);
	
	response.setMessages(listOfMessages);
	response.setData(data);
	response.setSource("agent");
//	response.setContextOut(listOfContextOut);
	
	System.out.println(response.getMessages());
	return Response.status(201).entity(response).build();


					
}


if (request.getJSONObject("result").getJSONObject("metadata").getString("intentName").equals("cache clear - yes - yes")) {
	

	String mailStatus = BaseUtils.mailSend("premanand.naik09@gmail.com","LUVTOCRY","premanand.naik@accenture.com,hawkeye.support@accenture.com","Incident no INC3018 is raised","Incident is raised for system issue");
	System.out.println(mailStatus);
				
}


if (request.getJSONObject("result").getJSONObject("metadata").getString("intentName").equals("log_incident")) {
	

	String subject = request.getJSONObject("result").getJSONObject("parameters").getString("subject");

	String mailStatus = BaseUtils.mailSend("premanand.naik09@gmail.com","LUVTOCRY","premanand.naik@accenture.com,hawkeye.support@accenture.com","Incident is raised",subject);
	System.out.println(mailStatus);
				
}

return null;
		
	}

	
		
	}



