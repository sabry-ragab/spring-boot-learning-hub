package com.example.soap.consumingwebservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.WebServiceMessage;

import java.io.ByteArrayOutputStream;

public class LoggingInterceptor implements ClientInterceptor {

	private static final Logger log = LoggerFactory  
		.getLogger(LoggingInterceptor.class);

	@Override
	public boolean handleRequest(MessageContext messageContext) {
		logMessage("SOAP Request", messageContext.getRequest());
		return true;
	}

	@Override
	public boolean handleResponse(MessageContext messageContext) {
		logMessage("SOAP Response", messageContext.getResponse());
		return true;
	}

	@Override
	public boolean handleFault(MessageContext messageContext) {
		logMessage("SOAP Fault", messageContext.getResponse());
		return true;
	}

	@Override
	public void afterCompletion(MessageContext messageContext, Exception ex) {
	}

	private void logMessage(String label, WebServiceMessage message) {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			message.writeTo(out);
			log.info("{}:\n{}", label, out.toString());
		} catch (Exception e) {
			log.error("Error logging SOAP message", e);
		}
	}
}
