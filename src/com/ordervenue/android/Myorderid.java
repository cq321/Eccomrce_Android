package com.ordervenue.android;

import java.util.ArrayList;

import com.onjection.opencart.model.MyOrderModel;
import com.onjection.opencart.model.MyOrderProdcutAdpter;
import com.onjection.opencart.model.MyOrderProductDetails;
import com.onjection.opencart.model.MyOrderTotalAdpter;
import com.onjection.opencart.model.MyOrderTotalsModel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Myorderid extends Activity {
	ImageView ivBack;
	ListView lvConfirmOrder, listtotal;
	TextView orderid;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.myorderid);
		int position = getIntent().getIntExtra("position", -1);
		@SuppressWarnings("unchecked")
		ArrayList<MyOrderModel> filelist = MyOrder.myorderlist;
		lvConfirmOrder = (ListView) findViewById(R.id.lvConfirmOrder);
		listtotal = (ListView) findViewById(R.id.listtotal);
		ivBack = (ImageView) findViewById(R.id.ivBack);
		orderid = (TextView) findViewById(R.id.orderid);
		orderid.setText(filelist.get(position).getOrder_id());
		ivBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				finish();
			}
		});
		ArrayList<MyOrderTotalsModel> mytotallist = filelist.get(position)
				.getMyodertotals();
		ArrayList<MyOrderProductDetails> myproductlist = filelist.get(position)
				.getMyorderproduct();
		MyOrderProdcutAdpter myOrderProdcutAdpter = new MyOrderProdcutAdpter(
				Myorderid.this, myproductlist);
		lvConfirmOrder.setAdapter(myOrderProdcutAdpter);
		MyOrderTotalAdpter myOrderTotalAdpter = new MyOrderTotalAdpter(
				Myorderid.this, mytotallist);
		listtotal.setAdapter(myOrderTotalAdpter);

	}
}
