package com.example.dopamines.domain.reservation.service;

import com.example.dopamines.domain.reservation.model.entity.Reservation;
import com.example.dopamines.domain.reservation.model.entity.Seat;
import com.example.dopamines.domain.reservation.model.request.ReservationReserveReq;
import com.example.dopamines.domain.reservation.model.request.SeatReadDetailReq;
import com.example.dopamines.domain.reservation.model.response.ReservationReadByUserRes;
import com.example.dopamines.domain.reservation.model.response.ReservationReadRes;
import com.example.dopamines.domain.reservation.model.response.ReservationReserveRes;
import com.example.dopamines.domain.reservation.model.response.SeatReadRes;
import com.example.dopamines.domain.reservation.repository.ReservationRepository;
import com.example.dopamines.domain.reservation.repository.SeatRepository;
import com.example.dopamines.domain.user.model.entity.User;
import com.example.dopamines.domain.user.repository.UserRepository;
import com.example.dopamines.global.common.BaseException;
import com.example.dopamines.global.common.BaseResponseStatus;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final SeatRepository seatRepository;
    private final UserRepository userRepository;

    public void create() {

    }

    public ReservationReserveRes reserve(ReservationReserveReq req) {
        LocalDateTime localDateTime = LocalDateTime.now();
        User user = userRepository.findById(req.getUserIdx())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.USER_NOT_FOUND));
        Seat seat = seatRepository.findById(req.getSeatIdx())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.SEAT_NOT_FOUND));

        Reservation reservation = Reservation.builder()
                .createdAt(localDateTime)
                .status(true)
                .user(user)
                .seat(seat)
                .build();

        reservation = reservationRepository.save(reservation);

        return ReservationReserveRes.builder()
                .idx(reservation.getIdx())
                .createdAt(reservation.getCreatedAt())
                .userIdx(user.getIdx())
                .userEmail(user.getEmail())
                .section(seat.getSection())
                .seatIdx(seat.getIdx())
                .time(seat.getTime())
                .build();
    }

    public List<SeatReadRes> seatList(Integer floor) {
        List<String> seatList = seatRepository.findDistinctSectionsByFloor(floor)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.RESERVE_SEAT_FAILED));
        List<SeatReadRes> seatReadResList = new ArrayList<>();

        for(String seat : seatList) {
            seatReadResList.add(SeatReadRes.builder()
                            .section(seat)
                            .build());
        }

        return seatReadResList;
    }

    public List<ReservationReadRes> seatListDetail(Integer floor, String section) {
        List<Seat> seatInfo = seatRepository.findByFloorAndSection(floor, section)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.RESERVE_TIME_FAILED));

        List<ReservationReadRes> reservationReadRes = new ArrayList<>();

        for(Seat seat : seatInfo) {
            if(reservationRepository.findBySeatIdx(seat.getIdx()).isPresent()) {
                reservationReadRes.add(ReservationReadRes.builder()
                        .idx(seat.getIdx())
                        .time(seat.getTime())
                        .status(reservationRepository.findBySeatIdx(seat.getIdx()).get().getStatus())
                        .build());
            } else {
                reservationReadRes.add(ReservationReadRes.builder()
                        .idx(seat.getIdx())
                        .time(seat.getTime())
                        .status(false)
                        .build());
            }
        }

        return reservationReadRes;
    }

    public List<ReservationReadByUserRes> reservationMyList(Long userIdx){
        List<Reservation> reservations = reservationRepository.findByUserIdx(userIdx)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.RESERVE_NOT_FOUND));

        List<ReservationReadByUserRes> result = new ArrayList<>();

        for(Reservation reservation : reservations){
            result.add(ReservationReadByUserRes.builder()
                    .createdAt(reservation.getCreatedAt())
                    .time(reservation.getSeat().getTime())
                    .section(reservation.getSeat().getSection())
                    .build());
        }

        return result;

    }

    public void cancel(Long idx){
        try {
            reservationRepository.deleteById(idx);
        } catch (EntityNotFoundException e) {
            throw new BaseException(BaseResponseStatus.RESERVE_NOT_FOUND);
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.RESERVE_DELETE_FAILED);
        }
    }
}
