package com.gez.cookery.jiaoshou.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.FrameLayout;

import com.gez.cookery.jiaoshou.util.Utils;

public abstract class BaseDialogFragment extends DialogFragment {
	
	private static final int DEFAULT_WIDTH_PADDING = 20;
	private DialogInterface.OnCancelListener onCancelListener;

	public void dismiss() {
		dismissAllowingStateLoss();
	}

	public int getDialogHeight() {
		return -2;
	}

	public int getDialogWidth() {
		return Utils.getScreenWidth(getActivity()) - Utils.convertDIP2PX(getActivity(), DEFAULT_WIDTH_PADDING);
	}
	
	@Override
	public void onActivityCreated(Bundle paramBundle) {
		super.onActivityCreated(paramBundle);
		if (getView() != null) {
			FrameLayout.LayoutParams localLayoutParams = new FrameLayout.LayoutParams(
					getDialogWidth(), getDialogHeight());
			getDialog().setContentView(getView(), localLayoutParams);
		}
	}

	public void onCancel(DialogInterface paramDialogInterface) {
		if (this.onCancelListener != null)
			this.onCancelListener.onCancel(paramDialogInterface);
		super.onCancel(paramDialogInterface);
	}
	
	public void setOnCancelListener(
			DialogInterface.OnCancelListener paramOnCancelListener) {
		this.onCancelListener = paramOnCancelListener;
	}
}

