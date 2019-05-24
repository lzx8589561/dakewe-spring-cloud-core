package com.dakewe.core.log.config;

import lombok.AllArgsConstructor;
import com.dakewe.core.launch.props.ZingProperties;
import com.dakewe.core.launch.server.ServerInfo;
import com.dakewe.core.log.aspect.ApiLogAspect;
import com.dakewe.core.log.event.ApiLogListener;
import com.dakewe.core.log.event.ErrorLogListener;
import com.dakewe.core.log.event.UsualLogListener;
import com.dakewe.core.log.feign.ILogClient;
import com.dakewe.core.log.logger.ZingLogger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 日志工具自动配置
 *
 * @author Zing
 */
@Configuration
@AllArgsConstructor
@ConditionalOnWebApplication
public class ZingLogToolAutoConfiguration {

	private final ILogClient logService;
	private final ServerInfo serverInfo;
	private final ZingProperties zingProperties;

	@Bean
	public ApiLogAspect apiLogAspect() {
		return new ApiLogAspect();
	}

	@Bean
	public ZingLogger zingLogger() {
		return new ZingLogger();
	}

	@Bean
	public ApiLogListener apiLogListener() {
		return new ApiLogListener(logService, serverInfo, zingProperties);
	}

	@Bean
	public ErrorLogListener errorEventListener() {
		return new ErrorLogListener(logService, serverInfo, zingProperties);
	}

	@Bean
	public UsualLogListener usualEventListener() {
		return new UsualLogListener(logService, serverInfo, zingProperties);
	}

}
