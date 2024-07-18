package com.example.dopamines.domain.reservation.model.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatReq {
    private String section;
    private Integer floor;
    private Boolean status;
}
