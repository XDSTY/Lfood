package com.xdsty.userservice.mapper;

import com.xdsty.userservice.entity.City;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CityMapper {

    List<City> selectAllCity();

}
