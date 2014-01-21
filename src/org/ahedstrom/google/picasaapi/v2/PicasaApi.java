package org.ahedstrom.google.picasaapi.v2;

import org.ahedstrom.google.Api;
import org.ahedstrom.google.auth.OAuth;

abstract class PicasaApi extends Api {

	protected PicasaApi(OAuth oauth, String appName) {
		super("https://picasaweb.google.com/data/feed/api/user/default", oauth, appName);
	}

}
