package com.gez.cookery.jiaoshou.util;

import com.gez.cookery.jiaoshou.R;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 应用程序UI工具包：封装UI相关的一些操作
 * 
 */
public class UIHelper {

	public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
	
	//获取进度窗体
	public static ProgressDialog showProgressDialog(Context context, String message) {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(true);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        ((TextView) progressDialog.findViewById(R.id.message)).setText(message);
        /*progressDialog.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.dismiss();
            }
        });*/
        ((AnimationDrawable) ((ImageView) progressDialog.findViewById(R.id.progress)).getDrawable()).start();
        return progressDialog;
    }
}
