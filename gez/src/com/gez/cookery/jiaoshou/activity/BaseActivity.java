package com.gez.cookery.jiaoshou.activity;

import com.actionbarsherlock.app.SherlockFragmentActivity;

import android.os.Bundle;

/**
 * 应用程序Activity的基类
 */
public class BaseActivity extends SherlockFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// 添加Activity到堆栈
		AppManager.getAppManager().addActivity(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		// 结束Activity&从堆栈中移除
		AppManager.getAppManager().finishActivity(this);
	}
}
