package com.gman97.cinemachain.repository;

import com.gman97.cinemachain.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
