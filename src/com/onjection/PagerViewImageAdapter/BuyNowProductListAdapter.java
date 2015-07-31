package com.onjection.PagerViewImageAdapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.onjection.lazylist.ImageLoader;
import com.onjection.opencart.model.MyCartListModel;
import com.ordervenue.android.R;

public class BuyNowProductListAdapter extends BaseAdapter {

	private Context ctx;
	private LayoutInflater inflater;
	private List<MyCartListModel> mycartproducts;
	int itemposition;

	public BuyNowProductListAdapter(Context ctx,
			List<MyCartListModel> mycartproducts) {
		this.ctx = ctx;
		this.mycartproducts = mycartproducts;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mycartproducts.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mycartproducts.get(arg0);
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
			convertView = inflater.inflate(R.layout.buynowlistadpter, null);

		TextView txtProductPrice = (TextView) convertView
				.findViewById(R.id.txtProductPrice);
		TextView txtProductName = (TextView) convertView
				.findViewById(R.id.txtProductName);
		TextView txtProductQuantity = (TextView) convertView
				.findViewById(R.id.txtProductQuantity);
		ImageView ivProductImage = (ImageView) convertView
				.findViewById(R.id.ivProductImage);
		LinearLayout llRemoveproduct = (LinearLayout) convertView
				.findViewById(R.id.llRemoveproduct);
		llRemoveproduct.setVisibility(View.GONE);
		final MyCartListModel mycartproductdetails = mycartproducts
				.get(position);
		ImageLoader img = new ImageLoader(ctx);
		img.DisplayImage(mycartproductdetails.getImage(), ivProductImage);
		txtProductPrice.setText(mycartproductdetails.getPrice());
		txtProductName.setText(mycartproductdetails.getName());
		txtProductQuantity.setText(mycartproductdetails.getQuantity());

		return convertView;
	}

}
