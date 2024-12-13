package com.example.hotel_booking_be_v1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate checkInDate; // Ngày nhận phòng

    @Column(nullable = false)
    private LocalDate checkOutDate; // Ngày trả phòng

    @Column(nullable = false)
    private int numberOfGuests; // Số lượng khách

    @Column(nullable = false)
    private BigDecimal totalPrice; // Tổng giá

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status; // Trạng thái đặt phòng

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Người đặt phòng

    @ManyToOne(optional = false)
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel; // Khách sạn

    @ManyToMany
    @JoinTable(
            name = "booking_room_mapping",
            joinColumns = @JoinColumn(name = "booking_id"),
            inverseJoinColumns = @JoinColumn(name = "room_id")
    )
    @JsonIgnore
    private List<Room> rooms; // Danh sách phòng đặt

}
