package com.example.soap.producingwebservice;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.callback.CallbackHandler;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurer;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;
import org.springframework.ws.soap.security.wss4j2.callback.SimplePasswordValidationCallbackHandler;
import org.springframework.ws.soap.security.wss4j2.support.CryptoFactoryBean;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WebServiceConfig implements WsConfigurer {
	@Bean
	ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(
			ApplicationContext applicationContext) {
		MessageDispatcherServlet servlet = new MessageDispatcherServlet();
		servlet.setApplicationContext(applicationContext);
		servlet.setTransformWsdlLocations(true);
		return new ServletRegistrationBean<>(servlet, "/ws/*");
	}

	@Bean(name = "countries")
	DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema countriesSchema) {
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("CountriesPort");
		wsdl11Definition.setLocationUri("/ws");
		wsdl11Definition
			.setTargetNamespace(
					"http://soap.example.com/producing-web-service");
		wsdl11Definition.setSchema(countriesSchema);
		return wsdl11Definition;
	}

	@Bean
	XsdSchema countriesSchema() {
		return new SimpleXsdSchema(new ClassPathResource("countries.xsd"));
	}

	// WS-Sec

	@Bean
	public Wss4jSecurityInterceptor serverSecurityInterceptor() {
		try {
			Wss4jSecurityInterceptor interceptor = new Wss4jSecurityInterceptor();

			// --- Validate client messages ---
			// Client sends UsernameToken + Timestamp, so server validates them
			interceptor.setValidationActions("UsernameToken Timestamp");
			interceptor.setValidationCallbackHandler(callbackHandler());
			interceptor.setTimestampStrict(true);

			// --- Secure server responses ---
			// Server signs outgoing SOAP messages with its private key
			interceptor.setSecurementActions("Signature");
			interceptor.setSecurementUsername("serverkey"); // alias in
															// server-keystore.jks
			interceptor.setSecurementPassword("P@ssw0rd"); // key password
			interceptor
				.setSecurementSignatureCrypto(cryptoFactory().getObject());

			return interceptor;

		} catch (Exception e) {
			throw new BeanCreationException(
					"Failed to create server Wss4jSecurityInterceptor", e);
		}
	}

	@Bean
	CallbackHandler callbackHandler() {
		SimplePasswordValidationCallbackHandler handler = new SimplePasswordValidationCallbackHandler();
		Map<String, String> users = new HashMap<>();
		users.put("sabry", "secret123");
		handler.setUsersMap(users);
		return handler;
	}

	@Bean
	CryptoFactoryBean cryptoFactory() {

		try {
			CryptoFactoryBean cryptoFactoryBean = new CryptoFactoryBean();
			cryptoFactoryBean.setKeyStorePassword("P@ssw0rd");
			cryptoFactoryBean
				.setKeyStoreLocation(
						new ClassPathResource("server-keystore.jks"));
			return cryptoFactoryBean;
		} catch (IOException e) {
			throw new BeanCreationException("Failed to load keystore", e);
		}

	}

	@Override
	public void addInterceptors(List<EndpointInterceptor> interceptors) {
		interceptors.add(serverSecurityInterceptor());
	}

}
