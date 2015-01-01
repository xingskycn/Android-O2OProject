package com.gez.cookery.jiaoshou.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.gez.cookery.jiaoshou.R;
import com.gez.cookery.jiaoshou.model.Dingddp;
import com.gez.cookery.jiaoshou.model.Dingdmx;
import com.gez.cookery.jiaoshou.model.JsonModel;
import com.gez.cookery.jiaoshou.net.IJsonModelData;
import com.gez.cookery.jiaoshou.net.RestClient;
import com.gez.cookery.jiaoshou.property.Constants;
import com.gez.cookery.jiaoshou.util.BitmapManager;
import com.gez.cookery.jiaoshou.util.NumberUtil;
import com.gez.cookery.jiaoshou.util.UIHelper;
import com.gez.cookery.jiaoshou.util.Utils;
import com.gez.cookery.jiaoshou.widget.ColumnText;

public class OrderDetailActivity extends SherlockFragmentActivity {
	
	private BitmapManager bmpManagerRestaurant;
	private BitmapManager bmpManagerFood;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final ProgressDialog progressDialog1 = UIHelper.showProgressDialog(
				OrderDetailActivity.this, "正在获取订单内容...");

		setContentView(R.layout.order_detail);

		setTitle(R.string.order_detail_title);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		String orderId = getIntent().getStringExtra("orderId");

		final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.order_detail_list_layout);
		final ColumnText cant = (ColumnText) findViewById(R.id.order_detail_business);
		
		bmpManagerRestaurant = new BitmapManager(Constants.businessLogo);
		bmpManagerFood = new BitmapManager(Constants.foodLogo);
		
		// 加载数据，获得订单菜品列表
		RestClient.getOrderCommentList(orderId, new IJsonModelData() {

			@Override
			public void onReturn(JsonModel data) {			
				progressDialog1.dismiss();
				
				if (data != null) {
					Dingddp list = (Dingddp) data;
					
					cant.setTextViewText(list.getCantMc());
					bmpManagerRestaurant.loadBitmap(Utils.guidToInt(list.getCantId()), RestClient.getImageUrl(list.getCantbstp()), cant.imageView);
					
					if (list.getCaipxx().size() >= 0) {
						int caipSize = list.getCaipxx().size();
						for (int i = 0; i < caipSize; i++) {
							ColumnText cct = new ColumnText(OrderDetailActivity.this);
							Dingdmx caip = list.getCaipxx().get(i);
							
							cct.setTextViewText(String.format("%s，%d份， ￥%s", caip.getCaipMc(), caip.getFens(), NumberUtil.getFloatString(caip.getFens() * caip.getDanj())));
							
							bmpManagerFood.loadBitmap(Utils.guidToInt(caip.getCaipId()), RestClient.getImageUrl(list.getCantbstp()), cct.imageView);
							
							linearLayout.addView(cct);
						}
						ColumnText sumPrice = new ColumnText(OrderDetailActivity.this);
						sumPrice.setTextViewText(String.format("总价格：" + list.getDingdze() + "元"));
						sumPrice.setImageResource(R.drawable.op_price);
						linearLayout.addView(sumPrice);
						
						ColumnText phoneNumber = new ColumnText(OrderDetailActivity.this);
						phoneNumber.setTextViewText(String.format("联系电话：" + list.getGukdh()));
						phoneNumber.setImageResource(R.drawable.op_phone);
						linearLayout.addView(phoneNumber);
						
						ColumnText address = new ColumnText(OrderDetailActivity.this);
						address.setTextViewText(String.format("送餐地址：" + list.getGukdz()));
						address.setImageResource(R.drawable.op_address);
						linearLayout.addView(address);
					} 
				}
				else {
					UIHelper.showToast(OrderDetailActivity.this, "订单加载错误！");
				}
			}
		});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
