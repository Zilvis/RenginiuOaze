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
public class ShortResponse {

    private Long id;
    private String title;
    private Status status;
    private Long userId;
    private double price;
    private String location;
    private LocalDateTime date;

    public ShortResponse(Events events) {
        this.id = events.getId();
        this.title = events.getTitle();
        this.status = events.getStatus();
        this.userId = events.getUser().getId();
        this.price = events.getPrice();
        this.location = events.getLocation();
        this.date = events.getDate();
    }
}
