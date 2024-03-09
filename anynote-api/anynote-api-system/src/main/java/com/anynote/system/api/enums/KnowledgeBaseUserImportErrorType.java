package com.anynote.system.api.enums;

public enum KnowledgeBaseUserImportErrorType {

    REGISTRATION_FAILED("注册失败"),
    USER_EXISTS_IN_KNOWLEDGE_BASE("用户已经存在于知识库中");

    private final String value;

    KnowledgeBaseUserImportErrorType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

}
