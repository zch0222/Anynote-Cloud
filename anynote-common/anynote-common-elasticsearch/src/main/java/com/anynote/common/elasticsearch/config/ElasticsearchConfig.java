package com.anynote.common.elasticsearch.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 称霸幼儿园
 */
@Configuration
public class ElasticsearchConfig {

    @Bean
    public RestClient client() {
        return RestClient.builder(HttpHost.create("http://localhost:9200"))
                .build();
    }
}
