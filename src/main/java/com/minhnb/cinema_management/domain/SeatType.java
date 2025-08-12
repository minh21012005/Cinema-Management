package com.minhnb.cinema_management.domain;

import java.util.List;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "seat_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // Thường, VIP, Đôi, Sweetbox...

    @Column(nullable = false)
    private Double priceExtra; // Giá cộng thêm so với vé thường

    @OneToMany(mappedBy = "seatType", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Seat> seats;
}
