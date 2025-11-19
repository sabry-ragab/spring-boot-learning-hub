package com.example.microservices.limitsservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LimitsConfigurationController {

	private final LimitsServiceProperties properties;

	public LimitsConfigurationController(LimitsServiceProperties properties) {
		this.properties = properties;
	}

	@GetMapping("/limits")
	public LimitConfiguration retrieveLimits() {
		return new LimitConfiguration(properties.getMaximum(),
				properties.getMinimum());
	}
}