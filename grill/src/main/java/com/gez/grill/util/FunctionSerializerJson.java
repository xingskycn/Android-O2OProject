package com.gez.grill.util;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

/**
 * Json序列化类.
 */
public class FunctionSerializerJson {

	/**
	 * 序列化对象.
	 */
	public static String serialize(Object icObject) {
		if (icObject != null) {
			try {
				ObjectMapper tcObjectMapper = new ObjectMapper();
				return tcObjectMapper.writeValueAsString(icObject);
			} catch (Exception e) {
				return "null";
			}
		} else {
			return "null";
		}
	}

	/**
	 * 反序列化对象.
	 */
	public static <T> T deserialize(String isJson, Class<T> icValueType) {
		try {
			ObjectMapper tcObjectMapper = new ObjectMapper();
			return tcObjectMapper.readValue(isJson, icValueType);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 反序列化对象.
	 */
	public static <T> T deserialize(String isJson, TypeReference<T> icValueType) {
		try {
			ObjectMapper tcObjectMapper = new ObjectMapper();
			return tcObjectMapper.readValue(isJson, icValueType);
		} catch (Exception e) {
			return null;
		}
	}
}
