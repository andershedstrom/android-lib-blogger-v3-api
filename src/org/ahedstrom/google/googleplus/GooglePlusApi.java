package org.ahedstrom.google.googleplus;

import org.ahedstrom.google.Api;
import org.ahedstrom.google.auth.OAuth;


abstract class GooglePlusApi extends Api {

	protected GooglePlusApi(String appName, OAuth oauth) {
		super("https://www.googleapis.com/plus/v1", oauth, appName);
	}

}
