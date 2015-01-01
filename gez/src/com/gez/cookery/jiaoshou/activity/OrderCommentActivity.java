package com.gez.cookery.jiaoshou.activity;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.gez.cookery.jiaoshou.R;
import com.gez.cookery.jiaoshou.model.Caippl;
import com.gez.cookery.jiaoshou.model.Dingddp;
import com.gez.cookery.jiaoshou.model.Dingdmx;
import com.gez.cookery.jiaoshou.model.JsonModel;
import com.gez.cookery.jiaoshou.model.Pingltj;
import com.gez.cookery.jiaoshou.model.Result;
import com.gez.cookery.jiaoshou.model.Shangjpl;
import com.gez.cookery.jiaoshou.net.IJsonModelData;
import com.gez.cookery.jiaoshou.net.RestClient;
import com.gez.cookery.jiaoshou.util.JsonUtil;
import com.gez.cookery.jiaoshou.util.UIHelper;
import com.gez.cookery.jiaoshou.widget.ColumnCommentText;

public class OrderCommentActivity extends SherlockFragmentActivity {

	private String orderId; // 订单ID
	private String cantId; // 餐厅ID
	private Button button;
	private ArrayList<Dingdmx> caipxx;
	private Pingltj pingltj; // 评论提交类
	private Shangjpl cantpl; // 餐厅评论
	private ArrayList<Caippl> caippl = null; // 菜品评论

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		final ProgressDialog progressDialog1 = UIHelper.showProgressDialog(
				OrderCommentActivity.this, "正在获取订单内容...");

		setContentView(R.layout.order_comment);

		setTitle(R.string.order_comment_title);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		orderId = getIntent().getStringExtra("orderId");

		final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.order_list_layout);
		final ColumnCommentText mCant = (ColumnCommentText) findViewById(R.id.order_restaurent_comment);
		button = (Button) findViewById(R.id.submit_comment_button_ok);

		// 加载数据，获得订单菜品列表
		RestClient.getOrderCommentList(orderId, new IJsonModelData() {

			@Override
			public void onReturn(JsonModel data) {
				// TODO Auto-generated method stub
				if (data != null) {
					Dingddp list = (Dingddp) data;
					caipxx = list.getCaipxx();

					cantId = (String) list.getCantId();
					mCant.setTextView((String) list.getCantMc());

					if (caipxx.size() >= 0) {

						for (int i = 0; i < caipxx.size(); i++) {
							ColumnCommentText cct = new ColumnCommentText(
									OrderCommentActivity.this);
							cct.setId((int) (R.id.order_restaurent_comment + i + 1));
							cct.setTextView((String) caipxx.get(i).getCaipMc());
							cct.setImageResource(R.drawable.dp_dish01);

							linearLayout.addView(cct);
						}
						progressDialog1.dismiss();
					} else {
						progressDialog1.dismiss();
						Toast.makeText(OrderCommentActivity.this, "订单加载错误！",
								Toast.LENGTH_SHORT).show();
					}
				}
			}
		});

		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				final ProgressDialog progressDialog = UIHelper
						.showProgressDialog(OrderCommentActivity.this,
								"正在提交点评...");

				// 餐厅评论
				cantpl = new Shangjpl();
				cantpl.setPinglnr((String) mCant.getEditText());
				cantpl.setPingj(mCant.getToggleButtonChecked());
				cantpl.setCantId(cantId);

				// 菜品评论
				caippl = new ArrayList<Caippl>();

				if (caipxx.size() >= 0) {

					for (int i = 0; i < caipxx.size(); i++) {
						ColumnCommentText cct = (ColumnCommentText) findViewById(R.id.order_restaurent_comment
								+ i + 1);
						Caippl caipplItem = new Caippl();
						caipplItem.setPinglnr(cct.getEditText());
						caipplItem.setPingj(cct.getToggleButtonChecked());
						caipplItem.setCaipId(caipxx.get(i).getCaipId());

						caippl.add(caipplItem);
					}
				}

				// 评论提交
				pingltj = new Pingltj();
				pingltj.setCantpl(cantpl);
				pingltj.setCaippl(caippl);

				String pingl = JsonUtil.toJson(pingltj);

				// 提交评论
				RestClient.addOrderComment(orderId, pingl,
						new IJsonModelData() {
							public void onReturn(JsonModel data) {
								progressDialog.dismiss();

								if (data != null) {
									Result result = (Result) data;
									if (result.isSuccess()) {
										UIHelper.showToast(
												OrderCommentActivity.this,
												"提交成功.");
										
										Intent intent = new Intent("refreshOrder");
										LocalBroadcastManager.getInstance(OrderCommentActivity.this).sendBroadcast(intent);
										
										finish();
									} else {
										UIHelper.showToast(
												OrderCommentActivity.this,
												result.getMessage());
									}
								}
							}
						});

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
