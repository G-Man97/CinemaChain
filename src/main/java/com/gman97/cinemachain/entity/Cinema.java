package com.gman97.cinemachain.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cinemas")
@Getter
@Setter
@ToString(exclude = {"halls"})
@EqualsAndHashCode(exclude = {"halls"})
@AllArgsConstructor
@NoArgsConstructor
public class Cinema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @OneToMany(mappedBy = "cinema", cascade = {CascadeType.REMOVE})
    private List<CinemaHall> halls = new ArrayList<>();

    public void addHall(CinemaHall hall) {
        halls.add(hall);
        hall.setCinema(this);
    }
}
