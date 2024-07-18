package com.example.dopamines.domain.reservation.controller;

import com.example.dopamines.domain.reservation.model.request.ReservationReq;
import com.example.dopamines.domain.reservation.model.response.ReservationListRes;
import com.example.dopamines.domain.reservation.model.response.ReservationRes;
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

    @PostMapping("/reservate")
    public ResponseEntity<BaseResponse<ReservationRes>> reservate(@RequestBody ReservationReq req) {
        try {
            ReservationRes response = reservationService.reservate(req);
            return ResponseEntity.ok(new BaseResponse<>(response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.
                    BAD_REQUEST).body(new BaseResponse<>(BaseResponseStatus.RESERVATION_CREATE_FAILED));
        }
    }

    @GetMapping("/reservation-list")
    public ResponseEntity<BaseResponse<List<ReservationListRes>>> reservationList(Long userIdx) {
        try {
            List<ReservationListRes> response = reservationService.reservationList(userIdx);
            return ResponseEntity.ok(new BaseResponse<>(response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse<>(BaseResponseStatus.RESERVATION_CREATE_FAILED));
        }

    }

    @GetMapping("/cancel")
    public ResponseEntity<BaseResponse<String>> cancel(Long idx) {
        try {
            reservationService.cancel(idx);
            return ResponseEntity.ok(new BaseResponse<>(BaseResponseStatus.SUCCESS));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.
                    BAD_REQUEST).body(new BaseResponse<>(BaseResponseStatus.RESERVATION_DELETE_FAILED));
        }
    }
}
