package org.ahedstrom.google;

public interface ApiCallback<T> {
	void onSuccess(T response);
	void onFailure();
}
