package org.ehox.ExchangeThat.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class CustomInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        HttpHeaders headers = request.getHeaders();
        if (!headers.containsKey(HttpHeaders.ACCEPT)) {
            headers.add(HttpHeaders.ACCEPT, APPLICATION_JSON_VALUE);
        }
        log.debug("Intercepting Request. URI = {}, Method = {}, Headers = {}", request.getURI(), request.getMethodValue(), request.getHeaders());
        var response = execution.execute(request, body);
//        log.debug("Response body = {}", new String(response.getBody().readAllBytes(), StandardCharsets.UTF_8));
        return response;
    }
}