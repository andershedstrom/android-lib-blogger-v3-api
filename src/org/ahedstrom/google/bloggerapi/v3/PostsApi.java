package org.ahedstrom.google.bloggerapi.v3;

import org.ahedstrom.google.ApiCallback;
import org.ahedstrom.google.auth.OAuth;
import org.json.JSONObject;

import android.content.Context;

public class PostsApi extends BloggerApi {

	private static final String pathTemplate = "/blogs/%s/posts";
	private final Context ctx;
	
	public PostsApi(Context ctx, OAuth oauth) {
		super(oauth);
		this.ctx = ctx;
	}
	
	public void insert(Posts entry, final ApiCallback<Posts> callback) {
		invokePost(ctx, 
			String.format(pathTemplate, entry.getBlogId()),
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
