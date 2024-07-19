package com.example.dopamines.domain.reservation.repository;

import com.example.dopamines.domain.reservation.model.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findAllByFloor(Integer floor);

    @Query("SELECT DISTINCT s.section FROM Seat s WHERE s.floor = :floor")
    List<String> findDistinctSectionsByFloor(Integer floor);

    List<Seat> findByFloorAndSection(Integer floor, String section);
}
