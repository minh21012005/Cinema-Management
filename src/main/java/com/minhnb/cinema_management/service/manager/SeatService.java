package com.minhnb.cinema_management.service.manager;

import org.springframework.stereotype.Service;

import com.minhnb.cinema_management.repository.manager.SeatRepository;

@Service
public class SeatService {
    private final SeatRepository seatRepository;

    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }
}
