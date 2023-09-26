//package com.anynote.gateway.security;
//
//import com.anynote.gateway.filter.AuthFilter;
//import com.anynote.gateway.properties.SecurityIgnoreProperties;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
//import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.security.web.server.SecurityWebFilterChain;
//import org.springframework.web.cors.CorsConfigurationSource;
//
///**
// * Spring Security 配置类
// * @author 称霸幼儿园
// */
//@EnableWebFluxSecurity
//public class SecurityConfig {
//
//    @Bean
//    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) {
//        httpSecurity
//                .csrf().disable()
//                //禁止网页iframe
//                .headers().frameOptions().disable();
//        return httpSecurity.build();
//    }
//
//
//
//}
