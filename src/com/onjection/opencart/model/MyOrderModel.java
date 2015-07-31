package com.onjection.opencart.model;

import java.io.Serializable;
import java.util.ArrayList;

public class MyOrderModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String order_id, total, date_added;
	@SuppressWarnings("unused")
	private ArrayList<MyOrderProductDetails> myorderproduct;
	private ArrayList<MyOrderTotalsModel> myodertotals;

	public MyOrderModel() {

	}

	public MyOrderModel(String order_id, String total, String date_added,
			ArrayList<MyOrderProductDetails> myorderproduct,
			ArrayList<MyOrderTotalsModel> myodertotals) {

		this.order_id = order_id;
		this.total = total;
		this.date_added = date_added;
		this.myorderproduct = myorderproduct;
		this.myodertotals = myodertotals;
	}

	/**
	 * @return the date_added
	 */
	public String getDate_added() {
		return date_added;
	}

	/**
	 * @param date_added
	 *            the date_added to set
	 */
	public void setDate_added(String date_added) {
		this.date_added = date_added;
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

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	/**
	 * @return the category_id
	 */
	public String getOrder_id() {
		return order_id;
	}

	public ArrayList<MyOrderProductDetails> getMyorderproduct() {
		return myorderproduct;
	}

	/**
	 * @param child
	 *            the child to set
	 */
	public void setMyorderproduct(
			ArrayList<MyOrderProductDetails> myorderproduct) {
		this.myorderproduct = myorderproduct;
	}

	public ArrayList<MyOrderTotalsModel> getMyodertotals() {
		return myodertotals;
	}

	/**
	 * @param child
	 *            the child to set
	 */
	public void setMyodertotals(ArrayList<MyOrderTotalsModel> myodertotals) {
		this.myodertotals = myodertotals;
	}
}
