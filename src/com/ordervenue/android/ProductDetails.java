package com.ordervenue.android;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.onjection.opencart.Utilz.ServiceHandler;
import com.onjection.opencart.Utilz.Urls;
import com.onjection.opencart.model.ProductOptionModel;
import com.onjection.opencart.model.ProductOptionValue;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class ProductDetails extends Activity implements OnClickListener {
	String product_id, jsonstringproduct = null;
	int language_id = 1;
	DisplayImageOptions options;
	int optionArrayLength;
	ImageLoader imageLoader;
	public static String strSeparator = "__,__";
	DBController controller = new DBController(this);
	private ProgressDialog pd;
	String model, quantity, location, stock_status_id, image, shipping, price,
			points, tax_class_id, date_available, weight, weight_class_id,
			length, width, height, name, description, special, rating,
			stock_status, discount, reward;
	int radioButtonSelectedId = 0, radioButtonParrenmtId = 0,
			chkBoxParrentId = 0, selectedColourID = 0;

	ArrayList<String> arrChkBoxSelectedID = new ArrayList<String>();

	private ArrayList<ProductOptionModel> productOptionList = new ArrayList<ProductOptionModel>();
	ProductOptionModel productOptionModel;
	TextView txtproductname, txtrating, txtsellingpriceofproduct,
			txtmrpofproduct, txtstockstatus, txtstockstatusdeliver,
			txtdesciptionupdate, txtdescription, txtOptionTitle1,
			txtOptionTitle2, tvLine;
	ImageView imgproductid, productImg1, productImg2, productImg3, productImg4,
			productImg5;
	String pImgUrl, pImgUrl4, pImgUrl3, pImgUrl2, pImgUrl1;
	Button btnbuynow;
	String Image_array[];
	static Boolean clicktxt = false;
	// Gallery gallery;
	LinearLayout linearlaytoutstockstatus, ratingCntr, linearlayoutaddtocard,llAllOption,
			linearlayoutshare, linearlayoutgotocard;

	@SuppressWarnings("unused")
	private Integer[] mImageIds = { R.drawable.item, R.drawable.item,
			R.drawable.item, R.drawable.item, R.drawable.item };
	ArrayList<String> productimages = new ArrayList<String>();
	ImageView productimage;
	ImageLoader img;
	ImageView ivBack, ivCart;
	LinearLayout ll1, ll2, ll3, ll4, ll5, llOptions, llChkBox, llSelect;
	RadioGroup radiogroup, radiogroupSelect;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.productdetails);
		product_id = getIntent().getStringExtra("Key_Product_id");
		new GetProductDetails().execute();
		llAllOption = (LinearLayout) findViewById(R.id.llAllOption);
		Log.e("product id", product_id);
		Log.e("optionArrayLength", optionArrayLength+"");
		if (optionArrayLength==0) {
			llAllOption.setVisibility(View.GONE);
		}
		imageLoader = ImageLoader.getInstance();
	 	imageLoader.init(ImageLoaderConfiguration.createDefault(this));
			options = new DisplayImageOptions.Builder()
			.showImageOnLoading(R.drawable.ic_launcher)
			.showImageForEmptyUri(R.drawable.ic_launcher)
			.showImageOnFail(R.drawable.ic_launcher).cacheInMemory(true)
					.cacheOnDisk(true).considerExifParams(true)
					.imageScaleType(ImageScaleType.EXACTLY)
					.bitmapConfig(Bitmap.Config.ARGB_8888)
					.build();
		
		txtproductname = (TextView) findViewById(R.id.txtproductname);
		txtrating = (TextView) findViewById(R.id.txtrating);
		txtsellingpriceofproduct = (TextView) findViewById(R.id.txtsellingpriceofproduct);
		txtmrpofproduct = (TextView) findViewById(R.id.txtmrpofproduct);
		tvLine  = (TextView) findViewById(R.id.tvLine);
		txtstockstatus = (TextView) findViewById(R.id.txtstockstatus);
		txtstockstatusdeliver = (TextView) findViewById(R.id.txtstockstatusdeliver);
		txtdesciptionupdate = (TextView) findViewById(R.id.txtdesciptionupdate);
		txtdescription = (TextView) findViewById(R.id.txtdescription);
		productImg1 = (ImageView) findViewById(R.id.iv1);
		productImg2 = (ImageView) findViewById(R.id.iv2);
		productImg3 = (ImageView) findViewById(R.id.iv3);
		productImg4 = (ImageView) findViewById(R.id.iv4);
		productImg5 = (ImageView) findViewById(R.id.iv5);
		ivCart = (ImageView) findViewById(R.id.ivCart);
		ivCart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(ProductDetails.this, MyCart.class);
				startActivity(i);
			}
		});
		radiogroup = (RadioGroup) ((ViewGroup) findViewById(R.id.radiogroup));
		radiogroupSelect = (RadioGroup) ((ViewGroup) findViewById(R.id.radiogroupSelect));

		ll1 = (LinearLayout) findViewById(R.id.ll1);
		ll2 = (LinearLayout) findViewById(R.id.ll2);
		ll3 = (LinearLayout) findViewById(R.id.ll3);
		ll4 = (LinearLayout) findViewById(R.id.ll4);
		ll5 = (LinearLayout) findViewById(R.id.ll5);
		llOptions = (LinearLayout) findViewById(R.id.llOptions);
		llChkBox = (LinearLayout) findViewById(R.id.llChkBox);

		llChkBox.setVisibility(View.GONE);
		llOptions.setVisibility(View.GONE);

		ll1.setVisibility(View.GONE);
		ll2.setVisibility(View.GONE);
		ll3.setVisibility(View.GONE);
		ll4.setVisibility(View.GONE);
		ll5.setVisibility(View.GONE);
		txtOptionTitle1 = (TextView) findViewById(R.id.txtOptionTitle1);
		txtOptionTitle2 = (TextView) findViewById(R.id.txtOptionTitle2);

		txtOptionTitle1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch (llOptions.getVisibility()) {
				case View.VISIBLE:
					llOptions.setVisibility(View.GONE);
					break;
				case View.GONE:
					llOptions.setVisibility(View.VISIBLE);
					break;
				default:
					break;
				}
			}
		});
		txtOptionTitle2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch (llChkBox.getVisibility()) {
				case View.VISIBLE:
					llChkBox.setVisibility(View.GONE);
					break;
				case View.GONE:
					llChkBox.setVisibility(View.VISIBLE);
					break;
				default:
					break;
				}
			}
		});
		imgproductid = (ImageView) findViewById(R.id.imgproductid);
		linearlayoutshare = (LinearLayout) findViewById(R.id.linearlayoutshare);
		btnbuynow = (Button) findViewById(R.id.btnbuynow);
		ivBack = (ImageView) findViewById(R.id.ivBack);
		ivBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ProductDetails.this.finish();

			}
		});
		/*
		 * gallery = (Gallery) findViewById(R.id.gallery1);
		 */linearlaytoutstockstatus = (LinearLayout) findViewById(R.id.linearlaytoutstockstatus);
		ratingCntr = (LinearLayout) findViewById(R.id.ratingCntr);

		linearlayoutaddtocard = (LinearLayout) findViewById(R.id.linearlayoutaddtocard);
		linearlayoutgotocard = (LinearLayout) findViewById(R.id.linearlayoutgotocard);
		
		
		linearlayoutgotocard.setOnClickListener(this);
		btnbuynow.setOnClickListener(this);
		linearlayoutshare.setOnClickListener(this);
		linearlayoutaddtocard.setOnClickListener(this);
		txtdescription.setOnClickListener(this);

	}

	private void ratingshow() {
		for (int i = 1; i <= 5; i++) {
			ImageView iv = new ImageView(ProductDetails.this);
			if (rating.equals("")) {
				iv.setImageDrawable(ProductDetails.this.getResources()
						.getDrawable(R.drawable.start_unchecked));
			} else {
				Double ratingvalue = Double.parseDouble(rating);
				if (i <= ratingvalue) {
					iv.setImageDrawable(ProductDetails.this.getResources()
							.getDrawable(R.drawable.start_checked));
				} else {
					iv.setImageDrawable(ProductDetails.this.getResources()
							.getDrawable(R.drawable.start_unchecked));
				}
			}
			ratingCntr.addView(iv);
		}
	}

	private void showimagesofproduct() {
		for (int i = 0; i < productimages.size(); i++) {
			switch (i + 1) {
			case 1:

//				img.DisplayImage(productimages.get(i), productImg1);
				imageLoader. displayImage(productimages.get(i), productImg1, options);
				pImgUrl = productimages.get(i);
				ll1.setVisibility(View.VISIBLE);
				productImg1.setVisibility(View.VISIBLE);
				productImg1.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
//						img.DisplayImage(pImgUrl, imgproductid);
						imageLoader. displayImage(pImgUrl, imgproductid, options);

					}
				});
				break;
			case 2:
				imageLoader. displayImage(productimages.get(i), productImg2, options);
//				img.DisplayImage(productimages.get(i), productImg2);
				productImg2.setVisibility(View.VISIBLE);
				ll2.setVisibility(View.VISIBLE);
				pImgUrl1 = productimages.get(i);
				productImg2.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
//						img.DisplayImage(pImgUrl1, imgproductid);
						imageLoader. displayImage(pImgUrl1, imgproductid, options);
					}
				});
				break;
			case 3:
				imageLoader. displayImage(productimages.get(i), productImg3, options);
//				img.DisplayImage(productimages.get(i), productImg3);
//				imageLoader. displayImage(productimages.get(i), productImg2, options);
				productImg3.setVisibility(View.VISIBLE);
				pImgUrl2 = productimages.get(i);
				ll3.setVisibility(View.VISIBLE);
				productImg3.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
//						img.DisplayImage(pImgUrl2, imgproductid);
						imageLoader. displayImage(pImgUrl2, imgproductid, options);

					}
				});
				break;
			case 4:

//				img.DisplayImage(productimages.get(i), productImg4);
				imageLoader. displayImage(productimages.get(i), productImg4, options);
				productImg4.setVisibility(View.VISIBLE);
				pImgUrl3 = productimages.get(i);
				ll4.setVisibility(View.VISIBLE);
				productImg4.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
//						img.DisplayImage(pImgUrl3, imgproductid);
						imageLoader. displayImage(pImgUrl3, imgproductid, options);
					}
				});
				break;
			case 5:

//				img.DisplayImage(productimages.get(i), productImg5);
				imageLoader. displayImage(productimages.get(i), productImg5, options);
				productImg5.setVisibility(View.VISIBLE);
				pImgUrl4 = productimages.get(i);
				ll5.setVisibility(View.VISIBLE);
				productImg5.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
//						img.DisplayImage(pImgUrl4, imgproductid);
						imageLoader. displayImage(pImgUrl4, imgproductid, options);
					}
				});
				break;

			default:
				break;
			}

		}

	}

	public class GetProductDetails extends AsyncTask<Void, Void, Void> {

		protected void onPreExecute() {
			super.onPreExecute();
			pd = new ProgressDialog(ProductDetails.this);
			pd.setMessage("Please wait...");
			pd.setCancelable(false);
			pd.show();
		}

		@SuppressLint("ShowToast")
		protected Void doInBackground(Void... arg0) {
			// Creating service handler class instance
			ServiceHandler sh = new ServiceHandler();
			// Making a request to url and getting response
			String jsonStr = sh.makeServiceCall(Urls.GetProductdetails
					+ product_id, ServiceHandler.GET);

			Log.e("CategoryResponse: ", "> " + jsonStr);
			if (jsonStr != null) {
				try {
					JSONObject jsonobjectprojectdetails = new JSONObject(
							jsonStr);
					Log.e("jsondealproduct: ", "> " + jsonobjectprojectdetails);
					JSONObject jsonobjectproject = jsonobjectprojectdetails
							.getJSONObject("product");

					model = jsonobjectproject.optString("model");
					quantity = jsonobjectproject.optString("quantity");
					location = jsonobjectproject.optString("location");
					stock_status_id = jsonobjectproject
							.optString("stock_status_id");
					image = jsonobjectproject.optString("image");
					shipping = jsonobjectproject.optString("shipping");
					price = jsonobjectproject.optString("price");
					points = jsonobjectproject.optString("points");
					tax_class_id = jsonobjectproject.optString("tax_class_id");
					date_available = jsonobjectproject
							.optString("date_available");
					weight = jsonobjectproject.optString("weight");
					weight_class_id = jsonobjectproject
							.optString("weight_class_id");
					length = jsonobjectproject.optString("length");
					width = jsonobjectproject.optString("width");
					height = jsonobjectproject.optString("height");
					name = jsonobjectproject.optString("name");
					description = jsonobjectproject.optString("description");
					special = jsonobjectproject.optString("special");
					rating = jsonobjectproject.optString("rating");
					discount = jsonobjectproject.optString("discount");
					reward = jsonobjectproject.optString("reward");
					stock_status = jsonobjectproject.optString("stock_status");
					JSONArray jsonproductimages = jsonobjectproject
							.getJSONArray("images");
					if (jsonproductimages.length() > 0) {
						for (int i = 0; i < jsonproductimages.length(); i++) {
							JSONObject json_data = jsonproductimages
									.getJSONObject(i);
							productimages.add(json_data.getString("image"));
						}
					}

					// Json SubArray of Options field

					JSONArray jsonarrayoptions = jsonobjectproject
							.getJSONArray("options");
					optionArrayLength=jsonarrayoptions.length();

					// OptionArray Check if length is gretaer then zero then
					// if block execute
					if (jsonarrayoptions.length() > 0) {
						for (int i = 0; i < jsonarrayoptions.length(); i++) {
							JSONObject Jsonobjectofoptions = jsonarrayoptions
									.getJSONObject(i);
							JSONArray jsonarrayofproductoptionvalue = Jsonobjectofoptions
									.getJSONArray("product_option_value");
							ArrayList<ProductOptionValue> productOptionValueslist = new ArrayList<ProductOptionValue>();

							for (int j = 0; j < jsonarrayofproductoptionvalue
									.length(); j++) {
								JSONObject jsonobjectofproductoptionvalue = jsonarrayofproductoptionvalue
										.getJSONObject(j);
								;
								ProductOptionValue productOptionValue = new ProductOptionValue(
										jsonobjectofproductoptionvalue
												.optString("product_option_value_id"),
										jsonobjectofproductoptionvalue
												.optString("option_value_id"),
										jsonobjectofproductoptionvalue
												.optString("name"),
										jsonobjectofproductoptionvalue
												.optString("image"),
										jsonobjectofproductoptionvalue
												.optString("quantity"),
										jsonobjectofproductoptionvalue
												.optString("subtract"),
										jsonobjectofproductoptionvalue
												.optString("price"),
										jsonobjectofproductoptionvalue
												.optString("price_prefix"),
										jsonobjectofproductoptionvalue
												.optString("weight"),
										jsonobjectofproductoptionvalue
												.optString("weight_prefix"));
								productOptionValueslist.add(productOptionValue);
							}

							ProductOptionModel productOptionModel = new ProductOptionModel(
									Jsonobjectofoptions
											.optString("product_option_id"),
									Jsonobjectofoptions.optString("option_id"),
									Jsonobjectofoptions.optString("name"),
									Jsonobjectofoptions.optString("type"),
									Jsonobjectofoptions.optString("value"),
									Jsonobjectofoptions.optString("required"),
									productOptionValueslist);
							productOptionList.add(productOptionModel);
						}

					} else {
						// OptionArray length is zero
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			return null;
		}

		@SuppressLint("ShowToast")
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if (pd.isShowing())
				pd.dismiss();
			txtproductname.setText(name);
			txtdesciptionupdate.setText(description);
//			ImageLoader img = new ImageLoader(ProductDetails.this);
//			img.DisplayImage(image, imgproductid);
			imageLoader. displayImage(image, imgproductid, options);
			txtrating.setText(rating);
//			Toast.makeText(getApplicationContext(), "price= "+price, 3).show();
			txtmrpofproduct.setText(price);
			if (special.equals("")) {
				tvLine.setVisibility(View.GONE);
			}
			txtsellingpriceofproduct.setText(special);
			if (quantity.equals("0")) {
				
				txtstockstatus.setText(stock_status);
				txtstockstatusdeliver.setText("Sorry For InConvineus");
			} else {
				txtstockstatus.setText("In Stock");
			}
			showimagesofproduct();
			ratingshow();
			// addRadioButtons(3);
			// addChkBox(4);
			addOptionViews();

		}
	}

	@SuppressLint("ShowToast")
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.linearlayoutaddtocard:
			/*
			 * if (radioButtonSelectedId == 0) {
			 * 
			 * } else if() { Toast.makeText(ProductDetails.this,
			 * "Please Select Radio Button", 10).show(); }
			 */
			// radioButtonSelectedId = 0, radioButtonParrenmtId = 0,
			// chkBoxParrentId = 0;
			Log.e("radioButtonSelectedId", "" + radioButtonSelectedId);
			Log.e("radioButtonParrenmtId", "" + radioButtonParrenmtId);
			Log.e("chkBoxParrentId", "" + chkBoxParrentId);
			if (radioButtonParrenmtId > 0) {
				if (radioButtonSelectedId > 0) {

					String quantity = "1";
					HashMap<String, String> queryValues = new HashMap<String, String>();

					ArrayList<HashMap<String, String>> arraylist = controller
							.getColuamData();
					if (arraylist.size() > 0) {

						for (int i = 0; i < arraylist.size(); i++) {
							Log.e("Arry Size ", "" + arraylist.size());
							Log.e("i ", "" + i);
							String ProdcutIDgetfromDB = arraylist.get(i).get(
									"ProdcutID");
							String quantityfetfromDB = arraylist.get(i).get(
									"quantity");
							String radioButtonSelectedIdFromDB = arraylist.get(
									i).get("radioButtonSelectedId");
							Log.e("radioButtonSelectedIdFromDB", ""
									+ radioButtonSelectedIdFromDB);
							// String = arraylist.get(
							// i).get("radioButtonSelectedId");
							if (ProdcutIDgetfromDB.equals(product_id)) {
								Log.e("product id Matched ", "true");
								if (radioButtonSelectedIdFromDB.equals(""
										+ radioButtonSelectedId)) {
									Log.e("radioButtonSelectedId matched",
											"true");
									Log.e("Updating ", "true");
									// if
									// (arraylist.get(i).get("chkBoxParrentId")
									// .equals(chkBoxParrentId)) {
									int quantitycounter = Integer
											.parseInt(quantityfetfromDB);
									quantitycounter = quantitycounter + 1;
									String quantityNO = quantitycounter + "";
									controller.updateProduct(
											ProdcutIDgetfromDB, quantityNO,
											radioButtonSelectedIdFromDB);
									Toast.makeText(getApplicationContext(),
											"Adding product to cart", 10)
											.show();
									Intent igotocart = new Intent(
											ProductDetails.this, MyCart.class);
									startActivity(igotocart);
									break;
									// }

								} else {
									Log.e("radioButtonSelectedId dint Matched ",
											"true");
									if (i == arraylist.size() - 1) {
										Log.e("Arry Size ",
												"" + arraylist.size());
										Log.e("i ", "" + i);
										Log.e("adding new  ", "true");
										queryValues
												.put("ProdcutID", product_id);
										queryValues.put("quantity", "" + 1);
										queryValues
												.put("radioButtonSelectedId",
														""
																+ radioButtonSelectedId);
										queryValues
												.put("radioButtonParrenmtId",
														""
																+ radioButtonParrenmtId);
										queryValues.put("chkBoxParrentId", ""
												+ chkBoxParrentId);
										queryValues
												.put("chkBoxSelectedID",
														convertArrayToString(arrChkBoxSelectedID));
										controller.insertUser(queryValues);
										Toast.makeText(getApplicationContext(),
												"Adding product to cart", 10)
												.show();
										Intent igotocart = new Intent(
												ProductDetails.this,
												MyCart.class);
										startActivity(igotocart);
									}
								}

							} else {
								Log.e("product id dint Matched ", "true");
								if (i == arraylist.size() - 1) {

									Log.e("Arry Size ", "" + arraylist.size());
									Log.e("i ", "" + i);
									Log.e("adding new  ", "true");
									Log.e("product dint Matched ", "true");
									queryValues.put("ProdcutID", product_id);
									queryValues.put("quantity", "" + 1);
									queryValues.put("radioButtonSelectedId", ""
											+ radioButtonSelectedId);
									queryValues.put("radioButtonParrenmtId", ""
											+ radioButtonParrenmtId);
									queryValues.put("chkBoxParrentId", ""
											+ chkBoxParrentId);
									queryValues
											.put("chkBoxSelectedID",
													convertArrayToString(arrChkBoxSelectedID));
									controller.insertUser(queryValues);
									Toast.makeText(getApplicationContext(),
											"Adding product to cart", 10)
											.show();
									Intent igotocart = new Intent(
											ProductDetails.this, MyCart.class);
									startActivity(igotocart);
								}
							}
						}
					} else {
						Log.e("array size 0 ", "true");
						Log.e("adding new ", "true");
						queryValues.put("ProdcutID", product_id);
						queryValues.put("quantity", "" + 1);
						queryValues.put("radioButtonSelectedId", ""
								+ radioButtonSelectedId);
						queryValues.put("radioButtonParrenmtId", ""
								+ radioButtonParrenmtId);
						queryValues
								.put("chkBoxParrentId", "" + chkBoxParrentId);
						queryValues.put("chkBoxSelectedID",
								convertArrayToString(arrChkBoxSelectedID));
						controller.insertUser(queryValues);
						Toast.makeText(getApplicationContext(),
								"Adding product to cart", 10).show();
						Intent igotocart = new Intent(ProductDetails.this,
								MyCart.class);
						startActivity(igotocart);
					}
					// linearlayoutaddtocard.setVisibility(View.GONE);
					// linearlayoutgotocard.setVisibility(View.VISIBLE);
				} else {
					Toast.makeText(ProductDetails.this, "Please select size", 4)
							.show();
				}
			} else {
				Log.e("radio id not found", "true");
				String quantity = "1";
				HashMap<String, String> queryValues = new HashMap<String, String>();

				ArrayList<HashMap<String, String>> arraylist = controller
						.getColuamData();
				if (arraylist.size() > 0) {

					for (int i = 0; i < arraylist.size(); i++) {
						String ProdcutIDgetfromDB = arraylist.get(i).get(
								"ProdcutID");
						String quantityfetfromDB = arraylist.get(i).get(
								"quantity");
						if (ProdcutIDgetfromDB.equals(product_id)) {
							Log.e("product id Matched ", "true");
							int quantitycounter = Integer
									.parseInt(quantityfetfromDB);
							quantitycounter = quantitycounter + 1;
							String quantityNO = quantitycounter + "";
							controller.update_byID(ProdcutIDgetfromDB,
									quantityNO);
							Toast.makeText(getApplicationContext(),
									"Adding product to cart", 10)

							.show();
							Intent igotocart = new Intent(ProductDetails.this,
									MyCart.class);
							startActivity(igotocart);
							break;
						} else {
							Log.e("product id dint Matched ", "true");
							if (i == arraylist.size() - 1) {
								Log.e("Arry Size ", "" + arraylist.size());
								Log.e("i ", "" + i);
								Log.e("adding new  ", "true");
								Log.e("product dint Matched ", "true");
								queryValues.put("ProdcutID", product_id);
								queryValues.put("quantity", "" + 1);
								queryValues
										.put("radioButtonSelectedId", "" + 0);
								queryValues.put("radioButtonParrenmtId", ""
										+ radioButtonParrenmtId);
								queryValues.put("chkBoxParrentId", ""
										+ chkBoxParrentId);
								queryValues
										.put("chkBoxSelectedID",
												convertArrayToString(arrChkBoxSelectedID));
								controller.insertUser(queryValues);
								Toast.makeText(getApplicationContext(),
										"Adding product to cart", 10)

								.show();
								Intent igotocart = new Intent(
										ProductDetails.this, MyCart.class);
								startActivity(igotocart);
							}
						}
					}
				} else {
					queryValues.put("ProdcutID", product_id);
					queryValues.put("quantity", quantity);
					queryValues.put("radioButtonSelectedId", "" + 0);
					queryValues.put("radioButtonParrenmtId", ""
							+ radioButtonParrenmtId);
					queryValues.put("chkBoxParrentId", "" + chkBoxParrentId);
					queryValues.put("chkBoxSelectedID",
							convertArrayToString(arrChkBoxSelectedID));
					controller.insertUser(queryValues);
					Toast.makeText(getApplicationContext(),
							"Adding product to cart", 10).show();
					Intent igotocart = new Intent(ProductDetails.this,
							MyCart.class);
					startActivity(igotocart);
				}
				// linearlayoutaddtocard.setVisibility(View.GONE);
				// linearlayoutgotocard.setVisibility(View.VISIBLE);
			}

			break;
		case R.id.linearlayoutgotocard:
			// Intent igotocart = new Intent(ProductDetails.this, MyCart.class);
			// startActivity(igotocart);

			break;
		case R.id.btnbuynow:
			Log.e("radioButtonSelectedId", "" + radioButtonSelectedId);
			Log.e("radioButtonParrenmtId", "" + radioButtonParrenmtId);
			Log.e("chkBoxParrentId", "" + chkBoxParrentId);
			if (radioButtonParrenmtId > 0) {
				if (radioButtonSelectedId > 0) {

					@SuppressWarnings("unused")
					String quantity = "1";
					HashMap<String, String> queryValues = new HashMap<String, String>();

					ArrayList<HashMap<String, String>> arraylist = controller
							.getColuamData();
					if (arraylist.size() > 0) {

						for (int i = 0; i < arraylist.size(); i++) {
							Log.e("Arry Size ", "" + arraylist.size());
							Log.e("i ", "" + i);
							String ProdcutIDgetfromDB = arraylist.get(i).get(
									"ProdcutID");
							String quantityfetfromDB = arraylist.get(i).get(
									"quantity");
							String radioButtonSelectedIdFromDB = arraylist.get(
									i).get("radioButtonSelectedId");
							Log.e("radioButtonSelectedIdFromDB", ""
									+ radioButtonSelectedIdFromDB);

							if (ProdcutIDgetfromDB.equals(product_id)) {
								Log.e("product id Matched ", "true");
								if (radioButtonSelectedIdFromDB.equals(""
										+ radioButtonSelectedId)) {
									Log.e("radioButtonSelectedId matched",
											"true");
									Log.e("Updating ", "true");

									int quantitycounter = Integer
											.parseInt(quantityfetfromDB);
									quantitycounter = quantitycounter + 1;
									String quantityNO = quantitycounter + "";
									controller.updateProduct(
											ProdcutIDgetfromDB, quantityNO,
											radioButtonSelectedIdFromDB);
									getjsonstring();

									Intent igotocart = new Intent(
											ProductDetails.this,
											BuyNowCart.class);
									igotocart.putExtra("jsonstringproduct",
											jsonstringproduct);
									startActivity(igotocart);
									break;

								} else {
									Log.e("radioButtonSelectedId dint Matched ",
											"true");
									if (i == arraylist.size() - 1) {
										Log.e("Arry Size ",
												"" + arraylist.size());
										Log.e("i ", "" + i);
										Log.e("adding new  ", "true");
										queryValues
												.put("ProdcutID", product_id);
										queryValues.put("quantity", "" + 1);
										queryValues
												.put("radioButtonSelectedId",
														""
																+ radioButtonSelectedId);
										queryValues
												.put("radioButtonParrenmtId",
														""
																+ radioButtonParrenmtId);
										queryValues.put("chkBoxParrentId", ""
												+ chkBoxParrentId);
										queryValues
												.put("chkBoxSelectedID",
														convertArrayToString(arrChkBoxSelectedID));
										controller.insertUser(queryValues);
										getjsonstring();

										Intent igotocart = new Intent(
												ProductDetails.this,
												BuyNowCart.class);
										igotocart.putExtra("jsonstringproduct",
												jsonstringproduct);

										startActivity(igotocart);
									}
								}

							} else {
								Log.e("product id dint Matched ", "true");
								if (i == arraylist.size() - 1) {

									Log.e("Arry Size ", "" + arraylist.size());
									Log.e("i ", "" + i);
									Log.e("adding new  ", "true");
									Log.e("product dint Matched ", "true");
									queryValues.put("ProdcutID", product_id);
									queryValues.put("quantity", "" + 1);
									queryValues.put("radioButtonSelectedId", ""
											+ radioButtonSelectedId);
									queryValues.put("radioButtonParrenmtId", ""
											+ radioButtonParrenmtId);
									queryValues.put("chkBoxParrentId", ""
											+ chkBoxParrentId);
									queryValues
											.put("chkBoxSelectedID",
													convertArrayToString(arrChkBoxSelectedID));
									controller.insertUser(queryValues);
									getjsonstring();

									Intent igotocart = new Intent(
											ProductDetails.this,
											BuyNowCart.class);
									igotocart.putExtra("jsonstringproduct",
											jsonstringproduct);

									startActivity(igotocart);
								}
							}
						}
					} else {
						Log.e("array size 0 ", "true");
						Log.e("adding new ", "true");
						queryValues.put("ProdcutID", product_id);
						queryValues.put("quantity", "" + 1);
						queryValues.put("radioButtonSelectedId", ""
								+ radioButtonSelectedId);
						queryValues.put("radioButtonParrenmtId", ""
								+ radioButtonParrenmtId);
						queryValues
								.put("chkBoxParrentId", "" + chkBoxParrentId);
						queryValues.put("chkBoxSelectedID",
								convertArrayToString(arrChkBoxSelectedID));
						controller.insertUser(queryValues);
						getjsonstring();

						Intent igotocart = new Intent(ProductDetails.this,
								BuyNowCart.class);
						igotocart.putExtra("jsonstringproduct",
								jsonstringproduct);

						startActivity(igotocart);
					}
					// linearlayoutaddtocard.setVisibility(View.GONE);
					// linearlayoutgotocard.setVisibility(View.VISIBLE);
				} else {
					Toast.makeText(ProductDetails.this, "Please select size", 4)
							.show();
				}
			} else {
				Log.e("radio id not found", "true");
				String quantity = "1";
				HashMap<String, String> queryValues = new HashMap<String, String>();

				ArrayList<HashMap<String, String>> arraylist = controller
						.getColuamData();
				if (arraylist.size() > 0) {

					for (int i = 0; i < arraylist.size(); i++) {
						String ProdcutIDgetfromDB = arraylist.get(i).get(
								"ProdcutID");
						String quantityfetfromDB = arraylist.get(i).get(
								"quantity");
						if (ProdcutIDgetfromDB.equals(product_id)) {
							Log.e("product id Matched ", "true");
							int quantitycounter = Integer
									.parseInt(quantityfetfromDB);
							quantitycounter = quantitycounter + 1;
							String quantityNO = quantitycounter + "";
							controller.update_byID(ProdcutIDgetfromDB,
									quantityNO);
							getjsonstring();

							Intent igotocart = new Intent(ProductDetails.this,
									BuyNowCart.class);
							igotocart.putExtra("jsonstringproduct",
									jsonstringproduct);

							startActivity(igotocart);
							break;
						} else {
							Log.e("product id dint Matched ", "true");
							if (i == arraylist.size() - 1) {
								Log.e("Arry Size ", "" + arraylist.size());
								Log.e("i ", "" + i);
								Log.e("adding new  ", "true");
								Log.e("product dint Matched ", "true");
								queryValues.put("ProdcutID", product_id);
								queryValues.put("quantity", "" + 1);
								queryValues
										.put("radioButtonSelectedId", "" + 0);
								queryValues.put("radioButtonParrenmtId", ""
										+ radioButtonParrenmtId);
								queryValues.put("chkBoxParrentId", ""
										+ chkBoxParrentId);
								queryValues
										.put("chkBoxSelectedID",
												convertArrayToString(arrChkBoxSelectedID));
								controller.insertUser(queryValues);
								getjsonstring();

								Intent igotocart = new Intent(
										ProductDetails.this, BuyNowCart.class);
								igotocart.putExtra("jsonstringproduct",
										jsonstringproduct);

								startActivity(igotocart);
							}
						}
					}
				} else {
					queryValues.put("ProdcutID", product_id);
					queryValues.put("quantity", quantity);
					queryValues.put("radioButtonSelectedId", "" + 0);
					queryValues.put("radioButtonParrenmtId", ""
							+ radioButtonParrenmtId);
					queryValues.put("chkBoxParrentId", "" + chkBoxParrentId);
					queryValues.put("chkBoxSelectedID",
							convertArrayToString(arrChkBoxSelectedID));
					controller.insertUser(queryValues);
					getjsonstring();
					Intent igotocart = new Intent(ProductDetails.this,
							BuyNowCart.class);
					igotocart.putExtra("jsonstringproduct", jsonstringproduct);

					startActivity(igotocart);
				}

			}

			break;
		case R.id.linearlayoutshare:
			Intent sharingIntent = new Intent(
					android.content.Intent.ACTION_SEND);
			sharingIntent.setType("text/plain");
			String shareBody = "Here is the share content body";
			sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
					"Subject Here");
			sharingIntent
					.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
			startActivity(Intent.createChooser(sharingIntent, "Share via"));
			break;
		case R.id.txtdescription:

//			if (clicktxt == false) {
//				txtdesciptionupdate.setVisibility(View.VISIBLE);
//				clicktxt = true;
//			} else {
//				clicktxt = false;
//			}

			break;
		default:
			break;
		}

	}

	private void getjsonstring() {
		int quantity = 1;
		JSONObject productlist = new JSONObject();
		JSONArray jsonarrayproduct = new JSONArray();
		JSONObject products = new JSONObject();

		JSONArray jsonArrayOption = new JSONArray();
		JSONObject jsonObjectOption = new JSONObject();
		JSONArray jsonArrayChkBoxId = new JSONArray();
		try {
			jsonObjectOption.put("" + radioButtonParrenmtId,
					radioButtonSelectedId);
			// jsonObjectOption.put(chkBoxParrentId,convertStringToArray(chkBoxSelectedID)
			// );
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		jsonArrayOption.put(jsonObjectOption);
		try {
			productlist.put("product_id", product_id);
			productlist.put("quantity", quantity);
			productlist.put("option", jsonArrayOption);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		jsonarrayproduct.put(productlist);
		try {
			products.put("products", jsonarrayproduct);
			products.put("language_id", language_id);
			products.put("tokan",
					Urls.tokan);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		jsonstringproduct = products.toString();
		Log.e("jsonStr", jsonstringproduct);

	}

	class GalleryImageAdapter extends BaseAdapter {
		private Context mContext;

		private Integer[] mImageIds = { R.drawable.item, R.drawable.item,
				R.drawable.item, R.drawable.item, R.drawable.item };

		public GalleryImageAdapter(Context context) {
			mContext = context;
		}

		public int getCount() {
			return mImageIds.length;
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		// Override this method according to your need
		public View getView(int index, View view, ViewGroup viewGroup) {
			// TODO Auto-generated method stub
			ImageView i = new ImageView(mContext);

			i.setImageResource(mImageIds[index]);
			i.setLayoutParams(new Gallery.LayoutParams(70, 70));

			i.setScaleType(ImageView.ScaleType.FIT_XY);

			return i;
		}

	}

	public void addChkBox(int number) {

		for (int row = 0; row < 1; row++) {
			LinearLayout ll = new LinearLayout(this);
			ll.setOrientation(LinearLayout.VERTICAL);

			for (int i = 1; i <= number; i++) {
				CheckBox rdbtn = new CheckBox(this);
				rdbtn.setId((row * 2) + i);
				switch (i) {
				case 1:
					rdbtn.setText("CheckBox " + i);
					break;
				case 2:
					rdbtn.setText("CheckBox " + i);
					break;
				case 3:
					rdbtn.setText("CheckBox " + i);
					break;
				case 4:
					rdbtn.setText("CheckBox " + i);
					break;
				default:
					break;
				}

				ll.addView(rdbtn);

			}
			llChkBox.addView(ll);
		}

	}

	public void addRadioButtons(int number) {

		for (int row = 0; row < 1; row++) {
			LinearLayout ll = new LinearLayout(this);
			ll.setOrientation(LinearLayout.HORIZONTAL);

			for (int i = 1; i <= number; i++) {
				RadioButton rdbtn = new RadioButton(this);
				rdbtn.setId((row * 2) + i);
				switch (i) {
				case 1:
					rdbtn.setText("Small ");
					break;
				case 2:
					rdbtn.setText("Medium ");
					break;
				case 3:
					rdbtn.setText("Large ");
					break;

				default:
					break;
				}
				ll.addView(rdbtn);

			}
			radiogroup.addView(ll);
		}

	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	void addOptionViews() {

		for (int i = 0; i < productOptionList.size(); i++) {
			if (productOptionList.get(i).getName().equals("Radio")) {
				radioButtonParrenmtId = Integer.parseInt(productOptionList.get(
						i).getProduct_option_id());

				LinearLayout ll = new LinearLayout(this);
				ll.setOrientation(LinearLayout.HORIZONTAL);
				for (int j = 0; j < productOptionList.get(i)
						.getProductoptionvalue().size(); j++) {
					RadioButton rdbtn = new RadioButton(this);
					rdbtn.setId(Integer.parseInt(productOptionList.get(i)
							.getProductoptionvalue().get(j)
							.getProduct_option_value_id()));

					rdbtn.setText(productOptionList.get(i)
							.getProductoptionvalue().get(j).getName());
					rdbtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {

						@SuppressLint("NewApi")
						@Override
						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {
							// TODO Auto-generated method stub
							if (isChecked) {
								radioButtonSelectedId = buttonView.getId();
								Log.e("radioButtonSelectedId", ""
										+ radioButtonSelectedId);
								Log.e("radioButtonParrenmtId", ""
										+ radioButtonParrenmtId);
							}

							Log.e("Checked Chnged",
									"my id is  " + buttonView.getId() + "  "
											+ isChecked);

						}
					});
					radiogroup.addView(rdbtn);
				}

			} else if (productOptionList.get(i).getName().equals("Checkbox")) {
				chkBoxParrentId = Integer.parseInt(productOptionList.get(i)
						.getProduct_option_id());
				LinearLayout ll = new LinearLayout(this);
				ll.setOrientation(LinearLayout.VERTICAL);
				for (int j = 0; j < productOptionList.get(i)
						.getProductoptionvalue().size(); j++) {
					CheckBox checkBox = new CheckBox(this);
					checkBox.setId(Integer.parseInt(productOptionList.get(i)
							.getProductoptionvalue().get(j)
							.getProduct_option_value_id()));
					checkBox.setText(productOptionList.get(i)
							.getProductoptionvalue().get(j).getName());
					checkBox.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							if (((CheckBox) v).isChecked()) {
								arrChkBoxSelectedID.add("" + v.getId());
								Log.e("Added", "" + v.getId());
								Log.e("chkBoxParrentId", "" + chkBoxParrentId);
							} else {
								if (arrChkBoxSelectedID.size() > 0) {
									for (int k = 0; k < arrChkBoxSelectedID
											.size(); k++) {
										if (arrChkBoxSelectedID.get(k).equals(
												"" + v.getId())) {

											arrChkBoxSelectedID.remove(k);

										}
									}
								}

							}
						}
					});
					ll.addView(checkBox);
				}
				llChkBox.addView(ll);

			} else if (productOptionList.get(i).getName().equals("Select")) {
				LinearLayout ll = new LinearLayout(this);
				ll.setOrientation(LinearLayout.HORIZONTAL);
				for (int j = 0; j < productOptionList.get(i)
						.getProductoptionvalue().size(); j++) {
					RadioButton rdbtn = new RadioButton(this);
					rdbtn.setId(Integer.parseInt(productOptionList.get(i)
							.getProductoptionvalue().get(j)
							.getProduct_option_value_id()));
					rdbtn.setText(productOptionList.get(i)
							.getProductoptionvalue().get(j).getName());
					// ll.addView(rdbtn);
					rdbtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {

						@SuppressLint("NewApi")
						@Override
						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {
							// TODO Auto-generated method stub
							if (isChecked) {
								selectedColourID = buttonView.getId();
								Log.e("selectedColourID", "" + selectedColourID);

							}

							Log.e("Checked Chnged",
									"my id is  " + buttonView.getId() + "  "
											+ isChecked);

						}
					});
					radiogroupSelect.addView(rdbtn);
				}
				// ((ViewGroup) findViewById(R.id.radiogroup)).addView(ll);
			}
		}
	}

	public static String convertArrayToString(ArrayList<String> array) {
		String str = "";
		for (int i = 0; i < array.size(); i++) {
			str = str + array.get(i);
			// Do not append comma at the end of last element
			if (i < array.size() - 1) {
				str = str + strSeparator;
			}
		}
		return str;
	}
}
