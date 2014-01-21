package org.ahedstrom.google.bloggerapi.v3;

import org.ahedstrom.google.ApiCallback;
import org.ahedstrom.google.auth.OAuth;
import org.json.JSONObject;

public class PostsApi extends BloggerApi {

	private static final String pathTemplate = "/blogs/%s/posts?isDraft=%s";
	
	public PostsApi(String appName, OAuth oauth) {
		super(appName, oauth);
	}
	
	public void insert(Posts entry, final ApiCallback<Posts> callback) {
		insert(entry, false, callback);
	}
	
	public void insert(Posts entry, boolean isDraft, final ApiCallback<Posts> callback) {
		invokePost(String.format(pathTemplate, entry.getBlogId(), isDraft),
			entry,
			new Callback() {
				@Override
				public void onSuccess(JSONObject response) {
					callback.onSuccess(new Posts(response));
				}
				@Override
				public void onFailure() {
					callback.onFailure();
				}
			}
		);
	}

}
