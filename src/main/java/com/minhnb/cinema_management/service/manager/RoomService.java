package com.minhnb.cinema_management.service.manager;

import com.minhnb.cinema_management.domain.Room;
import com.minhnb.cinema_management.domain.response.ResRoomDTO;
import com.minhnb.cinema_management.repository.manager.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {
    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
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
            dto.setType(room.getType());
            dto.setActive(room.isActive());
            return dto;
        }).collect(Collectors.toList());
    }

}
