package com.despegar.moku.util;

import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;

public class JsonUtils {

	private static ObjectMapper mapper;
	private static ObjectReader reader;
	private static ObjectWriter writer;

	static {
		mapper = new ObjectMapper();
		reader = mapper.reader().withType(new TypeReference<Map<String, String>>() {
		});
		writer = mapper.writer();
	}

	public static ObjectReader reader() {
		return reader;
	}

	public static ObjectWriter writer() {
		return writer;
	}
}
