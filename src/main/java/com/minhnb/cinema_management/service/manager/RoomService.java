package com.minhnb.cinema_management.service.manager;

import com.minhnb.cinema_management.domain.Cinema;
import com.minhnb.cinema_management.domain.Room;
import com.minhnb.cinema_management.domain.RoomType;
import com.minhnb.cinema_management.domain.Seat;
import com.minhnb.cinema_management.domain.SeatType;
import com.minhnb.cinema_management.domain.request.ReqCreateRoomDTO;
import com.minhnb.cinema_management.domain.request.ReqRoomDTO;
import com.minhnb.cinema_management.domain.response.ResRoomDTO;
import com.minhnb.cinema_management.repository.manager.RoomRepository;
import com.minhnb.cinema_management.repository.manager.RoomTypeRepository;
import com.minhnb.cinema_management.repository.manager.SeatRepository;
import com.minhnb.cinema_management.repository.manager.SeatTypeRepository;
import com.minhnb.cinema_management.util.error.IdInvalidException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final RoomTypeRepository roomTypeRepository;
    private final CinemaService cinemaService;
    private final SeatRepository seatRepository;
    private final SeatTypeRepository seatTypeRepository;

    public RoomService(RoomRepository roomRepository, RoomTypeRepository roomTypeRepository,
            CinemaService cinemaService, SeatRepository seatRepository, SeatTypeRepository seatTypeRepository) {
        this.roomRepository = roomRepository;
        this.roomTypeRepository = roomTypeRepository;
        this.cinemaService = cinemaService;
        this.seatRepository = seatRepository;
        this.seatTypeRepository = seatTypeRepository;
    }

    public List<ResRoomDTO> fetchAllRoom(Long cinemaId) {
        List<Room> rooms = this.roomRepository.findByCinema_Id(cinemaId);

        if (rooms.isEmpty()) {
            return Collections.emptyList(); // trả về list rỗng, không null
        }

        return rooms.stream().map(room -> {
            ResRoomDTO dto = new ResRoomDTO();
            ResRoomDTO.Type type = new ResRoomDTO.Type();
            type.setId(room.getRoomType().getId());
            type.setName(room.getRoomType().getName());
            dto.setId(room.getId());
            dto.setName(room.getName());
            dto.setType(type);
            dto.setActive(room.isActive());
            return dto;
        }).collect(Collectors.toList());
    }

    public List<RoomType> fetchAllRoomType() {
        return this.roomTypeRepository.findAll();
    }

    public Room createRoom(ReqCreateRoomDTO dto) throws IdInvalidException {
        Room room = new Room();
        long cinemaId = dto.getCinemaId();
        List<Room> rooms = this.roomRepository.findByCinema_Id(cinemaId);
        boolean isNameExists = rooms.stream().anyMatch(r -> r.getName().equals(dto.getName()));
        if (isNameExists) {
            throw new IdInvalidException("Tên phòng đã tồn tại, vui lòng chọn tên khác!");
        }
        Optional<Cinema> cinemaOptional = this.cinemaService.findById(cinemaId);
        Optional<RoomType> roomTypeOptional = this.roomTypeRepository.findById(dto.getRoomTypeId());
        if (roomTypeOptional.isPresent() && cinemaOptional.isPresent()) {
            room.setName(dto.getName());
            room.setRoomType(roomTypeOptional.get());
            room.setCinema(cinemaOptional.get());
            this.roomRepository.save(room);

            SeatType defaultSeatType = seatTypeRepository.findById(1L)
                    .orElseThrow(() -> new RuntimeException("SeatType Normal không tồn tại!"));

            List<Seat> seats = new ArrayList<>();
            for (int r = 0; r < dto.getRows(); r++) {
                char rowChar = (char) ('A' + r); // A, B, C...
                for (int c = 1; c <= dto.getCols(); c++) {
                    Seat seat = Seat.builder()
                            .name(rowChar + String.valueOf(c)) // A1, A2...
                            .room(room)
                            .seatType(defaultSeatType) // default Normal seat
                            .active(true)
                            .build();
                    seats.add(seat);
                }
            }

            seatRepository.saveAll(seats);
            room.setSeats(seats);

        }
        return room;
    }

    public Optional<Room> findById(Long id) {
        return this.roomRepository.findById(id);
    }

    public Room changeStatus(Room room) {
        if (room.isActive()) {
            room.setActive(false);
        } else {
            room.setActive(true);
        }
        this.roomRepository.save(room);
        return room;
    }

    public ResRoomDTO updateRoom(ReqRoomDTO dto) throws IdInvalidException {
        Optional<Room> roomOptional = this.roomRepository.findById(dto.getId());
        Room room = roomOptional.orElse(null);
        if (room != null) {
            boolean isNameExist = false;
            if (!dto.getName().equals(room.getName())) {
                Cinema cinema = room.getCinema();
                isNameExist = cinema.getRooms().stream().anyMatch(r -> r.getName().equals(dto.getName()));
            }
            if (isNameExist) {
                throw new IdInvalidException("Tên phòng đã tồn tại!");
            }
            room.setName(dto.getName());
            room.setRoomType(this.roomTypeRepository.findById(dto.getTypeId()).get());
            this.roomRepository.save(room);
        }
        ResRoomDTO resRoomDTO = new ResRoomDTO();
        ResRoomDTO.Type type = new ResRoomDTO.Type();
        type.setId(room.getRoomType().getId());
        type.setName(room.getRoomType().getName());
        resRoomDTO.setId(room.getId());
        resRoomDTO.setName(room.getName());
        resRoomDTO.setType(type);
        resRoomDTO.setActive(room.isActive());
        return resRoomDTO;
    }
}
