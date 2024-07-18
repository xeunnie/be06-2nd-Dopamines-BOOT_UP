package com.example.dopamines.domain.reservation.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SeatRes {
    private Long idx;
    private String section;
    private Boolean status;
}
