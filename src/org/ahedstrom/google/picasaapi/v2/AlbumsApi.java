package org.ahedstrom.google.picasaapi.v2;

import org.ahedstrom.google.ApiCallback;
import org.ahedstrom.google.auth.OAuth;

public class AlbumsApi extends PicasaApi {

	public AlbumsApi(OAuth oauth, String appName) {
		super(oauth, appName);
	}
	
	public void list(final ApiCallback<String> callback) {
		invokeGetString("", new Callback<String>() {
			
			@Override
			public void onSuccess(String response) {
				callback.onSuccess(response);
			}
			
			@Override
			public void onFailure() {
				callback.onFailure();
			}
		});
	}

}
