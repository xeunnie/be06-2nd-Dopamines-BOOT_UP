package com.example.dopamines.domain.user.service;

import com.example.dopamines.domain.user.model.entity.Team;
import com.example.dopamines.domain.user.model.entity.User;
import com.example.dopamines.domain.user.model.request.UserSignupRequest;
import com.example.dopamines.domain.user.model.response.UserSignupResponse;
import com.example.dopamines.domain.user.repository.TeamRepository;
import com.example.dopamines.domain.user.repository.UserRepository;
import com.example.dopamines.global.common.BaseException;
import com.example.dopamines.global.common.BaseResponseStatus;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final TeamRepository teamRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserSignupResponse signup(UserSignupRequest request) {
        LocalDateTime localDateTime = LocalDateTime.now();
        localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(bCryptPasswordEncoder.encode(request.getPassword()))
                .nickname(request.getNickname())
                .address(request.getAddress())
                .phoneNumber(request.getPhoneNumber())
                .createdAt(localDateTime)
                .updatedAt(localDateTime)
                .role("ROLE_TEMPORARY_USER")
                .build();
        User result = userRepository.save(user);

        if(result == null){
            throw new BaseException(BaseResponseStatus.USER_INVALID_SINGUP_REQUEST);
        }
        return new UserSignupResponse().toDto(result);
    }

    public void setActiveOn(String email) {
        Optional<User> findUser = userRepository.findByEmail(email);
        if(findUser.isPresent()){
            User user = findUser.get();
            user.setActiveOn(true);
            userRepository.save(user);
        }
    }

    // 임시로 쓸 user,team dummy 데이터 삽입
    public void insertUserAndTeam() {
        makeTeam();

        LocalDateTime localDateTime = LocalDateTime.now();
        localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        List<String[]> dummyUsers = new ArrayList<>();
        String[] five = new String[]{"강혜정", "박성준", "박종성", "송나경", "정수연"};
        String[] x404 = new String[]{"김다윤", "오규림", "이가은", "장유정"} ;
        String[] gamja = new String[]{"김동욱", "김은선", "이재룡", "지연희", "차윤슬"} ;
        String[] ketchop = new String[]{"구은주", "안준홍", "최정완", "한별"} ;
        String[] dopamines = new String[]{"곽동현", "서시현", "유송연", "최수빈", "최승은"} ;
        String[] fourtrees = new String[]{"강태성", "김우혁", "도지민", "서재은"} ;

        dummyUsers.add(five);
        dummyUsers.add(x404);
        dummyUsers.add(gamja);
        dummyUsers.add(ketchop);
        dummyUsers.add(dopamines);
        dummyUsers.add(fourtrees);

        Long teamIndex = 1L;
        for(String[] users : dummyUsers){
            for(int i=0; i<users.length; i++){
                User user = User.builder()
                        .name(users[i])
                        .email(users[i]+"@test.com")
                        .password(bCryptPasswordEncoder.encode("qwer1234"))
                        .nickname(users[i])
                        .address("보라매로 87")
                        .phoneNumber("010-1234-5678")
                        .createdAt(localDateTime)
                        .updatedAt(localDateTime)
                        .role("ROLE_USER")
                        .build();
                user.setActiveOn(true);
                user.setTeam(teamRepository.findById(teamIndex).get());
                user.setCourseNum(6);
                userRepository.save(user);
            }
            teamIndex++;
        }
    }

    private void makeTeam() {
        String[] teamName = {"파아아아아이브","404x","감자","케촙","도파민즈","포트리스"};
        for(int i=0; i<teamName.length; i++){
            Team team = Team.builder()
                    .teamName(teamName[i])
                    .build();
            teamRepository.save(team);
        }
    }
}
