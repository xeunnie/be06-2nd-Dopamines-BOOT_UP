package com.example.dopamines;

import com.example.dopamines.domain.reservation.model.entity.Seat;
import com.example.dopamines.domain.reservation.repository.SeatRepository;
import com.example.dopamines.domain.user.model.entity.Team;
import com.example.dopamines.domain.user.model.entity.User;
import com.example.dopamines.domain.user.repository.TeamRepository;
import com.example.dopamines.domain.user.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
@Profile("dev")
@RequiredArgsConstructor
public class InitDB {

    private final SeatRepository seatRepository;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostConstruct
    public void dataInsert() {
        List<Seat> seats = new ArrayList<>();

        seats.add(Seat.builder().section("A").floor(3).time("18:00 - 19:00").build());
        seats.add(Seat.builder().section("A").floor(3).time("19:00 - 20:00").build());
        seats.add(Seat.builder().section("A").floor(3).time("20:00 - 21:00").build());
        seats.add(Seat.builder().section("A").floor(3).time("21:00 - 22:00").build());
        seats.add(Seat.builder().section("A").floor(4).time("18:00 - 19:00").build());
        seats.add(Seat.builder().section("A").floor(4).time("19:00 - 20:00").build());
        seats.add(Seat.builder().section("A").floor(4).time("20:00 - 21:00").build());
        seats.add(Seat.builder().section("A").floor(4).time("21:00 - 22:00").build());
        seats.add(Seat.builder().section("A").floor(5).time("18:00 - 19:00").build());
        seats.add(Seat.builder().section("A").floor(5).time("19:00 - 20:00").build());
        seats.add(Seat.builder().section("A").floor(5).time("20:00 - 21:00").build());
        seats.add(Seat.builder().section("A").floor(5).time("21:00 - 22:00").build());
        seats.add(Seat.builder().section("B").floor(5).time("18:00 - 19:00").build());
        seats.add(Seat.builder().section("B").floor(5).time("19:00 - 20:00").build());
        seats.add(Seat.builder().section("B").floor(5).time("20:00 - 21:00").build());
        seats.add(Seat.builder().section("B").floor(5).time("21:00 - 22:00").build());
        seats.add(Seat.builder().section("C").floor(5).time("18:00 - 19:00").build());
        seats.add(Seat.builder().section("C").floor(5).time("19:00 - 20:00").build());
        seats.add(Seat.builder().section("C").floor(5).time("20:00 - 21:00").build());
        seats.add(Seat.builder().section("C").floor(5).time("21:00 - 22:00").build());
        seats.add(Seat.builder().section("D").floor(5).time("18:00 - 19:00").build());
        seats.add(Seat.builder().section("D").floor(5).time("19:00 - 20:00").build());
        seats.add(Seat.builder().section("D").floor(5).time("20:00 - 21:00").build());
        seats.add(Seat.builder().section("D").floor(5).time("21:00 - 22:00").build());
        seats.add(Seat.builder().section("E").floor(5).time("18:00 - 19:00").build());
        seats.add(Seat.builder().section("E").floor(5).time("19:00 - 20:00").build());
        seats.add(Seat.builder().section("E").floor(5).time("20:00 - 21:00").build());
        seats.add(Seat.builder().section("E").floor(5).time("21:00 - 22:00").build());
        seats.add(Seat.builder().section("F").floor(5).time("18:00 - 19:00").build());
        seats.add(Seat.builder().section("F").floor(5).time("19:00 - 20:00").build());
        seats.add(Seat.builder().section("F").floor(5).time("20:00 - 21:00").build());
        seats.add(Seat.builder().section("F").floor(5).time("21:00 - 22:00").build());

        for(Seat seat : seats) {
            seatRepository.save(seat);
        }

        insertUserAndTeam();
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
