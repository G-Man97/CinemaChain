package com.gman97.cinemachain.service;

import com.gman97.cinemachain.dto.MovieCreateEditDto;
import com.gman97.cinemachain.dto.MovieFindAllDto;
import com.gman97.cinemachain.dto.MovieReadDto;
import com.gman97.cinemachain.dto.PosterUpdateDto;
import com.gman97.cinemachain.entity.Movie;
import com.gman97.cinemachain.mapper.MovieCreateEditMapper;
import com.gman97.cinemachain.mapper.MovieFindAllMapper;
import com.gman97.cinemachain.mapper.MovieReadMapper;
import com.gman97.cinemachain.mapper.PosterUpdateMapper;
import com.gman97.cinemachain.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {


    @InjectMocks
    private MovieService movieService;
    @Mock
    private MovieRepository movieRepository;
    @Mock
    private MovieReadMapper movieReadMapper;
    @Mock
    private MovieFindAllMapper movieFindAllMapper;
    @Mock
    private MovieCreateEditMapper movieCreateEditMapper;
    @Mock
    private PosterUpdateMapper posterUpdateMapper;

    @Test
    void findAll() {
        var foundMovies = List.of(new Movie(), new Movie());

        when(movieRepository.findAll()).thenReturn(foundMovies);
        when(movieFindAllMapper.map(any(Movie.class)))
                .thenReturn(new MovieFindAllDto(1, "test", "18+", "test.jpg"))
                .thenReturn(new MovieFindAllDto(2, "test2", "12+", "test2.jpg"));

        var actual = movieService.findAll();

        assertThat(actual.size()).isEqualTo(foundMovies.size());
        verify(movieRepository).findAll();
        verify(movieFindAllMapper, times(foundMovies.size())).map(any(Movie.class));
    }

    @Test
    void findAllByIsItInRent() {
        var foundMovies = List.of(new Movie(), new Movie());

        when(movieRepository.findAllByIsItInRent(anyBoolean())).thenReturn(foundMovies);
        when(movieFindAllMapper.map(any(Movie.class)))
                .thenReturn(new MovieFindAllDto(1, "test", "18+", "test.jpg"))
                .thenReturn(new MovieFindAllDto(2, "test2", "12+", "test2.jpg"));

        var actual = movieService.findAllByIsItInRent(Boolean.TRUE);

        assertThat(actual.size()).isEqualTo(foundMovies.size());
        verify(movieRepository).findAllByIsItInRent(anyBoolean());
        verify(movieFindAllMapper, times(foundMovies.size())).map(any(Movie.class));
    }

    @Test
    void findByIdWithFetch() {
        when(movieRepository.findByIdWithFetch(anyInt())).thenReturn(Optional.of(new Movie()));
        when(movieReadMapper.map(any(Movie.class))).thenReturn(MovieReadDto.builder().build());

        var actual = movieService.findByIdWithFetch(5);

        assertTrue(actual.isPresent());
        verify(movieRepository).findByIdWithFetch(anyInt());
        verify(movieReadMapper).map(any(Movie.class));
    }

    @Test
    void findById() {
        when(movieRepository.findById(anyInt())).thenReturn(Optional.of(new Movie()));
        when(movieReadMapper.mapWithoutSessions(any(Movie.class))).thenReturn(MovieReadDto.builder().build());

        var actual = movieService.findById(5);

        assertTrue(actual.isPresent());
        verify(movieRepository).findById(anyInt());
        verify(movieReadMapper).mapWithoutSessions(any(Movie.class));
    }

    @Test
    void createMovie() throws IOException {
        MultipartFile image = new MockMultipartFile("test.jpg", InputStream.nullInputStream());
        var movieCreateEditDto = MovieCreateEditDto.builder().poster(image).build();
        var movie = new Movie();

        when(movieCreateEditMapper.map(movieCreateEditDto)).thenReturn(movie);
        when(movieRepository.save(movie)).thenReturn(movie);
        when(movieReadMapper.map(movie)).thenReturn(MovieReadDto.builder().build());

        var actual = movieService.createMovie(movieCreateEditDto);

        assertThat(actual).isNotNull();
        verify(movieCreateEditMapper).map(movieCreateEditDto);
        verify(movieRepository).save(movie);
        verify(movieReadMapper).map(movie);
    }

    @Test
    void updateMovie() {
        var movieCreateEditDto = MovieCreateEditDto.builder().build();
        var movie = new Movie();

        when(movieCreateEditMapper.map(movieCreateEditDto)).thenReturn(movie);
        when(movieRepository.saveAndFlush(movie)).thenReturn(movie);
        when(movieReadMapper.map(movie)).thenReturn(MovieReadDto.builder().build());

        var actual = movieService.updateMovie(movieCreateEditDto);

        assertThat(actual).isNotNull();
        verify(movieCreateEditMapper).map(movieCreateEditDto);
        verify(movieRepository).saveAndFlush(movie);
        verify(movieReadMapper).map(movie);
    }

    @Test
    void updatePoster() throws IOException {
        MultipartFile image = new MockMultipartFile("test.jpg", InputStream.nullInputStream());
        var posterUpdateDto = new PosterUpdateDto(1, image);
        var movie = new Movie();

        when(posterUpdateMapper.map(posterUpdateDto)).thenReturn(movie);
        when(movieRepository.saveAndFlush(movie)).thenReturn(movie);
        when(movieReadMapper.map(movie)).thenReturn(MovieReadDto.builder().build());

        var actual = movieService.updatePoster(posterUpdateDto);

        assertThat(actual).isNotNull();
        verify(posterUpdateMapper).map(posterUpdateDto);
        verify(movieRepository).saveAndFlush(movie);
        verify(movieReadMapper).map(movie);
    }
}