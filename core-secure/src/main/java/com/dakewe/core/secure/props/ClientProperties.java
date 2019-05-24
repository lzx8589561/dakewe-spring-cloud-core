package com.dakewe.core.secure.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * 客户端校验配置
 *
 * @author Zing
 */
@Data
@ConfigurationProperties("dakewe.secure")
public class ClientProperties {

	private final List<ClientSecure> client = new ArrayList<>();

}
