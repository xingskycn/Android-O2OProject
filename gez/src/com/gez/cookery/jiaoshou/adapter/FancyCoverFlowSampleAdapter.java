/*
 * Copyright 2013 David Schreiber
 *           2013 John Paul Nalog
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.gez.cookery.jiaoshou.adapter;

import java.util.ArrayList;
import java.util.List;

import com.gez.cookery.jiaoshou.R;
import com.gez.cookery.jiaoshou.model.CaipBasic;
import com.gez.cookery.jiaoshou.model.FlowTupBasic;
import com.gez.cookery.jiaoshou.model.Taocmx;
import com.gez.cookery.jiaoshou.net.RestClient;
import com.gez.cookery.jiaoshou.property.Constants;
import com.gez.cookery.jiaoshou.util.BitmapManager;
import com.gez.cookery.jiaoshou.util.Utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import at.technikum.mti.fancycoverflow.FancyCoverFlow;
import at.technikum.mti.fancycoverflow.FancyCoverFlowAdapter;

public class FancyCoverFlowSampleAdapter extends FancyCoverFlowAdapter {


	private LayoutInflater inflater;

	private List<Taocmx> tups = new ArrayList<Taocmx>();

	private BitmapManager bmpManager;

	Context context;

	private Fragment mainFragment;

	// =============================================================================
	// Supertype overrides
	// =============================================================================

	public FancyCoverFlowSampleAdapter(Context context, Fragment mainFragment,
			List<Taocmx> tups) {

		inflater = LayoutInflater.from(context);
		this.mainFragment = mainFragment;
		this.tups = tups;
		this.bmpManager = new BitmapManager(Constants.foodLogo);
		this.context = context;
	}

	@Override
	public int getCount() {
		return tups.size();
	}

	// @Override
	// public Integer getItem(int i) {
	// return images[i];
	// }
	//
	@Override
	public Object getItem(int i) {
		return tups.get(i);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getCoverFlowItem(int i, View reuseableView, ViewGroup viewGroup) {


		CustomViewGroup customViewGroup = null;

		if (reuseableView != null) {
			customViewGroup = (CustomViewGroup) reuseableView;
		} else {
			customViewGroup = new CustomViewGroup(viewGroup.getContext());

			// 滑动的图片与左右上下的距离
			customViewGroup.setLayoutParams(new FancyCoverFlow.LayoutParams(
					270, 270));
		}

		Taocmx taoc = (Taocmx) this.getItem(i);
		customViewGroup.getTextView().setText(String.format(taoc.getPeiz()));
		
		//获取屏幕高度和宽度
		Display display = mainFragment.getActivity().getWindowManager()
				.getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int screenWidth = size.x;
		int screenHeight = size.y;
		if(screenWidth < screenHeight){
			int temp = screenHeight;
			screenHeight = screenWidth;
			screenWidth = temp;
		}

		bmpManager.loadBitmap(
				Utils.guidToInt("4D444666-895B-4A2D-85CA-D1BA21A516AA"),
				RestClient.getImageUrl(taoc.getTup()),
				customViewGroup.getImageView(), screenWidth, screenHeight);

		return customViewGroup;
	}

	// CacheView cacheView;
	// if(reuseableView==null){
	// reuseableView=inflater.inflate(R.layout.first_paper_zhaop, null);
	// cacheView=new CacheView();
	// // cacheView.guk_tz=(TextView)
	// convertView.findViewById(R.id.first_paper_item_tiz);
	// //
	// bmpManager.loadBitmap(Utils.guidToInt("4D444666-895B-4A2D-85CA-D1BA21A516AA"),
	// RestClient.getImageUrl(this.getItem(i)), imageView);
	// cacheView.imgv_img=(ImageView)
	// reuseableView.findViewById(R.id.first_paper_item_zhaop);
	// reuseableView.setTag(cacheView);
	// }else{
	// cacheView=(CacheView) reuseableView.getTag();
	// }
	// // cacheView.guk_
	// tz.setText(String.valueOf(list.get(position).getGuktz()));
	// bmpManager.loadBitmap(Utils.guidToInt("4D444666-895B-4A2D-85CA-D1BA21A516AA"),
	// RestClient.getImageUrl(this.getItem(i)), cacheView.imgv_img);
	// return reuseableView;
	// }
	//
	// private static class CacheView{
	// // TextView guk_tz;
	// ImageView imgv_img;
	// }

	private static class CustomViewGroup extends LinearLayout {

		// =============================================================================
		// Child views
		// =============================================================================

		private TextView textView;

		private ImageView imageView;

		// =============================================================================
		// Constructor
		// =============================================================================

		private CustomViewGroup(Context context) {
			super(context);

			this.setOrientation(VERTICAL);

			this.imageView = new ImageView(context);
			this.textView = new TextView(context);

			LinearLayout.LayoutParams layoutParams = new LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.MATCH_PARENT);

			this.imageView.setLayoutParams(layoutParams);
			this.textView.setLayoutParams(layoutParams);

			this.imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
			this.imageView.setAdjustViewBounds(true);

			this.textView.setGravity(Gravity.CENTER);

			this.addView(this.imageView);
			this.addView(this.textView);
		}

		// =============================================================================
		// Getters
		// =============================================================================

		private TextView getTextView() {
			return textView;
		}

		private ImageView getImageView() {
			return imageView;
		}
	}

}
