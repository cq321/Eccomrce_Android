package com.onjection.opencart.model;

public class LatestProductModel {
	public String product_id, model, quantity, stock_status_id, image, price,
			name, reward;

	public LatestProductModel() {

	}

	/**
	 * @param product_id
	 * @param model
	 * @param quantity
	 * @param stock_status_id
	 * @param image
	 * @param price
	 * @param name
	 * @param reward
	 */
	public LatestProductModel(String product_id, String model, String quantity,
			String stock_status_id, String image, String price, String name,
			String reward) {
		this.product_id = product_id;
		this.model = model;
		this.quantity = quantity;
		this.stock_status_id = stock_status_id;
		this.image = image;
		this.price = price;
		this.name = name;
		this.reward = reward;
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
	 * @return the stock_status_id
	 */
	public String getStock_status_id() {
		return stock_status_id;
	}

	/**
	 * @param stock_status_id
	 *            the stock_status_id to set
	 */
	public void setStock_status_id(String stock_status_id) {
		this.stock_status_id = stock_status_id;
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
	 * @return the reward
	 */
	public String getReward() {
		return reward;
	}

	/**
	 * @param reward
	 *            the reward to set
	 */
	public void setReward(String reward) {
		this.reward = reward;
	}

}
