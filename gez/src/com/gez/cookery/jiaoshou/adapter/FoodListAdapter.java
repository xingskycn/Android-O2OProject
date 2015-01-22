package com.gez.cookery.jiaoshou.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gez.cookery.jiaoshou.R;
import com.gez.cookery.jiaoshou.activity.MakeOrderActivity;
import com.gez.cookery.jiaoshou.contract.ISelectCaip;
import com.gez.cookery.jiaoshou.model.CaipBasic;
import com.gez.cookery.jiaoshou.model.CantBasic;
import com.gez.cookery.jiaoshou.model.JsonModel;
import com.gez.cookery.jiaoshou.net.IJsonModelData;
import com.gez.cookery.jiaoshou.net.RestClient;
import com.gez.cookery.jiaoshou.property.Constants;
import com.gez.cookery.jiaoshou.util.BitmapManager;
import com.gez.cookery.jiaoshou.util.NumberUtil;
import com.gez.cookery.jiaoshou.util.UIHelper;
import com.gez.cookery.jiaoshou.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class FoodListAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<CaipBasic> foods = new ArrayList<CaipBasic>();
    //已选菜品
    private ArrayList<CaipBasic> foodSelect = new ArrayList<CaipBasic>();
    private BitmapManager bmpManager;
    private Fragment mainFragment;
    
    
	// 餐厅ID
	private String businessId = "30A01E8E-0807-4057-8EB5-ACE1ACEEB15B";
    Context context;	 
    
    public FoodListAdapter(Context context, Fragment mainFragment, List<CaipBasic> foods) {
    	
        inflater = LayoutInflater.from(context);  
    	this.mainFragment = mainFragment;
        this.foods = foods;
        this.bmpManager = new BitmapManager(Constants.foodLogo);
        this.context=context;
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
    
    @Override
    public void notifyDataSetChanged()
    {
    	int tiIndex = 0;
    	while (tiIndex < foodSelect.size()) {
    		
    		CaipBasic caip = foodSelect.get(tiIndex);
    		
    		boolean find = false;
    		for (CaipBasic food : foods) {
    			if (caip == food) {
    				break;
    			}
    			else if (caip.getId().equals(food.getId())) {
    				food.setCount(caip.getCount());
    				foodSelect.remove(tiIndex);
					foodSelect.add(tiIndex, food);
    				find  = true;
    				break;
    			}
    		}
    		
    		if (!find) {
    			tiIndex++;
    		}
    	}
    	
    	super.notifyDataSetChanged();
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
    	float scaleWidth = 0;
    	float scaleHeight = 0;
    	
        final ViewHolder holder;
        OnClick listener = null;
        if (null == view) {
            view = inflater.inflate(R.layout.food_list_item, null);
            holder = new ViewHolder();
            
            holder.name = (TextView) view.findViewById(R.id.food_item_name);
            holder.price = (Button) view.findViewById(R.id.food_button_price);
              holder.zengji = (ImageView) view.findViewById(R.id.food_item_zengji);
              holder.jiankang = (ImageView) view.findViewById(R.id.food_item_jiangkang);
              holder.jianzi = (ImageView) view.findViewById(R.id.food_item_jianzi);

            
            holder.logo = (ImageView) view.findViewById(R.id.food_item_logo);
            
            view.setTag(holder);
            
            listener = new OnClick();//在这里新建监听对象  
            holder.price.setOnClickListener(listener); 
            view.findViewById(R.id.lock_area).setOnClickListener(listener);
            view.setTag(holder.price.getId(), listener);//对监听对象保存  
        } else {
            holder = (ViewHolder) view.getTag();
            listener = (OnClick) view.getTag(holder.price.getId());//重新获得监听对象  
        }
        listener.setPosition(i);
        listener.setViewHolder(holder);
        
        CaipBasic food = foods.get(i);
        holder.name.setText(food.getCaipmc());
        
        
//        holder.chap.setText(String.valueOf(food.getChapcs()));
//        holder.haop.setText(String.valueOf(food.getHaopcs()));
//        holder.sellSum.setText("月售" + food.getYuesfs() + "份");
        
        
        
        
        holder.price.setText("￥" + NumberUtil.getFloatString(food.getJiag()));
        holder.zengji.setVisibility(food.isXinc() ? View.VISIBLE : View.GONE);
    	holder.jiankang.setVisibility(food.isTej() ? View.VISIBLE : View.GONE);
    	holder.jianzi.setVisibility(food.isZhaop() ? View.VISIBLE : View.GONE);
        bmpManager.loadBitmap(Utils.guidToInt(food.getId()), RestClient.getImageUrl(food.getCaipbs()), holder.logo);      	
        
        
        
        
//        bmp=BitmapFactory.decodeResource(context.getResources(),R.id.food_item_logo);
//       //对图片放大
//        int bmpWidth=bmp.getWidth();
//        int bmpHeight=bmp.getHeight();
//        /* 设定图片放大的比例 */
//        double scale=4;  
//        /* 计算这次要放大的比例 */
//        scaleWidth=(float)(scaleWidth*scale);
//        scaleHeight=(float)(scaleHeight*scale);
//        
//        /* 产生reSize后的Bitmap对象 */
//        Matrix matrix = new Matrix();  
//        matrix.postScale(scaleWidth, scaleHeight); 
////        Bitmap resizeBmp = Bitmap.createBitmap(bmp,0,0,bmpWidth,bmpHeight,matrix,true); 
//        holder.logo.setImageMatrix(matrix);
        
        
        
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
			
			if (!foodSelect.contains(food)) {
				foodSelect.add(food);
			}
			
			final ProgressDialog progressDialog = UIHelper.showProgressDialog(context, "正在加载数据...");
			
			RestClient.getBusinessDetail(businessId, new IJsonModelData(){
				public void onReturn(JsonModel data) {
					progressDialog.dismiss();
					
					if (data != null) {
						CantBasic mRestaurant = (CantBasic) data;
						Intent intent = new Intent(context,MakeOrderActivity.class);
						intent.putExtra("list", foodSelect);
						intent.putExtra("mRestaurant", mRestaurant);
						context.startActivity(intent);
					} else Toast.makeText(context, "加载失败，请检查网络或稍后重试！", Toast.LENGTH_SHORT).show();
				}
			});
			
			
			food.setCount(food.getCount() + 1);
			
			ISelectCaip parent = (ISelectCaip)mainFragment;
			parent.selectCaip(food);
		}
	}
    
    class ViewHolder {
    	ImageView logo;
        TextView name;
        ImageView jianzi;
        ImageView jiankang;
        ImageView zengji;
        TextView haop;
        TextView chap;
        TextView sellSum;
        TextView count;
        Button price;
    }
}
