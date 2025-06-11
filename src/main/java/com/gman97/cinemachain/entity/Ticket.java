package com.gman97.cinemachain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "tickets")
@Getter
@Setter
//@ToString(exclude = {"movie", "cinemaHall"})
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String movieTitle;

    private String movieDuration;

    private LocalDate date;

    private LocalTime beginSession;

    private String cinemaName;

    private Integer hallNumber;

    private Short row;

    private Short seatNo;

    private Double cost;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "film_session_id")
    private FilmSession filmSession;

}
