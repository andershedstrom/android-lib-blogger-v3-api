package org.ahedstrom.bloggerapi.v3;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;


public class BlogsApi extends Api {
	private static final String TAG = "BlogsApi";

	public BlogsApi(OAuth oauth) {
		super(oauth);
	}

	public void list(final ApiCallback<List<Blogs>> callback) {
		Log.d(TAG, "invoking");
		invoke("/users/self/blogs", new Callback() {
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
