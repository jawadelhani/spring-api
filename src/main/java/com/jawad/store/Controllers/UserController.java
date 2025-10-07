package com.jawad.store.Controllers;

import com.jawad.store.dtos.UserDto;
import com.jawad.store.entities.User;
import com.jawad.store.mappers.UserMapper;
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
    private final UserMapper userMapper;


    @GetMapping
    public Iterable<UserDto> getAllUsers(){
        return userRepository.findAll()
                .stream()
                .map(user -> userMapper.toDto(user))
                .toList();

    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id){
        var u= userRepository.findById(id).orElse(null);
        if (u==null){
            return ResponseEntity.notFound().build();
        }
        var userDto=new UserDto(u.getId(),u.getName(),u.getEmail());
        return ResponseEntity.ok(userDto);
    }
}
