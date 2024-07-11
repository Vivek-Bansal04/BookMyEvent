package com.bookmyshow.api.services;

import com.bookmyshow.api.dtos.ShowDTO;
import com.bookmyshow.api.exceptions.ResourceNotFoundException;
import com.bookmyshow.api.models.*;
import com.bookmyshow.api.repositories.AuditoriumRepository;
import com.bookmyshow.api.repositories.ShowRepository;
import com.bookmyshow.api.repositories.ShowSeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ShowService {
    private AuditoriumRepository auditoriumRepository;
    private ShowRepository showRepository;
    private ShowSeatRepository showSeatRepository;
    private MovieService movieService;

    @Autowired
    public ShowService(AuditoriumRepository auditoriumRepository,
                       ShowRepository showRepository,
                       ShowSeatRepository showSeatRepository,
                       MovieService movieService
    ) {
        this.auditoriumRepository = auditoriumRepository;
        this.showRepository = showRepository;
        this.showSeatRepository = showSeatRepository;
        this.movieService = movieService;
    }

    public Show createShow(ShowDTO request) {
        Show show = new Show();
        show.setStartTime(request.getStartTime());
        show.setEndTime(request.getEndTime());
        show.setStartDate(request.getStartDate());
        show.setEndDate(request.getEndDate());
        show.setLanguage(request.getLanguage());
        show.setShowFeatures(request.getShowFeatures());

        //TODO create service for this
        Auditorium auditorium = auditoriumRepository.findById(request.getAuditoriumId())
                .orElseThrow(() -> new ResourceNotFoundException("Auditorium", "Id", request.getAuditoriumId()));
        Movie movie = movieService.findMovieById(request.getMovieId());

        show.setMovie(movie);
        show.setAuditorium(auditorium);
        List<ShowSeat> showSeats = new ArrayList<>();

        for (Seat seat: auditorium.getSeats()) {
            ShowSeat showSeat = new ShowSeat();
            showSeat.setShow(show);
            showSeat.setSeat(seat);
            showSeat.setState(ShowSeatState.AVAILABLE);
            showSeats.add(showSeat);
        }
        show.setShowSeats(showSeats);
        show.setShowSeatTypes(addSeatType(request.getSeatPricing(),show));
        return showRepository.save(show);
    }

    public List<ShowSeatType> addSeatType(Map<SeatType, Integer> seatPricing,Show show){
        List<ShowSeatType> showSeatTypeList = new ArrayList<>();

        for (Map.Entry<SeatType, Integer> entry : seatPricing.entrySet()) {
                ShowSeatType showSeatType = new ShowSeatType();
                showSeatType.setSeatType(entry.getKey());
                showSeatType.setShow(show);
                showSeatType.setPrice(entry.getValue());
                showSeatTypeList.add(showSeatType);
        }
        return showSeatTypeList;
    }
}
