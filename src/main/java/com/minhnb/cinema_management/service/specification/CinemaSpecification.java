package com.minhnb.cinema_management.service.specification;

import com.minhnb.cinema_management.domain.Cinema;
import org.springframework.data.jpa.domain.Specification;

public class CinemaSpecification {
    public static Specification<Cinema> findCinemaWithFilters(String name) {
        Specification<Cinema> spec = (root, query, cb) -> cb.conjunction(); // mặc định luôn true

        if (name != null && !name.isEmpty()) {
            spec = spec.and(
                    (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
        }

        return spec;
    }
}
