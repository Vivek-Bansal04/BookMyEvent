package com.bookmyshow.api.services;

import com.bookmyshow.api.exceptions.CityNotFoundException;
import com.bookmyshow.api.models.*;
import com.bookmyshow.api.repositories.AuditoriumRepository;
import com.bookmyshow.api.repositories.CityRepository;
import com.bookmyshow.api.repositories.SeatRepository;
import com.bookmyshow.api.repositories.TheatreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TheatreService {
    private TheatreRepository theatreRepository;
    private CityRepository cityRepository;
    private AuditoriumRepository auditoriumRepository;
    private SeatRepository seatRepository;

    @Autowired
    public TheatreService(TheatreRepository theatreRepository,
                          CityRepository cityRepository,
                          AuditoriumRepository auditoriumRepository,
                          SeatRepository seatRepository) {
        this.theatreRepository = theatreRepository;
        this.cityRepository = cityRepository;
        this.auditoriumRepository = auditoriumRepository;
        this.seatRepository = seatRepository;
    }

    public Theatre createTheatre(
            String name,
            String address,
            Long cityId
    ) throws CityNotFoundException {
        Optional<City> cityOptional = cityRepository.findById(cityId);
        if (cityOptional.isEmpty()) {
            throw new CityNotFoundException("No city with given ID");
        }

        Theatre theatre = new Theatre();
        theatre.setName(name);
        theatre.setAddress(address);

//        Theatre savedTheatre = theatreRepository.save(theatre);
        City dbCity = cityOptional.get();

        if (dbCity.getTheatres() == null) {
            dbCity.setTheatres(new ArrayList<>());
        }
        dbCity.getTheatres().add(theatre);
        this.cityRepository.save(dbCity);

        return theatre;
    }

    public List<Theatre> getTheatres(){
        return theatreRepository.findAll();
    }

    public Theatre addAuditorium(Long theatreId, String name, int capacity) {

        Theatre theatre = theatreRepository.findById(theatreId).get();

        Auditorium auditorium = new Auditorium();
        auditorium.setName(name);
        auditorium.setCapacity(capacity);
        auditorium.setTheatre(theatre);

        Auditorium savedAuditorium = auditoriumRepository.save(auditorium);

        theatre.getAuditoriums().add(savedAuditorium);

        return theatreRepository.save(theatre);
    }

    // VIP: 10
    // PREMIUM: 20
    // GOLD: 50
    public void addSeats(
            Long auditoriumId,
            Map<SeatType, Integer> seatCount
    ) {
        Auditorium auditorium = auditoriumRepository.findById(auditoriumId).get();

        List<Seat> seats = new ArrayList<>();

        for (Map.Entry<SeatType, Integer> entry : seatCount.entrySet()) {
            for (int i = 0; i < entry.getValue(); ++i) {
                Seat seat = new Seat();
                seat.setSeatType(entry.getKey());
                // VIP1 VIP2 VIP3
                // PREMIUM1 PREMIUM2 PREMIUM3
                seat.setSeatNumber(entry.getKey().toString() + Integer.toString(i + 1));
                seats.add(seat);
            }
        }

        List<Seat> savedSeats = seatRepository.saveAll(seats);

        auditorium.setSeats(savedSeats);

        auditoriumRepository.save(auditorium);
    }
}
