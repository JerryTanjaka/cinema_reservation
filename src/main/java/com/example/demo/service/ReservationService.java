package com.example.demo.service;

import com.example.demo.endpoint.rest.mapper.ReservationMapper;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.Reservation;
import com.example.demo.model.dto.ReservationCreateRequest;
import com.example.demo.model.dto.ReservationResponse;
import com.example.demo.repository.ProjectionRepository;
import com.example.demo.repository.ReservationRepository;
import com.example.demo.repository.SeatRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.model.JProjection;
import com.example.demo.repository.model.JReservation;
import com.example.demo.repository.model.JSeat;
import com.example.demo.repository.model.JUser;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationService {

  private final ReservationRepository reservationRepository;
  private final ProjectionRepository projectionRepository;
  private final UserRepository userRepository;
  private final SeatRepository seatRepository;
  private final ReservationMapper reservationMapper;

  public ReservationResponse create(ReservationCreateRequest request) {
    Reservation reservation = reservationMapper.toDomain(request);
    JReservation jReservation = reservationMapper.toJpa(reservation);
    jReservation.setCreatedAt(Instant.now());
    jReservation.setProjection(getProjection(request.projectionId()));
    jReservation.setUser(getUser(request.userId()));
    jReservation.setSeats(getSeats(request.seatIds()));
    JReservation saved = reservationRepository.save(jReservation);
    return reservationMapper.toResponse(reservationMapper.toDomain(saved));
  }

  @Transactional(readOnly = true)
  public List<ReservationResponse> getAll() {
    return reservationRepository.findAll().stream()
        .map(reservationMapper::toDomain)
        .map(reservationMapper::toResponse)
        .toList();
  }

  @Transactional(readOnly = true)
  public ReservationResponse getById(UUID id) {
    JReservation jReservation =
        reservationRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Reservation not found: " + id));
    return reservationMapper.toResponse(reservationMapper.toDomain(jReservation));
  }

  public ReservationResponse update(UUID id, ReservationCreateRequest request) {
    JReservation jReservation =
        reservationRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Reservation not found: " + id));
    jReservation.setProjection(getProjection(request.projectionId()));
    jReservation.setUser(getUser(request.userId()));
    jReservation.setSeats(getSeats(request.seatIds()));
    JReservation updated = reservationRepository.save(jReservation);
    return reservationMapper.toResponse(reservationMapper.toDomain(updated));
  }

  public void delete(UUID id) {
    if (!reservationRepository.existsById(id)) {
      throw new NotFoundException("Reservation not found: " + id);
    }
    reservationRepository.deleteById(id);
  }

  private JProjection getProjection(UUID projectionId) {
    return projectionRepository
        .findById(projectionId)
        .orElseThrow(() -> new NotFoundException("Projection not found: " + projectionId));
  }

  private JUser getUser(UUID userId) {
    return userRepository
        .findById(userId)
        .orElseThrow(() -> new NotFoundException("User not found: " + userId));
  }

  private List<JSeat> getSeats(List<UUID> seatIds) {
    return seatRepository.findAllById(seatIds);
  }
}
