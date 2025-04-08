package com.anynote.ai.api.model.bo;

import com.anynote.core.web.model.bo.QueryParam;
import lombok.*;

/**
 * @author 称霸幼儿园
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatConversationDeleteParam extends QueryParam {

    private Long conversationId;
}
