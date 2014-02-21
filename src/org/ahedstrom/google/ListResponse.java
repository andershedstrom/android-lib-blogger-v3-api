package org.ahedstrom.google;

import java.util.Map;

public class ListResponse<T> extends Response<Object> {

	private String nextPageToken;
	
	public ListResponse(T actualResponse, String nextPageToken, Map<String, String> extra) {
		super(actualResponse, extra);
		this.nextPageToken = nextPageToken;
	}

	public ListResponse(T actualResponse, String nextPageToken) {
		super(actualResponse);
		this.nextPageToken = nextPageToken;
	}

	public ListResponse(T actualResponse) {
		super(actualResponse);
	}
	
	public String getNextPageToken() {
		return nextPageToken;
	}
}
