package com.example.hotel_booking_be_v1.service;

import com.example.hotel_booking_be_v1.model.Ward;
import com.example.hotel_booking_be_v1.repository.WardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WardService implements IWardService {
    private final WardRepository wardRepository;

    @Autowired
    public WardService(WardRepository wardRepository) {
        this.wardRepository = wardRepository;
    }

    public List<Ward> findAllwards() {
        return wardRepository.findAll();
    }

    public List<Ward> findwardsByDistrict(Long districtId) {
        return wardRepository.findByDistrictId(districtId);
    }

    public List<Ward> searchwardsByName(String name) {
        return wardRepository.findByNameContaining(name);
    }
}