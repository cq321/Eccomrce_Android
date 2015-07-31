package com.ordervenue.android;

import com.onjection.opencart.Utilz.Constant;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ShowToast")
public class AddAddressOfGuest extends Activity implements OnClickListener {
	Context ctx;
	ImageView ivBack;
	TextView tvHeader;
	Button btnBack, btnSave;
	EditText edtPhone, edtState, edtCity, edtPincode, edtLandMark, edtComp,
			edtAddress2, edtAddress1, edtL_Name, edtF_Name;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.add_address);
		ctx = AddAddressOfGuest.this;
		findAllID();
		tvHeader.setText("Ordervenue");
		btnSave.setOnClickListener(this);
		btnBack.setOnClickListener(this);
		ivBack.setOnClickListener(this);
	}

	private void findAllID() {
		btnBack = (Button) findViewById(R.id.btnBack);
		btnSave = (Button) findViewById(R.id.btnSave);
		ivBack = (ImageView) findViewById(R.id.ivBack);
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
		case R.id.btnSave:
			if (edtPhone.getText().toString().length() <= 0) {
				Toast.makeText(ctx, "Provide Phone No.", Toast.LENGTH_LONG)
						.show();
			} else if (edtState.getText().toString().length() <= 0) {
				Toast.makeText(ctx, "Provide State.", Toast.LENGTH_LONG).show();

			} else if (edtAddress1.getText().toString().length() <= 0) {
				Toast.makeText(ctx, "Provide Address.", Toast.LENGTH_LONG)
						.show();
			} else if (edtAddress2.getText().toString().length() <= 0) {
				Toast.makeText(ctx, "Provide Address 2.", Toast.LENGTH_LONG)
						.show();
			} else if (edtF_Name.getText().toString().length() <= 4) {
				Toast.makeText(ctx, "Provide FirstName.", Toast.LENGTH_LONG)
						.show();
			} else if (edtL_Name.getText().toString().length() <= 0) {
				Toast.makeText(ctx, "Provide LastName", Toast.LENGTH_LONG)
						.show();
			} else if (edtLandMark.getText().toString().length() <= 0) {
				Toast.makeText(ctx, "Provide LandMark", Toast.LENGTH_LONG)
						.show();
			} else if (edtPincode.getText().toString().length() <= 0) {
				Toast.makeText(ctx, "Provide PinCode", Toast.LENGTH_LONG)
						.show();
			} else if (edtComp.getText().toString().length() <= 0) {
				Toast.makeText(ctx, "Provide Compney", Toast.LENGTH_LONG)
						.show();
			} else if (edtCity.getText().toString().length() <= 0) {
				Toast.makeText(ctx, "Provide City", Toast.LENGTH_LONG).show();
			} else if (edtState.getText().toString().length() <= 0) {
				Toast.makeText(ctx, "Provide State", Toast.LENGTH_LONG).show();
			} else if (edtPhone.getText().toString().length() <= 0) {
				Toast.makeText(ctx, "Provide Phone", Toast.LENGTH_LONG).show();
			} else if (!Constant.isInternetConnected(ctx)) {
				Toast.makeText(ctx, "Check internet connection.",
						Toast.LENGTH_LONG).show();
			} else {
				String chk = "add";
				Toast.makeText(ctx, "Sucessfull Add Address", 10).show();
				Intent i = new Intent(ctx, PlaceOrderAsGuest.class);
				i.putExtra("edtPhone", edtPhone.getText().toString());
				i.putExtra("edtF_Name", edtF_Name.getText().toString());
				i.putExtra("edtL_Name", edtL_Name.getText().toString());
				i.putExtra("edtAddress1", edtAddress1.getText().toString());
				i.putExtra("edtAddress2", edtAddress2.getText().toString());
				i.putExtra("edtLandMark", edtLandMark.getText().toString());
				i.putExtra("edtPincode", edtPincode.getText().toString());
				i.putExtra("edtComp", edtComp.getText().toString());
				i.putExtra("edtCity", edtCity.getText().toString());
				i.putExtra("edtState", edtState.getText().toString());
				i.putExtra("chk", chk);
				startActivity(i);
				finish();

			}
			break;
		case R.id.btnBack:
			finish();
			break;
		case R.id.ivBack:
			finish();
			break;
		default:
			break;
		}

	}
}
