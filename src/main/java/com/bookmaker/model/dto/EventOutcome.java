package com.bookmaker.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventOutcome {

    private Long eventId;
    private String eventName;
    private Long eventWinnerId;
}
