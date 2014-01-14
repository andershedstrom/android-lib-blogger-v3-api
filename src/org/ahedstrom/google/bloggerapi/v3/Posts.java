package org.ahedstrom.google.bloggerapi.v3;

import org.ahedstrom.google.Base;
import org.json.JSONException;
import org.json.JSONObject;

public class Posts extends Base {

	private static final String KIND = "blogger#post";
	
	Posts(JSONObject json) {
		super(json);
	}

	public int getTotalItems() {
		return getInt("totalItems");
	}

	public String getSelfLink() {
		return getString("selfLink");
	}
	
	public String getBlogId() {
		try {
			return getJsonObject("blog").getString("id");
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	public static class Builder {
		private JSONObject json;
		
		public Builder() {
			try {
				this.json = new JSONObject();
				json.put("kind", KIND);
			} catch (JSONException e) {
				throw new RuntimeException(e);
			}
		}
	
		public Builder setBlogId(String blogId) {
			try {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("id", blogId);
				json.put("blog", jsonObject);
				return this;
			} catch (JSONException e) {
				throw new RuntimeException(e);
			}
		}
		
		public Builder setTitle(String title) {
			try {
				json.put("title", title);
				return this;
			} catch (JSONException e) {
				throw new RuntimeException(e);
			}
		}

		public Builder setContent(String content) {
			try {
				json.put("content", content);
				return this;
			} catch (JSONException e) {
				throw new RuntimeException(e);
			}
		}
		
		public Posts build() {
			return new Posts(json);
		}
	}
}
