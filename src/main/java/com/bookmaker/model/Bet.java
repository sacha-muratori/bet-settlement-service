package com.bookmaker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Bet {

    @Id
    private Long betId;
    private Long userId;
    private Long eventId;
    private Long eventMarketId;
    private Long eventWinnerId;
    private Double betAmount;

    // Getters and Setters
}
