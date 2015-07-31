package com.ordervenue.android;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.onjection.ServerTask.ServerDownload;
import com.onjection.ServerTask.ServerResponse;
import com.onjection.opencart.Utilz.Constant;
import com.onjection.opencart.Utilz.Prefs;
import com.onjection.opencart.Utilz.Urls;
import com.onjection.opencart.model.PayMentMethodModel;
import com.onjection.opencart.model.Quote;
import com.onjection.opencart.model.ShipingMethodModel;
import com.ordervenue.android.PlaceOrder.BillingAddressAdpter;
import com.ordervenue.android.PlaceOrder.ShipingAddressAdpter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PlaceOrderAsGuest extends Activity implements ServerResponse {
	LinearLayout addressdetailsOfguest;
	ImageView ivBack, ivCart;
	Button btnProceedToPay, btnChangeAddress, btnnext;
	EditText edtEmail;
	int chkboxpostionid;
	TextView txtName, txtAdd, txtLandMarkzz, txtCity_Pin, txtState, txtMob;
	ListView lvofdilogshipping, lvdialogbillingaddress;
	LinearLayout linearlayoutaddress;
	String edtPhone, edtState, edtCity, edtPincode, edtLandMark, edtComp,
			edtAddress2, edtAddress1, edtL_Name, edtF_Name;
	String chk, jsonstringproduct, jsonpoststringofrequest;
	JSONObject REQUEST;
	CheckBox chkboxid, chkboxidbilling;
	ProgressDialog pd;
	ArrayList<PayMentMethodModel> paymentmethodlist = new ArrayList<PayMentMethodModel>();
	ArrayList<ShipingMethodModel> shipingmethodlist = new ArrayList<ShipingMethodModel>();
	Dialog dialog, dialog1;
	ArrayList<Quote> quotelist = new ArrayList<Quote>();

	@SuppressLint("ShowToast")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.place_order_as_guest);
		chk = getIntent().getStringExtra("chk");
		btnnext = (Button) findViewById(R.id.btnnext);
		ivBack = (ImageView) findViewById(R.id.ivBack);
		btnProceedToPay = (Button) findViewById(R.id.btnProceedToPay);
		edtEmail = (EditText) findViewById(R.id.edtEmail);
		txtName = (TextView) findViewById(R.id.txtName);
		txtAdd = (TextView) findViewById(R.id.txtAdd);
		txtCity_Pin = (TextView) findViewById(R.id.txtCity_Pin);
		txtState = (TextView) findViewById(R.id.txtState);
		txtMob = (TextView) findViewById(R.id.txtMob);
		ivCart = (ImageView) findViewById(R.id.ivCart);
		ivCart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(PlaceOrderAsGuest.this, MyCart.class);
				startActivity(i);
			}
		});
		btnChangeAddress = (Button) findViewById(R.id.btnChangeAddress);
		if (!chk.equals("hello")) {
			edtPhone = getIntent().getStringExtra("edtPhone");
			edtState = getIntent().getStringExtra("edtState");
			edtCity = getIntent().getStringExtra("edtCity");
			edtPincode = getIntent().getStringExtra("edtPincode");
			edtLandMark = getIntent().getStringExtra("edtLandMark");
			edtComp = getIntent().getStringExtra("edtComp");
			edtAddress2 = getIntent().getStringExtra("edtAddress2");
			edtAddress1 = getIntent().getStringExtra("edtAddress1");
			edtL_Name = getIntent().getStringExtra("edtL_Name");
			edtF_Name = getIntent().getStringExtra("edtF_Name");
			try {
				setAddress();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		btnProceedToPay.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (edtEmail.getText().toString().length() <= 0) {
					Toast.makeText(PlaceOrderAsGuest.this,
							"Provide email address.", Toast.LENGTH_LONG).show();
				} else if (!Constant.isValidEmail1(edtEmail.getText()
						.toString())) {
					Toast.makeText(PlaceOrderAsGuest.this,
							"Provide valid email.", Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(PlaceOrderAsGuest.this,
							"Please Fill your Address", 10).show();
				}
			}
		});
		ivBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		btnChangeAddress.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent iaddress = new Intent(PlaceOrderAsGuest.this,
						AddAddressOfGuest.class);
				startActivity(iaddress);
			}
		});
		btnnext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!chk.equals("hello")) {
					if (REQUEST != null) {
						pd = ProgressDialog.show(PlaceOrderAsGuest.this, "",
								"Shipping Address", false);
						ServerDownload download = new ServerDownload(
								PlaceOrderAsGuest.this, "",
								PlaceOrderAsGuest.this, Constant.ShipingString);
						download.execute(jsonpoststringofrequest);
					}
				} else {
					Toast.makeText(PlaceOrderAsGuest.this,
							"Please fill Address Details", 10).show();
				}
			}
		});

	}

	private void setAddress() throws JSONException {
		StringBuffer name = new StringBuffer();
		name.append(edtF_Name).append(" ").append(edtL_Name);
		txtName.setText(name);
		name.setLength(0);
		name.append(edtAddress1).append(",").append(edtAddress2);
		txtAdd.setText(name);
		name.setLength(0);
		name.append(edtLandMark);
		// txtLandMark.setText(name);
		name.setLength(0);
		name.append(edtCity).append("-").append(edtState).append(",")
				.append(edtPincode);
		txtCity_Pin.setText(name);
		name.setLength(0);
		name.append(edtState);
		txtState.setText(name);
		name.setLength(0);
		name.append("Ph:").append(edtPhone);
		txtMob.setText(name);
		name.setLength(0);
		REQUEST = new JSONObject();

		JSONObject jsonaddress = new JSONObject();
		jsonaddress.put("payment_firstname", edtF_Name);
		jsonaddress.put("payment_lastname", edtL_Name);
		jsonaddress.put("payment_company", "Onjection");
		jsonaddress.put("payment_address_1", edtAddress1);
		jsonaddress.put("payment_address_2", edtAddress2);
		jsonaddress.put("payment_city", edtCity);
		jsonaddress.put("payment_postcode", edtPincode);
		jsonaddress.put("payment_country", "India");
		jsonaddress.put("payment_country_id", "99");
		jsonaddress.put("payment_zone", "Haryana");
		jsonaddress.put("payment_zone_id", "1486");
		jsonaddress.put("payment_telephone", edtPhone);
		jsonaddress.put("payment_email", edtEmail.getText().toString());

		JSONObject Jsonshipping = new JSONObject();
		Jsonshipping.put("shipping_firstname", edtF_Name);
		Jsonshipping.put("shipping_lastname", edtL_Name);
		Jsonshipping.put("shipping_company", "Onjection");
		Jsonshipping.put("shipping_address_1", edtAddress1);
		Jsonshipping.put("shipping_address_2", edtAddress2);
		Jsonshipping.put("shipping_city", edtCity);
		Jsonshipping.put("shipping_postcode", edtPincode);
		Jsonshipping.put("shipping_country", "India");
		Jsonshipping.put("shipping_country_id", "99");
		Jsonshipping.put("shipping_zone", "Haryana");
		Jsonshipping.put("shipping_zone_id", "1486");
		Jsonshipping.put("shipping_telephone", edtPhone);
		Jsonshipping.put("shipping_email", edtEmail.getText().toString());

		jsonstringproduct = Prefs.getPreferences(PlaceOrderAsGuest.this,
				"jsonstringproduct");
		JSONObject jsonproduct = new JSONObject(jsonstringproduct);
		try {
			JSONArray jsonarray = jsonproduct.getJSONArray("products");
			REQUEST.put("products", jsonarray);
			REQUEST.put("language_id", jsonproduct.optString("language_id"));
			REQUEST.put("tokan", Urls.tokan);
			REQUEST.put("payment_address", jsonaddress);
			REQUEST.put("shipping_address", Jsonshipping);
			jsonpoststringofrequest = REQUEST.toString();
			Log.d("REQUEST", REQUEST.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	@SuppressLint("ShowToast")
	@Override
	public void httpResponse(JSONObject jsonObject, String tag, int responceCode) {
		if (pd.isShowing()) {
			pd.dismiss();
		}
		if (tag.equals(Constant.PlaceOrderString)) {
			if (jsonObject != null) {
				Log.e("Responce Placeorder", "" + jsonObject);

				try {
					String status = jsonObject.getString("status");
					if (status.equals("success")) {
						JSONArray jsonpayment_methodArray = jsonObject
								.getJSONArray("payment_method");
						if (jsonpayment_methodArray.length() > 0) {
							for (int i = 0; i < jsonpayment_methodArray
									.length(); i++) {
								JSONObject jsonobject = jsonpayment_methodArray
										.getJSONObject(i);
								PayMentMethodModel payMentMethodModel = new PayMentMethodModel(
										jsonobject.optString("title"),
										jsonobject.optString("sort_order"),
										jsonobject.optString("terms"),
										jsonobject.optString("code"));
								paymentmethodlist.add(payMentMethodModel);
								if (dialog1 == null) {
									dialog1 = new Dialog(PlaceOrderAsGuest.this);
									dialog1.setCancelable(true);
									dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
									// dialog1.setTitle("Select Billing Method");
									try {
										dialog1.setContentView(R.layout.dialogbillingaddress);
									} catch (Exception e) {
										e.printStackTrace();
									}
									ImageView icon_close = (ImageView) dialog1
											.findViewById(R.id.icon_close);
									lvdialogbillingaddress = (ListView) dialog1
											.findViewById(R.id.lvdialogbillingaddress);
									;
									Button btnconfirm = (Button) dialog1
											.findViewById(R.id.btnconfirm);
									btnconfirm.setVisibility(View.GONE);
									BillingAddressAdpter billingAddressAdpter = new BillingAddressAdpter(
											PlaceOrderAsGuest.this,
											paymentmethodlist);
									lvdialogbillingaddress
											.setAdapter(billingAddressAdpter);
									icon_close
											.setOnClickListener(new OnClickListener() {

												@Override
												public void onClick(View v) {
													dialog.dismiss();
												}
											});
									lvdialogbillingaddress
											.setOnItemClickListener(new OnItemClickListener() {

												@Override
												public void onItemClick(
														AdapterView<?> parent,
														View view,
														int position, long id) {

													String title = paymentmethodlist
															.get(position)
															.getTitle();
													String code = paymentmethodlist
															.get(position)
															.getCode();
													String terms = paymentmethodlist
															.get(position)
															.getSort_order();
													String sort_order = paymentmethodlist
															.get(position)
															.getTerms();

													JSONObject payment_method = new JSONObject();
													try {
														payment_method.put(
																"code", code);
														payment_method.put(
																"terms", terms);
														payment_method.put(
																"sort_order",
																sort_order);
														payment_method.put(
																"title", title);
														REQUEST.put(
																"payment_method",
																payment_method);
														Log.e("REQUEST",
																REQUEST + "");
													} catch (JSONException e) {
														e.printStackTrace();
													}
													String finaljsondata = REQUEST
															.toString();
													Intent i = new Intent(
															PlaceOrderAsGuest.this,
															ConfirmOrder.class);
													i.putExtra("finaljsondata",
															finaljsondata);
													i.putExtra("payment", code);
													startActivity(i);
													PlaceOrderAsGuest.this
															.finish();
													dialog1.dismiss();

												}
											});
									/*
									 * btnconfirm .setOnClickListener(new
									 * OnClickListener() {
									 * 
									 * @SuppressLint("ShowToast")
									 * 
									 * @Override public void onClick(View v) {
									 * if (chkboxidbilling .isChecked()) {
									 * 
									 * 
									 * } else { Toast.makeText( PlaceOrder.this,
									 * "Please select CheckBox", 10).show(); } }
									 * });
									 */
								}
								dialog1.show();
								// PlaceOrderAsGuest.this.finish();
							}
						} else {
							Toast.makeText(PlaceOrderAsGuest.this,
									"Paymentmethod have 0 size Array", 10)
									.show();
						}
					} else {
						Toast.makeText(PlaceOrderAsGuest.this, "Status failed",
								10).show();
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				// {"payment_method":[{"title":"Cod","sort_order":"5","terms":"","code":"cod"},{"title":"Free Checkout","sort_order":"1","terms":"","code":"free_checkout"}],"status":"success"}
			}
		} else if (tag.equals(Constant.ShipingString)) {
			if (jsonObject != null) {
				Log.e("Responce Placeorder", "" + jsonObject);
				try {
					String status = jsonObject.getString("status");
					if (status.equals("success")) {
						JSONArray jsonshipping_methodArray = jsonObject
								.getJSONArray("shipping_method");
						if (jsonshipping_methodArray.length() > 0) {
							for (int i = 0; i < jsonshipping_methodArray
									.length(); i++) {
								JSONObject jsonobject = jsonshipping_methodArray
										.getJSONObject(i);
								JSONArray jsonarrayQoute = jsonobject
										.getJSONArray("quote");
								for (int j = 0; j < jsonarrayQoute.length(); j++) {
									JSONObject jsonobjectquote = jsonarrayQoute
											.getJSONObject(j);
									Quote quote = new Quote(
											jsonobjectquote.optString("text"),
											jsonobjectquote.optString("title"),
											jsonobjectquote.optString("code"),
											jsonobjectquote
													.optString("tax_class_id"),
											jsonobjectquote.optString("cost"));
									quotelist.add(quote);
								}
								ShipingMethodModel shipingMethodModel = new ShipingMethodModel(
										jsonobject.optString("title"),
										jsonobject.optString("sort_order"),
										jsonobject.optString("code"),
										jsonobject.optString("error"),
										quotelist);
								shipingmethodlist.add(shipingMethodModel);
								if (dialog == null) {
									dialog = new Dialog(PlaceOrderAsGuest.this);
									dialog.setCancelable(true);
									dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
									try {
										dialog.setContentView(R.layout.custom_numberlocation);
									} catch (Exception e) {
										e.printStackTrace();
									}
									lvofdilogshipping = (ListView) dialog
											.findViewById(R.id.lvofdilogshipping);
									;
									ImageView icon_close = (ImageView) dialog
											.findViewById(R.id.icon_close);
									Button btnnext = (Button) dialog
											.findViewById(R.id.btnnext);
									ShipingAddressAdpter shipingAddressAdpter = new ShipingAddressAdpter(
											PlaceOrderAsGuest.this,
											shipingmethodlist);
									lvofdilogshipping
											.setAdapter(shipingAddressAdpter);
									icon_close
											.setOnClickListener(new OnClickListener() {

												@Override
												public void onClick(View v) {
													dialog.dismiss();
												}
											});
									lvofdilogshipping
											.setOnItemClickListener(new OnItemClickListener() {

												@Override
												public void onItemClick(
														AdapterView<?> parent,
														View view,
														int position, long id) {

													String title = shipingmethodlist
															.get(position)
															.getArraylistquote()
															.get(position)
															.getTitle();
													String code = shipingmethodlist
															.get(position)
															.getArraylistquote()
															.get(position)
															.getCode();
													String cost = shipingmethodlist
															.get(position)
															.getArraylistquote()
															.get(position)
															.getCost();
													String tax_class_id = shipingmethodlist
															.get(position)
															.getArraylistquote()
															.get(position)
															.getTax_class_id();
													String text = shipingmethodlist
															.get(position)
															.getArraylistquote()
															.get(position)
															.getText();

													JSONObject shipping_method = new JSONObject();
													try {
														shipping_method.put(
																"code", code);
														shipping_method.put(
																"cost", cost);
														shipping_method.put(
																"tax_class_id",
																tax_class_id);
														shipping_method.put(
																"text", text);
														shipping_method.put(
																"title", title);
														REQUEST.put(
																"shipping_method",
																shipping_method);
														Log.e("REQUEST",
																REQUEST + "");
													} catch (JSONException e) {
														e.printStackTrace();
													}
													ServerDownload download = new ServerDownload(
															PlaceOrderAsGuest.this,
															"",
															PlaceOrderAsGuest.this,
															Constant.PlaceOrderString);
													download.execute(jsonpoststringofrequest);
													dialog.dismiss();

												}
											});
									// btnnext.setOnClickListener(new
									// OnClickListener() {
									//
									// @Override
									// public void onClick(View v) {
									// if (chkboxid.isChecked()) {
									//
									// ServerDownload download = new
									// ServerDownload(
									// PlaceOrderAsGuest.this,
									// "",
									// PlaceOrderAsGuest.this,
									// Constant.PlaceOrderString);
									// download.execute(jsonpoststringofrequest);
									// dialog.dismiss();
									// } else {
									// Toast.makeText(
									// PlaceOrderAsGuest.this,
									// "Please select CheckBox",
									// 10).show();
									// }
									// }
									// });
								}
								dialog.show();

							}

						} else {
							Toast.makeText(PlaceOrderAsGuest.this,
									"Paymentmethod have 0 size Array", 10)
									.show();
						}
					} else {
						Toast.makeText(PlaceOrderAsGuest.this, "Status failed",
								10).show();
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}

			}
		}
	}

	class ShipingAddressAdpter extends BaseAdapter {

		private Context ctx;
		private LayoutInflater inflater;
		private List<ShipingMethodModel> shipingprodcut;

		public ShipingAddressAdpter(Context ctx,
				List<ShipingMethodModel> shipingprodcut) {
			this.ctx = ctx;
			this.shipingprodcut = shipingprodcut;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return shipingprodcut.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return shipingprodcut.get(arg0);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@SuppressLint("InflateParams")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (inflater == null)
				inflater = (LayoutInflater) ctx
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			if (convertView == null)
				convertView = inflater.inflate(R.layout.shipinglist, null);
			TextView txtTitleshipping = (TextView) convertView
					.findViewById(R.id.txtTitleshipping);
			TextView txtsubtitle = (TextView) convertView
					.findViewById(R.id.txtsubtitle);
			TextView txtcost = (TextView) convertView
					.findViewById(R.id.txtcost);
			chkboxid = (CheckBox) convertView.findViewById(R.id.chkboxid);
			final ShipingMethodModel shipingprodcutdetails = shipingprodcut
					.get(position);
			txtTitleshipping.setText(shipingprodcutdetails.getTitle());
			txtsubtitle.setText(shipingprodcutdetails.getArraylistquote()
					.get(position).getTitle());
			txtcost.setText(shipingprodcutdetails.getArraylistquote()
					.get(position).getCost());
			// chkboxid.setTag(position);
			// int idprint = chkboxid.getId();
			// Log.e("idprint", idprint + "");
			// chkboxid.setOnCheckedChangeListener(new OnCheckedChangeListener()
			// {
			//
			// @Override
			// public void onCheckedChanged(CompoundButton buttonView,
			// boolean isChecked) {
			// if (isChecked) {
			// Log.e("Chkboxid", "" + buttonView.getId());
			// chkboxpostionid = (Integer) buttonView.getTag();

			/*
			 * String code = shipingprodcutdetails.getArraylistquote()
			 * .get(chkboxpostionid).getCode(); String cost =
			 * shipingprodcutdetails.getArraylistquote()
			 * .get(chkboxpostionid).getCost(); String tax_class_id =
			 * shipingprodcutdetails.getArraylistquote()
			 * .get(chkboxpostionid).getTax_class_id(); String text =
			 * shipingprodcutdetails.getArraylistquote()
			 * .get(chkboxpostionid).getText(); String title =
			 * shipingprodcutdetails.getArraylistquote()
			 * .get(chkboxpostionid).getTitle();
			 */

			// }

			// }
			// });
			return convertView;
		}
	}

	class BillingAddressAdpter extends BaseAdapter {

		private Context ctx;
		private LayoutInflater inflater;
		private List<PayMentMethodModel> billingproduct;

		public BillingAddressAdpter(Context ctx,
				List<PayMentMethodModel> billingproduct) {
			this.ctx = ctx;
			this.billingproduct = billingproduct;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return billingproduct.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return billingproduct.get(arg0);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@SuppressLint("InflateParams")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if (inflater == null)
				inflater = (LayoutInflater) ctx
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			if (convertView == null)
				convertView = inflater.inflate(R.layout.billinglist, null);
			TextView txtterms = (TextView) convertView
					.findViewById(R.id.txtterms);
			TextView txtbillingcode = (TextView) convertView
					.findViewById(R.id.txtbillingcode);
			TextView txttitlebilling = (TextView) convertView
					.findViewById(R.id.txttitlebilling);
			chkboxidbilling = (CheckBox) convertView
					.findViewById(R.id.chkboxidbilling);
			final PayMentMethodModel billingproductdetails = billingproduct
					.get(position);
			txttitlebilling.setText(billingproductdetails.getTitle());
			txtterms.setText(billingproductdetails.getTerms());
			txtbillingcode.setText(billingproductdetails.getCode());
			chkboxidbilling.setVisibility(View.GONE);

			return convertView;
		}
	}
}
