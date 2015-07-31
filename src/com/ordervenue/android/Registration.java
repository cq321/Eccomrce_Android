package com.ordervenue.android;

import org.json.JSONObject;

import com.onjection.ServerTask.ConnectionDector;
import com.onjection.ServerTask.ServerDownload;
import com.onjection.ServerTask.ServerResponse;
import com.onjection.opencart.Utilz.Constant;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Registration extends Activity implements ServerResponse , OnItemSelectedListener {
	EditText edtRegisterEmail, edtRegisterPassword, edtRegisterFirstNm,
			edtRegisterLastNm, edtRegisteraddress, edtRegisterPhone,
			 edtRegisterCity, edtRegisterPinCode,
			 edtRegisterFax;
	Button btnRegister;
	Spinner spnRegisterstate,spnRegisterNewsLetter;
	String selectedStateID, selectedNL;
	TextView txtSignIn, tvHeader, tvRegistercountry;
	ImageView ivBack, ivCart;
	Context ctx;
	ProgressDialog pd;
	String [] arrState, arrStateID, arrNews;
	ConnectionDector connectionDector = new ConnectionDector(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.signup);
		Constant.hideKeyboard(Registration.this);

		ctx = Registration.this;
		arrState = getResources().getStringArray(R.array.arr_state);
		arrStateID = getResources().getStringArray(R.array.arr_state_id);
		arrNews = getResources().getStringArray(R.array.arrNewsLetter);

		findallId();
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
				Registration.this,
				android.R.layout.simple_spinner_item, arrNews);

		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnRegisterNewsLetter.setAdapter(dataAdapter);
		spnRegisterNewsLetter.setSelection(2);
		spnRegisterNewsLetter.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (position==2) {
					selectedNL=""+0;
				}else{
				selectedNL=""+position;
			}}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(
				Registration.this,
				android.R.layout.simple_spinner_item, arrState);

		dataAdapter1
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnRegisterstate.setAdapter(dataAdapter1);
		spnRegisterstate.setOnItemSelectedListener(this);
		tvHeader.setText("SignUp");
		btnRegister.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Constant.hideKeyboard(Registration.this);
				doRegister();
			}
		});
		ivBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Registration.this.finish();
			}
		});
		txtSignIn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent ilogin = new Intent(ctx, LoginActivity.class);
				startActivity(ilogin);
				finish();
			}
		});
	}

	private void findallId() {
		edtRegisterEmail = (EditText) findViewById(R.id.edtRegisterEmail);
		edtRegisterPassword = (EditText) findViewById(R.id.edtRegisterPassword);
		edtRegisterFirstNm = (EditText) findViewById(R.id.edtRegisterFirstNm);
		edtRegisterLastNm = (EditText) findViewById(R.id.edtRegisterLastNm);
		edtRegisteraddress = (EditText) findViewById(R.id.edtRegisteraddress);
		edtRegisterPhone = (EditText) findViewById(R.id.edtRegisterPhone);
		tvRegistercountry = (TextView) findViewById(R.id.tvRegistercountry);
		spnRegisterstate = (Spinner) findViewById(R.id.spnRegisterstate);
		edtRegisterCity = (EditText) findViewById(R.id.edtRegisterCity);
		edtRegisterPinCode = (EditText) findViewById(R.id.edtRegisterPinCode);
		spnRegisterNewsLetter = (Spinner) findViewById(R.id.spnRegisterNewsLetter);
		edtRegisterFax = (EditText) findViewById(R.id.edtRegisterFax);
		btnRegister = (Button) findViewById(R.id.btnRegister);
		txtSignIn = (TextView) findViewById(R.id.txtSignIn);
		tvHeader = (TextView) findViewById(R.id.tvHeader);
		ivBack = (ImageView) findViewById(R.id.ivBack);
		ivCart = (ImageView) findViewById(R.id.ivCart);
		ivCart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(Registration.this, MyCart.class);
				startActivity(i);
			}
		});
	}

	@Override
	public void httpResponse(JSONObject jsonObject, String tag, int responceCode) {
		if (pd.isShowing()) {
			pd.dismiss();
		}
		if (jsonObject != null) {
			Log.e("Responce login", "" + jsonObject);
			try {
				String status = jsonObject.getString("status");
				String message = jsonObject.getString("message");
				if (status.equals("success")) {

					Toast.makeText(ctx, message, 10).show();
					Intent iregister = new Intent(ctx, LoginActivity.class);
					startActivity(iregister);
					finish();
				} else {
					Toast.makeText(ctx, "" + message, 3).show();
				}

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

	}

	private void doRegister() {

		if (edtRegisterEmail.getText().toString().length() <= 0) {
			Toast.makeText(ctx, "Provide email address.", Toast.LENGTH_LONG)
					.show();
		} else if (!Constant.isValidEmail1(edtRegisterEmail.getText()
				.toString())) {
			Toast.makeText(ctx, "Provide valid email.", Toast.LENGTH_LONG)
					.show();
		} else if (edtRegisterPassword.getText().toString().length() <= 0) {
			Toast.makeText(ctx, "Provide password.", Toast.LENGTH_LONG).show();

		} else if (edtRegisterPassword.getText().toString().length() <= 4) {
			Toast.makeText(ctx, "Provide password.", Toast.LENGTH_LONG).show();

		} else if (edtRegisteraddress.getText().toString().length() <= 0) {
			Toast.makeText(ctx, "Provide Address", Toast.LENGTH_LONG).show();
		} else if (edtRegisterFirstNm.getText().toString().length() <= 0) {
			Toast.makeText(ctx, "Provide first name.", Toast.LENGTH_LONG)
					.show();

		} else if (edtRegisterLastNm.getText().toString().length() <= 0) {
			Toast.makeText(ctx, "Provide last name.", Toast.LENGTH_LONG).show();

		} else if (edtRegisterCity.getText().toString().length() <= 0) {
			Toast.makeText(ctx, "Provide City.", Toast.LENGTH_LONG).show();

		} else if (edtRegisterPhone.getText().toString().length() <= 0) {
			Toast.makeText(ctx, "Provide ContactNo.", Toast.LENGTH_LONG).show();

		}  else if (!Constant.isInternetConnected(ctx)) {
			Toast.makeText(ctx, "Check internet connection.", Toast.LENGTH_LONG)
					.show();
		} else {

			if (connectionDector.isConnectingToInternet()) {
				// Progress Dialog initalisation

				pd = new ProgressDialog(Registration.this);
				pd.setMessage("Please wait...");
				pd.setCancelable(false);
				pd.show();
				ServerDownload download = new ServerDownload(Registration.this,
						"", Registration.this, Constant.tagregistraion);
				download.execute(postRequestOfRegister());

			} else {

				Toast.makeText(getApplicationContext(),
						"No Neteork Connection", Toast.LENGTH_SHORT).show();

			}

		}
	}

	String postRequestOfRegister() {

		String request = "{\"firstname\":\""
				+ edtRegisterFirstNm.getText().toString()
				+ "\",\"lastname\":\"" + edtRegisterLastNm.getText().toString()
				+ "\",\"phone\":\"" + edtRegisterPhone.getText().toString()
				+ "\",\"address\":\"" + edtRegisteraddress.getText().toString()
				+ "\",\"city\":\"" + edtRegisterCity.getText().toString()
				+ "\",\"country\":\"99" + "\",\"pincode\":\""
				+ edtRegisterPinCode.getText().toString() + "\",\"state\":\""
				+ selectedStateID + "\",\"password\":\""
				+ edtRegisterPassword.getText().toString() + "\",\"email\":\""
				+ edtRegisterEmail.getText().toString() + "\","
				+ "\"company\":\"" + "onjection" + "\"," + "\"fax\":\""
				+ edtRegisterFax.getText().toString() + "\",\"newsletter\":\""
				+ selectedNL + "\"}";
		Log.e("request", request);
		return request;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
		selectedStateID= arrStateID[position].toString();
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}
}
