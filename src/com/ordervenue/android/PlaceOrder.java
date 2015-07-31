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
import com.onjection.opencart.Utilz.ServiceHandler;
import com.onjection.opencart.Utilz.Urls;
import com.onjection.opencart.model.CustmorAddressModel;
import com.onjection.opencart.model.CustomerAddressAdpter;
import com.onjection.opencart.model.PayMentMethodModel;
import com.onjection.opencart.model.Quote;
import com.onjection.opencart.model.ShipingMethodModel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ShowToast")
public class PlaceOrder extends Activity implements ServerResponse {
	@SuppressLint("ShowToast")
	Button btnProceedToPay;
	TextView txtEmail, txtnewAddress;
	private ArrayList<CustmorAddressModel> CustomeraddressList = new ArrayList<CustmorAddressModel>();
	Context ctx;
	ArrayList<Quote> quotelist = new ArrayList<Quote>();
	ImageView ivBack, ivCart;
	ListView lvAddressselect;
	CustomerAddressAdpter customerAddressAdpter;
	JSONObject REQUEST;
	String jsonstringproduct, jsonpoststringofrequest;
	CheckBox chkboxid, chkboxidbilling;
	ProgressDialog pd;
	ArrayList<PayMentMethodModel> paymentmethodlist = new ArrayList<PayMentMethodModel>();
	ArrayList<ShipingMethodModel> shipingmethodlist = new ArrayList<ShipingMethodModel>();
	Dialog dialog, dialog1;
	ListView lvofdilogshipping, lvdialogbillingaddress;
	int chkboxpostionid;
	Boolean listclick = false;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.place_order);
		ctx = PlaceOrder.this;
		txtEmail = (TextView) findViewById(R.id.txtEmail);
		txtnewAddress = (TextView) findViewById(R.id.txtnewAddress);
		lvAddressselect = (ListView) findViewById(R.id.lvAddressselect);
		ivCart = (ImageView) findViewById(R.id.ivCart);
		ivCart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(PlaceOrder.this, MyCart.class);
				startActivity(i);
			}
		});
		new GetSearchAddress().execute();
		ivBack = (ImageView) findViewById(R.id.ivBack);
		txtEmail.setText(Prefs.getPreferences(PlaceOrder.this, "email"));
		btnProceedToPay = (Button) findViewById(R.id.btnProceedToPay);
		btnProceedToPay.setOnClickListener(new OnClickListener() {

			@SuppressLint("ShowToast")
			@Override
			public void onClick(View v) {
				Toast.makeText(ctx, "Please Select Addresss", 10).show();

			}
		});
		txtnewAddress.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int i = 1;
				Intent inewAddress = new Intent(PlaceOrder.this,
						AddNewAddress.class);
				inewAddress.putExtra("Address", "AddNewAddress");
				inewAddress.putExtra("i", i);
				startActivity(inewAddress);
			}
		});
		ivBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		lvAddressselect.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				lvAddressselect.getChildAt(position).setBackgroundColor(
						Color.GRAY);
				customerAddressAdpter.notifyDataSetChanged();
				REQUEST = new JSONObject();
				JSONObject jsonaddress = new JSONObject();
				try {
					jsonaddress.put("payment_firstname", CustomeraddressList
							.get(position).getFirstname());
					jsonaddress.put("payment_lastname", CustomeraddressList
							.get(position).getLastname());
					jsonaddress.put("payment_company",
							CustomeraddressList.get(position).getCompany());
					jsonaddress.put("payment_address_1", CustomeraddressList
							.get(position).getAddress_1());
					jsonaddress.put("payment_address_2", CustomeraddressList
							.get(position).getAddress_2());
					jsonaddress.put("payment_city",
							CustomeraddressList.get(position).getCity());
					jsonaddress.put("payment_postcode", CustomeraddressList
							.get(position).getPostcode());
					jsonaddress.put("payment_country",
							CustomeraddressList.get(position).getCountry());
					jsonaddress.put("payment_country_id", CustomeraddressList
							.get(position).getCountry_id());
					jsonaddress.put("payment_zone", "Haryana");
					jsonaddress.put("payment_zone_id",
							CustomeraddressList.get(position).getZone_id());
					jsonaddress.put("payment_telephone", "9958061126");
					jsonaddress.put("payment_email",
							Prefs.getPreferences(ctx, "email"));
				} catch (JSONException e) {
					e.printStackTrace();
				}
				JSONObject Jsonshipping = new JSONObject();

				try {
					Jsonshipping.put("shipping_firstname", CustomeraddressList
							.get(position).getFirstname());
					Jsonshipping.put("shipping_lastname", CustomeraddressList
							.get(position).getLastname());
					Jsonshipping.put("shipping_company", CustomeraddressList
							.get(position).getCompany());
					Jsonshipping.put("shipping_address_1", CustomeraddressList
							.get(position).getAddress_1());
					Jsonshipping.put("shipping_address_2", CustomeraddressList
							.get(position).getAddress_2());
					Jsonshipping.put("shipping_city",
							CustomeraddressList.get(position).getCity());
					Jsonshipping.put("shipping_postcode", CustomeraddressList
							.get(position).getPostcode());
					Jsonshipping.put("shipping_country", CustomeraddressList
							.get(position).getCountry());
					Jsonshipping.put("shipping_country_id", CustomeraddressList
							.get(position).getCountry_id());
					Jsonshipping.put("shipping_zone",
							CustomeraddressList.get(position).getState());
					Jsonshipping.put("shipping_zone_id", CustomeraddressList
							.get(position).getZone_id());
					Jsonshipping.put("shipping_telephone", "9958061126");
					Jsonshipping.put("shipping_email",
							Prefs.getPreferences(ctx, "email"));

				} catch (JSONException e) {
					e.printStackTrace();
				}
				jsonstringproduct = Prefs.getPreferences(PlaceOrder.this,
						"jsonstringproduct");
				JSONObject jsonproduct;
				try {
					jsonproduct = new JSONObject(jsonstringproduct);
					JSONArray jsonarray = jsonproduct.getJSONArray("products");
					REQUEST.put("products", jsonarray);
					REQUEST.put("language_id",
							jsonproduct.optString("language_id"));
					REQUEST.put("tokan", Urls.tokan);
					REQUEST.put("payment_address", jsonaddress);
					REQUEST.put("shipping_address", Jsonshipping);
					REQUEST.put("customer_id",
							Prefs.getPreferences(ctx, "Customer_id"));
					jsonpoststringofrequest = REQUEST.toString();
					Log.d("REQUEST", REQUEST.toString());
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
				pd = ProgressDialog.show(PlaceOrder.this, "",
						"Shipping Address", false);
				ServerDownload download = new ServerDownload(PlaceOrder.this,
						"", PlaceOrder.this, Constant.ShipingString);
				download.execute(jsonpoststringofrequest);
			}
		});
	}

	public class GetSearchAddress extends AsyncTask<Void, Void, Void> {

		protected void onPreExecute() {
			super.onPreExecute();

		}

		@SuppressLint("ShowToast")
		protected Void doInBackground(Void... arg0) {
			// Creating service handler class instance
			ServiceHandler sh = new ServiceHandler();
			// Making a request to url and getting response
			String Customer_id = Prefs.getPreferences(PlaceOrder.this,
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
			int id = 2;
			customerAddressAdpter = new CustomerAddressAdpter(ctx,
					CustomeraddressList, id);
			lvAddressselect.setAdapter(customerAddressAdpter);

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
									dialog1 = new Dialog(PlaceOrder.this);
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
											PlaceOrder.this, paymentmethodlist);
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
													Log.e("payment json", ""
															+ finaljsondata);
													Intent i = new Intent(
															PlaceOrder.this,
															ConfirmOrder.class);
													i.putExtra("finaljsondata",
															finaljsondata);
													i.putExtra("payment", code);
													startActivity(i);
													dialog1.dismiss();
													PlaceOrder.this.finish();
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
							}
						} else {
							Toast.makeText(PlaceOrder.this,
									"Paymentmethod have 0 size Array", 10)
									.show();
						}
					} else {
						Toast.makeText(PlaceOrder.this, "Status failed", 10)
								.show();
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
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
									dialog = new Dialog(PlaceOrder.this);
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
											PlaceOrder.this, shipingmethodlist);
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
															PlaceOrder.this,
															"",
															PlaceOrder.this,
															Constant.PlaceOrderString);
													download.execute(jsonpoststringofrequest);
													dialog.dismiss();

												}
											});
								}
								dialog.show();

							}

						} else {
							Toast.makeText(PlaceOrder.this,
									"Paymentmethod have 0 size Array", 10)
									.show();
						}
					} else {
						Toast.makeText(PlaceOrder.this, "Status failed", 10)
								.show();
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
			return shipingprodcut.size();
		}

		@Override
		public Object getItem(int arg0) {
			return shipingprodcut.get(arg0);
		}

		@Override
		public long getItemId(int position) {
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
			return billingproduct.size();
		}

		@Override
		public Object getItem(int arg0) {
			return billingproduct.get(arg0);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@SuppressLint("InflateParams")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
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
			/*
			 * chkboxidbilling.setId(position);
			 * 
			 * chkboxidbilling .setOnCheckedChangeListener(new
			 * OnCheckedChangeListener() {
			 * 
			 * @Override public void onCheckedChanged(CompoundButton buttonView,
			 * boolean isChecked) { if (isChecked) { int chkboxidbilling =
			 * buttonView.getId(); String title = billingproduct.get(
			 * chkboxidbilling).getTitle(); String code = billingproduct.get(
			 * chkboxidbilling).getCode(); String terms = billingproduct.get(
			 * chkboxidbilling).getSort_order(); String sort_order =
			 * billingproduct.get( chkboxidbilling).getTerms();
			 * 
			 * JSONObject payment_method = new JSONObject(); try {
			 * payment_method.put("code", code); payment_method.put("terms",
			 * terms); payment_method .put("sort_order", sort_order);
			 * payment_method.put("title", title); REQUEST.put("payment_method",
			 * payment_method); Log.e("REQUEST", REQUEST + ""); } catch
			 * (JSONException e) { e.printStackTrace(); }
			 * 
			 * }
			 * 
			 * } });
			 */
			return convertView;
		}
	}
}
