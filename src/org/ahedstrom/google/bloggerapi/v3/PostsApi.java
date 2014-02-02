package org.ahedstrom.google.bloggerapi.v3;

import java.util.ArrayList;
import java.util.List;

import org.ahedstrom.google.ApiCallback;
import org.ahedstrom.google.Response;
import org.ahedstrom.google.auth.OAuth;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PostsApi extends BloggerApi {

	private static final String pathTemplate = "/blogs/%s/posts?isDraft=%s";
	private static final String listPathTemplate = "/blogs/%s/posts";
	
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
	
	public void list(final String blogId, final ApiCallback<Response<List<Posts>>> callback) {
		new Thread() {
			@Override
			public void run() {
				try {
					JSONObject json = new JSONObject(invokeGet(String.format(listPathTemplate, blogId)));
					ArrayList<Posts> lst = new ArrayList<Posts>();
					Response<List<Posts>> r = new Response<List<Posts>>(lst);
					String nextPageToken = json.optString("nextPageToken");
					if (nextPageToken != null && nextPageToken.trim().length() > 0) {
						r.addExtra("nextPageToken", nextPageToken);
					}
					JSONArray items = json.getJSONArray("items");
					for (int i=0; i<items.length(); ++i) {
						lst.add(new Posts(items.getJSONObject(i)));
					}
					callback.onSuccess(r);
				} catch (JSONException e) {
					callback.onFailure();
				}
			}
		}.start();
	}

}
