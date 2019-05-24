package com.dakewe.core.log.event;


import org.springframework.context.ApplicationEvent;

import java.util.Map;

/**
 * 错误日志事件
 *
 * @author Zing
 */
public class ErrorLogEvent extends ApplicationEvent {

	public ErrorLogEvent(Map<String, Object> source) {
		super(source);
	}

}
