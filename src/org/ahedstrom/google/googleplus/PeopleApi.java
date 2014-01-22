package org.ahedstrom.google.googleplus;

import org.ahedstrom.google.ApiCallback;
import org.ahedstrom.google.auth.OAuth;
import org.json.JSONObject;


public class PeopleApi extends GooglePlusApi {
	public PeopleApi(String appName, OAuth oauth) {
		super(appName, oauth);
	}
	
	public void get(final ApiCallback<People> callback) {
		new Thread(){
			@Override
			public void run() {
				try {
					JSONObject jsonObject = new JSONObject(invokeGet("/people/me"));
					callback.onSuccess(new People(jsonObject));
				} catch (Exception e) {
					callback.onFailure();
				}
			}
		}.start();
	}
}
