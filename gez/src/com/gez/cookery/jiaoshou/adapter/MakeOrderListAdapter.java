package com.gez.cookery.jiaoshou.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.gez.cookery.jiaoshou.R;
import com.gez.cookery.jiaoshou.contract.IChangeOrder;
import com.gez.cookery.jiaoshou.model.CaipBasic;
import com.gez.cookery.jiaoshou.net.RestClient;
import com.gez.cookery.jiaoshou.property.Constants;
import com.gez.cookery.jiaoshou.util.BitmapManager;
import com.gez.cookery.jiaoshou.util.NumberUtil;

import java.util.ArrayList;
import java.util.List;

public class MakeOrderListAdapter extends BaseAdapter {
	
    private LayoutInflater inflater;
    private List<CaipBasic> foods = new ArrayList<CaipBasic>();
    private Context context;
    private BitmapManager bmpManager;
    
    public MakeOrderListAdapter(Context context, List<CaipBasic> foods) {
    	this.context = context;
        inflater = LayoutInflater.from(context);
        this.foods = foods;
        this.bmpManager = new BitmapManager(Constants.foodLogo);
    }

    public int getCount() {
        return foods.size();
    }

    public CaipBasic getItem(int i) {
    	if (i >=0 && i < foods.size()) {
    		return foods.get(i);
    	}
    	else {
    		return null;
    	}
    }

    public long getItemId(int i) {
        return foods.get(i).hashCode();
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
    	
        final ViewHolder holder;
        OnClick listenerAdd = null;
        OnClick listenerSub = null;
        if (null == view) {
            view = inflater.inflate(R.layout.make_order_list, null);
            holder = new ViewHolder();
            
            holder.name = (TextView) view.findViewById(R.id.make_order_item_name);
            holder.price = (TextView) view.findViewById(R.id.make_order_item_price);
            holder.sub = (Button) view.findViewById(R.id.make_order_item_button_sub);
            holder.count = (TextView) view.findViewById(R.id.make_order_item_count);
            holder.add = (Button) view.findViewById(R.id.make_order_item_button_add);
     
            holder.logo = (ImageView) view.findViewById(R.id.make_order_item_logo);
            
            view.setTag(holder);

            listenerAdd = new OnClick();//在这里新建监听对象  
            holder.add.setOnClickListener(listenerAdd);  
            view.setTag(holder.add.getId(), listenerAdd);//对监听对象保存
            
            listenerSub = new OnClick();//在这里新建监听对象  
            holder.sub.setOnClickListener(listenerSub);  
            view.setTag(holder.sub.getId(), listenerSub);//对监听对象保存
        } else {
            holder = (ViewHolder) view.getTag();
            listenerAdd = (OnClick) view.getTag(holder.add.getId());//重新获得监听对象  
            listenerSub = (OnClick) view.getTag(holder.sub.getId());
        }
        listenerAdd.setPosition(i);
        listenerAdd.setViewHolder(holder);
        
        listenerSub.setPosition(i);
        listenerSub.setViewHolder(holder);
        
        CaipBasic food = foods.get(i);
        holder.name.setText(food.getCaipmc());
        holder.count.setText(Integer.toString(food.getCount())); 
        holder.price.setText("￥" + NumberUtil.getFloatString(food.getJiag() * food.getCount()));
        
        bmpManager.loadBitmap(RestClient.getImageUrl(food.getCaipbs()), holder.logo);
//        if (!StringUtils.isEmpty(food.getCaipbs())) {
//        	//holder.logo.setImageResource(R.drawable.ij_cant2);
//        }
        /*if (null != food && food.getLogo().startsWith("http"))
            AsyncImageLoader.loadDrawable(food.getLogo(), holder.logo, new AsyncImageLoader.ImageCallback() {
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

    class OnClick implements OnClickListener {
		int position;
		ViewHolder viewHolder;

		public void setPosition(int position) {
			this.position = position;
		}

		public void setViewHolder(ViewHolder viewHolder) {
			this.viewHolder = viewHolder;
		}
		
		@Override
		public void onClick(View v) {
			CaipBasic food = foods.get(position);
			
			if (v.getId() == R.id.make_order_item_button_add) {
				food.setCount(food.getCount() + 1);
			}
			else {
				if (food.getCount() > 0) {
					food.setCount(food.getCount() - 1);
				}
			}
			viewHolder.count.setText(Integer.toString(food.getCount())); 
			viewHolder.price.setText("￥" + NumberUtil.getFloatString(food.getJiag() * food.getCount()));
			
			IChangeOrder parent = (IChangeOrder)context;
			parent.changeOrder();
		}
	}
    
    class ViewHolder {
    	ImageView logo;
    	
        TextView name;
        
        TextView price;
        TextView count;

        Button add;
        Button sub;
    }
}
