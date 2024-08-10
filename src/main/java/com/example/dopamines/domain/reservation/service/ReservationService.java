package com.example.dopamines.domain.reservation.service;

import com.example.dopamines.domain.reservation.model.entity.Reservation;
import com.example.dopamines.domain.reservation.model.entity.Seat;
import com.example.dopamines.domain.reservation.model.request.ReservationReserveReq;
import com.example.dopamines.domain.reservation.model.request.SeatReadDetailReq;
import com.example.dopamines.domain.reservation.model.response.*;
import com.example.dopamines.domain.reservation.repository.ReservationRepository;
import com.example.dopamines.domain.reservation.repository.SeatRepository;
import com.example.dopamines.domain.user.model.entity.User;
import com.example.dopamines.domain.user.repository.UserRepository;
import com.example.dopamines.global.common.BaseException;
import com.example.dopamines.global.common.BaseResponseStatus;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    @Transactional
    public List<ReservationReserveRes> reserve(ReservationReserveReq req, User user) {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = localDateTime.format(formatter);

        User reqUser = userRepository.findById(user.getIdx())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.USER_NOT_FOUND));

        List<ReservationReserveRes> reservationList = new ArrayList<>();
        for(Long seatIdx : req.getSelectedSeats()) {
            Seat seat = seatRepository.findById(seatIdx)
                    .orElseThrow(() -> new BaseException(BaseResponseStatus.SEAT_NOT_FOUND));

            Reservation reservation = Reservation.builder()
                    .createdAt(LocalDateTime.parse(formattedDateTime, formatter))
                    .status(true)
                    .user(reqUser)
                    .seat(seat)
                    .build();

            reservation = reservationRepository.save(reservation);

            reservationList.add(ReservationReserveRes.builder()
                    .idx(reservation.getIdx())
                    .createdAt(reservation.getCreatedAt())
                    .userIdx(user.getIdx())
                    .userEmail(user.getEmail())
                    .section(seat.getSection())
                    .seatIdx(seat.getIdx())
                    .time(seat.getTime())
                    .build());
        }

        return reservationList;
    }

    public ReservationTimeRes timeList(Integer floor, String section) {
        List<Seat> seatInfo = seatRepository.findByFloorAndSection(floor, section)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.RESERVE_TIME_FAILED));

        List<Long> seatIdxs = new ArrayList<>();
        for(Seat seat : seatInfo) {
            seatIdxs.add(seat.getIdx());
        }

        LocalDate today = LocalDate.now();

        List<Reservation> reservationSeatList = reservationRepository.findBySeatIdxInAndToday(seatInfo, today);
        List<Long> reservationIdxs = new ArrayList<>();
        for(Reservation reservation : reservationSeatList) {
            reservationIdxs.add(reservation.getSeat().getIdx());
        }

        return ReservationTimeRes.builder()
                .seatIdx(seatIdxs)
                .reserveSeatIdx(reservationIdxs)
                .build();
    }

    public List<ReservationReadByUserRes> reservationMyList(User user){
        List<Reservation> reservations = reservationRepository.findByUserIdx(user.getIdx())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.RESERVE_NOT_FOUND));

        List<ReservationReadByUserRes> result = new ArrayList<>();

        for(Reservation reservation : reservations){
            result.add(ReservationReadByUserRes.builder()
                    .idx(reservation.getIdx())
                    .createdAt(reservation.getCreatedAt())
                    .time(reservation.getSeat().getTime())
                    .section(reservation.getSeat().getSection())
                    .floor(reservation.getSeat().getFloor())
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
