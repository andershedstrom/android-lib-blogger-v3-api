package org.ahedstrom.google.bloggerapi.v3;

import org.ahedstrom.google.Api;
import org.ahedstrom.google.auth.OAuth;

abstract class BloggerApi extends Api {
	protected BloggerApi(String appName, OAuth oauth) {
		super("https://www.googleapis.com/blogger/v3", oauth, appName);
	}
}
