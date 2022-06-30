package com.jonservices.meetingscheduler.controller;

import com.jonservices.meetingscheduler.data.dto.MessageResponseDTO;
import com.jonservices.meetingscheduler.data.dto.RoomDTO;
import com.jonservices.meetingscheduler.docs.RoomControllerDocs;
import com.jonservices.meetingscheduler.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/rooms")
@CrossOrigin(maxAge = 3600)
public class RoomController implements RoomControllerDocs {

    @Autowired
    private RoomService roomService;

    @GetMapping
    public Page<RoomDTO> findAll(Pageable page) {
        return roomService.findAll(page);
    }

    @GetMapping("/{id}")
    public RoomDTO findById(@PathVariable Long id) {
        return roomService.findById(id);
    }

    @GetMapping("/search/{roomName}")
    public Page<RoomDTO> findByName(@PathVariable String roomName, Pageable page) {
        return roomService.findByName(roomName, page);
    }

    @PostMapping
    public RoomDTO save(@RequestBody @Valid RoomDTO roomDTO) {
        return roomService.save(roomDTO);
    }

    @PutMapping("/{id}")
    public RoomDTO update(@PathVariable Long id, @RequestBody @Valid RoomDTO roomDTO) {
        return roomService.update(id, roomDTO);
    }

    @DeleteMapping("/{id}")
    public MessageResponseDTO delete(@PathVariable Long id) {
        return roomService.delete(id);
    }

}
