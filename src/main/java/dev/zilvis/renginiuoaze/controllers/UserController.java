package dev.zilvis.renginiuoaze.controllers;

import dev.zilvis.renginiuoaze.models.Events;
import dev.zilvis.renginiuoaze.payload.response.ShortResponse;
import dev.zilvis.renginiuoaze.payload.response.UserInfoResponse;
import dev.zilvis.renginiuoaze.security.services.UserDetailsImpl;
import dev.zilvis.renginiuoaze.services.EventService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {


    private final AuthenticationManager authenticationManager;
    private final EventService eventService;

    @GetMapping("/info")
    public ResponseEntity<?> getUserInfo(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        UserInfoResponse userInfoResponse = new UserInfoResponse(
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail()
        );

        return ResponseEntity.ok(userInfoResponse);
    }

    @GetMapping("/posts")
    public ResponseEntity<?> getUserPosts(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<Events> eventsPage = eventService.getEventListByUserId(userDetails.getId());

        List<ShortResponse> eventResponse = eventsPage.stream()
                .map(ShortResponse::new)
                .toList();

        return ResponseEntity.ok(eventResponse);
    }
}