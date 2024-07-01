package com.bookmyshow.api.services;

import com.bookmyshow.api.dtos.CityDTO;
import com.bookmyshow.api.models.City;
import com.bookmyshow.api.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {
    private CityRepository cityRepository;

    @Autowired
    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public City addCity(CityDTO request) {
        City newCity = new City();
        newCity.setName(request.getName());
        return this.cityRepository.save(newCity);
    }

    public List<City> getCities(){
        return cityRepository.findAll();
    }
}
