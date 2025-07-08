package com.gman97.cinemachain.http.controller;

import com.gman97.cinemachain.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping
    public String findAllInRent(Model model) {
        model.addAttribute("movies", movieService.findAllByIsItInRent(Boolean.TRUE));
        return "movies";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Integer id, Model model) {
        return movieService.findByIdWithFetch(id)
                .map(mayBeMovie -> {
                    model.addAttribute("movie", mayBeMovie);
                    return "movie";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/soon")
    public String findAllSoon(Model model) {
        model.addAttribute("movies", movieService.findAllByIsItInRent(Boolean.FALSE));
        return "soon";
    }

    @GetMapping("/soon/{id}")
    public String findByIdSoon(@PathVariable Integer id, Model model) {
         return movieService.findById(id)
                 .map(mayBeMovie -> {
                     model.addAttribute("movie", mayBeMovie);
                     return "movie";
                 })
                 .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
