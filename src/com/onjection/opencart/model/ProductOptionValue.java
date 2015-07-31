package com.onjection.opencart.model;

public class ProductOptionValue {
	private String product_option_value_id, option_value_id, name, image,
			quantity, subtract, price, price_prefix, weight, weight_prefix;

	/**
	 * @return the product_option_value_id
	 */
	public String getProduct_option_value_id() {
		return product_option_value_id;
	}

	/**
	 * @param product_option_value_id
	 *            the product_option_value_id to set
	 */
	public void setProduct_option_value_id(String product_option_value_id) {
		this.product_option_value_id = product_option_value_id;
	}

	/**
	 * @return the option_value_id
	 */
	public String getOption_value_id() {
		return option_value_id;
	}

	/**
	 * @param option_value_id
	 *            the option_value_id to set
	 */
	public void setOption_value_id(String option_value_id) {
		this.option_value_id = option_value_id;
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
	 * @return the image
	 */
	public String getImage() {
		return image;
	}

	/**
	 * @param image
	 *            the image to set
	 */
	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * @return the quantity
	 */
	public String getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity
	 *            the quantity to set
	 */
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the subtract
	 */
	public String getSubtract() {
		return subtract;
	}

	/**
	 * @param subtract
	 *            the subtract to set
	 */
	public void setSubtract(String subtract) {
		this.subtract = subtract;
	}

	/**
	 * @return the price
	 */
	public String getPrice() {
		return price;
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(String price) {
		this.price = price;
	}

	/**
	 * @return the price_prefix
	 */
	public String getPrice_prefix() {
		return price_prefix;
	}

	/**
	 * @param price_prefix
	 *            the price_prefix to set
	 */
	public void setPrice_prefix(String price_prefix) {
		this.price_prefix = price_prefix;
	}

	/**
	 * @return the weight
	 */
	public String getWeight() {
		return weight;
	}

	/**
	 * @param weight
	 *            the weight to set
	 */
	public void setWeight(String weight) {
		this.weight = weight;
	}

	/**
	 * @return the weight_prefix
	 */
	public String getWeight_prefix() {
		return weight_prefix;
	}

	/**
	 * @param weight_prefix
	 *            the weight_prefix to set
	 */
	public void setWeight_prefix(String weight_prefix) {
		this.weight_prefix = weight_prefix;
	}

	/**
	 * @param product_option_value_id
	 * @param option_value_id
	 * @param name
	 * @param image
	 * @param quantity
	 * @param subtract
	 * @param price
	 * @param price_prefix
	 * @param weight
	 * @param weight_prefix
	 */
	public ProductOptionValue(String product_option_value_id,
			String option_value_id, String name, String image, String quantity,
			String subtract, String price, String price_prefix, String weight,
			String weight_prefix) {
		this.product_option_value_id = product_option_value_id;
		this.option_value_id = option_value_id;
		this.name = name;
		this.image = image;
		this.quantity = quantity;
		this.subtract = subtract;
		this.price = price;
		this.price_prefix = price_prefix;
		this.weight = weight;
		this.weight_prefix = weight_prefix;
	}

}
