package com.minhnb.cinema_management.domain.response;

import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResMovieDTO {

    private Long id;

    private String title;

    private String description;

    private int durationInMinutes;

    private LocalDate releaseDate;

    private LocalDate endDate;

    private boolean active;

    private String poster;

    // Chỉ lấy tên các category để trả về
    private List<String> categories;
}
