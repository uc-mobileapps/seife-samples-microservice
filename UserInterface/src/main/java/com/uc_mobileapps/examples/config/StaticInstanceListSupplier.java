package com.uc_mobileapps.examples.config;

import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service discovery surrogate, resolves all service names to localhost:8081.
 */
public class StaticInstanceListSupplier implements ServiceInstanceListSupplier {

    private final List<String> serviceIdentifiers;

    StaticInstanceListSupplier(String... serviceIdentifiers) {
        this.serviceIdentifiers = Collections.unmodifiableList(Arrays.asList(serviceIdentifiers));
    }

    @Override
    public String getServiceId() {
        return "";
    }

    @Override
    public Flux<List<ServiceInstance>> get() {
        return Flux.just(serviceIdentifiers.stream().map(
                serviceIdentifier -> new DefaultServiceInstance(
                        serviceIdentifier + "1", serviceIdentifier, "localhost", 8081, false))
                .collect(Collectors.toList()));
        //        return Flux.just(Arrays.asList(
//               new DefaultServiceInstance(serviceIdentifier + "1", serviceIdentifier, "localhost", 8081, false)
//                ));
    }
}
