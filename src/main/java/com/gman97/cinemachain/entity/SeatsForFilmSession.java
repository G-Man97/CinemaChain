package com.gman97.cinemachain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "seats_for_film_sessions")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SeatsForFilmSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Short row;

    private Short seatNo;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Integer ticketCost;

    @ManyToOne(fetch = FetchType.LAZY /*, optional = false*/)
    @JoinColumn(name = "film_session_id")
    private FilmSession filmSession;
}
