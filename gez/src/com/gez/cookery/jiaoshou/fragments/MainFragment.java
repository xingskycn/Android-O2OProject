package com.gez.cookery.jiaoshou.fragments;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.gez.cookery.jiaoshou.R;
import com.gez.cookery.jiaoshou.activity.MainActivity;
import com.gez.cookery.jiaoshou.activity.MakeOrderActivity;
import com.gez.cookery.jiaoshou.contract.ISelectCaip;
import com.gez.cookery.jiaoshou.contract.ISwitchMainFragment;
import com.gez.cookery.jiaoshou.model.CaipBasic;
import com.gez.cookery.jiaoshou.model.CantBasic;
import com.gez.cookery.jiaoshou.model.EnmFoodType;
import com.gez.cookery.jiaoshou.model.JsonModel;
import com.gez.cookery.jiaoshou.net.IJsonModelData;
import com.gez.cookery.jiaoshou.net.RestClient;
import com.gez.cookery.jiaoshou.util.UIHelper;

public class MainFragment extends SherlockFragment implements
		View.OnClickListener, ISwitchMainFragment, ISelectCaip{

	// 餐厅ID
	private String businessId = "30A01E8E-0807-4057-8EB5-ACE1ACEEB15B";
//	private String businessId = "e0e779c5-78ff-470b-a250-00df2c352204";
	// 当前选中按钮ID
	private int currentContentId;
	// 根视图
	private View mRootView;

	private FoodListFragment foodListFragment;
//	private FoodListFragment collectFoodFragment;
	
	
//	private FoodListFragment firstPaperFragment;
	private FirstPaperFragment firstPaperFragment;
	
	
	private RestaurantFragment restaurantFragment;
	private OrderListFragment orderListFragment;
	private Fragment currentFragment;

	// 结算按钮
//	private Button payButton;
//	// 总计
//	private TextView textSum;
	// 选中菜品集合
	private ArrayList<CaipBasic> listCaip = new ArrayList<CaipBasic>();

	// 重新显示需要跳转到的界面(-1为默认)
	private int switchTo = -1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mRootView = inflater.inflate(R.layout.main_fragment, null);

		mRootView.findViewById(R.id.home_button_food).setOnClickListener(this);
		mRootView.findViewById(R.id.home_button_face).setOnClickListener(this);
		mRootView.findViewById(R.id.home_button_restaurant).setOnClickListener(
				this);
		mRootView.findViewById(R.id.home_button_order).setOnClickListener(this);

		showContent(R.id.home_button_face);

		return mRootView;
	}

	@Override
	public void onClick(View paramView) {
		showContent(paramView.getId());
	}

	public void setCurrentSelection(int paramInt) {
		if (this.currentContentId != 0)
			mRootView.findViewById(this.currentContentId).setSelected(false);
		this.currentContentId = paramInt;
		mRootView.findViewById(this.currentContentId).setSelected(true);
	}

	private void showContent(int paramInt) {
		if (this.currentContentId == paramInt) {

		} else {

			setCurrentSelection(paramInt);

			Fragment fragment = null;

			switch (paramInt) {
			default:
			case R.id.home_button_food:	//订购按钮
				if (foodListFragment == null) {
					foodListFragment = new FoodListFragment();
					foodListFragment.setFoodType(EnmFoodType.food_default);
					foodListFragment.setBusinessId(businessId);
					foodListFragment.setMainFragment(this);
				}
				fragment = foodListFragment;
				break;
				
			case R.id.home_button_face:
				if (firstPaperFragment == null) {
					firstPaperFragment = new FirstPaperFragment();
					firstPaperFragment.setFoodType(EnmFoodType.food_tes);
					firstPaperFragment.setBusinessId(businessId);
					firstPaperFragment.setMainFragment(this);
				}
				fragment = firstPaperFragment;
				break;
				
				
			case R.id.home_button_restaurant:
				if (restaurantFragment == null) {
					restaurantFragment = new RestaurantFragment();
					restaurantFragment.setBusinessId(businessId);
				}
				fragment = restaurantFragment;
				break;
			case R.id.home_button_order:
				if (orderListFragment == null) {
					orderListFragment = new OrderListFragment();
				}
				fragment = orderListFragment;
				break;
			}

			if (fragment != null) {
				switchContent(currentFragment, fragment);
			}
		}
	}

	public void switchContent(Fragment from, Fragment to) {

		if (getActivity() instanceof MainActivity) {
			MainActivity mainActivity = (MainActivity) getActivity();

			if (from != null) {
				FragmentTransaction transaction = mainActivity
						.getSupportFragmentManager().beginTransaction();
				if (!to.isAdded()) { // 先判断是否被add过
					transaction.hide(from).add(R.id.main_content_frame, to)
							.commit(); // 隐藏当前的fragment，add下一个到Activity中
				} else {
					transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
				}
			} else {
				FragmentTransaction transaction = mainActivity
						.getSupportFragmentManager().beginTransaction();
				transaction.add(R.id.main_content_frame, to).commit();
			}

			currentFragment = to;
		}
	}

	@Override
	public void switchToBusiness() {
		showContent(R.id.home_button_restaurant);
	}

	@Override
	public void switchToOrder() {
		showContent(R.id.home_button_order);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		LocalBroadcastManager broadcastManager = LocalBroadcastManager
				.getInstance(getActivity());
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("refreshOrder");
		intentFilter.addAction("refreshCollectFoodList");
		BroadcastReceiver refreshOrderReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				if (intent.getAction().equals("refreshOrder")) {
					switchTo = R.id.home_button_order;
					foodListFragment = null;
//					collectFoodFragment = null;
					firstPaperFragment = null;
					listCaip.clear();
//					textSum.setText(String.format(""));
				}
				if (intent.getAction().equals("refreshCollectFoodList")) {
//					collectFoodFragment = null;
				}
			}
		};
		broadcastManager.registerReceiver(refreshOrderReceiver, intentFilter);
	}

	@Override
	public void onStart() {
		super.onStart();

		if (switchTo != -1) {
			showContent(switchTo);
			switchTo = -1;
		}
	}
	
	@Override
	public void selectCaip(CaipBasic caip) {
		listCaip.add(caip);
		float sum = 0;
		for (CaipBasic caipEach : listCaip) {
			sum += caipEach.getJiag();
		}
		
//		textSum.setText(String.format("共%s份，共%s元", listCaip.size(), sum));
	}
}
