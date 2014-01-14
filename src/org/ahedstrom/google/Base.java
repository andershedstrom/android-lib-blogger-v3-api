package org.ahedstrom.google;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.format.Time;

public abstract class Base {

	protected final JSONObject json;

	protected Base(JSONObject json) {
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
	
	protected JSONArray getJsonArray(String name) {
		try {
			return json.getJSONArray(name);
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
	
	@Override
	public String toString() {
		return json.toString();
	}
}
