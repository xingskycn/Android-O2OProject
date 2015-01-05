package com.gez.grill.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 处理数据相关类.
 */
public class FunctionData {
	/**
	 * 通用转换字符串为日期时间.
	 */
	public static Date parseDate(String isDateString) {
		Date tcDate = null;
		if (isDateString != null && !isDateString.equals("")) {
			if (isDateString.contains(" ")) {
				if (isDateString.contains(":")) {
					// 包含时间
					try {
						SimpleDateFormat tcDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						tcDate = tcDateFormat.parse(isDateString);
						return tcDate;
					} catch (Exception e) {

					}
					try {
						SimpleDateFormat tcDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
						tcDate = tcDateFormat.parse(isDateString);
						return tcDate;
					} catch (Exception e) {

					}
				} else {
					// 不包含时间
					try {
						SimpleDateFormat tcDateFormat = new SimpleDateFormat("yyyy-MM-dd");
						tcDate = tcDateFormat.parse(isDateString);
						return tcDate;
					} catch (Exception e) {

					}
				}
			} else {
				try {
					SimpleDateFormat tcDateFormat = new SimpleDateFormat("yyyy-MM-dd");
					tcDate = tcDateFormat.parse(isDateString);
					return tcDate;
				} catch (Exception e) {

				}
				try {
					SimpleDateFormat tcDateFormat = new SimpleDateFormat("yyyy-MM");
					tcDate = tcDateFormat.parse(isDateString);
					return tcDate;
				} catch (Exception e) {

				}
				try {
					SimpleDateFormat tcDateFormat = new SimpleDateFormat("HH:mm");
					tcDate = tcDateFormat.parse(isDateString);
					return tcDate;
				} catch (Exception e) {

				}
			}
		}
		return tcDate;
	}
}
