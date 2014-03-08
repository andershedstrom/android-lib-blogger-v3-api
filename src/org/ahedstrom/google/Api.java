package org.ahedstrom.google;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import org.ahedstrom.google.auth.OAuth;
import org.ahedstrom.google.bloggerapi.v3.Posts;
import org.ahedstrom.http.Http;
import org.ahedstrom.http.Http.Response;
import org.ahedstrom.http.QueryParam;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public abstract class Api {
	private static final String TAG = "Api";
	
	protected final String baseUrl; 
	
	protected final OAuth oauth;
	protected final String appName;
	
	protected Api(String baseUrl, OAuth oauth, String appName) {
		this.baseUrl = baseUrl;
		this.oauth = oauth;
		this.appName = appName;
	}

	protected String invokeGet(final String path, QueryParam ... params) {
		String url = buildUrl(path);
		if (params != null && params.length > 0) {
			StringBuilder buf = new StringBuilder(url);
			for (QueryParam qp : params) {
				if (qp != null) {
					buf.append("&").append(qp.name).append("=").append(qp.value);
				}
			}
			url = buf.toString();
		}
		Log.d(TAG, "url: " + url);
		try {
			Response r = Http.get(url);
			if (r.code >= 400) {
				doRenewOAuthToken();
				return invokeGet(path, params);
			}
			return r.data;
		} catch (MalformedURLException e) {
			Log.e(TAG, e.getMessage(), e);
			throw new ApiException(e);
		} catch (IOException e) {
			Log.e(TAG, e.getMessage(), e);
			throw new ApiException(e);
		} catch (JSONException e) {
			Log.e(TAG, e.getMessage(), e);
			throw new ApiException(e);
		}
	}
	
	protected String invokePost(final String path, final Posts entry) {
		final String url = String.format("%s%s", baseUrl, path);
		Log.d(TAG, "url: " + url);
		
		try {
			Map<String, String> headers = new HashMap<String, String>();
			headers.put("Authorization", "Bearer " + oauth.getAccessToken());
			headers.put("User-Agent", appName);
			headers.put("Content-Type", "application/json");
			
			Response post = Http.post(url, headers, entry.toString());
			if (post.code >= 400) {
				doRenewOAuthToken();
				return invokePost(path, entry);
			}
			return post.data;
		} catch (MalformedURLException e) {
			Log.e(TAG, e.getMessage(), e);
			throw new ApiException(e);
		} catch (IOException e) {
			Log.e(TAG, e.getMessage(), e);
			throw new ApiException(e);
		} catch (JSONException e) {
			Log.e(TAG, e.getMessage(), e);
			throw new ApiException(e);
		} 
	}

	protected void doRenewOAuthToken() throws MalformedURLException, IOException, JSONException {
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
	
	protected String buildUrl(String path) {
		return String.format("%s%s?access_token=%s",
				baseUrl,
				path,
				oauth.getAccessToken());
	}
}
