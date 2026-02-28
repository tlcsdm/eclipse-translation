package com.tlcsdm.eclipse.translation.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.tlcsdm.eclipse.translation.Activator;

/**
 * MD5编码相关的类
 * 
 * @author wangjingtao
 * 
 */
public class MD5 {
	// 首先初始化一个字符数组，用来存放每个16进制字符
	private static final char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
			'e', 'f' };
	private static final ILog LOG = Activator.getDefault().getLog();

	/**
	 * 获得一个字符串的MD5值
	 * 
	 * @param input 输入的字符串
	 * @return 输入字符串的MD5值
	 * 
	 */
	public static String md5(String input) {
		if (input == null)
			return null;

		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			byte[] inputByteArray = input.getBytes(StandardCharsets.UTF_8);
			messageDigest.update(inputByteArray);
			byte[] resultByteArray = messageDigest.digest();
			return byteArrayToHex(resultByteArray);
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}

	/**
	 * 获取文件的MD5值
	 * 
	 * @param file
	 * @return
	 */
	public static String md5(File file) {
		if (!file.isFile()) {
			LOG.log(new Status(IStatus.ERROR, Activator.PLUGIN_ID,
					"文件" + file.getAbsolutePath() + "不存在或者不是文件"));
			return null;
		}

		try (FileInputStream in = new FileInputStream(file)) {
			return md5(in);
		} catch (IOException e) {
			LOG.log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, e.getMessage(), e));
		}

		return null;
	}

	public static String md5(InputStream in) {
		try {
			MessageDigest messagedigest = MessageDigest.getInstance("MD5");

			byte[] buffer = new byte[1024];
			int read;
			while ((read = in.read(buffer)) != -1) {
				messagedigest.update(buffer, 0, read);
			}

			return byteArrayToHex(messagedigest.digest());
		} catch (NoSuchAlgorithmException | IOException e) {
			LOG.log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, e.getMessage(), e));
		}

		return null;
	}

	private static String byteArrayToHex(byte[] byteArray) {
		char[] resultCharArray = new char[byteArray.length * 2];
		int index = 0;
		for (byte b : byteArray) {
			resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
			resultCharArray[index++] = hexDigits[b & 0xf];
		}
		return new String(resultCharArray);
	}

}
