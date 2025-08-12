package com.minhnb.cinema_management.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_foods")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderFood {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Thuộc về đơn hàng nào
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    // Món ăn
    @ManyToOne
    @JoinColumn(name = "food_id")
    private Food food;

    // Số lượng
    @Column(nullable = false)
    private Integer quantity;
}
