package com.gez.cookery.jiaoshou.widget;

import com.gez.cookery.jiaoshou.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ColumnText extends LinearLayout {

	public ImageView imageView;
	private TextView  textView;
	//private ImageView imageViewSplit;
	
	public ColumnText(Context context) {
		super(context);
		
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.column_text, this);
		imageView = (ImageView) findViewById(R.id.column_text_image);
		textView = (TextView)findViewById(R.id.column_text_textview);
	}

	public ColumnText(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.column_text, this);
		imageView = (ImageView) findViewById(R.id.column_text_image);
		textView = (TextView)findViewById(R.id.column_text_textview);
		//imageViewSplit = (ImageView) findViewById(R.id.column_text_image_split);
		
		int resouceId = attrs.getAttributeResourceValue(null, "imagesrc", 0);
        if (resouceId > 0) {
            setImageResource(resouceId);
        }
        
        //boolean showSplit = attrs.getAttributeBooleanValue(null, "showsplit", true);
        //imageViewSplit.setVisibility(showSplit ? View.VISIBLE : View.GONE);
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
    public void setTextViewText(String text) {  
        textView.setText(text);  
    }  
}
