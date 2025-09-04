package com.minhnb.cinema_management.service.manager;

import com.minhnb.cinema_management.domain.Category;
import com.minhnb.cinema_management.domain.Movie;
import com.minhnb.cinema_management.domain.response.ResMovieDTO;
import com.minhnb.cinema_management.domain.response.ResultPaginationDTO;
import com.minhnb.cinema_management.repository.manager.MovieRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public ResultPaginationDTO fetchAllMovies(Pageable pageable){

        Page<Movie> pageMovie = this.movieRepository.findAll(pageable);

        ResultPaginationDTO rs = new ResultPaginationDTO();
        ResultPaginationDTO.Meta mt = new ResultPaginationDTO.Meta();

        mt.setPage(pageable.getPageNumber() + 1);
        mt.setPageSize(pageable.getPageSize());

        mt.setPages(pageMovie.getTotalPages());
        mt.setTotal(pageMovie.getTotalElements());

        rs.setMeta(mt);

        List<ResMovieDTO> dtoList = pageMovie.getContent().stream().map(movie -> {
            ResMovieDTO dto = new ResMovieDTO();

            dto.setId(movie.getId());
            dto.setTitle(movie.getTitle());
            dto.setDescription(movie.getDescription());
            dto.setDurationInMinutes(movie.getDurationInMinutes());
            dto.setReleaseDate(movie.getReleaseDate());
            dto.setEndDate(movie.getEndDate());
            dto.setActive(movie.isActive());
            dto.setPoster(movie.getPoster());

            List<String> categories = new ArrayList<>();
            for(Category category: movie.getCategories()){
                categories.add(category.getName());
            }

            dto.setCategories(categories);
            return dto;
        }).collect(Collectors.toList());

        rs.setResult(dtoList);
        return rs;
    }
}
