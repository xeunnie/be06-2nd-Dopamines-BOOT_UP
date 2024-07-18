package com.example.dopamines.domain.reservation.model.response;

import com.example.dopamines.domain.reservation.model.entity.Seat;
import com.example.dopamines.domain.user.model.entity.User;
import lombok.*;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationRes {
    private Long idx;
    private LocalDate createdAt;
    private Long userIdx;
    private String userEmail;

    private String section;
    private Long seatIdx;
    private String time;
}
