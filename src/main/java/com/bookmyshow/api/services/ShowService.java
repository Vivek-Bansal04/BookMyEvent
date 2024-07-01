package com.bookmyshow.api.services;

import com.bookmyshow.api.dtos.ShowDTO;
import com.bookmyshow.api.models.*;
import com.bookmyshow.api.repositories.AuditoriumRepository;
import com.bookmyshow.api.repositories.ShowRepository;
import com.bookmyshow.api.repositories.ShowSeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ShowService {
    private AuditoriumRepository auditoriumRepository;
    private ShowRepository showRepository;
    private ShowSeatRepository showSeatRepository;

    @Autowired
    public ShowService(AuditoriumRepository auditoriumRepository,
                       ShowRepository showRepository,
                       ShowSeatRepository showSeatRepository) {
        this.auditoriumRepository = auditoriumRepository;
        this.showRepository = showRepository;
        this.showSeatRepository = showSeatRepository;
    }

    public Show createShow(ShowDTO request) {
        Show show = new Show();
        show.setStartTime(request.getStartTime());
        show.setEndTime(request.getEndTime());
        show.setLanguage(request.getLanguage());

        Auditorium auditorium = auditoriumRepository.findById(request.getAuditoriumId()).get();
        show.setAuditorium(auditorium);
        Show savedShow = showRepository.save(show);
        List<ShowSeat> savedShowSeats = new ArrayList<>();

        for (Seat seat: auditorium.getSeats()) {
            ShowSeat showSeat = new ShowSeat();
            showSeat.setShow(savedShow);
            showSeat.setSeat(seat);
            showSeat.setState(ShowSeatState.AVAILABLE);
            savedShowSeats.add(showSeatRepository.save(showSeat));
        }

        savedShow.setShowSeats(savedShowSeats);

        return showRepository.save(savedShow);
    }
}
