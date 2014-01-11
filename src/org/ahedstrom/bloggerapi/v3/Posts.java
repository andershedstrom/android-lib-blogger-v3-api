package org.ahedstrom.bloggerapi.v3;

import org.json.JSONObject;

public class Posts extends Base {

	Posts(JSONObject json) {
		super(json);
	}

	public int getTotalItems() {
		return getInt("totalItems");
	}

	public String getSelfLink() {
		return getString("selfLink");
	}
}
