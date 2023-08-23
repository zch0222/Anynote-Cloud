package com.anynote.common.redis.enums;

/**
 *
 * @author 称霸幼儿园
 */
public enum CachePrefixEnum {

    ACCESS_TOKEN,
    REFRESH_TOKEN;

    public String getPrefix() {
        return "{" + this.name() + "}_";
    }

    /**
     * 获取用户Token前缀
     * @param username
     * @return
     */
    public String getPrefix(String username) {
        return "{" + username + "}_{" + this.name() + "}_";
    }
}
