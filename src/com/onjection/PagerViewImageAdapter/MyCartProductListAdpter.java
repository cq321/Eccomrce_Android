package com.onjection.PagerViewImageAdapter;

import java.util.List;

import com.onjection.lazylist.ImageLoader;
import com.ordervenue.android.R;import com.onjection.opencart.Utilz.Prefs;
import com.onjection.opencart.model.MyCartListModel;
import com.ordervenue.android.DBController;
import com.ordervenue.android.MyCart;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ShowToast")
public class MyCartProductListAdpter extends BaseAdapter {

	private Context ctx;
	private LayoutInflater inflater;
	private List<MyCartListModel> mycartproducts;
	int itempositionsssss;

	public MyCartProductListAdpter(Context ctx,
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
			convertView = inflater.inflate(R.layout.listitem_mycart, null);

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
		final MyCartListModel mycartproductdetails = mycartproducts
				.get(position);
		ImageLoader img = new ImageLoader(ctx);
		img.DisplayImage(mycartproductdetails.getImage(), ivProductImage);
		txtProductPrice.setText(mycartproductdetails.getPrice());
		txtProductName.setText(mycartproductdetails.getName());
		txtProductQuantity.setText(mycartproductdetails.getQuantity());
		llRemoveproduct.setOnClickListener(new OnClickListener() {
			@SuppressWarnings("static-access")
			@Override
			public void onClick(View v) {
				DBController controller = new DBController(ctx);
				int id = Integer.parseInt(mycartproductdetails.getProduct_id());
				controller.delete_byID(id);
				Toast.makeText(ctx, "Delete Product From Cart", 10).show();
				/*
				 * Intent i = new Intent(ctx, MyCart.class);
				 * ctx.startActivity(i);
				 */
				mycartproducts.remove(position);
				MyCart x = (MyCart) ctx;
				notifyDataSetChanged();
				// Prefs.setPreferences(ctx, "jsonstringproduct", "");
				x.getInstance().Databasefetchdata();
			}
		});
		return convertView;
	}

}
