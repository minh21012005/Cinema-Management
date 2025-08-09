package com.minhnb.cinema_management.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.minhnb.cinema_management.domain.Role;
import com.minhnb.cinema_management.repository.RoleRepository;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(
            RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role fetchById(long id) {
        Optional<Role> roleOptional = this.roleRepository.findById(id);
        if (roleOptional.isPresent())
            return roleOptional.get();
        return null;
    }
}
