package com.questly.questly_backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.questly.questly_backend.service.LatLongService;
import com.questly.questly_backend.entity.LatLong;
import com.questly.questly_backend.dto.LatLongDTO;

import java.util.List;

@RestController
@RequestMapping("/latlong")
@RequiredArgsConstructor
public class LatLongController {
    private final LatLongService latLongService;

    @GetMapping
    public List<LatLong> getAllLatLongs() {
        return latLongService.findAll();
    }

    @PostMapping
    public ResponseEntity<LatLong> createLatLong(@RequestBody LatLongDTO latLongDto) {
        LatLong savedLatLong = latLongService.saveLatLong(latLongDto);
        return new ResponseEntity<>(savedLatLong, HttpStatus.CREATED);
    }
}
