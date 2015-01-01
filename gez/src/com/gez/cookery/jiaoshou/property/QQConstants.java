package com.gez.cookery.jiaoshou.property;

public class QQConstants {
	// public static final String APP_ID = "wxd930ea5d5a258f4f";

	public static String APP_ID = "101069256";// 申请的开发appid
	
	public static final String OPEN_ID = "openid";// 用于唯一标识用户身份（每一个openid与QQ号码对应）
	
	public static final String ACCESS_TOKEN = "access_token";// 用户进行应用邀请、分享、支付等基本业务请求的凭据
	
	public static final String EXPIRES_IN = "expires_in";// access_token的有效时间，在有效期内可以发起业务请求，过期失效
	
	public static final String WX_APP_ID = "wx8e8dc60535c9cd93";

	public static final String WX_ACTION = "action";

	public static final String WX_ACTION_INVITE = "invite";

	public static final String WX_RESULT_CODE = "ret";

	public static final String WX_RESULT_MSG = "msg";

	public static final String WX_RESULT = "result";

	public static class ShowMsgActivity {
		public static final String STitle = "showmsg_title";
		public static final String SMessage = "showmsg_message";
		public static final String BAThumbData = "showmsg_thumb_data";

	}
}
