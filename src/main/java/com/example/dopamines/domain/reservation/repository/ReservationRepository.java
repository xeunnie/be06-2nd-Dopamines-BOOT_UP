package com.example.dopamines.domain.reservation.repository;

import com.example.dopamines.domain.reservation.model.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByUserIdx(Long userIdx);
}
