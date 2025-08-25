package com.minhnb.cinema_management.controller.manager;

import com.minhnb.cinema_management.domain.Cinema;
import com.minhnb.cinema_management.domain.Room;
import com.minhnb.cinema_management.domain.RoomType;
import com.minhnb.cinema_management.domain.request.ReqCreateRoomDTO;
import com.minhnb.cinema_management.domain.request.ReqRoomDTO;
import com.minhnb.cinema_management.domain.response.ResRoomDTO;
import com.minhnb.cinema_management.service.manager.CinemaService;
import com.minhnb.cinema_management.service.manager.RoomService;
import com.minhnb.cinema_management.util.error.IdInvalidException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/rooms/types")
    public ResponseEntity<List<RoomType>> fetchAllRoomType() {
        return ResponseEntity.status(HttpStatus.OK).body(this.roomService.fetchAllRoomType());
    }

    @PostMapping("/rooms")
    public ResponseEntity<Room> createRoom(@Valid @RequestBody ReqCreateRoomDTO dto) throws IdInvalidException {
        if (dto.getRows() <= 0 || dto.getCols() <= 0) {
            throw new IdInvalidException("Số hàng và số cột phải lớn hơn 0!");
        }
        if (dto.getRows() > 25 || dto.getCols() > 25) {
            throw new IdInvalidException("Số hàng và số cột phải nhỏ hơn hoặc bằng 25!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(this.roomService.createRoom(dto));
    }

    @PutMapping("/rooms")
    public ResponseEntity<ResRoomDTO> updateRoom(@RequestBody ReqRoomDTO dto) throws IdInvalidException {
        return ResponseEntity.status(HttpStatus.OK).body(this.roomService.updateRoom(dto));
    }

    @PutMapping("/rooms/change-status/{id}")
    public ResponseEntity<Object> changeRoomStatus(@PathVariable Long id) throws IdInvalidException {
        Optional<Room> roomOptional = this.roomService.findById(id);
        if (roomOptional.isEmpty()) {
            throw new IdInvalidException("Room không tồn tại!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(this.roomService.changeStatus(roomOptional.get()));
    }

}
