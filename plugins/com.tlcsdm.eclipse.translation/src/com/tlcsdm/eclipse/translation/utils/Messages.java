package com.tlcsdm.eclipse.translation.utils;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "com.tlcsdm.eclipse.translation.utils.messages";//$NON-NLS-1$
	static {
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	public static String pref_desc;
	public static String pref_link;
	public static String pref_platform;
	public static String pref_baidu_id;
	public static String pref_baidu_key;
	public static String pref_youdao_id;
	public static String pref_youdao_key;
	public static String pref_tencent_id;
	public static String pref_tencent_key;

	public static String platform_baidu;
	public static String platform_tencent;
	public static String platform_youdao;

	public static String translateConf_auto;

	public static String view_from;
	public static String view_to;
	public static String view_btn_translate;
	public static String view_auto_translate;

}
