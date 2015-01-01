package com.gez.cookery.jiaoshou.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.gez.cookery.jiaoshou.R;

public class ColumnSwitchText extends LinearLayout {

	private ImageView imageView;
	private TextView textView;
	private ToggleButton toggleButton;

	public ColumnSwitchText(Context context) {
		super(context);
	}

	public ColumnSwitchText(Context context, AttributeSet attrs) {
		super(context, attrs);

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.column_switch_text, this);
		imageView = (ImageView) findViewById(R.id.column_switch_text_image);
		textView = (TextView) findViewById(R.id.column_switch_text_edit);
		toggleButton = (ToggleButton) findViewById(R.id.column_switch_toggleButton);

		toggleButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				int i = Gravity.LEFT;
				if (isChecked)
					i = Gravity.RIGHT;
				((FrameLayout.LayoutParams) buttonView.getLayoutParams()).gravity = i;
			}
		});

		((View) toggleButton.getParent()).setOnClickListener(new View.OnClickListener() {
					public void onClick(View paramView) {
						ToggleButton toggleButton = ColumnSwitchText.this.toggleButton;
						if (toggleButton.isChecked()) {
							toggleButton.setChecked(false);
						} else {
							toggleButton.setChecked(true);
						}
						return;
					}
				});

		int resouceImageId = attrs.getAttributeResourceValue(null, "imagesrc", 0);
		if (resouceImageId > 0) {
			setImageResource(resouceImageId);
		}
		
		String textOff = attrs.getAttributeValue(null, "textOff");
		String textOn = attrs.getAttributeValue(null, "textOn");
		toggleButton.setTextOff(textOff);
		toggleButton.setTextOn(textOn);
	}

	/**
	 * 设置图片资源
	 */
	public void setImageResource(int resId) {
		imageView.setImageResource(resId);
	}

	/**
	 * 设置显示的文字
	 */
	public void setTextView(String text) {
		textView.setText(text);
	}

	/**
	 * 获取文字
	 */
	public String getTextView() {
		return textView.getText().toString();
	}

	/**
	 * 设置选中状态
	 */
	public void setToggleButtonChecked(Boolean checked) {
		toggleButton.setChecked(checked);
	}

	/**
	 * 获取选中状态
	 */
	public Boolean getToggleButtonChecked() {
		return toggleButton.isChecked();
	}
	
	/**
	 * 设置toggleButton点击事件
	 */
	public void setButtonClickListener(OnCheckedChangeListener listener) {
		if (toggleButton != null) {
			toggleButton.setOnCheckedChangeListener(listener);
		}
	}
	
	/**
	 * 设置控件位置方向
	 */
	public void setToggleButtonDirection(int direction) {
		((FrameLayout.LayoutParams) toggleButton.getLayoutParams()).gravity = direction;
	}
}
