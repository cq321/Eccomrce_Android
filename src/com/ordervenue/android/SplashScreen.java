package com.ordervenue.android;

import static com.androidhive.pushnotifications.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static com.androidhive.pushnotifications.CommonUtilities.EXTRA_MESSAGE;
import static com.androidhive.pushnotifications.CommonUtilities.SENDER_ID;

import java.util.ArrayList;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.androidhive.pushnotifications.AlertDialogManager;
import com.androidhive.pushnotifications.ConnectionDetector;
import com.androidhive.pushnotifications.GCMActivity;
import com.androidhive.pushnotifications.ServerUtilities;
import com.androidhive.pushnotifications.WakeLocker;
import com.google.android.gcm.GCMRegistrar;
import com.onjection.ServerTask.ServerDownload;
import com.onjection.ServerTask.ServerResponse;
import com.onjection.opencart.Utilz.Constant;
import com.onjection.opencart.model.CategoryModel;
import com.onjection.opencart.model.ChildInfo;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class SplashScreen extends Activity implements ServerResponse{
	String possibleEmail ;
	/*
	 * ViewPager viewPager;
	 */
	// Splash screen timer
	String email,name;
	long Delay = 5000;
	String language_id;
	ConnectionDetector cd;
	// Asyntask
	AsyncTask<Void, Void, Void> mRegisterTask;
	
	// Alert dialog manager
	AlertDialogManager alert = new AlertDialogManager();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.splashscreen);
		
		ServerDownload download = new ServerDownload(
				SplashScreen.this, "", SplashScreen.this,
				Constant.tagAuthentication);
		download.execute(postRequest());
		Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
		Account[] accounts = AccountManager.get(this).getAccounts();
		for (Account account : accounts) {
		    if (emailPattern.matcher(account.name).matches()) {
		       email = account.name;
		       
		    }
		}
		cd = new ConnectionDetector(getApplicationContext());

		// Check if Internet present
		if (!cd.isConnectingToInternet()) {
			// Internet Connection is not present
			alert.showAlertDialog(SplashScreen.this,
					"Internet Connection Error",
					"Please connect to working Internet connection", false);
			// stop executing code by return
			return;
		}
		
		// Getting name, email from intent
		
		
		name = "OrderVenue";
				
		
		// Make sure the device has the proper dependencies.
		GCMRegistrar.checkDevice(this);

		// Make sure the manifest was properly set - comment out this line
		// while developing the app, then uncomment it when it's ready.
		GCMRegistrar.checkManifest(this);

//		lblMessage = (TextView) findViewById(R.id.lblMessage);
		
		registerReceiver(mHandleMessageReceiver, new IntentFilter(
				DISPLAY_MESSAGE_ACTION));
		
		// Get GCM registration id
		final String regId = GCMRegistrar.getRegistrationId(this);

		// Check if regid already presents
		if (regId.equals("")) {
			// Registration is not present, register now with GCM			
			GCMRegistrar.register(this, SENDER_ID);
		} else {
			// Device is already registered on GCM
			if (GCMRegistrar.isRegisteredOnServer(this)) {
				// Skips registration.				
//				Toast.makeText(getApplicationContext(), "Already registered with GCM", Toast.LENGTH_LONG).show();
			} else {
				// Try to register again, but not in the UI thread.
				// It's also necessary to cancel the thread onDestroy(),
				// hence the use of AsyncTask instead of a raw thread.
				final Context context = this;
				Log.e("name and email", name+ "  " + email);
				mRegisterTask = new AsyncTask<Void, Void, Void>() {

					@Override
					protected Void doInBackground(Void... params) {
						// Register on our server
						// On server creates a new user
						ServerUtilities.register(context, name, email, regId);
						return null;
					}

					@Override
					protected void onPostExecute(Void result) {
						mRegisterTask = null;
					}

				};
				mRegisterTask.execute(null, null, null);
			}
		}	}

	@Override
	public void httpResponse(JSONObject jsonObject, String tag, int responceCode) {
		// TODO Auto-generated method stub
		Log.e("app auth", ""+jsonObject);
		
		if (jsonObject!=null) {
			try {
				String status = jsonObject.getString("status");
				if (status.equals("success")) {
					Intent myIntent = new Intent(SplashScreen.this, NevigationDrawer.class);

					startActivity(myIntent);
					finish();
				}else{
					new AlertDialog.Builder(this)
					.setTitle("App is expired")
					.setMessage("Please Contact App Developers")
					.setIcon(R.drawable.cross)
					.setPositiveButton(" OK ",
							new DialogInterface.OnClickListener() {
								public void onClick(
										DialogInterface dialog,
										int which) {
									// continue with delete
								}
							})
					// .setNegativeButton(android.R.string.no, new
					// DialogInterface.OnClickListener() {
					// public void onClick(DialogInterface dialog, int
					// which) {
					// // do nothing
					// }
					// })
			
					.show();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			Intent myIntent = new Intent(SplashScreen.this, NevigationDrawer.class);

			startActivity(myIntent);
			finish();
		}
		
	}
	String postRequest() {

		String request = "{\"app_id\":\"1"  
				+ "\",\"base_url\":\"www.ordervenue.com" 
				+ "\"}";
		Log.e("post request", request);
		return request;
}
	private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String newMessage = intent.getExtras().getString(EXTRA_MESSAGE);
			Log.e("notification msg ", newMessage);
			// Waking up mobile if it is sleeping
			WakeLocker.acquire(getApplicationContext());
			
			/**
			 * Take appropriate action on this message
			 * depending upon your app requirement
			 * For now i am just displaying it on the screen
			 * */
			
			// Showing received message
				
			Toast.makeText(getApplicationContext(), "New Message: " + newMessage, Toast.LENGTH_LONG).show();
			
			// Releasing wake lock
			WakeLocker.release();
		}
	};
	
	@Override
	protected void onDestroy() {
		if (mRegisterTask != null) {
			mRegisterTask.cancel(true);
		}
		try {
			unregisterReceiver(mHandleMessageReceiver);
			GCMRegistrar.onDestroy(this);
		} catch (Exception e) {
			Log.e("UnRegister Receiver Error", "> " + e.getMessage());
		}
		super.onDestroy();
	}
}
