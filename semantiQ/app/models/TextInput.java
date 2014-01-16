package models;

import play.data.validation.Constraints.Required;

public class TextInput {
	
	@Required
	private String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
