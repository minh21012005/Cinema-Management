package com.minhnb.cinema_management.controller.admin;

import com.minhnb.cinema_management.domain.User;
import com.minhnb.cinema_management.domain.request.CreateUserRequest;
import com.minhnb.cinema_management.domain.response.ResCreateUserDTO;
import com.minhnb.cinema_management.domain.response.ResUserDTO;
import com.minhnb.cinema_management.domain.response.ResultPaginationDTO;
import com.minhnb.cinema_management.service.UserService;
import com.minhnb.cinema_management.util.annotation.ApiMessage;
import com.minhnb.cinema_management.util.error.IdInvalidException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.turkraft.springfilter.boot.Filter;

@RestController
@RequestMapping("/api/v1")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
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

    @PostMapping("/users/register")
    @ApiMessage("Register a new user")
    public ResponseEntity<ResCreateUserDTO> register(@Valid @RequestBody CreateUserRequest postManUser) throws IdInvalidException {
        boolean isEmailExist = this.userService.isEmailExist(postManUser.getEmail());
        if (isEmailExist) {
            throw new IdInvalidException(
                    "Email " + postManUser.getEmail() + "đã tồn tại, vui lòng sử dụng email khác.");
        }

        boolean isPhoneExist = this.userService.isPhoneExist(postManUser.getPhone());
        if (isPhoneExist) {
            throw new IdInvalidException(
                    "Số điện thoại " + postManUser.getPhone() + " đã tồn tại, vui lòng sử dụng số điện thoại khác.");
        }

        String hashPassword = this.passwordEncoder.encode(postManUser.getPassword());
        postManUser.setPassword(hashPassword);
        User ericUser = this.userService.handleCreateUserForManager(postManUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.convertToResCreateUserDTO(ericUser));
    }
}
