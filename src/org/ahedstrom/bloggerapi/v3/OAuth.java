package org.ahedstrom.bloggerapi.v3;

public interface OAuth {
	String getAccessToken();
	String getRefreshToken();
	String getClientId();
	
	void onAccessTokenExpired(String newAccessToken);
}
