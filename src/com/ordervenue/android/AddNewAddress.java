package com.ordervenue.android;

import org.json.JSONObject;

import com.onjection.ServerTask.ConnectionDector;
import com.onjection.ServerTask.ServerDownload;
import com.onjection.ServerTask.ServerResponse;
import com.onjection.opencart.Utilz.Constant;
import com.onjection.opencart.Utilz.Prefs;
import com.onjection.opencart.Utilz.Urls;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint({ "CutPasteId", "ShowToast" })
public class AddNewAddress extends Activity implements OnClickListener,
		ServerResponse {
	ImageView ivBack, ivCart;
	TextView tvHeader;
	Button btnBack, btnSave;
	EditText edtPhone, edtState, edtCity, edtPincode, edtLandMark, edtComp,
			edtAddress2, edtAddress1, edtL_Name, edtF_Name;
	ProgressDialog pd;
	ConnectionDector connectionDector = new ConnectionDector(this);
	Context ctx;
	String Address;
	String address_id;
	int value;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.add_address);
		ctx = AddNewAddress.this;
		findAllID();
		tvHeader.setText("Ordervenue");
		Address = getIntent().getStringExtra("Address");
		value = getIntent().getIntExtra("i", 0);
		ivCart.setOnClickListener(this);
		ivBack.setOnClickListener(this);
		btnBack.setOnClickListener(this);
		btnSave.setOnClickListener(this);
		if (Address.equals("AddNewAddress")) {

		} else {
			address_id = getIntent().getStringExtra("address_id");
			edtState.setText(getIntent().getStringExtra("edtState"));
			edtF_Name.setText(getIntent().getStringExtra("edtF_Name"));
			edtCity.setText(getIntent().getStringExtra("edtCity"));
			edtPincode.setText(getIntent().getStringExtra("edtPincode"));
			edtComp.setText(getIntent().getStringExtra("edtComp"));
			edtAddress2.setText(getIntent().getStringExtra("edtAddress2"));
			edtAddress1.setText(getIntent().getStringExtra("edtAddress1"));
			edtL_Name.setText(getIntent().getStringExtra("edtL_Name"));
		}
	}

	private void findAllID() {
		btnBack = (Button) findViewById(R.id.btnBack);
		btnSave = (Button) findViewById(R.id.btnSave);
		ivBack = (ImageView) findViewById(R.id.ivBack);
		ivCart = (ImageView) findViewById(R.id.ivCart);
		edtPhone = (EditText) findViewById(R.id.edtPhone);
		edtState = (EditText) findViewById(R.id.edtState);
		edtCity = (EditText) findViewById(R.id.edtCity);
		edtComp = (EditText) findViewById(R.id.edtComp);
		edtPincode = (EditText) findViewById(R.id.edtPincode);
		edtLandMark = (EditText) findViewById(R.id.edtLandMark);
		edtAddress2 = (EditText) findViewById(R.id.edtAddress2);
		edtAddress1 = (EditText) findViewById(R.id.edtAddress1);
		edtL_Name = (EditText) findViewById(R.id.edtL_Name);
		edtF_Name = (EditText) findViewById(R.id.edtF_Name);
		tvHeader = (TextView) findViewById(R.id.tvHeader);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ivBack:
			if (value == 2) {
				backbuttonclcik();
			} else if (value == 1) {
				backbuttonplaceorder();
			} else {
				finish();
			}

			break;
		case R.id.ivCart:
			Intent i = new Intent(AddNewAddress.this, MyCart.class);
			startActivity(i);

			break;

		case R.id.btnBack:
			if (value == 2) {
				backbuttonclcik();
			} else if (value == 1) {
				backbuttonplaceorder();
			}
			break;
		case R.id.btnSave:
			if (Address.equals("AddNewAddress")) {
				InsertnewAddress();
			} else {
				UpdateNewAddress();
			}
		default:
			break;
		}
	}

	private void UpdateNewAddress() {

		if (edtPhone.getText().toString().length() <= 0) {
			Toast.makeText(ctx, "Provide Phone No.", Toast.LENGTH_LONG).show();
		} else if (edtState.getText().toString().length() <= 0) {
			Toast.makeText(ctx, "Provide State.", Toast.LENGTH_LONG).show();

		} else if (edtAddress1.getText().toString().length() <= 0) {
			Toast.makeText(ctx, "Provide Address.", Toast.LENGTH_LONG).show();

		} else if (edtAddress2.getText().toString().length() <= 0) {
			Toast.makeText(ctx, "Provide Address 2.", Toast.LENGTH_LONG).show();

		} else if (edtL_Name.getText().toString().length() <= 4) {
			Toast.makeText(ctx, "Provide FirstName.", Toast.LENGTH_LONG).show();

		} else if (edtL_Name.getText().toString().length() <= 0) {
			Toast.makeText(ctx, "Provide LastName", Toast.LENGTH_LONG).show();
		} else if (!Constant.isInternetConnected(ctx)) {
			Toast.makeText(ctx, "Check internet connection.", Toast.LENGTH_LONG)
					.show();
		} else {

			if (connectionDector.isConnectingToInternet()) {
				// Progress Dialog initalisation
				/*
				 * ProgressDialog pd = ProgressDialog.show(ctx, "",
				 * "Insert Address", false);
				 */
				ServerDownload download = new ServerDownload(
						AddNewAddress.this, "", AddNewAddress.this,
						Constant.UpdateNewAddresstag);
				download.execute(postRequestOfUpdate());

			} else {

				Toast.makeText(getApplicationContext(),
						"No Neteork Connection", Toast.LENGTH_SHORT).show();

			}

		}

	}

	private String postRequestOfUpdate() {

		String request = "{\"address_id\":\"" + address_id
				+ "\",\"customer_id\":\""
				+ Prefs.getPreferences(ctx, "Customer_id")
				+ "\",\"firstname\":\"" + edtF_Name.getText().toString()
				+ "\",\"lastname\":\"" + edtL_Name.getText().toString()
				+ "\",\"company\":\"" + edtComp.getText().toString()
				+ "\",\"address_1\":\"" + edtAddress1.getText().toString()
				+ edtAddress2.getText().toString() + "\",\"city\":\""
				+ edtCity.getText().toString() + "\",\"postcode\":\""
				+ edtPincode.getText().toString() + "\",\"country\":\"" + 99
				+ "\",\"state\":\"" + edtState.getText().toString() + "\"}";
		Log.e(request, request);
		return request;
	}

	private void InsertnewAddress() {
		if (edtPhone.getText().toString().length() <= 0) {
			Toast.makeText(ctx, "Provide Phone No.", Toast.LENGTH_LONG).show();
		} else if (edtState.getText().toString().length() <= 0) {
			Toast.makeText(ctx, "Provide State.", Toast.LENGTH_LONG).show();

		} else if (edtAddress1.getText().toString().length() <= 0) {
			Toast.makeText(ctx, "Provide Address.", Toast.LENGTH_LONG).show();

		} else if (edtAddress2.getText().toString().length() <= 0) {
			Toast.makeText(ctx, "Provide Address 2.", Toast.LENGTH_LONG).show();

		} else if (edtL_Name.getText().toString().length() <= 4) {
			Toast.makeText(ctx, "Provide FirstName.", Toast.LENGTH_LONG).show();

		} else if (edtL_Name.getText().toString().length() <= 0) {
			Toast.makeText(ctx, "Provide LastName", Toast.LENGTH_LONG).show();
		} else if (!Constant.isInternetConnected(ctx)) {
			Toast.makeText(ctx, "Check internet connection.", Toast.LENGTH_LONG)
					.show();
		} else {

			if (connectionDector.isConnectingToInternet()) {
				// Progress Dialog initalisation
				/*
				 * ProgressDialog pd = ProgressDialog.show(ctx, "",
				 * "Insert Address", false);
				 */
				ServerDownload download = new ServerDownload(
						AddNewAddress.this, "", AddNewAddress.this,
						Constant.AddNewAddresstag);
				download.execute(postRequestOfInsert());
				edtPhone.setText("");
				edtState.setText("");
				edtCity.setText("");
				edtPincode.setText("");
				edtLandMark.setText("");
				edtComp.setText("");
				edtAddress2.setText("");
				edtAddress1.setText("");
				edtL_Name.setText("");
				edtF_Name.setText("");
			} else {

				Toast.makeText(getApplicationContext(),
						"No Neteork Connection", Toast.LENGTH_SHORT).show();

			}

		}
	}

	private String postRequestOfInsert() {
		String request = "{\"customer_id\":\""
				+ Prefs.getPreferences(ctx, "Customer_id") + "\",\"tokan\":\""
				+ Urls.tokan + "\",\"firstname\":\""
				+ edtF_Name.getText().toString() + "\",\"lastname\":\""
				+ edtL_Name.getText().toString() + "\",\"company\":\""
				+ edtComp.getText().toString() + "\",\"address_1\":\""
				+ edtAddress1.getText().toString() + "\",\"address_2\":\""
				+ edtAddress2.getText().toString() + "\",\"city\":\""
				+ edtCity.getText().toString() + "\",\"postcode\":\""
				+ edtPincode.getText().toString() + "\",\"country\":\"" + 99
				+ "\",\"state\":\"" + edtState.getText().toString() + "\"}";
		return request;
	}

	private void backbuttonclcik() {
		Intent iaddress = new Intent(ctx, MyAddress.class);
		startActivity(iaddress);
		finish();
	}

	private void backbuttonplaceorder() {
		Intent iback = new Intent(ctx, PlaceOrder.class);
		startActivity(iback);
		finish();
	}

	@Override
	public void httpResponse(JSONObject jsonObject, String tag, int responceCode) {

		if (jsonObject != null) {
			Log.e("Responce Add New Address", "" + jsonObject);
			if (tag.equals(Constant.AddNewAddresstag)) {
				try {
					String status = jsonObject.getString("status");
					if (status.equals("success")) {
						String Message = jsonObject.getString("Message");
						Toast.makeText(ctx, "" + Message, 10).show();

					} else {
						Toast.makeText(ctx, "Error", 10).show();
					}
				} catch (Exception ex) {
					ex.printStackTrace();

				}
			} else if (tag.equals(Constant.UpdateNewAddresstag)) {
				try {
					String status = jsonObject.getString("status");
					if (status.equals("success")) {
						String Message = jsonObject.getString("Message");
						Toast.makeText(ctx, "" + Message, 10).show();
						Intent i = new Intent(this, MyAddress.class);
						startActivity(i);
						this.finish();

					} else {
						Toast.makeText(ctx, "Error", 10).show();
					}
				} catch (Exception ex) {
					ex.printStackTrace();

				}

			}
		}

	}
}
