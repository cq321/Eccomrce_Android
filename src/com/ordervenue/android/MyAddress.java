package com.ordervenue.android;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.onjection.opencart.Utilz.Prefs;
import com.onjection.opencart.Utilz.ServiceHandler;
import com.onjection.opencart.Utilz.Urls;
import com.onjection.opencart.model.CustmorAddressModel;
import com.onjection.opencart.model.CustomerAddressAdpter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MyAddress extends Activity {
	TextView txtAddNewAddress;
	ListView lvMyAddress;
	private ArrayList<CustmorAddressModel> CustomeraddressList = new ArrayList<CustmorAddressModel>();
	Context ctx;
	ImageView ivBack, ivCart;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.my_address);
		ctx = MyAddress.this;
		txtAddNewAddress = (TextView) findViewById(R.id.txtAddNewAddress);
		lvMyAddress = (ListView) findViewById(R.id.lvMyAddress);
		ivBack = (ImageView) findViewById(R.id.ivBack);
		new GetSearchAddress().execute();
		ivCart = (ImageView) findViewById(R.id.ivCart);
		ivCart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(MyAddress.this, MyCart.class);
				startActivity(i);
			}
		});
		txtAddNewAddress.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent inewAddress = new Intent(MyAddress.this,
						AddNewAddress.class);
				inewAddress.putExtra("Address", "AddNewAddress");
				startActivity(inewAddress);
				finish();
			}
		});
		ivBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	public class GetSearchAddress extends AsyncTask<Void, Void, Void> {

		protected void onPreExecute() {
			super.onPreExecute();

		}

		protected Void doInBackground(Void... arg0) {
			// Creating service handler class instance
			ServiceHandler sh = new ServiceHandler();
			// Making a request to url and getting response
			String Customer_id = Prefs.getPreferences(MyAddress.this,
					"Customer_id");
			String jsonStr = sh.makeServiceCall(Urls.customeraddress
					+ Customer_id, ServiceHandler.GET);

			Log.e("Response: ", "> " + jsonStr);
			if (jsonStr != null) {
				try {
					JSONObject jsonobject = new JSONObject(jsonStr);
					String status = jsonobject.getString("status");
					if (status.equals("success")) {
						JSONArray jsoncustomer_addressarray = jsonobject
								.getJSONArray("customer_address");
						if (jsoncustomer_addressarray.length() > 0) {
							for (int i = 0; i < jsoncustomer_addressarray
									.length(); i++) {
								JSONObject jsoncustmoraddressobject = jsoncustomer_addressarray
										.getJSONObject(i);
								CustmorAddressModel custmorAddressModel = new CustmorAddressModel(
										jsoncustmoraddressobject
												.optString("address_id"),
										jsoncustmoraddressobject
												.optString("customer_id"),
										jsoncustmoraddressobject
												.optString("firstname"),
										jsoncustmoraddressobject
												.optString("lastname"),
										jsoncustmoraddressobject
												.optString("company"),
										jsoncustmoraddressobject
												.optString("address_1"),
										jsoncustmoraddressobject
												.optString("address_2"),
										jsoncustmoraddressobject
												.optString("city"),
										jsoncustmoraddressobject
												.optString("postcode"),
										jsoncustmoraddressobject
												.optString("country_id"),
										jsoncustmoraddressobject
												.optString("zone_id"),
										jsoncustmoraddressobject
												.optString("custom_field"),
										jsoncustmoraddressobject
												.optString("country"),
										jsoncustmoraddressobject
												.optString("state"));
								CustomeraddressList.add(custmorAddressModel);
							}
						} else {
							Toast.makeText(getApplicationContext(),
									"No Address Found", 10).show();
						}
					} else {

					}
				} catch (Exception ex) {

				}
			} else {

			}
			return null;
		}

		@SuppressLint("ShowToast")
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			int id = 1;
			CustomerAddressAdpter customerAddressAdpter = new CustomerAddressAdpter(
					ctx, CustomeraddressList, id);
			lvMyAddress.setAdapter(customerAddressAdpter);

		}
	}
}
