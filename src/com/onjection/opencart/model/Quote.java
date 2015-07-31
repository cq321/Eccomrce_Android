package com.onjection.opencart.model;

public class Quote {
	String text, title, code, tax_class_id, cost;

	/**
	 * @param text
	 * @param title
	 * @param code
	 * @param tax_class_id
	 * @param cost
	 */
	public Quote(String text, String title, String code, String tax_class_id,
			String cost) {
		this.text = text;
		this.title = title;
		this.code = code;
		this.tax_class_id = tax_class_id;
		this.cost = cost;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text
	 *            the text to set
	 */
	public void setText(String text) {
		this.text = text;
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
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the tax_class_id
	 */
	public String getTax_class_id() {
		return tax_class_id;
	}

	/**
	 * @param tax_class_id
	 *            the tax_class_id to set
	 */
	public void setTax_class_id(String tax_class_id) {
		this.tax_class_id = tax_class_id;
	}

	/**
	 * @return the cost
	 */
	public String getCost() {
		return cost;
	}

	/**
	 * @param cost
	 *            the cost to set
	 */
	public void setCost(String cost) {
		this.cost = cost;
	}

}
