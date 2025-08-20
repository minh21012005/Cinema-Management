package com.minhnb.cinema_management.service.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.minhnb.cinema_management.domain.Role;
import com.minhnb.cinema_management.repository.admin.RoleRepository;

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

    public List<Role> fetchAllRole() {
        return this.roleRepository.findAll();
    }
}
