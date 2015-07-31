package com.onjection.opencart.model;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.onjection.opencart.model.MyOrderProductDetails;
import com.ordervenue.android.R;

public class MyOrderProdcutAdpter extends BaseAdapter {

	private Context ctx;
	private LayoutInflater inflater;
	private List<MyOrderProductDetails> orderinfoproduct;

	public MyOrderProdcutAdpter(Context ctx,
			List<MyOrderProductDetails> orderinfoproduct) {
		this.ctx = ctx;
		this.orderinfoproduct = orderinfoproduct;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return orderinfoproduct.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return orderinfoproduct.get(arg0);
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
			convertView = inflater
					.inflate(R.layout.listitem_confirmorder, null);

		TextView tvProductName = (TextView) convertView
				.findViewById(R.id.tvProductName);
		TextView tvQuantity = (TextView) convertView
				.findViewById(R.id.tvQuantity);
		TextView tvPrice = (TextView) convertView.findViewById(R.id.tvPrice);
		TextView tvTotal = (TextView) convertView.findViewById(R.id.tvTotal);
		final MyOrderProductDetails orderinfoproductdetails = orderinfoproduct
				.get(position);
		tvProductName.setText(orderinfoproductdetails.getName());
		tvQuantity.setText(orderinfoproductdetails.getQuantity());
		tvPrice.setText(orderinfoproductdetails.getPrice());
		tvTotal.setText(orderinfoproductdetails.getTotal());

		return convertView;
	}
}
