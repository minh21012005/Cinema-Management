package com.minhnb.cinema_management.repository.manager;

import com.minhnb.cinema_management.domain.Cinema;
import com.minhnb.cinema_management.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByCinema_Id(Long cinemaId);
    boolean existsByNameAndCinema(String name, Cinema cinema);
}
