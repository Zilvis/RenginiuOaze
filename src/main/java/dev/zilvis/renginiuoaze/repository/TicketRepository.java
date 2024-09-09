package dev.zilvis.renginiuoaze.repository;

import dev.zilvis.renginiuoaze.models.Tickets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Tickets, Integer> {
}
