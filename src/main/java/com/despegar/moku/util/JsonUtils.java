package com.despegar.moku.util;

import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;

public class JsonUtils {

	private static ObjectMapper mapper;
	private static ObjectReader reader;
	private static ObjectWriter writer;

	static {
		mapper = new ObjectMapper();
		reader = mapper.reader();
		writer = mapper.writer();
	}

	public static ObjectReader reader() {
		return reader;
	}

	public static ObjectWriter writer() {
		return writer;
	}

	public static Optional<Object> getValue(String json, String path) {
		Object o = null;
		try {
			o = JsonPath.read(json, path);
		} catch (PathNotFoundException e) {
		}
		return Optional.ofNullable(o);
	}
}
