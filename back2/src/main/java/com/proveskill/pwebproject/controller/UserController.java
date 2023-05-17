package com.proveskill.pwebproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Optional;

import com.proveskill.pwebproject.constants.PathConstants;
import com.proveskill.pwebproject.model.User;
import com.proveskill.pwebproject.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(PathConstants.BASE_PATH)
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping(PathConstants.USERS)
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = this.service.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping(PathConstants.USERS + "/{id}")
    public ResponseEntity<Optional<User>> getUserById(@PathVariable("id") Integer id, Model model) {
        return ResponseEntity.ok(this.service.findById(id));
    }

    @PutMapping(value = PathConstants.USERS, consumes = "application/json", produces = "application/json")
    public ResponseEntity<User> saveUser(@RequestBody @Valid User userDto) throws Exception {
        User createdUser = this.service.update(userDto);
        return ResponseEntity.ok(createdUser);
    }

    @DeleteMapping(PathConstants.USERS + "/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Integer id) {
        this.service.delete(id);
        return ResponseEntity.noContent().build();
    }


  // @PostMapping("/refresh-token")
  // public void refreshToken(
  //     HttpServletRequest request,
  //     HttpServletResponse response
  // ) throws IOException {
  //   service.refreshToken(request, response);
  // }


}
