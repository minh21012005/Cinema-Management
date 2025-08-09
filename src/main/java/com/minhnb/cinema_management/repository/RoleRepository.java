package com.minhnb.cinema_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.minhnb.cinema_management.domain.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
