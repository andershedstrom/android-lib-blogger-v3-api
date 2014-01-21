package org.ahedstrom.google;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import org.ahedstrom.google.auth.OAuth;
import org.ahedstrom.google.bloggerapi.v3.Posts;
import org.ahedstrom.http.Http;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public abstract class Api {
	private static final String TAG = "Api";
	
	public interface Callback {
		void onSuccess(JSONObject jsonObject);
		void onFailure();
	}
	
	private final String baseUrl; 
	private final static AsyncHttpClient client = new AsyncHttpClient();
	
	private final OAuth oauth;
	private final String appName;
	
	protected Api(String baseUrl, OAuth oauth, String appName) {
		this.baseUrl = baseUrl;
		this.oauth = oauth;
		this.appName = appName;
	}

	protected void invokeGet(final String path, final Callback callback) {
		String url = buildUrl(path);
		Log.d(TAG, "url: " + url);
		client.get(url, new ResponseHander(callback){
			@Override
			public void onSuccess(JSONObject json) {
				callback.onSuccess(json);
			}
			@Override
			public void onFailure(Throwable arg0, JSONObject error) {
				try {
					if (401 == error.getJSONObject("error").getInt("code")) {
						renewOAuthToken(path, callback);
					} else {
						callback.onFailure();
					}
				} catch (JSONException e) {
					callback.onFailure();
				}
			}
		});
	}
	
	protected void invokePost(final String path, final Posts entry, final Callback callback) {
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
					
					String resp = Http.post(url, headers, entry.toString());
					JSONObject r = new JSONObject(resp.toString());
					if (r.optJSONObject("error") == null) {
						callback.onSuccess(r);
					} else {
						if (401 == r.getJSONObject("error").getInt("code")) {
							renewOAuthToken(path, entry, callback);
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

	protected void renewOAuthToken(String path, Posts entry, Callback callback) {
		try {
			doRenewOAuthToken();
			invokePost(path, entry, callback);
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

	private void doRenewOAuthToken() throws MalformedURLException, IOException, JSONException {
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/x-www-form-urlencoded");
		
		String body = String.format("refresh_token=%s&client_id=%s&grant_type=refresh_token", 
				oauth.getRefreshToken(),
				oauth.getClientId());
		String resp = Http.post("https://accounts.google.com/o/oauth2/token", headers, body);
		JSONObject json = new JSONObject(resp);
		String renewedAccessToken = json.getString("access_token");
		oauth.onAccessTokenExpired(renewedAccessToken);
	}
	
	private void renewOAuthToken(final String path, final Callback callback) {
		try {
			doRenewOAuthToken();
			invokeGet(path, callback);
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
	
	private String buildUrl(String path) {
		return String.format("%s%s?access_token=%s",
				baseUrl,
				path,
				oauth.getAccessToken());
	}
	
	private static class ResponseHander extends JsonHttpResponseHandler {
		private final Callback callback;

		ResponseHander(Callback callback) {
			this.callback = callback;
		}

		@Override
		public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
			this.callback.onFailure();
		}
		@Override
		public void onFailure(int statusCode, Header[] headers,
				String responseBody, Throwable e) {
			this.callback.onFailure();
		}
		@Override
		public void onFailure(int statusCode, Header[] headers, Throwable e,
				JSONArray errorResponse) {
			this.callback.onFailure();
		}
		@Override
		public void onFailure(int statusCode, Header[] headers, Throwable e,
				JSONObject errorResponse) {
			this.callback.onFailure();
		}
		@Override
		public void onFailure(int statusCode, Throwable e,
				JSONArray errorResponse) {
			this.callback.onFailure();
		}
		@Override
		public void onFailure(int statusCode, Throwable e,
				JSONObject errorResponse) {
			this.callback.onFailure();
		}
		@Override
		public void onFailure(String responseBody, Throwable error) {
			this.callback.onFailure();
		}
		@Override
		public void onFailure(Throwable e, JSONArray errorResponse) {
			this.callback.onFailure();
		}
		@Override
		public void onFailure(Throwable e, JSONObject errorResponse) {
			this.callback.onFailure();
		}
	}
}
