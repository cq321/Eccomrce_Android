package com.onjection.opencart.model;

public class OrderInfoProduct {
	String total, unit_price, quantity, name;

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

	/**
	 * @param total
	 * @param unit_price
	 * @param quantity
	 * @param name
	 */
	public OrderInfoProduct(String total, String unit_price, String quantity,
			String name) {
		this.total = total;
		this.unit_price = unit_price;
		this.quantity = quantity;
		this.name = name;
	}

	/**
	 * @return the unit_price
	 */
	public String getUnit_price() {
		return unit_price;
	}

	/**
	 * @param unit_price
	 *            the unit_price to set
	 */
	public void setUnit_price(String unit_price) {
		this.unit_price = unit_price;
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
