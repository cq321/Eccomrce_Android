package com.ordervenue.android;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.onjection.PagerViewImageAdapter.CustomGridViewAdapter;
import com.onjection.opencart.Utilz.Constant;
import com.onjection.opencart.Utilz.ServiceHandler;
import com.onjection.opencart.Utilz.Urls;
import com.onjection.opencart.model.CategoryProduct;
import com.onjection.opencart.model.ProductOptionModel;
import com.onjection.opencart.model.ProductOptionValue;
import com.ordervenue.android.Firstfragment.Getproductcategory;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint({ "InflateParams", "ShowToast" })
public class NotificationCatogery extends Activity implements OnScrollListener,
		OnClickListener {
	Context ctx;
	ProgressDialog pd;
	String ctaegory_id, headerTitel;
	ArrayList<String> productimages = new ArrayList<String>();
	ProductOptionModel productOptionModel;
	private ArrayList<CategoryProduct> categoryproductlist = new ArrayList<CategoryProduct>();
	CategoryProduct categoryProduct;
	GridView gridView;
	CustomGridViewAdapter customGridAdapter;
	int page = 1;
	Button loadMore;
	ImageView ivBack;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notificationffer);
		ctx = NotificationCatogery.this;
		ctaegory_id = getIntent().getExtras().getString("ctaegory_id");
		// Toast.makeText(NotificationCatogery.this, "" + ctaegory_id,
		// 10).show();
		((TextView) findViewById(R.id.tvHeadershow)).setText("Offers");
		ivBack = (ImageView) findViewById(R.id.ivBack);
		loadMore = (Button) findViewById(R.id.loadmore);
		loadMore.setOnClickListener(this);
		// Toast.makeText(ctx, ctaegory_id, 5).show();
		gridView = (GridView) findViewById(R.id.gridview);
		gridView.setOnScrollListener(this);

		new Getproductcategory().execute();
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long arg3) {
				String product_id = categoryproductlist.get(position)
						.getProduct_id();
				Intent inetntproductdetails = new Intent(ctx,
						ProductDetails.class);
				inetntproductdetails.putExtra("Key_Product_id", product_id);
				startActivity(inetntproductdetails);
			}
		});
		ivBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(ctx, NevigationDrawer.class);
				startActivity(i);
				finish();
			}
		});
	}

	public class Getproductcategory extends AsyncTask<Void, Void, Void> {

		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			pd = new ProgressDialog(ctx);
			pd.setMessage("Please wait...");
			pd.setCancelable(false);
			pd.show();

		}

		@SuppressLint("ShowToast")
		protected Void doInBackground(Void... arg0) {
			ServiceHandler sh = new ServiceHandler();
			// Making a request to url and getting response
			String jsonStr = sh.makeServiceCall(Urls.GetProductByCategorey
					+ ctaegory_id + "page=" + page + "&limit=10",
					ServiceHandler.GET);
			// &category_id=
			Log.e("Response: ", "> " + jsonStr);
			if (jsonStr != null) {
				try {
					JSONObject jsonobject = new JSONObject(jsonStr);
					String status = jsonobject.getString("status");
					if (status.equals("success")) {
						JSONArray jsonarray = jsonobject
								.getJSONArray("products");
						for (int i = 0; i < jsonarray.length(); i++) {
							JSONObject jsonproductobject = jsonarray
									.getJSONObject(i);
							JSONArray jsonproductimages = jsonproductobject
									.getJSONArray("images");

							if (jsonproductimages.length() > 0) {
								for (int j = 0; j < jsonproductimages.length(); j++) {
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

							// OptionArray Check if length is gretaer then zero
							// then
							// if block execute
							if (jsonarrayoptions.length() > 0) {
								for (int k = 0; k < jsonarrayoptions.length(); k++) {
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
									productOptionList.add(productOptionModel);

								}
							}
							categoryProduct = new CategoryProduct(
									jsonproductobject.optString("product_id"),
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
									jsonproductobject.optString("tax_class_id"),
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
									jsonproductobject.optString("sort_order"),
									jsonproductobject.optString("status"),
									jsonproductobject.optString("viewed"),
									jsonproductobject.optString("date_added"),
									jsonproductobject
											.optString("date_modified"),
									jsonproductobject.optString("language_id"),
									jsonproductobject.optString("name"),
									jsonproductobject.optString("description"),
									jsonproductobject.optString("tag"),
									jsonproductobject.optString("store_id"),
									jsonproductobject.optString("manufacturer"),
									jsonproductobject.optString("discount"),
									jsonproductobject.optString("special"),
									jsonproductobject.optString("reward"),
									jsonproductobject.optString("stock_status"),
									jsonproductobject.optString("rating"),
									jsonproductobject.optString("reviews"),
									productOptionList);
							categoryproductlist.add(categoryProduct);
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			} else {
				Toast.makeText(ctx, "few pbolm faced", 5).show();
			}

			return null;
		}

		@SuppressLint("ShowToast")
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if (pd.isShowing())
				pd.dismiss();
			if (categoryproductlist.size() > 0) {
				customGridAdapter = new CustomGridViewAdapter(ctx,
						R.layout.grid_item, categoryproductlist);
			} else {
				Toast.makeText(ctx, "Sorry item not found", 3).show();
				Constant.showAlertActivityWithTitle("Category",
						"Item Not Found", ctx);

			}

			Log.e("", "" + customGridAdapter);
			gridView.setAdapter(customGridAdapter);
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub

		Log.e("visibleItemCount",
				" " + visibleItemCount + " "
						+ gridView.getLastVisiblePosition());
		if (gridView.getLastVisiblePosition() + 1 == 10) {
			loadMore.setVisibility(View.VISIBLE); // Load More Button
			// page = page + 1;
			// Log.e("Page", "" + page);
			// new Getproductcategory().execute();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.loadmore:
			page = page + 1;
			Log.e("Page", "" + page);
			new Getproductcategory().execute();
			break;

		default:
			break;
		}

	}
}