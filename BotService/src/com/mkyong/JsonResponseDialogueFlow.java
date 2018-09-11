package com.mkyong;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class JsonResponseDialogueFlow {

	@JsonProperty("messages")
		List<Messages> messages;
		String source;
		String speech;
		
		@JsonProperty("contextOut")	
		List<ContextOut> contextOut;
		
		@JsonProperty("data")
		Data data;
		
		public List<Messages> getMessages() {
			return messages;
		}
		public void setMessages(List<Messages> messages) {
			this.messages = messages;
		}
		public String getSource() {
			return source;
		}
		public void setSource(String source) {
			this.source = source;
		}
		public String getSpeech() {
			return speech;
		}
		public void setSpeech(String speech) {
			this.speech = speech;
		}
		public List<ContextOut> getContextOut() {
			return contextOut;
		}
		public void setContextOut(List<ContextOut> contextOut) {
			this.contextOut = contextOut;
		}
		public Data getData() {
			return data;
		}
		public void setData(Data data) {
			this.data = data;
		}
		

}