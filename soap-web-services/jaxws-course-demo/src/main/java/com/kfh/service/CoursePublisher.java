package com.kfh.service;

import jakarta.xml.ws.Endpoint;

public class CoursePublisher {
	public static void main(String[] args) {
		Endpoint
			.publish("http://localhost:9090/ws/course",
					new CourseServiceImpl());
		System.out
			.println(
					"Course SOAP Web Service is published at http://localhost:9090/ws/course?wsdl");
	}
}
