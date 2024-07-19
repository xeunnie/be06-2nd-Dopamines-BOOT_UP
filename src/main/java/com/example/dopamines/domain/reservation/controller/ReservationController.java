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
    public ResponseEntity<BaseResponse<ReservationReserveRes>> reserve(@RequestBody ReservationReserveReq req) {
        try {
            ReservationReserveRes response = reservationService.reserve(req);
            return ResponseEntity.ok(new BaseResponse<>(response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.
                    BAD_REQUEST).body(new BaseResponse<>(BaseResponseStatus.RESERVATION_CREATE_FAILED));
        }
    }

    @GetMapping("/reservation-list")
    public ResponseEntity<BaseResponse<List<ReservationReadByUserRes>>> reservationMyList(Long userIdx) {
        try {
            List<ReservationReadByUserRes> response = reservationService.reservationMyList(userIdx);
            return ResponseEntity.ok(new BaseResponse<>(response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse<>(BaseResponseStatus.RESERVATION_CREATE_FAILED));
        }
    }

    @GetMapping("/seat-list/{floor}")
    public ResponseEntity<BaseResponse<List<SeatReadRes>>> seatList(@PathVariable("floor") Integer floor) {
        List<SeatReadRes> response = reservationService.seatList(floor);
        return ResponseEntity.ok(new BaseResponse<>(response));
    }

    @GetMapping("/seat-list-detail")
    public ResponseEntity<BaseResponse<List<ReservationReadRes>>> seatListDetail(@RequestParam Integer floor, @RequestParam String section) {
        List<ReservationReadRes> response = reservationService.seatListDetail(floor, section);
        return ResponseEntity.ok(new BaseResponse<>(response));
    }

    @DeleteMapping("/cancel/{idx}")
    public ResponseEntity<BaseResponse<String>> cancel(@PathVariable Long idx) {
        try {
            reservationService.cancel(idx);
            return ResponseEntity.ok(new BaseResponse<>(BaseResponseStatus.SUCCESS));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse<>(BaseResponseStatus.RESERVATION_DELETE_FAILED));
        }
    }
}
