package com.tlcsdm.eclipse.translation.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import com.tlcsdm.eclipse.translation.Activator;

public class PreferenceInitializer extends AbstractPreferenceInitializer {

	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault(TranslatePreferencePage.TRANSLATE_PLATFORM, "baidu");
		store.setDefault(TranslatePreferencePage.APP_ID, "");
		store.setDefault(TranslatePreferencePage.SECURITY_KEY, "");
		store.setDefault(TranslatePreferencePage.YOUDAO_KEY, "");
		store.setDefault(TranslatePreferencePage.YOUDAO_KEYFROM, "");
	}

}
