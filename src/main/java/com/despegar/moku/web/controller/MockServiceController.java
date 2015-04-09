package com.despegar.moku.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.despegar.moku.service.MockServiceService;

@RestController
public class MockServiceController {

	private static final Logger logger = LoggerFactory.getLogger(MockServiceController.class);

	@Autowired
	private MockServiceService mockServiceService;

	@RequestMapping(value = "/service", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String jsonGetService() {
		return "{\"clave\":\"valor\"}";
	}

}
