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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final MovieReadMapper movieReadMapper;
    private final MovieFindAllMapper movieFindAllMapper;
    private final MovieCreateEditMapper movieCreateEditMapper;
    private final PosterUpdateMapper posterUpdateMapper;
    private final ImageService imageService;


    public List<MovieFindAllDto> findAll() {
        return movieRepository.findAll().stream()
                .map(movieFindAllMapper::map)
                .toList();
    }

    public List<MovieFindAllDto> findAllByIsItInRent(Boolean isItInRent) {
        return movieRepository.findAllByIsItInRent(isItInRent).stream()
                .map(movieFindAllMapper::map)
                .toList();
    }

    public Optional<MovieReadDto> findByIdWithFetch(Integer id) {
        return movieRepository.findByIdWithFetch(id)
                .map(movieReadMapper::map);
    }

    public Optional<MovieReadDto> findById(Integer id) {
        return movieRepository.findById(id)
                .map(movieReadMapper::mapWithoutSessions);
    }

    @Transactional
    public MovieReadDto createMovie(MovieCreateEditDto dto) {

        uploadImage(dto.getPoster());

        var movieToSave = movieCreateEditMapper.map(dto);
        var savedMovie = movieRepository.save(movieToSave);

        return movieReadMapper.map(savedMovie);
    }

//    public Optional<byte[]> findPosterByMovieId(Integer id) {
//        return movieRepository.findById(id)
//                .map(Movie::getPoster)
//                .filter(StringUtils::hasText)
//                .flatMap(imageService::get);
//    }

    private void uploadImage(MultipartFile image) {
        if (!image.isEmpty()) {
            try {
                imageService.uploadImage(image.getOriginalFilename(), image.getInputStream());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Transactional
    public MovieReadDto updateMovie(MovieCreateEditDto movieCreateEditDto) {
        Movie movie = movieRepository.saveAndFlush(movieCreateEditMapper.map(movieCreateEditDto));
        return movieReadMapper.mapWithoutSessions(movie);
    }

    @Transactional
    public MovieReadDto updatePoster(PosterUpdateDto posterUpdateDto) {
        uploadImage(posterUpdateDto.getPoster());
        Movie movie = movieRepository.saveAndFlush(posterUpdateMapper.map(posterUpdateDto));
        return movieReadMapper.mapWithoutSessions(movie);
    }
}
