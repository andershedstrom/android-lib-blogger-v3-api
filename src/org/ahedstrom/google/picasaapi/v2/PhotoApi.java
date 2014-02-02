package org.ahedstrom.google.picasaapi.v2;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import org.ahedstrom.google.ApiCallback;
import org.ahedstrom.google.auth.OAuth;
import org.ahedstrom.http.Http;
import org.ahedstrom.http.Http.Response;
import org.json.JSONException;

import android.util.Log;

public class PhotoApi extends PicasaApi {

	protected static final String TAG = "PhotoApi";

	public PhotoApi(OAuth oauth, String appName) {
		super(oauth, appName);
	}

	public String post(final String albumId, final String fileName, final InputStream data) throws MalformedURLException, IOException, JSONException {
		return invoke(albumId, fileName, createByteArrayOutputStream(data));
	}
	
	public void post(final String albumId, final String fileName, final InputStream data, final ApiCallback<String> callback) {
		new Thread(){
			@Override
			public void run() {
				try {
					callback.onSuccess(invoke(albumId, fileName, createByteArrayOutputStream(data)));
				} catch (Exception e) {
					Log.e(TAG, e.getMessage(), e);
					callback.onFailure();
				}
			}
		}.start();
	}
	
	private ByteArrayOutputStream createByteArrayOutputStream(InputStream data) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		if (data != null) {
			byte[] buffer = new byte[4096];
	        int n = 0;
	        while (-1 != (n = data.read(buffer))) {
	            out.write(buffer, 0, n);
	        }
		}
		return out;
	}
	
	private String invoke(String albumId, String fileName, ByteArrayOutputStream photoStream) throws MalformedURLException, IOException, JSONException {
		final String url = String.format("%s/albumid/%s", baseUrl, albumId);
		Log.d(TAG, "url: " + url);
		
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Authorization", "Bearer " + oauth.getAccessToken());
		headers.put("User-Agent", appName);
		headers.put("Content-Type", "image/jpeg");
		headers.put("Content-Length", String.valueOf(photoStream.size()));
		headers.put("Slug", fileName);
		
		Response post = Http.post(url, headers, photoStream);
		if (post.code >= 400) {
			doRenewOAuthToken();
			return invoke(albumId, fileName, photoStream);
		}
		return post.data;
	}
	
	
}
