package org.ahedstrom.google.bloggerapi.v3;

import java.util.ArrayList;
import java.util.List;

import org.ahedstrom.google.ApiCallback;
import org.ahedstrom.google.auth.OAuth;
import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;


public class BlogsApi extends BloggerApi {
	private static final String TAG = "BlogsApi";

	public BlogsApi(String appName, OAuth oauth) {
		super(appName, oauth);
	}

	public void list(final ApiCallback<List<Blogs>> callback) {
		new Thread(){
			@Override
			public void run() {
				try {
					JSONObject jsonObject = new JSONObject(invokeGet("/users/self/blogs"));
					List<Blogs> lst = new ArrayList<Blogs>();
					JSONArray jsonArray = jsonObject.getJSONArray("items");
					for (int i=0; i<jsonArray.length(); ++i) {
						JSONObject json = jsonArray.getJSONObject(i);
						lst.add(new Blogs(json));
					}
					Log.d(TAG, lst.size() + " blog(s) fetched");
					callback.onSuccess(lst);
				} catch (Exception e) {
					callback.onFailure();
				}
			}
		}.start();
	}
	
}
