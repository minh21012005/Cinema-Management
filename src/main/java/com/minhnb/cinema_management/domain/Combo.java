package com.minhnb.cinema_management.domain;

import java.util.List;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Combo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double price;
    private String description;

    @ManyToMany
    @JoinTable(name = "combo_food", joinColumns = @JoinColumn(name = "combo_id"), inverseJoinColumns = @JoinColumn(name = "food_id"))
    private List<Food> foods;
}
