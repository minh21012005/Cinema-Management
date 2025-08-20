package com.minhnb.cinema_management.controller.admin;

import com.minhnb.cinema_management.domain.Role;
import com.minhnb.cinema_management.service.admin.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/v1")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/roles")
    public ResponseEntity<Object> fetchAllRole(){
        return ResponseEntity.status(HttpStatus.OK).body(this.roleService.fetchAllRole());
    }
}
