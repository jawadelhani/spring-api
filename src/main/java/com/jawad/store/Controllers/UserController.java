package com.jawad.store.Controllers;

import com.jawad.store.dtos.ChangePsswordRequest;
import com.jawad.store.dtos.RegisterUserRequest;
import com.jawad.store.dtos.UpdateUserRequest;
import com.jawad.store.dtos.UserDto;
import com.jawad.store.mappers.UserMapper;
import com.jawad.store.repositories.UserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;
    private final UserMapper userMapper;


    @GetMapping
    public Iterable<UserDto> getAllUsers(@RequestParam(required = false,defaultValue = "",name = "name") String sort){
        if(!Set.of("name","email").contains(sort)){
            sort = "name";
        }
        return userRepository.findAll(Sort.by(sort))
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id){
        var u= userRepository.findById(id).orElse(null);
        if (u==null){
            return ResponseEntity.notFound().build();
        }
        var userDto=userMapper.toDto(u);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(
            @Valid @RequestBody RegisterUserRequest request,
            UriComponentsBuilder uriBuilder){
        var user=userMapper.toEntity(request);
        userRepository.save(user);
        var userDto=userMapper.toDto(user);
        //for responseEntity status 201 ,url where added /{id},not abligatory
        var uri=uriBuilder.path("/users/{id}").buildAndExpand(userDto.getId()).toUri();
        return ResponseEntity.created(uri).body(userDto);
    }
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable(name = "id") Long id,
            @RequestBody UpdateUserRequest request){
        //user found as entity
        var user=userRepository.findById(id).orElse(null);
        if (user==null){
            return ResponseEntity.notFound().build();
        }

        //user.setName(request.getName());
        //user.setEmail(request.getEmail());
        userMapper.updateUser(request, user);

        //we save entities
        userRepository.save(user);
        //we return Dto
        return ResponseEntity.ok(userMapper.toDto(user));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable(name = "id") Long id){
        var user=userRepository.findById(id).orElse(null);
        if (user==null){
            return ResponseEntity.notFound().build();
        }
        userRepository.delete(user);
        return ResponseEntity.noContent().build(); //204 status
    }

    @PostMapping("/{id}/change-password")
    public ResponseEntity<Void> changePassword(
            @PathVariable(name = "id") Long id,
            @RequestBody ChangePsswordRequest request){
        var user=userRepository.findById(id).orElse(null);
        if (user==null){
            return ResponseEntity.notFound().build();
        }
        if(!user.getPassword().equals(request.getOldPassword())){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        user.setPassword(request.getNewPassword());
        userRepository.save(user);

        //204 code status
        return ResponseEntity.noContent().build();
    }


}
