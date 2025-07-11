package com.tlcsdm.eclipse.translation.handlers;

import com.tlcsdm.eclipse.translation.utils.Messages;

public class TranslateConf {

	public static String TRANSLATEPLATFORM = "";
	public static String BAIDU_APP_ID = "";
	public static String BAIDU_SECURITY_KEY = "";
	public static String YOUDAO_KEYFROM = "";
	public static String YOUDAO_KEY = "";

	/**
	 * 自动检测
	 */
	public static final String AUTO = "auto";
	/**
	 * 中文
	 */
	public static final String ZH = "zh";
	/**
	 * 英文
	 */
	public static final String EN = "en";
	/**
	 * 日文
	 */
	public static final String JA = "ja";

	public static final String[][] TRANSLATE_PLATFORM_LIST = new String[][] { { Messages.platform_baidu, "baidu" },
			{ Messages.platform_youdao, "youdao" } };

}
