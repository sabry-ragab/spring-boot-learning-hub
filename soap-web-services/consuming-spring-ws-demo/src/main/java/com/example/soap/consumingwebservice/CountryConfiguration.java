package com.example.soap.consumingwebservice;

import java.io.IOException;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;
import org.springframework.ws.soap.security.wss4j2.support.CryptoFactoryBean;

/**
 * CountryConfiguration - Spring configuration class for setting up the SOAP
 * client beans required to consume the "Country" SOAP web service.
 */
@Configuration
public class CountryConfiguration {

	@Bean
	Jaxb2Marshaller marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();

		// This package must match the package in the <generatePackage>
		// specified in pom.xml or inferred from the WSDL/XSD targetNamespace.
		// Currently pointing to the producing web service package for
		// demonstration.
		marshaller.setContextPath("com.example.soap.producing_web_service");

		// Alternative:
		// marshaller.setContextPath("com.example.consumingwebservice.wsdl");

		return marshaller;
	}

	@Bean
	CountryClient countryClient(Jaxb2Marshaller marshaller) {
		CountryClient client = new CountryClient();

		// Default URI of the SOAP service endpoint
		client.setDefaultUri("http://localhost:8080/ws");

		// Configure marshaller/unmarshaller for object <-> XML conversion
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);

		// Optional: Add interceptors for logging, security, etc.
		client
			.setInterceptors(new ClientInterceptor[] { new LoggingInterceptor(),
					clientSecurityInterceptor() });

		return client;
	}

	// --- WS-Security configuration for SOAP client ---
	@Bean
	Wss4jSecurityInterceptor clientSecurityInterceptor() {
		try {
			Wss4jSecurityInterceptor interceptor = new Wss4jSecurityInterceptor();

			// Client will only authenticate with UsernameToken & Timestamp
			interceptor.setSecurementActions("UsernameToken Timestamp");
			interceptor.setSecurementUsername("sabry");
			interceptor.setSecurementPassword("secret123");

			// Verify server signature
			interceptor.setValidationActions("Signature");
			interceptor
				.setValidationSignatureCrypto(
						clientCryptoFactory().getObject());

			return interceptor;
		} catch (Exception e) {
			throw new BeanCreationException(
					"Failed to create Wss4jSecurityInterceptor", e);
		}
	}

	@Bean
	CryptoFactoryBean clientCryptoFactory() {
		try {
			CryptoFactoryBean cryptoFactoryBean = new CryptoFactoryBean();
			cryptoFactoryBean.setKeyStorePassword("P@ssw0rd");
			cryptoFactoryBean
				.setKeyStoreLocation(
						new ClassPathResource("client-truststore.jks"));
			return cryptoFactoryBean;
		} catch (IOException e) {
			throw new BeanCreationException("Failed to load keystore", e);
		}
	}

}