package com.wuweibi.ribbon.properties;

import lombok.Data;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.wuweibi.ribbon.properties.ServiceGrayProperties.PREFIX;

/**
 * 配置更新后自动刷新到配置对象
 * <p>
 * @author marker
 * Created by marker on 2021/07/12.
 */
@Data
@RefreshScope
@Configuration
@ConfigurationProperties(prefix = PREFIX)
public class ServiceGrayProperties {

    /**
     * 配置前缀
     */
    public static final String PREFIX = "spring.ribbon";

    /**
     *
     */
    private boolean enable = false;




    /**
     * 构造
     */
    public ServiceGrayProperties() {

    }

}
