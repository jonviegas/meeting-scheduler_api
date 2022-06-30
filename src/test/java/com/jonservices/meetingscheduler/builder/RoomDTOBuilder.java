package com.jonservices.meetingscheduler.builder;

import com.jonservices.meetingscheduler.data.dto.RoomDTO;
import lombok.Builder;

@Builder
public class RoomDTOBuilder {

    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private String name = "Docker Basics";

    @Builder.Default
    private String date = "29/06/2022";

    @Builder.Default
    private String startHour = "02:00";

    @Builder.Default
    private String endHour = "04:00";

    public RoomDTO toRoomDTO() {
        return new RoomDTO(id, name, date, startHour, endHour);
    }

    public RoomDTO aDifferentOne() {
        final RoomDTO roomDTO = toRoomDTO();
        roomDTO.setId(2L);
        roomDTO.setName("Java OOP");
        roomDTO.setStartHour("03:00");
        roomDTO.setEndHour("05:00");
        return roomDTO;
    }

}
