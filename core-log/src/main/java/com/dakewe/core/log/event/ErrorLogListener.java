package com.dakewe.core.log.event;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.dakewe.core.launch.props.ZingProperties;
import com.dakewe.core.launch.server.ServerInfo;
import com.dakewe.core.log.constant.EventConstant;
import com.dakewe.core.log.feign.ILogClient;
import com.dakewe.core.log.model.LogError;
import com.dakewe.core.log.utils.LogAbstractUtil;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

import java.util.Map;

/**
 * 异步监听错误日志事件
 *
 * @author Zing
 */
@Slf4j
@AllArgsConstructor
public class ErrorLogListener {

	private final ILogClient logService;
	private final ServerInfo serverInfo;
	private final ZingProperties zingProperties;

	@Async
	@Order
	@EventListener(ErrorLogEvent.class)
	public void saveErrorLog(ErrorLogEvent event) {
		Map<String, Object> source = (Map<String, Object>) event.getSource();
		LogError logError = (LogError) source.get(EventConstant.EVENT_LOG);
		LogAbstractUtil.addOtherInfoToLog(logError, zingProperties, serverInfo);
		logService.saveErrorLog(logError);
	}

}
