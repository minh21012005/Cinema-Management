package com.minhnb.cinema_management.service.manager;

import com.minhnb.cinema_management.domain.Room;
import com.minhnb.cinema_management.domain.Seat;
import com.minhnb.cinema_management.domain.SeatType;
import com.minhnb.cinema_management.domain.response.ResSeatDTO;
import com.minhnb.cinema_management.repository.manager.RoomRepository;
import com.minhnb.cinema_management.repository.manager.SeatRepository;
import com.minhnb.cinema_management.repository.manager.SeatTypeRepository;
import com.minhnb.cinema_management.util.error.IdInvalidException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SeatService {
    private final SeatRepository seatRepository;
    private final RoomRepository roomRepository;
    private final SeatTypeRepository seatTypeRepository;

    public SeatService(SeatRepository seatRepository, RoomRepository roomRepository,
                       SeatTypeRepository seatTypeRepository) {
        this.seatRepository = seatRepository;
        this.roomRepository = roomRepository;
        this.seatTypeRepository = seatTypeRepository;
    }

    public List<ResSeatDTO> fetchSeatsByRoom(Long id) throws IdInvalidException{
        Optional<Room> roomOptional = this.roomRepository.findById(id);
        if(roomOptional.isEmpty()){
            throw new IdInvalidException("Room không tồn tại!");
        }
        List<Seat> seats = roomOptional.get().getSeats();
        return seats.stream().map(seat -> {
            ResSeatDTO dto = new ResSeatDTO();
             dto.setId(seat.getId());
             dto.setName(seat.getName());
             dto.setActive(seat.isActive());
             dto.setSeatType(seat.getSeatType());
             return dto;
        }).collect(Collectors.toList());
    }

    public List<SeatType> fetchAllSeatTypes(){
        return this.seatTypeRepository.findAll();
    }
}
