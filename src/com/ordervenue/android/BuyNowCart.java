package com.ordervenue.android;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.onjection.PagerViewImageAdapter.BuyNowProductListAdapter;
import com.onjection.ServerTask.ServerDownload;
import com.onjection.ServerTask.ServerResponse;
import com.onjection.opencart.Utilz.Constant;
import com.onjection.opencart.Utilz.Prefs;
import com.onjection.opencart.model.MyCartListModel;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class BuyNowCart extends Activity implements ServerResponse {
	ImageView ivBack;
	TextView tvHeader, txtTotal;
	Button btnPlaceOrder;
	ListView lvMyCart;
	String jsonstringproduct;
	ProgressDialog pd;
	String total;
	private ArrayList<MyCartListModel> mycartproductlist = new ArrayList<MyCartListModel>();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.buynowcart);
		jsonstringproduct = getIntent().getStringExtra("jsonstringproduct");
		lvMyCart = (ListView) findViewById(R.id.lvMyCart);
		txtTotal = (TextView) findViewById(R.id.txtTotal);
		ivBack = (ImageView) findViewById(R.id.ivBack);
		if (jsonstringproduct != null) {
			pd = ProgressDialog.show(this, "", "fetching Data", false);
			ServerDownload download = new ServerDownload(BuyNowCart.this, "",
					BuyNowCart.this, Constant.tagmycart);
			download.execute(jsonstringproduct);
		}
		ivBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		btnPlaceOrder = (Button) findViewById(R.id.btnPlaceOrder);
		btnPlaceOrder.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mycartproductlist.size() > 0) {
					String Customer_id = Prefs.getPreferences(BuyNowCart.this,
							"Customer_id");
					if (Customer_id != null && !Customer_id.isEmpty()) {
						Prefs.setPreferences(BuyNowCart.this,
								"jsonstringproduct", jsonstringproduct);
						Log.e("jsonstringproduct", jsonstringproduct);
						Intent intent = new Intent(BuyNowCart.this,
								PlaceOrder.class);
						startActivity(intent);
					} else {
						OpenPopUp();
					}
				} else {
					Toast.makeText(BuyNowCart.this, "Please Add Item First", 5)
							.show();
				}
			}

		});
	}

	protected void OpenPopUp() {
		final Dialog dialog = new Dialog(BuyNowCart.this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

		dialog.setContentView(R.layout.alert_dialog);
		dialog.setCancelable(true);
		TextView txtTitle = (TextView) dialog.findViewById(R.id.txtTitle);
		txtTitle.setText("Do you Want Place Order As a guest");

		TextView txtDescription = (TextView) dialog
				.findViewById(R.id.txtDescription);
		txtDescription.setVisibility(View.GONE);

		Button btnOk = (Button) dialog.findViewById(R.id.buttonOk);
		btnOk.setText("Yes");
		btnOk.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				dialog.dismiss();
				String chk = "hello";

				Intent intent = new Intent(BuyNowCart.this,
						PlaceOrderAsGuest.class);
				intent.putExtra("chk", chk);
				Prefs.setPreferences(BuyNowCart.this, "jsonstringproduct",
						jsonstringproduct);
				startActivity(intent);
				/*
				 * Intent ihome = new Intent(mainActivity,
				 * MainSearchHotel.class); startActivity(ihome);
				 */

			}
		});

		Button btnCancel = (Button) dialog.findViewById(R.id.buttonCancel);
		btnCancel.setText("No");
		btnCancel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				dialog.dismiss();
				Prefs.setPreferences(BuyNowCart.this, "jsonstringproduct",
						jsonstringproduct);
				Intent intent = new Intent(BuyNowCart.this, LoginActivity.class);
				startActivity(intent);
			}
		});

		dialog.show();

	}

	@Override
	public void httpResponse(JSONObject jsonObject, String tag, int responceCode) {
		if (pd.isShowing()) {
			pd.dismiss();
		}
		if (jsonObject != null) {
			Log.e("Responce MyCart", "" + jsonObject);

			try {
				String status = jsonObject.getString("status");
				total = jsonObject.getString("total");
				if (status.equals("success")) {
					JSONArray jsoncartArray = jsonObject.getJSONArray("cart");
					if (jsoncartArray.length() > 0) {
						for (int i = 0; i < jsoncartArray.length(); i++) {
							JSONObject jsoncartsubprodcutlist = jsoncartArray
									.getJSONObject(i);
							MyCartListModel myCartListModel = new MyCartListModel(
									jsoncartsubprodcutlist.optString("total"),
									jsoncartsubprodcutlist.optString("model"),
									jsoncartsubprodcutlist.optString("weight"),
									jsoncartsubprodcutlist.optString("reward"),
									jsoncartsubprodcutlist
											.optString("product_id"),
									jsoncartsubprodcutlist.optString("minimum"),
									jsoncartsubprodcutlist.optString("width"),
									jsoncartsubprodcutlist
											.optString("weight_class_id"),
									jsoncartsubprodcutlist.optString("image"),
									jsoncartsubprodcutlist
											.optString("subtract"),
									jsoncartsubprodcutlist
											.optString("shipping"),
									jsoncartsubprodcutlist
											.optString("length_class_id"),
									jsoncartsubprodcutlist.optString("height"),
									jsoncartsubprodcutlist.optString("price"),
									jsoncartsubprodcutlist.optString("stock"),
									jsoncartsubprodcutlist
											.optString("tax_class_id"),
									jsoncartsubprodcutlist.optString("name"),
									jsoncartsubprodcutlist.optString("length"),
									jsoncartsubprodcutlist
											.optString("quantity"),
									jsoncartsubprodcutlist.optString("points"),
									jsoncartsubprodcutlist
											.optString("recurring"));
							mycartproductlist.add(myCartListModel);
						}
					}
				} else {
					Toast.makeText(BuyNowCart.this, "Error", 10).show();
				}
			} catch (Exception ex) {
				ex.printStackTrace();

			}

		} else {
			Toast.makeText(BuyNowCart.this, "Connection Error", 10).show();

		}
		txtTotal.setText(total);
		BuyNowProductListAdapter myCartProductListAdpter = new BuyNowProductListAdapter(
				BuyNowCart.this, mycartproductlist);
		lvMyCart.setAdapter(myCartProductListAdpter);
	}

}
