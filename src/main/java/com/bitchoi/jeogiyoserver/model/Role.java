package com.bitchoi.jeogiyoserver.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    USER("ROLE_GUEST", 1),
    MANAGER("ROLE_MANAGER", 2),
    ADMIN("ROLE_USER", 3);
    private final String key;
    private final int value;
}

