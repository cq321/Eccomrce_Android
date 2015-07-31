package com.onjection.opencart.model;

public class MyOrderTotalsModel {
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
	 * @param value
	 * @param title
	 */
	public MyOrderTotalsModel(String value, String title) {
		this.value = value;
		this.title = title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

}
