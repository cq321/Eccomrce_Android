package com.onjection.PagerViewImageAdapter;

import java.util.ArrayList;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.ordervenue.android.R;
import com.onjection.opencart.model.CategoryProduct;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomGridViewAdapter extends ArrayAdapter<CategoryProduct> {
	ArrayList<CategoryProduct> data = new ArrayList<CategoryProduct>();
	Context context;

	int layoutResourceId;
	DisplayImageOptions options;
	ImageLoader imageLoader;

	public CustomGridViewAdapter(Context context, int layoutResourceId,

	ArrayList<CategoryProduct> data) {

		super(context, layoutResourceId, data);

		this.layoutResourceId = layoutResourceId;

		this.context = context;

		this.data = data;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View row = convertView;

		RecordHolder holder = null;

		if (row == null) {

			LayoutInflater inflater = ((Activity) context).getLayoutInflater();

			row = inflater.inflate(layoutResourceId, parent, false);

			holder = new RecordHolder();

			holder.txtTitle = (TextView) row.findViewById(R.id.dealproductname);
			holder.prductspecialprice = (TextView) row
					.findViewById(R.id.prductspecialprice);
			holder.txtTitle = (TextView) row.findViewById(R.id.dealproductname);
			holder.productprice = (TextView) row
					.findViewById(R.id.productprice);

			holder.imageItem = (ImageView) row
					.findViewById(R.id.productdealimg);
			holder.tvline = (TextView) row.findViewById(R.id.tvLine);
			row.setTag(holder);

		} else {

			holder = (RecordHolder) row.getTag();

		}

		CategoryProduct item = data.get(position);
		if (item.getSpecial().equals("")) {
			holder.tvline.setVisibility(View.GONE);
		}
		holder.txtTitle.setText(item.getName());
		holder.prductspecialprice.setText(item.getSpecial());
		holder.productprice.setText(item.getPrice());
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(context));
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_launcher)
				.showImageForEmptyUri(R.drawable.ic_launcher)
				.showImageOnFail(R.drawable.ic_launcher).cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true)
				.imageScaleType(ImageScaleType.EXACTLY)
				.bitmapConfig(Bitmap.Config.ARGB_8888).build();
		imageLoader.displayImage(item.getImage(), holder.imageItem, options);
		// img.DisplayImage(, );
		return row;

	}

	static class RecordHolder {

		TextView txtTitle, prductspecialprice, productprice, tvline;

		ImageView imageItem;

	}
}
