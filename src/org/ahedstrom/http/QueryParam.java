package org.ahedstrom.http;


public class QueryParam {

	public final String name;
	public final String value;
	
	public QueryParam(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public static Builder builder() {
		return new Builder();
	}
	
	public static class Builder {
		private String n;
		private String v;
		
		public Builder name(String name) {
			n = name;
			return this;
		}
		
		public Builder value(String value) {
			v = value;
			return this;
		}

		public QueryParam build() {
			return new QueryParam(n, v);
		}
	}
}
