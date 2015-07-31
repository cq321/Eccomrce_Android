package com.onjection.PagerViewImageAdapter;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.onjection.opencart.model.MyOrderModel;
import com.ordervenue.android.Myorderid;
import com.ordervenue.android.R;

public class MyOrderAdpter extends BaseAdapter {

	private Context ctx;
	private LayoutInflater inflater;
	private List<MyOrderModel> myorderproducts;

	public MyOrderAdpter(Context ctx, List<MyOrderModel> myorderproducts) {
		this.ctx = ctx;
		this.myorderproducts = myorderproducts;
	}

	@Override
	public int getCount() {
		return myorderproducts.size();
	}

	@Override
	public Object getItem(int arg0) {
		return myorderproducts.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (inflater == null)
			inflater = (LayoutInflater) ctx
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null)
			convertView = inflater.inflate(R.layout.myorderlist, null);
		TextView orderid = (TextView) convertView.findViewById(R.id.orderid);
		TextView ordertotal = (TextView) convertView
				.findViewById(R.id.ordertotal);
		TextView orderdate = (TextView) convertView
				.findViewById(R.id.orderdate);
		/*
		 * TextView tvtitle1 = (TextView)
		 * convertView.findViewById(R.id.tvtitle1); TextView tvvalue1 =
		 * (TextView) convertView.findViewById(R.id.tvvalue1);
		 */

		/*
		 * LinearLayout lineartotallist = (LinearLayout) convertView
		 * .findViewById(R.id.lineartotallist);
		 */

		final MyOrderModel myorderproductsdetails = myorderproducts
				.get(position);

		orderid.setText(myorderproductsdetails.getOrder_id());
		ordertotal.setText(myorderproductsdetails.getTotal());
		orderdate.setText(myorderproductsdetails.getDate_added());
		/*
		 * orderid.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) {
		 * 
		 * 
		 * } });
		 */
		/*
		 * tvProductName.setText(myorderproductsdetails.getMyorderproduct()
		 * .get(position).getName());
		 * tvQuantity.setText(myorderproductsdetails.getMyorderproduct()
		 * .get(position).getQuantity());
		 * tvPrice.setText(myorderproductsdetails.getMyorderproduct()
		 * .get(position).getPrice());
		 * tvTotal.setText(myorderproductsdetails.getMyorderproduct()
		 * .get(position).getTotal());
		 */

		/*
		 * for (int i = 0; i < myorderproductsdetails.getMyodertotals().size();
		 * i++) { LinearLayout lila = new LinearLayout(ctx);
		 * lila.setOrientation(LinearLayout.HORIZONTAL); LayoutParams lparams =
		 * new LayoutParams(LayoutParams.WRAP_CONTENT,
		 * LayoutParams.WRAP_CONTENT);
		 * 
		 * TextView tvtitle1 = new TextView(ctx); tvtitle1.setTextSize(16);
		 * TextView tvvalue1 = new TextView(ctx); tvvalue1.setTextSize(18); //
		 * lparams.gravity= Gravity.CENTER; tvtitle1.setLayoutParams(lparams);
		 * tvtitle1.setGravity(Gravity.CENTER_VERTICAL |
		 * Gravity.CENTER_HORIZONTAL); tvvalue1.setLayoutParams(lparams);
		 * tvvalue1.setGravity(Gravity.CENTER_VERTICAL |
		 * Gravity.CENTER_HORIZONTAL);
		 * tvtitle1.setText(myorderproductsdetails.getMyodertotals().get(i)
		 * .getTitle());
		 * tvvalue1.setText(myorderproductsdetails.getMyodertotals().get(i)
		 * .getValue()); lila.addView(tvtitle1); lila.addView(tvvalue1);
		 * ((LinearLayout) linearlayoutmain).addView(lila); // ((LinearLayout)
		 * linearlayoutmain).addView(tvvalue1); }
		 */
		return convertView;
	}
}
