package com.bookmaker.repository;

import com.bookmaker.model.Bet;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BetRepository extends JpaRepository<Bet, Long> {

    List<Bet> findAll();
    List<Bet> findByEventId(Long eventId);
}
