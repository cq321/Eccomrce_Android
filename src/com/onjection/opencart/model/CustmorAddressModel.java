package com.onjection.opencart.model;

public class CustmorAddressModel {
	String address_id, customer_id, firstname, lastname, company, address_1,
			address_2, city, postcode, country_id, zone_id, custom_field,
			country, state;

	/**
	 * @return the address_id
	 */
	public String getAddress_id() {
		return address_id;
	}

	/**
	 * @param address_id
	 * @param customer_id
	 * @param firstname
	 * @param lastname
	 * @param company
	 * @param address_1
	 * @param address_2
	 * @param city
	 * @param postcode
	 * @param country_id
	 * @param zone_id
	 * @param custom_field
	 * @param country
	 * @param state
	 */
	public CustmorAddressModel(String address_id, String customer_id,
			String firstname, String lastname, String company,
			String address_1, String address_2, String city, String postcode,
			String country_id, String zone_id, String custom_field,
			String country, String state) {
		this.address_id = address_id;
		this.customer_id = customer_id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.company = company;
		this.address_1 = address_1;
		this.address_2 = address_2;
		this.city = city;
		this.postcode = postcode;
		this.country_id = country_id;
		this.zone_id = zone_id;
		this.custom_field = custom_field;
		this.country = country;
		this.state = state;
	}

	/**
	 * @param address_id
	 *            the address_id to set
	 */
	public void setAddress_id(String address_id) {
		this.address_id = address_id;
	}

	/**
	 * @return the customer_id
	 */
	public String getCustomer_id() {
		return customer_id;
	}

	/**
	 * @param customer_id
	 *            the customer_id to set
	 */
	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}

	/**
	 * @return the firstname
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * @param firstname
	 *            the firstname to set
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * @return the lastname
	 */
	public String getLastname() {
		return lastname;
	}

	/**
	 * @param lastname
	 *            the lastname to set
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	/**
	 * @return the company
	 */
	public String getCompany() {
		return company;
	}

	/**
	 * @param company
	 *            the company to set
	 */
	public void setCompany(String company) {
		this.company = company;
	}

	/**
	 * @return the address_1
	 */
	public String getAddress_1() {
		return address_1;
	}

	/**
	 * @param address_1
	 *            the address_1 to set
	 */
	public void setAddress_1(String address_1) {
		this.address_1 = address_1;
	}

	/**
	 * @return the address_2
	 */
	public String getAddress_2() {
		return address_2;
	}

	/**
	 * @param address_2
	 *            the address_2 to set
	 */
	public void setAddress_2(String address_2) {
		this.address_2 = address_2;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the postcode
	 */
	public String getPostcode() {
		return postcode;
	}

	/**
	 * @param postcode
	 *            the postcode to set
	 */
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	/**
	 * @return the country_id
	 */
	public String getCountry_id() {
		return country_id;
	}

	/**
	 * @param country_id
	 *            the country_id to set
	 */
	public void setCountry_id(String country_id) {
		this.country_id = country_id;
	}

	/**
	 * @return the zone_id
	 */
	public String getZone_id() {
		return zone_id;
	}

	/**
	 * @param zone_id
	 *            the zone_id to set
	 */
	public void setZone_id(String zone_id) {
		this.zone_id = zone_id;
	}

	/**
	 * @return the custom_field
	 */
	public String getCustom_field() {
		return custom_field;
	}

	/**
	 * @param custom_field
	 *            the custom_field to set
	 */
	public void setCustom_field(String custom_field) {
		this.custom_field = custom_field;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country
	 *            the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

}
