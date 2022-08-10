package com.wuweibi.ribbon.autoconfigure;


import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.IRule;
import com.wuweibi.ribbon.properties.ServiceGrayProperties;
import com.wuweibi.ribbon.rule.GrayLoadBalancerRule;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import javax.annotation.Resource;


/**
 * TokenManager 自动装配配置类
 *
 * @author marker
 */
/**
 * 灰度自动配置
 * 新规则 如果不含GVERSION头：走无版本标识的服务
 * 含GVERSION头：走有版本标识服务，且无法找到服务时，使用无版本标识服务
 * @author marker
 */
@ConditionalOnProperty(prefix = ServiceGrayProperties.PREFIX, value = "enabled", havingValue = "true")

@RibbonClients(defaultConfiguration = RibbonServiceGrayAutoConfiguration.class)
@EnableConfigurationProperties
@Configuration(proxyBeanMethods = false)
public class RibbonServiceGrayAutoConfiguration {


    @Resource
    private ServiceGrayProperties serviceGrayProperties;


    @Bean
    @Lazy
    public IRule metadataAwareRule(IClientConfig clientConfig) {
        GrayLoadBalancerRule rule = new GrayLoadBalancerRule();
        rule.initWithNiwsConfig(clientConfig);
        return rule;
    }








}
