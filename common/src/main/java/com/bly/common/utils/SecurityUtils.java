package com.bly.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * 安全工具
 * @date 2017年12月23日
 *
 * @author WangYi
 */
public class SecurityUtils {
  /**
   * MD5+盐值生成
   * @param password
   * @return
   */
  public static String encryptMD5(String password) {
        try {
            // 得到一个信息摘要器
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(password.getBytes());
            StringBuffer buffer = new StringBuffer();
            // 把每一个byte 做一个与运算 0xff;
            for (byte b : result) {
                // 与运算
                int number = b & 0xff;// 加盐
                String str = Integer.toHexString(number);
                if (str.length() == 1) {
                    buffer.append("0");
                }
                buffer.append(str);
            }
            // 标准的md5加密后的结果
            return buffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }
  
  public static void main(String[] args) {
	  String encryptMD5 = encryptMD5("123456");
	  System.out.println(encryptMD5);
  }
}
