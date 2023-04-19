package com.proveskill.controller.api;

import com.proveskill.constants.PathConstants;
import com.proveskill.model.dto.UserDto;
import com.proveskill.service.user.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Collection;

@Controller
@Slf4j
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers(Model model) {
        List<UserDto> users = this.userService.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") long id, Model model) {
        UserDto user = this.userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping(value = PathConstants.USERS, consumes = "application/json", produces = "application/json")
    public ResponseEntity<UserDto> saveUser(@RequestBody @Valid UserDto userDto) throws Exception {
        UserDto createdUser = this.userService.create(userDto);
        return ResponseEntity.ok(createdUser);
    }

    @DeleteMapping(value = PathConstants.USERS + "/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") long id) {
        this.userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
