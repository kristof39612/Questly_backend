package com.questly.questly_backend.model.LatLong;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LatLongRepository extends JpaRepository<LatLong, Long> {
}