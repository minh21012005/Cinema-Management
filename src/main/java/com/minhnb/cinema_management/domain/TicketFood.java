package com.minhnb.cinema_management.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ticket_foods")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketFood {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "ticket_id", nullable = false)
    private Ticket ticket;

    @ManyToOne(optional = false)
    @JoinColumn(name = "food_id", nullable = false)
    private Food food;

    @Column(nullable = false)
    private Integer quantity;
}
