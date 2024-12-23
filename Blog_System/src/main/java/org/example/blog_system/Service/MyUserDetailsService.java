package org.example.blog_system.Service;

import lombok.RequiredArgsConstructor;
import org.example.blog_system.ApiResponse.ApiException;
import org.example.blog_system.Model.MyUser;
import org.example.blog_system.Repository.AuthRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final AuthRepository authRepository;


    //This method can not return null
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        MyUser myUser = authRepository.findMyUserByUsername(username); //check from DB

        if(myUser== null)
            throw new ApiException("Wrong username/password!");

        return myUser;
        //implement in myUser


    }






}
