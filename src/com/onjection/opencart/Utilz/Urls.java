package com.onjection.opencart.Utilz;
public class Urls {
	// final static String baseUrl = "http://192.168.1.103/payment/api/";
//	final static String baseUrl ="http://www.alliedhunt.com/api/";
	public static String tokan ="61cb31603fd8ec7f7aebbf2c491618f8";
		final static String baseUrl = "https://www.ordervenue.com/app/";
	public static String GetlangApi = baseUrl + "getlanguage?tokan="+tokan;
	public static String GetCategoryApi = baseUrl
			+ "getcategories?tokan="+tokan+"&language_id=";
	public static String SlideShowuApi = baseUrl
			+ "get_slideshow?tokan="+tokan+"&language_id=1";
	public static String GetDealPrdouct = baseUrl
			+ "getspecialsproduct?tokan="+tokan+"&language_id=1&limit=5";
	public static String GetLatestproduct = baseUrl
			+ "getlatestproduct?tokan="+tokan+"&language_id=1&limit=5";
	public static String GetProductdetails = baseUrl
			+ "getproduct?tokan="+tokan+"&language_id=1&product_id=";
	public static String GetProductByCategorey = baseUrl
			+ "getproducts?tokan="+tokan+"&language_id=1&category_id=";
	public static String login = baseUrl + "login";
	public static String forgot = baseUrl + "forgotpassword";
	public static String SearchProducts = baseUrl
			+ "getproducts?tokan="+tokan+"&language_id=1&filter_name=";
	public static String Registraionapi = baseUrl + "/registration";
	public static String customeraddress = baseUrl
			+ "get_customer_address?customer_id=";
	public static String GetAddTOCardpostApi = baseUrl + "get_cart_products";
	public static String InsertNewAddressApi = baseUrl
			+ "insert_customer_address";
	public static String DeleteAddressApi = baseUrl
			+ "delete_customer_address?customer_id=";
	public static String EditAddressApi = baseUrl + "update_customer_address";
	public static String PlaceOrderApi = baseUrl + "getpayment_method";
	public static String ShipingApi = baseUrl + "getshipping_method";
	public static String Authentication = "http://www.onjection.com/api/auth_application";
	public static String ContactUs = baseUrl + "contactus";
	public static String featuredProducts = baseUrl
			+ "getfeatureproduct?tokan="+tokan+"&language_id=1";
	public static String ConfirmOrderApi = baseUrl + "confirmorder";
	public static String ConfrimSucessFullPayment = baseUrl + "place_order";
	public static String myorderapi = baseUrl
			+ "getcustomer_order?customer_id=";

}
