package dev.zilvis.renginiuoaze.services;

import dev.zilvis.renginiuoaze.enums.Status;
import dev.zilvis.renginiuoaze.models.Events;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EventService {
    List<Events> getEvents();
    Events save(Events events);
    Events findById(long id);
    String update(Events events);
    String deleteById(long id);
    String changeStatus(long id, Status status);
    Page<Events> getEvents(Pageable pageable);
    List<Events> getEventListByUserId(long userId);
}
