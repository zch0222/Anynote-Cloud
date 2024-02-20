package com.anynote.core.utils;

import org.springframework.web.util.UriComponentsBuilder;

public class UrlUtil {

    public static String removeAllParams(String url) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        return builder.replaceQuery(null).build().toUri().toString();
    }
}
