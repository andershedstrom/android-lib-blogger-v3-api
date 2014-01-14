package org.ahedstrom.google.googleplus;

import org.ahedstrom.google.Base;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class People extends Base {

	protected People(JSONObject json) {
		super(json);
	}
	
	public String getAccountEmail() {
		try {
			JSONArray jsonArray = getJsonArray("emails");
			if (jsonArray.length() > 0) {
				for (int i=0; i<jsonArray.length(); ++i) {
					if ("account".equals(jsonArray.getJSONObject(i).getString("type"))){
						return jsonArray.getJSONObject(i).getString("value");
					}
				}
			}
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
		return null;
	}

}
