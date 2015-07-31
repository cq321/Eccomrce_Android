package com.ordervenue.android;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ThankYou extends Activity {
	Button btnOk;
	TextView tvOrderId, tvAmount, tvTxnID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		setContentView(R.layout.activity_thank_you);
		Bundle b = getIntent().getExtras();

		tvAmount = (TextView) findViewById(R.id.tvAmount);
		tvOrderId = (TextView) findViewById(R.id.tvOrderID);
		tvTxnID = (TextView) findViewById(R.id.tvTxnID);

		tvAmount.setText("" + b.getString("amount"));
		tvOrderId.setText("" + b.getString("orderId"));
		tvTxnID.setText("" + b.getString("txnid"));
		
		btnOk = (Button) findViewById(R.id.btnOk);
		btnOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = getBaseContext().getPackageManager()
						.getLaunchIntentForPackage(
								getBaseContext().getPackageName());
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
ThankYou.this.finish();
			}
		});
	}

}
