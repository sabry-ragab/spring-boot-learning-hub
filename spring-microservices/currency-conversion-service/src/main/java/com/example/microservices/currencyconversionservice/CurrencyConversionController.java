package com.example.microservices.currencyconversionservice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CurrencyConversionController {

	private final RestTemplate restTemplate;
	private final CurrencyExchangeServiceProxy proxy;

	public CurrencyConversionController(RestTemplateBuilder restTemplateBuilder, CurrencyExchangeServiceProxy proxy) {
		this.restTemplate = restTemplateBuilder.build();
		this.proxy = proxy;
	}

	@GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion calculateCurrencyConversion(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) {

		Map<String, String> pathVariables = new HashMap<>();
		pathVariables.put("from", from);
		pathVariables.put("to", to);

		CurrencyConversion currencyConversion = restTemplate.getForObject(
				"http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversion.class, pathVariables);

		currencyConversion.setQuantity(quantity);
		currencyConversion.setTotalCalculatedAmount(quantity.multiply(currencyConversion.getConversionMultiple()));

		return currencyConversion;
	}

	@GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion calculateCurrencyConversionFiegn(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) {

		CurrencyConversion currencyConversion = proxy.retrieveExchangeValue(from, to);

		currencyConversion.setQuantity(quantity);
		currencyConversion.setTotalCalculatedAmount(quantity.multiply(currencyConversion.getConversionMultiple()));

		return currencyConversion;
	}
}