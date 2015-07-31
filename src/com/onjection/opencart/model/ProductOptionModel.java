package com.onjection.opencart.model;

import java.util.ArrayList;

public class ProductOptionModel    {
	private String product_option_id, option_id, name, type, value, required;
	private ArrayList<ProductOptionValue> productoptionvalue;

	public ProductOptionModel() {

	}

	/**
	 * @return the product_option_id
	 */
	public String getProduct_option_id() {
		return product_option_id;
	}

	/**
	 * @param product_option_id
	 *            the product_option_id to set
	 */
	public void setProduct_option_id(String product_option_id) {
		this.product_option_id = product_option_id;
	}

	/**
	 * @return the option_id
	 */
	public String getOption_id() {
		return option_id;
	}

	/**
	 * @param option_id
	 *            the option_id to set
	 */
	public void setOption_id(String option_id) {
		this.option_id = option_id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

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
	 * @return the required
	 */
	public String getRequired() {
		return required;
	}

	/**
	 * @param required
	 *            the required to set
	 */
	public void setRequired(String required) {
		this.required = required;
	}

	/**
	 * @return the productoptionvalue
	 */
	public ArrayList<ProductOptionValue> getProductoptionvalue() {
		return productoptionvalue;
	}

	/**
	 * @param productoptionvalue
	 *            the productoptionvalue to set
	 */
	public void setProductoptionvalue(
			ArrayList<ProductOptionValue> productoptionvalue) {
		this.productoptionvalue = productoptionvalue;
	}

	/**
	 * @param product_option_id
	 * @param option_id
	 * @param name
	 * @param type
	 * @param value
	 * @param required
	 * @param productoptionvalue
	 */
	public ProductOptionModel(String product_option_id, String option_id,
			String name, String type, String value, String required,
			ArrayList<ProductOptionValue> productoptionvalue) {
		this.product_option_id = product_option_id;
		this.option_id = option_id;
		this.name = name;
		this.type = type;
		this.value = value;
		this.required = required;
		this.productoptionvalue = productoptionvalue;
	}

}
