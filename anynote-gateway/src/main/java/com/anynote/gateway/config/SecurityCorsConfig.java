//package com.anynote.gateway.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.reactive.CorsConfigurationSource;
//import org.springframework.web.server.ServerWebExchange;
//
///**
// * spring security 跨域配置
// * @author 称霸幼儿园
// */
//@Configuration
//public class SecurityCorsConfig implements CorsConfigurationSource{
//
//    @Override
//    public CorsConfiguration getCorsConfiguration(ServerWebExchange exchange) {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.addAllowedOrigin("*");
//        configuration.addAllowedMethod("*");
//        configuration.addAllowedHeader("*");
//        return configuration;
//    }
//}
