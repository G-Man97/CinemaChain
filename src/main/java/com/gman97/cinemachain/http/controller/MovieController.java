package com.gman97.cinemachain.http.controller;

import com.gman97.cinemachain.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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

//    @GetMapping("/{id}/poster")
//    @ResponseBody
//    public ResponseEntity<byte[]> findPoster(@PathVariable Integer id) {
//        return movieService.findPosterByMovieId(id)
//                .map(content -> ResponseEntity.ok()
//                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
//                        .contentLength(content.length)
//                        .body(content))
//                .orElseGet(ResponseEntity.notFound()::build);
//    }
}
