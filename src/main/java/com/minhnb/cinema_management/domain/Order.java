package com.minhnb.cinema_management.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Người mua
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User customer;

    // Danh sách vé thuộc order này
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<Ticket> tickets;

    // Danh sách đồ ăn mua kèm (combo hoặc food)
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderFood> orderFoods;

    // Tổng tiền
    @Column(nullable = false)
    private Double totalAmount;

    // Thời gian đặt
    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // Trạng thái đơn hàng
    @Column(nullable = false)
    private boolean paid = false; // true = đã thanh toán, false = chưa
}
