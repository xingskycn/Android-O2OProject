package com.gez.cookery.jiaoshou.net;

import java.io.File;
import java.util.List;

import org.apache.http.Header;

import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.gez.cookery.jiaoshou.activity.ActivityGlobal;
import com.gez.cookery.jiaoshou.model.*;
import com.gez.cookery.jiaoshou.util.JsonUtil;
import com.gez.cookery.jiaoshou.util.StringUtils;
import com.google.gson.reflect.TypeToken;

public class RestClient {

	private static String tag = "RestClient";

	public static final int PAGE_SIZE = 10;
	public static final int MAP_PAGE_SIZE = 100;
	
	
//	private static final String BASE_URL = "http://192.168.1.229:8070/";
	
	private static final String BASE_URL = "http://alpha.glassware.stew.lysp.biz/";
//	 private static final String BASE_URL = "http://192.168.1.102:8080/";

	// private static final String BASE_IMAGE_URL =
	// "http://192.168.1.92:8090/resources/files/";
	// private static final String BASE_IMAGE_URL =
	// "http://alpha.woodware.cookery.lysp.biz/resources/files/";
//	private static final String BASE_IMAGE_URL = "http://alpha2.fruit.lysp.biz/resources/files/";
	
	private static final String BASE_IMAGE_URL="http://alpha.woodware.stew.lysp.biz/resources/files/";
//	private static final String BASE_IMAGE_URL = "http://192.168.1.229:8090/resources/files/";
//	private static final String TEST_URL = "http://192.168.1.102:8080/shouy/getShouyList";
	
	private static final String GET_SHOUY = "shouy/getShouyList";

	// 获取用户数据
	private static final String GET_CUSTOMER_INFO = "guk/getCustomerInfo";
	// 餐厅列表
	private static final String GET_BUSINESS_URL = "cant/getRestaurantList";
	// 收藏餐厅列表
	private static final String GET_BUSINESS_FAVORITE_URL = "cant/getMyFavoriteRestaurant";
	// 菜品列表
	private static final String GET_DISHES_URL = "caip/getDishesList";
	// 收藏菜品
	private static final String GET_MYFAVORITE_DISHES = "caip/getMyFavoriteDishes";
	// 特色菜
	private static final String GET_TES_DISHES = "caip/getSpecialDishesList";
	// 餐厅详情
	// private static final String GET_BUSINESS_DETAIL =
	// "cant/getRestaurantDetailForSingel";

	private static final String GET_BUSINESS_DETAIL = "cant/getRestaurantDetail";
	// 订单列表
	private static final String GET_ORDER_LIST = "dingd/getOrdersList";
	// 收藏餐厅
	private static final String COLLECT_BUSINESS = "cant/collectRestaurant";
	// 收藏菜品
	private static final String COLLECT_FOOD = "caip/collectDishes";
	// 菜品详情
	private static final String GET_FOOD_DETAIL = "caip/getDishesDetail";
	
	private static final String GET_FOOD_DETAIL_IMG = "caip/getDishesDetailList";
	// 菜品评论
	private static final String GET_FOOD_COMMENT = "caip/getCommentForDishes";
	// 催单
	private static final String GET_HURRY = "dingd/getHurry";
	// 餐厅评论
	private static final String GET_BUSINESS_COMMENT = "cant/getCommentForRestaurant";
	// 提交订单
	private static final String SUBMIT_ORDERS = "dingd/submitOrders";
	// 点评订单
	private static final String ORDER_COMMENT = "dingd/getOrdersCommentList";
	// 提交点评
	private static final String ADD_ORDER_COMMENT = "dingd/addCommentsForOrder";
	// 绑定账号
	private static final String BIND_ACCOUNT = "guk/bindAccount";
	// 意见反馈
	private static final String FEED_BACK = "guk/feedback";
	// 获取最新版本信息
	private static final String GET_VERSION = "guk/version";

	private static final String UPLOAD_PHOTO = "shouy/insertGukxc";
	//上传顾客体重	
	private static final String UPLOAD_GUKTZ = "shouy/insertGuktz";
	
	private static final String GET_GUKXC = "shouy/getGukxcList";
	//获取顾客的体重
	private static final String GET_GUKTZ = "shouy/getGuktzList";

	private static AsyncHttpClient client = new AsyncHttpClient();

	private static String getAbsoluteUrl(String relativeUrl) {
		return BASE_URL + relativeUrl;
	}

	public static String getImageUrl(String image) {
		if (StringUtils.isEmpty(image)) {
			return "";
		}

		Log.i("imageIf",image);
		
		if (image.length() >= 36) {
			String t = BASE_IMAGE_URL + image.substring(0, 36) + ".image";
			Log.i("imageIf",image);
			return BASE_IMAGE_URL + image.substring(0, 36) + ".image";
			
		} else {
			return "";
		}	
	}

	public static void getBusinessList(final EnmBusinessType businessType,
			final int pageIndex, final double latitude, final double longitude,
			final IJsonModelArrayData modelData) {
		RequestParams params = new RequestParams();
		params.put("pageIndex", Integer.toString(pageIndex));

		String url = "";
		switch (businessType) {
		case business_default:
			url = GET_BUSINESS_URL;
			params.put("pageSize", Integer.toString(PAGE_SIZE));
			break;
		case business_favorite:
			url = GET_BUSINESS_FAVORITE_URL;
			params.put("gukId", ActivityGlobal.getUserId());
			params.put("pageSize", Integer.toString(PAGE_SIZE));
			break;
		case business_map:
			url = GET_BUSINESS_URL;
			params.put("pageSize", Integer.toString(MAP_PAGE_SIZE));
			break;
		}

		params.put("jingd", Double.toString(longitude));
		params.put("weid", Double.toString(latitude));

		client.get(getAbsoluteUrl(url), params, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] responseBody) {

				List<CantBasic> list = JsonUtil.fromJson(new String(
						responseBody), new TypeToken<List<CantBasic>>() {
				}.getType());
				modelData.onReturn(list);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] responseBody, Throwable error) {
				modelData.onReturn(null);
			}
		});
	}

	public static void getBusinessDetail(final String cantId,
			final IJsonModelData modelData) {
		RequestParams params = new RequestParams();
		params.put("cantId", cantId);
		params.put("gukId", ActivityGlobal.getUserId());

		Log.i(tag, "ActivityGlobal.getUserId()=" + ActivityGlobal.getUserId());

		client.get(getAbsoluteUrl(GET_BUSINESS_DETAIL), params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int statusCode, Header[] headers,
							byte[] responseBody) {

						System.out.println(new String(responseBody));
						Cantxq list = JsonUtil.fromJson(
								new String(responseBody), Cantxq.class);
						modelData.onReturn(list);
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							byte[] responseBody, Throwable error) {
						System.out.println(new String(responseBody));
						modelData.onReturn(null);
					}
				});
	}

	public static void getFoodList(final EnmFoodType foodType,
			final int pageIndex, final String cantId,
			final IJsonModelArrayData modelData) {
		RequestParams params = new RequestParams();
		params.put("pageIndex", Integer.toString(pageIndex)); // 查看网页要用
		params.put("pageSize", Integer.toString(PAGE_SIZE));
		params.put("cantId", cantId);

		Log.i(tag, "toString(pageIndex)=" + Integer.toString(pageIndex));
		Log.i(tag, "toString(PAGE_SIZE)=" + Integer.toString(PAGE_SIZE));
		Log.i(tag, "cantId=" + cantId);

		String url = "";
		switch (foodType) {
		default:
		case food_default:
			url = GET_DISHES_URL;
			break;
		case food_favorite:
			url = GET_MYFAVORITE_DISHES;
			params.put("gukId", ActivityGlobal.getUserId());
			break;
		// case food_tej:
		// url = GET_TES_DISHES;
		// params.put("tes", "tej");
		// break;
		case food_tes:
			url = GET_TES_DISHES;
			// params.put("tes", "tes");
			break;
		}

		client.get(getAbsoluteUrl(url), params, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] responseBody) {

				List<CaipBasic> list = JsonUtil.fromJson(new String(
						responseBody), new TypeToken<List<CaipBasic>>() {
				}.getType());
				modelData.onReturn(list);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] responseBody, Throwable error) {

				modelData.onReturn(null);
			}
		});
	}

	public static void getFoodDetail(final String caipId,
			final IJsonModelData modelData) {
		RequestParams params = new RequestParams();
		params.put("caipId", caipId);
		params.put("gukId", ActivityGlobal.getUserId());

		client.get(getAbsoluteUrl(GET_FOOD_DETAIL), params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int statusCode, Header[] headers,
							byte[] responseBody) {

						Caipxq list = JsonUtil.fromJson(
								new String(responseBody), Caipxq.class);
						modelData.onReturn(list);
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							byte[] responseBody, Throwable error) {

						modelData.onReturn(null);
					}
				});
	}

	
	
	public static void getFoodCommentList(final String caipId,
			final int pageIndex, final IJsonModelArrayData modelData) {
		RequestParams params = new RequestParams();
		params.put("pageIndex", Integer.toString(pageIndex));
		params.put("pageSize", Integer.toString(PAGE_SIZE));
		params.put("caipId", caipId);

		client.get(getAbsoluteUrl(GET_FOOD_COMMENT), params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int statusCode, Header[] headers,
							byte[] responseBody) {

						List<Pingl> list = JsonUtil.fromJson(new String(
								responseBody), new TypeToken<List<Pingl>>() {
						}.getType());
						modelData.onReturn(list);
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							byte[] responseBody, Throwable error) {

						modelData.onReturn(null);
					}
				});
	}

	public static void getBusinessCommentList(final String cantId,
			final int pageIndex, final IJsonModelArrayData modelData) {
		RequestParams params = new RequestParams();
		params.put("pageIndex", Integer.toString(pageIndex));
		params.put("pageSize", Integer.toString(PAGE_SIZE));
		params.put("cantId", cantId);

		client.get(getAbsoluteUrl(GET_BUSINESS_COMMENT), params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int statusCode, Header[] headers,
							byte[] responseBody) {

						List<Pingl> list = JsonUtil.fromJson(new String(
								responseBody), new TypeToken<List<Pingl>>() {
						}.getType());
						modelData.onReturn(list);
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							byte[] responseBody, Throwable error) {

						modelData.onReturn(null);
					}
				});
	}
	
	public static void getFoodDetailImg(final String caipId, final IJsonModelArrayData modelData) {
		RequestParams params = new RequestParams();
		params.put("caip_id", caipId);

		client.get(getAbsoluteUrl(GET_FOOD_DETAIL_IMG), params,
			new AsyncHttpResponseHandler() {

				@Override
				public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
					List<Taocmx> list = JsonUtil.fromJson(new String(responseBody), new TypeToken<List<Taocmx>>() {}.getType());
					modelData.onReturn(list);
				}

				@Override
				public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
					modelData.onReturn(null);
				}
			});
	}

	public static void getCustomerInfo(final String gukId,
			final IJsonModelData modelData) {
		RequestParams params = new RequestParams();
		params.put("gukId", gukId);

		client.get(getAbsoluteUrl(GET_CUSTOMER_INFO), params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int statusCode, Header[] headers,
							byte[] responseBody) {

						GukBasic list = JsonUtil.fromJson(new String(
								responseBody), GukBasic.class);
						modelData.onReturn(list);
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							byte[] responseBody, Throwable error) {

						modelData.onReturn(null);
					}
				});
	}

	public static void getOrderList(final int pageIndex,
			final IJsonModelArrayData modelData) {
		RequestParams params = new RequestParams();
		params.put("pageIndex", Integer.toString(pageIndex));
		params.put("pageSize", Integer.toString(PAGE_SIZE));
		params.put("gukId", ActivityGlobal.getUserId());

		client.get(getAbsoluteUrl(GET_ORDER_LIST), params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int statusCode, Header[] headers,
							byte[] responseBody) {

						List<DingdBasic> list = JsonUtil.fromJson(new String(
								responseBody),
								new TypeToken<List<DingdBasic>>() {
								}.getType());
						modelData.onReturn(list);
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							byte[] responseBody, Throwable error) {

						modelData.onReturn(null);
					}
				});
	}

	public static void collectFood(final String caipId,
			final IJsonModelData modelData) {
		RequestParams params = new RequestParams();
		params.put("caipId", caipId);
		params.put("gukId", ActivityGlobal.getUserId());

		client.post(getAbsoluteUrl(COLLECT_FOOD), params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int statusCode, Header[] headers,
							byte[] responseBody) {

						Result list = JsonUtil.fromJson(
								new String(responseBody), Result.class);
						modelData.onReturn(list);
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							byte[] responseBody, Throwable error) {

						modelData.onReturn(null);
					}
				});
	}

	public static void collectBusiness(final String cantId,
			final IJsonModelData modelData) {
		RequestParams params = new RequestParams();
		params.put("cantId", cantId);
		params.put("gukId", ActivityGlobal.getUserId());

		client.post(getAbsoluteUrl(COLLECT_BUSINESS), params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int statusCode, Header[] headers,
							byte[] responseBody) {

						Result list = JsonUtil.fromJson(
								new String(responseBody), Result.class);
						modelData.onReturn(list);
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							byte[] responseBody, Throwable error) {

						modelData.onReturn(null);
					}
				});
	}

	public static void getHurry(final String dingdId,
			final IJsonModelData modelData) {
		RequestParams params = new RequestParams();
		params.put("dingdId", dingdId);

		Log.i(tag, "hurrydingdId=" + dingdId);

		client.post(getAbsoluteUrl(GET_HURRY), params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int statusCode, Header[] headers,
							byte[] responseBody) {

						Result list = JsonUtil.fromJson(
								new String(responseBody), Result.class);
						modelData.onReturn(list);
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							byte[] responseBody, Throwable error) {

						modelData.onReturn(null);
					}
				});
	}

	public static void submitOrders(final String gukdh, final String gukscdz,
			final String gukbz, final String songcsjxz, final String songcsj,
			final String zhiffs, final String cantId, final String dingdmx,
			final String peisf, final String duojf, final String shaojf,
			final String duofl, final String shaofl, final String bufc,
			final String bufs, final String bufj, final String buykz,
			final IJsonModelData modelData) {
		RequestParams params = new RequestParams();
		params.put("gukdh", gukdh);
		params.put("gukscdz", gukscdz);
		params.put("gukbz", gukbz);
		params.put("songcsjxz", songcsjxz);
		params.put("songcsj", songcsj);
		params.put("zhiffs", zhiffs);
		params.put("cantId", cantId);
		params.put("dingdmx", dingdmx);
		params.put("peisf", peisf);
		params.put("duojf", duojf);
		params.put("shaojf", shaojf);
		params.put("duofl", duofl);
		params.put("shaofl", shaofl);
		params.put("bufc", bufc);
		params.put("bufs", bufs);
		params.put("bufj", bufj);
		params.put("buykz", buykz);
		params.put("gukId", ActivityGlobal.getUserId());

		Log.i(tag, "gukdh=" + gukdh);
		Log.i(tag, "gukscdz=" + gukscdz);
		Log.i(tag, "gukbz=" + gukbz);
		Log.i(tag, "songcsjxz=" + songcsjxz);
		Log.i(tag, "songcsj=" + songcsj);
		Log.i(tag, "zhiffs=" + zhiffs);
		Log.i(tag, "cantId=" + cantId);
		Log.i(tag, "dingdmx=" + dingdmx);
		Log.i(tag, "peisf=" + peisf);
		Log.i(tag, "duojf=" + duojf);
		Log.i(tag, "shaojf=" + shaojf);
		Log.i(tag, "duofl=" + duofl);
		Log.i(tag, "shaofl=" + shaofl);
		Log.i(tag, "bufc=" + bufc);
		Log.i(tag, "bufs=" + bufs);
		Log.i(tag, "bufj=" + bufj);
		Log.i(tag, "buykz=" + buykz);
		Log.i(tag, "gukId=" + ActivityGlobal.getUserId());

		client.post(getAbsoluteUrl(SUBMIT_ORDERS), params, // SUBMIT_ORDERS
															// "http://192.168.1.175:8090/dingd/submitOrders"
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int statusCode, Header[] headers,
							byte[] responseBody) {

						GukBasic gukBasic = JsonUtil.fromJson(new String(
								responseBody), GukBasic.class);
						if (gukBasic == null) {
							Result list = JsonUtil.fromJson(new String(
									responseBody), Result.class);
							modelData.onReturn(list);
						} else
							modelData.onReturn(gukBasic);

						// Log.i(tag,"gukBasic="+gukBasic.toString(gukBasic));

					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							byte[] responseBody, Throwable error) {
						modelData.onReturn(null);
					}
				});
	}
	
	
	
	
	

	public static void getOrderCommentList(final String dingdId,
			final IJsonModelData modelData) {
		RequestParams params = new RequestParams();
		params.put("dingdId", dingdId);

		client.get(getAbsoluteUrl(ORDER_COMMENT), params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int statusCode, Header[] headers,
							byte[] responseBody) {

						Dingddp list = JsonUtil.fromJson(new String(
								responseBody), Dingddp.class);
						modelData.onReturn(list);
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							byte[] responseBody, Throwable error) {

						modelData.onReturn(null);
					}
				});
	}

	public static void addOrderComment(final String dingdId,
			final String pingl, final IJsonModelData modelData) {
		RequestParams params = new RequestParams();
		params.put("dingdId", dingdId);
		params.put("pingl", pingl);
		params.put("gukId", ActivityGlobal.getUserId());

		client.post(getAbsoluteUrl(ADD_ORDER_COMMENT), params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
						Result list = JsonUtil.fromJson(new String(responseBody), Result.class);
						modelData.onReturn(list);
					}

					@Override
					public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
						modelData.onReturn(null);
					}
				});
	}

	public static void bindAccount(final String qqh, final String weibm,
			final String dianh1, final String dianh2, final String diz1,
			final String diz2, final String diz3, final String diz4,
			final String diz5, final IJsonModelData modelData) {
		RequestParams params = new RequestParams();
		params.put("qqh", qqh);
		params.put("weibm", weibm);
		params.put("dianh1", dianh1);
		params.put("dianh2", dianh2);
		params.put("diz1", diz1);
		params.put("diz2", diz2);
		params.put("diz3", diz3);
		params.put("diz4", diz4);
		params.put("diz5", diz5);
		params.put("gukId", ActivityGlobal.getUserId());

		client.post(getAbsoluteUrl(BIND_ACCOUNT), params, new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
						// System.out.println(new String(responseBody));
						GukBasic list = JsonUtil.fromJson(new String(responseBody), GukBasic.class);
						modelData.onReturn(list);
					}

					@Override
					public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

						modelData.onReturn(null);
					}
				});
	}
	
	

	public static void feedBack(final String gukfk, final IJsonModelData modelData) {
		RequestParams params = new RequestParams();
		params.put("gukfk", gukfk);
		params.put("gukId", ActivityGlobal.getUserId());

		client.post(getAbsoluteUrl(FEED_BACK), params, new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
						System.out.println(new String(responseBody));
						Result list = JsonUtil.fromJson(new String(responseBody), Result.class);
						modelData.onReturn(list);
					}

					@Override
					public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
						modelData.onReturn(null);
					}
				});
	}
	
	

	public static void getLatestVersion(final IJsonModelData modelData) {

		client.get(getAbsoluteUrl(GET_VERSION), new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				System.out.println(new String(responseBody));
				Banb list = JsonUtil.fromJson(new String(responseBody), Banb.class);
				modelData.onReturn(list);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
				System.out.println(new String(responseBody));
				modelData.onReturn(null);
			}
		});
	}
	
	
	
	
	
	public static void getIndexData(final IJsonModelData modelData) {
		RequestParams params = new RequestParams();
		params.put("guk_id", ActivityGlobal.getUserId());
		
		client.get(getAbsoluteUrl(GET_SHOUY), params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				
				System.out.println(new String(responseBody));
				
				Shouy list = JsonUtil.fromJson(new String(responseBody), Shouy.class);
				modelData.onReturn(list);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
				modelData.onReturn(null);
			}
		});
	}
	
	
	
	
	
	
	
	public static void uploadFile(
			String riq,
			String path,
			String caip_id,
			final IJsonModelData modelData) throws Exception {  
		File file = new File(path);  
		if (file.exists() && file.length() > 0) {  
		    AsyncHttpClient client = new AsyncHttpClient();  
		    RequestParams params = new RequestParams();   
		    params.put("riq", riq);  
		    params.put("zhaop", file);
		    params.put("type", "iloveyou"); 
		    params.put("guk_id",ActivityGlobal.getUserId());
		    params.put("caip_id", "56AA2B9E-6EF2-41CB-B10E-DD0CF8C7C229");  
		    
		    // 上传文件  
		    client.post(getAbsoluteUrl(UPLOAD_PHOTO), params, new AsyncHttpResponseHandler() {  
		        @Override  
		        public void onSuccess(int statusCode, Header[] headers,  
		                byte[] responseBody) {  
		        	
					Result list = JsonUtil.fromJson(
							new String(responseBody), Result.class);
					modelData.onReturn(list);
		            // 上传成功后要做的工作  
//		            Toast.makeText(mContext, "上传成功", Toast.LENGTH_LONG).show();  
//		            progress.setProgress(0);  
		        }  
		  
		        @Override  
		        public void onFailure(int statusCode, Header[] headers,  
		                byte[] responseBody, Throwable error) { 
		        	
		        	modelData.onReturn(null);
		            // 上传失败后要做到工作  
//		            Toast.makeText(mContext, "上传失败", Toast.LENGTH_LONG).show();  /
		        }  
		  
		        
		        @Override  
		        public void onProgress(int bytesWritten, int totalSize) {  
		            // TODO Auto-generated method stub  
		            super.onProgress(bytesWritten, totalSize);  
		            int count = (int) ((bytesWritten * 1.0 / totalSize) * 100);  
		            // 上传进度显示  
//		            progress.setProgress(count);  
//		            Log.e("上传 Progress>>>>>", bytesWritten + " / " + totalSize);  
		        }  
		  
//		        @Override  
//		        public void onRetry(int retryNo) {  
//		            // TODO Auto-generated method stub  
//		            super.onRetry(retryNo);  
//		            // 返回重试次数  
//		        }  
		  
		    });  
		}
//		else {  
//		    Toast.makeText(mContext, "文件不存在", Toast.LENGTH_LONG).show();  
//		}  
	}
	
	
	
	
	
	
	
	public static void insertGuktz(
			String riq,
			float guktz,
			String zhuangt,
			String caip_id,
			final IJsonModelData modelData) throws Exception {  
		
		    AsyncHttpClient client = new AsyncHttpClient();  
		    RequestParams params = new RequestParams();   
		    params.put("riq", riq);  
		    params.put("tiz", Float.toString(guktz));
		    params.put("zhuangt", zhuangt); 
		    params.put("guk_id", ActivityGlobal.getUserId());
		    
		    // 上传文件  
		    client.post(getAbsoluteUrl(UPLOAD_GUKTZ), params, new AsyncHttpResponseHandler() {  
		        @Override  
		        public void onSuccess(int statusCode, Header[] headers,  
		                byte[] responseBody) {  
		        	
					Result list = JsonUtil.fromJson(
							new String(responseBody), Result.class);
					modelData.onReturn(list);
		        }  
		  
		        @Override  
		        public void onFailure(int statusCode, Header[] headers,  
		                byte[] responseBody, Throwable error) { 
		        	
		        	modelData.onReturn(null);
		        }  
		  
		    });  
	}
	

	
	public static void getGukxcList(final IJsonModelArrayData modelData) {
		RequestParams params = new RequestParams();
		params.put("guk_id", ActivityGlobal.getUserId());

		client.get(getAbsoluteUrl(GET_GUKXC), params, new AsyncHttpResponseHandler() {
				@Override
				public void onSuccess(int statusCode, Header[] headers,
						byte[] responseBody) {

					List<Gukxc> list = JsonUtil.fromJson(new String(
							responseBody), new TypeToken<List<Gukxc>>() {
					}.getType());
					modelData.onReturn(list);
				}

				@Override
				public void onFailure(int statusCode, Header[] headers,
						byte[] responseBody, Throwable error) {

					modelData.onReturn(null);
				}
			});
		}

	
	
	public static void getGuktzList(final IJsonModelArrayData modelData) {
		RequestParams params = new RequestParams();
		params.put("guk_id", ActivityGlobal.getUserId());
		
		client.get(getAbsoluteUrl(GET_GUKTZ), params, new AsyncHttpResponseHandler() {
				@Override
				public void onSuccess(int statusCode, Header[] headers,
						byte[] responseBody) {

					List<Guktz> list = JsonUtil.fromJson(new String(
							responseBody), new TypeToken<List<Guktz>>() {
					}.getType());
					modelData.onReturn(list);
				}

				@Override
				public void onFailure(int statusCode, Header[] headers,
						byte[] responseBody, Throwable error) {

					modelData.onReturn(null);
				}
			});
		}
	
	
	
}
