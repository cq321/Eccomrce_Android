package com.onjection.opencart.model;

public class DealProduct {
	public String product_id, model, image, price, special, name;

	/**
	 * @param product_id
	 * @param model
	 * @param image
	 * @param price
	 * @param special
	 * @param name
	 */
	public DealProduct(String product_id, String model, String image,
			String price, String special, String name) {
		this.product_id = product_id;
		this.model = model;
		this.image = image;
		this.price = price;
		this.special = special;
		this.name = name;
	}

	public DealProduct() {

	}

	/**
	 * @return the product_id
	 */
	public String getProduct_id() {
		return product_id;
	}

	/**
	 * @param product_id
	 *            the product_id to set
	 */
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	/**
	 * @return the model
	 */
	public String getModel() {
		return model;
	}

	/**
	 * @param model
	 *            the model to set
	 */
	public void setModel(String model) {
		this.model = model;
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
	 * @return the special
	 */
	public String getSpecial() {
		return special;
	}

	/**
	 * @param special
	 *            the special to set
	 */
	public void setSpecial(String special) {
		this.special = special;
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

}
