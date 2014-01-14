package org.ahedstrom.google.auth;

public interface OAuth {
	String getAccessToken();
	String getRefreshToken();
	String getClientId();
	
	void onAccessTokenExpired(String newAccessToken);
}
