package com.gman97.cinemachain.http.controller;

import com.gman97.cinemachain.dto.FilmSessionCreateDto;
import com.gman97.cinemachain.dto.MovieCreateEditDto;
import com.gman97.cinemachain.dto.PosterUpdateDto;
import com.gman97.cinemachain.entity.Pegi;
import com.gman97.cinemachain.service.CinemaService;
import com.gman97.cinemachain.service.FilmSessionService;
import com.gman97.cinemachain.service.MovieService;
import com.gman97.cinemachain.validation.group.CreateAction;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@RequestMapping("/admin")
@SessionAttributes({"updateMovie"})
@RequiredArgsConstructor
public class AdminController {

    private final MovieService movieService;
    private final CinemaService cinemaService;
    private final FilmSessionService filmSessionService;

    @GetMapping
    public String adminPage(Model model) {
        model.addAttribute("movies", movieService.findAll());
        return "admin";
    }

    @GetMapping("/film-session/{movieId}")
    public String createFilmSessionPage(@PathVariable Integer movieId, Model model,
                                        @ModelAttribute("seance") FilmSessionCreateDto filmSessionCreateDto) {
        model.addAttribute("movieId", movieId);
        model.addAttribute("cinemas", cinemaService.findAll());
        model.addAttribute("seance", filmSessionCreateDto);
        return "createSession";
    }

    @PostMapping("/film-session/{movieId}")
    public String createFilmSession(@PathVariable Integer movieId,
                                    @Valid FilmSessionCreateDto filmSessionCreateDto,
                                    BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("seance",filmSessionCreateDto);
            redirectAttributes.addFlashAttribute("errors", bindingResult);
            return "redirect:/admin/film-session/" + movieId;
        }

        filmSessionService.createFilmSession(filmSessionCreateDto);
        return "redirect:/movies/" + filmSessionCreateDto.getMovieId();
    }

    @GetMapping("/movie")
    public String createMoviePage(Model model, @ModelAttribute("movie") MovieCreateEditDto movieCreateEditDto) {
        model.addAttribute("pegis", List.of(Pegi.values()));
        model.addAttribute("movie", movieCreateEditDto);
        return "createMovie";
    }

    @PostMapping("/movie")
    public String createMovie(@Validated({Default.class, CreateAction.class}) MovieCreateEditDto movieCreateEditDto,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("movie", movieCreateEditDto);
            redirectAttributes.addFlashAttribute("errors", bindingResult);
            return "redirect:/admin/movie";
        }
            return "redirect:/movies/soon/" + movieService.createMovie(movieCreateEditDto).getId();
    }

    @GetMapping("/movies/{id}/update")
    public String updateMoviePage(@PathVariable Integer id, Model model,
                                  @SessionAttribute(value = "updateMovie", required = false) MovieCreateEditDto movieCreateEditDto) {

        if (movieCreateEditDto != null) {
            model.addAttribute("movie", movieCreateEditDto);
            model.addAttribute("pegis", Pegi.values());
            return "updateMovie";
        }

        return movieService.findById(id)
                .map(mayBeMovie -> {
                    model.addAttribute("movie", mayBeMovie);
                    model.addAttribute("pegis", Pegi.values());
                    return "updateMovie";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/movies/{id}/update")
    public String updateMovie(@PathVariable Integer id,
                              @Validated({Default.class}) MovieCreateEditDto movieCreateEditDto,
                              BindingResult bindingResult,
                              Model model,
                              SessionStatus status,
                              RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("updateMovie", movieCreateEditDto);
            redirectAttributes.addFlashAttribute("errors", bindingResult);
            return "redirect:/admin/movies/" + id + "/update";
        }

        var movie = movieService.updateMovie(movieCreateEditDto);

        status.setComplete(); //чистим атрибут сессии "updateMovie" и "posterName" при успешной валидации

        return movie.getIsItInRent()
                ? "redirect:/movies/" + id
                : "redirect:/movies/soon/" + id;
    }

    @GetMapping("/movies/{id}/poster/update")
    public String updatePosterPage(@PathVariable Integer id, Model model) {
        return movieService.findById(id)
                .map(mayBeMovie -> {
                    model.addAttribute("movie", mayBeMovie);
                    return "updatePoster";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/movies/{id}/poster/update")
    public String updatePoster(@PathVariable Integer id,
                              @Valid PosterUpdateDto posterUpdateDto,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult);
            return "redirect:/admin/movies/" + id + "/poster/update";
        }

        var movie = movieService.updatePoster(posterUpdateDto);

        return movie.getIsItInRent()
                ? "redirect:/movies/" + id
                : "redirect:/movies/soon/" + id;
    }

}
