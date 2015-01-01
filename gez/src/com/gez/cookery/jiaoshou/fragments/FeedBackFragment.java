package com.gez.cookery.jiaoshou.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.actionbarsherlock.app.SherlockFragment;
import com.gez.cookery.jiaoshou.R;
import com.gez.cookery.jiaoshou.activity.SubmitOrderActivity;
import com.gez.cookery.jiaoshou.model.JsonModel;
import com.gez.cookery.jiaoshou.model.Result;
import com.gez.cookery.jiaoshou.net.IJsonModelData;
import com.gez.cookery.jiaoshou.net.RestClient;
import com.gez.cookery.jiaoshou.util.UIHelper;

public class FeedBackFragment extends SherlockFragment {
	
	private EditText editText;
	private Button submitBtn;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.feed_back_fragment, null);
		
		editText = (EditText) view.findViewById(R.id.feed_back_fragment_text);
		submitBtn = (Button) view.findViewById(R.id.feed_back_fragment_button_ok);
		
		submitBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				final ProgressDialog progressDialog = UIHelper.showProgressDialog(getActivity(), "正在提交...");
				
				RestClient.feedBack(editText.getText().toString(), new IJsonModelData() {
					
					@Override
					public void onReturn(JsonModel data) {
						
						progressDialog.dismiss();
						if(data != null) {
							Result list = (Result) data;
							if (list.isSuccess()) {
								UIHelper.showToast(getActivity(), "提交成功.");
							} else UIHelper.showToast(getActivity(), "提交失败.");
						} else UIHelper.showToast(getActivity(), "提交失败.");
					}
				});
			}
		});
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
}
