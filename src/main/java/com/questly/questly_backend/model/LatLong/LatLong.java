package com.questly.questly_backend.model.LatLong;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class LatLong {
    private float latitude;
    private float longitude;
}
