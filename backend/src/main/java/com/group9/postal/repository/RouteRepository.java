package com.group9.postal.repository;

import com.group9.postal.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface RouteRepository extends JpaRepository<Route, Long>{
    List<Route> findByDriverUserId(Long driverId);
    List<Route> findByRouteStatus(String status);
}
