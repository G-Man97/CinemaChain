package com.gman97.cinemachain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "movies")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"filmSessions"})
@EqualsAndHashCode(exclude = {"filmSessions", "isItInRent"})
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private LocalDate premiere;

    private LocalDate rentEnd;

    private String duration;

    @Enumerated(EnumType.STRING)
    private Pegi pegi;

    private String genre;

    private String countries;

    private String composer;

    private String producer;

    private String actors;

    private String description;

    private String poster;

    private Boolean isItInRent;

    @OneToMany(mappedBy = "movie", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
    private List<FilmSession> filmSessions = new ArrayList<>();
}
