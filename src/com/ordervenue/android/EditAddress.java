package com.ordervenue.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class EditAddress extends Activity {

	String name, state, pin, addres;
	EditText edtName, edtAddress, edtLandMark, edtPincode, edtCity, edtState,
			edtPhone;
	Button btnSave ,btnBack;
ImageView ivBack, ivCart  ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_address);
		Bundle b = getIntent().getExtras();
		// bundle.putString("name", ""+name);
		// bundle.putString("state", ""+cutomeraddresdetails.getState());
		// bundle.putString("pin", ""+cutomeraddresdetails.getPostcode());
		// bundle.putString("addres", ""+addres);
		name = b.getString("name");
		state = b.getString("state");
		pin = b.getString("pin");
		addres = b.getString("addres");

		edtName = (EditText) findViewById(R.id.edtF_Name);
		edtAddress = (EditText) findViewById(R.id.edtAddress1);
		edtLandMark = (EditText) findViewById(R.id.edtLandMark);
		edtPincode = (EditText) findViewById(R.id.edtPincode);
		edtCity = (EditText) findViewById(R.id.edtCity);
		edtState = (EditText) findViewById(R.id.edtState);
		edtPhone = (EditText) findViewById(R.id.edtPhone);

		edtName.setText(name);
		edtAddress.setText(addres);
		edtLandMark.setText("");
		edtPincode.setText(pin);
		edtCity.setText("");
		edtState.setText(state);
		edtPhone.setText("");
		ivCart = (ImageView) findViewById(R.id.ivCart);
		ivCart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(EditAddress.this, MyCart.class);
				startActivity(i);
			}
		});
		ivBack = (ImageView) findViewById(R.id.ivBack);
		ivBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EditAddress.this.finish();

			}
		});

		btnSave = (Button) findViewById(R.id.btnSave);
		btnSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
 

			}
		});
		btnBack = (Button) findViewById(R.id.btnBack);
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EditAddress.this.finish();

			}
		});

	}

}
