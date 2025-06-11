package com.gman97.cinemachain.mapper;

public interface Mapper <F, T> {

    T map(F object);
}
