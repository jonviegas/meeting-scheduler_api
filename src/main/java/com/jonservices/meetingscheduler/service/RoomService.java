package com.jonservices.meetingscheduler.service;

import com.jonservices.meetingscheduler.data.dto.MessageResponseDTO;
import com.jonservices.meetingscheduler.data.dto.RoomDTO;
import com.jonservices.meetingscheduler.data.model.Room;
import com.jonservices.meetingscheduler.exceptions.InvalidRoomHourException;
import com.jonservices.meetingscheduler.exceptions.RoomNotFoundException;
import com.jonservices.meetingscheduler.mapper.RoomMapper;
import com.jonservices.meetingscheduler.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoomService {

    private final RoomMapper roomMapper = RoomMapper.INSTANCE;

    @Autowired
    private RoomRepository roomRepository;

    public Page<RoomDTO> findAll(Pageable page) {
        final Page<Room> roomList = roomRepository.findAll(page);
        return roomList.map(roomMapper::toDTO);
    }

    public RoomDTO findById(Long id) {
        final Room room = verifyIfExists(id);
        return roomMapper.toDTO(room);
    }

    public Page<RoomDTO> findByName(String roomName, Pageable page) {
        final Page<Room> roomList = verifyIfExists(roomName, page);
        return roomList.map(roomMapper::toDTO);
    }

    public RoomDTO save(RoomDTO roomDTO) {
        final Room roomToSave = roomMapper.toModel(roomDTO);
        verifyIfEndHourIsBeforeStartHour(roomToSave);
        final Room savedRoom = roomRepository.save(roomToSave);
        return roomMapper.toDTO(savedRoom);
    }

    public RoomDTO update(Long id, RoomDTO roomDTO) {
        verifyIfExists(id);
        roomDTO.setId(id);
        return save(roomDTO);
    }

    public MessageResponseDTO delete(Long id) {
        final Room roomToDelete = verifyIfExists(id);
        roomRepository.delete(roomToDelete);
        return createMessageResponse("Deleted", id);
    }

    private Room verifyIfExists(Long id) {
        final Optional<Room> room = roomRepository.findById(id);
        if (room.isEmpty())
            throw new RoomNotFoundException("id", id);
        return room.get();
    }

    private Page<Room> verifyIfExists(String roomName, Pageable page) {
        final Page<Room> roomList = roomRepository.findByNameContainingIgnoreCase(roomName, page);
        if (roomList.isEmpty())
            throw new RoomNotFoundException("name", roomName);
        return roomList;
    }

    private void verifyIfEndHourIsBeforeStartHour(Room room) {
        if (room.isEndHourBeforeStartHour())
            throw new InvalidRoomHourException();
    }

    private MessageResponseDTO createMessageResponse(String operation, Long id) {
        final String message = operation + " room with id " + id;
        return MessageResponseDTO.builder().message(message).build();
    }

}
