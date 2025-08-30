package com.minhnb.cinema_management.domain;

import java.util.List;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "seats",
        uniqueConstraints = @UniqueConstraint(columnNames = { "room_id", "row_index", "col_index" })
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Ghế sẽ xác định theo row/col thay vì chỉ name
    @Column(nullable = false)
    private Integer rowIndex;

    @Column(nullable = false)
    private Integer colIndex;

    // Mã ghế (A1, B5...), FE có thể generate
    private String name;

    @Column(nullable = false)
    private boolean active = true; // true = dùng được, false = không sử dụng

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @ManyToOne
    @JoinColumn(name = "seat_type_id", nullable = false)
    private SeatType seatType;

    @OneToMany(mappedBy = "seat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ticket> tickets;
}
