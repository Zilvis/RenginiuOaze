package dev.zilvis.renginiuoaze.repository;

import dev.zilvis.renginiuoaze.models.Events;
import dev.zilvis.renginiuoaze.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventsRepository extends JpaRepository<Events, Long> {
    Page<Events> findAll(Pageable pageable);;

    List<Events> findByUserId(long userId);
}
