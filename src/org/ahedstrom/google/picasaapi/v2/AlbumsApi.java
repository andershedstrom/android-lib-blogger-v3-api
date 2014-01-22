package org.ahedstrom.google.picasaapi.v2;

import org.ahedstrom.google.ApiCallback;
import org.ahedstrom.google.ApiException;
import org.ahedstrom.google.auth.OAuth;

public class AlbumsApi extends PicasaApi {

	public AlbumsApi(OAuth oauth, String appName) {
		super(oauth, appName);
	}
	
	public void list(final ApiCallback<String> callback) {
		new Thread(){
			@Override
			public void run() {
				try {
					callback.onSuccess(invokeGet(""));
				} catch (ApiException e) {
					callback.onFailure();
				}
			}
		}.start();
	}
}
