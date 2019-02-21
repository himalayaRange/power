package com.bly.power.interfaces;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;


public class RSAEncryptServiceImpl implements IRSAEncryptService {


	@Override
	public byte[] encryptByPublicKey(byte[] data, String publicKey) {
		byte[] keyBytes = Base64.decodeBase64(publicKey);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		try {
			Cipher cipher;
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PublicKey publicK = keyFactory.generatePublic(keySpec);
			// 对数据进行加密
			cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.ENCRYPT_MODE, publicK);
			return cipher.doFinal(data);
		} catch (Exception e) {
		}
		return null;
	}

	@Override
	public byte[] decryptByPublicKey(byte[] encryptedData, String publicKey) {
		byte[] keyBytes = Base64.decodeBase64(publicKey);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PublicKey publicK = keyFactory.generatePublic(keySpec);
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.DECRYPT_MODE, publicK);
			return cipher.doFinal(encryptedData);
		} catch (Exception e) {
		}
		return null;
	}

	@Override
	public byte[] encryptByPrivateKey(byte[] data, String privateKey) {
		byte[] keyBytes = Base64.decodeBase64(privateKey);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PrivateKey privateK = keyFactory.generatePrivate(keySpec);
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.ENCRYPT_MODE, privateK);
			return cipher.doFinal(data);
		} catch (Exception e) {
		}
		return null;
	}

	@Override
	public byte[] decryptByPriceKey(byte[] encryptedData, String privateKey) {
		byte[] keyBytes = Base64.decodeBase64(privateKey);
		try {
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PrivateKey privateK = keyFactory.generatePrivate(keySpec);
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.DECRYPT_MODE, privateK);
			return cipher.doFinal(encryptedData);
		} catch (Exception e) {
		}
		return null;
	}

	@Override
	public String sign(String privateKey, String publicKey, String pk, int number, String date, String... otherParam) {
		// 签名原文
		String signSrc = orgSignSrc(publicKey, pk, number, date, otherParam);
		byte[] privateByte = Base64.decodeBase64(privateKey);
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateByte);
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
			Signature signature = Signature.getInstance("MD5withRSA");
			signature.initSign(privateK);
			signature.update(signSrc.getBytes());
			return Base64.encodeBase64String(signature.sign());
		} catch (Exception e) {
		}
		return null;
	}

	@Override
	public boolean verify(String sign, String publicKey, String pk, int number, String date, String... otherParam) {
		// 签名原文
		String signSrc = orgSignSrc(publicKey, pk, number, date, otherParam);
		byte[] keyBytes = Base64.decodeBase64(publicKey);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PublicKey publicK = keyFactory.generatePublic(keySpec);
			Signature signature = Signature.getInstance("MD5withRSA");
			signature.initVerify(publicK);
			signature.update(signSrc.getBytes());
			return signature.verify(Base64.decodeBase64(sign));
		} catch (Exception e) {
		}
		return false;
	}

	private String orgSignSrc(String publicKey, String pk, int number, String date, String[] otherParam) {
		StringBuffer src = new StringBuffer();
		src.append("AK=" + publicKey + "&");
		src.append("PK=" + pk + "&");
		src.append("Number=" + number + "&");
		src.append("Date=" + date + "&");
		src.append("Data=");
		for (String param : otherParam) {
			src.append(param);
		}
		return src.toString();
	}

}
