package com.example.demo.endpoint.rest.mapper;

import com.example.demo.model.Movie;
import com.example.demo.model.dto.MovieCreateRequest;
import com.example.demo.model.dto.MovieResponse;
import com.example.demo.repository.model.JMovie;
import org.springframework.stereotype.Component;

@Component
public class MovieMapper {

  public Movie toDomain(MovieCreateRequest req) {
    return Movie.builder()
        .title(req.title())
        .genres(req.genres())
        .description(req.description())
        .duration(req.duration())
        .build();
  }

  public Movie toDomain(JMovie jMovie) {
    return Movie.builder()
        .id(jMovie.getId())
        .title(jMovie.getTitle())
        .genres(jMovie.getGenres())
        .description(jMovie.getDescription())
        .duration(jMovie.getDuration())
        .build();
  }

  public MovieResponse toResponse(Movie movie) {
    return MovieResponse.builder()
        .id(movie.id())
        .title(movie.title())
        .genres(movie.genres())
        .description(movie.description())
        .duration(movie.duration())
        .build();
  }

  public JMovie toJpa(Movie movie) {
    JMovie jMovie = new JMovie();
    jMovie.setId(movie.id());
    jMovie.setTitle(movie.title());
    jMovie.setGenres(movie.genres());
    jMovie.setDescription(movie.description());
    jMovie.setDuration(movie.duration());
    return jMovie;
  }
}
