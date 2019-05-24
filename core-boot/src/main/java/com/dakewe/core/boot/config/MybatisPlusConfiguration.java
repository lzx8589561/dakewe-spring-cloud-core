package com.dakewe.core.boot.config;

import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import com.dakewe.core.launch.constant.AppConstant;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * mybatisplus 配置
 *
 * @author Zing
 */
@Configuration
@MapperScan("com.dakewe.**.dao.**")
public class MybatisPlusConfiguration {

	@Bean
	@ConditionalOnMissingBean(PaginationInterceptor.class)
	public PaginationInterceptor paginationInterceptor() {
		return new PaginationInterceptor();
	}

	@Bean
	public LogicSqlInjector logicSqlInjector() {
		return new LogicSqlInjector();
	}

	/**
	 * SQL执行效率插件
	 *
	 * @return PerformanceInterceptor
	 */
	@Bean
	@Profile({AppConstant.DEV_CODE, AppConstant.TEST_CODE})
	public PerformanceInterceptor performanceInterceptor() {
		return new PerformanceInterceptor();
	}

}

