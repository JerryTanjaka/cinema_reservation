package com.example.demo.endpoint.rest.mapper;

import com.example.demo.model.User;
import com.example.demo.model.dto.UserCreateRequest;
import com.example.demo.model.dto.UserResponse;
import com.example.demo.repository.model.JUser;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

  public User toDomain(UserCreateRequest req) {
    return User.builder()
        .firstName(req.firstName())
        .lastName(req.lastName())
        .birthdate(req.birthdate())
        .email(req.email())
        .password(req.password())
        .phone(req.phone())
        .role(req.role())
        .build();
  }

  public User toDomain(JUser jUser) {
    return User.builder()
        .id(jUser.getId())
        .firstName(jUser.getFirstName())
        .lastName(jUser.getLastName())
        .birthdate(jUser.getBirthdate())
        .email(jUser.getEmail())
        .password(jUser.getPassword())
        .phone(jUser.getPhone())
        .role(jUser.getRole())
        .build();
  }

  public UserResponse toResponse(User user) {
    return UserResponse.builder()
        .id(user.id())
        .firstName(user.firstName())
        .lastName(user.lastName())
        .birthdate(user.birthdate())
        .email(user.email())
        .phone(user.phone())
        .role(user.role())
        .build();
  }

  public JUser toJpa(User user) {
    JUser jUser = new JUser();
    jUser.setId(user.id());
    jUser.setFirstName(user.firstName());
    jUser.setLastName(user.lastName());
    jUser.setBirthdate(user.birthdate());
    jUser.setEmail(user.email());
    jUser.setPassword(user.password());
    jUser.setPhone(user.phone());
    jUser.setRole(user.role());
    return jUser;
  }
}
