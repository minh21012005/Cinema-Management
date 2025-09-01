package com.minhnb.cinema_management.service.manager;

import com.minhnb.cinema_management.domain.Room;
import com.minhnb.cinema_management.domain.Seat;
import com.minhnb.cinema_management.domain.SeatType;
import com.minhnb.cinema_management.domain.request.ReqSeatDTO;
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

    public List<ResSeatDTO> fetchSeatsByRoom(Long id) throws IdInvalidException {
        Optional<Room> roomOptional = this.roomRepository.findById(id);
        if (roomOptional.isEmpty()) {
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

    public List<SeatType> fetchAllSeatTypes() {
        return this.seatTypeRepository.findAll();
    }

    public Optional<Seat> findById(Long id) {
        return this.seatRepository.findById(id);
    }

    public ResSeatDTO changeSeatStatus(Seat seat) {
        seat.setActive(!seat.isActive());
        seatRepository.save(seat);
        ResSeatDTO dto = new ResSeatDTO();
        dto.setId(seat.getId());
        dto.setName(seat.getName());
        dto.setActive(seat.isActive());
        dto.setSeatType(seat.getSeatType());
        return dto;
    }

    public Optional<SeatType> findSeatTypeById(Long id) {
        return this.seatTypeRepository.findById(id);
    }

    public ResSeatDTO changeSeatType(Seat seat, SeatType seatType) throws IdInvalidException {
        if (!seat.getSeatType().getName().equals("Đôi")) {
            if (!seatType.getName().equals("Đôi")) {
                seat.setSeatType(seatType);
            } else {
                int row = seat.getRowIndex();
                int col = seat.getColIndex();
                Long roomId = seat.getRoom().getId();
                Seat nextSeat = seatRepository.findByRowIndexAndColIndexAndRoomId
                        (row, col + 1, roomId).orElse(null);
                if (nextSeat != null && nextSeat.getSeatType().getName().equals("Đôi")) {
                    throw new IdInvalidException("Ghế bên cạnh đang là ghế đôi, hãy chuyển sang loại khác trước!");
                }
                if (nextSeat == null || !nextSeat.isActive()) {
                    seat.setName(seat.getName() + "-" + (col + 2));
                    seat.setSeatType(seatType);
                } else {
                    throw new IdInvalidException("Ghế bên cạnh đang hoạt động, không thể đổi sang loại ghế đôi!");
                }
            }
        } else {
            if (!seatType.getName().equals("Đôi")) {
                int index = seat.getName().indexOf("-");
                seat.setName(seat.getName().substring(0, index));
            }
            seat.setSeatType(seatType);
        }
        seat = seatRepository.save(seat);
        ResSeatDTO dto = new ResSeatDTO();
        dto.setId(seat.getId());
        dto.setName(seat.getName());
        dto.setActive(seat.isActive());
        dto.setSeatType(seat.getSeatType());
        return dto;
    }

    public ResSeatDTO createSeat(ReqSeatDTO dto) throws IdInvalidException {
        Seat rightSeat = this.seatRepository.findByRowIndexAndColIndexAndRoomId(
                dto.getRow(), dto.getCol(), dto.getRoomId()).orElse(null);
        Seat prevSeat = this.seatRepository.findByRowIndexAndColIndexAndRoomId(
                dto.getRow(), dto.getCol() - 1, dto.getRoomId()).orElse(null);
        Seat nextSeat = this.seatRepository.findByRowIndexAndColIndexAndRoomId(
                dto.getRow(), dto.getCol() + 1, dto.getRoomId()).orElse(null);
        SeatType seatType = this.findSeatTypeById(dto.getTypeId())
                .orElseThrow(() -> new IdInvalidException("Loại ghế không tồn tại!"));
        if (rightSeat != null) {
            throw new IdInvalidException("Vị trí này đã có ghế, không thể tạo!");
        }
        if (prevSeat != null && prevSeat.getSeatType().getName().equals("Đôi")) {
            throw new IdInvalidException("Vị trí này đã có ghế, không thể tạo!");
        }
        if (nextSeat != null && nextSeat.isActive() && seatType.getName().equals("Đôi")) {
            throw new IdInvalidException("Vị trí này đã có ghế, không thể tạo!");
        }
        if (nextSeat.getSeatType().getName().equals("Đôi") && seatType.getName().equals("Đôi")) {
            throw new IdInvalidException("Ghế bên cạnh đang là ghế đôi, hãy chuyển sang loại khác trước!");
        }
        Room room = this.roomRepository.findById(dto.getRoomId()).
                orElseThrow(() -> new IdInvalidException("Room không tồn tại!"));

        Seat seat = getSeat(dto, seatType, room);
        this.seatRepository.save(seat);
        return new ResSeatDTO(seat.getId(), seat.getName(), seat.isActive(), seat.getSeatType());
    }

    private static Seat getSeat(ReqSeatDTO dto, SeatType seatType, Room room) {
        char rowChar = (char) ('A' + dto.getRow());

        int colNumber = dto.getCol() + 1; // vì col bắt đầu từ 0

        String seatName;
        if (seatType.getName().equals("Đôi")) {
            seatName = rowChar + String.valueOf(colNumber) + "-" + (colNumber + 1);
        } else {
            seatName = rowChar + String.valueOf(colNumber);
        }

        Seat seat = new Seat();
        seat.setRowIndex(dto.getRow());
        seat.setColIndex(dto.getCol());
        seat.setSeatType(seatType);
        seat.setRoom(room);
        seat.setName(seatName);
        return seat;
    }
}
