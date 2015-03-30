package uk.co.smash.utils;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AppUtils {

	private static final Logger LOG = Logger.getLogger(AppUtils.class.getName());

	public static JsonNode convertJsonStringToJsonObject(String jsonString) throws Exception {

		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonObject = null;
		try {
			jsonObject = mapper.readTree(jsonString);
		} catch (JsonProcessingException e) {
			LOG.error("Unable to parse json from string ", e);
			throw e;
		} catch (IOException e) {
			LOG.error("Unable to parse json from string ", e);
			throw e;
		}
		return jsonObject;
	}

	public static String decrypt(String textToDecrypt) throws Exception {

		String decryptedString = "";
		try {
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, AppUtils.getSecretKeyToEncryptOrDecrypt());
			byte[] decodedText = Base64.decodeBase64(textToDecrypt);
			byte[] decryptedTextInBytes = cipher.doFinal(decodedText);
			decryptedString = new String(decryptedTextInBytes);
		} catch (IllegalBlockSizeException e) {
			LOG.error("Error while decrypting the thext ", e);
			throw e;
		} catch (BadPaddingException e) {
			LOG.error("Error while decrypting the thext ", e);
			throw e;
		} catch (InvalidKeyException e) {
			LOG.error("Error while decrypting the thext ", e);
			throw e;
		} catch (NoSuchAlgorithmException e) {
			LOG.error("Error while decrypting the thext ", e);
			throw e;
		} catch (NoSuchPaddingException e) {
			LOG.error("Error while decrypting the thext ", e);
			throw e;
		}

		return decryptedString;
	}

	public static String encrypt(String textToEncrypt) throws Exception {

		String encryptedString = "";
		try {
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, AppUtils.getSecretKeyToEncryptOrDecrypt());
			byte[] encryptedTextInBytes = cipher.doFinal(textToEncrypt.getBytes());
			encryptedString = Base64.encodeBase64String(encryptedTextInBytes);
		} catch (NoSuchAlgorithmException e) {
			LOG.error("Error while encrypting the thext ", e);
			throw e;
		} catch (NoSuchPaddingException e) {
			LOG.error("Error while encrypting the thext ", e);
			throw e;
		} catch (InvalidKeyException e) {
			LOG.error("Error while encrypting the thext ", e);
			throw e;
		} catch (IllegalBlockSizeException e) {
			LOG.error("Error while encrypting the thext ", e);
			throw e;
		} catch (BadPaddingException e) {
			LOG.error("Error while encrypting the thext ", e);
			throw e;
		}
		return encryptedString;
	}

	private static Key getSecretKeyToEncryptOrDecrypt() {

		String key = "donotchange_keepitsafe";
		Key aesKey = new SecretKeySpec(Base64.decodeBase64(key), "AES");
		return aesKey;
	}

	public static void main(String[] args) {

		try {
			System.out.println(AppUtils.encrypt("smash123!"));
			System.out.println(AppUtils.decrypt("x8mqc4s0bpIvbZEGP6lsIQ=="));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
