package com.example.dopamines.domain.reservation.model.request;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationReq {
    private Long userIdx;
    private Long seatIdx;
}
