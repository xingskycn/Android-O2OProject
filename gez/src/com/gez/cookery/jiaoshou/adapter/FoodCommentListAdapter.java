package com.gez.cookery.jiaoshou.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.gez.cookery.jiaoshou.R;
import com.gez.cookery.jiaoshou.model.Pingl;

import java.util.ArrayList;
import java.util.List;

public class FoodCommentListAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<Pingl> comments = new ArrayList<Pingl>();

    public FoodCommentListAdapter(Context context, List<Pingl> comments) {
        inflater = LayoutInflater.from(context);
        this.comments = comments;
    }

    public int getCount() {
        return comments.size();
    }

    public Pingl getItem(int i) {
    	if (i >=0 && i < comments.size()) {
    		return comments.get(i);
    	}
    	else {
    		return null;
    	}
    }

    public long getItemId(int i) {
        return comments.get(i).hashCode();
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
    	
        final ViewHolder holder;
        if (null == view) {
            view = inflater.inflate(R.layout.comment_list_item, null);
            holder = new ViewHolder();
            holder.userLogo = (ImageView) view.findViewById(R.id.user_level_logo);
            holder.times = (TextView) view.findViewById(R.id.user_comment_times);
            holder.count = (TextView) view.findViewById(R.id.user_comment_count);
            holder.price = (TextView) view.findViewById(R.id.user_comment_price);
            holder.comment = (ImageView) view.findViewById(R.id.user_comment_result);
            holder.content = (TextView) view.findViewById(R.id.user_comment_content);
            holder.bind = (ImageView) view.findViewById(R.id.user_bind);
            
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        
        Pingl pingl = comments.get(i);
        
        int times = pingl.getZongdccs();
        int count = pingl.getZongdcfs();
        double price = pingl.getZongdcje(); 
        if (times >= 1 || count >= 1 || price >= 10.0) {
        	holder.userLogo.setImageResource(R.drawable.cp_user1);
        }
        if (times >= 3 || count >= 5 || price >= 50.0) {
        	holder.userLogo.setImageResource(R.drawable.cp_user2);
        }
        if (times >= 10 || count >= 20 || price >= 200.0) {
        	holder.userLogo.setImageResource(R.drawable.cp_user3);
        }
        if (times >= 30 || count >= 50 || price >= 500.0) {
        	holder.userLogo.setImageResource(R.drawable.cp_user4);
        }
        if (times >= 50 || count >= 100 || price >= 1000.0) {
        	holder.userLogo.setImageResource(R.drawable.cp_user5);
        }
        if (times >= 100 || count >= 300 || price >= 5000.0) {
        	holder.userLogo.setImageResource(R.drawable.cp_user6);
        }
        holder.times.setText(String.valueOf(times) + "次");
        holder.count.setText(String.valueOf(count) + "份");
        holder.price.setText(String.valueOf(price) + "元");
        
        if (pingl.isPingj()) {
        	holder.comment.setImageResource(R.drawable.cp_commentgood);
        } else holder.comment.setImageResource(R.drawable.cp_commentpoor);
        
        holder.content.setText(pingl.getPinglnr());
        
//        if (pingl.isBindQq()) {
//        	holder.bind.setVisibility(View.VISIBLE);
//        	holder.bind.setImageResource(R.drawable.cp_qq);
//        } else if (pingl.isBindWeib()) {
//        	holder.bind.setVisibility(View.VISIBLE);
//        	holder.bind.setImageResource(R.drawable.cp_weibo);
//        } else 
        	holder.bind.setVisibility(View.GONE);
        
        return view;
    }

    class ViewHolder {
    	ImageView userLogo;
    	
        TextView times;
        TextView count;
        TextView price;
        
        ImageView comment;
        TextView content;
        ImageView bind;
    }
}

