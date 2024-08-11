package com.example.dopamines.global.security;

import com.example.dopamines.domain.user.model.entity.User;
import java.util.ArrayList;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {
    private final User user;

    //여러가지 role 을 보유했을 때, 확인 작업
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority(){
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });
        return collection;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

//    //이메일 인증 실패하거나,회원 정지 상태면 로그인 및 jwt 발급 실패
//    @Override
//    public boolean isEnabled() {
//        if(user.isEnabled() && user.isStatus()) {
//            return user.isEnabled();
//        }
//        return false;
//    }

    public Long getIdx() {return user.getIdx();}

    public User getUser() {return user;}
}
