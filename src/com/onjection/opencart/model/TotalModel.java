package com.onjection.opencart.model;

public class TotalModel {
	String value, title;

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @param value
	 * @param title
	 */
	public TotalModel(String value, String title) {
		this.value = value;
		this.title = title;
	}

}
