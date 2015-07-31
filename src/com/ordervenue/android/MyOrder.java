package com.ordervenue.android;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.onjection.PagerViewImageAdapter.MyOrderAdpter;
import com.onjection.opencart.Utilz.Prefs;
import com.onjection.opencart.Utilz.ServiceHandler;
import com.onjection.opencart.Utilz.Urls;
import com.onjection.opencart.model.MyOrderModel;
import com.onjection.opencart.model.MyOrderProductDetails;
import com.onjection.opencart.model.MyOrderTotalsModel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MyOrder extends Activity {
	ProgressDialog pd;
	ImageView ivBack;
	public static ArrayList<MyOrderModel> myorderlist = new ArrayList<MyOrderModel>();
	ListView lvmyorder;
	TextView tvHeadeshow;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.myorder);
		lvmyorder = (ListView) findViewById(R.id.lvmyorder);
		ivBack = (ImageView) findViewById(R.id.ivBack);
		tvHeadeshow = (TextView) findViewById(R.id.tvHeadeshow);
		tvHeadeshow.setText("MyOrders");
		new Myorderdetails().execute();
		ivBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
				myorderlist.clear();
			}
		});
		lvmyorder.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i = new Intent(MyOrder.this, Myorderid.class);
				i.putExtra("position", position);
				startActivity(i);
			}
		});
	}

	public class Myorderdetails extends AsyncTask<Void, Void, Void> {

		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			pd = new ProgressDialog(MyOrder.this);
			pd.setTitle("Searching");
			pd.setMessage("Please wait...");
			pd.setCancelable(false);
			pd.show();

		}

		@SuppressLint("ShowToast")
		protected Void doInBackground(Void... arg0) {
			ServiceHandler sh = new ServiceHandler();
			// Making a request to url and getting response
			String jsonStr = sh
					.makeServiceCall(
							Urls.myorderapi
									+ Prefs.getPreferences(MyOrder.this,
											"Customer_id"), ServiceHandler.GET);

			Log.e("Response: ", "> " + jsonStr);
			String status = "";
			JSONObject jsonObject;
			try {
				jsonObject = new JSONObject(jsonStr);
				status = jsonObject.getString("status");
				if (status.equals("success")) {
					JSONArray jsonarrayofOrder = jsonObject
							.getJSONArray("orders");
					if (jsonarrayofOrder.length() > 0) {
						for (int i = 0; i < jsonarrayofOrder.length(); i++) {
							JSONObject jsonobjectofsubpart = jsonarrayofOrder
									.getJSONObject(i);

							JSONArray jsonorderproductarray = jsonobjectofsubpart
									.getJSONArray("products");
							ArrayList<MyOrderProductDetails> myorderproductlist = new ArrayList<MyOrderProductDetails>();
							ArrayList<MyOrderTotalsModel> myordertotallist = new ArrayList<MyOrderTotalsModel>();
							if (jsonorderproductarray.length() > 0) {
								for (int j = 0; j < jsonorderproductarray
										.length(); j++) {
									JSONObject jsonobjectproductdetails = jsonorderproductarray
											.getJSONObject(j);
									MyOrderProductDetails myOrderProductDetails = new MyOrderProductDetails(
											jsonobjectproductdetails
													.optString("order_product_id"),
											jsonobjectproductdetails
													.optString("name"),
											jsonobjectproductdetails
													.optString("quantity"),
											jsonobjectproductdetails
													.optString("price"),
											jsonobjectproductdetails
													.optString("total"));
									myorderproductlist
											.add(myOrderProductDetails);
								}

							} else {
								Toast.makeText(getApplicationContext(),
										"Product not found", 10).show();

							}
							JSONArray jsonproductorder_totals = jsonobjectofsubpart
									.getJSONArray("order_totals");
							if (jsonproductorder_totals.length() > 0) {
								for (int k = 0; k < jsonproductorder_totals
										.length(); k++) {
									JSONObject jsonobjectoftoatls = jsonproductorder_totals
											.getJSONObject(k);
									MyOrderTotalsModel myOrderTotalsModel = new MyOrderTotalsModel(
											jsonobjectoftoatls
													.optString("text"),
											jsonobjectoftoatls
													.optString("title"));
									myordertotallist.add(myOrderTotalsModel);
								}

							} else {
								Toast.makeText(getApplicationContext(),
										"Product not found", 10).show();

							}
							MyOrderModel myOrderModel = new MyOrderModel(
									jsonobjectofsubpart.getString("order_id"),
									jsonobjectofsubpart.optString("total"),
									jsonobjectofsubpart.optString("date_added"),
									myorderproductlist, myordertotallist);
							myorderlist.add(myOrderModel);

						}

					} else {

					}

				}
			} catch (JSONException e1) {
				e1.printStackTrace();
			}

			return null;
		}

		@SuppressLint("ShowToast")
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if (pd.isShowing())
				pd.dismiss();
			if (myorderlist.size() == 0) {
				Toast.makeText(getApplicationContext(), "MyORDER not found", 10)
						.show();
			}

			MyOrderAdpter myOrderAdpter = new MyOrderAdpter(MyOrder.this,
					myorderlist);

			lvmyorder.setAdapter(myOrderAdpter);
		}
	}
}
