package com.gez.cookery.jiaoshou.activity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.view.MenuItem;
import com.gez.cookery.jiaoshou.R;
import com.gez.cookery.jiaoshou.model.GukBasic;
import com.gez.cookery.jiaoshou.model.JsonModel;
import com.gez.cookery.jiaoshou.model.User;
import com.gez.cookery.jiaoshou.model.UserQQTokenInfo;
import com.gez.cookery.jiaoshou.net.IJsonModelData;
import com.gez.cookery.jiaoshou.net.RestClient;
import com.gez.cookery.jiaoshou.property.Constants;
import com.gez.cookery.jiaoshou.property.QQConstants;
import com.gez.cookery.jiaoshou.property.WBConstants;
import com.gez.cookery.jiaoshou.util.JsonUtil;
import com.gez.cookery.jiaoshou.util.UIHelper;
import com.gez.cookery.jiaoshou.widget.ColumnInputText;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuth;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.LogoutAPI;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQAuth;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

public class MyAccountActivity extends BaseActivity implements
		View.OnClickListener {

	private Button saveButton;
	private Button weiboButton;
	private Button qqLoginButton;
	private TextView myWeiboScreenName;
	private TextView myQQNickname;
	private Tencent mTencent;
	private UserInfo mInfo;
	private boolean isSaveData = true;
	private boolean isExit = false;
	private TextWatcher myTextWatcher;

	private ColumnInputText myAddress_1;
	private ColumnInputText myAddress_2;
	private ColumnInputText myAddress_3;
	private ColumnInputText myAddress_4;
	private ColumnInputText myAddress_5;
	private ColumnInputText myMobileNumber_1;
	private ColumnInputText myMobileNumber_2;

	private String openid;// 用于唯一标识用户身份
	private String access_token;// 用户进行应用邀请、分享、支付等基本业务请求的凭据
	private String expires_in;// access_token的有效时间

	public static QQAuth mQQAuth;// QQ认证

	/** 微博 Web 授权类，提供登录等功能 */
	private WeiboAuth mWeiboAuth;

	/** 封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能 */
	private Oauth2AccessToken mAccessToken;

	/** 注意：SsoHandler 仅当 SDK 支持 SSO 时有效 */
	private SsoHandler mSsoHandler;

	/** 登出操作对应的listener */
	private LogOutRequestListener mLogoutListener = new LogOutRequestListener();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_my_account);

		setTitle(R.string.my_account);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		// 阻止EditText自动获得焦点弹出输入法
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		myAddress_1 = (ColumnInputText) findViewById(R.id.my_location_1);
		myAddress_2 = (ColumnInputText) findViewById(R.id.my_location_2);
		myAddress_3 = (ColumnInputText) findViewById(R.id.my_location_3);
		myAddress_4 = (ColumnInputText) findViewById(R.id.my_location_4);
		myAddress_5 = (ColumnInputText) findViewById(R.id.my_location_5);
		myMobileNumber_1 = (ColumnInputText) findViewById(R.id.my_mobile_number_1);
		myMobileNumber_2 = (ColumnInputText) findViewById(R.id.my_mobile_number_2);

		saveButton = (Button) findViewById(R.id.submit_account_info_button_ok);
		weiboButton = (Button) findViewById(R.id.connect_weibo_button);
		qqLoginButton = (Button) findViewById(R.id.connect_qq_button);
		myWeiboScreenName = (TextView) findViewById(R.id.connect_weibo_detail_name);
		myQQNickname = (TextView) findViewById(R.id.connect_qq_detail_name);

		// 创建微博实例
		mWeiboAuth = new WeiboAuth(this, WBConstants.APP_KEY,
				WBConstants.REDIRECT_URL, WBConstants.SCOPE);

		// 通过单点登录 (SSO) 获取 Token
		weiboButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				if (qqLoginButton.getText().equals("解除绑定")) {
					Toast.makeText(
							MyAccountActivity.this,
							"\n        尊敬的用户，您已绑定QQ账号，请先对其解除绑定，才能完成微博账号的绑定！\n",
							Toast.LENGTH_LONG).show();
				} else {
					if (weiboButton.getText().equals("绑定")) {
						mSsoHandler = new SsoHandler(MyAccountActivity.this,
								mWeiboAuth);
						mSsoHandler.authorize(new AuthListener());
					} else if (weiboButton.getText().equals("解除绑定")) {

						if (mAccessToken != null
								&& mAccessToken.isSessionValid()) {
							// new
							// LogoutAPI(mAccessToken).logout(mLogoutRequestListener);
							ActivityGlobal.setSpString(
									WBConstants.MY_NICK_NAME, null);
							new LogoutAPI(AccessTokenKeeper
									.readAccessToken(MyAccountActivity.this))
									.logout(mLogoutListener);
						} else {
							Toast.makeText(MyAccountActivity.this, "解除失败",
									Toast.LENGTH_SHORT).show();
							myWeiboScreenName.setText(R.string.connect_weibo_name);
							weiboButton.setText(R.string.connect_submit_name);
							weiboButton.setBackgroundResource(R.drawable.important_btn_bg);
						}
					}
				}
				isSaveData = false;
			}
		});

		// 从 SharedPreferences 中读取上次已保存好 AccessToken 等信息，
		// 第一次启动本应用，AccessToken 不可用
		mAccessToken = AccessTokenKeeper.readAccessToken(this);
		if (mAccessToken.isSessionValid()) {
			setMyScreenName();
		}

		mQQAuth = QQAuth.createInstance(QQConstants.APP_ID,
				MyAccountActivity.this.getApplicationContext());
		mTencent = Tencent.createInstance(QQConstants.APP_ID,
				MyAccountActivity.this);

		openid = ActivityGlobal.getSpString(QQConstants.OPEN_ID, null);
		access_token = ActivityGlobal.getSpString(QQConstants.ACCESS_TOKEN,
				null);
		expires_in = ActivityGlobal.getSpString(QQConstants.EXPIRES_IN, null);

		if (null == expires_in) {
		} else if (((Long.parseLong(expires_in) - System.currentTimeMillis()) / 1000) <= 0) {

			Toast.makeText(MyAccountActivity.this, "您的QQ认证已过期，请重新绑定！",
					Toast.LENGTH_SHORT).show();
			myQQNickname.setText(R.string.connect_qq_name);
			qqLoginButton.setText(R.string.connect_submit_name);
			qqLoginButton.setBackgroundResource(R.drawable.important_btn_bg);
		} else {

			mTencent.setOpenId(openid);
			mTencent.setAccessToken(access_token,
					Long.toString((Long.parseLong(expires_in) - System
							.currentTimeMillis()) / 1000));
			setMyQQNickName();
		}
		qqLoginButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (weiboButton.getText().equals("解除绑定")) {
					Toast.makeText(
							MyAccountActivity.this,
							"\n        尊敬的用户，您已绑定微博账号，请先对其解除绑定，才能完成QQ账号的绑定！\n",
							Toast.LENGTH_LONG).show();
				} else {
					if (qqLoginButton.getText().equals("绑定")) {

						if (!mQQAuth.isSessionValid()) {
							IUiListener listener = new BaseUiListener() {
								@Override
								protected void doComplete(JSONObject values) {
									setMyQQNickName();
								}
							};
							// mQQAuth.login(this, "all", listener);
							mTencent.loginWithOEM(MyAccountActivity.this,
									"all", listener, "10000144", "10000144",
									"xxxx");
						} else {
							mQQAuth.logout(MyAccountActivity.this);
							setMyQQNickName();
						}
					} else if (qqLoginButton.getText().equals("解除绑定")) {

						mTencent.logout(MyAccountActivity.this);
						ActivityGlobal.setSpString(QQConstants.OPEN_ID, null);
						ActivityGlobal.setSpString(QQConstants.ACCESS_TOKEN,
								null);
						ActivityGlobal
								.setSpString(QQConstants.EXPIRES_IN, null);
						myQQNickname.setText(R.string.connect_qq_name);
						qqLoginButton.setText(R.string.connect_submit_name);
						qqLoginButton.setBackgroundResource(R.drawable.important_btn_bg);
					}
				}
				isSaveData = false;
			}
		});

		refreshMyInfo();

		myAddress_1.setButtonClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				myAddress_1.setEditText("");
			}
		});
		myAddress_2.setButtonClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				myAddress_2.setEditText("");
			}
		});
		myAddress_3.setButtonClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				myAddress_3.setEditText("");
			}
		});
		myAddress_4.setButtonClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				myAddress_4.setEditText("");
			}
		});
		myAddress_5.setButtonClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				myAddress_5.setEditText("");
			}
		});
		myMobileNumber_1.setButtonClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				myMobileNumber_1.setEditText("");
			}
		});
		myMobileNumber_2.setButtonClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				myMobileNumber_2.setEditText("");
			}
		});
		saveButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 保存数据
				saveMyData();
			}
		});
		myTextWatcher = new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}
			@Override
			public void afterTextChanged(Editable s) {
				isSaveData = false;
			}
		};
	}

	/**
	 * 保存数据
	 */
	protected void saveMyData() {
		final ProgressDialog progressDialog = UIHelper.showProgressDialog(
				MyAccountActivity.this, "正在保存数据...");
		RestClient.bindAccount(
				ActivityGlobal.getSpString(QQConstants.OPEN_ID, null),
				mAccessToken.getUid(), myMobileNumber_1.getEditText(),
				myMobileNumber_2.getEditText(), myAddress_1.getEditText(),
				myAddress_2.getEditText(), myAddress_3.getEditText(),
				myAddress_4.getEditText(), myAddress_5.getEditText(),
				new IJsonModelData() {
					@Override
					public void onReturn(JsonModel data) {
						progressDialog.dismiss();
						if (data != null) {
							GukBasic userInfo = (GukBasic) data;
							User user = ActivityGlobal.getUser();
							ActivityGlobal.setSpString(Constants.USER_ID,
									userInfo.getId());

							user.setId(userInfo.getId());
							user.setDiz1(userInfo.getDiz1());
							user.setDiz2(userInfo.getDiz2());
							user.setDiz3(userInfo.getDiz3());
							user.setDiz4(userInfo.getDiz4());
							user.setDiz5(userInfo.getDiz5());
							user.setMordh(userInfo.getMordh());
							user.setMordz(userInfo.getMordz());

							user.setDianh1(userInfo.getDianh1());
							user.setDianh2(userInfo.getDianh2());


							myAddress_1.setEditText(userInfo.getDiz1());
							myAddress_2.setEditText(userInfo.getDiz2());
							myAddress_3.setEditText(userInfo.getDiz3());
							myAddress_4.setEditText(userInfo.getDiz4());
							myAddress_5.setEditText(userInfo.getDiz5());
							myMobileNumber_1.setEditText(userInfo.getDianh1());
							myMobileNumber_2.setEditText(userInfo.getDianh2());
							UIHelper.showToast(MyAccountActivity.this, "保存成功.");
							isSaveData = true;
						} else
							UIHelper.showToast(MyAccountActivity.this, "保存失败.");

						if (isExit) {
							finish();
						}
					}
				});
	}

	/**
	 * 更新个人信息
	 */
	private void refreshMyInfo() {
		final ProgressDialog progressDialog = UIHelper.showProgressDialog(
				MyAccountActivity.this, "正在加载数据...");
		String userId = ActivityGlobal.getUserId();
		RestClient.getCustomerInfo(userId, new IJsonModelData() {
			public void onReturn(JsonModel data) {
				if (data != null) {
					GukBasic detail = (GukBasic) data;
					if (detail.getDiz1() != null) {
						myAddress_1.setEditText(detail.getDiz1());
						myAddress_1.setTextChangedListener(myTextWatcher);
					}
					if (detail.getDiz2() != null) {
						myAddress_2.setEditText(detail.getDiz2());
						myAddress_2.setTextChangedListener(myTextWatcher);
					}
					if (detail.getDiz3() != null) {
						myAddress_3.setEditText(detail.getDiz3());
						myAddress_3.setTextChangedListener(myTextWatcher);
					}
					if (detail.getDiz4() != null) {
						myAddress_4.setEditText(detail.getDiz4());
						myAddress_4.setTextChangedListener(myTextWatcher);
					}
					if (detail.getDiz5() != null) {
						myAddress_5.setEditText(detail.getDiz5());
						myAddress_5.setTextChangedListener(myTextWatcher);
					}
					if (detail.getDianh1() != null) {
						myMobileNumber_1.setEditText(detail.getDianh1());
						myMobileNumber_1.setTextChangedListener(myTextWatcher);
					}
					if (detail.getDianh2() != null) {
						myMobileNumber_2.setEditText(detail.getDianh2());
						myMobileNumber_2.setTextChangedListener(myTextWatcher);
					}
				}
				progressDialog.dismiss();
			}
		});
	}

	/**
	 * 设置微博昵称
	 */
	protected void setMyScreenName() {
		myWeiboScreenName.setText(ActivityGlobal.getSpString(
				WBConstants.MY_NICK_NAME, null));
		weiboButton.setText(R.string.connect_remove_name);
		weiboButton.setBackgroundResource(R.drawable.normal_btn_bg);
	}

	/**
	 * 设置QQ昵称
	 */
	protected void setMyQQNickName() {
		if (mQQAuth != null && mQQAuth.isSessionValid()) {
			IUiListener listener = new IUiListener() {
				@Override
				public void onError(UiError e) {
				}

				@Override
				public void onComplete(final Object response) {
					JSONObject responseObject = (JSONObject) response;
					if (responseObject.has("nickname")) {
						try {
							System.out.println(responseObject.getString("nickname"));
							myQQNickname.setText(responseObject
									.getString("nickname"));
						} catch (JSONException e) {
							e.printStackTrace();
						}
						qqLoginButton.setText(R.string.connect_remove_name);
						qqLoginButton.setBackgroundResource(R.drawable.normal_btn_bg);
					} else {
						try {
							Toast.makeText(MyAccountActivity.this, responseObject.getString("msg"), Toast.LENGTH_LONG).show();
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
						
				}

				@Override
				public void onCancel() {
				}
			};
			mInfo = new UserInfo(this, mQQAuth.getQQToken());
			mInfo.getUserInfo(listener);
		}
	}

	/**
	 * 当 SSO 授权 Activity 退出时，该函数被调用。
	 * 
	 * @see {@link Activity#onActivityResult}
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		// SSO 授权回调
		// 重要：发起 SSO 登录的 Activity 必须重写 onActivityResult
		if (mSsoHandler != null) {
			mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
		}
	}

	/**
	 * 微博认证授权回调类。 1. SSO 授权时，需要在 {@link #onActivityResult} 中调用
	 * {@link SsoHandler#authorizeCallBack} 后， 该回调才会被执行。 2. 非 SSO
	 * 授权时，当授权结束后，该回调就会被执行。 当授权成功后，请保存该 access_token、expires_in、uid 等信息到
	 * SharedPreferences 中。
	 */
	class AuthListener implements WeiboAuthListener {

		@Override
		public void onComplete(Bundle values) {
			// 从 Bundle 中解析 Token
			mAccessToken = Oauth2AccessToken.parseAccessToken(values);
			if (mAccessToken.isSessionValid()) {
				// 显示昵称
				ActivityGlobal.setSpString(WBConstants.MY_NICK_NAME, values
						.getString("com.sina.weibo.intent.extra.NICK_NAME"));
				setMyScreenName();

				// 保存 Token 到 SharedPreferences
				AccessTokenKeeper.writeAccessToken(MyAccountActivity.this,
						mAccessToken);
				Toast.makeText(MyAccountActivity.this,
						R.string.weibosdk_toast_auth_success,
						Toast.LENGTH_SHORT).show();
			} else {
				// 当您注册的应用程序签名不正确时，就会收到 Code，请确保签名正确
				String code = values.getString("code");
				String message = getString(R.string.weibosdk_toast_auth_failed);
				if (!TextUtils.isEmpty(code)) {
					message = message + "\nObtained the code: " + code;
				}
				Toast.makeText(MyAccountActivity.this, message,
						Toast.LENGTH_SHORT).show();
			}
		}

		@Override
		public void onCancel() {
			Toast.makeText(MyAccountActivity.this,
					R.string.weibosdk_toast_auth_canceled, Toast.LENGTH_SHORT)
					.show();
		}

		@Override
		public void onWeiboException(WeiboException e) {
			Toast.makeText(MyAccountActivity.this,
					"Auth exception : " + e.getMessage(), Toast.LENGTH_SHORT)
					.show();
		}
	}

	private class BaseUiListener implements IUiListener {

		@Override
		public void onComplete(Object response) {
			UserQQTokenInfo tokenInfo = JsonUtil.fromJson(response.toString(),
					UserQQTokenInfo.class);
			ActivityGlobal.setSpString(QQConstants.OPEN_ID,
					tokenInfo.getOpenid());
			ActivityGlobal.setSpString(QQConstants.ACCESS_TOKEN,
					tokenInfo.getAccess_token());
			ActivityGlobal
					.setSpString(
							QQConstants.EXPIRES_IN,
							Long.toString(System.currentTimeMillis()
									+ Long.parseLong(tokenInfo.getExpires_in())
									* 1000));
			doComplete((JSONObject) response);
		}

		protected void doComplete(JSONObject values) {

		}

		@Override
		public void onError(UiError e) {
			Toast.makeText(MyAccountActivity.this, "onError: " + e.errorDetail,
					Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onCancel() {
			Toast.makeText(MyAccountActivity.this,
					R.string.weibosdk_toast_auth_canceled, Toast.LENGTH_SHORT)
					.show();
		}
	}

	@Override
	public void onClick(View v) {
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			if (isSaveData) {
				finish();
			} else dialog_Exit(MyAccountActivity.this);

			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * 登出按钮的监听器，接收登出处理结果。（API 请求结果的监听器）
	 */
	private class LogOutRequestListener implements RequestListener {
		@Override
		public void onComplete(String response) {
			if (!TextUtils.isEmpty(response)) {
				try {
					JSONObject obj = new JSONObject(response);
					String value = obj.getString("result");

					if ("true".equalsIgnoreCase(value)) {
						AccessTokenKeeper.clear(MyAccountActivity.this);
						myWeiboScreenName.setText(R.string.connect_weibo_name);
						weiboButton.setText(R.string.connect_submit_name);
						weiboButton.setBackgroundResource(R.drawable.important_btn_bg);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}

		@Override
		public void onComplete4binary(ByteArrayOutputStream responseOS) {
			// Do nothing
		}

		@Override
		public void onIOException(IOException e) {
			Toast.makeText(MyAccountActivity.this, "解除失败", Toast.LENGTH_SHORT)
					.show();
		}

		@Override
		public void onError(WeiboException e) {
			Toast.makeText(MyAccountActivity.this, "解除失败", Toast.LENGTH_SHORT)
					.show();
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (((keyCode == KeyEvent.KEYCODE_BACK) || (keyCode == KeyEvent.KEYCODE_HOME))
				&& event.getRepeatCount() == 0) {
			if (isSaveData) {
				finish();
			} else dialog_Exit(MyAccountActivity.this);
		}
		return false;
	}

	public void dialog_Exit(Context context) {
		Builder builder = new Builder(context);
		builder.setIcon(R.drawable.alert_dialog_icon);
		builder.setTitle("提示");
		builder.setMessage("数据发生改变，是否保存 ？");

		builder.setPositiveButton("否", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				MyAccountActivity.this.finish();
			}
		});

		builder.setNegativeButton("是", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				// 保存数据
				saveMyData();
				isExit = true;
			}
		});
		builder.create().show();
	}
}
