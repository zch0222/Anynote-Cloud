package com.anynote.manage.model.vo;

import com.anynote.system.api.model.bo.Token;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 在线用户VO
 * @author 称霸幼儿园
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OnlineUserVO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class OnlineItem {

        /**
         * 缓存key
         */
        private String cacheKey;

        /**
         * 用户 Token
         */
        private Token token;

        /**
         * 登录时间
         */
        private Long loginTime;

        /**
         * 登录IP地址
         */
        private String ipaddr;

        /**
         * 长期有效
         */
        private boolean longTerm;
    }

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 角色
     */
    private String role;

    /**
     * 在线数量
     */
    private Integer onlineItemCount;

    private List<OnlineItem> onlineItems;
}
