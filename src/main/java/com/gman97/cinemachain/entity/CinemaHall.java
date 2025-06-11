package com.gman97.cinemachain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cinema_halls")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CinemaHall {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer number;

    @Enumerated(EnumType.STRING)
    private HallSize size;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;
}
