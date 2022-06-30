package com.jonservices.meetingscheduler.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RoomNotFoundException extends RuntimeException {

    public RoomNotFoundException(String attribute, Object value) {
        super("Room with " + attribute + " " + value + " not found");
    }

}
