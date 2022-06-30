package com.jonservices.meetingscheduler.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoomDTO {

    private Long id;

    @NotEmpty
    @Size(min = 2, max = 300)
    private String name;

    // dd/MM/yyyy
    @Pattern(regexp = "(((0[1-9])|([12][0-9])|(3[01]))\\/((0[0-9])|(1[012]))\\/((20[012]\\d|19\\d\\d)|(1\\d|2[0123])))")
    @NotEmpty
    private String date;

    // HH:mm
    @Pattern(regexp = "^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$")
    @NotEmpty
    private String startHour;

    // HH:mm
    @Pattern(regexp = "^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$")
    @NotEmpty
    private String endHour;

}
