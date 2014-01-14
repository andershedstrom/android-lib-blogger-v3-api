package org.ahedstrom.google.bloggerapi.v3;

import org.ahedstrom.google.Base;
import org.json.JSONObject;

public class Pages extends Base {

	Pages(JSONObject json) {
		super(json);
	}

	public int getTotalItems() {
		return getInt("totalItems");
	}

	public String getSelfLink() {
		return getString("selfLink");
	}
}
