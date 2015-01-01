package com.gez.cookery.jiaoshou.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.gez.cookery.jiaoshou.R;
import com.gez.cookery.jiaoshou.activity.OrderCommentActivity;
import com.gez.cookery.jiaoshou.model.DingdBasic;
import com.gez.cookery.jiaoshou.model.JsonModel;
import com.gez.cookery.jiaoshou.model.Result;
import com.gez.cookery.jiaoshou.net.IJsonModelData;
import com.gez.cookery.jiaoshou.net.RestClient;
import com.gez.cookery.jiaoshou.util.NumberUtil;
import com.gez.cookery.jiaoshou.util.StatusUtil;
import com.gez.cookery.jiaoshou.util.UIHelper;

import java.util.ArrayList;
import java.util.List;

public class OrderListAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<DingdBasic> orders = new ArrayList<DingdBasic>();
	private Context context;

	public OrderListAdapter(Context context, List<DingdBasic> orders) {
		inflater = LayoutInflater.from(context);
		this.orders = orders;
		this.context = context;
	}

	public int getCount() {
		return orders.size();
	}

	public DingdBasic getItem(int i) {
		if (i >= 0 && i < orders.size()) {
			return orders.get(i);
		} else {
			return null;
		}
	}

	public long getItemId(int i) {
		return orders.get(i).hashCode();
	}

	public View getView(int i, View view, ViewGroup viewGroup) {

		final ViewHolder holder;
		OnClick listener = null;
		if (null == view) {
			view = inflater.inflate(R.layout.order_list_item, null);
			holder = new ViewHolder();
			holder.id = (TextView) view.findViewById(R.id.order_item_id);
			holder.fens = (TextView) view.findViewById(R.id.order_item_fens);
			holder.zhuangt = (TextView) view
					.findViewById(R.id.order_item_zhuangt);
			holder.pingj = (Button) view
					.findViewById(R.id.order_item_button_pingj);

			holder.logo = (ImageView) view.findViewById(R.id.order_item_logo);
			holder.cantbz = (TextView) view.findViewById(R.id.order_item_cantbz);
			view.setTag(holder);

			listener = new OnClick();// 在这里新建监听对象
			holder.pingj.setOnClickListener(listener);
			view.setTag(holder.pingj.getId(), listener);// 对监听对象保存
		} else {
			holder = (ViewHolder) view.getTag();
			listener = (OnClick) view.getTag(holder.pingj.getId());// 重新获得监听对象
		}
		listener.setPosition(i);
		listener.setViewHolder(holder);

		DingdBasic order = orders.get(i);
		holder.logo.setImageResource(R.drawable.ep_order);
		holder.id.setText(String.format("编号：%s", order.getBianh()));
		holder.fens.setText(String.format("共%s份，￥%s", order.getZongfs(), NumberUtil.getNumberWithoutDot(order.getZongjg())));
		int dingdzt = Integer.parseInt(order.getDingdzt());
		holder.zhuangt.setText(String.format("【%s】", StatusUtil.convertDingdStatus(dingdzt)));
		if (order.getCantbz() != null && !"".equals(order.getCantbz())) {
			holder.cantbz.setText(String.format("【%s】", order.getCantbz()));
		}
		else {
			holder.cantbz.setText("");
		}
		
		if (dingdzt == 1) {
			holder.logo.setImageResource(R.drawable.ep_order);
		} else if (dingdzt == 2) {
			holder.logo.setImageResource(R.drawable.ep_affirm);
		} else if (dingdzt == 3) {
			holder.logo.setImageResource(R.drawable.ep_make);
		} else if (dingdzt == 4) {
			holder.logo.setImageResource(R.drawable.ep_outside);
		} else if (dingdzt == 5) {
			holder.logo.setImageResource(R.drawable.ep_service);
		} else if (dingdzt == 6) {
			holder.logo.setImageResource(R.drawable.ep_history);
		} else if (dingdzt == 7) {
			holder.logo.setImageResource(R.drawable.ep_cancel);
		}
		
		int dianpzt = Integer.parseInt(order.getDianpzt());
		if (dianpzt == 2) {
			holder.pingj.setText("已点评");
			holder.pingj.setBackgroundResource(R.drawable.normal_inverse_btn_bg);
		} else {
			if (dingdzt < 5) {
				holder.pingj.setText("催单");
				holder.pingj.setBackgroundResource(R.drawable.normal_inverse_btn_bg);
			} else {
				holder.pingj.setText("点评");
				holder.pingj.setBackgroundResource(R.drawable.important_btn_bg);
			}
		}
		return view;
	}

	class OnClick implements OnClickListener {
		int position;
		ViewHolder viewHolder;

		public void setPosition(int position) {
			this.position = position;
		}

		public void setViewHolder(ViewHolder viewHolder) {
			this.viewHolder = viewHolder;
		}

		@Override
		public void onClick(View v) {
			DingdBasic order = orders.get(position);

			int dingdzt = Integer.parseInt(order.getDingdzt());
			int dianpzt = Integer.parseInt(order.getDianpzt());
			if (dianpzt != 2) {
				if (dingdzt < 5) {

					// 催单
					final ProgressDialog progressDialog = UIHelper
							.showProgressDialog(context, "正在提交");

					RestClient.getHurry(order.getId(), new IJsonModelData() {
						public void onReturn(JsonModel data) {
							progressDialog.dismiss();

							if (data != null) {
								Result result = (Result) data;
								if (result.isSuccess()) {
									UIHelper.showToast(context, "已经为您催单");
								} else {
									UIHelper.showToast(context,
											result.getMessage());
								}
							}
						}
					});

				} else {
					// 点评
					Intent intent = new Intent(context,
							OrderCommentActivity.class);
					intent.putExtra("orderId", order.getId());
					context.startActivity(intent);
				}
			}
		}
	}

	class ViewHolder {
		ImageView logo;
		TextView id;
		TextView fens;
		TextView zhuangt;
		TextView cantbz;
		Button pingj;
	}
}
