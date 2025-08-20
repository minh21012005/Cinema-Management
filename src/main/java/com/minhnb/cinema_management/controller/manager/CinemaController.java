package com.minhnb.cinema_management.controller.manager;

import com.minhnb.cinema_management.domain.Cinema;
import com.minhnb.cinema_management.domain.response.ResUserDTO;
import com.minhnb.cinema_management.domain.response.ResultPaginationDTO;
import com.minhnb.cinema_management.service.manager.CinemaService;
import com.minhnb.cinema_management.util.annotation.ApiMessage;
import com.minhnb.cinema_management.util.error.IdInvalidException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@PreAuthorize("hasRole('MANAGER')")
public class CinemaController {
    private final CinemaService cinemaService;

    public CinemaController(CinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }

    @GetMapping("/cinemas")
    public ResponseEntity<ResultPaginationDTO> fetchAllCinema(
            @RequestParam Optional<String> name,
            Pageable pageable) {

        String nameFilter = name.orElse(null);

        return ResponseEntity.status(HttpStatus.OK).body(this.cinemaService.fetchAllCinema(nameFilter, pageable));
    }

    @PostMapping("/cinemas")
    public ResponseEntity<Object> createCinema(@RequestBody Cinema cinema) throws IdInvalidException {
        boolean isNameExists = this.cinemaService.existsByName(cinema.getName());
        boolean isAddressExists = this.cinemaService.existsByAddress(cinema.getAddress());
        boolean isPhoneExists = this.cinemaService.existsByPhone(cinema.getPhone());

        if (isNameExists) {
            throw new IdInvalidException(
                    "Tên " + cinema.getName() + "đã tồn tại, vui lòng sử dụng tên khác.");
        }
        if (isAddressExists) {
            throw new IdInvalidException(
                    "Địa chỉ " + cinema.getAddress() + "đã tồn tại, vui lòng sử dụng địa chỉ khác.");
        }
        if (isPhoneExists) {
            throw new IdInvalidException(
                    "Số điện thoại " + cinema.getPhone() + "đã tồn tại, vui lòng sử dụng số điện thoại khác.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(this.cinemaService.createCinema(cinema));
    }

    @PutMapping("/cinemas/change-status/{id}")
    @ApiMessage("Change status of user")
    public ResponseEntity<Object> changeStatus(@PathVariable long id) {
        Cinema cinema = this.cinemaService.changeStatusOfCinema(id);
        return ResponseEntity.status(HttpStatus.OK).body(cinema);
    }
}
