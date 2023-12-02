package com.anynote.core.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * 文件配置类
 * @author 称霸幼儿园
 */
@Slf4j
@Configuration
public class FileConfig {

    /**
     * 指定自定义解析器
     * 将 multipartResolver 指向我们刚刚创建好的继承 CustomMultipartResolver 类的 自定义文件上传处理类
     *
     * @return
     */
    @Bean(name = "multipartResolver")
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setDefaultEncoding("UTF-8");
        commonsMultipartResolver.getFileUpload().setProgressListener(
                (long pBytesRead, long pContentLength, int pItems) -> {
                    log.info("pBytesRead:{}, pContentLength:{}, pItems:{}", pBytesRead, pContentLength, pItems);
                }
        );
        return new CommonsMultipartResolver();
    }
}
