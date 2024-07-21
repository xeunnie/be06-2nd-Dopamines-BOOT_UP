package com.example.dopamines.domain.reservation.model.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReservationReadRes {
    private Long idx;

    private String time;
    private Boolean status;
}
