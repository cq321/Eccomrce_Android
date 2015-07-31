package com.ordervenue.android;

import org.json.JSONException;
import org.json.JSONObject;

import com.onjection.ServerTask.ConnectionDector;
import com.onjection.ServerTask.ServerDownload;
import com.onjection.ServerTask.ServerResponse;
import com.onjection.opencart.Utilz.Constant;
import com.onjection.opencart.Utilz.Prefs;

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

public class ContactUs extends Activity implements ServerResponse {
	Button btnSubmit;
	TextView tvHeader;
	EditText edtDescrp, edtsubject, edtphoneno, edtedmailaddress, edtlastname,
			edtfirstname;
	ImageView ivBack, ivCart;
	Context ctx;
	ProgressDialog pd;
	ConnectionDector connectionDector;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		// Remove notification bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.contactus);
		ctx = ContactUs.this;
		allidfind();
		tvHeader.setText("Contact Us");
		ivCart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(ContactUs.this, MyCart.class);
				startActivity(i);
			}
		});
		ivBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		btnSubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (edtDescrp.getText().toString().length() <= 0) {
					Toast.makeText(ctx, "Please insert at least one value!",
							Toast.LENGTH_LONG).show();
				} else if (edtsubject.getText().toString().length() <= 0) {
					Toast.makeText(ctx, "Please Provide Subject.",
							Toast.LENGTH_LONG).show();
				} else if (edtedmailaddress.getText().toString().length() <= 0) {
					Toast.makeText(ctx, "Please Provide EmailId.",
							Toast.LENGTH_LONG).show();
				} else if (!Constant.isValidEmail1(edtedmailaddress.getText()
						.toString())) {
					Toast.makeText(ctx, "Provide valid email.",
							Toast.LENGTH_LONG).show();
				} else if (edtfirstname.getText().toString().length() <= 0) {
					Toast.makeText(ctx, "Please Provide FirstName.",
							Toast.LENGTH_LONG).show();
				} else if (edtlastname.getText().toString().length() <= 0) {
					Toast.makeText(ctx, "Please Provide LastName.",
							Toast.LENGTH_LONG).show();
				} else if (edtphoneno.getText().toString().length() <= 0
						&& edtphoneno.getText().toString().length() >= 10) {
					Toast.makeText(ContactUs.this,
							"Please Provide Correct Mobile NO.",
							Toast.LENGTH_LONG).show();
				} else {
					if (new ConnectionDector(ctx).isConnectingToInternet()) { //
						pd = ProgressDialog.show(ContactUs.this, "",
								"Please Wait", false);
						ServerDownload download = new ServerDownload(
								ContactUs.this, "", ContactUs.this,
								Constant.ContactUs);
						download.execute(postRequestOfContact());

					} else {
						Toast.makeText(ctx, "No Neteork Connection",
								Toast.LENGTH_SHORT).show();
					}

				}
			}

		});
	}

	private void allidfind() {
		edtDescrp = (EditText) findViewById(R.id.edtDescrp);
		edtsubject = (EditText) findViewById(R.id.edtsubject);
		edtphoneno = (EditText) findViewById(R.id.edtphoneno);
		edtedmailaddress = (EditText) findViewById(R.id.edtedmailaddress);
		edtlastname = (EditText) findViewById(R.id.edtlastname);
		edtfirstname = (EditText) findViewById(R.id.edtfirstname);
		btnSubmit = (Button) findViewById(R.id.btnSubmit);
		tvHeader = (TextView) findViewById(R.id.tvHeader);
		ivBack = (ImageView) findViewById(R.id.ivBack);
		ivCart = (ImageView) findViewById(R.id.ivCart);
		String Customer_id = Prefs.getPreferences(ctx, "Customer_id");
		if (Customer_id != "") {
			edtedmailaddress.setText(Prefs.getPreferences(ctx, "email"));
			edtfirstname.setText(Prefs.getPreferences(ctx, "firstname"));
			edtlastname.setText(Prefs.getPreferences(ctx, "lastname"));
			edtphoneno.setText(Prefs.getPreferences(ctx, "telephone"));
		} else {

		}

	}

	@Override
	public void httpResponse(JSONObject jsonObject, String tag, int responceCode) {
		// TODO Auto-generated method stub
		if (pd.isShowing()) {
			pd.dismiss();
		}

		if (jsonObject != null) {
			Log.e("Responce Forgot pass", "" + jsonObject);

			try {
				String status = jsonObject.getString("status");
				if (status.equals("success")) {
					Toast.makeText(ctx, "" + jsonObject.getString("message"), 3)
							.show();
					this.finish();

				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {

			}
		}

		// 06-08 14:53:32.965: E/Contact Us(9748):
		// {"status":"success","message":"Mail Successfully Send"}

	}

	private String postRequestOfContact() {
		String request = "{\"firstname\":\""
				+ edtfirstname.getText().toString() + "\",\"lastname\":\""
				+ edtlastname.getText().toString() + "\",\"phone\":\""
				+ edtphoneno.getText().toString() + "\",\"email\":\""
				+ edtedmailaddress.getText().toString() + "\",\"sunject\":\""
				+ edtsubject.getText().toString() + "\",\"message\":\""
				+ edtDescrp.getText().toString() + "\"}";

		return request;
	}
}
