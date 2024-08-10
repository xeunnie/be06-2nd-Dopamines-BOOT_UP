package com.example.dopamines.domain.reservation.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
    public class ReservationReadByUserRes {
    private Long idx;
    private LocalDateTime createdAt;
    private String time;
    private String section;
    private Integer floor;
}