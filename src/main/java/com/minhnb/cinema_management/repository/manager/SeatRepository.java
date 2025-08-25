package com.minhnb.cinema_management.repository.manager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.minhnb.cinema_management.domain.Seat;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

}
