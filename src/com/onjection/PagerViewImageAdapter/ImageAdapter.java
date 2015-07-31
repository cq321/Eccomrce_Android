package com.onjection.PagerViewImageAdapter;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.ordervenue.android.Firstfragment;
import com.ordervenue.android.HomeFragment;
import com.ordervenue.android.R;

@SuppressLint("ShowToast")
public class ImageAdapter extends PagerAdapter {
	Context context;
	public ArrayList<ViewPagerAdpterModel> arrURL = new ArrayList<ViewPagerAdpterModel>();
	DisplayImageOptions options;
	ImageLoader imageLoader;
	Fragment fragment = null;

	public ImageAdapter(Context context, ArrayList<ViewPagerAdpterModel> urls) {
		this.context = context;
		this.arrURL = urls;
	}

	@Override
	public int getCount() {
		return arrURL.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == ((ImageView) object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, final int position) {
		ImageView imageView = new ImageView(context);
		imageView.setScaleType(ImageView.ScaleType.FIT_XY);
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(context));
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_launcher)
				.showImageForEmptyUri(R.drawable.ic_launcher)
				.showImageOnFail(R.drawable.ic_launcher).cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true)
				.imageScaleType(ImageScaleType.EXACTLY)
				.bitmapConfig(Bitmap.Config.ARGB_8888).build();
		imageLoader.displayImage(arrURL.get(position).getImage(), imageView,
				options);
		((ViewPager) container).addView(imageView, 0);
		imageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) { String catergoryid = arrURL.get(position).getCategory_id();
		    String ChildName = arrURL.get(position).getTitle();
		    fragment = new Firstfragment();
		    final Bundle bundle = new Bundle();
		    bundle.putString("ctaegory_id", catergoryid);
		    // Toast.makeText(context, "" + catergoryid, 10).show();
		    bundle.putString("ChildName", ChildName);
		    fragment.setArguments(bundle);
		    FragmentActivity activity = (FragmentActivity) context;

		    activity.getSupportFragmentManager().beginTransaction()
		      .replace(R.id.content_frame, fragment).commit();
		    /*
		     * context.sendBroadcast(new Intent("call.myfragment.action")
		     * .putExtra("catergoryid", catergoryid));
		     */}
		});

		return imageView;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		((ViewPager) container).removeView((ImageView) object);
	}

}
