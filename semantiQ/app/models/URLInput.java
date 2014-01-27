package models;

import play.data.validation.Constraints.Required;

public class URLInput {
	
	@Required
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
