package com.gez.cookery.jiaoshou.fragments;

import java.util.ArrayList;
import java.util.List;

import com.gez.cookery.jiaoshou.activity.FootDetailImgActivity;
import com.gez.cookery.jiaoshou.model.Taocmx;
import com.gez.cookery.jiaoshou.property.Constants;
import com.actionbarsherlock.app.SherlockFragment;
import com.gez.cookery.jiaoshou.R;
import com.gez.cookery.jiaoshou.net.IJsonModelArrayData;
import com.gez.cookery.jiaoshou.net.RestClient;
import com.gez.cookery.jiaoshou.util.BitmapManager;
import com.gez.cookery.jiaoshou.util.Utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class FoodImagelFragment extends SherlockFragment {
	private View mRootView;
	private BitmapManager bmpManager;
	private List<Taocmx> listViewData = new ArrayList<Taocmx>();

	private String foodId;

	public void setFoodId(String foodId) {
		this.foodId = foodId;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mRootView = inflater.inflate(R.layout.food_detail_image_fragment, null);
		bmpManager = new BitmapManager(Constants.foodLogo);
		showDetail();

		return mRootView;
	}

	private void showDetail() {
		RestClient.getFoodDetailImg(foodId, new IJsonModelArrayData() {
			public void onReturn(List<?> data) {
				if (data != null) {
					listViewData = (List<Taocmx>) data;

					ListView lv = (ListView) mRootView.findViewById(R.id.img_list);
					foodDetailImgAdapter mAdapter = new foodDetailImgAdapter(getActivity());
					lv.setAdapter(mAdapter);
					lv.setOnItemClickListener(new OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> ad, View v, int i, long arg3) {
							Intent intent = new Intent(getActivity(), FootDetailImgActivity.class);
							intent.putExtra("id", listViewData.get(i).getId());
							intent.putExtra("url", listViewData.get(i).getTup());
							getActivity().startActivity(intent);
						}
					});

				}
			}
		});
	}

	class foodDetailImgAdapter extends BaseAdapter {
		private LayoutInflater myInflater;
		private String[] times = new String[] { "早", "中", "晚" };

		public foodDetailImgAdapter(Context context) {
			this.myInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return listViewData.size();
		}

		@Override
		public Object getItem(int i) {
			if (i > -1 && i < listViewData.size()) {
				return listViewData.get(i);
			} else {
				return null;
			}
		}

		@Override
		public long getItemId(int i) {
			return listViewData.get(i).hashCode();
		}

		@Override
		public View getView(int i, View view, ViewGroup viewGroup) {
			ViewHolder holder;
			int j = i > 2 ? i % 3 : i;

			if (view == null) {
				view = myInflater.inflate(R.layout.food_detail_item, null);
				holder = new ViewHolder();
				holder.name = (TextView) view.findViewById(R.id.food_item_name);
				holder.img = (ImageView) view.findViewById(R.id.food_item_img);
				holder.describe = (TextView) view.findViewById(R.id.food_item_describe);
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}

			holder.name.setText("第" + listViewData.get(i).getTians() + "天" + times[j].toString());
			holder.describe.setText(listViewData.get(i).getPeiz());
			bmpManager.loadBitmap(Utils.guidToInt(listViewData.get(i).getId()), RestClient.getImageUrl(listViewData.get(i).getTup()), holder.img);

			return view;
		}
	}

	class ViewHolder {
		TextView name;
		ImageView img;
		TextView describe;
	}
}
