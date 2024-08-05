package com.example.dopamines.domain.reservation.repository;

import com.example.dopamines.domain.reservation.model.entity.Reservation;
import com.example.dopamines.domain.reservation.model.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.swing.text.html.Option;
import java.nio.ByteBuffer;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Optional<List<Reservation>> findByUserIdx(Long userIdx);

    Optional<Reservation> findBySeatIdx(Long seatIdx);

    // JPQL 쿼리 사용
    @Query("SELECT r FROM Reservation r WHERE r.seat IN :seatIdxs AND DATE(r.createdAt) = :today")
    List<Reservation> findBySeatIdxInAndToday(List<Seat> seatIdxs, LocalDate today);
}