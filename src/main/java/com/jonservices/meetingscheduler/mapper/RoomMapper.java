package com.jonservices.meetingscheduler.mapper;

import com.jonservices.meetingscheduler.data.dto.RoomDTO;
import com.jonservices.meetingscheduler.data.model.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoomMapper {

    RoomMapper INSTANCE = Mappers.getMapper(RoomMapper.class);

    @Mappings({
            @Mapping(target = "date", source = "date", dateFormat = "dd/MM/yyyy"),
            @Mapping(target = "startHour", source = "startHour", dateFormat = "HH:mm"),
            @Mapping(target = "endHour", source = "endHour", dateFormat = "HH:mm")
    })
    Room toModel(RoomDTO roomDTO);

    @Mappings({
            @Mapping(target = "date", source = "date", dateFormat = "dd/MM/yyyy"),
            @Mapping(target = "startHour", source = "startHour", dateFormat = "HH:mm"),
            @Mapping(target = "endHour", source = "endHour", dateFormat = "HH:mm")
    })
    RoomDTO toDTO(Room room);
}
