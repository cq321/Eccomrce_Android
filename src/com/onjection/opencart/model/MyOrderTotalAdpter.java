package com.onjection.opencart.model;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ordervenue.android.R;

public class MyOrderTotalAdpter extends BaseAdapter {

	private Context ctx;
	private LayoutInflater inflater;
	private List<MyOrderTotalsModel> totalproduct;

	public MyOrderTotalAdpter(Context ctx, List<MyOrderTotalsModel> totalproduct) {
		this.ctx = ctx;
		this.totalproduct = totalproduct;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return totalproduct.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return totalproduct.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (inflater == null)
			inflater = (LayoutInflater) ctx
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null)
			convertView = inflater.inflate(R.layout.listitem_total, null);

		TextView tvtitle = (TextView) convertView.findViewById(R.id.tvtitle);
		TextView tvvalue = (TextView) convertView.findViewById(R.id.tvvalue);

		final MyOrderTotalsModel totalproductdetails = totalproduct
				.get(position);
		tvvalue.setText(totalproductdetails.getValue());
		tvtitle.setText(totalproductdetails.getTitle());

		return convertView;
	}
}
