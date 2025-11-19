package com.example.soap.consumingwebservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.ws.soap.client.SoapFaultClientException;

import com.example.soap.producing_web_service.GetCountryResponse;

/**
 * CommandLineRunner implementation that runs at startup.
 * <p>
 * It invokes the SOAP service through {@link CountryClient} to fetch details
 * about a country.
 * </p>
 */
@Component
public class CountryCommandLineRunner implements CommandLineRunner {

	private static final Logger log = LoggerFactory
		.getLogger(CountryCommandLineRunner.class);

	private final CountryClient countryClient;

	public CountryCommandLineRunner(CountryClient countryClient) {
		this.countryClient = countryClient;
	}

	@Override
	public void run(String... args) throws Exception {
		try {
			String country = "Poland"; // default country

			if (args.length > 0) {
				country = args[0];
			}

			log.info("Requesting details for country: {}", country);

			GetCountryResponse response = countryClient.getCountry(country);

			log.info("Country: {}", response.getCountry().getName());
			log.info("Capital: {}", response.getCountry().getCapital());
			log.info("Population: {}", response.getCountry().getPopulation());
			log.info("Currency: {}", response.getCountry().getCurrency());
		} catch (SoapFaultClientException ex) {
			System.err
				.println("SOAP Fault: " + ex.getFaultStringOrReason()
						+ " (Code: " + ex.getFaultCode() + ")");
		}
	}
}
