package com.minhnb.cinema_management.repository.manager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.minhnb.cinema_management.domain.SeatType;

@Repository
public interface SeatTypeRepository extends JpaRepository<SeatType, Long> {

}
