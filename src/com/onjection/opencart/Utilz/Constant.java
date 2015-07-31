package com.onjection.opencart.Utilz;

import android.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class Constant {
	public static final String tagSlideview = "slideview";
	public static final String tagDealProducts = "dealproducts";
	public static final String tagLatestProducts = "Latestproducts";
	public static final String tagLogin = "Login";
	public static final String tagAuthentication = "Authentication";
	public static final String tagregistraion = "Registration";
	public static final String tagmycart = "Cart";
	public static final String AddNewAddresstag = "AddnewAddress";
	public static final String UpdateNewAddresstag = "UpdateAddress";
	public static final String PlaceOrderString = "placeorder";
	public static final String ShipingString = "Shiping";
	public static final String ContactUs = "contactUs";
	public static final String Confirmorder = "confirmorder";
	public static final String forgotpassword = "forgotpassword";
	public static final String ConfirmSucess = "paymentSucess";

	public static void hideKeyboard(Activity activity) {
		try {
			InputMethodManager inputManager = (InputMethodManager) activity
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			// check if no view has focus:
			View view = activity.getCurrentFocus();
			if (view != null) {
				inputManager.hideSoftInputFromWindow(view.getWindowToken(),
						InputMethodManager.HIDE_NOT_ALWAYS);
			}
		} catch (Exception e) {
			// Ignore exceptions if any
			Log.e("KeyBoardUtil", e.toString(), e);
		}
	}

	public static boolean isInternetConnected(Context c) {
		ConnectivityManager cm = (ConnectivityManager) c
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		return cm.getActiveNetworkInfo() != null
				&& cm.getActiveNetworkInfo().isConnectedOrConnecting();
	}

	public static boolean isValidEmail1(CharSequence target) {
		if (target == null) {
			return false;
		} else {
			return android.util.Patterns.EMAIL_ADDRESS.matcher(target)
					.matches();
		}
	}

	public static void showAlertActivityWithTitle(String title, String message,
			final Context context) {

		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(message)
				.setTitle(title)
				.setCancelable(false)
				.setPositiveButton(R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {

								// context.finish();
							}
						});
		// AlertDialog alert = builder.create();

		try {
			builder.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void showAlertActivityWithTitleFinish(String title,
			String message, final Activity mActivity) {

		AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
		builder.setMessage(message)
				.setTitle(title)
				.setCancelable(false)
				.setPositiveButton(R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {

								mActivity.finish();
							}
						});
		// AlertDialog alert = builder.create();

		try {
			builder.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void showAlertActivity(String message,
			final Context thiscontext) {

		AlertDialog.Builder builder = new AlertDialog.Builder(thiscontext);
		builder.setMessage(message)
				.setCancelable(false)
				.setPositiveButton(R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {

								// context.finish();
							}
						});
		// AlertDialog alert = builder.create();

		try {
			builder.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
