package com.example.hotel_booking_be_v1.controller;


import com.example.hotel_booking_be_v1.model.*;
import com.example.hotel_booking_be_v1.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

//@CrossOrigin("http://localhost:5173")
@RequiredArgsConstructor
@RestController
@RequestMapping("/bookings")
public class BookingController {
    private final IBookingService bookingService;
    private final IRoomService roomService;
    private final IUserService userService;
    private final IHotelService hotelService;


    @PostMapping("/add")
    public ResponseEntity<?> addBooking(@RequestBody BookingDTO bookingDTO) {
        try {
            // Tạo đối tượng Booking từ BookingDTO
            Booking booking = new Booking();
            booking.setCheckInDate(bookingDTO.getCheckInDate());
            booking.setCheckOutDate(bookingDTO.getCheckOutDate());
            booking.setNumberOfGuests(bookingDTO.getNumberOfGuests());
            booking.setStatus(bookingDTO.getStatus());

            // Lấy đối tượng User từ userId trong DTO
            User user = userService.getUserById(bookingDTO.getUserId());
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
            }
            booking.setUser(user);

            // Lấy đối tượng Hotel từ hotelId trong DTO
            Hotel hotel = hotelService.getHotelById1(bookingDTO.getHotelId());
            if (hotel == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Hotel not found.");
            }
            booking.setHotel(hotel);

            // Lấy danh sách Room từ roomIds trong DTO
            List<Room> rooms = roomService.getAllRoomByIds(bookingDTO.getRoomIds());
            if (rooms.size() != bookingDTO.getRoomIds().size()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Some rooms not found.");
            }
            booking.setRooms(rooms);

            long days = ChronoUnit.DAYS.between(bookingDTO.getCheckInDate(), bookingDTO.getCheckOutDate());

            if (days <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Check-out date must be after check-in date.");
            }

            BigDecimal totalPrice = rooms.stream()
                    .map(Room::getRoomPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add).multiply(BigDecimal.valueOf(days));
            booking.setTotalPrice(totalPrice);

            // Lưu Booking vào cơ sở dữ liệu
            bookingService.saveBooking(booking);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Booking created successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Booking>> getBookingsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(bookingService.findBookingsByUser(userId));
    }

    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<List<Booking>> getBookingsByHotel(@PathVariable Long hotelId) {
        return ResponseEntity.ok(bookingService.findBookingsByHotel(hotelId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Booking>> getBookingsByStatus(@PathVariable BookingStatus status) {
        return ResponseEntity.ok(bookingService.findBookingsByStatus(status));
    }
}
