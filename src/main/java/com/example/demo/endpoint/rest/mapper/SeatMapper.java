package com.example.demo.endpoint.rest.mapper;

import com.example.demo.model.Seat;
import com.example.demo.model.dto.SeatCreateRequest;
import com.example.demo.model.dto.SeatResponse;
import com.example.demo.repository.model.JSeat;
import org.springframework.stereotype.Component;

@Component
public class SeatMapper {

  public Seat toDomain(SeatCreateRequest req) {
    return Seat.builder().number(req.number()).roomId(req.roomId()).build();
  }

  public Seat toDomain(JSeat jSeat) {
    return Seat.builder()
        .id(jSeat.getId())
        .number(jSeat.getNumber())
        .roomId(jSeat.getRoom() == null ? null : jSeat.getRoom().getId())
        .build();
  }

  public SeatResponse toResponse(Seat seat) {
    return SeatResponse.builder().id(seat.id()).number(seat.number()).roomId(seat.roomId()).build();
  }

  public JSeat toJpa(Seat seat) {
    JSeat jSeat = new JSeat();
    jSeat.setId(seat.id());
    jSeat.setNumber(seat.number());
    return jSeat;
  }
}
