package com.questly.questly_backend.model.LatLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LatLongService {
    private final LatLongRepository latLongRepository;

    @Autowired
    public LatLongService(LatLongRepository latLongRepository) {this.latLongRepository = latLongRepository;}

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
