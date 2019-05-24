package com.dakewe.core.secure.config;


import lombok.AllArgsConstructor;
import com.dakewe.core.secure.aspect.AuthAspect;
import com.dakewe.core.secure.interceptor.ClientInterceptor;
import com.dakewe.core.secure.interceptor.SecureInterceptor;
import com.dakewe.core.secure.props.ClientProperties;
import com.dakewe.core.secure.props.SecureProperties;
import com.dakewe.core.secure.provider.ClientDetailsServiceImpl;
import com.dakewe.core.secure.provider.IClientDetailsService;
import com.dakewe.core.secure.registry.SecureRegistry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 安全配置类
 *
 * @author Zing
 */
@Order
@Configuration
@AllArgsConstructor
@EnableConfigurationProperties({SecureProperties.class, ClientProperties.class})
public class SecureConfiguration implements WebMvcConfigurer {

	private final SecureRegistry secureRegistry;

	private final SecureProperties secureProperties;

	private final ClientProperties clientProperties;

	private final JdbcTemplate jdbcTemplate;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		clientProperties.getClient().forEach(cs -> registry.addInterceptor(new ClientInterceptor(cs.getClientId())).addPathPatterns(cs.getPathPatterns()));

		if (secureRegistry.isEnable()) {
			registry.addInterceptor(new SecureInterceptor())
				.excludePathPatterns(secureRegistry.getExcludePatterns())
				.excludePathPatterns(secureRegistry.getDefaultExcludePatterns())
				.excludePathPatterns(secureProperties.getExcludePatterns());
		}
	}

	@Bean
	public AuthAspect authAspect() {
		return new AuthAspect();
	}

	@Bean
	@ConditionalOnMissingBean(IClientDetailsService.class)
	public IClientDetailsService clientDetailsService() {
		return new ClientDetailsServiceImpl(jdbcTemplate);
	}

}
