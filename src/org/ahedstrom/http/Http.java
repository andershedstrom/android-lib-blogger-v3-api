package org.ahedstrom.http;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;

import android.util.Log;

public class Http {

	private static final String TAG = "Http";

	public static Response get(String url)throws MalformedURLException, IOException {
		return execute("GET", url, Collections.<String, String>emptyMap(), (String)null);
	}

	public static Response post(String url, Map<String, String> headers, String body) throws MalformedURLException, IOException {
		return execute("POST", url, headers, body);
	}

	public static Response post(String url, Map<String, String> headers, ByteArrayOutputStream body) throws MalformedURLException, IOException {
		return execute("POST", url, headers, body);
	}
	
	private static Response execute(String metod, String url, Map<String, String> headers, ByteArrayOutputStream body) throws MalformedURLException, IOException {
		Response resp = new Response();
		Log.d(TAG, metod + " -> " + url);
		OutputStream out = null;
		BufferedReader in = null;
		try {
			URL endpoint = URI.create(url).toURL();
			HttpURLConnection conn = (HttpURLConnection) endpoint.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod(metod);
			for (Entry<String, String> entry : headers.entrySet()) {
				conn.addRequestProperty(entry.getKey(), entry.getValue());
			}
			
			if (body != null) {
				byte[] buffer = body.toByteArray();
				out = conn.getOutputStream();
	            out.write(buffer, 0, buffer.length);
			}
			
			resp.code = conn.getResponseCode();
			Log.d(TAG, "response code: " + resp.code);
			StringBuilder builder = new StringBuilder();
			if (resp.code >= 400) {
				in = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "utf-8"));
			} else {
				in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
			}
			
			int read;
			while ((read = in.read()) != -1) {
				builder.append((char) read);
			}
			
			resp.data = builder.toString();
			Log.d(TAG, "response: " + resp.data);
			return resp;
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
	
	private static Response execute(String metod, String url, Map<String, String> headers, String body) throws MalformedURLException, IOException {
		Response resp = new Response();
		Log.d(TAG, metod + " -> " + url);
		Log.d(TAG, "{body} -> " + body);
		BufferedWriter out = null;
		BufferedReader in = null;
		try {
			URL endpoint = URI.create(url).toURL();
			HttpURLConnection conn = (HttpURLConnection) endpoint.openConnection();
			conn.setDoOutput((body != null && body.trim().length() > 0));
			conn.setDoInput(true);
			conn.setRequestMethod(metod);
			for (Entry<String, String> entry : headers.entrySet()) {
				conn.addRequestProperty(entry.getKey(), entry.getValue());
			}
			
			if (body != null && body.trim().length() > 0) {
				out = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "utf-8"));
				out.write(body);
				out.flush();
			}
			
			resp.code = conn.getResponseCode();
			Log.d(TAG, "response code: " + resp.code);
			StringBuilder builder = new StringBuilder();
			if (resp.code >= 400) {
				in = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "utf-8"));
			} else {
				in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
			}
			
			int read;
			while ((read = in.read()) != -1) {
				builder.append((char) read);
			}
			
			resp.data = builder.toString();
			Log.d(TAG, "response: " + resp.data);
			return resp;
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
	
	public static class Response {
		public int code;
		public String data;
	}
}
