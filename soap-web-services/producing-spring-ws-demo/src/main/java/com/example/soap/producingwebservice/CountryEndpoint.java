package com.example.soap.producingwebservice;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.example.soap.producing_web_service.GetCountryRequest;
import com.example.soap.producing_web_service.GetCountryResponse;

@Endpoint
public class CountryEndpoint {
	private static final String NAMESPACE_URI = "http://soap.example.com/producing-web-service";

	private CountryRepository countryRepository;

	public CountryEndpoint(CountryRepository countryRepository) {
		this.countryRepository = countryRepository;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCountryRequest")
	@ResponsePayload
	public GetCountryResponse getCountry(
			@RequestPayload GetCountryRequest request) {
		if (request.getName() == null) {
			throw new InvalidCountryException("Country name is required");
		}
		GetCountryResponse response = new GetCountryResponse();
		response.setCountry(countryRepository.findCountry(request.getName()));

		return response;
	}
}
