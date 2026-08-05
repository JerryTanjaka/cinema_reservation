package com.example.demo.service;

import com.example.demo.endpoint.rest.mapper.SeatMapper;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.Seat;
import com.example.demo.model.dto.SeatCreateRequest;
import com.example.demo.model.dto.SeatResponse;
import com.example.demo.repository.RoomRepository;
import com.example.demo.repository.SeatRepository;
import com.example.demo.repository.model.JRoom;
import com.example.demo.repository.model.JSeat;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SeatService {

  private final SeatRepository seatRepository;
  private final RoomRepository roomRepository;
  private final SeatMapper seatMapper;

  public SeatResponse create(SeatCreateRequest request) {
    Seat seat = seatMapper.toDomain(request);
    JSeat jSeat = seatMapper.toJpa(seat);
    jSeat.setRoom(getRoom(request.roomId()));
    JSeat saved = seatRepository.save(jSeat);
    return seatMapper.toResponse(seatMapper.toDomain(saved));
  }

  public List<SeatResponse> getAll() {
    return seatRepository.findAll().stream()
        .map(seatMapper::toDomain)
        .map(seatMapper::toResponse)
        .toList();
  }

  public SeatResponse getById(UUID id) {
    JSeat jSeat =
        seatRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Seat not found: " + id));
    return seatMapper.toResponse(seatMapper.toDomain(jSeat));
  }

  public SeatResponse update(UUID id, SeatCreateRequest request) {
    JSeat jSeat =
        seatRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Seat not found: " + id));
    jSeat.setNumber(request.number());
    jSeat.setRoom(getRoom(request.roomId()));
    JSeat updated = seatRepository.save(jSeat);
    return seatMapper.toResponse(seatMapper.toDomain(updated));
  }

  public void delete(UUID id) {
    if (!seatRepository.existsById(id)) {
      throw new NotFoundException("Seat not found: " + id);
    }
    seatRepository.deleteById(id);
  }

  private JRoom getRoom(UUID roomId) {
    return roomRepository
        .findById(roomId)
        .orElseThrow(() -> new NotFoundException("Room not found: " + roomId));
  }
}
