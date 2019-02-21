package com.bly.common.jwt.helper;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSONObject;
import com.bly.common.jwt.bean.AccessToken;
import com.bly.common.jwt.security.JWTConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

public class JwtUtils {
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
	
	/**
	 * 不要将隐私信息放入，base64可以对称解密，相当于明文
	 * @param username
	 * @param userId
	 * @param role
	 * @param audience
	 * @param issuer
	 * @param TTLMillis
	 * @param base64Security
	 * @return
	 */
	public static String createJWT(AccessToken accessToken) { 
		try {
			SignatureAlgorithm signatureAlgorithm  = SignatureAlgorithm.HS256;
			long nowMillis  = System.currentTimeMillis();
			Date now = new Date(nowMillis);
			// 生成签名秘钥 ，一个base64加密后的字符串
			byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(accessToken.getPrivateKey());
			SecretKeySpec siginKey  = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("userId", accessToken.getUserId());
			jsonObject.put("userName", accessToken.getUserName());
			jsonObject.put("roleId", accessToken.getRoleId());
			jsonObject.put("roleName", accessToken.getRoleName());
			jsonObject.put("attributes", accessToken.getAttributes());
			//-----------构建 JWT-----------------
			JwtBuilder builder = Jwts.builder()
			// Header
			.setHeaderParam("typ", "JWT")
			.claim("type", accessToken.getTokenType())
			// 创建时间
			.setIssuedAt(now)
			// 载体，用户的基本信息
			.setSubject(jsonObject.toString())
			// 发送给谁
			.setIssuer(accessToken.getIssuser())
			// 个人签名
			.setAudience(accessToken.getAudience())
			// 第三段秘钥签名
			.signWith(signatureAlgorithm, siginKey);
			// Token过期时间
			if (accessToken.getExpire() >= 0) {
				long expMillis  = nowMillis + accessToken.getExpire() ; 
				Date exp = new Date(expMillis);
				// 系统之前的时间都是不可被承认的
				builder.setExpiration(exp).setNotBefore(now);
			}
			
			// 生成JWT
			return builder.compact();
		} catch (Exception e) {
			logger.error("create jwt exception : " , e.getMessage());
			return null;
		}
		
	}
	
	/**
	 * jwt方式解析token获取Claims
	 * @param jsonWebToken
	 * @param base64Security
	 * @return
	 */
	public static Claims parseJWT(String jsonWebToken , String base64Security) {
		try {
			Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(base64Security))
			.parseClaimsJws(jsonWebToken).getBody();
			return claims;
		} catch (SignatureException ex) {
			logger.error("秘钥不正确，伪造的JWT串 ，error : " , ex.getMessage());
			return null;
		} catch (ExpiredJwtException  ex) {
			logger.error("JWT串已经过期，本次请求失效 ，error " , ex.getMessage());
			return null;
		} catch (Exception  ex) {
			logger.error("解析token异常 ，error " , ex.getMessage());
			return null;
		} 
	}
	
	public static void main(String[] args) {
		AccessToken accessToken  = new AccessToken();
		accessToken.setTokenType("bearer");
		accessToken.setUserId("1");
		accessToken.setUserName("wangyi");
		accessToken.setRoleId("2");
		accessToken.setRoleName("管理员");
		accessToken.setAudience("restapiuser");
		accessToken.setExpire(30*1000); //30秒过期
		accessToken.setIssuser("xxxx@163.com");
		accessToken.setPrivateKey(JWTConstant.SECURITY_KEY);
		Map<String,Object> attributes = new ConcurrentHashMap<String,Object>();
		attributes.put("resource", "https://baidu.com");
		attributes.put("auth", "xxxx");
		accessToken.setAttributes(attributes);
		String token = createJWT(accessToken);
		System.out.println(token);
		
		Claims claims = parseJWT(token, JWTConstant.SECURITY_KEY); 
		System.out.println(claims);
		System.out.println("JWT 类型 ：" + claims.get("type"));
		System.out.println(claims.getExpiration());
		System.out.println(claims.getSubject()); //主题用户信息
		JSONObject subject = JSONObject.parseObject(claims.getSubject());
		System.out.println("userName:" + subject.getString("userName") + "\nuserId:" + subject.getString("userId"));
		
	}
}
