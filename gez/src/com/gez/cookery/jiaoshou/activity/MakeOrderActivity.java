package com.gez.cookery.jiaoshou.activity;

import java.util.ArrayList;

import com.gez.cookery.jiaoshou.R;
import com.gez.cookery.jiaoshou.adapter.MakeOrderListAdapter;
import com.gez.cookery.jiaoshou.contract.IChangeOrder;
import com.gez.cookery.jiaoshou.model.CaipBasic;
import com.gez.cookery.jiaoshou.model.CantBasic;
import com.gez.cookery.jiaoshou.util.NumberUtil;
import com.gez.cookery.jiaoshou.widget.ColumnText;
import com.actionbarsherlock.view.MenuItem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MakeOrderActivity extends BaseActivity implements IChangeOrder {

	//确定按钮
	private Button buttonOK;
	//列表
	private ListView listView;
	private MakeOrderListAdapter adapter;
	//配送费
	private double deliveryPrice;
	private ColumnText deliveryPriceShow;
	//起送最低价
	private double lowestPrice;
	private ColumnText lowestPriceShow;
	//当前餐厅
	private CantBasic mRestaurant;
	//选中菜品集合
	private ArrayList<CaipBasic> listCaipSelect = new ArrayList<CaipBasic>();
	//菜品合计
	private int sum = 0;
	private float sumPrice = 0;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.make_order);
		
		buttonOK = (Button) this.findViewById(R.id.order_button_ok);
		listView = (ListView) this.findViewById(R.id.order_listview);
		deliveryPriceShow = (ColumnText) this.findViewById(R.id.the_delivery_price);
		lowestPriceShow = (ColumnText) this.findViewById(R.id.the_lowest_price);

		setTitle(R.string.make_order_title);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		mRestaurant = (CantBasic) this.getIntent().getExtras().getSerializable("mRestaurant");
		
		deliveryPrice = mRestaurant.getPeisf();
		lowestPrice = mRestaurant.getQisjg();
		
		//获取所选菜品 一份菜品一个对象 所以有重复 需要重新计算得到选中菜品和数量
		ArrayList<CaipBasic> listCaip = (ArrayList<CaipBasic>)this.getIntent().getExtras().getSerializable("list");
		
		for (CaipBasic caip : listCaip) {
			
			CaipBasic caipFind = null;
			for (CaipBasic caipSelect : listCaipSelect) {
				if (caip.getId().equals(caipSelect.getId())){
					caipFind = caipSelect;
					break;
				}
			}
			
			if (caipFind != null) {
				caipFind.setCount(caipFind.getCount() + 1);
			}
			else {
				caip.setCount(1);
				listCaipSelect.add(caip);
			}
		}
		
		adapter = new MakeOrderListAdapter(this, listCaipSelect);
		listView.setAdapter(adapter);
		
		if(deliveryPrice > 0) {
			deliveryPriceShow.setVisibility(View.VISIBLE);
			deliveryPriceShow.setTextViewText("配送费 ( ￥" + Double.toString(deliveryPrice) + " ) ");
		}
		if(lowestPrice > 0) {
			lowestPriceShow.setVisibility(View.VISIBLE);
			lowestPriceShow.setTextViewText("餐厅 " + Double.toString(lowestPrice) + " 元起送");
		}
		
		buttonOK.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (sum == 0) {
					Toast.makeText(MakeOrderActivity.this, "请至少选择一个菜品", Toast.LENGTH_SHORT).show();
				} else if (sumPrice < lowestPrice) {
					Toast.makeText(MakeOrderActivity.this, "未达到餐厅起送价", Toast.LENGTH_SHORT).show();
				} else {
					Intent intent = new Intent(MakeOrderActivity.this, SubmitOrderActivity.class);
					intent.putExtra("list", listCaipSelect);
					intent.putExtra("sumPrice", sumPrice);
					intent.putExtra("mRestaurant", mRestaurant);
					startActivity(intent);
				}
			}
		});
		
		changeOrder();
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

	@Override
	public void changeOrder() {
		sum = 0;
		sumPrice = 0;
		for (CaipBasic caip : listCaipSelect) {
			sum += caip.getCount();
			sumPrice += caip.getJiag() * caip.getCount();
		}
		sumPrice += deliveryPrice;
		buttonOK.setText(String.format("确认(%s份￥%s元)", sum, NumberUtil.getFloatString(sumPrice)));
	}
}
