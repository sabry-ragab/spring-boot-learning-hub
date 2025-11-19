package com.example.soap.consumingwebservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import com.example.soap.producing_web_service.GetCountryRequest;
import com.example.soap.producing_web_service.GetCountryResponse;

/**
 * CountryClient - A SOAP client for consuming the "GetCountry" service.
 *
 * <p>
 * This class extends {@link WebServiceGatewaySupport}, which provides
 * convenient access to a
 * {@link org.springframework.ws.client.core.WebServiceTemplate}. It allows us
 * to easily marshal a request object, send it to a SOAP endpoint, and unmarshal
 * the response back into a Java object.
 * </p>
 */
public class CountryClient extends WebServiceGatewaySupport {

	private static final Logger log = LoggerFactory
		.getLogger(CountryClient.class);

	/**
	 * Calls the SOAP service with the given country name and returns the
	 * response.
	 *
	 * @param country the country name to request
	 * @return {@link GetCountryResponse} containing country details
	 */
	public GetCountryResponse getCountry(String country) {

		// Create and populate the request object
		GetCountryRequest request = new GetCountryRequest();
		request.setName(country);

		log.info("Requesting location for " + country);

		// Send the request and receive the response
		GetCountryResponse response = (GetCountryResponse) getWebServiceTemplate()
			.marshalSendAndReceive("http://localhost:8080/ws/countries",
					request, new SoapActionCallback(
							"http://soap.example.com/producing-web-service/GetCountryRequest"));

		return response;

	}
}
