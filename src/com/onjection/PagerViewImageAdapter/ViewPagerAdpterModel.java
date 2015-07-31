package com.onjection.PagerViewImageAdapter;

public class ViewPagerAdpterModel {
	String banner_image_id, banner_id, image, sort_order, title,category_id;

	/**
	 * @param banner_image_id
	 * @param banner_id
	 * @param image
	 * @param sort_order
	 * @param title
	 */
	public ViewPagerAdpterModel(String banner_image_id, String banner_id,
			String image, String sort_order, String title,String category_id) {
		this.banner_image_id = banner_image_id;
		this.banner_id = banner_id;
		this.image = image;
		this.sort_order = sort_order;
		this.title = title;
		this.category_id=category_id;
	}

	/**
	 * @return the category_id
	 */
	public String getCategory_id() {
		return category_id;
	}

	/**
	 * @param category_id the category_id to set
	 */
	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}

	/**
	 * @return the banner_image_id
	 */
	public String getBanner_image_id() {
		return banner_image_id;
	}

	/**
	 * @param banner_image_id
	 *            the banner_image_id to set
	 */
	public void setBanner_image_id(String banner_image_id) {
		this.banner_image_id = banner_image_id;
	}

	/**
	 * @return the banner_id
	 */
	public String getBanner_id() {
		return banner_id;
	}

	/**
	 * @param banner_id
	 *            the banner_id to set
	 */
	public void setBanner_id(String banner_id) {
		this.banner_id = banner_id;
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

}
