package com.onjection.opencart.model;

public class PriceFilter {
	public String name;
	public boolean box;

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
	 * @return the box
	 */
	public boolean isBox() {
		return box;
	}

	/**
	 * @param box
	 *            the box to set
	 */
	public void setBox(boolean box) {
		this.box = box;
	}

	/**
	 * @param name
	 * @param box
	 */
	public PriceFilter(String name, boolean box) {
		this.name = name;
		this.box = box;
	}
}
