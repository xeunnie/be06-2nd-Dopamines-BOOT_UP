package com.example.dopamines.domain.admin.model.response;

import lombok.Builder;

@Builder
public class UserBlackRes {
    private Integer courseNum;
    private String name;
    private boolean status;
    private String role;
}
