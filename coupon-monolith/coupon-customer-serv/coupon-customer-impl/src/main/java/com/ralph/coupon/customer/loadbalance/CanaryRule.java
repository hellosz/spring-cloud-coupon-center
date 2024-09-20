package com.ralph.coupon.customer.loadbalance;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.*;
import org.springframework.cloud.loadbalancer.core.NoopServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.SelectedInstanceCallback;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @ClassName: CanaryRule
 * @description: 增加自定义的负载均衡规则（金丝雀）
 * @author: Neng.Tian
 * @create: 2024-09-20 15:22
 **/
@Slf4j
public class CanaryRule implements ReactorServiceInstanceLoadBalancer {

    private ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider;

    private String serviceId;

    final AtomicInteger position;

    static final String TRAFFIC_SIGN = "traffic-version";

    public CanaryRule(ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider, String serviceId) {
        this.serviceInstanceListSupplierProvider = serviceInstanceListSupplierProvider;
        this.serviceId = serviceId;
        this.position = new AtomicInteger(new Random().nextInt(1000));
    }

    @Override
    public Mono<Response<ServiceInstance>> choose(Request request) {
        ServiceInstanceListSupplier supplier = serviceInstanceListSupplierProvider.getIfAvailable(NoopServiceInstanceListSupplier::new);

        return supplier.get(request)
                .next()
                .map(serviceInstances -> processInstanceresponse(supplier, serviceInstances, request));
    }

    private Response<ServiceInstance> processInstanceresponse(ServiceInstanceListSupplier supplier, List<ServiceInstance> serviceInstances, Request request) {
        Response<ServiceInstance> serviceInstanceResponse = getInstanceResponse(serviceInstances, request);

        if (supplier instanceof SelectedInstanceCallback && serviceInstanceResponse.hasServer()) {
            ((SelectedInstanceCallback) supplier).selectedServiceInstance(serviceInstanceResponse.getServer());
        }

        return serviceInstanceResponse;
    }

    /**
     * 核心逻辑
     *
     * @param serviceInstances
     * @param request
     * @return
     */
    private Response<ServiceInstance> getInstanceResponse(List<ServiceInstance> serviceInstances, Request request) {
        if (CollectionUtils.isEmpty(serviceInstances)) {
            log.warn("No instance available: {}", serviceId);

            return new EmptyResponse();
        }

        // 获取流量请求头中的标识
        DefaultRequestContext context = (DefaultRequestContext)request.getContext();
        RequestData requestData = (RequestData)context.getClientRequest();
        HttpHeaders headers = requestData.getHeaders();
        String trafficValue = headers.getFirst(TRAFFIC_SIGN);

        // 标记流量，导流到金丝雀实例
        if (StringUtils.isNotBlank(trafficValue)) {
            List<ServiceInstance> instances = serviceInstances.stream()
                    .filter(serviceInstance -> {
                        String metaValue = serviceInstance.getMetadata().get(TRAFFIC_SIGN); // 提取元数据
                        return trafficValue.equalsIgnoreCase(metaValue);
                    })
                    .collect(Collectors.toList());

           return getRoundRobinInstance(instances);
        } else {
            // 非标记流量，过滤金丝雀实例
            List<ServiceInstance> instances = serviceInstances.stream()
                    .filter(serviceInstance -> !serviceInstance.getMetadata().containsKey(TRAFFIC_SIGN))
                    .collect(Collectors.toList());

            return getRoundRobinInstance(instances);

        }
    }

    // 使用轮询机制获取节点
    private Response<ServiceInstance> getRoundRobinInstance(List<ServiceInstance> instances) {
        if (instances.isEmpty()) {
            log.warn("No servers available for service:" + serviceId);

            return new EmptyResponse();
        }

        int pos = Math.abs(position.incrementAndGet());
        ServiceInstance serviceInstance = instances.get(pos % instances.size());
        return new DefaultResponse(serviceInstance);
    }
}
