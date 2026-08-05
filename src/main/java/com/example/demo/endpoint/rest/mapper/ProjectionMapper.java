package com.example.demo.endpoint.rest.mapper;

import com.example.demo.model.Projection;
import com.example.demo.model.dto.ProjectionCreateRequest;
import com.example.demo.model.dto.ProjectionResponse;
import com.example.demo.repository.model.JProjection;
import org.springframework.stereotype.Component;

@Component
public class ProjectionMapper {

  public Projection toDomain(ProjectionCreateRequest req) {
    return Projection.builder()
        .datetime(req.datetime())
        .seatPrice(req.seatPrice())
        .roomId(req.roomId())
        .movieId(req.movieId())
        .build();
  }

  public Projection toDomain(JProjection jProjection) {
    return Projection.builder()
        .id(jProjection.getId())
        .datetime(jProjection.getDatetime())
        .seatPrice(jProjection.getSeatPrice())
        .roomId(jProjection.getRoom() == null ? null : jProjection.getRoom().getId())
        .movieId(jProjection.getMovie() == null ? null : jProjection.getMovie().getId())
        .build();
  }

  public ProjectionResponse toResponse(Projection projection) {
    return ProjectionResponse.builder()
        .id(projection.id())
        .datetime(projection.datetime())
        .seatPrice(projection.seatPrice())
        .roomId(projection.roomId())
        .movieId(projection.movieId())
        .build();
  }

  public JProjection toJpa(Projection projection) {
    JProjection jProjection = new JProjection();
    jProjection.setId(projection.id());
    jProjection.setDatetime(projection.datetime());
    jProjection.setSeatPrice(projection.seatPrice());
    return jProjection;
  }
}
