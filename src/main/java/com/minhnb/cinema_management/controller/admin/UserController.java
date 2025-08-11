package com.minhnb.cinema_management.controller.admin;

import com.minhnb.cinema_management.domain.User;
import com.minhnb.cinema_management.domain.response.ResUserDTO;
import com.minhnb.cinema_management.domain.response.ResultPaginationDTO;
import com.minhnb.cinema_management.service.UserService;
import com.minhnb.cinema_management.util.annotation.ApiMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.turkraft.springfilter.boot.Filter;

@RestController
@RequestMapping("/api/v1")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    @ApiMessage("fetch all users")
    public ResponseEntity<ResultPaginationDTO> getAllUser(
            @Filter Specification<User> spec,
            Pageable pageable) {

        return ResponseEntity.status(HttpStatus.OK).body(
                this.userService.fetchAllUser(spec, pageable));
    }

    @PutMapping("/users/change-status/{id}")
    @ApiMessage("Change status of user")
    public ResponseEntity<ResUserDTO> changeStatus(@PathVariable long id) {
        ResUserDTO resUserDTO = this.userService.changeStatusOfUser(id);
        return ResponseEntity.status(HttpStatus.OK).body(resUserDTO);
    }

    @GetMapping("/users/{id}")
    @ApiMessage("Fetch User")
    public ResponseEntity<ResUserDTO> getUser(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.fetchUserById(id));
    }
}
