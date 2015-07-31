package com.ordervenue.android;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.onjection.PagerViewImageAdapter.ConfirmOrderAdpter;
import com.onjection.PagerViewImageAdapter.TotalAdpter;
import com.onjection.ServerTask.ServerDownload;
import com.onjection.ServerTask.ServerResponse;
import com.onjection.opencart.Utilz.Constant;
import com.onjection.opencart.model.OrderInfoProduct;
import com.onjection.opencart.model.PayMentMethodModel;
import com.onjection.opencart.model.TotalModel;
import com.payu.sdk.Constants;
import com.payu.sdk.PayU;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ConfirmOrder extends Activity implements ServerResponse {
	String finaljsondata;
	ArrayList<TotalModel> totalarraylist = new ArrayList<TotalModel>();
	ArrayList<OrderInfoProduct> orderInfoProductlist = new ArrayList<OrderInfoProduct>();
	ListView lvConfirmOrder, listtotal;
	Context ctx;
	Button btnconfirm;
	String pyment_type;
	String order;
	ProgressDialog pd;
	ImageView ivBack, ivCart;
	int seconds = 00;
	String total;
	String surl = "https://dl.dropboxusercontent.com/s/dtnvwz5p4uymjvg/success.html";
	String furl = "https://dl.dropboxusercontent.com/s/z69y7fupciqzr7x/furlWithParams.html";
	Calendar c;
	SimpleDateFormat sdf;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.confrimorder);
		ctx = ConfirmOrder.this;

		finaljsondata = getIntent().getStringExtra("finaljsondata");
		pyment_type = getIntent().getStringExtra("payment");
		Random r = new Random();
		int i1 = r.nextInt(10000 - 1001) + 1001;
		Random r1 = new Random();
		int i11 = r1.nextInt(100000 - 1001) + 1001;
		seconds = i1 + i11;
		order = "" + seconds;
		c = Calendar.getInstance();
		sdf = new SimpleDateFormat("ddmmyyyyhhmmss ");
		order = sdf.format(c.getTime());
		Log.e("confirm order page ", pyment_type + "..." + finaljsondata);
		lvConfirmOrder = (ListView) findViewById(R.id.lvConfirmOrder);
		listtotal = (ListView) findViewById(R.id.listtotal);
		btnconfirm = (Button) findViewById(R.id.btnconfirm);
		ivBack = (ImageView) findViewById(R.id.ivBack);
		ivBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(ctx, PlaceOrder.class);
				startActivity(i);
				finish();
			}
		});
		ivCart = (ImageView) findViewById(R.id.ivCart);
		ivCart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(ConfirmOrder.this, MyCart.class);
				startActivity(i);
			}
		});
		pd = ProgressDialog.show(this, "", "Loading..", false);

		ServerDownload download = new ServerDownload(ConfirmOrder.this, "",
				ConfirmOrder.this, Constant.Confirmorder);
		download.execute(finaljsondata);

		btnconfirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (pyment_type.equals("cod")) {
					pd.setCancelable(false);
					pd.setMessage("please wait..");
					pd.show();

					ServerDownload download = new ServerDownload(
							ConfirmOrder.this, "", ConfirmOrder.this,
							Constant.ConfirmSucess);
					download.execute(postRequest(order, "" + 1));
				} else {
					final HashMap<String, String> params = new HashMap<String, String>();
					double amount = Double.parseDouble(total);
					// for (int i = 0; i < linearLayout.getChildCount() - 2;
					// i++) {
					// LinearLayout param = (LinearLayout) linearLayout
					// .getChildAt(i);
					// if (((TextView) param.getChildAt(0)).getText().toString()
					// .equals("amount")) {
					// amount = Double.parseDouble("" + amount);
					// }
					params.put("amount", total);
					params.put("surl", surl);
					params.put("furl", furl);
					params.put("productinfo", "ordervenue");
					/*Toast.makeText(ConfirmOrder.this,
							"txn" + sdf.format(c.getTime()), 3).show();*/
					params.put("txnid", "txn" + sdf.format(c.getTime()));
					// }
					// String hash = calculateHash(params);
					// params.put(((TextView) param.getChildAt(0)).getText()
					// .toString(), ((EditText) param.getChildAt(1))
					// .getText().toString());
					params.remove("amount");
					// params.put("hash", hash);

					if (!Constants.SDK_HASH_GENERATION) { // cool we gotta fetch
						// hash from the server.

						pd.setCancelable(false);
						pd.setMessage("Calculating Hash. please wait..");
						pd.show();

						final double finalAmount = Double.parseDouble(total);
						new AsyncTask<Void, Void, Void>() {
							@Override
							protected Void doInBackground(Void... voids) {
								try {
									HttpClient httpclient = new DefaultHttpClient();
									HttpPost httppost = new HttpPost(
											Constants.FETCH_DATA_URL);
									List<NameValuePair> postParams = new ArrayList<NameValuePair>(
											5);
									postParams.add(new BasicNameValuePair(
											"command", "mobileHashTestWs"));
									postParams
											.add(new BasicNameValuePair(
													"key",
													(getPackageManager()
															.getApplicationInfo(
																	getPackageName(),
																	PackageManager.GET_META_DATA).metaData)
															.getString("payu_merchant_id")));
									postParams.add(new BasicNameValuePair(
											"var1", params.get("txnid")));
									postParams.add(new BasicNameValuePair(
											"var2", String.valueOf(finalAmount)));
									postParams.add(new BasicNameValuePair(
											"var3", params.get("productinfo")));
									postParams.add(new BasicNameValuePair(
											"var4", params
													.get("user_credentials")));
									postParams.add(new BasicNameValuePair(
											"hash", "helo"));
									httppost.setEntity(new UrlEncodedFormEntity(
											postParams));
									JSONObject response = new JSONObject(
											EntityUtils.toString(httpclient
													.execute(httppost)
													.getEntity()));

									// set the hash values here.

									if (response.has("result")) {
										PayU.merchantCodesHash = response
												.getJSONObject("result")
												.getString("merchantCodesHash");
										PayU.paymentHash = response
												.getJSONObject("result")
												.getString("paymentHash");
										PayU.vasHash = response.getJSONObject(
												"result")
												.getString("mobileSdk");
										PayU.ibiboCodeHash = response
												.getJSONObject("result")
												.getString(
														"detailsForMobileSdk");

										if (response.getJSONObject("result")
												.has("deleteHash")) {
											PayU.deleteCardHash = response
													.getJSONObject("result")
													.getString("deleteHash");
											PayU.getUserCardHash = response
													.getJSONObject("result")
													.getString(
															"getUserCardHash");
											PayU.editUserCardHash = response
													.getJSONObject("result")
													.getString(
															"editUserCardHash");
											PayU.saveUserCardHash = response
													.getJSONObject("result")
													.getString(
															"saveUserCardHash");
										}

									}
									pd.dismiss();

									// PayU.getInstance(MainActivity.this).startPaymentProcess(finalAmount,
									// params);
									PayU.getInstance(ConfirmOrder.this)
											.startPaymentProcess(
													finalAmount,
													params,
													new PayU.PaymentMode[] {
															PayU.PaymentMode.CC,
															PayU.PaymentMode.NB });

								} catch (UnsupportedEncodingException e) {
									e.printStackTrace();
									/*
									 * if(pd.isShowing()) pd.dismiss();
									 */
									Toast.makeText(ConfirmOrder.this,
											e.getMessage(), Toast.LENGTH_LONG)
											.show();
								} catch (ClientProtocolException e) {
									e.printStackTrace();
									/*
									 * if(pd.isShowing()) pd.dismiss();
									 */
									Toast.makeText(ConfirmOrder.this,
											e.getMessage(), Toast.LENGTH_LONG)
											.show();
								} catch (JSONException e) {
									if (pd.isShowing())
										pd.dismiss();
									e.printStackTrace();
								} catch (IOException e) {
									e.printStackTrace();
									/*
									 * if(pd.isShowing()) pd.dismiss();
									 */
									Toast.makeText(ConfirmOrder.this,
											e.getMessage(), Toast.LENGTH_LONG)
											.show();
								} catch (PackageManager.NameNotFoundException e) {
									e.printStackTrace();
									/*
									 * if(pd.isShowing()) pd.dismiss();
									 */
									Toast.makeText(ConfirmOrder.this,
											e.getMessage(), Toast.LENGTH_LONG)
											.show();
								}
								return null;
							}
						}.execute();
					} else {
						PayU.getInstance(ConfirmOrder.this)
								.startPaymentProcess(amount, params);
						// PayU.getInstance(MainActivity.this).startPaymentProcess(amount,
						// params, new PayU.PaymentMode[]{PayU.PaymentMode.CC,
						// PayU.PaymentMode.NB});
					}

				}
			}
		});

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent i = new Intent(ctx, PlaceOrder.class);
		startActivity(i);
		finish();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		Log.e("On Activity res", "Called");
		if (data != null) {

			if (requestCode == PayU.RESULT) {
				Log.e("Request code", "" + requestCode);
				if (resultCode == RESULT_OK) {

					// success
					if (data != null)
						Toast.makeText(
								this,
								"Payment Successfull  "
										+ data.getStringExtra("result"),
								Toast.LENGTH_LONG).show();
					pd.setCancelable(false);
					pd.setMessage("please wait..");
					pd.show();
					Log.e("payment Sucessfull",
							"Success:  " + data.getStringExtra("result"));
					ServerDownload download = new ServerDownload(
							ConfirmOrder.this, "", ConfirmOrder.this,
							Constant.ConfirmSucess);
					download.execute(postRequest(order, "" + 1));

				}
				if (resultCode == RESULT_CANCELED) {
					// failed

					if (data != null)
						Log.e("payment Fail",
								"Success:  " + data.getStringExtra("result"));
					Toast.makeText(this,
							"Failed  " + data.getStringExtra("result"),
							Toast.LENGTH_LONG).show();
					pd.setCancelable(false);
					pd.setMessage("please wait..");
					pd.show();
					ServerDownload download = new ServerDownload(
							ConfirmOrder.this, "", ConfirmOrder.this,
							Constant.ConfirmSucess);
					download.execute(postRequest(order, "" + 0));

				}
			}
		}
	}

	@SuppressWarnings("null")
	@Override
	public void httpResponse(JSONObject jsonObject, String tag, int responceCode) {

		if (jsonObject != null) {
			if (tag.equals(Constant.ConfirmSucess)) {

				if (pd.isShowing()) {
					pd.dismiss();
				}
				Log.e("Responce sucess order", "" + jsonObject.toString());

				String status = "";
				try {
					status = jsonObject.getString("status");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (status.equals("success")) {
					Intent intent = new Intent(ConfirmOrder.this,
							ThankYou.class);
					Bundle b = new Bundle();
					b.putString("orderId", order);
					b.putString("amount", total);
					b.putString("txnid", "txn" + sdf.format(c.getTime()));
					intent.putExtras(b);
					startActivity(intent);
					new DBController(ConfirmOrder.this).deleteAll();
				} else if (status.equals("failed")) {
					Toast.makeText(ctx, "Payment Failed", 3).show();
				}

			} else if (tag.equals(Constant.Confirmorder)) {
				pd.dismiss();
				Log.e("Responce Placeorder", "" + jsonObject);
				try {
					String status = jsonObject.getString("status");
					if (status.equals("success")) {
						JSONObject order_info = jsonObject
								.getJSONObject("order_info");
						order = order_info.optString("order_id");
						total = order_info.optString("total");
						// total = ""+1;
						JSONArray jsonarrayproducts = order_info
								.getJSONArray("products");
						JSONArray jsonarraytotals = order_info
								.getJSONArray("totals");
						if (jsonarrayproducts.length() > 0) {
							for (int i = 0; i < jsonarrayproducts.length(); i++) {
								JSONObject jsonobjectofproducts = jsonarrayproducts
										.getJSONObject(i);
								OrderInfoProduct orderInfoProduct = new OrderInfoProduct(
										jsonobjectofproducts.optString("total"),
										jsonobjectofproducts
												.optString("unit_price"),
										jsonobjectofproducts
												.optString("quantity"),
										jsonobjectofproducts.optString("name"));
								orderInfoProductlist.add(orderInfoProduct);
							}
						} else {

						}
						if (jsonarraytotals.length() > 0) {
							for (int i = 0; i < jsonarraytotals.length(); i++) {
								JSONObject jsonobjecttotaloftotal = jsonarraytotals
										.getJSONObject(i);

								TotalModel totalModel = new TotalModel(
										jsonobjecttotaloftotal
												.optString("value"),
										jsonobjecttotaloftotal
												.optString("title"));
								totalarraylist.add(totalModel);

							}
						} else {

						}

					}
				} catch (Exception ex) {
					ex.printStackTrace();

				}

			}
			ConfirmOrderAdpter confrimorderadpter = new ConfirmOrderAdpter(ctx,
					orderInfoProductlist);
			lvConfirmOrder.setAdapter(confrimorderadpter);
			TotalAdpter totalAdpter = new TotalAdpter(ctx, totalarraylist);
			listtotal.setAdapter(totalAdpter);
		}
	}

	String postRequest(String ordrId, String sucess) {

		String request = "{\"order_id\":\"" + ordrId
				+ "\",\"order_status_id\":\"" + sucess + "\"}";
		Log.e("post request", request);
		return request;
	}
}
