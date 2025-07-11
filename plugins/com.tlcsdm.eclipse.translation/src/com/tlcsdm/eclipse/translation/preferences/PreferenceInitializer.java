package com.tlcsdm.eclipse.translation.preferences;

import javax.crypto.SecretKey;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import com.sun.jna.platform.win32.Advapi32Util;
import com.sun.jna.platform.win32.WinReg;
import com.tlcsdm.eclipse.translation.Activator;
import com.tlcsdm.eclipse.translation.utils.AesUtil;

public class PreferenceInitializer extends AbstractPreferenceInitializer {

	private static final String FIXED_KEY_STR = "my16TranSlatIoN!";

	@Override
	public void initializeDefaultPreferences() {
		String appid = "";
		String securityKey = "";
		String youdaoKey = "";
		String youdaoKeyFrom = "";

		boolean isWindows = System.getProperty("os.name").toLowerCase().contains("win");
		String orgName = getWindowsOrganization();
		String owner = getWindowsOwner();
		// For Renesas
		if (isWindows && ((orgName != null && orgName.toLowerCase().contains("renesas"))
				|| (owner != null && owner.toLowerCase().contains("rdb")))) {
			SecretKey key = AesUtil.getFixedKey(FIXED_KEY_STR);
			String encryptedAppid = "z4D6WMgA05oqGSSBRGNafqybEiVNBqlLEdVme+oCuMo=";
			String encryptedSecurityKey = "pLCaiVPwwHEmYdjoVrHNCZ9F5Kpr8oj67F2efi76RtM=";
			String encryptedYoudaoKey = "IHSJRGQRqkI2lzu1380mIJALGp4btT8KBDhlV7BMvk4=";
			String encryptedYoudaoKeyFrom = "gUcJvEwI5MAmMtiUATqVOeXn+c/u2ybsiunw6yRmI3mQCxqeG7U/CgQ4ZVewTL5O";

			appid = AesUtil.decrypt(encryptedAppid, key);
			securityKey = AesUtil.decrypt(encryptedSecurityKey, key);
			youdaoKey = AesUtil.decrypt(encryptedYoudaoKey, key);
			youdaoKeyFrom = AesUtil.decrypt(encryptedYoudaoKeyFrom, key);
		}

		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault(TranslatePreferencePage.TRANSLATE_PLATFORM, "baidu");
		store.setDefault(TranslatePreferencePage.APP_ID, appid);
		store.setDefault(TranslatePreferencePage.SECURITY_KEY, securityKey);
		store.setDefault(TranslatePreferencePage.YOUDAO_KEY, youdaoKey);
		store.setDefault(TranslatePreferencePage.YOUDAO_KEYFROM, youdaoKeyFrom);
	}

	private String getWindowsOrganization() {
		try {
			return Advapi32Util.registryGetStringValue(WinReg.HKEY_LOCAL_MACHINE,
					"SOFTWARE\\Microsoft\\Windows NT\\CurrentVersion", "RegisteredOrganization");
		} catch (Exception e) {
			return null;
		}
	}

	private String getWindowsOwner() {
		try {
			return Advapi32Util.registryGetStringValue(WinReg.HKEY_LOCAL_MACHINE,
					"SOFTWARE\\Microsoft\\Windows NT\\CurrentVersion", "RegisteredOwner");
		} catch (Exception e) {
			return null;
		}
	}

}
