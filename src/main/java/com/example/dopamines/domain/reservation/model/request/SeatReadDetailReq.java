package com.example.dopamines.domain.reservation.model.request;

import lombok.*;

@Getter
@Builder
public class SeatReadDetailReq {
    private String section;
    private Integer floor;
}
