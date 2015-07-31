package com.ordervenue.android;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.onjection.PagerViewImageAdapter.CustomGridViewAdapter;
import com.onjection.opencart.Utilz.Constant;
import com.onjection.opencart.Utilz.ServiceHandler;
import com.onjection.opencart.Utilz.Urls;
import com.onjection.opencart.model.CategoryProduct;
import com.onjection.opencart.model.ProductOptionModel;
import com.onjection.opencart.model.ProductOptionValue;

public class SearchActivity extends Activity {
	EditText edtSearch;
	ProgressDialog pd;
	String ctaegory_id;
	LinearLayout llSearch, lnrlayoutheader;
	ArrayList<String> productimages = new ArrayList<String>();
	ProductOptionModel productOptionModel;
	private ArrayList<CategoryProduct> categoryproductlist = new ArrayList<CategoryProduct>();
	CategoryProduct categoryProduct;
	GridView gridView;
	CustomGridViewAdapter customGridAdapter;
	InputMethodManager imm;
	TextView txtheader;
	ImageView ivBack,ivCart;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_search);
		llSearch = (LinearLayout) findViewById(R.id.llSearch);
		lnrlayoutheader = (LinearLayout) findViewById(R.id.lnrlayoutheader);
		edtSearch = (EditText) findViewById(R.id.edtSerch);
		txtheader = (TextView) findViewById(R.id.txtheader);
		gridView = (GridView) findViewById(R.id.gridview);
		ivBack = (ImageView) findViewById(R.id.ivBack);
		ivCart = (ImageView) findViewById(R.id.ivCart);
		ivCart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(SearchActivity.this, MyCart.class);
				startActivity(i);
			}
		});
		ImageView ivSerach = (ImageView) findViewById(R.id.ivSearch);
		ivSerach.setOnClickListener(new OnClickListener() {

			@SuppressLint("ShowToast")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (edtSearch.getText().toString() != "") {
					Constant.hideKeyboard(SearchActivity.this);
					new SearchProducts().execute();
				} else {
					Toast.makeText(SearchActivity.this, "Enter Key word", 3)
							.show();

				}

			}
		});
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long arg3) {
				String product_id = categoryproductlist.get(position)
						.getProduct_id();
				Intent inetntproductdetails = new Intent(SearchActivity.this,
						ProductDetails.class);
				inetntproductdetails.putExtra("Key_Product_id", product_id);
				startActivity(inetntproductdetails);
			}
		});
		ivBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	public void hideSoftKeyboard() {
		if (getCurrentFocus() != null) {
			InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
			inputMethodManager.hideSoftInputFromWindow(getCurrentFocus()
					.getWindowToken(), 0);
		}
	}

	@SuppressLint("InflateParams")
	public class SearchProducts extends AsyncTask<Void, Void, Void> {

		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			pd = new ProgressDialog(SearchActivity.this);
			pd.setTitle("Searching");
			pd.setMessage("Please wait...");
			pd.setCancelable(false);
			pd.show();

		}

		@SuppressLint("ShowToast")
		protected Void doInBackground(Void... arg0) {
			ServiceHandler sh = new ServiceHandler();
			// Making a request to url and getting response
			String srch =  edtSearch.getText().toString().trim();
			srch = srch.replace(" ", "");
			String str = srch.replaceAll("\\P{Alnum}", "").toLowerCase();
			Log.e("srch = ", srch + " " +str );
			String jsonStr = sh.makeServiceCall(Urls.SearchProducts
					+str, ServiceHandler.GET);

			Log.e("Response: ", "> " + jsonStr);
			if (jsonStr != null) {
				try {
					JSONObject jsonobject = new JSONObject(jsonStr);
					String status = jsonobject.getString("status");
					if (status.equals("success")) {
						JSONArray jsonarray = jsonobject
								.getJSONArray("products");
						if (jsonarray.length() > 0) {
							for (int i = 0; i < jsonarray.length(); i++) {
								JSONObject jsonproductobject = jsonarray
										.getJSONObject(i);
								JSONArray jsonproductimages = jsonproductobject
										.getJSONArray("images");

								if (jsonproductimages.length() > 0) {
									for (int j = 0; j < jsonproductimages
											.length(); j++) {
										JSONObject json_data = jsonproductimages
												.getJSONObject(j);
										productimages.add(json_data
												.getString("image"));
									}
								}

								// Json SubArray of Options field

								JSONArray jsonarrayoptions = jsonproductobject
										.getJSONArray("options");
								ArrayList<ProductOptionModel> productOptionList = new ArrayList<ProductOptionModel>();

								// OptionArray Check if length is gretaer then
								// zero
								// then
								// if block execute
								if (jsonarrayoptions.length() > 0) {
									for (int k = 0; k < jsonarrayoptions
											.length(); k++) {
										JSONObject Jsonobjectofoptions = jsonarrayoptions
												.getJSONObject(k);
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
											productOptionValueslist
													.add(productOptionValue);
										}

										productOptionModel = new ProductOptionModel(
												Jsonobjectofoptions
														.optString("product_option_id"),
												Jsonobjectofoptions
														.optString("option_id"),
												Jsonobjectofoptions
														.optString("name"),
												Jsonobjectofoptions
														.optString("type"),
												Jsonobjectofoptions
														.optString("value"),
												Jsonobjectofoptions
														.optString("required"),
												productOptionValueslist);
										productOptionList
												.add(productOptionModel);

									}
								}
								categoryProduct = new CategoryProduct(
										jsonproductobject
												.optString("product_id"),
										jsonproductobject.optString("model"),
										jsonproductobject.optString("sku"),
										jsonproductobject.optString("upc"),
										jsonproductobject.optString("ean"),
										jsonproductobject.optString("jan"),
										jsonproductobject.optString("isbn"),
										jsonproductobject.optString("mpn"),
										jsonproductobject.optString("location"),
										jsonproductobject.optString("quantity"),
										jsonproductobject
												.optString("stock_status_id"),
										jsonproductobject.optString("image"),
										jsonproductobject
												.optString("manufacturer_id"),
										jsonproductobject.optString("shipping"),
										jsonproductobject.optString("price"),
										jsonproductobject.optString("points"),
										jsonproductobject
												.optString("tax_class_id"),
										jsonproductobject
												.optString("date_available"),
										jsonproductobject.optString("weight"),
										jsonproductobject
												.optString("weight_class_id"),
										jsonproductobject.optString("length"),
										jsonproductobject.optString("width"),
										jsonproductobject.optString("height"),
										jsonproductobject
												.optString("length_class_id"),
										jsonproductobject.optString("subtract"),
										jsonproductobject.optString("minimum"),
										jsonproductobject
												.optString("sort_order"),
										jsonproductobject.optString("status"),
										jsonproductobject.optString("viewed"),
										jsonproductobject
												.optString("date_added"),
										jsonproductobject
												.optString("date_modified"),
										jsonproductobject
												.optString("language_id"),
										jsonproductobject.optString("name"),
										jsonproductobject
												.optString("description"),
										jsonproductobject.optString("tag"),
										jsonproductobject.optString("store_id"),
										jsonproductobject
												.optString("manufacturer"),
										jsonproductobject.optString("discount"),
										jsonproductobject.optString("special"),
										jsonproductobject.optString("reward"),
										jsonproductobject
												.optString("stock_status"),
										jsonproductobject.optString("rating"),
										jsonproductobject.optString("reviews"),
										productOptionList);
								categoryproductlist.add(categoryProduct);
							}
						} else {
							Toast.makeText(SearchActivity.this, "Data Blank", 5)
									.show();
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			} else {
				Toast.makeText(SearchActivity.this, "few pbolm faced", 5)
						.show();
			}

			return null;
		}

		@SuppressLint("ShowToast")
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if (pd.isShowing())
				pd.dismiss();

			customGridAdapter = new CustomGridViewAdapter(SearchActivity.this,
					R.layout.grid_item, categoryproductlist);

			/*
			 * customGridAdapter = new CustomGridViewAdapter(ctx,
			 * R.layout.grid_item, categoryproductlist);
			 */
			Log.e("", "" + customGridAdapter);
			gridView.setAdapter(customGridAdapter);
			if (categoryproductlist.size() == 0) {
				Toast toast = Toast.makeText(SearchActivity.this,
						"No Product Found", Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
			}
			SpannableStringBuilder builder = new SpannableStringBuilder();
			String showtxt = edtSearch.getText().toString();

			builder.append("Search Results For : ").append(showtxt);
			txtheader.setText(builder);

			hideSoftKeyboard();
			llSearch.setVisibility(View.GONE);
			lnrlayoutheader.setVisibility(View.VISIBLE);
		}
	}

}
