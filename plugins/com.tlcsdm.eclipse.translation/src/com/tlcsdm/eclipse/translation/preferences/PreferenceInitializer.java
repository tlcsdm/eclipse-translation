package com.tlcsdm.eclipse.translation.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import com.tlcsdm.eclipse.translation.Activator;

public class PreferenceInitializer extends AbstractPreferenceInitializer {

	@Override
	public void initializeDefaultPreferences() {
		String appid = "";
		String securityKey = "";
		String youdaoKey = "";
		String youdaoKeyFrom = "";
		String tencentId = "";
		String tencentKey = "";
		String defaultPlatform = "baidu";

		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault(TranslatePreferencePage.TRANSLATE_PLATFORM, defaultPlatform);
		store.setDefault(TranslatePreferencePage.APP_ID, appid);
		store.setDefault(TranslatePreferencePage.SECURITY_KEY, securityKey);
		store.setDefault(TranslatePreferencePage.YOUDAO_KEY, youdaoKey);
		store.setDefault(TranslatePreferencePage.YOUDAO_KEYFROM, youdaoKeyFrom);
		store.setDefault(TranslatePreferencePage.TENCENT_ID, tencentId);
		store.setDefault(TranslatePreferencePage.TENCENT_KEY, tencentKey);
	}

}
