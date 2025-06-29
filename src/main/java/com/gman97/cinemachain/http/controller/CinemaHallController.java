package com.gman97.cinemachain.http.controller;

import com.gman97.cinemachain.dto.CinemaHallFindAllDto;
import com.gman97.cinemachain.service.CinemaHallService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/cinema-halls")
@RequiredArgsConstructor
public class CinemaHallController {

    private final CinemaHallService cinemaHallService;

    @GetMapping("/by-cinema-id/{id}")
    @ResponseBody
    public CinemaHallFindAllDto[] findByCinemaId(@PathVariable Integer id) {
        return cinemaHallService.findAllByCinemaId(id);
    }
}
