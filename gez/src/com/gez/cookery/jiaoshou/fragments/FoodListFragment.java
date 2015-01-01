package com.gez.cookery.jiaoshou.fragments;

import com.actionbarsherlock.app.SherlockFragment;
import com.gez.cookery.jiaoshou.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.gez.cookery.jiaoshou.activity.FoodDetailActivity;
import com.gez.cookery.jiaoshou.adapter.FoodListAdapter;
import com.gez.cookery.jiaoshou.model.CaipBasic;
import com.gez.cookery.jiaoshou.model.EnmFoodType;
import com.gez.cookery.jiaoshou.model.EnmListViewAction;
import com.gez.cookery.jiaoshou.model.EnmListViewData;
import com.gez.cookery.jiaoshou.net.IJsonModelArrayData;
import com.gez.cookery.jiaoshou.net.RestClient;
import com.gez.cookery.jiaoshou.util.StringUtils;
import com.gez.cookery.jiaoshou.widget.PullToRefreshListView;

import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class FoodListFragment extends SherlockFragment {
	
	//列表对象
	private PullToRefreshListView lvListView;
	//适配器
	private FoodListAdapter lvListViewAdapter;
	//列表尾部视图
	private View lvListView_footer;
	//列表加载更多视图
	private TextView lvListView_foot_more;
	//加载状态视图
	private ProgressBar lvListView_foot_progress;
	//列表数据
	private List<CaipBasic> lvListViewData = new ArrayList<CaipBasic>();
	//数据总数
	private int lvListViewSumData;
	
	private View mRootView;
	
	//菜品类型
	private EnmFoodType foodType;
	//餐厅ID
	private String businessId;
	//主Fragment
	private Fragment mainFragment;
	

	
	public void setMainFragment(Fragment fragment) {
		this.mainFragment = fragment;
	}
	
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public void setFoodType(EnmFoodType foodType) {
		this.foodType = foodType;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mRootView = inflater.inflate(R.layout.food_list, null);
		
		initListViewListView(mRootView, inflater);
		
		return mRootView;
	}
	
	
	/**
	 * 初始化列表
	 */
	private void initListViewListView(View parentView, LayoutInflater inflater) {
		lvListViewAdapter = new FoodListAdapter(getActivity(), mainFragment, lvListViewData);

		lvListView_footer = inflater.inflate(R.layout.listview_footer,
				null);
		lvListView_foot_more = (TextView) lvListView_footer
				.findViewById(R.id.listview_foot_more);
		lvListView_foot_progress = (ProgressBar) lvListView_footer
				.findViewById(R.id.listview_foot_progress);
		lvListView = (PullToRefreshListView) parentView.findViewById(R.id.food_list);
		lvListView.addFooterView(lvListView_footer);// 添加底部视图 必须在setAdapter前
		lvListView.setAdapter(lvListViewAdapter);
		lvListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 点击头部、底部栏无效
				if (position == 0 || view == lvListView_footer)
					return;

				CaipBasic food = (CaipBasic) lvListViewAdapter.getItem(position - 1);
				Intent intent = new Intent(FoodListFragment.this.getActivity(), FoodDetailActivity.class);
				intent.putExtra("id", food.getId());
				intent.putExtra("name", food.getCaipmc());
				
		        startActivity(intent);
			}
		});
		
		
		
		
		lvListView.setOnScrollListener(new AbsListView.OnScrollListener() {
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				lvListView.onScrollStateChanged(view, scrollState);

				// 数据为空--不用继续下面代码了
				if (lvListViewData.isEmpty())
					return;

				// 判断是否滚动到底部
				boolean scrollEnd = false;
				try {
					if (view.getPositionForView(lvListView_footer) == view
							.getLastVisiblePosition())
						scrollEnd = true;
				} catch (Exception e) {
					scrollEnd = false;
				}

				EnmListViewData lvDataState = (EnmListViewData) lvListView.getTag();
				
				if (scrollEnd
					 && lvDataState == EnmListViewData.LISTVIEW_DATA_MORE) {
					lvListView.setTag(EnmListViewData.LISTVIEW_DATA_LOADING);
					lvListView_foot_more.setText(R.string.load_ing);
					lvListView_foot_progress.setVisibility(View.VISIBLE);
					// 当前pageIndex
					int pageIndex = lvListViewSumData / RestClient.PAGE_SIZE;
					loadLvListViewData(pageIndex,
							EnmListViewAction.LISTVIEW_ACTION_SCROLL);
				}
			}

			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				lvListView.onScroll(view, firstVisibleItem, visibleItemCount,
						totalItemCount);
			}
		});

		
		lvListView.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
			public void onRefresh() {
				loadLvListViewData(0, EnmListViewAction.LISTVIEW_ACTION_REFRESH);
			}
		});

		loadLvListViewData(0, EnmListViewAction.LISTVIEW_ACTION_INIT);
	}

	//private IJsonModelArrayData 
	
	/**
	 * 多线程加载数据
	 * 
	 * @param pageIndex
	 *            当前页数
	 * @param action
	 *            动作标识
	 */
	private void loadLvListViewData(final int pageIndex,
			final EnmListViewAction action) {

		// 加载数据
		RestClient.getFoodList(foodType, pageIndex, businessId, new IJsonModelArrayData() {
			public void onReturn(List<?> data) {

				List<CaipBasic> list = null;
				int size = -1;
				if (data != null) {
					list = (List<CaipBasic>) data;
					size = list.size();
				}

				
				if (size >= 0) {
					// 列表数据处理
					handleLvData(list, action);

					if (size < RestClient.PAGE_SIZE) {
						lvListView.setTag(EnmListViewData.LISTVIEW_DATA_FULL);
						lvListViewAdapter.notifyDataSetChanged();
						lvListView_foot_more.setText(R.string.load_full);
					} else if (size == RestClient.PAGE_SIZE) {
						lvListView.setTag(EnmListViewData.LISTVIEW_DATA_MORE);
						lvListViewAdapter.notifyDataSetChanged();
						lvListView_foot_more.setText(R.string.load_more);
					}
				} else if (size == -1) {
					// 有异常--显示加载出错 & 弹出错误消息
					lvListView.setTag(EnmListViewData.LISTVIEW_DATA_MORE);
					lvListView_foot_more.setText(R.string.load_error);
				}
				if (lvListViewAdapter.getCount() == 0) {
					lvListView.setTag(EnmListViewData.LISTVIEW_DATA_EMPTY);
					if (foodType == EnmFoodType.food_default) {
						lvListView_foot_more.setText(R.string.load_food_empty);
					}
					else {
						lvListView_foot_more.setText(R.string.load_collect_empty);
					}
				}
				lvListView_foot_progress.setVisibility(ProgressBar.GONE);
				if (action == EnmListViewAction.LISTVIEW_ACTION_REFRESH) {
					lvListView.onRefreshComplete(getString(R.string.pull_to_refresh_update)
							+ StringUtils.toDateString(new Date()));
					lvListView.setSelection(0);
				}
			}
		});
	}

	/**
	 * listview数据处理
	 * 
	 * @param list
	 *            数据
	 * @param actiontype
	 *            操作类型
	 */
	private void handleLvData(List<CaipBasic> list, EnmListViewAction actiontype) {
		switch (actiontype) {
		default:
		case LISTVIEW_ACTION_INIT:
		case LISTVIEW_ACTION_REFRESH: {
			lvListViewSumData = list.size();

			lvListViewData.clear();// 先清除原有数据
			lvListViewData.addAll(list);
		}
			break;
		case LISTVIEW_ACTION_SCROLL: {
			int size = list.size();
			lvListViewSumData += size;
			if (size > 0) {
				for (CaipBasic blog1 : list) {
					boolean b = false;
					for (CaipBasic blog2 : lvListViewData) {
						if (blog1.getId().equals(blog2.getId())) {
							b = true;
							break;
						}
					}
					if (!b)
						lvListViewData.add(blog1);
				}
			} else {
				lvListViewData.addAll(list);
			}
		}
			break;
		}
	}
}
