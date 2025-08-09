package com.minhnb.cinema_management.domain;

import java.util.List;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SeatType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;        // Thường, VIP, Đôi, Sweetbox,...
    private Double priceExtra;  // Giá cộng thêm so với vé thường

    @OneToMany(mappedBy = "seatType", cascade = CascadeType.ALL)
    private List<Seat> seats;
}


