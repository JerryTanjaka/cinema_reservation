package com.example.demo.endpoint.rest.mapper;

import com.example.demo.model.Room;
import com.example.demo.model.dto.RoomCreateRequest;
import com.example.demo.model.dto.RoomResponse;
import com.example.demo.repository.model.JRoom;
import org.springframework.stereotype.Component;

@Component
public class RoomMapper {

  public Room toDomain(RoomCreateRequest req) {
    return Room.builder().number(req.number()).capacity(req.capacity()).build();
  }

  public Room toDomain(JRoom jRoom) {
    return Room.builder()
        .id(jRoom.getId())
        .number(jRoom.getNumber())
        .capacity(jRoom.getCapacity())
        .build();
  }

  public RoomResponse toResponse(Room room) {
    return RoomResponse.builder()
        .id(room.id())
        .number(room.number())
        .capacity(room.capacity())
        .build();
  }

  public JRoom toJpa(Room room) {
    JRoom jRoom = new JRoom();
    jRoom.setId(room.id());
    jRoom.setNumber(room.number());
    jRoom.setCapacity(room.capacity());
    return jRoom;
  }
}
