package com.dakewe.core.log.publisher;

import com.dakewe.core.log.constant.EventConstant;
import com.dakewe.core.log.event.ErrorLogEvent;
import com.dakewe.core.log.model.LogError;
import com.dakewe.core.log.utils.LogAbstractUtil;
import com.dakewe.core.tool.utils.Exceptions;
import com.dakewe.core.tool.utils.Func;
import com.dakewe.core.tool.utils.SpringUtil;
import com.dakewe.core.tool.utils.WebUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 异常信息事件发送
 *
 * @author Zing
 */
public class ErrorLogPublisher {

	public static void publishEvent(Throwable error, String requestUri) {
		HttpServletRequest request = WebUtil.getRequest();
		LogError logError = new LogError();
		logError.setRequestUri(requestUri);
		if (Func.isNotEmpty(error)) {
			logError.setStackTrace(Exceptions.getStackTraceAsString(error));
			logError.setExceptionName(error.getClass().getName());
			logError.setMessage(error.getMessage());
			StackTraceElement[] elements = error.getStackTrace();
			if (Func.isNotEmpty(elements)) {
				StackTraceElement element = elements[0];
				logError.setMethodName(element.getMethodName());
				logError.setMethodClass(element.getClassName());
				logError.setFileName(element.getFileName());
				logError.setLineNumber(element.getLineNumber());
			}
		}
		LogAbstractUtil.addRequestInfoToLog(request, logError);

		Map<String, Object> event = new HashMap<>(16);
		event.put(EventConstant.EVENT_LOG, logError);
		event.put(EventConstant.EVENT_REQUEST, request);
		SpringUtil.publishEvent(new ErrorLogEvent(event));
	}

}
