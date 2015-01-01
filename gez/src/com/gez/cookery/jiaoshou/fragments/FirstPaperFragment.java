package com.gez.cookery.jiaoshou.fragments;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import lecho.lib.hellocharts.listener.LineChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
//import at.technikum.mti.fancycoverflow.samples.example.SimpleExample;
//import at.technikum.mti.fancycoverflow.samples.example.ViewGroupExample;
//import at.technikum.mti.fancycoverflow.samples.example.ViewGroupReflectionExample;
//import at.technikum.mti.fancycoverflow.samples.example.XmlInflateExample;
import at.technikum.mti.fancycoverflow.FancyCoverFlow;

import com.actionbarsherlock.app.SherlockFragment;
import com.gez.cookery.jiaoshou.R;
import com.gez.cookery.jiaoshou.adapter.FancyCoverFlowSampleAdapter;
import com.gez.cookery.jiaoshou.adapter.FancyCoverGukzpAdapter;
import com.gez.cookery.jiaoshou.adapter.FoodListAdapter;
import com.gez.cookery.jiaoshou.adapter.GukzpAdapter;
import com.gez.cookery.jiaoshou.adapter.TizAdapter;
import com.gez.cookery.jiaoshou.model.CaipBasic;
import com.gez.cookery.jiaoshou.model.EnmFoodType;
import com.gez.cookery.jiaoshou.model.EnmListViewAction;
import com.gez.cookery.jiaoshou.model.FlowTupBasic;
import com.gez.cookery.jiaoshou.model.Guktz;
import com.gez.cookery.jiaoshou.model.Gukxc;
import com.gez.cookery.jiaoshou.model.JsonModel;
import com.gez.cookery.jiaoshou.model.Result;
import com.gez.cookery.jiaoshou.model.Shouy;
import com.gez.cookery.jiaoshou.model.Taocmx;
import com.gez.cookery.jiaoshou.model.mTextTz;
import com.gez.cookery.jiaoshou.net.IJsonModelArrayData;
import com.gez.cookery.jiaoshou.net.IJsonModelData;
import com.gez.cookery.jiaoshou.net.RestClient;
import com.gez.cookery.jiaoshou.util.BitmapManager;
import com.gez.cookery.jiaoshou.util.JsonUtil;
import com.gez.cookery.jiaoshou.util.Utils;
import com.gez.cookery.jiaoshou.widget.PullToRefreshListView;

public class FirstPaperFragment extends SherlockFragment {
	protected static final int SELECT_PIC_BY_PICK_PHOTO = 100;
	// 列表对象
	private PullToRefreshListView lvListView; // 下拉刷新
	// 适配器
	private FoodListAdapter lvListViewAdapter;
	// 列表尾部视图
	private View lvListView_footer;
	// 列表加载更多视图
	private TextView lvListView_foot_more;
	// 加载状态视图
	private ProgressBar lvListView_foot_progress;
	// 列表数据
	// private List<FlowTupBasic> lvTaocTupViewData = new
	// ArrayList<FlowTupBasic>();

	private List<FlowTupBasic> lvZhaopViewData = new ArrayList<FlowTupBasic>();

	private Shouy shouyList = new Shouy();

	private List<Taocmx> taocList = new ArrayList<Taocmx>();

	private List<Gukxc> xiangcList = new ArrayList<Gukxc>();

	private List<Guktz> guktzList = new ArrayList<Guktz>();

	// 数据总数
	private int lvListViewSumData;

	private View mRootView;

	// 菜品类型
	private EnmFoodType foodType;
	// 餐厅ID
	private String businessId;
	// 主Fragment
	private Fragment mainFragment;

	private FancyCoverFlow fancyCoverFlow;

	private FancyCoverFlow fancyCoverFlow1;

	private Button addMap;

	private Button addTz;

	private File sdcardTempFile;

	private static int[] MARGINS = new int[] { 50, 30, 15, 20 };

	private static int LINEWIDTH = 2;

	private static float ANGEL = -30;

	private static float PADDING = 30;

	/** 获取到的图片路径 */
	private String picPath;

	private Uri photoUri;

	private Intent lastIntent;
	/***
	 * 从Intent获取图片路径的KEY
	 */
	public static final String KEY_PHOTO_PATH = "photo_path";

	private String riq;

	private final Calendar date = Calendar.getInstance();

	// 传照片的标志位
	private int flag = 0;

	private ImageView cesza;

	private BitmapManager bmpManager = new BitmapManager();

	private LineChartView chart; // PieChartView决定了所显示图形的样式
	private LineChartData data; // PieChartData决定了所显示图形的数据
	private boolean hasAxes = true;	//是否有坐标轴
	private boolean hasAxesNames = true;	//是否有坐标名
	private boolean hasLines = true;	//是否有线段
	private boolean hasPoints = true;	//是否有点
	private ValueShape shape = ValueShape.CIRCLE;	//点是圆形还是矩形
	private boolean isFilled = true;	//线段面积是否填充
	private boolean hasLabels = false;	//是否有标签
	private boolean isCubic = true;	//是否平滑
	private boolean hasLabelForSelected = true;	//点被选中时是否显示标签

	public void setMainFragment(Fragment fragment) {
		this.mainFragment = fragment;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public void setFoodType(EnmFoodType foodType) {
		this.foodType = foodType;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mRootView = inflater.inflate(R.layout.first_paper, null);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		riq = sdf.format(date.getTime());

		RestClient.getIndexData(new IJsonModelData() {

			@Override
			public void onReturn(JsonModel data) {
				int taocSize = -1;
				int zaopSize = -1;
				int guktzSize = -1;
				List<mTextTz> mtextTzList = new ArrayList<mTextTz>();
				;
				if (data != null) {
					Shouy shouy = (Shouy) data;
					shouyList = shouy;
					drawChart(shouy.getGuktz());
					taocList = shouy.getTaocmx();
					guktzList = shouy.getGuktz();
					xiangcList = shouy.getGukxc();
					taocSize = shouy.getTaocmx().size();
					guktzSize = guktzList.size();
					zaopSize = shouy.getGukxc().size();
					// shouyList = shouy;

				}

				if (zaopSize > 0) {
					for (Gukxc blog1 : xiangcList) {
						FlowTupBasic xiangcTup = new FlowTupBasic();
						if (blog1.getZhaop().length() > 3) {
							String t = blog1.getZhaop();
							xiangcTup.setImage(blog1.getZhaop());
							lvZhaopViewData.add(xiangcTup);
						}
					}
				}

				if (guktzSize > 0) {
					for (Guktz blog1 : guktzList) {

						mTextTz mtextTz = new mTextTz();
						float a = blog1.getTiz();
						if (blog1.getTiz() > 0) {
							float t = blog1.getTiz();
							mtextTz.setGuktz(t);
							mtextTzList.add(mtextTz);
						}
					}
				}

				showTextTz(mtextTzList);
				drawTaoc();
				drawXiangc();

			}

		});

		addMap = (Button) mRootView.findViewById(R.id.first_paper_add);
		addTz = (Button) mRootView.findViewById(R.id.first_paper_addtz);

		//
		sdcardTempFile = new File("/mnt/sdcard/", "tmp_pic_"
				+ SystemClock.currentThreadTimeMillis() + ".jpg");
		addMap.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent = new Intent("android.intent.action.PICK");
				intent.setDataAndType(
						MediaStore.Images.Media.INTERNAL_CONTENT_URI, "image/*");
				intent.putExtra("output", Uri.fromFile(sdcardTempFile));

				intent.putExtra("crop", "true");
				intent.putExtra("aspectX", 2);// 裁剪框比例
				intent.putExtra("aspectY", 3);
				intent.putExtra("outputX", 200);// 输出图片大小
				intent.putExtra("outputY", 300);
				startActivityForResult(intent, SELECT_PIC_BY_PICK_PHOTO);

			}
		});

		addTz.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				// LayoutInflater inflater =
				// getLayoutInflater(savedInstanceState);
				LayoutInflater inflater = LayoutInflater
						.from(FirstPaperFragment.this.getActivity());
				final View layout = inflater.inflate(
						R.layout.first_paper_tz_dialog, null);
				AlertDialog.Builder builder = new AlertDialog.Builder(
						FirstPaperFragment.this.getActivity());
				builder.setTitle("请输入体重")
						.setView(layout)
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										EditText edit = (EditText) layout
												.findViewById(R.id.tizhongid);
										String mTiz = edit.getText().toString();
										float guktz = Float.parseFloat(mTiz);
										String zhuangt = "02";

										if (shouyList.getGuktz().isEmpty() == false) {
											if (guktz < shouyList
													.getGuktz()
													.get(shouyList.getGuktz()
															.size() - 1)
													.getTiz()) {
												zhuangt = "01";
											} else if (guktz == shouyList
													.getGuktz()
													.get(shouyList.getGuktz()
															.size() - 1)
													.getTiz()) {
												zhuangt = "02";
											} else if (guktz > shouyList
													.getGuktz()
													.get(shouyList.getGuktz()
															.size() - 1)
													.getTiz()) {
												zhuangt = "03";
											}
										}

										// 这里就获取到了修改到的值，剩下的就是把这个newName用TextView的setText方法放入
										try {
											RestClient.insertGuktz(riq, guktz,
													zhuangt,
													shouyList.getCaip_id(),
													new IJsonModelData() {
														@Override
														public void onReturn(
																JsonModel data) {
															if (data != null) {
																Result result = (Result) data;
																Toast.makeText(
																		FirstPaperFragment.this
																				.getActivity(),
																		result.getMessage(),
																		Toast.LENGTH_LONG)
																		.show();
																// 提交完体重后
																// 再获取体重列表
																getGuktiz();

															} else {
																Toast.makeText(
																		FirstPaperFragment.this
																				.getActivity(),
																		"上传失败了",
																		Toast.LENGTH_LONG)
																		.show();
															}
														}
													});
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}

									}

								}).setNegativeButton("取消", null).show();

			}
		});

		return mRootView;
	}

	public void getGukxc() {
		RestClient.getGukxcList(new IJsonModelArrayData() {

			public void onReturn(List<?> data) {
				lvZhaopViewData.clear();
				List<FlowTupBasic> test = lvZhaopViewData;
				List<Gukxc> list = null;
				int zaopSize = 0;
				int size = -1;
				if (data != null) {
					list = (List<Gukxc>) data;
					size = list.size();
					xiangcList = list;
					zaopSize = list.size();
				}
				if (zaopSize > 0) {
					for (Gukxc blog1 : list) {
						FlowTupBasic xiangcTup = new FlowTupBasic();

						if (blog1.getZhaop().length() > 3) {
							String t = blog1.getZhaop();
							xiangcTup.setImage(blog1.getZhaop());
							lvZhaopViewData.add(xiangcTup);
						}
					}
				}
				test = lvZhaopViewData;
				int n = lvZhaopViewData.size();
				if (lvZhaopViewData.size() >= 1) {
					drawXiangc();
				}
			}
		});
	}

	public void getGuktiz() {
		RestClient.getGuktzList(new IJsonModelArrayData() {

			public void onReturn(List<?> data) {

				List<mTextTz> mtextTzList = new ArrayList<mTextTz>();
				List<Guktz> list = null;
				int guktzSize = 0;
				int size = -1;
				if (data != null) {
					list = (List<Guktz>) data;
					size = list.size();
				}
				if (size > 0) {
					for (Guktz blog1 : list) {
						mTextTz guktz = new mTextTz();

						if (blog1.getTiz() > 0) {
							Log.i("guktz", String.valueOf(blog1.getTiz()));
							float t = blog1.getTiz();
							guktz.setGuktz(t);
							mtextTzList.add(guktz);
						}
					}

					showTextTz(mtextTzList);
				}
			}
		});
	}

	// 精选界面返回数据,重写了接口
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {

			String urlphoto = sdcardTempFile.getAbsolutePath();
			// String urlphoto = doPhoto(requestCode,data);
			Log.i("FirstPaperFragment", urlphoto);

			try {
				RestClient.uploadFile(riq, urlphoto,
						shouyList.getTaocmx().get(0).getCaip_id(),
						new IJsonModelData() {
							@Override
							public void onReturn(JsonModel data) {
								if (data != null) {
									Result result = (Result) data;
									Toast.makeText(
											FirstPaperFragment.this
													.getActivity(),
											result.getMessage(),
											Toast.LENGTH_LONG).show();
									getGukxc();

								} else {
									Toast.makeText(
											FirstPaperFragment.this
													.getActivity(), "上传失败了",
											Toast.LENGTH_LONG).show();
								}
							}
						});
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void showTextTz(List<mTextTz> mtextTzList) {

		Gallery gallery;

		gallery = (Gallery) mRootView.findViewById(R.id.first_paper_gallery);
		TizAdapter pageAdapter = new TizAdapter(
				FirstPaperFragment.this.getActivity(), mtextTzList);
		gallery.setAdapter(pageAdapter);
	}

	private void drawTaoc() {
		// TODO Auto-generated method stub

		this.fancyCoverFlow = (FancyCoverFlow) mRootView
				.findViewById(R.id.fancyCoverFlow);
		// loadLvImageViewData();
		this.fancyCoverFlow.setAdapter(new FancyCoverFlowSampleAdapter(
				getActivity(), mainFragment, taocList));

		// 透明度
		this.fancyCoverFlow.setUnselectedAlpha(1.0f);
		this.fancyCoverFlow.setUnselectedSaturation(0.0f);
		this.fancyCoverFlow.setUnselectedScale(0.2f);
		this.fancyCoverFlow.setSpacing(10);
		this.fancyCoverFlow.setMaxRotation(0);
		this.fancyCoverFlow.setScaleDownGravity(0.2f);
		this.fancyCoverFlow
				.setActionDistance(FancyCoverFlow.ACTION_DISTANCE_AUTO);
	}

	// gallery方法
	private void drawXiangc() {
		// TODO Auto-generated method stub
		this.fancyCoverFlow1 = (FancyCoverFlow) mRootView
				.findViewById(R.id.fancyCoverFlow1);

		List<FlowTupBasic> test = null;
		test = lvZhaopViewData;
		this.fancyCoverFlow1.setAdapter(new FancyCoverGukzpAdapter(
				getActivity(), mainFragment, lvZhaopViewData));
		this.fancyCoverFlow1.setUnselectedAlpha(1.0f);
		this.fancyCoverFlow1.setUnselectedSaturation(0.0f);

		// 定义有多少未选中的图片应该缩小 一就意味着没有缩小
		this.fancyCoverFlow1.setUnselectedScale(1.0f);
		this.fancyCoverFlow1.setSpacing(50);
		this.fancyCoverFlow1.setMaxRotation(0);

		// 图片之间的错位距离
		this.fancyCoverFlow1.setScaleDownGravity(0.2f);
		this.fancyCoverFlow1
				.setActionDistance(FancyCoverFlow.ACTION_DISTANCE_AUTO);

	}

//	for (int i = 0; i < len; i++) {
//		values.add(new PointValue(i+1, list.get(i).getTiz()));
//	}
//	int len = list.size();
	
	private void drawChart(ArrayList<Guktz> list) {

		chart = (LineChartView) mRootView.findViewById(R.id.chart1);
		chart.setOnValueTouchListener(new ValueTouchListener());
		
		chart.setViewportCalculationEnabled(false);
		Viewport v = new Viewport(0, 8, 6, 0);
		chart.setMaximumViewport(v);
		chart.setCurrentViewport(v);
		
		data = new LineChartData();
		List<PointValue> values = new ArrayList<PointValue>();

		values.add(new PointValue(1, 1));
		values.add(new PointValue(2, 2));
		values.add(new PointValue(3, 3));
		values.add(new PointValue(4, 4));
		
		Line line = new Line(values);
		
		line.setShape(shape);
		line.setCubic(isCubic);
		line.setFilled(isFilled);
		line.setHasLabels(hasLabels);
		line.setHasLabelsOnlyForSelected(hasLabelForSelected);
		line.setHasLines(hasLines);
		line.setHasPoints(hasPoints);
		
		List<Line> lines = new ArrayList<Line>(1);
		lines.add(line);
		data.setLines(lines);
		
		if (hasAxes) {
			
			List<AxisValue> as = new ArrayList<AxisValue>();
			
			for(int i=1; i<=4; i++){
				as.add(new AxisValue(i, ("hello"+i).toCharArray()));
			}
			Axis axisX = new Axis(as);
			Axis axisY = new Axis();	
				
			if (hasAxesNames) {
				axisX.setName("Axis X");
				axisY.setName("Axis Y");
			}
			data.setAxisXBottom(axisX);
			data.setAxisYLeft(axisY);
		} else {
			data.setAxisXBottom(null);
			data.setAxisYLeft(null);
		}
		chart.setLineChartData(data);		
	}
	
	private class ValueTouchListener implements LineChartOnValueSelectListener {

		@Override
		public void onValueSelected(int lineIndex, int pointIndex, PointValue value) {
			Toast.makeText(getActivity(), "Selected: " + value, Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onValueDeselected() {
			// TODO Auto-generated method stub

		}

	}

}
