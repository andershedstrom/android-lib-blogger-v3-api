package org.ahedstrom.google.bloggerapi.v3;

import java.util.ArrayList;
import java.util.List;

import org.ahedstrom.google.ApiCallback;
import org.ahedstrom.google.auth.OAuth;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;


public class BlogsApi extends BloggerApi {
	private static final String TAG = "BlogsApi";

	public BlogsApi(String appName, OAuth oauth) {
		super(appName, oauth);
	}

	public void list(final ApiCallback<List<Blogs>> callback) {
		Log.d(TAG, "invoking");
		invokeGet("/users/self/blogs", new Callback<JSONObject>() {
			@Override
			public void onSuccess(JSONObject jsonObject) {
				List<Blogs> lst = new ArrayList<Blogs>();
				try {
					JSONArray jsonArray = jsonObject.getJSONArray("items");
					for (int i=0; i<jsonArray.length(); ++i) {
						JSONObject json = jsonArray.getJSONObject(i);
						lst.add(new Blogs(json));
					}
					Log.d(TAG, lst.size() + " blog(s) fetched");
					callback.onSuccess(lst);
				} catch (JSONException e) {
					onFailure();
				}
			}
			@Override
			public void onFailure() {
				callback.onFailure();
			}
		});
	}
	
}
