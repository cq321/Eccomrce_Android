package com.onjection.opencart.model;

public class ChildInfo {
	private String category_id, image, parent_id, top, column, sort_order,
			status, date_added, date_modified, language_id, name, description,
			meta_title, meta_description, meta_keyword, store_id;

	ChildInfo() {

	}

	/**
	 * @param category_id
	 * @param image
	 * @param parent_id
	 * @param top
	 * @param column
	 * @param sort_order
	 * @param status
	 * @param date_added
	 * @param date_modified
	 * @param language_id
	 * @param name
	 * @param description
	 * @param meta_title
	 * @param meta_description
	 * @param meta_keyword
	 * @param store_id
	 */
	public ChildInfo(String category_id, String image, String parent_id,
			String top, String column, String sort_order, String status,
			String date_added, String date_modified, String language_id,
			String name, String description, String meta_title,
			String meta_description, String meta_keyword, String store_id) {
		super();
		this.category_id = category_id;
		this.image = image;
		this.parent_id = parent_id;
		this.top = top;
		this.column = column;
		this.sort_order = sort_order;
		this.status = status;
		this.date_added = date_added;
		this.date_modified = date_modified;
		this.language_id = language_id;
		this.name = name;
		this.description = description;
		this.meta_title = meta_title;
		this.meta_description = meta_description;
		this.meta_keyword = meta_keyword;
		this.store_id = store_id;
	}

	/**
	 * @return the category_id
	 */
	public String getCategory_id() {
		return category_id;
	}

	/**
	 * @param category_id
	 *            the category_id to set
	 */
	public void setCategory_id(String category_id) {
		this.category_id = category_id;
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
	 * @return the parent_id
	 */
	public String getParent_id() {
		return parent_id;
	}

	/**
	 * @param parent_id
	 *            the parent_id to set
	 */
	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}

	/**
	 * @return the top
	 */
	public String getTop() {
		return top;
	}

	/**
	 * @param top
	 *            the top to set
	 */
	public void setTop(String top) {
		this.top = top;
	}

	/**
	 * @return the column
	 */
	public String getColumn() {
		return column;
	}

	/**
	 * @param column
	 *            the column to set
	 */
	public void setColumn(String column) {
		this.column = column;
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
	 * @return the meta_title
	 */
	public String getMeta_title() {
		return meta_title;
	}

	/**
	 * @param meta_title
	 *            the meta_title to set
	 */
	public void setMeta_title(String meta_title) {
		this.meta_title = meta_title;
	}

	/**
	 * @return the meta_description
	 */
	public String getMeta_description() {
		return meta_description;
	}

	/**
	 * @param meta_description
	 *            the meta_description to set
	 */
	public void setMeta_description(String meta_description) {
		this.meta_description = meta_description;
	}

	/**
	 * @return the meta_keyword
	 */
	public String getMeta_keyword() {
		return meta_keyword;
	}

	/**
	 * @param meta_keyword
	 *            the meta_keyword to set
	 */
	public void setMeta_keyword(String meta_keyword) {
		this.meta_keyword = meta_keyword;
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

}
