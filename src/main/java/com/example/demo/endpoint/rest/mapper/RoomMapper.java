package com.example.demo.endpoint.rest.mapper;

import com.example.demo.model.Room;
import com.example.demo.model.dto.RoomCreateRequest;
import com.example.demo.model.dto.RoomResponse;
import com.example.demo.repository.model.JRoom;

public class RoomMapper {
    public Room toRoom(RoomCreateRequest  req) {
        return Room.builder()
                .number(req.number())
                .capacity(req.capacity())
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