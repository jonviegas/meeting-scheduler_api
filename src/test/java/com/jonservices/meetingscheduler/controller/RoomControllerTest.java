package com.jonservices.meetingscheduler.controller;

import com.jonservices.meetingscheduler.builder.RoomDTOBuilder;
import com.jonservices.meetingscheduler.data.dto.MessageResponseDTO;
import com.jonservices.meetingscheduler.data.dto.RoomDTO;
import com.jonservices.meetingscheduler.exceptions.RoomNotFoundException;
import com.jonservices.meetingscheduler.service.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import static com.jonservices.meetingscheduler.mocks.RoomTestMocks.*;
import static com.jonservices.meetingscheduler.utils.JSONConvertionUtils.asJsonString;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class RoomControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RoomService roomService;

    @InjectMocks
    private RoomController roomController;

    @BeforeEach
    void setupEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(roomController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test // GET Find All
    @DisplayName("When GET is called then all rooms and ok status is returned")
    void whenGETIsCalledThenAllRoomsAndOkStatusIsReturned() throws Exception {
        // when
        when(roomService.findAll(DEFAULT_PAGEABLE)).thenReturn(ALL_ROOMS_DTO);

        // then
        mockMvc.perform(get(ROOM_API_URL_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(ALL_ROOMS_DTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name", is(EXPECTED_ROOM_DTO.getName())))
                .andExpect(jsonPath("$.content[0].date", is(EXPECTED_ROOM_DTO.getDate())))
                .andExpect(jsonPath("$.content[1].endHour", is(ANOTHER_ROOM_DTO.getEndHour())))
                .andExpect(jsonPath("$.content[1].startHour", is(ANOTHER_ROOM_DTO.getStartHour())));
    }

    @Test // GET Find by id
    @DisplayName("When GET is called with valid id then ok status is returned")
    void whenGETIsCalledWithValidIdThenOkStatusIsReturned() throws Exception {
        // when
        when(roomService.findById(VALID_ROOM_ID)).thenReturn(EXPECTED_ROOM_DTO);

        // then
        mockMvc.perform(get(ROOM_API_URL_PATH + "/" + VALID_ROOM_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(EXPECTED_ROOM_DTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(EXPECTED_ROOM_DTO.getName())));
    }

    @Test // GET Find by Name
    @DisplayName("When GET is called with valid name then ok status is returned")
    void whenGETIsCalledWithValidNameThenOkStatusIsReturned() throws Exception {
        // when
        when(roomService.findByName(VALID_ROOM_NAME, DEFAULT_PAGEABLE)).thenReturn(ROOM_PAGE_DTO);

        // then
        mockMvc.perform(get(ROOM_API_URL_PATH + "/search/" + VALID_ROOM_NAME)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(EXPECTED_ROOM_DTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name", is(EXPECTED_ROOM_DTO.getName())))
                .andExpect(jsonPath("$.content[0].date", is(EXPECTED_ROOM_DTO.getDate())))
                .andExpect(jsonPath("$.content[0].endHour", is(EXPECTED_ROOM_DTO.getEndHour())));
    }

    @Test // POST Save Room
    @DisplayName("When POST is called then it should create room")
    void whenPOSTIsCalledThenItShouldCreateRoom() throws Exception {
        // when
        when(roomService.save(EXPECTED_ROOM_DTO)).thenReturn(EXPECTED_ROOM_DTO);

        // then
        mockMvc.perform(post(ROOM_API_URL_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(EXPECTED_ROOM_DTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(EXPECTED_ROOM_DTO.getName())));
    }

    @Test // PUT Update Room by id
    @DisplayName("When PUT is called then it should update room")
    void whenPUTIsCalledThenItShouldUpdateRoom() throws Exception {
        // given
        final RoomDTO updatedRoomDTO = RoomDTOBuilder.builder().build().toRoomDTO();
        updatedRoomDTO.setName("Docker-Compose Basics");

        // when
        when(roomService.update(VALID_ROOM_ID, updatedRoomDTO)).thenReturn(updatedRoomDTO);

        // then
        mockMvc.perform(put(ROOM_API_URL_PATH + "/" + VALID_ROOM_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedRoomDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(updatedRoomDTO.getName())));
    }

    @Test // DELETE Delete by id
    @DisplayName("When DELETE is called then it should delete room")
    void whenPOSTIsCalledThenItShouldDeleteRoom() throws Exception {
        // given
        final MessageResponseDTO expectedMessageResponseDTO = MessageResponseDTO
                .builder().message("Deleted room with id " + VALID_ROOM_ID).build();

        // when
        when(roomService.delete(VALID_ROOM_ID)).thenReturn(expectedMessageResponseDTO);

        // then
        mockMvc.perform(delete(ROOM_API_URL_PATH + "/" + VALID_ROOM_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is(expectedMessageResponseDTO.getMessage())));
    }

    @Test // GET Find by id RoomNotFoundException
    @DisplayName("When GET is called with an invalid id then it should thrown an error")
    void whenGETIsCalledWithAnInvalidIdThenItShouldThrownAnError() throws Exception {
        // when
        doThrow(RoomNotFoundException.class).when(roomService).findById(INVALID_ROOM_ID);

        // then
        mockMvc.perform(get(ROOM_API_URL_PATH + "/" + INVALID_ROOM_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test // GET Find by Name RoomNotFoundException
    @DisplayName("When GET is called with an invalid name then it should thrown an error")
    void whenGETIsCalledWithAnInvalidNameThenItShouldThrownAnError() throws Exception {
        // when
        doThrow(RoomNotFoundException.class).when(roomService).findByName(INVALID_ROOM_NAME, DEFAULT_PAGEABLE);

        // then
        mockMvc.perform(get(ROOM_API_URL_PATH + "/search/" + INVALID_ROOM_NAME)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test // POST Save Room Exception
    @DisplayName("When POST is called with invalid hour format then it should thrown an error")
    void whenPOSTIsCalledWithInvalidHourFormatThenItShouldThrownAnError() throws Exception {
        // when
        final RoomDTO roomDTO = RoomDTOBuilder.builder().build().toRoomDTO();
        roomDTO.setStartHour("01:00:00");

        // then
        mockMvc.perform(post(ROOM_API_URL_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(roomDTO)))
                .andExpect(status().isBadRequest());
    }

}
