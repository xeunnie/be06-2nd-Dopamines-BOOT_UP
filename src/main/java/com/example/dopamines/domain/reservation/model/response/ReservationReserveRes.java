package com.example.dopamines.domain.reservation.model.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationReserveRes {
    private Long idx;
    private LocalDateTime createdAt;
    private Long userIdx;
    private String userEmail;

    private String section;
    private Long seatIdx;
    private String time;
}
