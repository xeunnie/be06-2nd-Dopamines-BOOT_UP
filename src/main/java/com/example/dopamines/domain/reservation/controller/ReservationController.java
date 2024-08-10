package com.example.dopamines.domain.reservation.controller;

import com.example.dopamines.domain.reservation.model.request.ReservationReserveReq;
import com.example.dopamines.domain.reservation.model.request.SeatReadDetailReq;
import com.example.dopamines.domain.reservation.model.response.*;
import com.example.dopamines.domain.reservation.service.ReservationService;
import com.example.dopamines.domain.user.model.entity.User;
import com.example.dopamines.global.common.BaseResponse;
import com.example.dopamines.global.common.BaseResponseStatus;
import com.example.dopamines.global.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("/reserve")
    public ResponseEntity<BaseResponse<?>> reserve(@RequestBody ReservationReserveReq req, @AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = userDetails.getUser();
        List<ReservationReserveRes> response = reservationService.reserve(req, user);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(response));
    }

    @GetMapping("/reservation-list")
    public ResponseEntity<BaseResponse<?>> reservationMyList(@AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = userDetails.getUser();
        System.out.println(user);
        List<ReservationReadByUserRes> response = reservationService.reservationMyList(user);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(response));
    }

    @GetMapping("/time-list")
    public ResponseEntity<BaseResponse<?>> seatListDetail(@RequestParam Integer floor, @RequestParam String section) {
        ReservationTimeRes response = reservationService.timeList(floor, section);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(response));
    }

    @DeleteMapping("/cancel/{idx}")
    public ResponseEntity<BaseResponse<?>> cancel(@PathVariable Long idx) {
        reservationService.cancel(idx);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(BaseResponseStatus.SUCCESS_NO_CONTENT));
    }
}
