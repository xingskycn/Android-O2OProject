package com.alipay.config;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.3
 *日期：2012-08-10
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
	
 *提示：如何获取安全校验码和合作身份者ID
 *1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *2.点击“商家服务”(https://b.alipay.com/order/myOrder.htm)
 *3.点击“查询合作者身份(PID)”、“查询安全校验码(Key)”

 *安全校验码查看时，输入支付密码后，页面呈灰色的现象，怎么办？
 *解决方法：
 *1、检查浏览器配置，不让浏览器做弹框屏蔽设置
 *2、更换浏览器或电脑，重新登录查询。
 */

public class AlipayConfig {
	
	//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	// 合作身份者ID，以2088开头由16位纯数字组成的字符串
	public static String partner = "2088511426576289";
	// 商户的私钥
	public static String private_key = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAL5U5BOi0tFpj0gIxwjNljatWM0n5alYUqC6C1L3IYSi2ajzjU1Ail+NZiMyKVCGqXQtlB2H0ORY8ZRE3t4kz5gowH1VUrFF/k9y03Z5WAXiy/5URi10NTHxSFpWtys0IaDmqYTVe8rhovvfGlqmXnn+zG7Vmy76ISrjDGeC+6GRAgMBAAECgYAkIYdeduVgXZKWkRv/fs+KN6QI5g8B88NTwZ2OtV1GiYeaPAnR5XOfKf9+pPIU2Etow5I/fN3Zy0yo4d4Q+5nwqzPAahktZKzjwDjLNq6PeWGm5FgxfzXECehewFMWhxdi5bosX9vtfpFs7L+otlhJoREptPbjcvNl9URQzNmQQQJBAPmGghUjVawkOczHN17RWsWzf5YGl0yoZZzE5TWAOEf6r7SClRSoQHJIyJvbVRKHD3sgRyBA3SnLFJ8MvETzBEkCQQDDRS7sR6Y3d4LpLgZx6/dk7YZA/mGCiWCTDz61pAgCpQn9L2RCnuBqpE8UUX2VTW0pV9rz/3aMGw2Wxlw5BqMJAkEA4lSsZ2n+ir7tIp7MzZeOA2eWYcmmsSTJzlIWKdocssVjXJNZkDwxALieJijjWIPjeeZEPbA4K3GGSeIuX0gi6QJAK5Ed/P2naiyx+pCYQ4agNdaA47Nd0iAQef8mwKQOXXixF9ohBnQoIeLy+deMq2kQ9+5wHYxbyLqod4FeRiLi8QJBALN0VAQ4/Mi8/5hb/kDEolSbLiMksXNq4cFgY2T4+YZFifMKJEBMC6rtf3CXT2nSWj4cAloqNiSefDTAoHR3/aY=";
	// 支付宝的公钥，无需修改该值
	public static String ali_public_key  = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

	//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	

	// 调试用，创建TXT日志文件夹路径
	public static String log_path = "D:\\javaworkspace\\JAVA-UTF-8\\WebRoot\\log";

	// 字符编码格式 目前支持 gbk 或 utf-8
	public static String input_charset = "utf-8";
	
	// 签名方式 不需修改
	public static String sign_type = "RSA";

}
