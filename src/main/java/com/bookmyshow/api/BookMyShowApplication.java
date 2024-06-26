package com.bookmyshow.api;

import com.bookmyshow.api.controllers.*;
import com.bookmyshow.api.dtos.CreateUserRequestDto;
import com.bookmyshow.api.models.Language;
import com.bookmyshow.api.models.SeatType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class BookMyShowApplication implements CommandLineRunner {
//    @Autowired
//    @Qualifier("apple")
//    private UserService userService;

	//    @Autowired
	private UserController userController;
	private CityController cityController;
	private TheatreController theatreController;
	private ShowController showController;
	private BookingController bookingController;


	@Autowired
	public BookMyShowApplication(UserController userController,
								 CityController cityController,
								 TheatreController theatreController,
								 ShowController showController,
								 BookingController bookingController
	) {
		this.userController = userController;
		this.cityController = cityController;
		this.theatreController = theatreController;
		this.showController = showController;
		this.bookingController = bookingController;
	}

	public static void main(String[] args) {
		SpringApplication.run(BookMyShowApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		CreateUserRequestDto requestDto = new CreateUserRequestDto();
//		requestDto.setEmail("test@gmail.com");
//
//		this.userController.createUser(requestDto);
//		this.cityController.addCity("Chandigarh");
//		this.theatreController.createTheatre(
//				"PVR",
//				"ABC Road, Chandigarh",
//				1L
//		);
//
//		this.theatreController.addAuditorium(1L, "Audi 1", 123);
//
//		Map<SeatType, Integer> seatsForAudi = new HashMap<>();
//		seatsForAudi.put(SeatType.VIP, 20);
//		seatsForAudi.put(SeatType.GOLD, 100);
//
//		this.theatreController.addSeats(1L, seatsForAudi);
//
//		this.showController.createShow(
//				0L,
//				new Date(),
//				new Date(),
//				1L,
//				null,
//				Language.ENGLISH
//		);
//
//		BookingRunner bookingRunner1 = new BookingRunner(
//				this.bookingController,
//				3L,
//				List.of(58L, 59L, 60L),
//				1L
//		);
//
//		BookingRunner bookingRunner2 = new BookingRunner(
//				this.bookingController,
//				3L,
//				List.of(60L, 61L, 62L),
//				1L
//		);
//
//		Thread t1 = new Thread(bookingRunner1);
//		Thread t2 = new Thread(bookingRunner2);
//		t1.start();
//		t2.start();
	}
}

