package com.gez.cookery.jiaoshou.activity;

import uk.co.senab.photoview.PhotoViewAttacher;
import uk.co.senab.photoview.PhotoViewAttacher.OnPhotoTapListener;

import com.actionbarsherlock.view.MenuItem;
import com.gez.cookery.jiaoshou.net.RestClient;
import com.gez.cookery.jiaoshou.property.Constants;
import com.gez.cookery.jiaoshou.util.BitmapManager;
import com.gez.cookery.jiaoshou.util.Utils;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

public class FootDetailImgActivity extends BaseActivity {

	static final String PHOTO_TAP_TOAST_STRING = "Photo Tap! X: %.2f %% Y:%.2f %%";
	private PhotoViewAttacher mAttacher;
	private Toast mCurrentToast;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		String caipId = this.getIntent().getExtras().getString("id");
		String tuplj = this.getIntent().getExtras().getString("url");

		ImageView iv = new ImageView(this);
		WindowManager wm = this.getWindowManager();
		BitmapManager bmpManager = new BitmapManager(Constants.foodLogo);
		bmpManager.loadBitmap(Utils.guidToInt(caipId), RestClient.getImageUrl(tuplj), iv);

		iv.setMaxWidth(wm.getDefaultDisplay().getWidth());
		iv.setAdjustViewBounds(true);
		
		mAttacher = new PhotoViewAttacher(iv);
		mAttacher.setOnPhotoTapListener(new PhotoTapListener());
		
		setTitle("图片");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setContentView(iv);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
	
	private class PhotoTapListener implements OnPhotoTapListener {

		@Override
		public void onPhotoTap(View view, float x, float y) {
			float xPercentage = x * 100f;
			float yPercentage = y * 100f;

			if (null != mCurrentToast) {
				mCurrentToast.cancel();
			}

			mCurrentToast = Toast.makeText(FootDetailImgActivity.this, String.format(PHOTO_TAP_TOAST_STRING, xPercentage, yPercentage), Toast.LENGTH_SHORT);
			mCurrentToast.show();
		}
	}
}