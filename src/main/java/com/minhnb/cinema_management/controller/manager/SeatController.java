package com.minhnb.cinema_management.controller.manager;

import com.minhnb.cinema_management.domain.Seat;
import com.minhnb.cinema_management.domain.SeatType;
import com.minhnb.cinema_management.domain.request.ReqSeatDTO;
import com.minhnb.cinema_management.domain.response.ResSeatDTO;
import com.minhnb.cinema_management.service.manager.SeatService;
import com.minhnb.cinema_management.util.error.IdInvalidException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@PreAuthorize("hasRole('MANAGER')")
public class SeatController {
    private final SeatService seatService;

    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @GetMapping("/rooms/{id}/seats")
    public ResponseEntity<List<ResSeatDTO>> fetchAllSeatByRoom(@PathVariable Long id) throws IdInvalidException {
        return ResponseEntity.status(HttpStatus.OK).body(this.seatService.fetchSeatsByRoom(id));
    }

    @GetMapping("/seats/types")
    public ResponseEntity<List<SeatType>> fetchAllSeatTypes() {
        return ResponseEntity.status(HttpStatus.OK).body(this.seatService.fetchAllSeatTypes());
    }

    @PutMapping("/seats/change-status/{id}")
    public ResponseEntity<ResSeatDTO> changeSeatStatus(@PathVariable Long id) throws IdInvalidException {
        Seat seat = this.seatService.findById(id).orElseThrow(() -> new IdInvalidException(""));
        return ResponseEntity.status(HttpStatus.OK).body(this.seatService.changeSeatStatus(seat));
    }

    @PutMapping("/seats/{id}/type/{typeId}")
    public ResponseEntity<ResSeatDTO> changeSeatType(
            @PathVariable Long id, @PathVariable Long typeId) throws IdInvalidException {
        Seat seat = this.seatService.findById(id).orElseThrow(() -> new IdInvalidException(""));
        SeatType seatType = this.seatService.findSeatTypeById(typeId).orElseThrow(() -> new IdInvalidException(""));
        return ResponseEntity.status(HttpStatus.OK).body(this.seatService.changeSeatType(seat, seatType));
    }

    @PostMapping("/seats")
    public ResponseEntity<ResSeatDTO> createSeat(@RequestBody ReqSeatDTO dto) throws IdInvalidException{
        return ResponseEntity.status(HttpStatus.CREATED).body(this.seatService.createSeat(dto));
    }
}
