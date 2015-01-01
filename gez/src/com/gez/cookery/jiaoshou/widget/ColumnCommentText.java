package com.gez.cookery.jiaoshou.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.gez.cookery.jiaoshou.R;

public class ColumnCommentText extends LinearLayout {

	private ImageView imageView;
	private TextView textView;
	private EditText editText;
	private ToggleButton toggleButton;

	public ColumnCommentText(Context context) {
		super(context);

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.column_comment_text, this);
		imageView = (ImageView) findViewById(R.id.column_comment_text_image);
		textView = (TextView) findViewById(R.id.column_comment_text_name);
		editText = (EditText) findViewById(R.id.column_comment_text_edit);
		toggleButton = (ToggleButton) findViewById(R.id.column_comment_toggleButton);

		toggleButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				int i = Gravity.LEFT;
				if (isChecked)
					i = Gravity.RIGHT;
				((FrameLayout.LayoutParams) buttonView.getLayoutParams()).gravity = i;
			}

		});

		((View) toggleButton.getParent())
				.setOnClickListener(new View.OnClickListener() {
					public void onClick(View paramView) {
						ToggleButton toggleButton = ColumnCommentText.this.toggleButton;
						if (toggleButton.isChecked()) {
							toggleButton.setChecked(false);
						} else {
							toggleButton.setChecked(true);
						}
						return;
					}
				});

	}

	public ColumnCommentText(Context context, AttributeSet attrs) {
		super(context, attrs);

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.column_comment_text, this);
		imageView = (ImageView) findViewById(R.id.column_comment_text_image);
		textView = (TextView) findViewById(R.id.column_comment_text_name);
		editText = (EditText) findViewById(R.id.column_comment_text_edit);
		toggleButton = (ToggleButton) findViewById(R.id.column_comment_toggleButton);

		toggleButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				int i = Gravity.LEFT;
				if (isChecked)
					i = Gravity.RIGHT;
				((FrameLayout.LayoutParams) buttonView.getLayoutParams()).gravity = i;
			}

		});

		((View) toggleButton.getParent())
				.setOnClickListener(new View.OnClickListener() {
					public void onClick(View paramView) {
						ToggleButton toggleButton = ColumnCommentText.this.toggleButton;
						if (toggleButton.isChecked()) {
							toggleButton.setChecked(false);
						} else {
							toggleButton.setChecked(true);
						}
						return;
					}
				});

		int resouceImageId = attrs.getAttributeResourceValue(null, "imagesrc",
				0);
		if (resouceImageId > 0) {
			setImageResource(resouceImageId);
		}

		String hint = attrs.getAttributeValue(null, "edit_hint");
		if (hint != null) {
			editText.setHint(hint);
		}
	}

	/**
	 * 设置图片资源
	 */
	public void setImageResource(int resId) {
		imageView.setImageResource(resId);
	}

	/**
	 * 设置标题显示的文字
	 */
	public void setTextView(String text) {
		textView.setText(text);
	}

	/**
	 * 获取标题
	 */
	public String getTextView() {
		return textView.getText().toString();
	}

	/**
	 * 设置评论显示的文字
	 */
	public void setEditText(String text) {
		editText.setText(text);
	}

	/**
	 * 获取评论文字
	 */
	public String getEditText() {
		return editText.getText().toString();
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
}
