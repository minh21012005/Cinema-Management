package com.minhnb.cinema_management.repository.manager;

import com.minhnb.cinema_management.domain.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    Optional<Seat> findByRowIndexAndColIndexAndRoomId(Integer rowIndex, Integer colIndex, Long roomId);
}
