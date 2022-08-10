# RibbonServiceGray 基于Ribbon的灰度负载均衡策略

开发者：marker

RibbonServiceGray 是一款 基于Ribbon的灰度负载均衡策略。Ribbon本省提供的负载均衡策略可以满足我们最基本的使用。



以下是Ribbon提供的负载均衡算法清单：

| 负载均衡算法 | 实现类        | 说明                                                                                         |
|--------|------------|--------------------------------------------------------------------------------------------|
| 随机策略   | RandomRule |                                                                                            |
| 轮询策略 |   RoundRobinRule         |                                                                                            |
| 重试策略 |   RetryRule  |                                                                                            |
| 最低并发策略 |   BestAvailableRule  | 性能仅次于最低并发策略。                                                                               |
| 可用过滤策略 |   AvailabilityFilteringRule  | 过滤掉那些因为一直连接失败的被标记为circuit tripped的后端server，并过滤掉那些高并发的的后端server（active connections 超过配置的阈值） |
| 响应时间加权策略 |   WeightedResponseTimeRule  | 每隔30秒计算一次服务器响应时间，以响应时间作为权重，响应时间越短的服务器被选中的概率越大。                                             |
| 区域权衡策略 |   ZoneAvoidanceRule  | 默认 使用的RoundRobinRule                                                                       |
  
Ribbon在服务内部，调用其他服务负载均衡原理图如下所示：

![image](D:\WOR\ribbon-service-gray\image\48ef6c3443f888b4eb4a8a3c4b967c18.png)


基于Ribbon的服务灰度调用原理图：

待设计

### RibbonServiceGray更新历史

[更新历史文档](#history.md)

### RibbonServiceGray特性

 
### 快速开始
  
在你的微服务项目中集成maven依赖，如果你是非springCloud微服务架构，那么该组件不适合你的项目。


在项目中pom.xml加入maven依赖：
```
<!-- 开发者：marker Ribbon灰度支持 -->
<dependency>
    <groupId>com.wuweibi</groupId>
    <artifactId>ribbon-service-gray</artifactId>
    <version>0.0.1</version>
</dependency>
```  

 

### Springboot配置内容

```
spring:
  ribbon:
    enabled: true 
```
