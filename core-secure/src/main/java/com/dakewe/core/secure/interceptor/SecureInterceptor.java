package com.dakewe.core.secure.interceptor;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * jwt拦截器校验
 *
 * @author Zing
 */
@Slf4j
@AllArgsConstructor
public class SecureInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		return true;
//		if (null != SecureUtil.getUser()) {
//			return true;
//		} else {
//			log.warn("签名认证失败，请求接口：{}，请求IP：{}，请求参数：{}", request.getRequestURI(), WebUtil.getIP(request), JsonUtil.toJson(request.getParameterMap()));
//			R result = R.fail(ResultCode.UN_AUTHORIZED);
//			response.setCharacterEncoding(ZingConstant.UTF_8);
//			response.setHeader(ZingConstant.CONTENT_TYPE_NAME, MediaType.APPLICATION_JSON_UTF8_VALUE);
//			response.setStatus(HttpServletResponse.SC_OK);
//			try {
//				response.getWriter().write(Objects.requireNonNull(JsonUtil.toJson(result)));
//			} catch (IOException ex) {
//				log.error(ex.getMessage());
//			}
//			return false;
//		}
	}

}
