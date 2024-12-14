package com.example.hotel_booking_be_v1.service;

import com.example.hotel_booking_be_v1.exception.InternalServerException;
import com.example.hotel_booking_be_v1.exception.ResourceNotFoundException;
import com.example.hotel_booking_be_v1.model.Room;
import com.example.hotel_booking_be_v1.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomService implements IRoomService{
    private final RoomRepository roomRepository;


    public List<Room> getRoomsByHotelsId(Long hotelId){
        return roomRepository.findByHotelId(hotelId);
    }

    public void addNewRoom(Room room) {
        roomRepository.save(room);
    }

    @Override
    public List<Room> getAllRoomByIds(List<Long> roomIds) {
        return roomRepository.findAllById(roomIds);
    }
}

