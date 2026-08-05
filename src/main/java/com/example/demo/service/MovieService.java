package com.example.demo.service;

import com.example.demo.endpoint.rest.mapper.MovieMapper;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.Movie;
import com.example.demo.model.dto.MovieCreateRequest;
import com.example.demo.model.dto.MovieResponse;
import com.example.demo.repository.MovieRepository;
import com.example.demo.repository.model.JMovie;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieService {

  private final MovieRepository movieRepository;
  private final MovieMapper movieMapper;

  public MovieResponse create(MovieCreateRequest request) {
    Movie movie = movieMapper.toDomain(request);
    JMovie jMovie = movieMapper.toJpa(movie);
    JMovie saved = movieRepository.save(jMovie);
    return movieMapper.toResponse(movieMapper.toDomain(saved));
  }

  public List<MovieResponse> getAll() {
    return movieRepository.findAll().stream()
        .map(movieMapper::toDomain)
        .map(movieMapper::toResponse)
        .toList();
  }

  public MovieResponse getById(UUID id) {
    JMovie jMovie =
        movieRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Movie not found: " + id));
    return movieMapper.toResponse(movieMapper.toDomain(jMovie));
  }

  public MovieResponse update(UUID id, MovieCreateRequest request) {
    JMovie jMovie =
        movieRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Movie not found: " + id));
    jMovie.setTitle(request.title());
    jMovie.setGenres(request.genres());
    jMovie.setDescription(request.description());
    jMovie.setDuration(request.duration());
    JMovie updated = movieRepository.save(jMovie);
    return movieMapper.toResponse(movieMapper.toDomain(updated));
  }

  public void delete(UUID id) {
    if (!movieRepository.existsById(id)) {
      throw new NotFoundException("Movie not found: " + id);
    }
    movieRepository.deleteById(id);
  }
}
