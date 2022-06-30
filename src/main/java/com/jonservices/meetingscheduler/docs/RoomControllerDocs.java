package com.jonservices.meetingscheduler.docs;

import com.jonservices.meetingscheduler.data.dto.MessageResponseDTO;
import com.jonservices.meetingscheduler.data.dto.RoomDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;

@Tag(name = "Room Controller")
public interface RoomControllerDocs {

    @Operation(summary = "Find all rooms")
    @ApiResponse(responseCode = "200", description = "Returns OK status")
    @ResponseStatus(HttpStatus.OK)
    Page<RoomDTO> findAll(Pageable page);

    @Operation(summary = "Finds a room by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns OK status if the room exists"),
            @ApiResponse(responseCode = "400", description = "Returns BAD REQUEST status when an invalid id format is passed"),
            @ApiResponse(responseCode = "404", description = "Returns NOT FOUND status when the room does not exists")
    })
    @ResponseStatus(HttpStatus.OK)
    RoomDTO findById(@PathVariable Long id);

    @Operation(summary = "Returns a list of rooms that contains a specific name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns OK status if room exists"),
            @ApiResponse(responseCode = "404", description = "Returns NOT FOUND status when room does not exist")
    })
    @ResponseStatus(HttpStatus.OK)
    Page<RoomDTO> findByName(@PathVariable String roomName, Pageable page);

    @Operation(summary = "Saves a new room in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Returns CREATED status when a new room is saved"),
            @ApiResponse(responseCode = "400", description = "Returns BAD REQUEST status when end hour is before start hour")
    })
    @ResponseStatus(HttpStatus.CREATED)
    public RoomDTO save(@RequestBody @Valid RoomDTO roomDTO);

    @Operation(summary = "Updates a room in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns OK status when a room is updated"),
            @ApiResponse(responseCode = "400", description = "Returns BAD REQUEST status when an invalid id format is passed"),
            @ApiResponse(responseCode = "400", description = "Returns BAD REQUEST status when end hour is before start hour"),
            @ApiResponse(responseCode = "404", description = "Returns NOT FOUND status when the room does not exists")
    })
    @ResponseStatus(HttpStatus.OK)
    public RoomDTO update(@PathVariable Long id, @RequestBody @Valid RoomDTO roomDTO);

    @Operation(summary = "Removes a room from the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns OK status when a room is deleted"),
            @ApiResponse(responseCode = "400", description = "Returns BAD REQUEST status when an invalid id format is passed"),
            @ApiResponse(responseCode = "404", description = "Returns NOT FOUND status when the room does not exists")
    })
    @ResponseStatus(HttpStatus.OK)
    public MessageResponseDTO delete(@PathVariable Long id);

}
