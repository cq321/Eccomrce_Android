package com.onjection.opencart.model;

import java.util.ArrayList;

public class CategoryProduct {
	String product_id, model, sku, upc, ean, jan, isbn, mpn, location,
			quantity, stock_status_id, image, manufacturer_id, shipping, price,
			points, tax_class_id, date_available, weight, weight_class_id,
			length, width, height, length_class_id, subtract, minimum,
			sort_order, status, viewed, date_added, date_modified, language_id,
			name, description, tag, store_id, manufacturer, discount, special,
			reward, stock_status, rating, reviews;
	private ArrayList<ProductOptionModel> productOptionModels;

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
	 * @return the sku
	 */
	public String getSku() {
		return sku;
	}

	/**
	 * @param sku
	 *            the sku to set
	 */
	public void setSku(String sku) {
		this.sku = sku;
	}

	/**
	 * @return the upc
	 */
	public String getUpc() {
		return upc;
	}

	/**
	 * @param upc
	 *            the upc to set
	 */
	public void setUpc(String upc) {
		this.upc = upc;
	}

	/**
	 * @return the ean
	 */
	public String getEan() {
		return ean;
	}

	/**
	 * @param ean
	 *            the ean to set
	 */
	public void setEan(String ean) {
		this.ean = ean;
	}

	/**
	 * @return the jan
	 */
	public String getJan() {
		return jan;
	}

	/**
	 * @param jan
	 *            the jan to set
	 */
	public void setJan(String jan) {
		this.jan = jan;
	}

	/**
	 * @return the isbn
	 */
	public String getIsbn() {
		return isbn;
	}

	/**
	 * @param isbn
	 *            the isbn to set
	 */
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	/**
	 * @return the mpn
	 */
	public String getMpn() {
		return mpn;
	}

	/**
	 * @param mpn
	 *            the mpn to set
	 */
	public void setMpn(String mpn) {
		this.mpn = mpn;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
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
	 * @return the manufacturer_id
	 */
	public String getManufacturer_id() {
		return manufacturer_id;
	}

	/**
	 * @param manufacturer_id
	 *            the manufacturer_id to set
	 */
	public void setManufacturer_id(String manufacturer_id) {
		this.manufacturer_id = manufacturer_id;
	}

	/**
	 * @return the shipping
	 */
	public String getShipping() {
		return shipping;
	}

	/**
	 * @param shipping
	 *            the shipping to set
	 */
	public void setShipping(String shipping) {
		this.shipping = shipping;
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
	 * @return the points
	 */
	public String getPoints() {
		return points;
	}

	/**
	 * @param points
	 *            the points to set
	 */
	public void setPoints(String points) {
		this.points = points;
	}

	/**
	 * @return the tax_class_id
	 */
	public String getTax_class_id() {
		return tax_class_id;
	}

	/**
	 * @param tax_class_id
	 *            the tax_class_id to set
	 */
	public void setTax_class_id(String tax_class_id) {
		this.tax_class_id = tax_class_id;
	}

	/**
	 * @return the date_available
	 */
	public String getDate_available() {
		return date_available;
	}

	/**
	 * @param date_available
	 *            the date_available to set
	 */
	public void setDate_available(String date_available) {
		this.date_available = date_available;
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
	 * @return the weight_class_id
	 */
	public String getWeight_class_id() {
		return weight_class_id;
	}

	/**
	 * @param weight_class_id
	 *            the weight_class_id to set
	 */
	public void setWeight_class_id(String weight_class_id) {
		this.weight_class_id = weight_class_id;
	}

	/**
	 * @return the length
	 */
	public String getLength() {
		return length;
	}

	/**
	 * @param length
	 *            the length to set
	 */
	public void setLength(String length) {
		this.length = length;
	}

	/**
	 * @return the width
	 */
	public String getWidth() {
		return width;
	}

	/**
	 * @param width
	 *            the width to set
	 */
	public void setWidth(String width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public String getHeight() {
		return height;
	}

	/**
	 * @param height
	 *            the height to set
	 */
	public void setHeight(String height) {
		this.height = height;
	}

	/**
	 * @return the length_class_id
	 */
	public String getLength_class_id() {
		return length_class_id;
	}

	/**
	 * @param length_class_id
	 *            the length_class_id to set
	 */
	public void setLength_class_id(String length_class_id) {
		this.length_class_id = length_class_id;
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
	 * @return the minimum
	 */
	public String getMinimum() {
		return minimum;
	}

	/**
	 * @param minimum
	 *            the minimum to set
	 */
	public void setMinimum(String minimum) {
		this.minimum = minimum;
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
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the viewed
	 */
	public String getViewed() {
		return viewed;
	}

	/**
	 * @param viewed
	 *            the viewed to set
	 */
	public void setViewed(String viewed) {
		this.viewed = viewed;
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
	 * @return the date_modified
	 */
	public String getDate_modified() {
		return date_modified;
	}

	/**
	 * @param date_modified
	 *            the date_modified to set
	 */
	public void setDate_modified(String date_modified) {
		this.date_modified = date_modified;
	}

	/**
	 * @return the language_id
	 */
	public String getLanguage_id() {
		return language_id;
	}

	/**
	 * @param language_id
	 *            the language_id to set
	 */
	public void setLanguage_id(String language_id) {
		this.language_id = language_id;
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
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the tag
	 */
	public String getTag() {
		return tag;
	}

	/**
	 * @param tag
	 *            the tag to set
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}

	/**
	 * @return the store_id
	 */
	public String getStore_id() {
		return store_id;
	}

	/**
	 * @param store_id
	 *            the store_id to set
	 */
	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}

	/**
	 * @return the manufacturer
	 */
	public String getManufacturer() {
		return manufacturer;
	}

	/**
	 * @param manufacturer
	 *            the manufacturer to set
	 */
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	/**
	 * @return the discount
	 */
	public String getDiscount() {
		return discount;
	}

	/**
	 * @param discount
	 *            the discount to set
	 */
	public void setDiscount(String discount) {
		this.discount = discount;
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

	/**
	 * @return the stock_status
	 */
	public String getStock_status() {
		return stock_status;
	}

	/**
	 * @param stock_status
	 *            the stock_status to set
	 */
	public void setStock_status(String stock_status) {
		this.stock_status = stock_status;
	}

	/**
	 * @return the rating
	 */
	public String getRating() {
		return rating;
	}

	/**
	 * @param rating
	 *            the rating to set
	 */
	public void setRating(String rating) {
		this.rating = rating;
	}

	/**
	 * @return the reviews
	 */
	public String getReviews() {
		return reviews;
	}

	/**
	 * @param reviews
	 *            the reviews to set
	 */
	public void setReviews(String reviews) {
		this.reviews = reviews;
	}

	/**
	 * @return the productOptionModels
	 */
	public ArrayList<ProductOptionModel> getProductOptionModels() {
		return productOptionModels;
	}

	/**
	 * @param productOptionModels
	 *            the productOptionModels to set
	 */
	public void setProductOptionModels(
			ArrayList<ProductOptionModel> productOptionModels) {
		this.productOptionModels = productOptionModels;
	}

	/**
	 * @param product_id
	 * @param model
	 * @param sku
	 * @param upc
	 * @param ean
	 * @param jan
	 * @param isbn
	 * @param mpn
	 * @param location
	 * @param quantity
	 * @param stock_status_id
	 * @param image
	 * @param manufacturer_id
	 * @param shipping
	 * @param price
	 * @param points
	 * @param tax_class_id
	 * @param date_available
	 * @param weight
	 * @param weight_class_id
	 * @param length
	 * @param width
	 * @param height
	 * @param length_class_id
	 * @param subtract
	 * @param minimum
	 * @param sort_order
	 * @param status
	 * @param viewed
	 * @param date_added
	 * @param date_modified
	 * @param language_id
	 * @param name
	 * @param description
	 * @param tag
	 * @param store_id
	 * @param manufacturer
	 * @param discount
	 * @param special
	 * @param reward
	 * @param stock_status
	 * @param rating
	 * @param reviews
	 * @param productOptionModels
	 */
	public CategoryProduct(String product_id, String model, String sku,
			String upc, String ean, String jan, String isbn, String mpn,
			String location, String quantity, String stock_status_id,
			String image, String manufacturer_id, String shipping,
			String price, String points, String tax_class_id,
			String date_available, String weight, String weight_class_id,
			String length, String width, String height, String length_class_id,
			String subtract, String minimum, String sort_order, String status,
			String viewed, String date_added, String date_modified,
			String language_id, String name, String description, String tag,
			String store_id, String manufacturer, String discount,
			String special, String reward, String stock_status, String rating,
			String reviews, ArrayList<ProductOptionModel> productOptionModels) {
		this.product_id = product_id;
		this.model = model;
		this.sku = sku;
		this.upc = upc;
		this.ean = ean;
		this.jan = jan;
		this.isbn = isbn;
		this.mpn = mpn;
		this.location = location;
		this.quantity = quantity;
		this.stock_status_id = stock_status_id;
		this.image = image;
		this.manufacturer_id = manufacturer_id;
		this.shipping = shipping;
		this.price = price;
		this.points = points;
		this.tax_class_id = tax_class_id;
		this.date_available = date_available;
		this.weight = weight;
		this.weight_class_id = weight_class_id;
		this.length = length;
		this.width = width;
		this.height = height;
		this.length_class_id = length_class_id;
		this.subtract = subtract;
		this.minimum = minimum;
		this.sort_order = sort_order;
		this.status = status;
		this.viewed = viewed;
		this.date_added = date_added;
		this.date_modified = date_modified;
		this.language_id = language_id;
		this.name = name;
		this.description = description;
		this.tag = tag;
		this.store_id = store_id;
		this.manufacturer = manufacturer;
		this.discount = discount;
		this.special = special;
		this.reward = reward;
		this.stock_status = stock_status;
		this.rating = rating;
		this.reviews = reviews;
		this.productOptionModels = productOptionModels;
	}

}
