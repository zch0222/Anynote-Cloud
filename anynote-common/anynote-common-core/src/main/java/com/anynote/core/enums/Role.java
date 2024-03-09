package com.anynote.core.enums;

public enum Role {

    ADMIN_X("ADMIN_X"),
    STUDENT("STUDENT"),
    TEACHER("TEACHER");


    private final String value;

    Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
