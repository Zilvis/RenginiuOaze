package dev.zilvis.renginiuoaze.payload.response;

import dev.zilvis.renginiuoaze.enums.Status;
import dev.zilvis.renginiuoaze.models.Events;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventsResponse {
    private Long id;
    private String title;
    private String description;
    private Status status;
    private Long userId;
    private double price;
    private String location;
    private String imageUrl;
    private LocalDateTime date;

    public EventsResponse(Events events) {
        this.id = events.getId();
        this.title = events.getTitle();
        this.description = events.getDescription();
        this.status = events.getStatus();
        this.userId = events.getUser().getId();
        this.price = events.getPrice();
        this.location = events.getLocation();
        this.imageUrl = events.getImageUrl();
        this.date = events.getDate();
    }
}
