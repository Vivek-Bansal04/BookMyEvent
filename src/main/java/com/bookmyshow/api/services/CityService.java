package com.bookmyshow.api.services;

import com.bookmyshow.api.models.City;
import com.bookmyshow.api.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityService {
    private CityRepository cityRepository;

    @Autowired
    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public City addCity(String name) {
        City newCity = new City();
        newCity.setName(name);
        return this.cityRepository.save(newCity);
    }
}
