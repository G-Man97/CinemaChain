package com.gman97.cinemachain.integration.http.controller;

import com.gman97.cinemachain.dto.FilmSessionCreateDto;
import com.gman97.cinemachain.dto.MovieCreateEditDto;
import com.gman97.cinemachain.dto.MovieReadDto;
import com.gman97.cinemachain.entity.Pegi;
import com.gman97.cinemachain.integration.IntegrationTestBase;
import com.gman97.cinemachain.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static com.gman97.cinemachain.dto.FilmSessionCreateDto.Fields.*;
import static com.gman97.cinemachain.dto.MovieCreateEditDto.Fields.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.isA;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@Transactional
@AutoConfigureMockMvc
@RequiredArgsConstructor
@WithMockUser(username = "admin@gmail.com", password = "test12345678", authorities = {"ADMIN"})
class AdminControllerTest extends IntegrationTestBase {

    private final MockMvc mockMvc;
    private final MovieRepository movieRepository;

    @Value("${app.image.bucket}")
    private String BUCKET;
    private final String IMAGE_NAME = "testImage.jpg";

    @Test
    void adminPage() throws Exception {
        mockMvc.perform(get("/admin"))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        view().name("admin"),
                        model().attributeExists("movies"),
                        model().attribute("movies", hasSize(4))
                );
    }

    @Test
    void createFilmSessionPage() throws Exception {
        var dto = FilmSessionCreateDto.builder().movieId(1).build();

        mockMvc.perform(get("/admin/film-session/{movieId}", 1)
                .param(movieId, "1")
                .param(beginSession, "")
                .param(cinemaHallId, "")
                .param(date, "")
                .param(ticketCost, "")
        ).andExpectAll(
                status().is2xxSuccessful(),
                view().name("createSession"),
                model().attributeExists("movieId", "cinemas", "seance"),
                model().attribute("movieId", equalTo(1)),
                model().attribute("cinemas", hasSize(2)),
                model().attribute("seance", equalTo(dto))
        );
    }

    @Test
    void createFilmSessionWhenDtoIsValid() throws Exception {
        mockMvc.perform(post("/admin/film-session/{movieId}", 1)
                .param(movieId, "1")
                .param(beginSession, "15:00")
                .param(cinemaHallId, "3")
                .param(date, String.valueOf(LocalDate.now().plusDays(2)))
                .param(ticketCost, "500")
        ).andExpectAll(
                status().is3xxRedirection(),
                redirectedUrlPattern("/movies/{\\d+}")
        );
    }

    @Test
    void createFilmSessionWhenDtoIsInvalid() throws Exception {
        var dto = FilmSessionCreateDto.builder()
                .movieId(1)
                .beginSession(LocalTime.of(4, 0))
                .cinemaHallId(3)
                .date(LocalDate.now().minusDays(2))
                .ticketCost(500)
                .build();

        mockMvc.perform(post("/admin/film-session/{movieId}", 1)
                .param(movieId, "1")
                .param(beginSession, "04:00")
                .param(cinemaHallId, "3")
                .param(date, String.valueOf(LocalDate.now().minusDays(2)))
                .param(ticketCost, "500")
        ).andExpectAll(
                status().is3xxRedirection(),
                redirectedUrlPattern("/admin/film-session/{\\d+}"),
                flash().attributeExists("seance", "errors"),
                flash().attribute("seance", equalTo(dto))
        );
    }

    @Test
    void createMoviePage() throws Exception {
        mockMvc.perform(get("/admin/movie")
                .flashAttr("movie", MovieCreateEditDto.builder().isItInRent(Boolean.FALSE).build())
        ).andExpectAll(
                status().is2xxSuccessful(),
                view().name("createMovie"),
                model().attributeExists("pegis", "movie"),
                model().attribute("pegis", equalTo(List.of(Pegi.values()))),
                model().attribute("movie", equalTo(MovieCreateEditDto.builder().isItInRent(Boolean.FALSE).build()))
        );
    }

    @Test
    void createMovieWhenDtoIsValid() throws Exception {
        mockMvc.perform(
                multipart("/admin/movie")
                        .file(new MockMultipartFile("poster", IMAGE_NAME,
                                "image/jpg", new byte[]{1, 2, 3}))
                        .param(id, "")
                        .param(title, "Matrix")
                        .param(premiere, String.valueOf(LocalDate.now().plusDays(7)))
                        .param(rentEnd, String.valueOf(LocalDate.now().plusDays(27)))
                        .param(duration, "136")
                        .param(pegi, "EIGHTEEN_PLUS")
                        .param(genre, "fantastic, action")
                        .param(countries, "USA, Australia")
                        .param(composer, "Don Davis")
                        .param(producer, "Lana Wachowski, Lilly Wachowski")
                        .param(actors, "actors")
                        .param(description, "description")
                        .param(isItInRent, "false")
        ).andExpectAll(
                status().is3xxRedirection(),
                redirectedUrlPattern("/movies/soon/{\\d+}")
        );
    }

    @Test
    void createMovieWhenDtoIsInvalid() throws Exception {
        var file = new MockMultipartFile("poster", "",
                "image/jpg", new byte[0]);
        var dto = MovieCreateEditDto.builder()
                .rentEnd(LocalDate.now().minusDays(27))
                .duration(136)
                .pegi(Pegi.EIGHTEEN_PLUS)
                .genre("fantastic, action")
                .actors("actors")
                .poster(file)
                .isItInRent(Boolean.FALSE)
                .build();
        mockMvc.perform(
                multipart("/admin/movie")
                        .file(file)
                        .param(id, "")
                        .param(title, (String) null)
                        .param(premiere, (String) null)
                        .param(rentEnd, String.valueOf(LocalDate.now().minusDays(27)))
                        .param(duration, "136")
                        .param(pegi, "EIGHTEEN_PLUS")
                        .param(genre, "fantastic, action")
                        .param(countries, (String) null)
                        .param(composer, (String) null)
                        .param(producer, (String) null)
                        .param(actors, "actors")
                        .param(description, (String) null)
                        .param(isItInRent, "false")
        ).andExpectAll(
                status().is3xxRedirection(),
                redirectedUrl("/admin/movie"),
                flash().attributeExists("movie", "errors"),
                flash().attribute("movie", equalTo(dto))
        );
    }

    @Test
    void updateMoviePageWhenDtoIsNull() throws Exception {
        mockMvc.perform(get("/admin/movies/{id}/update", 1))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        view().name("updateMovie"),
                        model().attributeExists("movie", "pegis"),
                        model().attribute("movie", isA(MovieReadDto.class)),
                        model().attribute("pegis", equalTo(Pegi.values()))
                );
    }

    @Test
    void updateMoviePageWhenDtoIsNotNull() throws Exception {
        var movie = movieRepository.findById(1).get();
        var dto = MovieCreateEditDto.builder()
                .id(movie.getId())
                .title(movie.getTitle() + " test")
                .premiere(movie.getPremiere())
                .rentEnd(movie.getRentEnd())
                .duration(movie.getDuration())
                .pegi(movie.getPegi())
                .genre(movie.getGenre())
                .countries(movie.getCountries())
                .composer(movie.getComposer())
                .producer(movie.getProducer())
                .actors(movie.getActors())
                .description(movie.getDescription() + " test")
                .isItInRent(movie.getIsItInRent())
                .build();

        mockMvc.perform(get("/admin/movies/{id}/update", 1)
                        .sessionAttr("updateMovie", dto)
                )
                .andExpectAll(
                        status().is2xxSuccessful(),
                        view().name("updateMovie"),
                        model().attributeExists("movie", "pegis"),
                        model().attribute("movie", isA(MovieCreateEditDto.class)),
                        model().attribute("pegis", equalTo(Pegi.values()))
                );
    }

    @Test
    void updateMovieWhenDtoIsValid() throws Exception {
        var movie = movieRepository.findById(1).get();

        mockMvc.perform(post("/admin/movies/{id}/update", 1)
                .param(id, movie.getId().toString())
                .param(title, movie.getTitle() + " test")
                .param(premiere, String.valueOf(movie.getPremiere()))
                .param(rentEnd, String.valueOf(movie.getRentEnd()))
                .param(duration, String.valueOf(movie.getDuration()))
                .param(pegi, String.valueOf(movie.getPegi()))
                .param(genre, movie.getGenre())
                .param(countries, movie.getCountries())
                .param(composer, movie.getComposer())
                .param(producer, movie.getProducer())
                .param(actors, movie.getActors())
                .param(description, movie.getDescription() + " test")
                .param(isItInRent, String.valueOf(movie.getIsItInRent()))
        ).andExpectAll(
                status().is3xxRedirection(),
                redirectedUrlPattern("/movies/{\\d+}"),
                model().attributeDoesNotExist("updateMovie", "errors")
        );
    }

    @Test
    void updateMovieWhenDtoIsInvalid() throws Exception {
        var movie = movieRepository.findById(1).get();

        mockMvc.perform(post("/admin/movies/{id}/update", 1)
                .param(id, movie.getId().toString())
                .param(title, (String) null)
                .param(premiere, String.valueOf(movie.getPremiere()))
                .param(rentEnd, String.valueOf(movie.getRentEnd()))
                .param(duration, String.valueOf(movie.getDuration()))
                .param(pegi, String.valueOf(movie.getPegi()))
                .param(genre, movie.getGenre())
                .param(countries, movie.getCountries())
                .param(composer, movie.getComposer())
                .param(producer, movie.getProducer())
                .param(actors, movie.getActors())
                .param(description, (String) null)
                .param(isItInRent, String.valueOf(movie.getIsItInRent()))
        ).andExpectAll(
                status().is3xxRedirection(),
                redirectedUrlPattern("/admin/movies/{\\d+}/update"),
                flash().attributeExists("errors")
        );
    }

    @Test
    void updatePosterPage() throws Exception {
        mockMvc.perform(get("/admin/movies/{id}/poster/update", 1))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        view().name("updatePoster")
                );
    }

    @Test
    void updatePosterWhenDtoIsValid() throws Exception {
        mockMvc.perform(multipart("/admin/movies/{id}/poster/update", 1)
                .file(new MockMultipartFile("poster", IMAGE_NAME, "image/jpg", new byte[]{1, 2, 3}))
                .param("movieId", "1")
        ).andExpectAll(
                status().is3xxRedirection(),
                redirectedUrlPattern("/movies/{\\d+}"),
                model().attributeDoesNotExist("errors")
        );
    }

    @Test
    void updatePosterWhenDtoIsInvalid() throws Exception {
        mockMvc.perform(multipart("/admin/movies/{id}/poster/update", 1)
                .file(new MockMultipartFile("poster", new byte[0]))
                .param("movieId", "1")
        ).andExpectAll(
                status().is3xxRedirection(),
                redirectedUrlPattern("/admin/movies/{\\d+}/poster/update"),
                flash().attributeExists("errors")
        );
    }

    @AfterEach
    void clean() {
        try {
            Files.deleteIfExists(Path.of(BUCKET, IMAGE_NAME));
            Files.deleteIfExists(Path.of(BUCKET));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}