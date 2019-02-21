package com.bly.power.interfaces;


public interface IRSAEncryptService {

	/**
	 * 公钥加密
	 * 
	 * @param input
	 *            数据源
	 * @param publicKey
	 *            AK 公钥（BASE64编码）
	 * @return
	 */
	public byte[] encryptByPublicKey(byte[] data, String publicKey);

	/**
	 * 公钥解密
	 * 
	 * @param encryptedData
	 *            已加密的数据
	 * @param publicKey
	 *            AK 公钥
	 * @return
	 */
	public byte[] decryptByPublicKey(byte[] encryptedData, String publicKey);

	/**
	 * 私钥加密
	 * 
	 * @param input
	 *            数据源
	 * @param privateKey
	 *            SK 私钥
	 * @return
	 */
	public byte[] encryptByPrivateKey(byte[] data, String privateKey);

	/**
	 * 私钥解密
	 * 
	 * @param encryptedData
	 *            已加密的数据
	 * @param privateKey
	 *            SK 私钥
	 * @return
	 */
	public byte[] decryptByPriceKey(byte[] encryptedData, String privateKey);

	/**
	 * 用私钥对信息生成数字签名
	 * 
	 * @param privateKey
	 *            SK 用来生成签名
	 * @param publicKey
	 *            AK
	 * @param pk
	 *            productKey 产品唯一标识
	 * @param number
	 *            随机数
	 * @param date
	 *            日期 yyyy-MM-dd HH:mm:ss
	 * @param otherParam
	 *            其他参数
	 * @return
	 */
	public String sign(String privateKey, String publicKey, String pk,
			int number, String date, String... otherParam);

	/**
	 * 用公钥校验签名
	 * 
	 * @param sign
	 *            需要验证的签名
	 * @param publicKey
	 *            AK 用来校验签名
	 * @param pk
	 *            productKey 产品唯一标识
	 * @param number
	 *            随机数
	 * @param date
	 *            日期  yyyy-MM-dd HH:mm:ss
	 * @param otherParam
	 *            其他参数
	 * @return
	 */
	public boolean verify(String sign, String publicKey, String pk,
			int number, String date, String... otherParam);
}
