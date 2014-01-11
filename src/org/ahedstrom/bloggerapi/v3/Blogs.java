package org.ahedstrom.bloggerapi.v3;

import org.json.JSONObject;

import android.text.format.Time;

/**
 * 
 * <pre>
 *  {
 *    "kind": "blogger#blog",
 *    "id": value,
 *    "name": value,
 *    "description": value,
 *    "published": value,
 *    "updated": value,
 *    "url": value,
 *    "selfLink": value,
 *    "posts": {
 *      "totalItems": value,
 *      "selfLink": value
 *    },
 *    "pages": {
 *      "totalItems": value,
 *      "selfLink": value
 *    },
 *    "locale": {
 *      "language": value,
 *      "country": value,
 *       "variant": value
 *    }
 *  }
 * </pre>
 * 
 * @author anders
 *
 */
public class Blogs extends Base {

	public Blogs(JSONObject json) {
		super(json);
	}
	
	public String getKind() {
		return getString("kind");
	}
	
	public String getId() {
		return getString("id");
	}
	
	public String getName() {
		return getString("name");
	}

	public String getDescription() {
		return getString("description");
	}
	
	public Time getPublished() {
		return getTime("published");
	}
	
	public Time getUpdated() {
		return getTime("updated");
	}

	public String getUrl() {
		return getString("url");
	}

	public String getSelfLink() {
		return getString("selfLink");
	}
	
	public Posts getPosts() {
		return new Posts(getJsonObject("posts"));
	}

	public Pages getPages() {
		return new Pages(getJsonObject("pages"));
	}

	public Locale getLocale() {
		return new Locale(getJsonObject("locale"));
	}
}
