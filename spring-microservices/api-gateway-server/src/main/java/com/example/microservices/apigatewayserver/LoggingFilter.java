package com.example.microservices.apigatewayserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class LoggingFilter implements GlobalFilter, Ordered {

	private final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		HttpMethod method = exchange.getRequest().getMethod();
		String path = exchange.getRequest().getURI().getPath();
		logger.info("Incoming request: {} {}", method, path);
		
		return chain.filter(exchange)
				.doOnSuccess(done -> logger.info(
						"Response status: {}",
						exchange.getResponse().getStatusCode()));

	}

	@Override
	public int getOrder() {
		return 0;
	}
}