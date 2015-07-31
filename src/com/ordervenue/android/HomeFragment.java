package com.ordervenue.android;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.meetme.android.horizontallistview.HorizontalListView;
import com.onjection.PagerViewImageAdapter.DealProductAdapter;
import com.onjection.PagerViewImageAdapter.ImageAdapter;
import com.onjection.PagerViewImageAdapter.LatestProductAdapter;
import com.onjection.PagerViewImageAdapter.ViewPagerAdpterModel;
import com.onjection.ServerTask.ConnectionDector;
import com.onjection.opencart.Utilz.ServiceHandler;
import com.onjection.opencart.Utilz.Urls;
import com.onjection.opencart.model.DealProduct;
import com.onjection.opencart.model.LatestProductModel;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressLint("InflateParams")
public class HomeFragment extends Fragment {
	ViewPager viewPager;
	Context ctx;
	int currentPage;
	Timer swipeTime;
	ProgressDialog pd;
	HorizontalListView dealproductlist, latestproductlist, hvFeaturedProduct;
	ArrayList<String> arrStr = new ArrayList<String>();
	ConnectionDector cd;
	ArrayList<ViewPagerAdpterModel> arrSliderImgUrls = new ArrayList<ViewPagerAdpterModel>();
	private ArrayList<DealProduct> DealProductlist = new ArrayList<DealProduct>();
	private ArrayList<LatestProductModel> LatestProductlist = new ArrayList<LatestProductModel>();
	private ArrayList<LatestProductModel> FeaturedProductlist = new ArrayList<LatestProductModel>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.activity_main, null);
		ctx = getActivity();
		viewPager = (ViewPager) rootView.findViewById(R.id.view_pager);
		dealproductlist = (HorizontalListView) rootView
				.findViewById(R.id.HorizontalListView);
		latestproductlist = (HorizontalListView) rootView
				.findViewById(R.id.HorizontalListView1);
		hvFeaturedProduct = (HorizontalListView) rootView
				.findViewById(R.id.HorizontalListView2);
		LinearLayout llSearch = (LinearLayout) rootView
				.findViewById(R.id.llSearch);
		llSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getActivity(), SearchActivity.class);
				startActivity(i);

			}
		});
		TextView tvSearch = (TextView) rootView.findViewById(R.id.tvSerch);
		tvSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getActivity(), SearchActivity.class);
				startActivity(i);

			}
		});
		if (arrSliderImgUrls.size() > 0 && DealProductlist.size() > 0
				&& LatestProductlist.size() > 0) {

		} else {

			Log.e("Slider api hiting", "true");
			new GetSlider().execute();

		}
		final Handler handler = new Handler();
		final Runnable Update = new Runnable() {
			public void run() {
				if (currentPage == 3) {
					currentPage = 0;
				}
				viewPager.setCurrentItem(currentPage++, true);
			}
		};

		swipeTime = new Timer();
		swipeTime.schedule(new TimerTask() {

			@Override
			public void run() {
				handler.post(Update);
			}
		}, 2500, 5000);

		dealproductlist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String product_id = DealProductlist.get(position)
						.getProduct_id();
				Intent inetntproductdetails = new Intent(ctx,
						ProductDetails.class);
				inetntproductdetails.putExtra("Key_Product_id", product_id);
				startActivity(inetntproductdetails);
			}
		});
		latestproductlist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String product_id = LatestProductlist.get(position)
						.getProduct_id();
				Intent inetntproductdetails = new Intent(ctx,
						ProductDetails.class);
				inetntproductdetails.putExtra("Key_Product_id", product_id);
				startActivity(inetntproductdetails);
			}
		});
		hvFeaturedProduct.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String product_id = FeaturedProductlist.get(position)
						.getProduct_id();
				Intent inetntproductdetails = new Intent(ctx,
						ProductDetails.class);
				inetntproductdetails.putExtra("Key_Product_id", product_id);
				startActivity(inetntproductdetails);
			}
		});

		return rootView;
	}

	public class GetSlider extends AsyncTask<Void, Void, Void> {

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
			String jsonStr = sh.makeServiceCall(Urls.SlideShowuApi,
					ServiceHandler.GET);

			Log.e("Response: ", "> " + jsonStr);

			if (jsonStr != null) {
				try {
					JSONObject jsonObject = new JSONObject(jsonStr);
					Log.e("Slider responce", "" + jsonObject);
					JSONArray jsonproductarray = jsonObject
							.optJSONArray("slider");
					for (int i = 0; i < jsonproductarray.length(); i++) {
						/*
						 * arrSliderImgUrls.add(jsonObject.optJSONArray("slider")
						 * .optJSONObject(i).optString("image"));
						 */
						JSONObject jsonobjectslider = jsonproductarray
								.getJSONObject(i);
						ViewPagerAdpterModel viewPagerAdpterModel = new ViewPagerAdpterModel(
								jsonobjectslider.optString("banner_image_id"),
								jsonobjectslider.optString("banner_id"),
								jsonobjectslider.optString("image"),
								jsonobjectslider.optString("sort_order"),
								jsonobjectslider.optString("title"),
								jsonobjectslider.optString("category_id"));
						arrSliderImgUrls.add(viewPagerAdpterModel);
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}

			} /* else if (jsondealproduct != null) { */

			/* } */else {

			}
			return null;
		}

		@SuppressLint("ShowToast")
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if (pd.isShowing())
				pd.dismiss();
			ImageAdapter adapter = new ImageAdapter(getActivity(),
					arrSliderImgUrls);

			viewPager.setAdapter(adapter);
			Log.e("Dealproducts api hiting", "true");
			new GetDeal().execute();
		}
	}

	public class GetDeal extends AsyncTask<Void, Void, Void> {

		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			// pd = new ProgressDialog(ctx);
			// pd.setMessage("Please wait...");
			// pd.setCancelable(false);
			// pd.show();

		}

		protected Void doInBackground(Void... arg0) {
			// Creating service handler class instance
			ServiceHandler sh = new ServiceHandler();
			// Making a request to url and getting response

			String jsondealproduct = sh.makeServiceCall(Urls.GetDealPrdouct,
					ServiceHandler.GET);

			Log.e("Response:jsondealproduct ", "> " + jsondealproduct);

			if (jsondealproduct != null) {

				try {
					JSONObject jsonobjectdeal = new JSONObject(jsondealproduct);
					Log.e("jsondealproduct: ", "> " + jsonobjectdeal);

					JSONArray jsonproductarray = jsonobjectdeal
							.optJSONArray("product");
					for (int i = 0; i < jsonproductarray.length(); i++) {
						JSONObject jsonobject = jsonproductarray
								.getJSONObject(i);
						DealProduct dealProduct = new DealProduct(
								jsonobject.optString("product_id"),
								jsonobject.optString("model"),
								jsonobject.optString("image"),
								jsonobject.optString("price"),
								jsonobject.optString("special"),
								jsonobject.optString("name"));
						DealProductlist.add(dealProduct);

					}
				} catch (Exception ex) {

				}

			}
			return null;
		}

		@SuppressLint("ShowToast")
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// if (pd.isShowing())
			// pd.dismiss();

			DealProductAdapter dealProductAdapter = new DealProductAdapter(ctx,
					DealProductlist);

			dealproductlist.setAdapter(dealProductAdapter);
			Log.e("Latetst products api hiting", "true");
			new GetLatest().execute();

		}
	}

	public class GetLatest extends AsyncTask<Void, Void, Void> {

		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			// pd = new ProgressDialog(ctx);
			// pd.setMessage("Please wait...");
			// pd.setCancelable(false);
			// pd.show();

		}

		protected Void doInBackground(Void... arg0) {
			// Creating service handler class instance
			ServiceHandler sh = new ServiceHandler();

			String jsonlatestproduct = sh.makeServiceCall(
					Urls.GetLatestproduct, ServiceHandler.GET);

			Log.e("Response: GetLatestproduct", "> " + jsonlatestproduct);

			if (jsonlatestproduct != null) {

				try {
					JSONObject jsonobjectlatestproduct = new JSONObject(
							jsonlatestproduct);
					Log.e("jsondealproduct: ", "> " + jsonobjectlatestproduct);

					JSONArray jsonlatestproductarray = jsonobjectlatestproduct
							.optJSONArray("product");
					System.out.println(jsonlatestproductarray.length());
					for (int i = 0; i < jsonlatestproductarray.length(); i++) {
						JSONObject jsonobjectltest = jsonlatestproductarray
								.getJSONObject(i);
						LatestProductModel latestProduct = new LatestProductModel(
								jsonobjectltest.optString("product_id"),
								jsonobjectltest.optString("model"),
								jsonobjectltest.optString("quantity"),
								jsonobjectltest.optString("stock_status_id"),
								jsonobjectltest.optString("image"),
								jsonobjectltest.optString("price"),
								jsonobjectltest.optString("name"),
								jsonobjectltest.optString("special"));
						LatestProductlist.add(latestProduct);

					}
				} catch (Exception ex) {

				}

			} /* else if (jsondealproduct != null) { */

			/* } */else {

			}
			return null;
		}

		@SuppressLint("ShowToast")
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// if (pd.isShowing())
			// pd.dismiss();

			LatestProductAdapter latestProductAdapter = new LatestProductAdapter(
					ctx, LatestProductlist);

			latestproductlist.setAdapter(latestProductAdapter);
			Log.e("Featured product api hiting", "true");
			new GetFeatured().execute();
		}
	}

	public class GetFeatured extends AsyncTask<Void, Void, Void> {

		protected void onPreExecute() {
			super.onPreExecute();
			// // Showing progress dialog
			// pd = new ProgressDialog(ctx);
			// pd.setMessage("Please wait...");
			// pd.setCancelable(false);
			// pd.show();

		}

		protected Void doInBackground(Void... arg0) {
			// Creating service handler class instance
			ServiceHandler sh = new ServiceHandler();
			// Making a request to url and getting response
			;
			String jsonFeaturdeproduct = sh.makeServiceCall(
					Urls.featuredProducts, ServiceHandler.GET);
			Log.e("Response: jsonFeaturdeproduct", "> " + jsonFeaturdeproduct);

			if (jsonFeaturdeproduct != null) {

				try {
					JSONObject jsonobjectlatestproduct = new JSONObject(
							jsonFeaturdeproduct);
					Log.e("jsonfeaturedproduct: ", "> "
							+ jsonobjectlatestproduct);

					JSONArray jsonlatestproductarray = jsonobjectlatestproduct
							.optJSONArray("product");
					System.out.println(jsonlatestproductarray.length());
					for (int i = 0; i < jsonlatestproductarray.length(); i++) {
						JSONObject jsonobjectltest = jsonlatestproductarray
								.getJSONObject(i);
						LatestProductModel latestProduct = new LatestProductModel(
								jsonobjectltest.optString("product_id"),
								jsonobjectltest.optString("model"),
								jsonobjectltest.optString("quantity"),
								jsonobjectltest.optString("stock_status_id"),
								jsonobjectltest.optString("image"),
								jsonobjectltest.optString("price"),
								jsonobjectltest.optString("name"),
								jsonobjectltest.optString("special"));
						FeaturedProductlist.add(latestProduct);

					}
				} catch (Exception ex) {

				}

			} else {

			}
			return null;
		}

		@SuppressLint("ShowToast")
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// if (pd.isShowing())
			// pd.dismiss();

			LatestProductAdapter latestProductAdapter2 = new LatestProductAdapter(
					ctx, FeaturedProductlist);
			hvFeaturedProduct.setAdapter(latestProductAdapter2);
		}
	}

}
