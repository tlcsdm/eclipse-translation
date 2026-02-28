package com.tlcsdm.eclipse.translation.youdao;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import com.tlcsdm.eclipse.translation.handlers.TranslateConf;
import com.tlcsdm.eclipse.translation.utils.HttpGet;

public class YoudaoTranslateImpl implements YoudaoTranslate {
	private static final String YOUDAO_HOST = "https://openapi.youdao.com/api";

	@Override
	public String translate(String src, String from, String to) {
		Map<String, String> paramsMap = new HashMap<>();
		initParamsMap(src, from, to, paramsMap);
		return HttpGet.get(YOUDAO_HOST, paramsMap);
	}

	public String translate(String src, String from, String to, Map<String, String> paramsMap) {
		initParamsMap(src, from, to, paramsMap);
		return HttpGet.get(YOUDAO_HOST, paramsMap);
	}

	public void initParamsMap(String src, String from, String to, Map<String, String> paramsMap) {
		paramsMap.put("appKey", TranslateConf.YOUDAO_KEY);
		paramsMap.put("from", from);
		paramsMap.put("to", to);
		paramsMap.put("q", src);
		String salt = String.valueOf(System.currentTimeMillis());
		paramsMap.put("salt", salt);
		String curtime = String.valueOf(System.currentTimeMillis() / 1000);
		paramsMap.put("curtime", curtime);
		paramsMap.put("signType", "v3");
		String signStr = TranslateConf.YOUDAO_KEY + truncate(src) + salt + curtime + TranslateConf.YOUDAO_KEYFROM;
		String sign = getDigest(signStr);
		paramsMap.put("sign", sign);
	}

	private String truncate(String q) {
		if (q == null) {
			return null;
		}
		int len = q.length();
		return len <= 20 ? q : (q.substring(0, 10) + len + q.substring(len - 10, len));
	}

	/**
	 * 生成加密字段
	 */
	private String getDigest(String string) {
		if (string == null) {
			return null;
		}
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		byte[] btInput = string.getBytes(StandardCharsets.UTF_8);
		try {
			MessageDigest mdInst = MessageDigest.getInstance("SHA-256");
			mdInst.update(btInput);
			byte[] md = mdInst.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (byte byte0 : md) {
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}

}
