package com.jonservices.meetingscheduler.service;

import com.jonservices.meetingscheduler.builder.RoomDTOBuilder;
import com.jonservices.meetingscheduler.data.dto.MessageResponseDTO;
import com.jonservices.meetingscheduler.data.dto.RoomDTO;
import com.jonservices.meetingscheduler.data.model.Room;
import com.jonservices.meetingscheduler.exceptions.InvalidRoomHourException;
import com.jonservices.meetingscheduler.exceptions.RoomNotFoundException;
import com.jonservices.meetingscheduler.repository.RoomRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.Optional;

import static com.jonservices.meetingscheduler.mocks.RoomTestMocks.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoomServiceTest {

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomService roomService;

    @Test // Find all
    @DisplayName("Should return all registered rooms")
    void shouldReturnAllRegisteredRooms() {
        // given
        final Page<Room> returnedRoomsPage = ALL_ROOMS_DTO.map(ROOM_MAPPER::toModel);

        // when
        when(roomRepository.findAll(DEFAULT_PAGEABLE)).thenReturn(returnedRoomsPage);
        final Page<RoomDTO> returnedRoomsDTOPage = roomService.findAll(DEFAULT_PAGEABLE);

        // then
        assertThat(returnedRoomsDTOPage).isNotEmpty();
        assertThat(returnedRoomsDTOPage).isEqualTo(ALL_ROOMS_DTO);
    }

    @Test // Find by id
    @DisplayName("When registered room searched by its id then it should be returned")
    void whenRegisteredRoomSearchedByItsIdThenItShouldBeReturned() {
        // when
        when(roomRepository.findById(VALID_ROOM_ID)).thenReturn(EXPECTED_OPTIONAL_ROOM);
        final RoomDTO returnedRoomDTO = roomService.findById(VALID_ROOM_ID);

        // then
        assertThat(returnedRoomDTO).isEqualTo(EXPECTED_ROOM_DTO);
    }

    @Test // Find by name
    @DisplayName("When registered room searched by its name then it should be returned")
    void whenRegisteredRoomSearchedByItsNameThenItShouldBeReturned() {
        // given
        final Page<Room> returnedRoomsPage = ROOM_PAGE_DTO.map(ROOM_MAPPER::toModel);
        final String roomName = EXPECTED_ROOM_DTO.getName();

        // when
        when(roomRepository.findByNameContainingIgnoreCase(roomName, DEFAULT_PAGEABLE)).thenReturn(returnedRoomsPage);
        final Page<RoomDTO> returnedRoomsPageDTO = roomService.findByName(roomName, DEFAULT_PAGEABLE);

        // then
        assertThat(returnedRoomsPageDTO).isEqualTo(ROOM_PAGE_DTO);
    }

    @Test // Save room
    @DisplayName("When room informed then it should be created")
    void whenRoomInformedThenItShouldBeCreated() {
        // given
        final Room expectedSavedRoom = ROOM_MAPPER.toModel(EXPECTED_ROOM_DTO);

        // when
        when(roomRepository.save(expectedSavedRoom)).thenReturn(expectedSavedRoom);
        final RoomDTO returnedRoomDTO = roomService.save(EXPECTED_ROOM_DTO);

        // then
        assertThat(returnedRoomDTO).isEqualTo(EXPECTED_ROOM_DTO);
    }

    @Test // Update room
    @DisplayName("When room informed then it should be updated")
    void whenRoomInformedThenItShouldBeUpdated() {
        // given
        final RoomDTO expectedUpdatedRoomDTO = RoomDTOBuilder.builder().build().toRoomDTO();
        expectedUpdatedRoomDTO.setName("Docker-Compose Basics");
        final Room expectedUpdatedRoom = ROOM_MAPPER.toModel(expectedUpdatedRoomDTO);

        // when
        when(roomRepository.findById(VALID_ROOM_ID)).thenReturn(EXPECTED_OPTIONAL_ROOM);
        when(roomRepository.save(expectedUpdatedRoom)).thenReturn(expectedUpdatedRoom);
        final RoomDTO returnedRoomDTO = roomService.update(VALID_ROOM_ID, expectedUpdatedRoomDTO);

        // then
        assertThat(returnedRoomDTO).isEqualTo(expectedUpdatedRoomDTO);
    }

    @Test // Delete Room
    @DisplayName("Should delete room by its id")
    void shouldDeleteRoomByItsId() {
        // given
        final MessageResponseDTO expectedMessageResponseDTO = MessageResponseDTO
                .builder().message("Deleted room with id " + EXPECTED_ROOM_DTO.getId()).build();

        // when
        when(roomRepository.findById(VALID_ROOM_ID)).thenReturn(EXPECTED_OPTIONAL_ROOM);
        final MessageResponseDTO returnedMessageResponseDTO = roomService.delete(VALID_ROOM_ID);

        // then
        assertThat(expectedMessageResponseDTO).isEqualTo(returnedMessageResponseDTO);
    }

    @Test // RoomNotFoundException Invalid id
    @DisplayName("When invalid id informed then it should thrown an exception")
    void whenInvalidIdInformedThenItShouldThrownAnException() {
        // when
        when(roomRepository.findById(INVALID_ROOM_ID)).thenReturn(Optional.empty());

        // then
        assertThatThrownBy(() -> roomService.findById(INVALID_ROOM_ID))
                .isInstanceOf(RoomNotFoundException.class)
                .hasMessageContaining("Room with id %s not found", INVALID_ROOM_ID);
    }

    @Test // RoomNotFoundException Invalid name
    @DisplayName("When invalid name informed then it should thrown an exception")
    void whenInvalidNameInformedThenItShouldThrownAnException() {
        // when
        when(roomRepository.findByNameContainingIgnoreCase(INVALID_ROOM_NAME, DEFAULT_PAGEABLE)).thenReturn(new PageImpl<>(new ArrayList<>()));

        // then
        assertThatThrownBy(() -> roomService.findByName(INVALID_ROOM_NAME, DEFAULT_PAGEABLE))
                .isInstanceOf(RoomNotFoundException.class)
                .hasMessageContaining("Room with name %s not found", INVALID_ROOM_NAME);
    }

    @Test // InvalidRoomHourException
    @DisplayName("When end hour is before start hour then it should thrown an exception")
    void whenEndHourIsBeforeStartHourThenItShouldThrownAnException() {
        // when
        final RoomDTO roomDTOWithInvalidHour = RoomDTOBuilder.builder().build().toRoomDTO();
        roomDTOWithInvalidHour.setStartHour("09:00");

        // then
        assertThatThrownBy(() -> roomService.save(roomDTOWithInvalidHour))
                .isInstanceOf(InvalidRoomHourException.class)
                .hasMessageContaining("End hour must be after Start hour");
    }

}
