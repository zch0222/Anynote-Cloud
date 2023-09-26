//package com.anynote.gateway.security;
//
//import com.alibaba.fastjson2.JSON;
//import com.anynote.core.exception.BusinessException;
//import com.anynote.core.exception.auth.TokenException;
//import com.anynote.core.web.enums.ResCode;
//import com.anynote.core.web.model.bo.ResData;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.core.io.buffer.DataBuffer;
//import org.springframework.core.io.buffer.DataBufferFactory;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.AuthenticationEntryPoint;
//import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.nio.charset.StandardCharsets;
//
///**
// *
// * @author 称霸幼儿园
// */
//@Slf4j
//@Component
//public class NoteAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {
//
//
//
//
//    @Override
//    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException ex) {
//        return Mono.defer(() -> Mono.just(exchange.getResponse()))
//                .flatMap(response -> {
//                    response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
//                    DataBufferFactory dataBufferFactory = response.bufferFactory();
//                    String result = JSON.toJSONString(ResData.error(((TokenException) ex.getCause()).getErrorCode()));
//                    DataBuffer buffer = dataBufferFactory.wrap(result.getBytes());
//
//                    return response.writeWith(Mono.just(buffer));
//                });
////        HttpServletResponse response = (HttpServletResponse) exchange.getResponse();
////        log.info("1111");
////
////        try (PrintWriter printWriter = response.getWriter()) {
////            if (ex.getCause() instanceof BusinessException) {
////                printWriter.write(JSON.toJSONString(ResData.error(((BusinessException) ex.getCause()).getErrorCode())));
////            }
////            else {
////                printWriter.write(JSON.toJSONString(ResData.error(ResCode.AUTH_ERROR)));
////            }
////        } catch (IOException e) {
////            throw new RuntimeException(e);
////        }
//    }
//}
