package com.tlcsdm.eclipse.translation.utils;

import org.eclipse.jface.preference.IPreferenceStore;

import com.tlcsdm.eclipse.translation.Activator;
import com.tlcsdm.eclipse.translation.baidu.BaiduTranslateImpl;
import com.tlcsdm.eclipse.translation.baidu.BaiduTranslateModel;
import com.tlcsdm.eclipse.translation.handlers.TranslateConf;
import com.tlcsdm.eclipse.translation.preferences.TranslatePreferencePage;
import com.tlcsdm.eclipse.translation.tencent.TencentTranslateImpl;
import com.tlcsdm.eclipse.translation.tencent.TencentTranslateModel;
import com.tlcsdm.eclipse.translation.youdao.YoudaoTranslate;
import com.tlcsdm.eclipse.translation.youdao.YoudaoTranslateImpl;
import com.tlcsdm.eclipse.translation.youdao.YoudaoTranslateModel;

public class TranslationUtil {

	public static String doTranslationAction(String query, String from, String to) {
		initKey();
		if (!query.isEmpty()) {
			String translateResult = "";
			switch (TranslateConf.TRANSLATEPLATFORM) {

			case "baidu":
				translateResult = baiduTranslate(query, from, to);
				break;

			case "tencent":
				translateResult = tencentTranslate(query, from, to);
				break;

			case "youdao":
				translateResult = youdaoTranslate(query, from, to);
				break;

			default:
				break;
			}
			return translateResult;
		}
		return "";
	}

	private static void initKey() {
		IPreferenceStore preferenceStore = Activator.getDefault().getPreferenceStore();
		TranslateConf.TRANSLATEPLATFORM = preferenceStore.getString(TranslatePreferencePage.TRANSLATE_PLATFORM);
		TranslateConf.BAIDU_APP_ID = preferenceStore.getString(TranslatePreferencePage.APP_ID);
		TranslateConf.BAIDU_SECURITY_KEY = preferenceStore.getString(TranslatePreferencePage.SECURITY_KEY);
		TranslateConf.YOUDAO_KEY = preferenceStore.getString(TranslatePreferencePage.YOUDAO_KEY);
		TranslateConf.YOUDAO_KEYFROM = preferenceStore.getString(TranslatePreferencePage.YOUDAO_KEYFROM);
		TranslateConf.TENCENT_SECURITY_ID = preferenceStore.getString(TranslatePreferencePage.TENCENT_ID);
		TranslateConf.TENCENT_SECURITY_KEY = preferenceStore.getString(TranslatePreferencePage.TENCENT_KEY);
	}

	/**
	 * 百度翻译
	 * 
	 * @param src
	 * @return
	 */
	public static String baiduTranslate(String src, String from, String to) {
		BaiduTranslateModel mode = new BaiduTranslateImpl().translate(src, from, to);
		return mode.getDst();
	}

	/**
	 * 腾讯翻译
	 * 
	 * @param src
	 * @return
	 */
	public static String tencentTranslate(String src, String from, String to) {
		TencentTranslateModel mode = new TencentTranslateImpl().translate(src, from, to);
		return mode.getDst();
	}

	/**
	 * 有道翻译
	 * 
	 * @param src
	 * @return
	 */
	public static String youdaoTranslate(String src, String from, String to) {
		YoudaoTranslate translate = new YoudaoTranslateImpl();
		YoudaoTranslateModel model = new YoudaoTranslateModel();
		model.initModel(translate.translate(src, from, to));
		return model.printResult();
	}

}
