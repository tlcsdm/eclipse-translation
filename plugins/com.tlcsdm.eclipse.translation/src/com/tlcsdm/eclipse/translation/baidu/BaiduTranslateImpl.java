package com.tlcsdm.eclipse.translation.baidu;

import com.tlcsdm.eclipse.translation.handlers.TranslateConf;

public class BaiduTranslateImpl implements BaiduTranslate {

	@Override
	public BaiduTranslateModel translate(String text, String from, String to) {
		BaiduTranslateModel mode = null;
		TransApi api = new TransApi(TranslateConf.BAIDU_APP_ID, TranslateConf.BAIDU_SECURITY_KEY);
		String result = api.getTransResult(text, from, to);
		if (result.contains("dst")) {
			mode = new BaiduTranslateModel(result);
			return mode;
		} else {
			mode = new BaiduTranslateModel();
			mode.setDst(result);
			return mode;
		}
	}

}
