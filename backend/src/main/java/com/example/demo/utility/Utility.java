package com.example.demo.utility;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

@Component
public class Utility {

	private static final byte[] sharedVector = { 0x01, 0x02, 0x03, 0x05, 0x07, 0x0B, 0x0D, 0x11 };
	private static final String key = "geniusinfotech"; // Ensure that the key length is correct for DESede

	public String encryptText(String rawText) {
		if (rawText == null || rawText.isEmpty() || "null".equals(rawText)) {
			return rawText;
		}

		try {
			byte[] keyArray = getKeyBytes(key);
			Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(keyArray, "DESede"), new IvParameterSpec(sharedVector));
			byte[] encrypted = cipher.doFinal(rawText.getBytes("UTF-8"));
			return Base64.encodeBase64String(encrypted);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public String decryptText(String encText) {
		if (encText == null || encText.isEmpty() || "null".equals(encText)) {
			return encText;
		}

		try {
			byte[] keyArray = getKeyBytes(key);
			Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(keyArray, "DESede"), new IvParameterSpec(sharedVector));
			byte[] decrypted = cipher.doFinal(Base64.decodeBase64(encText));
			return new String(decrypted, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	private byte[] getKeyBytes(String key) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest m = MessageDigest.getInstance("MD5");
		byte[] keyBytes = m.digest(key.getBytes("UTF-8"));
		byte[] keyArray = new byte[24];
		System.arraycopy(keyBytes, 0, keyArray, 0, keyBytes.length);
		System.arraycopy(keyBytes, 0, keyArray, keyBytes.length, keyArray.length - keyBytes.length);
		return keyArray;
	}

	public Date getDateWithTime(Date date, String time) {
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		try {
			return sdf.parse(df.format(date) + " " + time);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public long getInitialDelay(Date date, String time, long repeat) {
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		try {
			Date sdate = sdf.parse(df.format(date) + " " + time);
			long startTime = sdate.getTime();
			long currentTime = System.currentTimeMillis();
			long initialDelay = currentTime - startTime;

			if (initialDelay < 0) {
				initialDelay = Math.abs(initialDelay) / 1000;
			} else {
				if ((initialDelay / 1000) < 60) {
					initialDelay = (initialDelay / 1000);
				} else {
					long repeatInMS = (repeat * 1000);
					int repeatCount = (int) (initialDelay / repeatInMS);
					long newTime = ((repeatInMS * repeatCount) + startTime) + repeatInMS;
					initialDelay = Math.abs(newTime - currentTime) / 1000;
				}
			}
			return initialDelay;
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public static void main(String[] args) {
		Utility u = new Utility();

		// Test encryption
		String encryptedText = u.encryptText("2024-12-13");
		System.out.println("Encrypted: " + encryptedText);

		// Test decryption
		String decryptedText = u.decryptText(encryptedText);
		System.out.println("Decrypted: " + decryptedText);

		// Additional test case
		String decryptedTest = u.decryptText("WQjbKa2Pa4EyJ0RtGW4nIw==");
		System.out.println("Decrypted Test: " + decryptedTest);
	}
}
