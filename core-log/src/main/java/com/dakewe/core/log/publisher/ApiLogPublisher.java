package com.dakewe.core.log.publisher;

import com.dakewe.core.log.annotation.ApiLog;
import com.dakewe.core.log.constant.EventConstant;
import com.dakewe.core.log.event.ApiLogEvent;
import com.dakewe.core.log.model.LogApi;
import com.dakewe.core.log.utils.LogAbstractUtil;
import com.dakewe.core.tool.constant.ZingConstant;
import com.dakewe.core.tool.utils.SpringUtil;
import com.dakewe.core.tool.utils.WebUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * API日志信息事件发送
 *
 * @author Zing
 */
public class ApiLogPublisher {

	public static void publishEvent(String methodName, String methodClass, ApiLog apiLog, long time) {
		HttpServletRequest request = WebUtil.getRequest();
		LogApi logApi = new LogApi();
		logApi.setType(ZingConstant.LOG_NORMAL_TYPE);
		logApi.setTitle(apiLog.value());
		logApi.setTime(String.valueOf(time));
		logApi.setMethodClass(methodClass);
		logApi.setMethodName(methodName);

		LogAbstractUtil.addRequestInfoToLog(request, logApi);
		Map<String, Object> event = new HashMap<>(16);
		event.put(EventConstant.EVENT_LOG, logApi);
		SpringUtil.publishEvent(new ApiLogEvent(event));
	}

}
