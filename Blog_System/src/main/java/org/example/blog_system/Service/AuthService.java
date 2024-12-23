package org.example.blog_system.Service;

import lombok.RequiredArgsConstructor;
import org.example.blog_system.ApiResponse.ApiException;
import org.example.blog_system.Model.MyUser;
import org.example.blog_system.Repository.AuthRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;



    public void register(MyUser myUser){

        myUser.setRole("USER");

        String passwordHash = new BCryptPasswordEncoder().encode(myUser.getPassword());
        myUser.setPassword(passwordHash);
        authRepository.save(myUser);
    }


    public List<MyUser> getUsers(){

        return authRepository.findAll();

    }

    public void updateUser(Integer userId, MyUser updatedUser, String loggedInUsername) {
        MyUser currentUser = authRepository.findMyUserById(userId);
        if (currentUser == null) {
            throw new ApiException("User not found!");
        }

        if (!currentUser.getUsername().equals(loggedInUsername)) {
            throw new ApiException("You can only update your own profile!");
        }

        currentUser.setUsername(updatedUser.getUsername());
        currentUser.setPassword(new BCryptPasswordEncoder().encode(updatedUser.getPassword()));
        authRepository.save(currentUser);
    }


    public void deleteUser(Integer userId) {
        MyUser user = authRepository.findById(userId)
                .orElseThrow(() -> new ApiException("User not found!"));

        authRepository.delete(user);
    }






}
