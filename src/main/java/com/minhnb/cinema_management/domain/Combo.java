package com.minhnb.cinema_management.domain;

import java.util.List;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "combos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Combo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // Tên combo

    @Column(nullable = false)
    private Double price; // Giá combo

    private String description; // Mô tả combo

    @Column(nullable = false)
    private boolean available = true; // Còn bán hay không

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "combo_food",
            joinColumns = @JoinColumn(name = "combo_id"),
            inverseJoinColumns = @JoinColumn(name = "food_id")
    )
    private List<Food> foods; // Danh sách món ăn trong combo
}
