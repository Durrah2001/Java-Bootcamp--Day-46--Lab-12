package org.example.blog_system.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.blog_system.ApiResponse.ApiResponse;
import org.example.blog_system.Model.MyUser;
import org.example.blog_system.Service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/user")
public class AuthController {

    private final AuthService authService;


    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid MyUser myUser){
        authService.register(myUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("User registered!"));
    }

    @GetMapping("/get/users")
    public ResponseEntity getUsers(){

        return ResponseEntity.status(200).body(authService.getUsers());
    }

    @DeleteMapping("/delete/user/{userID}")
    public ResponseEntity deleteUser(@PathVariable Integer userID) {
        authService.deleteUser(userID);
        return ResponseEntity.ok("User deleted successfully");
    }


    @PutMapping("/update/user/{userId}")
    public ResponseEntity updateUser(
            @PathVariable Integer userId,
            @RequestBody MyUser updatedUser,
            @AuthenticationPrincipal MyUser loggedInUser) {

        authService.updateUser(userId, updatedUser, loggedInUser.getUsername());

        return ResponseEntity.status(200).body(new ApiResponse("User updated successfully!"));
    }



}
