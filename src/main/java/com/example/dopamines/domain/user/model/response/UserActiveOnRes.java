package com.example.dopamines.domain.user.model.response;

import lombok.Builder;

@Builder
public class UserActiveOnRes {
    private String email;
    private String name;
    private boolean enabled;
}
