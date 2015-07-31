package com.onjection.PagerViewImageAdapter;

import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.ordervenue.android.R;import com.onjection.opencart.model.DealProduct;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DealProductAdapter extends BaseAdapter {
	DisplayImageOptions options;
	ImageLoader imageLoader;
	private Context ctx;
	private LayoutInflater inflater;
	private List<DealProduct> dealproducts;

	public DealProductAdapter(Context ctx, List<DealProduct> dealproducts) {
		this.ctx = ctx;
		this.dealproducts = dealproducts;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return dealproducts.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return dealproducts.get(arg0);
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
			convertView = inflater.inflate(R.layout.listitemhorizontal, null);

		TextView dealproductname = (TextView) convertView
				.findViewById(R.id.dealproductname);
		TextView tvline = (TextView) convertView
				.findViewById(R.id.tvLine);
		TextView productprice = (TextView) convertView
				.findViewById(R.id.productprice);
		TextView prductspecialprice = (TextView) convertView
				.findViewById(R.id.prductspecialprice);
		ImageView productdealimg = (ImageView) convertView
				.findViewById(R.id.productdealimg);
		final DealProduct dealproductdetails = dealproducts.get(position);
//		ImageLoader img = new ImageLoader(ctx);
//		img.DisplayImage(dealproductdetails.getImage(), productdealimg);
		imageLoader = ImageLoader.getInstance();
	 	imageLoader.init(ImageLoaderConfiguration.createDefault(ctx));
			options = new DisplayImageOptions.Builder()
			.showImageOnLoading(R.drawable.ic_launcher)
			.showImageForEmptyUri(R.drawable.ic_launcher)
			.showImageOnFail(R.drawable.ic_launcher).cacheInMemory(true)
					.cacheOnDisk(true).considerExifParams(true)
					.imageScaleType(ImageScaleType.EXACTLY)
					.bitmapConfig(Bitmap.Config.ARGB_8888)
					.build();
			imageLoader. displayImage(dealproductdetails.getImage(), productdealimg, options);
		if (dealproductdetails.getPrice().equals("")) {
			tvline.setVisibility(View.GONE);
		}
		dealproductname.setText(dealproductdetails.getName());
		productprice.setText(dealproductdetails.getPrice());
		prductspecialprice.setText(dealproductdetails.getSpecial());
		return convertView;
	}
}
