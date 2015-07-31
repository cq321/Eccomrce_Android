package com.onjection.opencart.model;

public class MyOrderProductDetails {
	String order_product_id, name, quantity, price, total;

	/**
	 * @return the order_product_id
	 */
	public String getOrder_product_id() {
		return order_product_id;
	}

	/**
	 * @param order_product_id
	 *            the order_product_id to set
	 */
	public void setOrder_product_id(String order_product_id) {
		this.order_product_id = order_product_id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param order_product_id
	 * @param name
	 * @param quantity
	 * @param price
	 * @param total
	 */
	public MyOrderProductDetails(String order_product_id, String name,
			String quantity, String price, String total) {
		this.order_product_id = order_product_id;
		this.name = name;
		this.quantity = quantity;
		this.price = price;
		this.total = total;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @return the total
	 */
	public String getTotal() {
		return total;
	}

	/**
	 * @param total
	 *            the total to set
	 */
	public void setTotal(String total) {
		this.total = total;
	}

}
