package com.example.dopamines.domain.reservation.controller;

import com.example.dopamines.domain.reservation.model.request.ReservationReserveReq;
import com.example.dopamines.domain.reservation.model.request.SeatReadDetailReq;
import com.example.dopamines.domain.reservation.model.response.ReservationReadByUserRes;
import com.example.dopamines.domain.reservation.model.response.ReservationReadRes;
import com.example.dopamines.domain.reservation.model.response.ReservationReserveRes;
import com.example.dopamines.domain.reservation.model.response.SeatReadRes;
import com.example.dopamines.domain.reservation.service.ReservationService;
import com.example.dopamines.global.common.BaseResponse;
import com.example.dopamines.global.common.BaseResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("/reserve")
    public ResponseEntity<BaseResponse<?>> reserve(@RequestBody ReservationReserveReq req) {
        ReservationReserveRes response = reservationService.reserve(req);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(response));
    }

    @GetMapping("/reservation-list")
    public ResponseEntity<BaseResponse<List<?>>> reservationMyList(Long userIdx) {
        List<ReservationReadByUserRes> response = reservationService.reservationMyList(userIdx);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(response));
    }

    @GetMapping("/seat-list/{floor}")
    public ResponseEntity<BaseResponse<List<?>>> seatList(@PathVariable("floor") Integer floor) {
        List<SeatReadRes> response = reservationService.seatList(floor);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(response));
    }

    @GetMapping("/seat-list-detail")
    public ResponseEntity<BaseResponse<List<?>>> seatListDetail(@RequestParam Integer floor, @RequestParam String section) {
        List<ReservationReadRes> response = reservationService.seatListDetail(floor, section);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(response));
    }

    @DeleteMapping("/cancel/{idx}")
    public ResponseEntity<BaseResponse<?>> cancel(@PathVariable Long idx) {
        reservationService.cancel(idx);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(BaseResponseStatus.SUCCESS_NO_CONTENT));
    }
}
