package com.ordervenue.android;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.onjection.PagerViewImageAdapter.CustomGridViewAdapter;
import com.onjection.PagerViewImageAdapter.ListAdpterOfPriceFilter;
import com.onjection.opencart.Utilz.Constant;
import com.onjection.opencart.Utilz.ServiceHandler;
import com.onjection.opencart.Utilz.Urls;
import com.onjection.opencart.model.CategoryProduct;
import com.onjection.opencart.model.PriceFilter;
import com.onjection.opencart.model.ProductOptionModel;
import com.onjection.opencart.model.ProductOptionValue;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint({ "InflateParams", "ShowToast" })
public class Firstfragment extends Fragment implements OnScrollListener,
		OnClickListener {

	Context ctx;
	ListAdpterOfPriceFilter boxAdapter;
	ArrayList<PriceFilter> products = new ArrayList<PriceFilter>();
	ProgressDialog pd;
	String ctaegory_id, headerTitel;
	ArrayList<String> productimages = new ArrayList<String>();
	ProductOptionModel productOptionModel;
	private ArrayList<CategoryProduct> categoryproductlist = new ArrayList<CategoryProduct>();
	CategoryProduct categoryProduct;
	GridView gridView;
	CustomGridViewAdapter customGridAdapter;
	int page = 1, totalItem = 0;
	Button loadMore;
	LinearLayout linearlayoutsort, linearlayoutfilter, listviewfooter;
	String url;
	String loadingchk = "chk";
	TextView tvsortby;
	ListView lvsortbyproduct, listViewfilter;
	Dialog dialog, dialog1;
	String[] listSortproduct = { "Product A to Z", "Product Z to A",
			"Price Low to High", "Price High to Low" };
	String[] listfilterprice = { "Under 500", "501-1,000", "1,001-1,500",
			"1,501-2,000", "2,000 Above" };
	// String []
	static final int CUSTOM_DIALOG_ID = 0;
	String chk = "normal";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.products_list, null);
		ctx = getActivity();
		ctaegory_id = getArguments().getString("ctaegory_id");
		url = Urls.GetProductByCategorey + ctaegory_id + "&page=" + page
				+ "&limit=10";
		Log.e("URL ", Urls.GetProductByCategorey + ctaegory_id + "page=" + page
				+ "&limit=10");
		Log.e("ctaegory_id", "" + ctaegory_id);
		headerTitel = getArguments().getString("ChildName");
		((TextView) rootView.findViewById(R.id.tvHeader)).setText(headerTitel);
		tvsortby = (TextView) rootView.findViewById(R.id.tvsortby);
		linearlayoutsort = (LinearLayout) rootView
				.findViewById(R.id.linearlayoutsort);
		linearlayoutfilter = (LinearLayout) rootView
				.findViewById(R.id.linearlayoutfilter);
		loadMore = (Button) rootView.findViewById(R.id.loadmore);
		loadMore.setOnClickListener(this);
		listviewfooter = (LinearLayout) rootView
				.findViewById(R.id.listviewfooter);
		listviewfooter.setVisibility(View.GONE);
		// Toast.makeText(ctx, ctaegory_id, 5).show();
		gridView = (GridView) rootView.findViewById(R.id.gridview);
		gridView.setOnScrollListener(this);
		fillData();
		new Getproductcategory().execute();
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long arg3) {
				String product_id = categoryproductlist.get(position)
						.getProduct_id();
				Intent inetntproductdetails = new Intent(ctx,
						ProductDetails.class);
				inetntproductdetails.putExtra("Key_Product_id", product_id);
				startActivity(inetntproductdetails);
			}
		});
		linearlayoutsort.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Toast.makeText(ctx, "click", 10).show();
				if (dialog == null) {
					dialog = new Dialog(ctx);
					dialog.setCancelable(true);
					// dialog.setTitle("Choose Sort By");
					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					try {
						dialog.setContentView(R.layout.dialogofsortby);
					} catch (Exception e) {
						e.printStackTrace();
					}
					ImageView icon_close = (ImageView) dialog
							.findViewById(R.id.icon_close);

					lvsortbyproduct = (ListView) dialog
							.findViewById(R.id.lvsortbyproduct);
					ArrayAdapter<String> adapter

					= new ArrayAdapter<String>(ctx,

					android.R.layout.simple_list_item_1, listSortproduct);
					lvsortbyproduct.setAdapter(adapter);
					icon_close.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							dialog.dismiss();
						}
					});
					lvsortbyproduct
							.setOnItemClickListener(new OnItemClickListener() {

								@Override
								public void onItemClick(AdapterView<?> parent,
										View view, int position, long id) {
									tvsortby.setText(listSortproduct[position]);

									dialog.dismiss();
									if (listSortproduct[position]
											.equals("Product A to Z")) {
										chk = listSortproduct[position];
										page = 1;
										totalItem = 0;
										url = Urls.GetProductByCategorey
												+ ctaegory_id
												+ "&sort=pd.name&order=ASC"
												+ "&page=" + page + "&limit=20";
										categoryproductlist.clear();

										new Getproductcategory().execute();

									} else if (listSortproduct[position]
											.equals("Product Z to A")) {
										chk = listSortproduct[position];
										page = 1;
										totalItem = 0;
										url = Urls.GetProductByCategorey
												+ ctaegory_id
												+ "&sort=pd.name&order=DESC"
												+ "&page=" + page + "&limit=20";
										categoryproductlist.clear();

										new Getproductcategory().execute();

									} else if (listSortproduct[position]
											.equals("Price Low to High")) {
										chk = listSortproduct[position];
										page = 1;
										totalItem = 0;
										url = Urls.GetProductByCategorey
												+ ctaegory_id
												+ "&sort=p.price&order=ASC"
												+ "&page=" + page + "&limit=20";
										categoryproductlist.clear();

										new Getproductcategory().execute();

									} else if (listSortproduct[position]
											.equals("Price High to Low�?")) {
										chk = listSortproduct[position];
										page = 1;
										totalItem = 0;

										url = Urls.GetProductByCategorey
												+ ctaegory_id
												+ "&sort=p.price&order=DESC"
												+ "&page=" + page + "&limit=20";
										categoryproductlist.clear();

										new Getproductcategory().execute();

									}

								}
							});
				}
				dialog.show();

			}
		});
		linearlayoutfilter.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (dialog1 == null) {
					dialog1 = new Dialog(ctx);
					dialog1.setCancelable(true);
					dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
					try {
						dialog1.setContentView(R.layout.filterdailog);
					} catch (Exception e) {
						e.printStackTrace();
					}
					listViewfilter = (ListView) dialog1
							.findViewById(R.id.listViewfilter);
					Button btnok = (Button) dialog1.findViewById(R.id.btnok);
					ImageView icon_close = (ImageView) dialog1
							.findViewById(R.id.icon_close);

					boxAdapter = new ListAdpterOfPriceFilter(ctx, products);
					listViewfilter.setAdapter(boxAdapter);
					icon_close.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							dialog1.dismiss();
						}
					});
					btnok.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							String result = "";
							for (PriceFilter p : boxAdapter.getBox()) {
								if (p.box) {
									result += "\n" + p.name;
								}
							}
							/*
							 * Toast.makeText(ctx, result, Toast.LENGTH_LONG)
							 * .show();
							 */
							String[] array = result.split("\n");
							int i = array.length;
							String[] sendarray = new String[i];
							int l = 0;
							for (int k = 1; k < i; k++) {

								if (array[k].equals("Under 500")) {
									sendarray[l] = "0_500";
									l++;

								} else if (array[k].equals("501-1,000")) {
									sendarray[l] = "501_1000";
									l++;

								} else if (array[k].equals("1,001-1,500")) {
									sendarray[l] = "1001_1500";
									l++;

								} else if (array[k].equals("1,501-2,000")) {
									sendarray[l] = "1501_2000";
									l++;

								} else if (array[k].equals("2,000 Above")) {
									sendarray[l] = "2000_above";
									l++;

								}

							}

							switch (i) {
							case 2:
								url = "http://www.ordervenue.com/app/getproducts?language_id=1&category_id="
										+ ctaegory_id
										+ "&price="
										+ sendarray[0];
								Log.e("url", url);
								categoryproductlist.clear();
								loadingchk = "false";
								new Getproductcategory().execute();
								break;
							case 3:
								url = "http://www.ordervenue.com/app/getproducts?language_id=1&category_id="
										+ ctaegory_id
										+ "&price="
										+ sendarray[0] + "-" + sendarray[1];
								Log.e("url", url);
								categoryproductlist.clear();
								loadingchk = "false";

								new Getproductcategory().execute();
								break;
							case 4:
								url = "http://www.ordervenue.com/app/getproducts?language_id=1&category_id="
										+ ctaegory_id
										+ "&price="
										+ sendarray[0]
										+ "-"
										+ sendarray[1]
										+ "-" + sendarray[2];
								Log.e("url", url);
								categoryproductlist.clear();
								loadingchk = "false";

								new Getproductcategory().execute();

								break;
							case 5:
								url = "http://www.ordervenue.com/app/getproducts?language_id=1&category_id="
										+ ctaegory_id
										+ "&price="
										+ sendarray[0]
										+ "-"
										+ sendarray[1]
										+ "-"
										+ sendarray[2]
										+ "-"
										+ sendarray[3];
								Log.e("url", url);
								categoryproductlist.clear();
								loadingchk = "false";

								new Getproductcategory().execute();

								break;
							case 6:
								url = "http://www.ordervenue.com/app/getproducts?language_id=1&category_id="
										+ ctaegory_id
										+ "&price="
										+ sendarray[0]
										+ "-"
										+ sendarray[1]
										+ "-"
										+ sendarray[2]
										+ "-"
										+ sendarray[3] + "-" + sendarray[4];
								Log.e("url", url);
								categoryproductlist.clear();
								loadingchk = "false";

								new Getproductcategory().execute();

								break;

							default:
								break;
							}
							dialog1.dismiss();
						}
					});
				}
				dialog1.show();

			}
		});

		return rootView;
	}

	private void fillData() {
		for (int i = 0; i <= 4; i++) {
			products.add(new PriceFilter(listfilterprice[i], false));
		}

	}

	protected void showDialog() {
	}

	@SuppressLint("InflateParams")
	public class Getproductcategory extends AsyncTask<Void, Void, Void> {

		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			pd = new ProgressDialog(ctx);
			pd.setMessage("Please wait...");
			pd.setCancelable(false);
			pd.show();
		}

		@SuppressLint("ShowToast")
		protected Void doInBackground(Void... arg0) {
			ServiceHandler sh = new ServiceHandler();
			// Making a request to url and getting response
			Log.e("url", "" + url);
			String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);
			// &category_id=
			Log.e("Response: ", "> " + jsonStr);
			if (jsonStr != null) {
				try {
					JSONObject jsonobject = new JSONObject(jsonStr);
					String status = jsonobject.getString("status");
					totalItem = Integer.parseInt(jsonobject.getString("count"));
					if (status.equals("success")) {
						JSONArray jsonarray = jsonobject
								.getJSONArray("products");
						for (int i = 0; i < jsonarray.length(); i++) {
							JSONObject jsonproductobject = jsonarray
									.getJSONObject(i);
							JSONArray jsonproductimages = jsonproductobject
									.getJSONArray("images");

							if (jsonproductimages.length() > 0) {
								for (int j = 0; j < jsonproductimages.length(); j++) {
									JSONObject json_data = jsonproductimages
											.getJSONObject(j);
									productimages.add(json_data
											.getString("image"));
								}
							}

							// Json SubArray of Options field

							JSONArray jsonarrayoptions = jsonproductobject
									.getJSONArray("options");
							ArrayList<ProductOptionModel> productOptionList = new ArrayList<ProductOptionModel>();

							// OptionArray Check if length is gretaer then zero
							// then
							// if block execute
							if (jsonarrayoptions.length() > 0) {
								for (int k = 0; k < jsonarrayoptions.length(); k++) {
									JSONObject Jsonobjectofoptions = jsonarrayoptions
											.getJSONObject(k);
									JSONArray jsonarrayofproductoptionvalue = Jsonobjectofoptions
											.getJSONArray("product_option_value");
									ArrayList<ProductOptionValue> productOptionValueslist = new ArrayList<ProductOptionValue>();

									for (int j = 0; j < jsonarrayofproductoptionvalue
											.length(); j++) {
										JSONObject jsonobjectofproductoptionvalue = jsonarrayofproductoptionvalue
												.getJSONObject(j);
										;
										ProductOptionValue productOptionValue = new ProductOptionValue(
												jsonobjectofproductoptionvalue
														.optString("product_option_value_id"),
												jsonobjectofproductoptionvalue
														.optString("option_value_id"),
												jsonobjectofproductoptionvalue
														.optString("name"),
												jsonobjectofproductoptionvalue
														.optString("image"),
												jsonobjectofproductoptionvalue
														.optString("quantity"),
												jsonobjectofproductoptionvalue
														.optString("subtract"),
												jsonobjectofproductoptionvalue
														.optString("price"),
												jsonobjectofproductoptionvalue
														.optString("price_prefix"),
												jsonobjectofproductoptionvalue
														.optString("weight"),
												jsonobjectofproductoptionvalue
														.optString("weight_prefix"));
										productOptionValueslist
												.add(productOptionValue);
									}

									productOptionModel = new ProductOptionModel(
											Jsonobjectofoptions
													.optString("product_option_id"),
											Jsonobjectofoptions
													.optString("option_id"),
											Jsonobjectofoptions
													.optString("name"),
											Jsonobjectofoptions
													.optString("type"),
											Jsonobjectofoptions
													.optString("value"),
											Jsonobjectofoptions
													.optString("required"),
											productOptionValueslist);
									productOptionList.add(productOptionModel);

								}
							}
							categoryProduct = new CategoryProduct(
									jsonproductobject.optString("product_id"),
									jsonproductobject.optString("model"),
									jsonproductobject.optString("sku"),
									jsonproductobject.optString("upc"),
									jsonproductobject.optString("ean"),
									jsonproductobject.optString("jan"),
									jsonproductobject.optString("isbn"),
									jsonproductobject.optString("mpn"),
									jsonproductobject.optString("location"),
									jsonproductobject.optString("quantity"),
									jsonproductobject
											.optString("stock_status_id"),
									jsonproductobject.optString("image"),
									jsonproductobject
											.optString("manufacturer_id"),
									jsonproductobject.optString("shipping"),
									jsonproductobject.optString("price"),
									jsonproductobject.optString("points"),
									jsonproductobject.optString("tax_class_id"),
									jsonproductobject
											.optString("date_available"),
									jsonproductobject.optString("weight"),
									jsonproductobject
											.optString("weight_class_id"),
									jsonproductobject.optString("length"),
									jsonproductobject.optString("width"),
									jsonproductobject.optString("height"),
									jsonproductobject
											.optString("length_class_id"),
									jsonproductobject.optString("subtract"),
									jsonproductobject.optString("minimum"),
									jsonproductobject.optString("sort_order"),
									jsonproductobject.optString("status"),
									jsonproductobject.optString("viewed"),
									jsonproductobject.optString("date_added"),
									jsonproductobject
											.optString("date_modified"),
									jsonproductobject.optString("language_id"),
									jsonproductobject.optString("name"),
									jsonproductobject.optString("description"),
									jsonproductobject.optString("tag"),
									jsonproductobject.optString("store_id"),
									jsonproductobject.optString("manufacturer"),
									jsonproductobject.optString("discount"),
									jsonproductobject.optString("special"),
									jsonproductobject.optString("reward"),
									jsonproductobject.optString("stock_status"),
									jsonproductobject.optString("rating"),
									jsonproductobject.optString("reviews"),
									productOptionList);
							categoryproductlist.add(categoryProduct);
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			} else {
				Toast.makeText(ctx, "few pbolm faced", 5).show();
			}

			return null;
		}

		@SuppressLint("ShowToast")
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if (pd.isShowing())
				pd.dismiss();
			listviewfooter.setVisibility(View.GONE);
			if (categoryproductlist.size() > 0) {
				customGridAdapter = new CustomGridViewAdapter(ctx,
						R.layout.grid_item, categoryproductlist);
				customGridAdapter.notifyDataSetChanged();
				gridView.invalidateViews();

			} else {
				Toast.makeText(getActivity(), "Sorry item not found", 3).show();
				Constant.showAlertActivityWithTitle("Category",
						"Item not found", ctx);

			}

			Log.e("", "" + customGridAdapter);
			if (categoryproductlist.size() > 10) {
			} else {
				gridView.setAdapter(customGridAdapter);

			}

		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		if (gridView.getLastVisiblePosition() + 1 == page * 10) {
			if (gridView.getLastVisiblePosition() + 1 < totalItem) {

				listviewfooter.setVisibility(View.VISIBLE);
				if (chk.equals("Product A to Z")) {
					page = page + 1;
					Log.e("Page", "" + page);
					if ((totalItem / 10) + 1 > page) {
						url = Urls.GetProductByCategorey + ctaegory_id
								+ "&sort=pd.name&order=ASC" + "&page=" + page
								+ "&limit=20";
						Log.e("load more url ", url);
						new Getproductcategory().execute();
					} else {
						loadMore.setVisibility(View.GONE);
					}

				} else if (chk.equals("Product Z to A")) {
					page = page + 1;
					Log.e("Page", "" + page);
					if ((totalItem / 10) + 1 > page) {
						url = Urls.GetProductByCategorey + ctaegory_id
								+ "&sort=pd.name&order=DESC" + "&page=" + page
								+ "&limit=20";
						Log.e("load more url ", url);
						new Getproductcategory().execute();
					} else {
						loadMore.setVisibility(View.GONE);
					}
				} else if (chk.equals("Price Low to High")) {
					page = page + 1;
					Log.e("Page", "" + page);
					if ((totalItem / 10) + 1 > page) {
						url = Urls.GetProductByCategorey + ctaegory_id
								+ "&sort=p.price&order=ASC" + "&page=" + page
								+ "&limit=20";
						Log.e("load more url ", url);
						new Getproductcategory().execute();
					} else {
						loadMore.setVisibility(View.GONE);
					}
				} else if (chk.equals("Price High to Low�?")) {
					page = page + 1;
					Log.e("Page", "" + page);
					if ((totalItem / 10) + 1 > page) {
						url = Urls.GetProductByCategorey + ctaegory_id
								+ "&sort=p.price&order=DESC" + "&page=" + page
								+ "&limit=20";
						Log.e("load more url ", url);
						new Getproductcategory().execute();
					} else {
						loadMore.setVisibility(View.GONE);
					}
				} else if (chk.equals("normal")) {
					page = page + 1;
					Log.e("Page", "" + page);
					if ((totalItem / 10) + 1 > page) {
						url = Urls.GetProductByCategorey + ctaegory_id
								+ "&page=" + page + "&limit=10";
						Log.e("load more url ", url);
						new Getproductcategory().execute();
					} else {
						loadMore.setVisibility(View.GONE);
					}
				}

			} else {
				listviewfooter.setVisibility(View.GONE);
			}
		} else {
			listviewfooter.setVisibility(View.GONE);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.loadmore:
			if (chk.equals("Product A to Z")) {
				page = page + 1;
				Log.e("Page", "" + page);
				if ((totalItem / 10) + 1 > page) {
					url = Urls.GetProductByCategorey + ctaegory_id
							+ "&sort=pd.name&order=ASC" + "&page=" + page
							+ "&limit=20";
					Log.e("load more url ", url);
					new Getproductcategory().execute();
				} else {
					loadMore.setVisibility(View.GONE);
				}

			} else if (chk.equals("Product Z to A")) {
				page = page + 1;
				Log.e("Page", "" + page);
				if ((totalItem / 10) + 1 > page) {
					url = Urls.GetProductByCategorey + ctaegory_id
							+ "&sort=pd.name&order=DESC" + "&page=" + page
							+ "&limit=20";
					Log.e("load more url ", url);
					new Getproductcategory().execute();
				} else {
					loadMore.setVisibility(View.GONE);
				}
			} else if (chk.equals("Price Low to High")) {
				page = page + 1;
				Log.e("Page", "" + page);
				if ((totalItem / 10) + 1 > page) {
					url = Urls.GetProductByCategorey + ctaegory_id
							+ "&sort=p.price&order=ASC" + "&page=" + page
							+ "&limit=20";
					Log.e("load more url ", url);
					new Getproductcategory().execute();
				} else {
					loadMore.setVisibility(View.GONE);
				}
			} else if (chk.equals("Price High to Low�?")) {
				page = page + 1;
				Log.e("Page", "" + page);
				if ((totalItem / 10) + 1 > page) {
					url = Urls.GetProductByCategorey + ctaegory_id
							+ "&sort=p.price&order=DESC" + "&page=" + page
							+ "&limit=20";
					Log.e("load more url ", url);
					new Getproductcategory().execute();
				} else {
					loadMore.setVisibility(View.GONE);
				}
			} else if (chk.equals("normal")) {
				page = page + 1;
				Log.e("Page", "" + page);
				if ((totalItem / 10) + 1 > page) {
					url = Urls.GetProductByCategorey + ctaegory_id + "&page="
							+ page + "&limit=10";
					Log.e("load more url ", url);
					new Getproductcategory().execute();
				} else {
					loadMore.setVisibility(View.GONE);
				}
			}

			break;

		default:
			break;
		}

	}
}
