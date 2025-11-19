package com.example.soap.producingwebservice;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

@SoapFault(faultCode = FaultCode.CLIENT)
public class InvalidCountryException extends RuntimeException {
	public InvalidCountryException(String message) {
		super(message);
	}
}