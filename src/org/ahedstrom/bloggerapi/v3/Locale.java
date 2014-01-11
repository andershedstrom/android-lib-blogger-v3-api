package org.ahedstrom.bloggerapi.v3;

import org.json.JSONObject;

public class Locale extends Base {

	Locale(JSONObject json) {
		super(json);
	}

	public String getLanguage() {
		return optString("language");
	}

	public String getCountry() {
		return optString("country");
	}
	
	public String getVariant() {
		return optString("variant");
	}
}
