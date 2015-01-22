package com.gez.cookery.jiaoshou.activity;

import java.util.ArrayList;
import java.util.Calendar;

import com.gez.cookery.jiaoshou.R;
import com.gez.cookery.jiaoshou.contract.ISelectValue;
import com.gez.cookery.jiaoshou.fragments.SelectValueFragment;
import com.gez.cookery.jiaoshou.model.CaipBasic;
import com.gez.cookery.jiaoshou.model.CantBasic;
import com.gez.cookery.jiaoshou.model.Dingdmx;
import com.gez.cookery.jiaoshou.model.GukBasic;
import com.gez.cookery.jiaoshou.model.JsonModel;
import com.gez.cookery.jiaoshou.model.Result;
import com.gez.cookery.jiaoshou.model.User;
import com.gez.cookery.jiaoshou.net.IJsonModelData;
import com.gez.cookery.jiaoshou.net.RestClient;
import com.gez.cookery.jiaoshou.property.Constants;
import com.gez.cookery.jiaoshou.util.JsonUtil;
import com.gez.cookery.jiaoshou.util.NumberUtil;
import com.gez.cookery.jiaoshou.util.StringUtils;
import com.gez.cookery.jiaoshou.util.UIHelper;
import com.gez.cookery.jiaoshou.util.Utils;
import com.gez.cookery.jiaoshou.widget.ColumnInputText;
import com.gez.cookery.jiaoshou.widget.ColumnSwitchText;
import com.gez.cookery.jiaoshou.widget.ColumnText;
import com.actionbarsherlock.view.MenuItem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class SubmitOrderActivity extends BaseActivity implements ISelectValue {

	//选择数据
	private String[] addressValue;
	private String[] phoneValue;
	private String[] timeValue = new String[]{"尽快送达", "当前时间后半小时", "当前时间后1小时", "当前时间后1个半小时", "当前时间后2个小时"};
	
	//当前选择窗体(0为未选,1为地址,2为电话,3为送达时间)
	private int currentFragment = 0;
	//确定按钮
	private Button buttonOK;
	//餐厅ID
	private String businessId;
	//当前餐厅
	private CantBasic mRestaurant;
	//选中菜品集合
	private ArrayList<CaipBasic> listCaipSelect = new ArrayList<CaipBasic>();
	//地址
	private ColumnInputText address;
	//联系电话
	private ColumnInputText phone;
	//总价格
	private float sumPrice;
	private ColumnText sumPriceShow;
	//送达时间
	private ColumnInputText time;
	//备注
	private ColumnInputText remark;
		
	private ColumnSwitchText duojf;
	private ColumnSwitchText shaojf;
	private ColumnSwitchText duofl;
	private ColumnSwitchText shaofl;
	private ColumnSwitchText bufc;
	private ColumnSwitchText bufs;
	private ColumnSwitchText bufj;
	private ColumnSwitchText buykz;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.submit_order);
		
		// 阻止EditText自动获得焦点弹出输入法
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		buttonOK = (Button) this.findViewById(R.id.submit_order_button_ok);
		address = (ColumnInputText) this.findViewById(R.id.submit_order_address);	//送餐地址
		phone = (ColumnInputText) this.findViewById(R.id.submit_order_phone);	//联系电话
		sumPriceShow = (ColumnText) this.findViewById(R.id.order_sum_price);
		time = (ColumnInputText) this.findViewById(R.id.submit_order_time);
		remark = (ColumnInputText)this.findViewById(R.id.submit_order_remark);
		
		duojf = (ColumnSwitchText)this.findViewById(R.id.submit_order_duojf);
		shaojf = (ColumnSwitchText)this.findViewById(R.id.submit_order_shaojf);
		duofl = (ColumnSwitchText)this.findViewById(R.id.submit_order_duofl);
		shaofl = (ColumnSwitchText)this.findViewById(R.id.submit_order_shaofl);
		bufc = (ColumnSwitchText)this.findViewById(R.id.submit_order_bufc);
		bufs = (ColumnSwitchText)this.findViewById(R.id.submit_order_bufs);
		bufj = (ColumnSwitchText)this.findViewById(R.id.submit_order_bufj);
		buykz = (ColumnSwitchText)this.findViewById(R.id.submit_order_buykz);
		
		User user = ActivityGlobal.getUser();
		if (user.getDiz1() != null && !user.getDiz1().toString().trim().equals("")) {
			address.setEditText(user.getDiz1());
		}
		if (user.getDianh1() != null && !user.getDianh1().toString().trim().equals("")) {
			phone.setEditText(user.getDianh1());
		}
		
		setTitle(R.string.submit_order_title);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		mRestaurant = (CantBasic) this.getIntent().getExtras().getSerializable("mRestaurant");
		sumPrice = this.getIntent().getExtras().getFloat("sumPrice");
		sumPriceShow.setTextViewText("总价格：" + NumberUtil.getFloatString(sumPrice) + "元");
		this.businessId = mRestaurant.getId();
		
		//获取所选菜品 一份菜品一个对象 所以有重复 需要重新计算得到选中菜品和数量
		listCaipSelect = (ArrayList<CaipBasic>)this.getIntent().getExtras().getSerializable("list");

		if (mRestaurant.isXiansdjf()) {
			duojf.setVisibility(View.VISIBLE);
			duojf.setTextView("多加饭");
			duojf.setToggleButtonChecked(user.isDuojf());
			duojf.setButtonClickListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					if (isChecked) {
						duojf.setToggleButtonDirection(Gravity.RIGHT);
						shaojf.setToggleButtonChecked(false);
						shaojf.setToggleButtonDirection(Gravity.LEFT);
					} else duojf.setToggleButtonDirection(Gravity.LEFT);
				}
			});
		}
		if (mRestaurant.isXianssjf()) {
			shaojf.setVisibility(View.VISIBLE);
			shaojf.setTextView("少加饭");
			shaojf.setToggleButtonChecked(user.isShaojf());
			shaojf.setButtonClickListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					if (isChecked) {
						shaojf.setToggleButtonDirection(Gravity.RIGHT);
						duojf.setToggleButtonChecked(false);
						duojf.setToggleButtonDirection(Gravity.LEFT);
					} else shaojf.setToggleButtonDirection(Gravity.LEFT);
				}
			});
		}
		if (mRestaurant.isXiansdfl()) {
			duofl.setVisibility(View.VISIBLE);
			duofl.setTextView("多放辣");
			duofl.setToggleButtonChecked(user.isDuofl());
			duofl.setButtonClickListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					if (isChecked) {
						duofl.setToggleButtonDirection(Gravity.RIGHT);
						shaofl.setToggleButtonChecked(false);
						shaofl.setToggleButtonDirection(Gravity.LEFT);
					} else duofl.setToggleButtonDirection(Gravity.LEFT);
				}
			});
		}
		if (mRestaurant.isXianssfl()) {
			shaofl.setVisibility(View.VISIBLE);
			shaofl.setTextView("少放辣");
			shaofl.setToggleButtonChecked(user.isShaofl());
			shaofl.setButtonClickListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					if (isChecked) {
						shaofl.setToggleButtonDirection(Gravity.RIGHT);
						duofl.setToggleButtonChecked(false);
						duofl.setToggleButtonDirection(Gravity.LEFT);
					} else shaofl.setToggleButtonDirection(Gravity.LEFT);
				}
			});
		}
		if (mRestaurant.isXiansbfc()) {
			bufc.setVisibility(View.VISIBLE);
			bufc.setTextView("不放葱");
			bufc.setToggleButtonChecked(user.isBufc());
		}
		if (mRestaurant.isXiansbfs()) {
			bufs.setVisibility(View.VISIBLE);
			bufs.setTextView("不放蒜");
			bufs.setToggleButtonChecked(user.isBufs());
		}
		if (mRestaurant.isXiansbfj()) {
			bufj.setVisibility(View.VISIBLE);
			bufj.setTextView("不放姜");
			bufj.setToggleButtonChecked(user.isBufj());
		}
		if (mRestaurant.isXiansbykz()) {
			buykz.setVisibility(View.VISIBLE);
			buykz.setTextView("不要筷子");
			buykz.setToggleButtonChecked(user.isBuykz());
		}
		
		//确定按钮点击事件
		buttonOK.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String addressStr = address.getEditText(); 
				if (addressStr == null || "".equals(addressStr)) {
					UIHelper.showToast(SubmitOrderActivity.this, "请输入送餐地址");
					return;
				}
				
				String phoneStr = phone.getEditText();
				if (phoneStr == null || "".equals(phoneStr)) {
					UIHelper.showToast(SubmitOrderActivity.this, "请输入送餐电话");
					return;
				}
				else if (!Utils.isPhone(phoneStr)) {
					UIHelper.showToast(SubmitOrderActivity.this, "请输入正确的送餐电话");
					return;
				}
				
				String timeStr = time.getEditText();
				String songcsjxz = "2";
				if (timeStr == null || "".equals(timeStr)) {
					UIHelper.showToast(SubmitOrderActivity.this, "请输入正确的送餐时间");
					return;
				}
				else {
					if (timeStr.equals(timeValue[0])) {
						songcsjxz = "1";
						timeStr = "";
					}
					else {
						Calendar calendar = Calendar.getInstance();
						boolean find = false;
						if (timeStr.equals(timeValue[1])) {
							calendar.add(Calendar.MINUTE, 30);
							find = true;
						}
						if (timeStr.equals(timeValue[2])) {
							calendar.add(Calendar.MINUTE, 60);
							find = true;
						}
						if (timeStr.equals(timeValue[3])) {
							calendar.add(Calendar.MINUTE, 90);
							find = true;
						}
						if (timeStr.equals(timeValue[4])) {
							calendar.add(Calendar.MINUTE, 120);
							find = true;
						}
						
						if (!find) {
							UIHelper.showToast(SubmitOrderActivity.this, "请输入正确的送餐时间");
							return;
						}
						
						timeStr = StringUtils.toDateString(calendar.getTime());
					}
				}//else
				
				ArrayList<Dingdmx> dingdmxtj = new ArrayList<Dingdmx>();
				for (CaipBasic caip : listCaipSelect) {
					Dingdmx dingd = new Dingdmx();
					dingd.setCaipId(caip.getId());
					dingd.setFens(caip.getCount());
					dingd.setDanj(caip.getJiag());
					dingdmxtj.add(dingd);
				}
				
				String dingdmx = JsonUtil.toJson(dingdmxtj);
				
				final ProgressDialog progressDialog = UIHelper.showProgressDialog(SubmitOrderActivity.this, "正在提交订单");

				RestClient.submitOrders(
						phoneStr, 
						addressStr, 
						remark.getEditText(), 
						songcsjxz, 
						timeStr, 
						"1", 
						businessId, 
						dingdmx,
						String.valueOf(mRestaurant.getPeisf()),
						duojf.getToggleButtonChecked().toString(),
						shaojf.getToggleButtonChecked().toString(),
						duofl.getToggleButtonChecked().toString(),
						shaofl.getToggleButtonChecked().toString(),
						bufc.getToggleButtonChecked().toString(),
						bufs.getToggleButtonChecked().toString(),
						bufj.getToggleButtonChecked().toString(),
						buykz.getToggleButtonChecked().toString(),
						new IJsonModelData() {
					public void onReturn(JsonModel data) {
						progressDialog.dismiss();
						
						if (data != null) {
							if (data instanceof GukBasic){
								
								GukBasic userInfo = (GukBasic) data;
								User user = ActivityGlobal.getUser();
								ActivityGlobal.setSpString(Constants.USER_ID, userInfo.getId());
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
								UIHelper.showToast(SubmitOrderActivity.this, "提交订单成功.");
								
								// 发送广播消息
								Intent intent = new Intent("refreshOrder");
								LocalBroadcastManager.getInstance(SubmitOrderActivity.this).sendBroadcast(intent);
								
								AppManager.getAppManager().finishAllActivity();
							} else if (data instanceof Result) {
								Result result = (Result)data;
								if (!result.isSuccess()) {
									UIHelper.showToast(SubmitOrderActivity.this, result.getMessage());
								}
							}
						} else UIHelper.showToast(SubmitOrderActivity.this, "提交订单失败.");
					}
				});
			}
		});
		
		//初始化数据
		ArrayList<String> arrayAddress = new ArrayList<String>();
		if (user.getDiz1() != null && !"".equals(user.getDiz1())) {
			arrayAddress.add(user.getDiz1());
		}
		if (user.getDiz2() != null && !"".equals(user.getDiz2())) {
			arrayAddress.add(user.getDiz2());
		}
		if (user.getDiz3() != null && !"".equals(user.getDiz3())) {
			arrayAddress.add(user.getDiz3());
		}
		if (user.getDiz4() != null && !"".equals(user.getDiz4())) {
			arrayAddress.add(user.getDiz4());
		}
		if (user.getDiz5() != null && !"".equals(user.getDiz5())) {
			arrayAddress.add(user.getDiz5());
		}
		if (arrayAddress.size() > 0) {
			addressValue = (String[])arrayAddress.toArray(new String[0]);
		}
		
		ArrayList<String> arrayPhone = new ArrayList<String>();
		if (user.getDianh1() != null && !"".equals(user.getDianh1())) {
			arrayPhone.add(user.getDianh1());
		}
		if (user.getDianh2() != null && !"".equals(user.getDianh2())) {
			arrayPhone.add(user.getDianh2());
		}
		if (arrayPhone.size() > 0) {
			phoneValue = (String[])arrayPhone.toArray(new String[0]);
		}
		
		address.setButtonClickListener(addressSelect);
		phone.setButtonClickListener(phoneSelect);
		
		time.setEditText("尽快送达");
		time.setButtonClickListener(timeSelect);
	}

	private View.OnClickListener addressSelect = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if (addressValue != null) {
				currentFragment = 1;
				SelectValueFragment newFragment = new SelectValueFragment();
				newFragment.setTitle("选择送餐地址");
				newFragment.setListValue(addressValue);
		        newFragment.show(getSupportFragmentManager().beginTransaction(), "addressDialog");
			}
		}
	};
	
	private View.OnClickListener phoneSelect = new View.OnClickListener() {
			
		@Override
		public void onClick(View v) {
			if (phoneValue != null) {
				currentFragment = 2;
				SelectValueFragment newFragment = new SelectValueFragment();
				newFragment.setTitle("选择联系电话");
				newFragment.setListValue(phoneValue);
		        newFragment.show(getSupportFragmentManager().beginTransaction(), "phoneDialog");
			}
		}
	};
	
	private View.OnClickListener timeSelect = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			currentFragment = 3;
			SelectValueFragment newFragment = new SelectValueFragment();
			newFragment.setTitle("送达时间");
			newFragment.setListValue(timeValue);
	        newFragment.show(getSupportFragmentManager().beginTransaction(), "timeDialog");
		}
	};
	
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
	public void selectValue(int index) {
		switch(currentFragment) {
			default:
				break;
			case 1:
				{
					String value = addressValue[index];
					address.setEditText(value);
				}
				break;
			case 2:
				{
					String value = phoneValue[index];
					phone.setEditText(value);
				}
				break;
			case 3:
				{
					String value = timeValue[index];
					time.setEditText(value);
				}
				break;
		}
		
		currentFragment = 0;
	}
}