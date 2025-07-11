package com.tlcsdm.eclipse.translation.preferences;

import javax.crypto.SecretKey;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import com.tlcsdm.eclipse.translation.Activator;
import com.tlcsdm.eclipse.translation.utils.AesUtil;

public class PreferenceInitializer extends AbstractPreferenceInitializer {

	private static final String FIXED_KEY_STR = "my16TranSlatIoN!";

	@Override
	public void initializeDefaultPreferences() {
		SecretKey key = AesUtil.getFixedKey(FIXED_KEY_STR);
		String encryptedAppid = "z4D6WMgA05oqGSSBRGNafqybEiVNBqlLEdVme+oCuMo=";
		String encryptedSecurityKey = "pLCaiVPwwHEmYdjoVrHNCZ9F5Kpr8oj67F2efi76RtM=";
		String encryptedYoudaoKey = "IHSJRGQRqkI2lzu1380mIJALGp4btT8KBDhlV7BMvk4=";
		String encryptedYoudaoKeyFrom = "gUcJvEwI5MAmMtiUATqVOeXn+c/u2ybsiunw6yRmI3mQCxqeG7U/CgQ4ZVewTL5O";

		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault(TranslatePreferencePage.TRANSLATE_PLATFORM, "baidu");
		store.setDefault(TranslatePreferencePage.APP_ID, AesUtil.decrypt(encryptedAppid, key));
		store.setDefault(TranslatePreferencePage.SECURITY_KEY, AesUtil.decrypt(encryptedSecurityKey, key));
		store.setDefault(TranslatePreferencePage.YOUDAO_KEY, AesUtil.decrypt(encryptedYoudaoKey, key));
		store.setDefault(TranslatePreferencePage.YOUDAO_KEYFROM, AesUtil.decrypt(encryptedYoudaoKeyFrom, key));
	}

}
