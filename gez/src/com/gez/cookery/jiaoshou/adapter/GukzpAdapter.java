package com.gez.cookery.jiaoshou.adapter;

	import java.util.List;
	







	import com.gez.cookery.jiaoshou.R;
import com.gez.cookery.jiaoshou.model.FlowTupBasic;
import com.gez.cookery.jiaoshou.model.mTextTz;
	


import com.gez.cookery.jiaoshou.net.RestClient;
import com.gez.cookery.jiaoshou.property.Constants;
import com.gez.cookery.jiaoshou.util.BitmapManager;
import com.gez.cookery.jiaoshou.util.Utils;

	import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

	 
		
	 public class GukzpAdapter extends BaseAdapter {
	    private List<FlowTupBasic> list;
	    LayoutInflater inflater;
	    private BitmapManager bmpManager;
	    
	    public GukzpAdapter(Context context,List<FlowTupBasic> list) {
	        this.list=list;
	        this.bmpManager = new BitmapManager();
	        this.inflater=LayoutInflater.from(context);
	     }
	
	    @Override
	     public int getCount() {
	         return list.size();
	     }
	
	     @Override
	     public String getItem(int position) {
	        return list.get(position).getImage();
	    }
	
	     @Override
	     public long getItemId(int position) {
	         return position;
	     }
	     
//	     @Override
//		  public String getItem(int position) {
//		    return tups.get(position).getImage();
//		  }
	
	    @Override
	     public View getView(int position, View convertView, ViewGroup parent) {
	         CacheView cacheView;
	         if(convertView==null){
	             convertView=inflater.inflate(R.layout.first_paper_zhaop, null);
	             cacheView=new CacheView();
//	             cacheView.guk_tz=(TextView) convertView.findViewById(R.id.first_paper_item_tiz);
//	             bmpManager.loadBitmap(Utils.guidToInt("4D444666-895B-4A2D-85CA-D1BA21A516AA"), RestClient.getImageUrl(this.getItem(i)), imageView);   
	             cacheView.imgv_img=(ImageView) convertView.findViewById(R.id.first_paper_item_zhaop);
	             convertView.setTag(cacheView);
	        }else{
	             cacheView=(CacheView) convertView.getTag();
	       }
//	         cacheView.guk_ tz.setText(String.valueOf(list.get(position).getGuktz()));
	         int n = this.getCount();
	         String tu = this.getItem(position);
	         Log.i("GukzpAdapter", tu);
	         bmpManager.loadBitmap(Utils.guidToInt("4D444666-895B-4A2D-85CA-D1BA21A516AA"), RestClient.getImageUrl(this.getItem(position)), cacheView.imgv_img);   
	        return convertView;
	    }
	     
	    private static class CacheView{
//	        TextView guk_tz;
	        ImageView imgv_img;
	     }
	 }
