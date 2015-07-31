package com.onjection.opencart.Utilz;

import com.ordervenue.android.MyOrder;
import com.ordervenue.android.R;import com.ordervenue.android.ContactUs;
import com.ordervenue.android.LoginActivity;
import com.ordervenue.android.MyAddress;
import com.ordervenue.android.MyCart;
import com.ordervenue.android.NevigationDrawer;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

@SuppressLint("ShowToast")
public class MenuDropdownOnItemClickListener implements OnItemClickListener {

	String TAG = "DogsDropdownOnItemClickListener.java";
	NevigationDrawer mainActivity;
	String Customer_id;

	@Override
	public void onItemClick(AdapterView<?> arg0, View v, int arg2, long arg3) {

		// get the context and main activity to access variables
		Context mContext = v.getContext();
		mainActivity = ((NevigationDrawer) mContext);

		// add some animation when a list item was clicked
		Animation fadeInAnimation = AnimationUtils.loadAnimation(
				v.getContext(), android.R.anim.slide_in_left);
		fadeInAnimation.setDuration(10);
		v.startAnimation(fadeInAnimation);

		// dismiss the pop up
		mainActivity.popupWindowMenu.dismiss();

		// get the text and set it as the button text
		// String selectedItemText = ((TextView) v).getText().toString();
		// mainActivity.ivActionmenu.setText(selectedItemText);

		// get the id
		String selectedItemTag = ((TextView) v).getTag().toString();
		int slecteditemposition = Integer.parseInt(selectedItemTag);
		switch (slecteditemposition) {
		case 1:
			Customer_id = Prefs.getPreferences(mainActivity, "Customer_id");
			if (Customer_id != "") {
				openAskPopUp();
			} else {
				Intent intent = new Intent(mainActivity, LoginActivity.class);
				mainActivity.startActivity(intent);
			}

			break;
		case 2:
			Intent intentcontactus = new Intent(mainActivity, ContactUs.class);
			mainActivity.startActivity(intentcontactus);
			break;
		case 3:
			Customer_id = Prefs.getPreferences(mainActivity, "Customer_id");
			if (Customer_id != "") {
				Intent intent = new Intent(mainActivity, MyAddress.class);
				mainActivity.startActivity(intent);
			} else {

			}
			break;
		case 4:
			Customer_id = Prefs.getPreferences(mainActivity, "Customer_id");
			if (Customer_id != "") {
				Intent intent = new Intent(mainActivity, MyCart.class);
				mainActivity.startActivity(intent);
			} else {

			}
			break;
		case 5:
			Customer_id = Prefs.getPreferences(mainActivity, "Customer_id");
			if (Customer_id != "") {
				   MyOrder.myorderlist.clear();
				Intent intent = new Intent(mainActivity, MyOrder.class);
				mainActivity.startActivity(intent);
			} else {

			}
			/*Intent sharingIntent = new Intent(
					android.content.Intent.ACTION_SEND);
			sharingIntent.setType("text/plain");
			String shareBody = "Here is the share content body";
			sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
					"Subject Here");
			sharingIntent
					.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
			mainActivity.startActivity(Intent.createChooser(sharingIntent,
					"Share via"));*/
			break;
		case 6:
			Toast.makeText(mainActivity,
					"After Publish upon playstore Open it", 10).show();
			break;
		case 7:
			break;
		case 8:
			break;

		default:
			break;
		}

	}

	private void openAskPopUp() {
		final Dialog dialog = new Dialog(mainActivity);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

		dialog.setContentView(R.layout.alert_dialog);
		dialog.setCancelable(true);
		TextView txtTitle = (TextView) dialog.findViewById(R.id.txtTitle);
		txtTitle.setText("Do you really want to signout?");

		TextView txtDescription = (TextView) dialog
				.findViewById(R.id.txtDescription);
		txtDescription.setVisibility(View.GONE);

		Button btnOk = (Button) dialog.findViewById(R.id.buttonOk);
		btnOk.setText("Yes");
		btnOk.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Prefs.setPreferences(mainActivity, "Customer_id", "");
				/*
				 * user_Prefrences.setUser_id("");
				 * user_Prefrences.setUser_email("");
				 * user_Prefrences.setUserAdd("");
				 * user_Prefrences.setUserMob("");
				 * user_Prefrences.setUserName("");
				 * user_Prefrences.setUserComp("");
				 */
				// displayView(0);

				dialog.dismiss();
				Toast.makeText(mainActivity, "Signout successfully", 5).show();
				Intent intent = new Intent(mainActivity, NevigationDrawer.class);
				mainActivity.startActivity(intent);
				/*
				 * Intent ihome = new Intent(mainActivity,
				 * MainSearchHotel.class); startActivity(ihome);
				 */

			}
		});

		Button btnCancel = (Button) dialog.findViewById(R.id.buttonCancel);
		btnCancel.setText("No");
		btnCancel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// StudentDetail coloursModel = colourList.get(positionUpdated);

				dialog.dismiss();
			}
		});

		dialog.show();

	}

}
