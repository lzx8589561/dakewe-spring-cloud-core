package com.dakewe.core.log.publisher;

import com.dakewe.core.log.constant.EventConstant;
import com.dakewe.core.log.event.UsualLogEvent;
import com.dakewe.core.log.model.LogUsual;
import com.dakewe.core.log.utils.LogAbstractUtil;
import com.dakewe.core.tool.utils.SpringUtil;
import com.dakewe.core.tool.utils.WebUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 日志信息事件发送
 *
 * @author Zing
 */
public class UsualLogPublisher {

	public static void publishEvent(String level, String id, String data) {
		HttpServletRequest request = WebUtil.getRequest();
		LogUsual logUsual = new LogUsual();
		logUsual.setLogLevel(level);
		logUsual.setLogId(id);
		logUsual.setLogData(data);

		LogAbstractUtil.addRequestInfoToLog(request, logUsual);
		Map<String, Object> event = new HashMap<>(16);
		event.put(EventConstant.EVENT_LOG, logUsual);
		event.put(EventConstant.EVENT_REQUEST, request);
		SpringUtil.publishEvent(new UsualLogEvent(event));
	}

}
