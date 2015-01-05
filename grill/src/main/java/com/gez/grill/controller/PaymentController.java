package com.gez.grill.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.util.AlipayNotify;

/**
 * 顾客控制器类
 * 
 * @author Zhaowei
 * 
 */
@Controller
@Scope(value = "prototype")
public class PaymentController {
	@RequestMapping(value = "/payNotify")
	@ResponseBody
	public String payBackNotify(HttpServletRequest request) {

		// 获取支付宝POST过来反馈信息
		Map<String, String> params = new HashMap<String, String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			params.put(name, valueStr);
		}

		// 商户订单号
		String out_trade_no = request.getParameter("out_trade_no");

		// 支付宝交易号
		String trade_no = request.getParameter("trade_no");

		// 交易状态
		String trade_status = request.getParameter("trade_status");

		// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
		if (AlipayNotify.verify(params)) {
			if (trade_status.equals("TRADE_SUCCESS")) {
				//更新订单状态
			}

			return "success";
		} else {
			return "fail";
		}
	}

}