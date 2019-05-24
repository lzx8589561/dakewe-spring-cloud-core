package com.dakewe.core.secure.utils;

import com.dakewe.core.secure.ZingUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.SneakyThrows;
import com.dakewe.core.secure.constant.TokenConstant;
import com.dakewe.core.secure.constant.SecureConstant;
import com.dakewe.core.secure.exception.SecureException;
import com.dakewe.core.secure.provider.IClientDetails;
import com.dakewe.core.secure.provider.IClientDetailsService;
import com.dakewe.core.tool.utils.*;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.*;

/**
 * Secure工具类
 *
 * @author Zing
 */
public class SecureUtil {
	private static final String USER_REQUEST_ATTR = "_USER_REQUEST_ATTR_";

	private final static String HEADER = TokenConstant.HEADER;
	private final static String BEARER = TokenConstant.BEARER;
	private final static String ACCOUNT = TokenConstant.ACCOUNT;
	private final static String USER_ID = TokenConstant.USER_ID;
	private final static String ROLE_ID = TokenConstant.ROLE_ID;
	private final static String USER_NAME = TokenConstant.USER_NAME;
	private final static String ROLE_NAME = TokenConstant.ROLE_NAME;
	private final static String TENANT_CODE = TokenConstant.TENANT_CODE;
	private final static String CLIENT_ID = TokenConstant.CLIENT_ID;
	private final static Integer AUTH_LENGTH = TokenConstant.AUTH_LENGTH;
	private static String BASE64_SECURITY = Base64.getEncoder().encodeToString(TokenConstant.SIGN_KEY.getBytes(Charsets.UTF_8));

	private static IClientDetailsService clientDetailsService;

	static {
		clientDetailsService = SpringUtil.getBean(IClientDetailsService.class);
	}

	/**
	 * 获取用户信息
	 *
	 * @return ZingUser
	 */
	public static ZingUser getUser() {
		HttpServletRequest request = WebUtil.getRequest();
		if (request == null) {
			return null;
		}
		// 优先从 request 中获取
		Object zingUser = request.getAttribute(USER_REQUEST_ATTR);
		if (zingUser == null) {
			zingUser = getUser(request);
			if (zingUser != null) {
				// 设置到 request 中
				request.setAttribute(USER_REQUEST_ATTR, zingUser);
			}
		}
		return (ZingUser) zingUser;
	}

	/**
	 * 获取用户信息
	 *
	 * @param request request
	 * @return ZingUser
	 */
	public static ZingUser getUser(HttpServletRequest request) {
		Claims claims = getClaims(request);
		if (claims == null) {
			return null;
		}
		String clientId = Func.toStr(claims.get(SecureUtil.CLIENT_ID));
		Integer userId = Func.toInt(claims.get(SecureUtil.USER_ID));
		String tenantCode = Func.toStr(claims.get(SecureUtil.TENANT_CODE));
		String roleId = Func.toStr(claims.get(SecureUtil.ROLE_ID));
		String account = Func.toStr(claims.get(SecureUtil.ACCOUNT));
		String roleName = Func.toStr(claims.get(SecureUtil.ROLE_NAME));
		String userName = Func.toStr(claims.get(SecureUtil.USER_NAME));
		ZingUser zingUser = new ZingUser();
		zingUser.setClientId(clientId);
		zingUser.setUserId(userId);
		zingUser.setTenantCode(tenantCode);
		zingUser.setAccount(account);
		zingUser.setRoleId(roleId);
		zingUser.setRoleName(roleName);
		zingUser.setUserName(userName);
		return zingUser;
	}


	/**
	 * 获取用户id
	 *
	 * @return userId
	 */
	public static Integer getUserId() {
		ZingUser user = getUser();
		return (null == user) ? -1 : user.getUserId();
	}

	/**
	 * 获取用户id
	 *
	 * @param request request
	 * @return userId
	 */
	public static Integer getUserId(HttpServletRequest request) {
		ZingUser user = getUser(request);
		return (null == user) ? -1 : user.getUserId();
	}

	/**
	 * 获取用户账号
	 *
	 * @return userAccount
	 */
	public static String getUserAccount() {
		ZingUser user = getUser();
		return (null == user) ? StringPool.EMPTY : user.getAccount();
	}

	/**
	 * 获取用户账号
	 *
	 * @param request request
	 * @return userAccount
	 */
	public static String getUserAccount(HttpServletRequest request) {
		ZingUser user = getUser(request);
		return (null == user) ? StringPool.EMPTY : user.getAccount();
	}

	/**
	 * 获取用户名
	 *
	 * @return userName
	 */
	public static String getUserName() {
		ZingUser user = getUser();
		return (null == user) ? StringPool.EMPTY : user.getUserName();
	}

	/**
	 * 获取用户名
	 *
	 * @param request request
	 * @return userName
	 */
	public static String getUserName(HttpServletRequest request) {
		ZingUser user = getUser(request);
		return (null == user) ? StringPool.EMPTY : user.getUserName();
	}

	/**
	 * 获取用户角色
	 *
	 * @return userName
	 */
	public static String getUserRole() {
		ZingUser user = getUser();
		return (null == user) ? StringPool.EMPTY : user.getRoleName();
	}

	/**
	 * 获取用角色
	 *
	 * @param request request
	 * @return userName
	 */
	public static String getUserRole(HttpServletRequest request) {
		ZingUser user = getUser(request);
		return (null == user) ? StringPool.EMPTY : user.getRoleName();
	}

	/**
	 * 获取租户编号
	 *
	 * @return tenantCode
	 */
	public static String getTenantCode() {
		ZingUser user = getUser();
		return (null == user) ? StringPool.EMPTY : user.getTenantCode();
	}

	/**
	 * 获取租户编号
	 *
	 * @param request request
	 * @return tenantCode
	 */
	public static String getTenantCode(HttpServletRequest request) {
		ZingUser user = getUser(request);
		return (null == user) ? StringPool.EMPTY : user.getTenantCode();
	}

	/**
	 * 获取客户端id
	 *
	 * @return tenantCode
	 */
	public static String getClientId() {
		ZingUser user = getUser();
		return (null == user) ? StringPool.EMPTY : user.getClientId();
	}

	/**
	 * 获取客户端id
	 *
	 * @param request request
	 * @return tenantCode
	 */
	public static String getClientId(HttpServletRequest request) {
		ZingUser user = getUser(request);
		return (null == user) ? StringPool.EMPTY : user.getClientId();
	}

	/**
	 * 获取Claims
	 *
	 * @param request request
	 * @return Claims
	 */
	public static Claims getClaims(HttpServletRequest request) {
		String auth = request.getHeader(SecureUtil.HEADER);
		if ((auth != null) && (auth.length() > AUTH_LENGTH)) {
			String headStr = auth.substring(0, 6).toLowerCase();
			if (headStr.compareTo(SecureUtil.BEARER) == 0) {
				auth = auth.substring(7);
				return SecureUtil.parseJWT(auth);
			}
		}
		return null;
	}

	/**
	 * 获取请求头
	 *
	 * @return header
	 */
	public static String getHeader() {
		return getHeader(Objects.requireNonNull(WebUtil.getRequest()));
	}

	/**
	 * 获取请求头
	 *
	 * @param request request
	 * @return header
	 */
	public static String getHeader(HttpServletRequest request) {
		return request.getHeader(HEADER);
	}

	/**
	 * 解析jsonWebToken
	 *
	 * @param jsonWebToken jsonWebToken
	 * @return Claims
	 */
	public static Claims parseJWT(String jsonWebToken) {
		try {
			return Jwts.parser()
				.setSigningKey(Base64.getDecoder().decode(BASE64_SECURITY))
				.parseClaimsJws(jsonWebToken).getBody();
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * 创建令牌
	 *
	 * @param user     user
	 * @param audience audience
	 * @param issuer   issuer
	 * @param isExpire isExpire
	 * @return jwt
	 */
	public static String createJWT(Map<String, String> user, String audience, String issuer, boolean isExpire) {

		String[] tokens = extractAndDecodeHeader();
		assert tokens.length == 2;
		String clientId = tokens[0];
		String clientSecret = tokens[1];

		// 校验客户端信息
		if (!validateClient(clientId, clientSecret)) {
			throw new SecureException("客户端认证失败!");
		}

		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);

		//生成签名密钥
		byte[] apiKeySecretBytes = Base64.getDecoder().decode(BASE64_SECURITY);
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

		//添加构成JWT的类
		JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JsonWebToken")
			.setIssuer(issuer)
			.setAudience(audience)
			.signWith(signatureAlgorithm, signingKey);

		//设置JWT参数
		user.forEach(builder::claim);

		//设置应用id
		builder.claim(CLIENT_ID, clientId);

		//添加Token过期时间
		if (isExpire) {
			long expMillis = nowMillis + getExpire();
			Date exp = new Date(expMillis);
			builder.setExpiration(exp).setNotBefore(now);
		}

		//生成JWT
		return builder.compact();
	}

	/**
	 * 获取过期时间(次日凌晨3点)
	 *
	 * @return expire
	 */
	public static long getExpire() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, 1);
		cal.set(Calendar.HOUR_OF_DAY, 3);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTimeInMillis() - System.currentTimeMillis();
	}

	/**
	 * 获取过期时间的秒数(次日凌晨3点)
	 *
	 * @return expire
	 */
	public static int getExpireSeconds() {
		return (int) (getExpire() / 1000);
	}

	/**
	 * 客户端信息解码
	 */
	@SneakyThrows
	public static String[] extractAndDecodeHeader() {
		// 获取请求头客户端信息
		String header = Objects.requireNonNull(WebUtil.getRequest()).getHeader(SecureConstant.BASIC_HEADER_KEY);
		if (header == null || !header.startsWith(SecureConstant.BASIC_HEADER_PREFIX)) {
			throw new SecureException("No client information in request header");
		}
		byte[] base64Token = header.substring(6).getBytes(Charsets.UTF_8_NAME);

		byte[] decoded;
		try {
			decoded = Base64.getDecoder().decode(base64Token);
		} catch (IllegalArgumentException var7) {
			throw new RuntimeException("Failed to decode basic authentication token");
		}

		String token = new String(decoded, Charsets.UTF_8_NAME);
		int index = token.indexOf(StringPool.COLON);
		if (index == -1) {
			throw new RuntimeException("Invalid basic authentication token");
		} else {
			return new String[]{token.substring(0, index), token.substring(index + 1)};
		}
	}

	/**
	 * 获取请求头中的客户端id
	 */
	public static String getClientIdFromHeader() {
		String[] tokens = extractAndDecodeHeader();
		assert tokens.length == 2;
		return tokens[0];
	}

	/**
	 * 校验Client
	 *
	 * @param clientId     客户端id
	 * @param clientSecret 客户端密钥
	 * @return boolean
	 */
	private static boolean validateClient(String clientId, String clientSecret) {
		IClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
		if (clientDetails != null) {
			return StringUtil.equals(clientId, clientDetails.getClientId()) && StringUtil.equals(clientSecret, clientDetails.getClientSecret());
		}
		return false;
	}

}
