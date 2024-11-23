package com.ttn.demo.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

public class WebClientConfig {
    @Bean
    public WebClientConfig webClient(WebClient.Builder builder){
        return (WebClientConfig) builder.build();
    }
}
