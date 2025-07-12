package com.tlcsdm.eclipse.translation.tencent;

import com.tlcsdm.eclipse.translation.handlers.TranslateConf;

public class TencentTranslateImpl implements TencentTranslate {

	@Override
	public TencentTranslateModel translate(String text, String from, String to) {
		TencentTranslateModel mode = null;
		TencentTransApi api = new TencentTransApi(TranslateConf.TENCENT_SECURITY_ID,
				TranslateConf.TENCENT_SECURITY_KEY);
		String result = api.getTransResult(text, from, to);
		if (result.contains("TargetText")) {
			mode = new TencentTranslateModel(result);
			return mode;
		} else {
			mode = new TencentTranslateModel();
			mode.setDst(result);
			return mode;
		}
	}

}
