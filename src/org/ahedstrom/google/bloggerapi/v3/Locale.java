package org.ahedstrom.google.bloggerapi.v3;

import org.ahedstrom.google.Base;
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
