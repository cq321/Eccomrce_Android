package com.ordervenue.android;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.onjection.ServerTask.ConnectionDector;
import com.onjection.opencart.Utilz.MenuDropdownOnItemClickListener;
import com.onjection.opencart.Utilz.Prefs;
import com.onjection.opencart.Utilz.ServiceHandler;
import com.onjection.opencart.Utilz.Urls;
import com.onjection.opencart.model.CategoryModel;
import com.onjection.opencart.model.ChildInfo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ExpandableListView.OnChildClickListener;

public class NevigationDrawer extends FragmentActivity {
	private DrawerLayout mDrawerLayout;
	private ArrayList<CategoryModel> CategoryModellist = new ArrayList<CategoryModel>();
	CategoryModel categoryModel;
	ImageView home, ivKart;
	public ImageView ivActionmenu;
	Fragment fragment = null;
	TextView txtappname;
	ExpandableListView expListView;
	HashMap<String, List<String>> listDataChild;
	ExpandableListAdapter listAdapter;
	List<String> listDataHeader;
	String language_id;
	Context ctx;
	ConnectionDector connectionDector = new ConnectionDector(this);

	String popUpContents[];
	public PopupWindow popupWindowMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		// Remove notification bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		/*
		 * String fontPath = "fonts/Shadow Boxing.ttf";
		 */
		setContentView(R.layout.navigationdrawer);
		ctx = NevigationDrawer.this;
		home = (ImageView) findViewById(R.id.home);
		home.setOnClickListener(homeOnclickListener);
		ivKart = (ImageView) findViewById(R.id.ivKart);
		ivKart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(NevigationDrawer.this, MyCart.class);
				startActivity(i);

			}
		});
		ivActionmenu = (ImageView) findViewById(R.id.menu);
		ivActionmenu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				popupWindowMenu.showAsDropDown(v, -5, 5);
				// Toast.makeText(NevigationDrawer.this, "clicked", 3).show();
			}
		});
		txtappname = (TextView) findViewById(R.id.txtappname);

		setUpDrawer();

		List<String> menuList = new ArrayList<String>();
		String Customer_id = Prefs.getPreferences(ctx, "Customer_id");
		if (Customer_id != "") {
			menuList.add("Logout::1");
			menuList.add("My Address::3");
			menuList.add("My Cart::4");
			menuList.add("My Orders::5");
		} else {
			menuList.add("Login::1");
			// menuList.add("Menu 3::3");
			// menuList.add("Menu 4::4");

		}
		menuList.add("Contact Us::2");
		// menuList.add("Invite Friends::5");
		// menuList.add("Rate the App::6");

		// convert to simple array
		popUpContents = new String[menuList.size()];
		menuList.toArray(popUpContents);

		/*
		 * initialize pop up window
		 */
		popupWindowMenu = popupWindowMenu();

	}

	private void setUpDrawer() {
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		new GetSearchdetails().execute();

		mDrawerLayout.setScrimColor(getResources().getColor(
				android.R.color.transparent));
		mDrawerLayout.setDrawerListener(mDrawerListener);
		expListView = (ExpandableListView) findViewById(R.id.lvExp);

		fragment = new HomeFragment();
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, fragment).commit();
		mDrawerLayout.closeDrawer(expListView);
		expListView.setOnChildClickListener(new OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				if (groupPosition == 0) {
					fragment = new HomeFragment();
				} else if (groupPosition > 0) {
					if (childPosition >= 0) {
						String ctaegory_id = CategoryModellist
								.get(groupPosition - 1).getChild()
								.get(childPosition).getCategory_id();
						String childnamestring = CategoryModellist
								.get(groupPosition - 1).getChild()
								.get(childPosition).getName();
						Log.e("child name", childnamestring + " " + ctaegory_id);
						Bundle bundle = new Bundle();
						bundle.putString("ctaegory_id", ctaegory_id);
						bundle.putString("ChildName", childnamestring);

						fragment = new Firstfragment();
						fragment.setArguments(bundle);
					}

				}
				/*
				 * switch (groupPosition) { case 0: switch (childPosition) {
				 * case 0:
				 * 
				 * break; case 1: fragment = new Firstfragment(); break; case 2:
				 * fragment = new Firstfragment(); break;
				 * 
				 * } break;
				 * 
				 * case 1: switch (childPosition) { case 0: String ctaegory_id =
				 * CategoryModellist .get(groupPosition).getChild()
				 * .get(childPosition).getCategory_id(); Bundle bundle = new
				 * Bundle(); bundle.putString("ctaegory_id", ctaegory_id);
				 * Firstfragment fragobj = new Firstfragment();
				 * fragobj.setArguments(bundle); break; case 1: fragment = new
				 * Firstfragment(); break; case 2: fragment = new
				 * Firstfragment(); break; default: break; } break;
				 * 
				 * case 2: switch (childPosition) { case 0: fragment = new
				 * Firstfragment(); break; case 1: fragment = new
				 * Firstfragment(); break; case 2: fragment = new
				 * Firstfragment(); break; default: break; } break;
				 * 
				 * default: break; }
				 */
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.content_frame, fragment).commit();
				mDrawerLayout.closeDrawer(expListView);
				return false;
			}
		});
	}

	View.OnClickListener homeOnclickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (mDrawerLayout.isDrawerOpen(expListView)) {
				mDrawerLayout.closeDrawer(expListView);
			} else {
				mDrawerLayout.openDrawer(expListView);
			}
		}
	};

	@SuppressWarnings("unused")
	private OnItemClickListener mDrawerItemClickedListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {

			switch (position) {
			case 0:
				fragment = new HomeFragment();
				break;
			case 1:
				fragment = new Firstfragment();
				break;
			case 2:
				fragment = new Secondfragments();
				break;
			case 3:
				fragment = new ThirdFragements();
				break;
			default:
				return;
			}

			getSupportFragmentManager().beginTransaction()
					.replace(R.id.content_frame, fragment).commit();

			mDrawerLayout.closeDrawer(expListView);
		}
	};

	private DrawerListener mDrawerListener = new DrawerListener() {

		@Override
		public void onDrawerStateChanged(int status) {

		}

		@Override
		public void onDrawerSlide(View view, float slideArg) {

		}

		@Override
		public void onDrawerOpened(View view) {
		}

		@Override
		public void onDrawerClosed(View view) {
		}
	};

	private void prepareListData() {

		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, List<String>>();
		listDataHeader.add("Home");
		List<String> homedetails = new ArrayList<String>();
		listDataChild.put(listDataHeader.get(0), homedetails);

		// Adding child data
		for (int i = 0; i < CategoryModellist.size(); i++) {
			List<String> product1 = new ArrayList<String>();
			listDataHeader.add(CategoryModellist.get(i).getName());

			for (int j = 0; j < CategoryModellist.get(i).getChild().size(); j++) {

				product1.add(CategoryModellist.get(i).getChild().get(j)
						.getName());

			}
			listDataChild.put(listDataHeader.get(i + 1), product1);
			listAdapter = new ExpandableListAdapter(this, listDataHeader,
					listDataChild);
			// setting list adapter
			expListView.setAdapter(listAdapter);
		}

	}

	@SuppressLint("InflateParams")
	public class ExpandableListAdapter extends BaseExpandableListAdapter {

		private Context _context;
		private List<String> _listDataHeader; // header titles
		// child data in format of header title, child title
		private HashMap<String, List<String>> _listDataChild;

		public ExpandableListAdapter(Context context,
				List<String> listDataHeader,
				HashMap<String, List<String>> listChildData) {
			this._context = context;
			this._listDataHeader = listDataHeader;
			this._listDataChild = listChildData;
		}

		@Override
		public Object getChild(int groupPosition, int childPosititon) {
			return this._listDataChild.get(
					this._listDataHeader.get(groupPosition))
					.get(childPosititon);
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		@Override
		public View getChildView(int groupPosition, final int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {

			final String childText = (String) getChild(groupPosition,
					childPosition);

			if (convertView == null) {
				LayoutInflater infalInflater = (LayoutInflater) this._context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = infalInflater.inflate(R.layout.list_item, null);
			}

			TextView txtListChild = (TextView) convertView
					.findViewById(R.id.lblListItem);

			txtListChild.setText(childText);
			return convertView;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			return this._listDataChild.get(
					this._listDataHeader.get(groupPosition)).size();
		}

		@Override
		public Object getGroup(int groupPosition) {
			return this._listDataHeader.get(groupPosition);
		}

		@Override
		public int getGroupCount() {
			return this._listDataHeader.size();
		}

		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		@SuppressLint("InflateParams")
		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			final String headerTitle = (String) getGroup(groupPosition);
			if (convertView == null) {
				LayoutInflater infalInflater = (LayoutInflater) this._context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = infalInflater.inflate(R.layout.list_group, null);
			}

			TextView lblListHeader = (TextView) convertView
					.findViewById(R.id.lblListHeader);
			lblListHeader.setTypeface(null, Typeface.BOLD);
			lblListHeader.setText(headerTitle);
			if (headerTitle == "Home") {
				lblListHeader.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						fragment = new HomeFragment();
						getSupportFragmentManager().beginTransaction()
								.replace(R.id.content_frame, fragment).commit();
						mDrawerLayout.closeDrawer(expListView);

					}
				});

			}

			return convertView;
		}

		@Override
		public boolean hasStableIds() {
			return false;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}
	}

	public class GetSearchdetails extends AsyncTask<Void, Void, Void> {

		protected void onPreExecute() {
			super.onPreExecute();

		}

		protected Void doInBackground(Void... arg0) {
			// Creating service handler class instance
			ServiceHandler sh = new ServiceHandler();
			// Making a request to url and getting response
			String jsonStr = sh.makeServiceCall(Urls.GetlangApi,
					ServiceHandler.GET);

			Log.e("Response: ", "> " + jsonStr);

			if (jsonStr != null) {
				try {
					JSONObject jsonobject = new JSONObject(jsonStr);
					String status = jsonobject.getString("status");
					if (status.equals("success")) {
						JSONArray jsonarray = jsonobject
								.getJSONArray("languages");
						for (int i = 0; i < jsonarray.length(); i++) {
							JSONObject Jsonlangobject = jsonarray
									.getJSONObject(i);
							language_id = Jsonlangobject
									.getString("language_id");
							String name = Jsonlangobject.getString("name");
							Log.e("language_id", language_id);
						}

					} else {
						Toast.makeText(NevigationDrawer.this,
								"Something Worng In Network Problem", 5).show();
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				Log.e("ServiceHandler", "Couldn't get any data from the url");
			}
			return null;
		}

		@SuppressLint("ShowToast")
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			new GetCategory().execute();
		}
	}

	public class GetCategory extends AsyncTask<Void, Void, Void> {

		protected void onPreExecute() {
			super.onPreExecute();
		}

		@SuppressLint("ShowToast")
		protected Void doInBackground(Void... arg0) {
			// Creating service handler class instance
			ServiceHandler sh = new ServiceHandler();
			// Making a request to url and getting response
			String jsonStr = sh.makeServiceCall(Urls.GetCategoryApi
					+ language_id, ServiceHandler.GET);

			Log.e("CategoryResponse: ", "> " + jsonStr);

			if (jsonStr != null) {
				try {
					JSONObject jsonobject = new JSONObject(jsonStr);
					String status = jsonobject.getString("status");
					if (status.equals("success")) {
						JSONArray jsonarray = jsonobject
								.getJSONArray("categories");

						for (int i = 0; i < jsonarray.length(); i++) {
							JSONObject Jsoncategoryobject = jsonarray
									.getJSONObject(i);
							JSONArray Jsonchildarray = Jsoncategoryobject
									.getJSONArray("child");
							ArrayList<ChildInfo> childinfolist = new ArrayList<ChildInfo>();
							;

							if (Jsonchildarray.length() > 0) {
								for (int j = 0; j < Jsonchildarray.length(); j++)

								{
									JSONObject Jsonchildobject = Jsonchildarray
											.getJSONObject(j);

									ChildInfo childinfo = new ChildInfo(
											Jsonchildobject
													.optString("category_id"),
											Jsonchildobject.optString("image"),
											Jsonchildobject
													.optString("parent_id"),
											Jsonchildobject.optString("top"),
											Jsonchildobject.optString("column"),
											Jsonchildobject
													.optString("sort_order"),
											Jsonchildobject.optString("status"),
											Jsonchildobject
													.optString("date_added"),
											Jsonchildobject
													.optString("date_modified"),
											Jsonchildobject
													.optString("language_id"),
											Jsonchildobject.optString("name"),
											Jsonchildobject
													.optString("description"),
											Jsonchildobject
													.optString("meta_title"),
											Jsonchildobject
													.optString("meta_description"),
											Jsonchildobject
													.optString("meta_keyword"),
											Jsonchildobject
													.optString("store_id"));
									childinfolist.add(childinfo);
								}
							}
							categoryModel = new CategoryModel(
									Jsoncategoryobject.optString("date_added"),
									Jsoncategoryobject.optString("image"),
									Jsoncategoryobject.optString("parent_id"),
									Jsoncategoryobject.optString("category_id"),
									Jsoncategoryobject.optString("top"),
									Jsoncategoryobject.optString("column"),
									Jsoncategoryobject.optString("sort_order"),
									Jsoncategoryobject.optString("status"),
									Jsoncategoryobject.optString("date_added"),
									Jsoncategoryobject
											.optString("date_modified"),
									Jsoncategoryobject.optString("name"),
									Jsoncategoryobject.optString("description"),
									Jsoncategoryobject.optString("meta_title"),
									Jsoncategoryobject
											.optString("meta_description"),
									Jsoncategoryobject
											.optString("meta_keyword"),
									Jsoncategoryobject.optString("store_id"),
									childinfolist);
							CategoryModellist.add(categoryModel);
						}

					} else {
						Toast.makeText(NevigationDrawer.this,
								"Something Worng In Network Problem", 5).show();
					}

				} catch (JSONException e) { // TODO

					e.printStackTrace();
				}

			} else {
				Log.e("ServiceHandler", "Couldn't get any data from the url");
			}
			return null;
		}

		@SuppressLint("ShowToast")
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			prepareListData();

		}
	}

	/*
	 * 
	 */
	public PopupWindow popupWindowMenu() {

		// initialize a pop up window type
		PopupWindow popupWindow = new PopupWindow(this);

		// the drop down list is a list view
		ListView listViewDogs = new ListView(this);

		// set our adapter and pass our pop up window contents
		listViewDogs.setAdapter(menuAdapter(popUpContents));

		// set the item click listener
		listViewDogs
				.setOnItemClickListener(new MenuDropdownOnItemClickListener());

		// some other visual settings
		popupWindow.setFocusable(true);
		popupWindow.setWidth(250);
		popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);

		// set the list view as pop up window content
		popupWindow.setContentView(listViewDogs);

		return popupWindow;
	}

	/*
	 * adapter where the list values will be set
	 */
	private ArrayAdapter<String> menuAdapter(String menuArray[]) {

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.menu_item, menuArray) {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {

				// setting the ID and text for every items in the list
				String item = getItem(position);
				String[] itemArr = item.split("::");
				String text = itemArr[0];
				String id = itemArr[1];

				// visual settings for the list item
				TextView listItem = new TextView(NevigationDrawer.this);

				listItem.setText(text);
				listItem.setTag(id);
				listItem.setTextSize(20);

				listItem.setPadding(20, 10, 20, 10);
				listItem.setTextColor(Color.WHITE);

				return listItem;
			}
		};

		return adapter;
	}
}
