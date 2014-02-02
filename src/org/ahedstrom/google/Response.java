package org.ahedstrom.google;

import java.util.HashMap;
import java.util.Map;

public class Response<T> {

	private Map<String, String> extra;
	private T actualResponse;

	public Response(T actualResponse) {
		this(actualResponse, new HashMap<String, String>());
	}

	public Response(T actualResponse, Map<String, String> extra) {
		this.actualResponse = actualResponse;
		this.extra = extra;
	}
	
	public Response<T> addExtra(String key, String value) {
		extra.put(key, value);
		return this;
	}

	public T getActualResponse() {
		return actualResponse;
	}
}
