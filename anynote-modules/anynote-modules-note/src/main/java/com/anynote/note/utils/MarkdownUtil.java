package com.anynote.note.utils;

import com.anynote.core.utils.StringUtils;
import com.anynote.note.model.bo.MarkdownImage;

/**
 * Markdown 工具类
 * @author 称霸幼儿园
 */
public class MarkdownUtil {

    public static String buildMarkdownImage(String attribute, String url) {
        return StringUtils.format("![{}]({})", attribute, url);
    }
}
