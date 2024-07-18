package com.example.dopamines.domain.reservation.repository;

import com.example.dopamines.domain.reservation.model.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat, Long> {
}
