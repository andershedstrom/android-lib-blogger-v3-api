package org.ahedstrom.google.googleplus;

import org.ahedstrom.google.ApiCallback;
import org.ahedstrom.google.auth.OAuth;
import org.json.JSONObject;

import android.util.Log;


public class PeopleApi extends GooglePlusApi {
	private static final String TAG = "PeopleApi";

	public PeopleApi(String appName, OAuth oauth) {
		super(appName, oauth);
	}
	
	public void get(final ApiCallback<People> callback) {
		Log.d(TAG, "invoking");
		invokeGet("/people/me", new Callback() {
			@Override
			public void onSuccess(JSONObject jsonObject) {
				Log.d(TAG, jsonObject.toString());
				callback.onSuccess(new People(jsonObject));
			}
			@Override
			public void onFailure() {
				callback.onFailure();
			}
		});
	}
}
