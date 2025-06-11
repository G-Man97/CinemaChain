package com.gman97.cinemachain.http.controller;

import com.gman97.cinemachain.service.FilmSessionService;
import com.gman97.cinemachain.service.SeatsForFilmSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/seats")
@RequiredArgsConstructor
public class SeatsForFilmSessionController {

    private final SeatsForFilmSessionService seatsForFilmSessionService;
    private final FilmSessionService filmSessionService;

    @GetMapping("/{filmSessionId}")
    public String findByFilmSessionId(@PathVariable("filmSessionId") Long id, Model model) {
        model.addAttribute("seats", seatsForFilmSessionService.findByFilmSessionId(id));
        model.addAttribute("seanceInfo", filmSessionService.findSeanceInfoById(id));
        return "seats";
    }
}
