package com.example.demo.service;

import com.example.demo.endpoint.rest.mapper.ProjectionMapper;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.Projection;
import com.example.demo.model.dto.ProjectionCreateRequest;
import com.example.demo.model.dto.ProjectionResponse;
import com.example.demo.repository.MovieRepository;
import com.example.demo.repository.ProjectionRepository;
import com.example.demo.repository.RoomRepository;
import com.example.demo.repository.model.JMovie;
import com.example.demo.repository.model.JProjection;
import com.example.demo.repository.model.JRoom;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectionService {

  private final ProjectionRepository projectionRepository;
  private final RoomRepository roomRepository;
  private final MovieRepository movieRepository;
  private final ProjectionMapper projectionMapper;

  public ProjectionResponse create(ProjectionCreateRequest request) {
    Projection projection = projectionMapper.toDomain(request);
    JProjection jProjection = projectionMapper.toJpa(projection);
    jProjection.setRoom(getRoom(request.roomId()));
    jProjection.setMovie(getMovie(request.movieId()));
    JProjection saved = projectionRepository.save(jProjection);
    return projectionMapper.toResponse(projectionMapper.toDomain(saved));
  }

  public List<ProjectionResponse> getAll() {
    return projectionRepository.findAll().stream()
        .map(projectionMapper::toDomain)
        .map(projectionMapper::toResponse)
        .toList();
  }

  public ProjectionResponse getById(UUID id) {
    JProjection jProjection =
        projectionRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Projection not found: " + id));
    return projectionMapper.toResponse(projectionMapper.toDomain(jProjection));
  }

  public ProjectionResponse update(UUID id, ProjectionCreateRequest request) {
    JProjection jProjection =
        projectionRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Projection not found: " + id));
    jProjection.setDatetime(request.datetime());
    jProjection.setSeatPrice(request.seatPrice());
    jProjection.setRoom(getRoom(request.roomId()));
    jProjection.setMovie(getMovie(request.movieId()));
    JProjection updated = projectionRepository.save(jProjection);
    return projectionMapper.toResponse(projectionMapper.toDomain(updated));
  }

  public void delete(UUID id) {
    if (!projectionRepository.existsById(id)) {
      throw new NotFoundException("Projection not found: " + id);
    }
    projectionRepository.deleteById(id);
  }

  private JRoom getRoom(UUID roomId) {
    return roomRepository
        .findById(roomId)
        .orElseThrow(() -> new NotFoundException("Room not found: " + roomId));
  }

  private JMovie getMovie(UUID movieId) {
    return movieRepository
        .findById(movieId)
        .orElseThrow(() -> new NotFoundException("Movie not found: " + movieId));
  }
}
