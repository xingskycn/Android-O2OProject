package com.gez.cookery.jiaoshou.fragments;

import com.actionbarsherlock.app.SherlockFragment;
import com.gez.cookery.jiaoshou.R;
import com.gez.cookery.jiaoshou.activity.ActivityGlobal;
import com.gez.cookery.jiaoshou.activity.MainActivity;
import com.slidingmenu.lib.app.SlidingFragmentActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainMenuFragment extends SherlockFragment implements
		View.OnClickListener {

	private ViewGroup root;
	private int currentContentId;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		root = (ViewGroup) inflater.inflate(R.layout.main_menu, null);

		this.root.findViewById(R.id.home_menu_main_clear_data)
				.setOnClickListener(this);
		this.root.findViewById(R.id.home_menu_main_about_us)
				.setOnClickListener(this);
		this.root.findViewById(R.id.home_menu_main_feedback)
				.setOnClickListener(this);
		this.root.findViewById(R.id.home_menu_main_business)
				.setOnClickListener(this);
		this.root.findViewById(R.id.home_menu_main_exit)
				.setOnClickListener(this);

		setCurrentSelection(R.id.home_menu_main_business);
		
		return root;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onClick(View paramView) {
		showContent(paramView.getId());
	}

	public SlidingFragmentActivity getSlidingFragmentActivity() {
		return (SlidingFragmentActivity) getActivity();
	}

	public void setCurrentSelection(int paramInt) {
		if (this.root == null)
			return;
		if (this.currentContentId != 0)
			this.root.findViewById(this.currentContentId).setSelected(false);
		this.currentContentId = paramInt;
		this.root.findViewById(this.currentContentId).setSelected(true);
	}

	private void showContent(int paramInt) {
		if (this.currentContentId == paramInt) {
			getSlidingFragmentActivity().getSlidingMenu().showContent();
		} else {
			if (paramInt == R.id.home_menu_main_clear_data) {
				Dialog alertDialog = new AlertDialog.Builder(this.getActivity())
						.setTitle("清除数据")
						.setMessage("您真的要清除数据吗？清空您的订单、地址、电话等信息")
						.setPositiveButton("否",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										
										
									}
								})
						.setNegativeButton("是",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										ActivityGlobal.resetUserId();
										MainMenuFragment.this.setCurrentSelection(R.id.home_menu_main_business);
										MainMenuFragment.this.switchFragment(R.id.home_menu_main_business, true);
									}
								}).create();
				alertDialog.show();
			}
			else {
				setCurrentSelection(paramInt);
				switchFragment(paramInt, false);
			}
		}
	}
	
	private void switchFragment(int paramInt, boolean clear) {
		if (getActivity() == null)
			return;

		if (getActivity() instanceof MainActivity) {
			MainActivity mainActivity = (MainActivity) getActivity();
			mainActivity.showContent(paramInt, clear);
		}
	}
}
