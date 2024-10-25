package com.questly.questly_backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.questly.questly_backend.repository.LatLongRepository;
import com.questly.questly_backend.entity.LatLong;
import com.questly.questly_backend.dto.LatLongDTO;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LatLongService {
    private final LatLongRepository latLongRepository;

    public List<LatLong> findAll() {
        return latLongRepository.findAll();
    }

    public LatLong save(LatLong latLong) {
        return latLongRepository.save(latLong);
    }

    public LatLong saveLatLong(LatLongDTO latLongDTO) {
        LatLong latLong = new LatLong();
        latLong.setLat(latLongDTO.getLat());
        latLong.setLng(latLongDTO.getLng());
        return latLongRepository.save(latLong);
    }
}
