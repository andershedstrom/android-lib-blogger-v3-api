package org.ahedstrom.bloggerapi.v3;

public interface ApiCallback<T> {
	void onSuccess(T response);
	void onFailure();
}
