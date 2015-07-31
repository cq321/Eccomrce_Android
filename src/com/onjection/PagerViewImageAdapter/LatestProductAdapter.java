package com.onjection.PagerViewImageAdapter;

import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.ordervenue.android.R;import com.onjection.opencart.model.LatestProductModel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LatestProductAdapter extends BaseAdapter {

	private Context ctx;
	private LayoutInflater inflater;
	private List<LatestProductModel> latestproducts;
	DisplayImageOptions options;
	ImageLoader imageLoader;
	public LatestProductAdapter(Context ctx, List<LatestProductModel> latestproducts) {
		this.ctx = ctx;
		this.latestproducts = latestproducts;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return latestproducts.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return latestproducts.get(arg0);
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
			convertView = inflater.inflate(R.layout.latestproductcustom, null);

		TextView dealproductname = (TextView) convertView
				.findViewById(R.id.dealproductname);
		TextView productprice = (TextView) convertView
				.findViewById(R.id.productprice);
		TextView tvline = (TextView) convertView
				.findViewById(R.id.tvLine);
		
		TextView prductspecialprice = (TextView) convertView
				.findViewById(R.id.prductspecialprice);
		ImageView productdealimg = (ImageView) convertView
				.findViewById(R.id.productdealimg);
		final LatestProductModel latestProductlists=latestproducts.get(position);
//		ImageLoader img = new ImageLoader(ctx);
//		img.DisplayImage(latestProductlists.getImage(), productdealimg);
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
			imageLoader. displayImage(latestProductlists.getImage(), productdealimg, options);
			if (latestProductlists.getPrice().equals("")) {
				tvline.setVisibility(View.GONE);
			}
		dealproductname.setText(latestProductlists.getName());
		productprice.setText(latestProductlists.getPrice());
		if (latestProductlists.getReward().equals("")) {
			prductspecialprice.setVisibility(View.GONE);
		}else{
		prductspecialprice.setText(latestProductlists.getReward());
		}
		return convertView;
	}
}
