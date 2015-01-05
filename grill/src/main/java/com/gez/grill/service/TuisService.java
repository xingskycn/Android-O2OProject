package com.gez.grill.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gez.grill.entity.Dingd;
import com.gez.grill.entity.Dingdmx;
import com.gez.grill.entity.Token;
import com.gez.grill.entity.Yanz;
import com.gez.grill.mapper.CaipMapper;
import com.gez.grill.mapper.DingdMapper;
import com.gez.grill.mapper.GukMapper;
import com.gez.grill.mapper.TuisMapper;
import com.gez.grill.util.FunctionSerializerJson;

@Service
public class TuisService {

	@Autowired
	private GukMapper gukInstance;

	@Autowired
	private DingdMapper dingdInstance;

	@Autowired
	private CaipMapper caipInstance;

	@Autowired
	private TuisMapper tuisInstance;
	
	private static String url2weix = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxf8891e9179fc47d5&secret=ee0104785a9b9b87b4d15644974887e3";
	
	private static String url2server = "http://grill.lysp.biz/";

	public void push2BusinessForWeix(String gukdh, String gukdz, String cantId, ArrayList<Dingdmx> dingdmx) {
		List<String> openIds = gukInstance.getCustomerOpenId(cantId);
		int len = openIds.size();

		if (len < 1) {
			return;
		}

		String msg = "有新订单啦！\r\n电话：\n" + gukdh + "\r\n地址：\n" + gukdz + "\r\n菜单：\n";

		ArrayList<String> Ids = new ArrayList<String>(); 
		int mxlen = dingdmx.size();
		for (int i = 0; i < mxlen; i++) {
			Ids.add(dingdmx.get(i).getCaipId());
		}

		List<String> capimc = caipInstance.getDishesName(Ids);
		int mclen = capimc.size();
		for (int j = 0; j < mclen; j++) {
			msg += capimc.get(j) + " × " + dingdmx.get(j).getFens();
			if(mclen != j - 1){
				msg += "\n";
			}
		}

		for (int k = 0; k < len; k++) {
			String current = openIds.get(k);
			if (null != current && !"".equals(current)) {
				sendText(current, msg);
			}
		}
	}

	public void push2BusinessForHurry(String dingdId) {
		Dingd current = dingdInstance.getOrderBaseAgain(dingdId);
		String cantId = current.getCantId();

		if (null != cantId && !"".equals(cantId)) {
			List<String> openIds = gukInstance.getCustomerOpenId(cantId);
			int len = openIds.size();

			if (len < 1) {
				return;
			}

			String msg = "顾客催单啦！\r\n订单编号：\n" + current.getBianh() + "\r\n顾客电话：\n" + current.getGukdh() + "\r\n下单时间：\n" + current.getXiadsj() + "\r\n确认时间：\n";
			if (null == current.getQuersj() || "".equals(current.getQuersj())) {
				msg += "未确认";
			} else {
				msg += current.getQuersj();
			}
			
			for (int i = 0; i < len; i++) {
				String now = openIds.get(i);
				if (null != now && !"".equals(now)) {
					msg += "\r\n <a href=\\\"" + url2server + "hShangjddmx.php?openId=" + now + "&dingdId=" + dingdId + "\\\">详情</a>";
					sendText(now, msg);
				}
			}
		}
	}

	private String getAccessToken() {
		String token = "";
		Yanz current = tuisInstance.getToken("02");

		token = current.getBiaos();

		Date now = new Date();
		long d = now.getTime() - current.getHuoqsj().getTime();
		if ((d / 1000) >= current.getChixsj()) {
			token = reGetToken();
		}

		return token;
	}

	@Transactional
	private String reGetToken() {
		String result = assault(url2weix, "");
		Token resp = FunctionSerializerJson.deserialize(result, new TypeReference<Token>() {});

		Yanz updated = new Yanz();
		updated.setPingtlx("02");
		updated.setBiaos(resp.getAccess_token());
		updated.setChixsj(resp.getExpires_in());
		tuisInstance.updateToken(updated);

		return resp.getAccess_token();
	}

	private void sendText(String to, String msg) {
		String token = getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + token;
		String paramstr = "{\"touser\":\"" + to + "\",\"msgtype\":\"text\",\"text\":{\"content\":\"" + msg + "\"}}";

		assault(url, paramstr);
	}

	private static String assault(String url, String params) {
		PrintWriter out = null;
		BufferedReader in = null;
		StringBuilder result = new StringBuilder();

		try {
			URL realUrl = new URL(url);
			URLConnection conn = realUrl.openConnection();
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setDoOutput(true);
			conn.setDoInput(true);

			out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), "utf-8"));
			out.print(params);
			out.flush();

			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result.append(line);
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}

		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result.toString();
	}
}
