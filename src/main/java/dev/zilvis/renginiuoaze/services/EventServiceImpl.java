package dev.zilvis.renginiuoaze.services;

import dev.zilvis.renginiuoaze.enums.Status;
import dev.zilvis.renginiuoaze.models.Events;
import dev.zilvis.renginiuoaze.repository.EventsRepository;
import dev.zilvis.renginiuoaze.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventsRepository eventsRepository;
    private final UserRepository userRepository;

    @Override
    public List<Events> getEvents() {
        return eventsRepository.findAll();
    }

    @Override
    public Events save(Events events) {
        return eventsRepository.save(events);
    }

    @Override
    public Events findById(long id) {
        if (eventsRepository.findById(id).isPresent()) {
            return eventsRepository.findById(id).get();
        } else {
            return null;
        }
    }

    @Override
    public String update(Events events) {
        return "";
    }

    @Override
    public String deleteById(long id) {
        return "";
    }

    @Override
    public String changeStatus(long id, Status status) {
        return "";
    }

    @Override
    public Page<Events> getEvents(Pageable pageable) {
        return eventsRepository.findAll(pageable);
    }

    @Override
    public List<Events> getEventListByUserId(long userId) {
        return eventsRepository.findByUserId(userId);
    }
}
