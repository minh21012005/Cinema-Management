package com.minhnb.cinema_management.repository.manager;

import com.minhnb.cinema_management.domain.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CinemaRepository extends JpaRepository<Cinema, Long>, JpaSpecificationExecutor<Cinema> {
    boolean existsByName(String name);

    boolean existsByAddress(String address);

    boolean existsByPhone(String phone);
}
