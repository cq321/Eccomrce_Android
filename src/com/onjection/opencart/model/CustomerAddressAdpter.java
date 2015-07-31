package com.onjection.opencart.model;

import java.util.List;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.onjection.opencart.Utilz.Prefs;
import com.onjection.opencart.Utilz.ServiceHandler;
import com.onjection.opencart.Utilz.Urls;
import com.ordervenue.android.AddNewAddress;
import com.ordervenue.android.R;

public class CustomerAddressAdpter extends BaseAdapter {
	private Context ctx;
	private LayoutInflater inflater;
	int id;
	private List<CustmorAddressModel> customeaddress;
	String address_id, customer_id, Message;
	String status;ProgressDialog pd;
	int itm_position;
	public CustomerAddressAdpter(Context ctx,
			   List<CustmorAddressModel> customeaddress, int id) {
			  this.ctx = ctx;
			  this.customeaddress = customeaddress;
			  this.id = id;
			 }

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return customeaddress.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return customeaddress.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (inflater == null)
			inflater = (LayoutInflater) ctx
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null)
			convertView = inflater.inflate(R.layout.address_list, null);
		TextView txtName = (TextView) convertView.findViewById(R.id.txtName);
		TextView txtAdd = (TextView) convertView.findViewById(R.id.txtAdd);
		TextView txtLandMark = (TextView) convertView
				.findViewById(R.id.txtLandMark);
		TextView txtCity_Pin = (TextView) convertView
				.findViewById(R.id.txtCity_Pin);
		LinearLayout llEdit = (LinearLayout) convertView
				.findViewById(R.id.llEdit);
		LinearLayout llDelete = (LinearLayout) convertView
				.findViewById(R.id.llDelete);
		LinearLayout btneditremove = (LinearLayout) convertView
			    .findViewById(R.id.btneditremove);
			  if (id == 2) {
			   btneditremove.setVisibility(View.GONE);
			  }
		TextView txtState = (TextView) convertView.findViewById(R.id.txtState);
		TextView txtMob = (TextView) convertView.findViewById(R.id.txtMob);
		final CustmorAddressModel cutomeraddresdetails = customeaddress
				.get(position);
		StringBuffer name = new StringBuffer();
		name.append(cutomeraddresdetails.getFirstname()).append(" ")
				.append(cutomeraddresdetails.getLastname());
		txtName.setText(name);
		txtState.setText(cutomeraddresdetails.getState());
		txtCity_Pin.setText(cutomeraddresdetails.getPostcode());
		String addres = cutomeraddresdetails.getAddress_1().concat(
				cutomeraddresdetails.getAddress_2());
		txtAdd.setText(addres);
		txtLandMark.setText(cutomeraddresdetails.getCity());
		llEdit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				 Intent intentEdit = new Intent(ctx, AddNewAddress.class);
				    intentEdit.putExtra("Address", "EditOldAddress");
				    intentEdit.putExtra("edtF_Name",
				      cutomeraddresdetails.getFirstname());
				    intentEdit.putExtra("edtState", cutomeraddresdetails.getState());
				    intentEdit.putExtra("edtCity", cutomeraddresdetails.getCity());
				    intentEdit.putExtra("edtPincode",
				      cutomeraddresdetails.getPostcode());
				    intentEdit.putExtra("edtComp",
				      cutomeraddresdetails.getCompany());
				    intentEdit.putExtra("edtAddress2",
				      cutomeraddresdetails.getAddress_2());
				    intentEdit.putExtra("edtAddress1",
				      cutomeraddresdetails.getAddress_1());
				    intentEdit.putExtra("edtL_Name",
				      cutomeraddresdetails.getLastname());
				    intentEdit.putExtra("address_id",
				      cutomeraddresdetails.getAddress_id());
				    ctx.startActivity(intentEdit);
				    ((Activity)ctx).finish();
			}
		});
		llDelete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// customer_id=1&address_id=5
				address_id = cutomeraddresdetails.getAddress_id();
				customer_id = Prefs.getPreferences(ctx, "Customer_id");
				itm_position=position;
				new DeleteAddress().execute();
				
				

			}
		});
		return convertView;
	}

	public class DeleteAddress extends AsyncTask<Void, Void, Void> {

		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			pd = new ProgressDialog(ctx);
			pd.setMessage("Please wait...");
			pd.setCancelable(false);
			pd.show();

		}

		protected Void doInBackground(Void... arg0) {
			// Creating service handler class instance
			ServiceHandler sh = new ServiceHandler();
			// Making a request to url and getting response

			String jsonStr = sh.makeServiceCall(Urls.DeleteAddressApi
					+ customer_id + "&address_id=" + address_id,
					ServiceHandler.GET);
			if (jsonStr != null) {
				Log.e("Responce Add New Address", "" + jsonStr);

				try {
					JSONObject jsonObject = new JSONObject(jsonStr);
					 status = jsonObject.optString("status");
					if (status.equals("success")) {
						Message = jsonObject.optString("message");

					} else {
//					  {"status":"success","message":"Successfully Deleted"}

					}
				} catch (Exception ex) {
					ex.printStackTrace();

				}
			}
			return null;
		}
//		public void updateResults(ArrayList<CustmorAddressModel> results) {
//			customeaddress = results;
//	        //Triggers the list update
//	        notifyDataSetChanged();
//	    }
		@SuppressLint("ShowToast")
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if (pd.isShowing()) {
				pd.dismiss();
			}
			if (status.equals("success")) {
				Toast.makeText(ctx, ""+Message, 2).show();
				
				customeaddress.remove(itm_position);
				notifyDataSetChanged();
			}

		}
	}
}
