package com.dakewe.core.log.event;

import org.springframework.context.ApplicationEvent;

import java.util.Map;

/**
 * 系统日志事件
 *
 * @author Zing
 */
public class UsualLogEvent extends ApplicationEvent {

	public UsualLogEvent(Map<String, Object> source) {
		super(source);
	}

}
