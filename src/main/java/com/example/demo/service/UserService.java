package com.example.demo.service;

import com.example.demo.endpoint.rest.mapper.UserMapper;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.User;
import com.example.demo.model.dto.UserCreateRequest;
import com.example.demo.model.dto.UserResponse;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.model.JUser;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;

  public UserResponse create(UserCreateRequest request) {
    User user = userMapper.toDomain(request);
    JUser jUser = userMapper.toJpa(user);
    JUser saved = userRepository.save(jUser);
    return userMapper.toResponse(userMapper.toDomain(saved));
  }

  public List<UserResponse> getAll() {
    return userRepository.findAll().stream()
        .map(userMapper::toDomain)
        .map(userMapper::toResponse)
        .toList();
  }

  public UserResponse getById(UUID id) {
    JUser jUser =
        userRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("User not found: " + id));
    return userMapper.toResponse(userMapper.toDomain(jUser));
  }

  public UserResponse update(UUID id, UserCreateRequest request) {
    JUser jUser =
        userRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("User not found: " + id));
    jUser.setFirstName(request.firstName());
    jUser.setLastName(request.lastName());
    jUser.setBirthdate(request.birthdate());
    jUser.setEmail(request.email());
    jUser.setPassword(request.password());
    jUser.setPhone(request.phone());
    jUser.setRole(request.role());
    JUser updated = userRepository.save(jUser);
    return userMapper.toResponse(userMapper.toDomain(updated));
  }

  public void delete(UUID id) {
    if (!userRepository.existsById(id)) {
      throw new NotFoundException("User not found: " + id);
    }
    userRepository.deleteById(id);
  }
}
