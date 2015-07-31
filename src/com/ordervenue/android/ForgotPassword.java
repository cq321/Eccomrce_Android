package com.ordervenue.android;

import org.json.JSONException;
import org.json.JSONObject;

import com.onjection.ServerTask.ServerDownload;
import com.onjection.ServerTask.ServerResponse;
import com.onjection.opencart.Utilz.Constant;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class ForgotPassword extends Activity implements OnClickListener,
		ServerResponse {
	ImageView ivBack, ivCart;
	EditText edtForgotPasssword;
	Button btnSubmit;
	ProgressDialog pd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.forgotpassword);
		btnSubmit = (Button) findViewById(R.id.btnSubmit);
		btnSubmit.setOnClickListener(this);
		edtForgotPasssword = (EditText) findViewById(R.id.edtForgPass);
		ivCart = (ImageView) findViewById(R.id.ivCart);
		ivCart.setOnClickListener(this);
		ivBack = (ImageView) findViewById(R.id.ivBack);
		ivBack.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnSubmit:
			Constant.hideKeyboard(ForgotPassword.this);
			if (!android.util.Patterns.EMAIL_ADDRESS.matcher(edtForgotPasssword.getText().toString()).matches()) {
				Toast.makeText(this, "Provide valid email address.",
						Toast.LENGTH_LONG).show();
			} else {
				pd = ProgressDialog.show(this, "", "Sending Password", false);
				ServerDownload download = new ServerDownload(
						ForgotPassword.this, "", ForgotPassword.this,
						Constant.forgotpassword);
				download.execute(postRequest());
			}
			break;

		case R.id.ivBack:
			ForgotPassword.this.finish();

			break;

		case R.id.ivCart:
			Intent i = new Intent(ForgotPassword.this, MyCart.class);
			startActivity(i);
			ForgotPassword.this.finish();
			break;

		default:
			break;
		}

	}

	String postRequest() {

		String request = "{\"mailID\":\""
				+ edtForgotPasssword.getText().toString() + "\"}";
		Log.e("post request", request);
		return request;
	}

	@Override
	public void httpResponse(JSONObject jsonObject, String tag, int responceCode) {
		// TODO Auto-generated method stub
	if(	pd.isIndeterminate()){
		pd.dismiss();
	}
	if (jsonObject!=null) {
		Log.e("Responce Forgot pass", "" + jsonObject);

		try {
			String status = jsonObject.getString("status");
			if (status.equals("success")) {
				Toast.makeText(ForgotPassword.this, "" + jsonObject.getString("message"), 3)
				.show();
		this.finish();				
	}

	}catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} finally {

	}
}
}}