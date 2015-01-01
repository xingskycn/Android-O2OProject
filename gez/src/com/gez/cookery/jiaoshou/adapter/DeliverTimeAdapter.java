package com.gez.cookery.jiaoshou.adapter;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.opengl.Visibility;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.gez.cookery.jiaoshou.R;
import com.gez.cookery.jiaoshou.model.DingdBasic;
import com.gez.cookery.jiaoshou.util.NumberUtil;
import com.gez.cookery.jiaoshou.util.StatusUtil;

import java.util.ArrayList;
import java.util.List;

public class DeliverTimeAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<DingdBasic> orders = new ArrayList<DingdBasic>();

    public DeliverTimeAdapter(Context context, List<DingdBasic> orders) {
        inflater = LayoutInflater.from(context);
        this.orders = orders;
    }

    public int getCount() {
        return orders.size();
    }

    public DingdBasic getItem(int i) {
    	if (i >=0 && i < orders.size()) {
    		return orders.get(i);
    	}
    	else {
    		return null;
    	}
    }

    public long getItemId(int i) {
        return orders.get(i).hashCode();
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
    	
        final ViewHolder holder;
        if (null == view) {
            view = inflater.inflate(R.layout.order_list_item, null);
            holder = new ViewHolder();
            holder.id = (TextView) view.findViewById(R.id.order_item_id);
            holder.fens = (TextView) view.findViewById(R.id.order_item_fens);
            holder.zhuangt = (TextView) view.findViewById(R.id.order_item_zhuangt);
            holder.pingj = (Button) view.findViewById(R.id.order_item_button_pingj);

            holder.logo = (ImageView) view.findViewById(R.id.order_item_logo);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        
        DingdBasic order = orders.get(i);
        holder.id.setText(String.format("编号：%s", order.getBianh()));
        holder.fens.setText(String.format("共%s份，￥%s", order.getZongfs(), order.getZongjg()));
        int dingdzt = Integer.parseInt(order.getDingdzt());
        holder.zhuangt.setText(String.format("状态：%s", StatusUtil.convertDingdStatus(dingdzt)));
        
        int dianpzt = Integer.parseInt(order.getDianpzt());
        if (dianpzt == 2) {
        	holder.pingj.setText("已点评");
        	holder.pingj.setBackgroundResource(R.color.order_yidp);
        }
        else {
        	if (dingdzt < 5) {
        		holder.pingj.setText("催单");
            	holder.pingj.setBackgroundResource(R.color.order_cuid);
        	}
        	else {
        		holder.pingj.setText("未点评");
            	holder.pingj.setBackgroundResource(R.color.order_dianp);
        	}
        }
        
        holder.logo.setImageResource(R.drawable.kd_logo2);
        /*if (null != order && order.getLogo().startsWith("http"))
            AsyncImageLoader.loadDrawable(order.getLogo(), holder.logo, new AsyncImageLoader.ImageCallback() {
                @Override
                public void imageLoaded(String filePath, Object o) {
                    ImageView imageView = (ImageView) o;
                    try {
                        if (imageView.isShown()) {
                            BitmapDrawable bd = (BitmapDrawable) Drawable.createFromPath(filePath);
                            imageView.setImageDrawable(bd);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });*/
        return view;
    }

    class ViewHolder {
        ImageView logo;
        TextView id;
        TextView fens;
        TextView zhuangt;
        Button pingj; 
    }
}
