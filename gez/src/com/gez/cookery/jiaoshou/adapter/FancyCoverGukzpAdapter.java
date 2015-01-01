/*
 * Copyright 2013 David Schreiber
 *           2013 John Paul Nalog
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.gez.cookery.jiaoshou.adapter;


import java.util.ArrayList;
import java.util.List;

import com.gez.cookery.jiaoshou.R;
import com.gez.cookery.jiaoshou.model.CaipBasic;
import com.gez.cookery.jiaoshou.model.FlowTupBasic;
import com.gez.cookery.jiaoshou.model.Taocmx;
import com.gez.cookery.jiaoshou.net.RestClient;
import com.gez.cookery.jiaoshou.property.Constants;
import com.gez.cookery.jiaoshou.util.BitmapManager;
import com.gez.cookery.jiaoshou.util.Utils;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import at.technikum.mti.fancycoverflow.FancyCoverFlow;
import at.technikum.mti.fancycoverflow.FancyCoverFlowAdapter;

public class FancyCoverGukzpAdapter extends FancyCoverFlowAdapter {

    // =============================================================================
    // Private members
    // =============================================================================

//    private int[] images = {R.drawable.image1,R.drawable.image1,R.drawable.image1,R.drawable.image1, };
    
//    String[] image ={"e5d2fb0a-a44c-45f9-b75c-e61d4d63136e.image","361bb792-8440-4ef7-b6ec-634123f56c0a.image","e89f0929-2fff-4a78-8808-d8d95b6f2607.image","214a7d29-3ebb-4a3f-9be8-663b5e013e3d.image",};
//    String[] foodId = {"4D444666-895B-4A2D-85CA-D1BA21A516AA","4D444666-895B-4A2D-85CA-D1BA21A516BB","4D444666-895B-4A2D-85CA-D1BA21A516CC","4D444666-895B-4A2D-85CA-D1BA21A516EE",};
    
    
    private LayoutInflater inflater;
    
    private List<FlowTupBasic> tups = new ArrayList<FlowTupBasic>();
    
    private BitmapManager bmpManager;
    
    Context context;
    
    private Fragment mainFragment;

    // =============================================================================
    // Supertype overrides
    // =============================================================================

    public FancyCoverGukzpAdapter(Context context, Fragment mainFragment,List<FlowTupBasic> tups) {
    	
        inflater = LayoutInflater.from(context);  
    	this.mainFragment = mainFragment;
        this.tups = tups;
        this.bmpManager = new BitmapManager();
        this.context=context;
    }

    
    @Override
    public int getCount() {
        return tups.size();
    }

//    @Override
//    public Integer getItem(int i) {
//        return images[i];
//    }
//    
     @Override
	  public String getItem(int i) {
	    return tups.get(i).getImage();
	  }
     
     
     


 	@Override
 	public long getItemId(int position) {
 		// TODO Auto-generated method stub
 		return 0;
 	}
 	


    @Override
    public View getCoverFlowItem(int i, View reuseableView, ViewGroup viewGroup) {
        ImageView imageView = null;
        
//        String[] image =new String[10];
//        for(int k = 0;k<10;k++){
//        	image[k] = "e5d2fb0a-a44c-45f9-b75c-e61d4d63136e.image";
//        }
       

        if (reuseableView != null) {
        	imageView = (ImageView) reuseableView;
        } else {
            imageView = new ImageView(viewGroup.getContext());
            
            //对图片进行放大或者缩小显示
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            
            
            //滑动的图片与左右上下的距离
            imageView.setLayoutParams(new FancyCoverFlow.LayoutParams(200, 300));

        }
        
//        URL picUrl;
//		try {
//			
//			picUrl = new URL(" http://www.souchiwang.com/images/user_3.jpg");
//			Bitmap pngBM = BitmapFactory.decodeStream(picUrl.openStream()); 
//			imageView.setImageBitmap(pngBM);
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
        
        
//        imageView.setImageResource(this.getItem(i));
//        imageView.setImageURI(uri);
        
        
//        for(int j=0;j<10;j++){
//           bmpManager.loadBitmap(Utils.guidToInt("4D444666-895B-4A2D-85CA-D1BA21A516AA"), RestClient.getImageUrl("361bb792-8440-4ef7-b6ec-634123f56c0a.image"), imageView);   
//        }
        
        int n = this.getCount();
        String t = this.getItem(i);
        Log.i("FancyCoverGukzpAdapter", t);
        bmpManager.loadBitmap(Utils.guidToInt("4D444666-895B-4A2D-85CA-D1BA21A516AA"), RestClient.getImageUrl(this.getItem(i)), imageView);   
        return imageView;
       }
    
}
