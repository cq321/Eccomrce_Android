package com.ordervenue.android;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.onjection.PagerViewImageAdapter.MyCartProductListAdpter;
import com.onjection.ServerTask.ServerDownload;
import com.onjection.ServerTask.ServerResponse;
import com.onjection.opencart.Utilz.Constant;
import com.onjection.opencart.Utilz.Prefs;
import com.onjection.opencart.Utilz.Urls;
import com.onjection.opencart.model.MyCartListModel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MyCart extends Activity implements ServerResponse {
	ImageView ivBack;
	TextView tvHeader, txtTotal;
	Button btnPlaceOrder;
	DBController controller = new DBController(this);
	JSONObject productlist;
	int language_id = 1;
	ProgressDialog pd;
	ListView lvMyCart;
	String total;
	String jsonstringproduct = null;
	static MyCart activityA;
	private ArrayList<MyCartListModel> mycartproductlist = new ArrayList<MyCartListModel>();

	@SuppressLint("ShowToast")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.mycart);
		activityA = this;
		lvMyCart = (ListView) findViewById(R.id.lvMyCart);
		txtTotal = (TextView) findViewById(R.id.txtTotal);
		ivBack = (ImageView) findViewById(R.id.ivBack);
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
					String Customer_id = Prefs.getPreferences(MyCart.this,
							"Customer_id");
					if (Customer_id != null && !Customer_id.isEmpty()) {
						Prefs.setPreferences(MyCart.this, "jsonstringproduct",
								jsonstringproduct);
						Log.e("jsonstringproduct", jsonstringproduct);
						Intent intent = new Intent(MyCart.this,
								PlaceOrder.class);
						startActivity(intent);
					} else {
						OpenPopUp();
					}
				} else {
					Toast.makeText(MyCart.this, "Please Add Item First", 5)
							.show();
				}
			}
		});
		Databasefetchdata();
		pd = ProgressDialog.show(this, "", "MyCart", false);
		ServerDownload download = new ServerDownload(MyCart.this, "",
				MyCart.this, Constant.tagmycart);
		download.execute(jsonstringproduct);
		lvMyCart.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String product_id = mycartproductlist.get(position)
						.getProduct_id();
				Intent inetntproductdetails = new Intent(MyCart.this,
						ProductDetails.class);
				inetntproductdetails.putExtra("Key_Product_id", product_id);
				startActivity(inetntproductdetails);
				finish();
			}
		});
	}

	public void Databasefetchdata() {
		jsonstringproduct = null;

		ArrayList<HashMap<String, String>> arraylist = controller
				.getAllProdcut();
		JSONArray jsonArray = new JSONArray();

		for (int i = 0; i < arraylist.size(); i++) {
			String ProdcutID = arraylist.get(i).get("ProdcutID");
			String quantity = arraylist.get(i).get("quantity");
			String radioButtonPID = arraylist.get(i).get(
					"radioButtonParrenmtId");
			String radioButtonSelectedId = arraylist.get(i).get(
					"radioButtonSelectedId");
			String chkBoxParrentId = arraylist.get(i).get("chkBoxParrentId");
			String chkBoxSelectedID = arraylist.get(i).get("chkBoxSelectedID");
			Log.e("ProdcutID", ProdcutID);
			Log.e("quantity", quantity);
			Log.e("radioButtonPID", radioButtonPID);
			Log.e("radioButtonSelectedId", radioButtonSelectedId);
			Log.e("chkBoxSelectedID", chkBoxSelectedID);
			Log.e("chkBoxParrentId", chkBoxParrentId);
			JSONArray jsonArrayOption = new JSONArray();
			@SuppressWarnings("unused")
			JSONArray jsonArrayChkBoxId = new JSONArray();
			JSONObject jsonObjectOption = new JSONObject();
			try {
				jsonObjectOption.put(radioButtonPID, radioButtonSelectedId);
				// jsonObjectOption.put(chkBoxParrentId,convertStringToArray(chkBoxSelectedID)
				// );
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			jsonArrayOption.put(jsonObjectOption);
			productlist = new JSONObject();
			try {
				productlist.put("product_id", ProdcutID);
				productlist.put("quantity", quantity);
				productlist.put("option", jsonArrayOption);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jsonArray.put(productlist);
		}
		JSONObject products = new JSONObject();
		try {
			products.put("products", jsonArray);
			products.put("language_id", language_id);
			products.put("tokam", Urls.tokan);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		jsonstringproduct = products.toString();
		Log.e("jsonStr", jsonstringproduct);

		/*
		 * ivBack = (ImageView) findViewById(R.id.ivBack); tvHeader = (TextView)
		 * findViewById(R.id.tvHeader); tvHeader.setText("My Cart");
		 * ivBack.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { finish(); } });
		 */

	}

	protected void OpenPopUp() {

		final Dialog dialog = new Dialog(MyCart.this);
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

				Intent intent = new Intent(MyCart.this, PlaceOrderAsGuest.class);
				intent.putExtra("chk", chk);
				Prefs.setPreferences(MyCart.this, "jsonstringproduct",
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
				Prefs.setPreferences(MyCart.this, "jsonstringproduct",
						jsonstringproduct);
				Intent intent = new Intent(MyCart.this, LoginActivity.class);
				startActivity(intent);
			}
		});

		dialog.show();

	}

	@SuppressLint("ShowToast")
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
					Toast.makeText(MyCart.this, "Error", 10).show();
				}
			} catch (Exception ex) {
				ex.printStackTrace();

			}

		} else {
			Toast.makeText(MyCart.this, "Connection Error", 10).show();

		}
		txtTotal.setText(total);
		MyCartProductListAdpter myCartProductListAdpter = new MyCartProductListAdpter(
				MyCart.this, mycartproductlist);
		lvMyCart.setAdapter(myCartProductListAdpter);
	}

	public static MyCart getInstance() {
		return activityA;
	}
}