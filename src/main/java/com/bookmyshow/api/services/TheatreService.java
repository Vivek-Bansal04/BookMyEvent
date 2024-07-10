package com.bookmyshow.api.services;

import com.bookmyshow.api.dtos.AuditoriumDTO;
import com.bookmyshow.api.exceptions.CityNotFoundException;
import com.bookmyshow.api.exceptions.ResourceNotFoundException;
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
        theatre.setCity(cityOptional.get());

        return theatreRepository.save(theatre);
    }

    public List<Theatre> getTheatres(){
        return theatreRepository.findAll();
    }

    public Auditorium addAuditorium(Long theatreId, AuditoriumDTO request){
        Optional<Theatre> theatreOptional = theatreRepository.findById(theatreId);
        if (theatreOptional.isEmpty()) {
            throw new ResourceNotFoundException("Theatre","id",theatreId);
        }

        Auditorium auditorium = new Auditorium();
        auditorium.setName(request.getName());
        auditorium.setCapacity(request.getCapacity());
        auditorium.setTheatre(theatreOptional.get());
        List<Seat> seats = addSeats(request.getSeatCount());
        auditorium.setSeats(seats);

        return auditoriumRepository.save(auditorium);
    }

    // VIP: 10
    // PREMIUM: 20
    // GOLD: 50
    public void addSeats(
            Long auditoriumId,
            Map<SeatType, Integer> seatCount
    ) {
        Auditorium auditorium = auditoriumRepository.findById(auditoriumId).get();
        List<Seat> seats = addSeats(seatCount);
        auditorium.getSeats().addAll(seats);
        auditoriumRepository.save(auditorium);
    }

    public List<Seat> addSeats(Map<SeatType, Integer> seatCount){
        List<Seat> seats = new ArrayList<>();

        for (Map.Entry<SeatType, Integer> entry : seatCount.entrySet()) {
            for (int i = 0; i < entry.getValue(); ++i) {
                Seat seat = new Seat();
                seat.setSeatType(entry.getKey());
                seat.setSeatNumber(entry.getKey().toString() + Integer.toString(i + 1));
                seats.add(seat);
            }
        }
        return seats;
    }
}
