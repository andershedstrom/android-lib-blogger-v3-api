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
	
	public void insert(final Posts entry, final boolean isDraft, final ApiCallback<Posts> callback) {
		new Thread(){
			@Override
			public void run() {
				try {
					JSONObject json = new JSONObject(invokePost(String.format(pathTemplate, entry.getBlogId(), isDraft), entry));
					callback.onSuccess(new Posts(json));
				} catch (Exception e) {
					callback.onFailure();
				}
			}
		}.start();
	}

}
