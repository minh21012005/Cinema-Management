package com.minhnb.cinema_management.domain.response;

import com.minhnb.cinema_management.domain.SeatType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResSeatDTO {
    private Long id;
    private String name; // Ví dụ: A1, B5...
    private boolean active;
    private SeatType seatType;
}
