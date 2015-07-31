package com.onjection.opencart.model;

public class PayMentMethodModel {
	String title, sort_order, terms, code;

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
	 * @return the terms
	 */
	public String getTerms() {
		return terms;
	}

	/**
	 * @param terms
	 *            the terms to set
	 */
	public void setTerms(String terms) {
		this.terms = terms;
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
	 * @param title
	 * @param sort_order
	 * @param terms
	 * @param code
	 */
	public PayMentMethodModel(String title, String sort_order, String terms,
			String code) {
		this.title = title;
		this.sort_order = sort_order;
		this.terms = terms;
		this.code = code;
	}

}
