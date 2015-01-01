package com.gez.cookery.jiaoshou.util;

import java.math.BigDecimal;

public final class NumberUtil {
	
    public static double getDot(double number, int dot) {
        BigDecimal bd = new BigDecimal(number);
        bd = bd.setScale(dot, BigDecimal.ROUND_HALF_UP);
        return bd.doubleValue();
    }
    
    public static String getFloatString(float number) {
    	String value = String.valueOf(number);
    	return getNumberWithoutDot(value);
    }
    
    public static String getNumberWithoutDot(String number) {
    	if(number != null && number.indexOf(".") > 0){  
    		number = number.replaceAll("0+?$", "");//去掉多余的0  
    		number = number.replaceAll("[.]$", "");//如最后一位是.则去掉  
		}
    	return number;
    }
    
    /**
     * 获取距离的字符串表示
     * @param distance
     * @return
     */
    public static String getDistance(float distance) {
    	String str = "";
    	if (distance < 1) {
    		str = Double.toString(getDot(distance * 1000, 0)) + "m";
    	}
    	else {
    		str = Double.toString(getDot(distance, 2)) + "km";
    	}
    	
    	return str;
    }
}
