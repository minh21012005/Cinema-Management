package com.minhnb.cinema_management.service.manager;

import com.minhnb.cinema_management.domain.Cinema;
import com.minhnb.cinema_management.domain.Room;
import com.minhnb.cinema_management.domain.RoomType;
import com.minhnb.cinema_management.domain.request.ReqCreateRoomDTO;
import com.minhnb.cinema_management.domain.response.ResRoomDTO;
import com.minhnb.cinema_management.repository.manager.RoomRepository;
import com.minhnb.cinema_management.repository.manager.RoomTypeRepository;
import com.minhnb.cinema_management.util.error.IdInvalidException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final RoomTypeRepository roomTypeRepository;
    private final CinemaService cinemaService;

    public RoomService(RoomRepository roomRepository, RoomTypeRepository roomTypeRepository, CinemaService cinemaService) {
        this.roomRepository = roomRepository;
        this.roomTypeRepository = roomTypeRepository;
        this.cinemaService = cinemaService;
    }

    public List<ResRoomDTO> fetchAllRoom(Long cinemaId) {
        List<Room> rooms = this.roomRepository.findByCinema_Id(cinemaId);

        if (rooms.isEmpty()) {
            return Collections.emptyList(); // trả về list rỗng, không null
        }

        return rooms.stream().map(room -> {
            ResRoomDTO dto = new ResRoomDTO();
            dto.setId(room.getId());
            dto.setName(room.getName());
            dto.setType(room.getRoomType().getName());
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
        }
        return room;
    }

    public Optional<Room> findById(Long id){
        return this.roomRepository.findById(id);
    }

    public Room changeStatus(Room room){
        if(room.isActive()){
            room.setActive(false);
        }else {
            room.setActive(true);
        }
        this.roomRepository.save(room);
        return room;
    }
}
