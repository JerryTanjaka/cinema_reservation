package com.example.demo.endpoint.rest.mapper;

import com.example.demo.model.Reservation;
import com.example.demo.model.dto.ReservationCreateRequest;
import com.example.demo.model.dto.ReservationResponse;
import com.example.demo.repository.model.JReservation;
import com.example.demo.repository.model.JSeat;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class ReservationMapper {

  public Reservation toDomain(ReservationCreateRequest req) {
    return Reservation.builder()
        .projectionId(req.projectionId())
        .userId(req.userId())
        .seatIds(req.seatIds())
        .build();
  }

  public Reservation toDomain(JReservation jReservation) {
    List<UUID> seatIds =
        jReservation.getSeats() == null
            ? List.of()
            : jReservation.getSeats().stream().map(JSeat::getId).toList();
    return Reservation.builder()
        .id(jReservation.getId())
        .createdAt(jReservation.getCreatedAt())
        .projectionId(
            jReservation.getProjection() == null ? null : jReservation.getProjection().getId())
        .userId(jReservation.getUser() == null ? null : jReservation.getUser().getId())
        .seatIds(seatIds)
        .build();
  }

  public ReservationResponse toResponse(Reservation reservation) {
    return ReservationResponse.builder()
        .id(reservation.id())
        .createdAt(reservation.createdAt())
        .projectionId(reservation.projectionId())
        .userId(reservation.userId())
        .seatIds(reservation.seatIds())
        .build();
  }

  public JReservation toJpa(Reservation reservation) {
    JReservation jReservation = new JReservation();
    jReservation.setId(reservation.id());
    jReservation.setCreatedAt(reservation.createdAt());
    return jReservation;
  }
}
