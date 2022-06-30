package com.jonservices.meetingscheduler.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidRoomHourException extends RuntimeException {
    public InvalidRoomHourException() {
        super("End hour must be after Start hour");
    }
}
