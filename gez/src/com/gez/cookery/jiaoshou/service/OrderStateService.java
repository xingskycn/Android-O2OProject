package com.gez.cookery.jiaoshou.service;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.gez.cookery.jiaoshou.model.DingdBasic;
import com.gez.cookery.jiaoshou.net.IJsonModelArrayData;
import com.gez.cookery.jiaoshou.net.RestClient;
import com.gez.cookery.jiaoshou.util.StatusUtil;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

public class OrderStateService extends Service {
	
	private int refreshTimes = 0;
	private Timer timer = null;
	private List<DingdBasic> formerOrderList = null;

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@SuppressLint("HandlerLeak")
	@SuppressWarnings("deprecation")
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);

		final Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					// 加载订单列表数据
					RestClient.getOrderList(0, new IJsonModelArrayData() {
						public void onReturn(List<?> data) {

							List<DingdBasic> list = null;
							int size = -1;
							if (data != null) {
								list = (List<DingdBasic>) data;
								size = list.size();
							}
							if (size >= 0) {
								// 第一次请求数据
								if (refreshTimes == 1) {
									int flag = 0;
									for (int i = 0; i < size; i++) {
										int dingdzt = Integer.parseInt(list.get(i).getDingdzt());
										if (dingdzt == 1 || dingdzt == 2 || dingdzt == 3
												|| dingdzt == 4 || dingdzt == 6) {
											flag++;
										}
									}
									if (flag == 0) { // 如果所有订单已完成，取消定时器
										timer.cancel();
									} else formerOrderList = list;
								} else if (refreshTimes > 1) {
									for (int i = 0; i < size; i++) {
										for (int j = 0; j < formerOrderList.size(); j++){
											if (list.get(i).getBianh().equals(formerOrderList.get(j).getBianh())) {
												if (!list.get(i).getDingdzt().equals(formerOrderList.get(j).getDingdzt())) {
													int dingdzt = Integer.parseInt(list.get(i).getDingdzt());
													String myState = StatusUtil.convertDingdStatus(dingdzt);
													Toast.makeText(OrderStateService.this, "您的订单：" + list.get(i).getBianh() + " 已经被商家设置为：【" + myState + "】状态", Toast.LENGTH_LONG).show();
													// 发送广播消息
													Intent intent = new Intent("refreshOrderState");
													LocalBroadcastManager.getInstance(OrderStateService.this).sendBroadcast(intent);
												}
											}
										}
								    }
									if (size == formerOrderList.size()) {
										int flag = 0;
										for (int i = 0; i < size; i++) {
											int dingdzt = Integer.parseInt(list.get(i).getDingdzt());
											if (dingdzt == 1 || dingdzt == 2 || dingdzt == 3
													|| dingdzt == 4 || dingdzt == 6) {
												flag++;
											}
										}
										if (flag == 0) { // 如果所有订单已完成，取消定时器
											timer.cancel();
										} else formerOrderList = list;
									}
									formerOrderList = list;
								}
							}
						}
					});
					break;
				}
				super.handleMessage(msg);
			}
		};

		TimerTask task = new TimerTask() {
			public void run() {
				Message message = new Message();
				message.what = 1;
				refreshTimes++;
				handler.sendMessage(message);
			}
		};

		timer = new Timer(true);
		timer.schedule(task, 2000, 60000); // 延时2000ms后执行，60s执行一次
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
