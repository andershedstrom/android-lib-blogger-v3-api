package org.ahedstrom.bloggerapi.v3;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.format.Time;

abstract class Base {

	protected final JSONObject json;

	Base(JSONObject json) {
		this.json = json;
	}
	
	public JSONObject getJsonObject() {
		return json;
	}
	
	public Object get(String name) {
		try {
			return json.get(name);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	public Object opt(String name) {
		return json.opt(name);
	}

	protected String optString(String name) {
		return json.optString(name);
	}

	protected int getInt(String name) {
		try {
			return json.getInt(name);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}
	
	protected JSONObject getJsonObject(String name) {
		try {
			return json.getJSONObject(name);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}
	
	protected String getString(String name) {
		try {
			return json.getString(name);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}
	
	protected Time getTime(String name) {
		Time t = null;
		String value = optString(name);
		if (value != null) {
			t = new Time();
			t.parse3339(value);
		}
		return t;
	}
}
