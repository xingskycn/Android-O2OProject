package com.gez.cookery.jiaoshou.fragments;

import com.actionbarsherlock.app.SherlockFragment;
import com.gez.cookery.jiaoshou.R;
import com.gez.cookery.jiaoshou.model.Cantxq;
import com.gez.cookery.jiaoshou.model.JsonModel;
import com.gez.cookery.jiaoshou.net.IJsonModelData;
import com.gez.cookery.jiaoshou.net.RestClient;
import com.gez.cookery.jiaoshou.property.Constants;
import com.gez.cookery.jiaoshou.util.BitmapManager;
import com.gez.cookery.jiaoshou.util.StringUtils;
import com.gez.cookery.jiaoshou.util.UIHelper;
import com.gez.cookery.jiaoshou.util.Utils;
import com.gez.cookery.jiaoshou.widget.ColumnText;
import com.umeng.socialize.controller.RequestType;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;

import android.app.ProgressDialog;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class RestaurantFragment extends SherlockFragment {

	private View mRootView;

	private TextView name;
	private Button share;
	
	private ColumnText caip;
	private ColumnText haop;
	private ColumnText pingjscsj;
	private ColumnText qisjg;
	private ColumnText peisf;
	private ColumnText yinysj;
	private ColumnText dingz;
	private ColumnText dianh;
	private ColumnText jianj;
	private ColumnText gongg;
	
	private ImageView mainLogo;
	private ImageView zhaop1;
	private ImageView zhaop2;
	private ImageView zhaop3;
	private ImageView zhaop4;
	private ImageView zhaop5;
	
	private BitmapManager bmpManager;
	
	//餐厅ID
	private String businessId;
	
	//餐厅详情
	private Cantxq cant;
	
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	// 首先在您的Activity中添加如下成员变量
	final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share", RequestType.SOCIAL);
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mRootView = inflater.inflate(R.layout.business_detail_fragment, null);
		
		name = (TextView) mRootView.findViewById(R.id.business_detail_name);
		share = (Button) mRootView.findViewById(R.id.business_detail_button_share);
		
		caip = (ColumnText) mRootView.findViewById(R.id.business_detail_frag_caip);
		haop = (ColumnText) mRootView.findViewById(R.id.business_detail_frag_haop);
		pingjscsj = (ColumnText) mRootView.findViewById(R.id.business_detail_frag_pingjscsj);
		qisjg = (ColumnText) mRootView.findViewById(R.id.business_detail_frag_qisjg);
		peisf = (ColumnText) mRootView.findViewById(R.id.business_detail_frag_peisf);
		yinysj = (ColumnText) mRootView.findViewById(R.id.business_detail_frag_yinysj);
		dingz = (ColumnText) mRootView.findViewById(R.id.business_detail_frag_dingz);
		dianh = (ColumnText) mRootView.findViewById(R.id.business_detail_frag_dianh);
		jianj = (ColumnText) mRootView.findViewById(R.id.business_detail_frag_jianj);
		gongg = (ColumnText) mRootView.findViewById(R.id.business_detail_frag_gongg);
		
		mainLogo = (ImageView) mRootView.findViewById(R.id.business_detail_image);
		zhaop1 = (ImageView) mRootView.findViewById(R.id.restaurant_detail_image1);
		zhaop2 = (ImageView) mRootView.findViewById(R.id.restaurant_detail_image2);
		zhaop3 = (ImageView) mRootView.findViewById(R.id.restaurant_detail_image3);
		zhaop4 = (ImageView) mRootView.findViewById(R.id.restaurant_detail_image4);
		zhaop5 = (ImageView) mRootView.findViewById(R.id.restaurant_detail_image5);
		
		share.setOnClickListener(shareClick);

		bmpManager = new BitmapManager(Constants.businessLogo);
		
		showDetail();
		
		return mRootView;
	}

	private View.OnClickListener shareClick = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if (cant != null) {
				// 设置分享内容
				mController.setShareContent(cant.getCantmc() + "餐厅真心不错哦!(来自格致餐厅安卓APP)");
				
				// 是否只有已登录用户才能打开分享选择页
			    mController.openShare(RestaurantFragment.this.getActivity(), false);
			}
		}
	};
	
	private void showDetail() {
		final ProgressDialog progressDialog = UIHelper.showProgressDialog(this.getActivity(), "正在加载餐厅数据");

		RestClient.getBusinessDetail(businessId, new IJsonModelData(){
			public void onReturn(JsonModel data) {
				progressDialog.dismiss();
				
				if (data != null) {
					Cantxq detail = (Cantxq)data;
					cant = detail;

					share.setVisibility(View.VISIBLE);
					name.setText(detail.getCantmc());
					
					caip.setTextViewText(String.format("菜品类型：%s", detail.getCaiplx()));
					haop.setTextViewText(String.format("好评%s次，差评%s次", detail.getHaopcs(), detail.getChapcs()));
					pingjscsj.setTextViewText(String.format("平均送达时间：%s分钟", detail.getPingjscsj()));
					qisjg.setTextViewText(String.format("起送价格：%s元", detail.getQisjg()));
					peisf.setTextViewText(String.format("配送费：%s元", detail.getPeisf()));
					yinysj.setTextViewText(String.format("营业时间：%s - %s", detail.getYingykssj(), detail.getYingyjssj()));
					dingz.setTextViewText(String.format("餐厅地址：%s", detail.getCantdz()));
					dianh.setTextViewText(String.format("餐厅电话：%s", detail.getCantdh()));
					jianj.setTextViewText(String.format("餐厅简介：%s", detail.getCantjj()));
					gongg.setTextViewText(String.format("餐厅公告：%s", detail.getCantgg()));
					
					try {
						bmpManager.loadBitmap(Utils.guidToInt(detail.getId()), RestClient.getImageUrl(detail.getCantbstp()), mainLogo);
						
						if (!StringUtils.isEmpty(detail.getCantzp1())) {
							System.out.println("heheheeeheheh" + detail.getCantzp1());
							zhaop1.setVisibility(View.VISIBLE);
						    bmpManager.loadBitmap(RestClient.getImageUrl(detail.getCantzp1()), zhaop1);
						}
						if (!StringUtils.isEmpty(detail.getCantzp2())) {
							zhaop2.setVisibility(View.VISIBLE);
						    bmpManager.loadBitmap(RestClient.getImageUrl(detail.getCantzp2()), zhaop2);
						}
						if (!StringUtils.isEmpty(detail.getCantzp3())) {
							zhaop3.setVisibility(View.VISIBLE);
						    bmpManager.loadBitmap(RestClient.getImageUrl(detail.getCantzp3()), zhaop3);
						}
						if (!StringUtils.isEmpty(detail.getCantzp4())) {
							zhaop4.setVisibility(View.VISIBLE);
						    bmpManager.loadBitmap(RestClient.getImageUrl(detail.getCantzp4()), zhaop4);
						}
						if (!StringUtils.isEmpty(detail.getCantzp5())) {
							zhaop5.setVisibility(View.VISIBLE);
						    bmpManager.loadBitmap(RestClient.getImageUrl(detail.getCantzp5()), zhaop5);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					
				}
			}
		});
	}
}
