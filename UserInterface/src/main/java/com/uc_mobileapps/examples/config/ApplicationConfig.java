package com.uc_mobileapps.examples.config;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Configuration
public class ApplicationConfig {

    private final List<Locale> availableLocales;

    private ObjectFactory<HttpMessageConverters> messageConverters;

    public ApplicationConfig(@Value("classpath:messages_*.properties") final Resource[] localeResources,
                             ObjectFactory<HttpMessageConverters> messageConverters) {
        this.messageConverters = messageConverters;
        this.availableLocales = getAvailableLocalesFromResources(localeResources);
        availableLocales.add(Locale.getDefault());
    }

    /**
     * Simulates a service discovery.
     * @return service instance list all mapping to localhost:8081
     */
    @Bean
    @Primary
    ServiceInstanceListSupplier serviceInstanceListSupplier() {
        return new StaticInstanceListSupplier("tripclient", "customerclient");
    }

    /**
     * Locales determined from available messages_* resources.
     * @return list of locales
     */
    @Bean
    @Qualifier("availableLocales")
    public List<Locale> availableLocales() {
        return availableLocales;
    }


    /**
     * Get all available locales that have a messages resource package.
     * @param localeResources
     * @return list of locales
     */
    private List<Locale> getAvailableLocalesFromResources(Resource[] localeResources) {
        // idea from https://stackoverflow.com/a/57111007/1615430
        return Arrays.stream(localeResources).map(resource -> {
            final String code = resource.getFilename().split("messages_")[1].split(".properties")[0];
            return Locale.forLanguageTag(code);
        }).collect(Collectors.toList());
    }

}
