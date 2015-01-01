package com.gez.cookery.jiaoshou.activity;

import java.util.UUID;

import com.gez.cookery.jiaoshou.model.GukBasic;
import com.gez.cookery.jiaoshou.model.JsonModel;
import com.gez.cookery.jiaoshou.model.User;
import com.gez.cookery.jiaoshou.net.IJsonModelData;
import com.gez.cookery.jiaoshou.net.RestClient;
import com.gez.cookery.jiaoshou.property.Constants;
import com.gez.cookery.jiaoshou.property.QQConstants;
import com.gez.cookery.jiaoshou.property.WBConstants;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class ActivityGlobal extends Application {

	private static ActivityGlobal mInstance = null;
	private static Context context;
	private static User user = new User();
	private boolean mKeyRight = true;

	@Override
	public void onCreate() {
		super.onCreate();
		context = getApplicationContext();
		
		String userId = getSpString(Constants.USER_ID, null);
		if (userId == null) {
			userId = UUID.randomUUID().toString();
		}
		setSpString(Constants.USER_ID, userId);
		user.setId(userId);

		//获取用户信息
		getUserInfo();
		
		mInstance = this;
	}

	public static String getUserId() {
		return user.getId();
	}

	public static User getUser() {
		return user;
	}

	public static void resetUserId() {
		String userId = UUID.randomUUID().toString();
		user = new User();
		user.setId(userId);
		setSpString(Constants.USER_ID, userId);
		
		ActivityGlobal.setSpString(QQConstants.OPEN_ID, null);
		ActivityGlobal.setSpString(QQConstants.ACCESS_TOKEN, null);
		ActivityGlobal.setSpString(QQConstants.EXPIRES_IN, null);
		
		ActivityGlobal.setSpString(WBConstants.MY_NICK_NAME, null);
		
		getUserInfo();
	}
	
	public static ActivityGlobal getInstance() {
		return mInstance;
	}

	public static Context getContext() {
		return context;
	}

	public boolean getKeyRight() {
		return mKeyRight;
	}

	public static String getSpString(String key, String def) {
		final SharedPreferences sharedPreferences = getContext()
				.getSharedPreferences(Constants.SP_DATA, Context.MODE_PRIVATE);
		return sharedPreferences.getString(key, def);
	}

	public static boolean getSpBoolean(String key, boolean def) {
		final SharedPreferences sharedPreferences = getContext()
				.getSharedPreferences(Constants.SP_DATA, Context.MODE_PRIVATE);
		return sharedPreferences.getBoolean(key, def);
	}

	public static long getSpLong(String key, long def) {
		final SharedPreferences sharedPreferences = getContext()
				.getSharedPreferences(Constants.SP_DATA, Context.MODE_PRIVATE);
		return sharedPreferences.getLong(key, def);
	}

	public static void setSpLong(String key, long value) {
		final SharedPreferences sharedPreferences = getContext()
				.getSharedPreferences(Constants.SP_DATA, Context.MODE_PRIVATE);
		sharedPreferences.edit().putLong(key, value).commit();
	}

	public static int getSpInt(String key, int def) {
		final SharedPreferences sharedPreferences = getContext()
				.getSharedPreferences(Constants.SP_DATA, Context.MODE_PRIVATE);
		return sharedPreferences.getInt(key, def);
	}

	public static void setSpInt(String key, int value) {
		final SharedPreferences sharedPreferences = getContext()
				.getSharedPreferences(Constants.SP_DATA, Context.MODE_PRIVATE);
		sharedPreferences.edit().putInt(key, value).commit();
	}

	public static void setSpString(String key, String value) {
		final SharedPreferences sharedPreferences = getContext()
				.getSharedPreferences(Constants.SP_DATA, Context.MODE_PRIVATE);
		sharedPreferences.edit().putString(key, value).commit();
	}

	public static void setSpBoolean(String key, boolean value) {
		final SharedPreferences sharedPreferences = getContext()
				.getSharedPreferences(Constants.SP_DATA, Context.MODE_PRIVATE);
		sharedPreferences.edit().putBoolean(key, value).commit();
	}

	public void setKeyRight(boolean keyRight) {
		this.mKeyRight = keyRight;
	}

	public static void getUserInfo() {
		RestClient.getCustomerInfo(user.getId(), new IJsonModelData() {
			
			public void onReturn(JsonModel data) {
				if (data != null) {
					GukBasic detail = (GukBasic) data;
					user.setDiz1(detail.getDiz1());
					user.setDiz2(detail.getDiz2());
					user.setDiz3(detail.getDiz3());
					user.setDiz4(detail.getDiz4());
					user.setDiz5(detail.getDiz5());
					user.setQqh(detail.getQqh());
					user.setWeibm(detail.getWeibm());
					user.setMordh(detail.getMordh());
					user.setMordz(detail.getMordz());
					user.setZhangh(detail.getZhangh());
					user.setDianh1(detail.getDianh1());
					user.setDianh2(detail.getDianh2());
					user.setDuofl(detail.isDuofl());
					user.setShaofl(detail.isShaofl());
					user.setDuojf(detail.isDuojf());
					user.setShaojf(detail.isShaojf());
					user.setBufc(detail.isBufc());
					user.setBufj(detail.isBufj());
					user.setBufs(detail.isBufs());
					user.setBuykz(detail.isBuykz());
				}
			}
		});
	}
}
