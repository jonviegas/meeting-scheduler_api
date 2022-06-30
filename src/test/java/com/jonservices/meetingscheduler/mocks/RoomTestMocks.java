package com.jonservices.meetingscheduler.mocks;

import com.jonservices.meetingscheduler.builder.RoomDTOBuilder;
import com.jonservices.meetingscheduler.data.dto.RoomDTO;
import com.jonservices.meetingscheduler.data.model.Room;
import com.jonservices.meetingscheduler.mapper.RoomMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Optional;

public class RoomTestMocks {

    public static final String ROOM_API_URL_PATH = "/rooms";
    public static final RoomDTO EXPECTED_ROOM_DTO = RoomDTOBuilder.builder().build().toRoomDTO();
    public static final RoomDTO ANOTHER_ROOM_DTO = RoomDTOBuilder.builder().build().aDifferentOne();
    public static final long VALID_ROOM_ID = EXPECTED_ROOM_DTO.getId();
    public static final long INVALID_ROOM_ID = 0L;
    public static final String VALID_ROOM_NAME = EXPECTED_ROOM_DTO.getName();
    public static final String INVALID_ROOM_NAME = "UX Design";
    public static final Pageable DEFAULT_PAGEABLE = PageRequest.ofSize(20);
    public static final Page<RoomDTO> ALL_ROOMS_DTO = new PageImpl<>(Arrays.asList(EXPECTED_ROOM_DTO, ANOTHER_ROOM_DTO));
    public static final Page<RoomDTO> ROOM_PAGE_DTO = new PageImpl<>(Arrays.asList(EXPECTED_ROOM_DTO));
    public static final RoomMapper ROOM_MAPPER = RoomMapper.INSTANCE;
    public static final Optional<Room> EXPECTED_OPTIONAL_ROOM = Optional.of(ROOM_MAPPER.toModel(EXPECTED_ROOM_DTO));
}
