package dev.zilvis.renginiuoaze.controllers;

import dev.zilvis.renginiuoaze.enums.Status;
import dev.zilvis.renginiuoaze.models.Events;
import dev.zilvis.renginiuoaze.models.User;
import dev.zilvis.renginiuoaze.payload.response.EventsResponse;
import dev.zilvis.renginiuoaze.payload.response.PaginatedResponse;
import dev.zilvis.renginiuoaze.repository.EventsRepository;
import dev.zilvis.renginiuoaze.repository.UserRepository;
import dev.zilvis.renginiuoaze.security.services.UserDetailsImpl;
import dev.zilvis.renginiuoaze.services.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/events")
//@CrossOrigin(origins = "http://127.0.0.1:5500", maxAge = 3600, allowCredentials="true")
public class EventsController {

    private final EventService eventService;
    private final EventsRepository eventsRepository;
    private final UserRepository userRepository;


//    @PostMapping("/public/add")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<?> addEvent(@Valid @RequestBody Events event) {
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//
//        User user = userRepository.findByUsername(userDetails.getUsername())
//                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//
//        event.setStatus(Status.STATUS_ACTIVE);
//
//        user.addEvent(event);
//
//        eventsRepository.save(event);
//
//        return ResponseEntity.ok("Successfully added event");
//    }

    @PostMapping("/public/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addEvent(
            @RequestParam("title") String title,
            @RequestParam("date") String date,
            @RequestParam("description") String description,
            @RequestParam("location") String location,
            @RequestParam("price") Double price,
            @RequestParam("status") String status,
            @RequestParam("image") MultipartFile image) {

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            Long userId = userDetails.getId();

            // Save the image and get the URL
            String imageUrl = saveImage(image);

            // Create event and set properties
            Events event = new Events();
            event.setTitle(title);
            event.setDate(date);
            event.setDescription(description);
            event.setLocation(location);
            event.setPrice(price);
            event.setStatus(Status.valueOf(status));
            event.setImageUrl(imageUrl);
            event.setUser(userRepository.findById(userId).orElse(null));

            eventsRepository.save(event);

            return ResponseEntity.ok("Successfully added event");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image");
        }
    }

    private String saveImage(MultipartFile file) throws IOException {
        // Generate a unique file name
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get("src/main/resources/static/uploads/", fileName);

        // Create the directory if it does not exist
        Files.createDirectories(filePath.getParent());

        // Save the file
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return fileName;
    }


    @GetMapping("/public/getImg/{filename}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        try {
            Path filePath = Paths.get("src/main/resources/static/uploads/").resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                String contentType = Files.probeContentType(filePath);
                if (contentType == null) {
                    contentType = "application/octet-stream";
                }
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/public/")
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

    @GetMapping("/public/{id}")
    public ResponseEntity<?> getEventById(@PathVariable Long id) {
        Events e = eventService.findById(id);
        EventsResponse eventResponse = e != null ? new EventsResponse(e) : new EventsResponse();
        return ResponseEntity.ok(eventResponse);
    }
}
