package com.gman97.cinemachain.integration.service;

import com.gman97.cinemachain.dto.MovieCreateEditDto;
import com.gman97.cinemachain.dto.PosterUpdateDto;
import com.gman97.cinemachain.entity.Pegi;
import com.gman97.cinemachain.integration.IntegrationTestBase;
import com.gman97.cinemachain.mapper.MovieFindAllMapper;
import com.gman97.cinemachain.mapper.MovieReadMapper;
import com.gman97.cinemachain.repository.MovieRepository;
import com.gman97.cinemachain.service.ImageService;
import com.gman97.cinemachain.service.MovieService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
@RequiredArgsConstructor
class MovieServiceTest extends IntegrationTestBase {

    private final MovieService movieService;
    private final ImageService imageService;
    private final MovieRepository movieRepository;
    private final MovieReadMapper movieReadMapper;
    private final MovieFindAllMapper movieFindAllMapper;
    private final EntityManager entityManager;

    private final String BUCKET = "C:/Users/Georgy/IdeaProjects/CinemaChain/testDir";
    private final String IMAGE_NAME = "testImage.jpg";

    @Test
    void findAll() {
        // Скрипт data.sql накатывает 4 фильма: 2 в прокате и 2 ещё не в прокате

        var actual = movieService.findAll();

        assertThat(actual).hasSize(4);
    }

    @Test
    void findAllByIsItInRent() {
        var movie1InRent = movieRepository.findById(1).get();
        var movie2InRent = movieRepository.findById(2).get();

        var actual = movieService.findAllByIsItInRent(Boolean.TRUE);

        assertThat(actual).hasSize(2);
        assertThat(actual).contains(movieFindAllMapper.map(movie1InRent), movieFindAllMapper.map(movie2InRent));
    }

    @Test
    void findById() {
        var actual = movieService.findById(1);

        assertTrue(actual.isPresent());
        assertThat(actual.get().getTitle()).isEqualTo("Ono");
    }

    @Test
    void createMovie() {
        var dto = MovieCreateEditDto.builder()
                .title("test")
                .actors("test")
                .genre("test")
                .composer("test")
                .countries("test")
                .description("test")
                .duration(96)
                .pegi(Pegi.SIXTEEN_PLUS)
                .isItInRent(Boolean.FALSE)
                .premiere(LocalDate.now().plusDays(5))
                .producer("test")
                .rentEnd(LocalDate.now().plusDays(30))
                .poster(new MockMultipartFile(IMAGE_NAME, IMAGE_NAME, "application-octet-stream", new byte[]{1, 2, 3}))
                .build();
        ReflectionTestUtils.setField(imageService, "bucket", BUCKET);

        var actual = movieService.createMovie(dto);

        assertNotNull(actual);
        assertThat(actual).isEqualTo(movieReadMapper.map(movieRepository.findById(actual.getId()).get()));
        assertTrue(new File(BUCKET, IMAGE_NAME).exists());
    }

    @Test
    void updateMovie() {
        var movie = movieRepository.findByIdWithFetch(1).get();
        var dto = MovieCreateEditDto.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .actors(movie.getActors())
                .genre("test")
                .composer("test")
                .countries(movie.getCountries())
                .description(movie.getDescription() + " test")
                .duration(movie.getDuration())
                .pegi(movie.getPegi())
                .isItInRent(movie.getIsItInRent())
                .premiere(movie.getPremiere())
                .producer(movie.getProducer())
                .rentEnd(movie.getRentEnd())
                .poster(null)
                .build();

        var actual = movieService.updateMovie(dto);

        entityManager.refresh(movie);
        assertThat(actual).isEqualTo(movieReadMapper.map(movie));
    }

    @Test
    void updatePoster() {
        var dto = new PosterUpdateDto(1, new MockMultipartFile(IMAGE_NAME, IMAGE_NAME, "application-octet-stream", new byte[]{1, 2, 3}));
        ReflectionTestUtils.setField(imageService, "bucket", BUCKET);

        var actual = movieService.updatePoster(dto);

        assertThat(actual.getPoster()).isEqualTo(dto.getPoster().getOriginalFilename());
        assertTrue(new File(BUCKET, IMAGE_NAME).exists());
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