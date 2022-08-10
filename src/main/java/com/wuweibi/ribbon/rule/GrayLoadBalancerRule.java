package com.wuweibi.ribbon.rule;

import com.netflix.client.ClientFactory;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.*;
import com.wuweibi.ribbon.predicate.GrayMetadataAwarePredicate;
import lombok.extern.slf4j.Slf4j;


/**
 * 灰度负载均衡规则
 *
 * @author marker
 */
@Slf4j
public class GrayLoadBalancerRule extends ZoneAvoidanceRule {


    /**
     * 组合模式
     * 当前面的不行 ，就降级过滤
     */
    private CompositePredicate compositePredicate;
    private GrayMetadataAwarePredicate grayMetadataAwarePredicate;


    public GrayLoadBalancerRule() {
           grayMetadataAwarePredicate = new GrayMetadataAwarePredicate(this, null);
        ZoneAvoidancePredicate zonePredicate = new ZoneAvoidancePredicate(this, null);
        compositePredicate = createCompositePredicate(super.getPredicate(), grayMetadataAwarePredicate, zonePredicate);
    }


    /**
     * 动态服务的时候，会调用这个方法
     * @param clientConfig
     */
    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {
        super.initWithNiwsConfig(clientConfig);
          grayMetadataAwarePredicate = new GrayMetadataAwarePredicate(this, clientConfig);
        ZoneAvoidancePredicate zonePredicate = new ZoneAvoidancePredicate(this, clientConfig);
        this.compositePredicate = createCompositePredicate(super.getPredicate(), grayMetadataAwarePredicate, zonePredicate);
        clientConfig.getClientName();
        super.setLoadBalancer(ClientFactory.getNamedLoadBalancer(clientConfig.getClientName()));

    }



    private CompositePredicate createCompositePredicate(AbstractServerPredicate p1, AbstractServerPredicate p2,
                                                        AbstractServerPredicate p3) {
        return CompositePredicate.withPredicates(p2)
                .addFallbackPredicate(p3)
                .addFallbackPredicate(AbstractServerPredicate.alwaysTrue())
                // 如果p1不能找到服务器0，那么降级到p2
                .setFallbackThresholdAsMinimalFilteredNumberOfServers(0)
                .build();
    }


    public AbstractServerPredicate getPredicate() {
        return compositePredicate;
    }



    @Override
    public Server choose(Object key) {
        return super.choose(key);
    }


    /**
     * 必须重写负载设置负载均衡
     * @param lb
     */
    @Override
    public void setLoadBalancer(ILoadBalancer lb) {
        super.setLoadBalancer(lb);
    }


    @Override
    public ILoadBalancer getLoadBalancer() {
        return super.getLoadBalancer();
    }
}
