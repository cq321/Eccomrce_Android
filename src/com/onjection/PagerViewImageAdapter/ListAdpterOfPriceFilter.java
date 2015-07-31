package com.onjection.PagerViewImageAdapter;

import java.util.ArrayList;

import com.onjection.opencart.model.PriceFilter;
import com.ordervenue.android.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class ListAdpterOfPriceFilter extends BaseAdapter {
	Context ctx;
	LayoutInflater lInflater;
	ArrayList<PriceFilter> objects;

	public ListAdpterOfPriceFilter(Context context,
			ArrayList<PriceFilter> products) {
		ctx = context;
		objects = products;
		lInflater = (LayoutInflater) ctx
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return objects.size();
	}

	@Override
	public Object getItem(int position) {
		return objects.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			view = lInflater.inflate(R.layout.filterpricerow, parent, false);
		}

		PriceFilter p = getProduct(position);

		((TextView) view.findViewById(R.id.textView1)).setText(p.name);

		CheckBox cbBuy = (CheckBox) view.findViewById(R.id.checkBox1);
		cbBuy.setOnCheckedChangeListener(myCheckChangList);
		cbBuy.setTag(position);
		cbBuy.setChecked(p.box);
		return view;
	}

	PriceFilter getProduct(int position) {
		return ((PriceFilter) getItem(position));
	}

	public ArrayList<PriceFilter> getBox() {
		ArrayList<PriceFilter> box = new ArrayList<PriceFilter>();
		for (PriceFilter p : objects) {
			if (p.box)
				box.add(p);
		}
		return box;
	}

	OnCheckedChangeListener myCheckChangList = new OnCheckedChangeListener() {
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			getProduct((Integer) buttonView.getTag()).box = isChecked;
		}
	};
}
