package com.gez.cookery.jiaoshou.widget;

import com.gez.cookery.jiaoshou.R;

import android.content.Context;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ColumnInputText extends LinearLayout {

	private ImageView imageView;
	private EditText  editText;
	private Button button;

	//设置按钮点击事件
	public void setButtonClickListener(View.OnClickListener buttonClickListener) {
		if (button != null) {
			button.setOnClickListener(buttonClickListener);
		}
	}

	public ColumnInputText(Context context) {
		super(context);
	}

	public ColumnInputText(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.column_input_text, this);
		imageView = (ImageView) findViewById(R.id.column_input_text_image);
		editText = (EditText)findViewById(R.id.column_input_text_edit);
		button = (Button)findViewById(R.id.column_input_text_button);
		
		int resouceImageId = attrs.getAttributeResourceValue(null, "imagesrc", 0);
        if (resouceImageId > 0) {
            setImageResource(resouceImageId);
        }
        
        int resouceButtonId = attrs.getAttributeResourceValue(null, "button_back", 0);
        if (resouceButtonId > 0) {
            button.setBackgroundResource(resouceButtonId);
            button.setVisibility(View.VISIBLE);
        }
        else {
        	button.setVisibility(View.GONE);
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
     * 设置显示的文字 
     */  
    public void setEditText(String text) {  
    	editText.setText(text);  
    }
    
    /** 
     * 获取文字 
     */ 
    public String getEditText() {
    	return editText.getText().toString();
    }
    
    /**
     * 设置文本是否发生改变监听器
     */
    public void setTextChangedListener(TextWatcher textWatcher) {
    	editText.addTextChangedListener(textWatcher);
    }
}
