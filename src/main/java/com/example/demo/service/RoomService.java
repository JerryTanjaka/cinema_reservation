package com.example.demo.service;

import com.example.demo.exception.NotFoundException;
import com.example.demo.endpoint.rest.mapper.RoomMapper;
import com.example.demo.model.Room;
import com.example.demo.model.dto.RoomCreateRequest;
import com.example.demo.model.dto.RoomResponse;
import com.example.demo.repository.RoomRepository;
import com.example.demo.repository.model.JRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;

    public RoomResponse create(RoomCreateRequest request) {
        Room room = roomMapper.toDomain(request);
        JRoom jRoom = roomMapper.toJpa(room);
        JRoom saved = roomRepository.save(jRoom);
        return roomMapper.toResponse(roomMapper.toDomain(saved));
    }

    public List<RoomResponse> getAll() {
        return roomRepository.findAll().stream()
                .map(roomMapper::toDomain)
                .map(roomMapper::toResponse)
                .toList();
    }

    public RoomResponse getById(UUID id) {
        JRoom jRoom = roomRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Room not found: " + id));
        return roomMapper.toResponse(roomMapper.toDomain(jRoom));
    }

    public RoomResponse update(UUID id, RoomCreateRequest request) {
        JRoom jRoom = roomRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Room not found: " + id));
        jRoom.setNumber(request.number());
        jRoom.setCapacity(request.capacity());
        JRoom updated = roomRepository.save(jRoom);
        return roomMapper.toResponse(roomMapper.toDomain(updated));
    }

    public void delete(UUID id) {
        if (!roomRepository.existsById(id)) {
            throw new NotFoundException("Room not found: " + id);
        }
        roomRepository.deleteById(id);
    }
}