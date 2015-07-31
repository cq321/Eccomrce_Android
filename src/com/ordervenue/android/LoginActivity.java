package com.ordervenue.android;

import org.json.JSONException;
import org.json.JSONObject;

import com.onjection.ServerTask.ServerDownload;
import com.onjection.ServerTask.ServerResponse;
import com.onjection.opencart.Utilz.Constant;
import com.onjection.opencart.Utilz.Prefs;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener,
		ServerResponse {
	EditText edtUsername, edtpassword;
	Button btnOk;
	ProgressDialog pd;
	TextView txtSignup, txtfrgtpassowrd, tvHeader;
	ImageView ivBack, ivCart;
	Context ctx;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_login);
		ctx = LoginActivity.this;
		edtUsername = (EditText) findViewById(R.id.edtUserName);
		edtpassword = (EditText) findViewById(R.id.edtPassword);
		txtSignup = (TextView) findViewById(R.id.txtSignup);
		txtfrgtpassowrd = (TextView) findViewById(R.id.txtfrgtpassowrd);
		tvHeader = (TextView) findViewById(R.id.tvHeader);
		ivBack = (ImageView) findViewById(R.id.ivBack);
		btnOk = (Button) findViewById(R.id.btnOk);
		btnOk.setOnClickListener(this);
		txtSignup.setOnClickListener(this);
		txtfrgtpassowrd.setOnClickListener(this);
		ivCart = (ImageView) findViewById(R.id.ivCart);
		ivCart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(LoginActivity.this, MyCart.class);
				startActivity(i);
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnOk:
			Constant.hideKeyboard(LoginActivity.this);

			if (edtUsername.getText().toString().length() <= 0) {
				Toast.makeText(ctx, "Provide email address.", Toast.LENGTH_LONG)
						.show();
			} else if (!Constant
					.isValidEmail1(edtUsername.getText().toString())) {
				Toast.makeText(ctx, "Provide valid email.", Toast.LENGTH_LONG)
						.show();
			} else if (edtUsername.getText().toString().length() <= 0) {
				Toast.makeText(ctx, "Provide password.", Toast.LENGTH_LONG)
						.show();

			} else if (!Constant.isInternetConnected(ctx)) {
				Toast.makeText(ctx, "Check internet connection.",
						Toast.LENGTH_LONG).show();
			} else {
				pd = ProgressDialog.show(this, "", "Login", false);
				ServerDownload download = new ServerDownload(
						LoginActivity.this, "", LoginActivity.this,
						Constant.tagLogin);
				download.execute(postRequest());
			}

			break;
		case R.id.txtfrgtpassowrd:
			Intent inetntforgotpassword = new Intent(LoginActivity.this,
					ForgotPassword.class);
			startActivity(inetntforgotpassword);
			finish();
			break;
		case R.id.txtSignup:
			Intent isignup = new Intent(LoginActivity.this, Registration.class);
			startActivity(isignup);
			finish();
			break;
		case R.id.ivBack:
			finish();
			break;
		default:
			break;
		}

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
				if (status.equals("success")) {
					JSONObject jsoncustomerdetails = jsonObject
							.getJSONObject("customer");
					/*
					 * jsoncustomerdetails.optString("total"); ;
					 * jsoncustomerdetails.optString("newsletter");
					 * jsoncustomerdetails.optString("wishlist"); ; ; ;
					 */

					Prefs.setPreferences(LoginActivity.this, "Customer_id",
							jsoncustomerdetails.optString("customer_id"));
					Prefs.setPreferences(LoginActivity.this, "email",
							jsoncustomerdetails.optString("email"));
					Prefs.setPreferences(LoginActivity.this, "firstname",
							jsoncustomerdetails.optString("firstname"));
					Prefs.setPreferences(LoginActivity.this, "lastname",
							jsoncustomerdetails.optString("lastname"));
					Prefs.setPreferences(LoginActivity.this, "telephone",
							jsoncustomerdetails.optString("telephone"));

					Intent intent = new Intent(LoginActivity.this,
							NevigationDrawer.class);
					startActivity(intent);
					finish();
				} else {
					// Toast.makeText(LoginActivity.this,
					// "Invalid Username or Password", 5).show();
					new AlertDialog.Builder(this)
							.setTitle("Something went worng")
							.setMessage("Invalid username or password")
							.setPositiveButton("Try again",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											// continue with delete
										}
									})

							.show();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			} finally {

			}
		}

	}

	String postRequest() {

		String request = "{\"username\":\"" + edtUsername.getText().toString()
				+ "\",\"password\":\"" + edtpassword.getText().toString()
				+ "\"}";
		Log.e("post request", request);
		return request;
	}

}
