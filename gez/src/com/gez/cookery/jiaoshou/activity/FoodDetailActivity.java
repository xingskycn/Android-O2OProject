package com.gez.cookery.jiaoshou.activity;

import com.gez.cookery.jiaoshou.R;
import com.gez.cookery.jiaoshou.fragments.FoodCommentFragment;
import com.gez.cookery.jiaoshou.fragments.FoodDetailFragment;
import com.gez.cookery.jiaoshou.fragments.FoodImagelFragment;
import com.actionbarsherlock.view.MenuItem;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

public class FoodDetailActivity extends BaseActivity implements View.OnClickListener {

	//菜品ID
	private String foodId;
	
	//当前选中按钮ID
	private int currentContentId;
	
	private FoodDetailFragment foodDetailFragment;
	private FoodImagelFragment foodImageFragment;
	private FoodCommentFragment foodPinglFragment;
	
	private Fragment currentFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_food_detail);
		
		setTitle(this.getIntent().getExtras().getCharSequence("name"));
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		foodId = this.getIntent().getExtras().getString("id");
		
		this.findViewById(R.id.food_button_detail).setOnClickListener(this);
		this.findViewById(R.id.food_button_image).setOnClickListener(this);
		this.findViewById(R.id.food_button_pingl).setOnClickListener(this);
		
		showContent(R.id.food_button_detail);
	}
	
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
	public void onClick(View paramView) {
		showContent(paramView.getId());
	}
	
	public void setCurrentSelection(int paramInt) {
		if (this.currentContentId != 0)
			this.findViewById(this.currentContentId).setSelected(false);
		this.currentContentId = paramInt;
		this.findViewById(this.currentContentId).setSelected(true);
	}

	private void showContent(int paramInt) {
		if (this.currentContentId == paramInt) {
			
		} 
		else {
			
			setCurrentSelection(paramInt);
			
			Fragment fragment = null;
			
			switch (paramInt) {
				default:
				case R.id.food_button_detail:
					{
						if (foodDetailFragment == null) {
							foodDetailFragment = new FoodDetailFragment();
							foodDetailFragment.setFoodId(foodId);
						}
						fragment = foodDetailFragment;
					}
					break;
				case R.id.food_button_image:
					{
						if (foodImageFragment == null) {
							foodImageFragment = new FoodImagelFragment();
							foodImageFragment.setFoodId(foodId);
						}
						fragment = foodImageFragment;
					}
					break;
				case R.id.food_button_pingl:
					{
						if (foodPinglFragment == null) {
							foodPinglFragment = new FoodCommentFragment();
							foodPinglFragment.setFoodId(foodId);
						}
						fragment = foodPinglFragment;
					}
					break;
			}

			if (fragment != null) {
				switchContent(currentFragment, fragment);
			}
		}
	}
	
	public void switchContent(Fragment from, Fragment to) {
		FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction();
		if (from  != null) {		
	        if (!to.isAdded()) {    
	            transaction.hide(from).add(R.id.food_detail_content_frame, to).commit(); 
	        } else {
	            transaction.hide(from).show(to).commit();
	        }
		}
		else {
			transaction.add(R.id.food_detail_content_frame, to).commit();
		}
        
        currentFragment = to;
	}
}
