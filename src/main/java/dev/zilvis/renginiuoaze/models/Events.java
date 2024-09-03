package dev.zilvis.renginiuoaze.models;


import com.fasterxml.jackson.annotation.JsonFormat;
import dev.zilvis.renginiuoaze.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Events {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(min = 5, max = 100)
    private String title;


    @Lob
    @Column(length = 1000000)
    private String description;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;


    @Size(min = 3, max = 50)
    private String location;

    private Double price;

    private String imageUrl;
}
