package com.dakewe.core.launch.config;

import lombok.AllArgsConstructor;
import com.dakewe.core.launch.props.ZingProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * 配置类
 *
 * @author Zing
 */
@Configuration
@AllArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
@EnableConfigurationProperties({
	ZingProperties.class
})
public class LaunchConfiguration {

}
