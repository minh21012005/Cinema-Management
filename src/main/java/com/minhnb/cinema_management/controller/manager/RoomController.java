package com.minhnb.cinema_management.controller.manager;

import com.minhnb.cinema_management.domain.Cinema;
import com.minhnb.cinema_management.domain.response.ResRoomDTO;
import com.minhnb.cinema_management.service.manager.CinemaService;
import com.minhnb.cinema_management.service.manager.RoomService;
import com.minhnb.cinema_management.util.error.IdInvalidException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@PreAuthorize("hasRole('MANAGER')")
public class RoomController {
    private final RoomService roomService;
    private final CinemaService cinemaService;

    public RoomController(RoomService roomService, CinemaService cinemaService) {
        this.roomService = roomService;
        this.cinemaService = cinemaService;
    }

    @GetMapping("/cinemas/{id}/rooms")
    public ResponseEntity<?> getRoomsByCinema(@PathVariable Long id) throws IdInvalidException {
        Optional<Cinema> cinema = this.cinemaService.findById(id);
        if (cinema.isEmpty()) {
            throw new IdInvalidException("Cinema với id " + id + " không tồn tại!");
        }

        List<ResRoomDTO> rooms = this.roomService.fetchAllRoom(id);
        return ResponseEntity.ok(rooms); // nếu rooms rỗng thì trả [] thay vì null
    }


}
