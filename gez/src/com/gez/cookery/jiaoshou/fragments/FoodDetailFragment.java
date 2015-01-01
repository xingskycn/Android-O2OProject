package com.gez.cookery.jiaoshou.fragments;

import com.actionbarsherlock.app.SherlockFragment;
import com.gez.cookery.jiaoshou.R;
import com.gez.cookery.jiaoshou.model.Caipxq;
import com.gez.cookery.jiaoshou.model.JsonModel;
import com.gez.cookery.jiaoshou.model.Result;
import com.gez.cookery.jiaoshou.net.IJsonModelData;
import com.gez.cookery.jiaoshou.net.RestClient;
import com.gez.cookery.jiaoshou.property.Constants;
import com.gez.cookery.jiaoshou.util.BitmapManager;
import com.gez.cookery.jiaoshou.util.NumberUtil;
import com.gez.cookery.jiaoshou.util.StatusUtil;
import com.gez.cookery.jiaoshou.util.UIHelper;
import com.gez.cookery.jiaoshou.util.Utils;
import com.gez.cookery.jiaoshou.widget.ColumnText;
import com.umeng.socialize.controller.RequestType;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class FoodDetailFragment extends SherlockFragment {

	private View mRootView;

	private TextView name;
	private Button collect;
	private Button share;
	private ImageView foodLogo;
	
	private ColumnText jiag;
	private ColumnText jianj;
	private ColumnText tez;
	private ColumnText huod;
	
	private BitmapManager bmpManager;
	//菜品ID
	private String foodId;
	
	private Caipxq caip;
	
	// 首先在您的Activity中添加如下成员变量
	final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share", RequestType.SOCIAL);
	
	public void setFoodId(String foodId) {
		this.foodId = foodId;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mRootView = inflater.inflate(R.layout.food_detail_fragment, null);
		
		name = (TextView)mRootView.findViewById(R.id.food_detail_name);
		collect = (Button)mRootView.findViewById(R.id.food_detail_button_collect);
		share = (Button)mRootView.findViewById(R.id.food_detail_button_share);
		foodLogo = (ImageView)mRootView.findViewById(R.id.food_detail_logo);
		
		jiag = (ColumnText)mRootView.findViewById(R.id.food_detail_frag_jiag);
		jianj = (ColumnText)mRootView.findViewById(R.id.food_detail_frag_jianj);
		tez = (ColumnText)mRootView.findViewById(R.id.food_detail_frag_tez);
		huod = (ColumnText)mRootView.findViewById(R.id.food_detail_frag_huod);
		
		collect.setOnClickListener(collectClick);
		share.setOnClickListener(shareClick);

		bmpManager = new BitmapManager(Constants.foodLogo);
		
		showDetail();
		
		return mRootView;
	}

	private View.OnClickListener collectClick = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			final ProgressDialog progressDialog = UIHelper.showProgressDialog(FoodDetailFragment.this.getActivity(), "正在提交");
			
			RestClient.collectFood(foodId, new IJsonModelData() {
				public void onReturn(JsonModel data) {
					progressDialog.dismiss();
					
					if (data != null) {
						Result result = (Result)data;
						if (result.isSuccess()) {
							collect.setText("已收藏");
							collect.setBackgroundResource(R.drawable.normal_btn_bg);
							collect.setEnabled(false);
							// 发送广播消息
							Intent intent = new Intent("refreshCollectFoodList");
							LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
						}
						else {
							UIHelper.showToast(FoodDetailFragment.this.getActivity(), result.getMessage());
						}
					}
				}
			});
		}
	};
	
	private View.OnClickListener shareClick = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if (caip != null) {
				// 设置分享内容
				mController.setShareContent(caip.getCaipmc() + "真心不错哦!(来自点点餐安卓APP)");
				
				// 是否只有已登录用户才能打开分享选择页
			    mController.openShare(FoodDetailFragment.this.getActivity(), false);
			}
		}
	};
	
	private void showDetail() {
		final ProgressDialog progressDialog = UIHelper.showProgressDialog(this.getActivity(), "正在加载菜品数据");

		RestClient.getFoodDetail(foodId, new IJsonModelData(){
			public void onReturn(JsonModel data) {
				progressDialog.dismiss();
				
				if (data != null) {
					Caipxq detail = (Caipxq)data;
					caip = detail;

					collect.setVisibility(View.VISIBLE);
					share.setVisibility(View.VISIBLE);
					if (detail.isFavorite()) {
						collect.setText("已收藏");
						collect.setBackgroundResource(R.drawable.normal_btn_bg);
						collect.setEnabled(false);
					}
					
					name.setText(detail.getCaipmc());
					
					jiag.setTextViewText(String.format("菜品价格：￥%s", NumberUtil.getFloatString(detail.getJiag())));
					jianj.setTextViewText(String.format("菜品简介：%s", detail.getJianj()));

					StringBuilder foodTez = new StringBuilder();
					foodTez.append(StatusUtil.convertLaw(Integer.parseInt(detail.getLaw())));
					if (detail.isQingz()) {
						foodTez.append(" 清真");
					}
					
					tez.setTextViewText(String.format("菜品特征：%s", foodTez.toString()));
					
					StringBuilder foodHuod = new StringBuilder();
					boolean add = false;
					if (detail.isZhaop()) {
						if (add) {
							foodHuod.append(" ");
						}
						foodHuod.append("招牌");
						add = true;
					}
					if (detail.isTej()) {
						if (add) {
							foodHuod.append(" ");
						}
						foodHuod.append("特价");
						add = true;
					}
					if (detail.isXinc()) {
						if (add) {
							foodHuod.append(" ");
						}
						foodHuod.append("新菜");
						add = true;
					}
					
					huod.setTextViewText(String.format("菜品活动：%s", foodHuod.toString()));
					
					bmpManager.loadBitmap(Utils.guidToInt(detail.getId()), RestClient.getImageUrl(detail.getCaipbs()), foodLogo);
				}
			}
		});
	}
}
