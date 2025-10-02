package com.jawad.store.Controllers;

import com.jawad.store.entities.User;
import com.jawad.store.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;


    @GetMapping
    public Iterable<User> getAllUsers(){
        return userRepository.findAll();

    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id){
        var u= userRepository.findById(id).orElse(null);
        if (u==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(u);
    }
}
