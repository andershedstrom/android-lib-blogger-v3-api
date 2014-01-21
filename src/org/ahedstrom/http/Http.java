package org.ahedstrom.http;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;

import android.util.Log;

public class Http {

	private static final String TAG = "Http";

	public static String post(String url, Map<String, String> headers, String body) throws MalformedURLException, IOException {
		Log.d(TAG, "post -> " + url);
		BufferedWriter out = null;
		BufferedReader in = null;
		try {
			URL endpoint = URI.create(url).toURL();
			HttpURLConnection conn = (HttpURLConnection) endpoint.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod("POST");
			for (Entry<String, String> entry : headers.entrySet()) {
				conn.addRequestProperty(entry.getKey(), entry.getValue());
			}
			
			out = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "utf-8"));
			out.write(body);
			out.flush();
			
			Log.d(TAG, "response code: " + conn.getResponseCode());
			if (conn.getResponseCode() >= 400) {
				in = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
			} else {
				in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			}
			StringBuilder builder = new StringBuilder();
			String line = null;
			while( (line = in.readLine()) != null) {
				builder.append(line);
			}
			
			Log.d(TAG, "response: " + builder.toString());
			return builder.toString();
		} finally {
			try {
				if (out != null) out.close();
			} catch (IOException e) {
			}
			try {
				if (in != null) in.close();
			} catch (IOException e) {
			}
		}
	}
}
