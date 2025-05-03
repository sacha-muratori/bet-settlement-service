package com.bookmaker.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Bet {

    @Id
    private Long id;
    private Long userId;
    private Long eventId;
    private Long eventMarketId;
    private Long eventWinnerId;
    private Double betAmount;

    // Getters and Setters
}
