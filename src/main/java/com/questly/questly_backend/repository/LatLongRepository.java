package com.questly.questly_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.questly.questly_backend.entity.LatLong;

@Repository
public interface LatLongRepository extends JpaRepository<LatLong, Long> {
}