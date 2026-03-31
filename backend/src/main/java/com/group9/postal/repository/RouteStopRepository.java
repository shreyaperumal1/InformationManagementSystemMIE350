package com.group9.postal.repository;

import com.group9.postal.model.RouteStop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteStopRepository extends JpaRepository<RouteStop, Long> {
}