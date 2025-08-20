package com.minhnb.cinema_management.repository.admin;

import org.springframework.data.jpa.repository.JpaRepository;

import com.minhnb.cinema_management.domain.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
