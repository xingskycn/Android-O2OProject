package com.gez.cookery.jiaoshou.fragments;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
import android.widget.Toast;
import at.technikum.mti.fancycoverflow.FancyCoverFlow;

import com.actionbarsherlock.app.SherlockFragment;
import com.gez.cookery.jiaoshou.R;
import com.gez.cookery.jiaoshou.adapter.FancyCoverFlowSampleAdapter;
import com.gez.cookery.jiaoshou.adapter.FancyCoverGukzpAdapter;
import com.gez.cookery.jiaoshou.adapter.TizAdapter;
import com.gez.cookery.jiaoshou.model.EnmFoodType;
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
import com.gez.cookery.jiaoshou.util.JsonUtil;
import com.gez.cookery.jiaoshou.util.Utils;

public class FirstPaperFragment extends SherlockFragment {
	protected static final int SELECT_PIC_BY_PICK_PHOTO = 100;

	private List<FlowTupBasic> lvZhaopViewData = new ArrayList<FlowTupBasic>();

	private Shouy shouyList = new Shouy();

	private List<Gukxc> xiangcList = new ArrayList<Gukxc>();

	private List<Guktz> guktzList = new ArrayList<Guktz>();

	private List<Taocmx> taocmxList = new ArrayList<Taocmx>();

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

	/***
	 * 从Intent获取图片路径的KEY
	 */
	public static final String KEY_PHOTO_PATH = "photo_path";

	private String riq;

	private final Calendar date = Calendar.getInstance();

	private LineChartView chart; // PieChartView决定了所显示图形的样式
	private LineChartData data; // PieChartData决定了所显示图形的数据
	private boolean hasAxes = true; // 是否有坐标轴
	private boolean hasAxesNames = true; // 是否有坐标名
	private boolean hasLines = true; // 是否有线段
	private boolean hasPoints = true; // 是否有点
	private ValueShape shape = ValueShape.CIRCLE; // 点是圆形还是矩形
	private boolean isFilled = true; // 线段面积是否填充
	private boolean hasLabels = true; // 是否有标签
	private boolean isCubic = true; // 是否平滑
	private boolean hasLabelForSelected = false; // 点被选中时是否显示标签

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
		riq = sdf.format(date.getTime()); // 获得的格式化日期字符串为yyyy-MM-dd
											// hh:mm:ss，可以直接插入数据库Da

		RestClient.getIndexData(new IJsonModelData() {

			@Override
			public void onReturn(JsonModel data) {

				if (data != null) {
					shouyList = (Shouy) data; // 获得Shouy实体所有数据
					taocmxList = shouyList.getTaocmx(); // 套餐明细列表
					guktzList = shouyList.getGuktz(); // 体重列表
					xiangcList = shouyList.getGukxc(); // 相册列表
					drawChart(shouyList.getGuktz()); // 用得到的顾客体重列表画图
				}

				if (xiangcList.size() > 0) {
					for (Gukxc blog1 : xiangcList) {
						FlowTupBasic xiangcTup = new FlowTupBasic();
						if (blog1.getZhaop().length() > 3) {
							xiangcTup.setImage(blog1.getZhaop());
							lvZhaopViewData.add(xiangcTup); // 把所有获得的图片加入List<FlowTupBasic>图片列表
						}
					}
				}

				List<mTextTz> mtextTzList = new ArrayList<mTextTz>();
				if (guktzList.size() > 0) {
					for (Guktz blog1 : guktzList) {
						mTextTz mtextTz = new mTextTz();
						if (blog1.getTiz() > 0) {
							mtextTz.setGuktz(blog1.getTiz());
							mtextTzList.add(mtextTz);
						}
					}
				}

				drawTaoc(); // FancyCoverFlow展示套餐
				showTextTz(mtextTzList); // 文字显示体重列表
				drawXiangc(); // FancyCoverFlow展示相册
			}

		});

		addMap = (Button) mRootView.findViewById(R.id.first_paper_add); // 添加顾客照片
		addTz = (Button) mRootView.findViewById(R.id.first_paper_addtz); // 添加体重

		sdcardTempFile = new File("/mnt/sdcard/", "tmp_pic_"
				+ SystemClock.currentThreadTimeMillis() + ".jpg"); // 照片保存名称

		addMap.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// 从弹出列表中选择某项并返回所选数据
				Intent intent = new Intent("android.intent.action.PICK");
				// 图片基础地址，此为内部地址
				intent.setDataAndType(
						MediaStore.Images.Media.INTERNAL_CONTENT_URI, "image/*");
				// 设置图片完整路径
				intent.putExtra("output", Uri.fromFile(sdcardTempFile));

				intent.putExtra("crop", "true");
				intent.putExtra("aspectX", 2);// 裁剪框比例
				intent.putExtra("aspectY", 3);
				intent.putExtra("outputX", 200);// 输出图片大小
				intent.putExtra("outputY", 300);
				startActivityForResult(intent, SELECT_PIC_BY_PICK_PHOTO); // 选择好图片后，图片数据发送到onActivityResult()进行处理
			}
		});
		addTz.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//设置一个子layout，作为输入框布局
				LayoutInflater inflater = LayoutInflater
						.from(FirstPaperFragment.this.getActivity());
				final View layout = inflater.inflate(
						R.layout.first_paper_tz_dialog, null);
				//弹出对话框的设置，标题为请输入体重，内容由输入框读入，按钮包括确定、取消
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
										//将输入体重与最新一次体重记录比较，设置状态、差值、百分比（差值和百分比还没做）
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

										// 插入新体重,获取到了编辑的值，剩下的就是把这个newName用TextView的setText方法放入
										try {
											RestClient.insertGuktz(riq, guktz,
													zhuangt,
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
																 // 提交完体重后再获取体重列表
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
				List<FlowTupBasic> test = lvZhaopViewData;
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
					drawChart((ArrayList<Guktz>) list);
				}
			}
		});
	}

	/**
	 * 接收addmap中获得的图片数据
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {

			String urlphoto = sdcardTempFile.getAbsolutePath();
			Log.i("FirstPaperFragment", urlphoto);
			try {
				RestClient.uploadFile(riq, urlphoto, new IJsonModelData() {
					@Override
					public void onReturn(JsonModel data) {
						if (data != null) {
							Result result = (Result) data;
							Toast.makeText(
									FirstPaperFragment.this.getActivity(),
									result.getMessage(), Toast.LENGTH_LONG)
									.show();
							getGukxc();

						} else {
							Toast.makeText(
									FirstPaperFragment.this.getActivity(),
									"上传失败了", Toast.LENGTH_LONG).show();
						}
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 简单展示顾客体重的文字列表
	 * 
	 * @param mtextTzList
	 */
	private void showTextTz(List<mTextTz> mtextTzList) {

		Gallery gallery;

		gallery = (Gallery) mRootView.findViewById(R.id.first_paper_gallery);
		TizAdapter pageAdapter = new TizAdapter(
				FirstPaperFragment.this.getActivity(), mtextTzList);
		gallery.setAdapter(pageAdapter);
	}

	/**
	 * 套餐列表
	 */
	private void drawTaoc() {

		this.fancyCoverFlow = (FancyCoverFlow) mRootView
				.findViewById(R.id.fancyCoverFlow);
		this.fancyCoverFlow.setAdapter(new FancyCoverFlowSampleAdapter(
				getActivity(), mainFragment, taocmxList));

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

	/**
	 * 相册列表
	 */
	private void drawXiangc() {
		this.fancyCoverFlow1 = (FancyCoverFlow) mRootView
				.findViewById(R.id.fancyCoverFlow1);

		List<FlowTupBasic> test = lvZhaopViewData;
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

	/**
	 * 绘制体重图表
	 * 
	 * @param list
	 */
	private void drawChart(ArrayList<Guktz> list) {
		if (list.size() > 0) {
			int len = list.size();
			chart = (LineChartView) mRootView.findViewById(R.id.chart1);
			// chart.setOnValueTouchListener(new ValueTouchListener());
			chart.setViewportCalculationEnabled(false);
			data = new LineChartData();
			List<PointValue> values = new ArrayList<PointValue>();
			float max = 0;
			float min = list.get(0).getTiz();

			for (int i = 0; i < len; i++) {
				values.add(new PointValue(i + 1, list.get(i).getTiz()));
				if (list.get(i).getTiz() > max) {
					max = list.get(i).getTiz();
				}
				if (list.get(i).getTiz() < min) {
					min = list.get(i).getTiz();
				}
			}

			max = Math.round(max);
			min = Math.round(min);

			Viewport v = new Viewport(0, max + 1, 6, min - 1);
			chart.setMaximumViewport(v);
			chart.setCurrentViewport(v);

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
				for (int i = 0; i < len; i++) {
					String orgin = list.get(i).getRiq();
					String reality = "";
					if (null != orgin && !"".equals(orgin)) {
						reality = orgin.split(" ")[0];
					}
					as.add(new AxisValue(i + 1, reality.substring(5)
							.toCharArray()));
				}
				Axis axisX = new Axis(as);
				Axis axisY = new Axis();

				if (hasAxesNames) {
					axisX.setName("时间:月-日");
					axisY.setName("斤");
				}
				data.setAxisXBottom(axisX);
				data.setAxisYLeft(axisY);
			} else {
				data.setAxisXBottom(null);
				data.setAxisYLeft(null);
			}
			chart.setLineChartData(data);
		}
	}

	// private class ValueTouchListener implements
	// LineChartOnValueSelectListener {
	//
	// @Override
	// public void onValueSelected(int lineIndex, int pointIndex,
	// PointValue value) {
	// Toast.makeText(getActivity(), "Selected: " + value,
	// Toast.LENGTH_SHORT).show();
	// }
	//
	// @Override
	// public void onValueDeselected() {
	//
	// }
	//
	// }

}
