package com.example.microservices.currencyexchangeservice;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("/currency-exchange")
public class CurrencyExchangeController {

	private final ExchangeValueRepository repository;

	private final Environment environment;

	public CurrencyExchangeController(ExchangeValueRepository repository, Environment environment) {
		this.repository = repository;
		this.environment = environment;
	}

	@GetMapping("/from/{from}/to/{to}")
	public ExchangeValue retrieveExchangeValue(@PathVariable String from, @PathVariable String to) {
		ExchangeValue exchangeValue = repository.findByFromAndTo(from, to);
		exchangeValue.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
		return exchangeValue;
	}

	@GetMapping("/test-fault-tolerance")
	@CircuitBreaker(name = "currencyExchangeCB", fallbackMethod = "fallbackForTestFaultTolerance")
	public String testFaultTolerance() {
		if (Math.random() < 0.5) {
			throw new RuntimeException("Simulated service failure");
		}
		return "Service is working fine!";
	}

	public String fallbackForTestFaultTolerance(Throwable t) {
		return "Fallback response: Service is currently unavailable.";
	}
}