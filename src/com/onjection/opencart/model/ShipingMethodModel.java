package com.onjection.opencart.model;

import java.util.ArrayList;

public class ShipingMethodModel {
	String title, sort_order, code, error;
	ArrayList<Quote> arraylistquote;

	/**
	 * @param title
	 * @param sort_order
	 * @param code
	 * @param error
	 * @param arraylistquote
	 */
	public ShipingMethodModel(String title, String sort_order, String code,
			String error, ArrayList<Quote> arraylistquote) {
		this.title = title;
		this.sort_order = sort_order;
		this.code = code;
		this.error = error;
		this.arraylistquote = arraylistquote;
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
	 * @return the sort_order
	 */
	public String getSort_order() {
		return sort_order;
	}

	/**
	 * @param sort_order
	 *            the sort_order to set
	 */
	public void setSort_order(String sort_order) {
		this.sort_order = sort_order;
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
	 * @return the error
	 */
	public String getError() {
		return error;
	}

	/**
	 * @param error
	 *            the error to set
	 */
	public void setError(String error) {
		this.error = error;
	}

	/**
	 * @return the arraylistquote
	 */
	public ArrayList<Quote> getArraylistquote() {
		return arraylistquote;
	}

	/**
	 * @param arraylistquote
	 *            the arraylistquote to set
	 */
	public void setArraylistquote(ArrayList<Quote> arraylistquote) {
		this.arraylistquote = arraylistquote;
	}

}
