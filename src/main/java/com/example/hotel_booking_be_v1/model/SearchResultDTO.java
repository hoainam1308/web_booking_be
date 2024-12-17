package com.example.hotel_booking_be_v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchResultDTO {

    private String type; // "Province", "District", "Ward", "Hotel"
    private String name;
    private Long id; // ID của đối tượng
}
