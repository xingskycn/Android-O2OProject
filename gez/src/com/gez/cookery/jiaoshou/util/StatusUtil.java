package com.gez.cookery.jiaoshou.util;

public class StatusUtil {

	public static String convertLaw(int status) {
		String sStatus = "";
		switch(status) {
		case 1:
			sStatus = "不辣";
			break;
		case 2:
			sStatus = "微辣";
			break;
		case 3:
			sStatus = "中辣";
			break;
		case 4:
			sStatus = "超辣";
			break;
		}
		
		return sStatus;
	}
	
	public static String convertCantStatus(int status) {
		String sStatus = "";
		switch(status) {
		case 1:
			sStatus = "开业";
			break;
		case 2:
			sStatus = "歇业";
			break;
		case 3:
			sStatus = "休息";
			break;
		case 4:
			sStatus = "忙碌";
			break;
		}
		
		return sStatus;
	}
	
	public static String convertDingdStatus(int status) {
		String sStatus = "";
		switch(status) {
		case 1:
			sStatus = "下单";
			break;
		case 2:
			sStatus = "确认";
			break;
		case 3:
			sStatus = "下厨";
			break;
		case 4:
			sStatus = "外送";
			break;
		case 5:
			sStatus = "送达";
			break;
		case 6:
			sStatus = "归档";
			break;
		case 7:
			sStatus = "取消";
			break;
		}
		
		return sStatus;
	}
}
