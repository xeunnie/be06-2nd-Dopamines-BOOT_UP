package com.example.dopamines.domain.admin.model.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserBlackRes {
    private Integer courseNum;
    private String name;
    private boolean status;
    private String role;
}
