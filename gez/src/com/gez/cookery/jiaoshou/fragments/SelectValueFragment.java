package com.gez.cookery.jiaoshou.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.gez.cookery.jiaoshou.R;
import com.gez.cookery.jiaoshou.contract.*;

public class SelectValueFragment extends BaseDialogFragment {

	private String title = "";

	public void setTitle(String title) {
		this.title = title;
	}

	private String[] listValue = null;
	
	public String[] getListValue() {
		return listValue;
	}

	public void setListValue(String[] listValue) {
		this.listValue = listValue;
	}

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.select_value_fragment, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.select_value_list);
        TextView textView = (TextView) rootView.findViewById(R.id.select_value_title);
        
        textView.setText(title);
        
        if (listValue != null) {
        	ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>( 
	        getActivity(), android.R.layout.simple_list_item_1, listValue); 
	        
	        listView.setAdapter(arrayAdapter);
	        
	        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					ISelectValue parentContext = (ISelectValue)SelectValueFragment.this.getActivity();
					parentContext.selectValue(position);
					
					SelectValueFragment.this.dismiss();
				}
	        });
        }

        return rootView;
    } 
}
