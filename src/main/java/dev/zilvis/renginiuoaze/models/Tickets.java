package dev.zilvis.renginiuoaze.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Tickets {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String eventId;
    private Double price;
    private String currency;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
