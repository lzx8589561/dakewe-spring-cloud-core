package com.dakewe.core.log.utils;

import com.dakewe.core.launch.props.ZingProperties;
import com.dakewe.core.launch.server.ServerInfo;
import com.dakewe.core.log.model.LogAbstract;
import com.dakewe.core.secure.utils.SecureUtil;
import com.dakewe.core.tool.utils.StringPool;
import com.dakewe.core.tool.utils.UrlUtil;
import com.dakewe.core.tool.utils.WebUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * INet 相关工具
 *
 * @author Zing
 */
public class LogAbstractUtil {

	/**
	 * 向log中添加补齐request的信息
	 *
	 * @param request     请求
	 * @param logAbstract 日志基础类
	 */
	public static void addRequestInfoToLog(HttpServletRequest request, LogAbstract logAbstract) {
		logAbstract.setRemoteIp(WebUtil.getIP(request));
		logAbstract.setUserAgent(request.getHeader(WebUtil.USER_AGENT_HEADER));
		logAbstract.setRequestUri(UrlUtil.getPath(request.getRequestURI()));
		logAbstract.setMethod(request.getMethod());
		logAbstract.setParams(WebUtil.getRequestParamString(request));
		logAbstract.setCreateBy(SecureUtil.getUserAccount(request));
	}

	/**
	 * 向log中添加补齐其他的信息
	 *
	 * @param logAbstract     日志基础类
	 * @param zingProperties 配置信息
	 * @param serverInfo      服务信息
	 */
	public static void addOtherInfoToLog(LogAbstract logAbstract, ZingProperties zingProperties, ServerInfo serverInfo) {
		logAbstract.setServiceId(zingProperties.getName());
		logAbstract.setServerHost(serverInfo.getHostName());
		logAbstract.setServerIp(serverInfo.getIpWithPort());
		logAbstract.setEnv(zingProperties.getEnv());
		logAbstract.setCreateTime(LocalDateTime.now());

		//这里判断一下params为null的情况，否则log服务在解析该字段的时候，可能会报出NPE
		if (logAbstract.getParams() == null) {
			logAbstract.setParams(StringPool.EMPTY);
		}
	}
}
