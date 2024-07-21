package com.example.dopamines.domain.reservation.model.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationReserveReq {
    private Long userIdx;
    private Long seatIdx;
}
