package org.ehox.ExchangeThat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Configuration
public class Config {
    private static final int CONN_TIMEOUT = 1000;

    @Bean
    public RestTemplate restTemplate() {
        var s = new SimpleClientHttpRequestFactory();
        s.setConnectTimeout(CONN_TIMEOUT);
        var restTemplate = new RestTemplate(s);
        restTemplate.setInterceptors(this.getInterceptorList());
        return restTemplate;
    }

    private List<ClientHttpRequestInterceptor> getInterceptorList(){
        return List.of(new CustomInterceptor());
    }
}
