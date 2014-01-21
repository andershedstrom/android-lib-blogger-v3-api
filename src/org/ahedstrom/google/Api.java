package org.ahedstrom.google;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import org.ahedstrom.google.auth.OAuth;
import org.ahedstrom.google.bloggerapi.v3.Posts;
import org.ahedstrom.http.Http;
import org.ahedstrom.http.Http.Response;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public abstract class Api {
	private static final String TAG = "Api";
	
	public interface Callback<T> {
		void onSuccess(T response);
		void onFailure();
	}
	
	private final String baseUrl; 
	
	private final OAuth oauth;
	private final String appName;
	
	protected Api(String baseUrl, OAuth oauth, String appName) {
		this.baseUrl = baseUrl;
		this.oauth = oauth;
		this.appName = appName;
	}

	protected void invokeGetString(final String path, final Callback<String> callback) {
		final String url = buildUrl(path);
		Log.d(TAG, "url: " + url);
		new Thread(){
			@Override
			public void run() {
				try {
					Response r = Http.get(url);
					if (r.code >= 400) {
						doRenewOAuthToken();
						invokeGetString(path, callback);
					} else {
						callback.onSuccess(r.data);
					}
				} catch (MalformedURLException e) {
					Log.e(TAG, e.getMessage(), e);
					callback.onFailure();
				} catch (IOException e) {
					Log.e(TAG, e.getMessage(), e);
					callback.onFailure();
				} catch (JSONException e) {
					Log.e(TAG, e.getMessage(), e);
					callback.onFailure();
				}
			}
		}.start();
	}
	
	protected void invokeGet(final String path, final Callback<JSONObject> callback) {
		final String url = buildUrl(path);
		Log.d(TAG, "url: " + url);
		
		new Thread(){
			@Override
			public void run() {
				try {
					String resp = Http.get(url).data;
					JSONObject r = new JSONObject(resp.toString());
					if (r.optJSONObject("error") == null) {
						callback.onSuccess(r);
					} else {
						if (401 == r.getJSONObject("error").getInt("code")) {
							doRenewOAuthToken();
							invokeGet(path, callback);
						} else {
							callback.onFailure();
						}
					}
				} catch (MalformedURLException e) {
					Log.e(TAG, e.getMessage(), e);
					callback.onFailure();
				} catch (IOException e) {
					Log.e(TAG, e.getMessage(), e);
					callback.onFailure();
				} catch (JSONException e) {
					Log.e(TAG, e.getMessage(), e);
					callback.onFailure();
				}
			}
		}.start();
	}
	
	protected void invokePost(final String path, final Posts entry, final Callback<JSONObject> callback) {
		final String url = String.format("%s%s", baseUrl, path);
		Log.d(TAG, "url: " + url);
		
		new Thread() {
			@Override
			public void run() {
				try {
					Map<String, String> headers = new HashMap<String, String>();
					headers.put("Authorization", "Bearer " + oauth.getAccessToken());
					headers.put("User-Agent", appName);
					headers.put("Content-Type", "application/json");
					
					String resp = Http.post(url, headers, entry.toString()).data;
					JSONObject r = new JSONObject(resp);
					if (r.optJSONObject("error") == null) {
						callback.onSuccess(r);
					} else {
						if (401 == r.getJSONObject("error").getInt("code")) {
							doRenewOAuthToken();
							invokePost(path, entry, callback);
						} else {
							callback.onFailure();
						}
					}
				} catch (MalformedURLException e) {
					Log.e(TAG, e.getMessage(), e);
					callback.onFailure();
				} catch (IOException e) {
					Log.e(TAG, e.getMessage(), e);
					callback.onFailure();
				} catch (JSONException e) {
					Log.e(TAG, e.getMessage(), e);
					callback.onFailure();
				} 
			}
		}.start();
	}

	private void doRenewOAuthToken() throws MalformedURLException, IOException, JSONException {
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/x-www-form-urlencoded");
		
		String body = String.format("refresh_token=%s&client_id=%s&grant_type=refresh_token", 
				oauth.getRefreshToken(),
				oauth.getClientId());
		String resp = Http.post("https://accounts.google.com/o/oauth2/token", headers, body).data;
		JSONObject json = new JSONObject(resp);
		String renewedAccessToken = json.getString("access_token");
		oauth.onAccessTokenExpired(renewedAccessToken);
	}
	
	private String buildUrl(String path) {
		return String.format("%s%s?access_token=%s",
				baseUrl,
				path,
				oauth.getAccessToken());
	}
}
