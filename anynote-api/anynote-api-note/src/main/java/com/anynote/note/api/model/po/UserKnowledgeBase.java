package com.anynote.note.api.model.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户知识库关联表
 * @author 称霸幼儿园
 */
@TableName("n_user_knowledge_base")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserKnowledgeBase {

    private Long userId;

    private Long knowledgeBaseId;

    private Integer permissions;
}
