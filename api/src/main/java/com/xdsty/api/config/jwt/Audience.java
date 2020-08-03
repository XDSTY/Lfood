package com.xdsty.api.config.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author 张富华
 * @date 2020/6/16 9:40
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "audience")
@PropertySource("classpath:application.yml")
public class Audience {

    private String clientId;

    private String secret;

    private String name;

    private String accessTokenExpiredMills;

    private String refreshTokenExpiredMills;

}