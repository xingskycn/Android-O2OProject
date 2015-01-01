package com.gez.cookery.jiaoshou.adapter;


	 import java.util.List;

import com.gez.cookery.jiaoshou.R;
import com.gez.cookery.jiaoshou.model.mTextTz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
	 
		
	 public class TizAdapter extends BaseAdapter {
	    private List<mTextTz> list;
	    LayoutInflater inflater;
	    public TizAdapter(Context context,List<mTextTz> list) {
	         this.list=list;
	        this.inflater=LayoutInflater.from(context);
	     }
	
	    @Override
	     public int getCount() {
	         return list.size();
	     }
	
	     @Override
	     public Object getItem(int position) {
	        return list.get(position);
	    }
	
	     @Override
	     public long getItemId(int position) {
	         return position;
	     }
	
	    @Override
	     public View getView(int position, View convertView, ViewGroup parent) {
	         CacheView cacheView;
	         if(convertView==null){
	             convertView=inflater.inflate(R.layout.first_paper_tiz, null);
	             cacheView=new CacheView();
	             cacheView.guk_tz=(TextView) convertView.findViewById(R.id.first_paper_item_tiz);
	           //cacheView.imgv_img=(ImageView) convertView.findViewById(R.id.imageView1);
	             convertView.setTag(cacheView);
	        }else{
	             cacheView=(CacheView) convertView.getTag();
	       }
	         cacheView.guk_tz.setText(String.valueOf(list.get(position).getGuktz()));
        
	        return convertView;
	    }
	     
	    private static class CacheView{
	        TextView guk_tz;
	        //ImageView imgv_img;
	     }
	 }