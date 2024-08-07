package com.example.dopamines.domain.reservation.model.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ReservationTimeRes {

    private List<Long> seatIdx;
    private List<Long> reserveSeatIdx;
}