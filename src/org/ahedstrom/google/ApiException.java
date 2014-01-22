package org.ahedstrom.google;

@SuppressWarnings("serial")
public class ApiException extends RuntimeException {

	public ApiException() {
		super();
	}

	public ApiException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	public ApiException(String detailMessage) {
		super(detailMessage);
	}

	public ApiException(Throwable throwable) {
		super(throwable);
	}

}
