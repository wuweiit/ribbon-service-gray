package com.wuweibi.ribbon.predicate;

import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.google.common.base.Optional;
import com.netflix.client.config.IClientConfig;
import com.netflix.config.DynamicDoubleProperty;
import com.netflix.loadbalancer.*;
import com.wuweibi.ribbon.constant.CommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 基于 Metadata version 的服务筛选
 * 仅支持Nacos
 *
 * @author marker
 */
@Slf4j
public class GrayMetadataAwarePredicate extends AbstractServerPredicate {


    public GrayMetadataAwarePredicate(IRule rule, IClientConfig clientConfig) {
        super(rule, clientConfig);
    }

    @Override
    public boolean apply(PredicateKey predicateKey) {
        Server server = predicateKey.getServer();

        // 仅仅允许元数据中有"version"的服务
        if (server instanceof NacosServer) {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

            String metaVersion = ((NacosServer) server).getMetadata().get(CommonConstants.VERSION);
            if (attributes == null) {
                if (StringUtils.isEmpty(metaVersion)) {
                    return true;
                }
                return false;
            }
            // 从http头获取version
            String requestVersion = attributes.getRequest().getHeader(CommonConstants.GVERSION);
            // 正常请求不带请求版本，name元数据的版本属性必须是空
            if (StringUtils.isEmpty(requestVersion)) {
                if(StringUtils.isEmpty(metaVersion))
                    return true;
                else return false;
            }
            // 携带请求版本的
            return requestVersion.equals(metaVersion);
        }
        return true;
    }

    static Map<String, ZoneSnapshot> createSnapshot(LoadBalancerStats lbStats) {
        Map<String, ZoneSnapshot> map = new HashMap<String, ZoneSnapshot>();
        for (String zone : lbStats.getAvailableZones()) {
            ZoneSnapshot snapshot = lbStats.getZoneSnapshot(zone);
            map.put(zone, snapshot);
        }
        return map;
    }


    // 使用组合模式不会调用这里
    @Override
    public Optional<Server> chooseRoundRobinAfterFiltering(List<Server> servers, Object loadBalancerKey) {
        Optional<Server> optional = super.chooseRoundRobinAfterFiltering(servers, loadBalancerKey);
        if (!optional.isPresent()) {
            log.warn("[Gray] server total[{}] is filter[0], loadBalancerKey={}", servers.size(), loadBalancerKey);
        }
        return optional;
    }


    @Override
    public void setLoadBalancerStats(LoadBalancerStats stats) {
        super.setLoadBalancerStats(stats);
    }
}
