package com.example.dopamines.domain.reservation.service;

import com.example.dopamines.domain.reservation.model.entity.Reservation;
import com.example.dopamines.domain.reservation.model.entity.Seat;
import com.example.dopamines.domain.reservation.model.request.ReservationReq;
import com.example.dopamines.domain.reservation.model.response.ReservationListRes;
import com.example.dopamines.domain.reservation.model.response.ReservationRes;
import com.example.dopamines.domain.reservation.repository.ReservationRepository;
import com.example.dopamines.domain.reservation.repository.SeatRepository;
import com.example.dopamines.domain.user.repository.UserRepository;
import com.example.dopamines.global.common.BaseException;
import com.example.dopamines.global.common.BaseResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final SeatRepository seatRepository;
    private final UserRepository userRepository;

    public void create() {

    }

    public ReservationRes reservate(ReservationReq req){

        Reservation reservation = Reservation.builder()
                .createdAt(LocalDate.parse(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))))
                .status(true)
                .user(userRepository.findById(req.getUserIdx()).get())
                .seat(seatRepository.findById(req.getSeatIdx()).get())
                .build();

        reservation = reservationRepository.save(reservation);

        return ReservationRes.builder()
                .idx(reservation.getIdx())
                .createdAt(reservation.getCreatedAt())
                .userIdx(reservation.getUser().getIdx())
                .userEmail(reservation.getUser().getEmail())
                .section(reservation.getSeat().getSection())
                .seatIdx(reservation.getSeat().getIdx())
                .time(reservation.getSeat().getTime())
                .build();
    }

    public List<ReservationListRes> reservationList(Long userIdx){
        List<Reservation> reservations = reservationRepository.findByUserIdx(userIdx);

        List<ReservationListRes> result = new ArrayList<>();

        for(Reservation reservation : reservations){
            result.add(ReservationListRes.builder()
                    .createdAt(reservation.getCreatedAt())
                    .time(reservation.getSeat().getTime())
                    .section(reservation.getSeat().getSection())
                    .build());
        }

        return result;

    }

    public void cancel(Long idx){
        reservationRepository.deleteById(idx);
    }
}
