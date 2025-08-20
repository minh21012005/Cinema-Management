package com.minhnb.cinema_management.service.manager;

import com.minhnb.cinema_management.domain.Cinema;
import com.minhnb.cinema_management.domain.User;
import com.minhnb.cinema_management.domain.response.ResUserDTO;
import com.minhnb.cinema_management.domain.response.ResultPaginationDTO;
import com.minhnb.cinema_management.repository.manager.CinemaRepository;
import com.minhnb.cinema_management.service.specification.CinemaSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CinemaService {
    private final CinemaRepository cinemaRepository;

    public CinemaService(CinemaRepository cinemaRepository) {
        this.cinemaRepository = cinemaRepository;
    }

    public ResultPaginationDTO fetchAllCinema(String name, Pageable pageable){
        Page<Cinema> pageCinema = this.cinemaRepository.findAll(
                CinemaSpecification.findCinemaWithFilters(name),
                pageable);
        ResultPaginationDTO rs = new ResultPaginationDTO();
        ResultPaginationDTO.Meta mt = new ResultPaginationDTO.Meta();

        mt.setPage(pageable.getPageNumber() + 1);
        mt.setPageSize(pageable.getPageSize());

        mt.setPages(pageCinema.getTotalPages());
        mt.setTotal(pageCinema.getTotalElements());

        rs.setMeta(mt);

        List<Cinema> listCinema = pageCinema.getContent().stream().collect(Collectors.toList());

        rs.setResult(listCinema);

        return rs;
    }

    public boolean existsByName(String name){
        return this.cinemaRepository.existsByName(name);
    }

    public boolean existsByAddress(String address){
        return this.cinemaRepository.existsByAddress(address);
    }

    public boolean existsByPhone(String phone){
        return this.cinemaRepository.existsByPhone(phone);
    }

    public Cinema createCinema(Cinema cinema){
        return this.cinemaRepository.save(cinema);
    }

    public Cinema changeStatusOfCinema(long id) {
        Cinema cinema = this.cinemaRepository.findById(id).orElse(null);
        if (cinema != null) {
            if (cinema.isActive()) {
                cinema.setActive(false);
            } else {
                cinema.setActive(true);
            }
            this.cinemaRepository.save(cinema);
        }
        return cinema;
    }
}
