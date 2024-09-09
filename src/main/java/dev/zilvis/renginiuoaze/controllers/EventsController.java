package dev.zilvis.renginiuoaze.controllers;

import dev.zilvis.renginiuoaze.enums.Status;
import dev.zilvis.renginiuoaze.models.Events;
import dev.zilvis.renginiuoaze.payload.response.EventsResponse;
import dev.zilvis.renginiuoaze.payload.response.PaginatedResponse;
import dev.zilvis.renginiuoaze.repository.EventsRepository;
import dev.zilvis.renginiuoaze.repository.UserRepository;
import dev.zilvis.renginiuoaze.security.services.UserDetailsImpl;
import dev.zilvis.renginiuoaze.services.EventService;
import dev.zilvis.renginiuoaze.services.ImageUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/events")
public class EventsController {

    private final EventService eventService;
    private final UserRepository userRepository;
    private final ImageUploadService imageUploadService;

    // Naujo Post kurimas

    @PostMapping("/")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> addEvent(
            @RequestParam("title") String title,
            @RequestParam("date") LocalDateTime date,
            @RequestParam("description") String description,
            @RequestParam("location") String location,
            @RequestParam("price") Double price,
            @RequestParam("status") String status,
            @RequestParam("image") MultipartFile image) {

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            Long userId = userDetails.getId();

            String imageUrl = imageUploadService.saveImage(image);

            Events event = new Events();
            event.setTitle(title);
            event.setDate(date);
            event.setDescription(description);
            event.setLocation(location);
            event.setPrice(price);
            event.setStatus(Status.valueOf(status));
            event.setImageUrl(imageUrl);
            event.setUser(userRepository.findById(userId).orElse(null));

            eventService.save(event);

            return ResponseEntity.ok("Successfully added event");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image");
        }
    }

    // Visu Post gavimas

    @GetMapping("/")
    public ResponseEntity<PaginatedResponse<EventsResponse>> getAllEvents(
            @PageableDefault(size = 10) Pageable pageable) {

        Page<Events> eventsPage = eventService.getEvents(pageable);

        List<EventsResponse> eventResponse = eventsPage.getContent().stream()
                .map(EventsResponse::new)
                .collect(Collectors.toList());

        PaginatedResponse<EventsResponse> paginatedResponse = new PaginatedResponse<>(
                eventResponse,
                eventsPage.getTotalPages(),
                eventsPage.getTotalElements(),
                eventsPage.getNumber(),
                eventsPage.getSize()
        );

        return ResponseEntity.ok(paginatedResponse);
    }

    // Vieno Post gavimas

    @GetMapping("/{id}")
    public ResponseEntity<?> getEventById(@PathVariable Long id) {
        Events e = eventService.findById(id);
        EventsResponse eventResponse = e != null ? new EventsResponse(e) : new EventsResponse();
        return ResponseEntity.ok(eventResponse);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        eventService.deleteById(id);
    }
}
