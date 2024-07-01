package com.bookmyshow.api.controllers;

import com.bookmyshow.api.dtos.CityDTO;
import com.bookmyshow.api.models.City;
import com.bookmyshow.api.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/cities")
public class CityController {
    private CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<City> addCity(@RequestBody CityDTO request) {
        return ResponseEntity.ok(cityService.addCity(request));
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<City>> getCities() {
        return ResponseEntity.ok(cityService.getCities());
    }
}
