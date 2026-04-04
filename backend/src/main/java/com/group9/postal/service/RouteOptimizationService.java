package com.group9.postal.service;

import org.springframework.stereotype.Service;
import com.group9.postal.model.Address;

import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

@Service
public class RouteOptimizationService {

    public List<Address> optimizeRoute(Address warehouse, List<Address> stops) {
        List<Address> unvisited = new ArrayList<>(stops);
        List<Address> optimized = new ArrayList<>();
        Address current = warehouse;

        while (!unvisited.isEmpty()) {
            Address nearest = findNearest(current, unvisited);
            optimized.add(nearest);
            unvisited.remove(nearest);
            current = nearest;
        }
        return optimized;
    }

    private Address findNearest(Address from, List<Address> candidates) {
        return candidates.stream()
                .min(Comparator.comparingDouble(a ->
                        distance(from.getLatitude(), from.getLongitude(),
                                a.getLatitude(), a.getLongitude())))
                .orElseThrow();
    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        // Haversine formula for real distance
        double R = 6371;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) *
                        Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon/2) * Math.sin(dLon/2);
        return R * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
    }

}