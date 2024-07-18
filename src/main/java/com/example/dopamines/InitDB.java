package com.example.dopamines;

import com.example.dopamines.domain.reservation.model.entity.Seat;
import com.example.dopamines.domain.reservation.repository.SeatRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Profile("dev")
@RequiredArgsConstructor
public class InitDB {

    private final SeatRepository seatRepository;

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
    }

}
