package com.example.dopamines.domain.reservation.model.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationReserveReq {
    private List<Long> selectedSeats;
}