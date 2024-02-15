package org.scaler.userservicemwfeve.controllers;

import org.scaler.userservicemwfeve.dtos.LoginRequestDto;
import org.scaler.userservicemwfeve.dtos.LogoutRequestDto;
import org.scaler.userservicemwfeve.dtos.SignUpRequestDto;
import org.scaler.userservicemwfeve.dtos.UserDto;
import org.scaler.userservicemwfeve.models.Token;
import org.scaler.userservicemwfeve.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public Token login(@RequestBody LoginRequestDto requestDto){
        return userService.login(requestDto.getEmail(), requestDto.getPassword());
    }

    @PostMapping("/signup")
    public UserDto signUp(@RequestBody SignUpRequestDto request) {
        String email = request.getEmail();
        String password = request.getPassword();
        String name = request.getName();

        return UserDto.from(userService.signUp(name, email, password));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequestDto requestDto) {
        userService.logout(requestDto.getToken());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/validate/{token}")
    public UserDto validateToken(@PathVariable("token") @NonNull String token) {
        return UserDto.from(userService.validateToken(token));
    }
}
