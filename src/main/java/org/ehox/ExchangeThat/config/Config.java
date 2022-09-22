package org.ehox.ExchangeThat.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class Config {
    private static final int CONN_TIMEOUT = 1000;

    @Bean
    public RestTemplate restTemplate() {
        var s = new SimpleClientHttpRequestFactory();
        s.setConnectTimeout(CONN_TIMEOUT);
        var restTemplate = new RestTemplate(s);
        restTemplate.setInterceptors(List.of(new CustomInterceptor()));
        return restTemplate;
    }

    @Bean
    public Caffeine caffeineConfig() {
        return Caffeine.newBuilder().expireAfterWrite(1, TimeUnit.MINUTES);
    }

    @Bean
    public CacheManager cacheManager(Caffeine caffeine) {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        caffeineCacheManager.setCaffeine(caffeine);
        return caffeineCacheManager;
    }
}
