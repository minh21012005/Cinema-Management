package com.minhnb.cinema_management.controller.manager;

import com.minhnb.cinema_management.domain.response.ResMovieDTO;
import com.minhnb.cinema_management.domain.response.ResultPaginationDTO;
import com.minhnb.cinema_management.service.manager.MovieService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@PreAuthorize("hasRole('MANAGER')")
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/movies")
    public ResponseEntity<ResultPaginationDTO> fetchAllMovies(
            @RequestParam Optional<String> title,
            @RequestParam Optional<String> category,
            Pageable pageable
    ){
        return ResponseEntity.status(HttpStatus.OK).body(this.movieService.fetchAllMovies(pageable));
    }
}
