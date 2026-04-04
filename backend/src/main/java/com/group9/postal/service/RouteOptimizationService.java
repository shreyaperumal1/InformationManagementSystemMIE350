package com.group9.postal.service;

import com.group9.postal.model.Order;
import com.group9.postal.model.User;
import com.group9.postal.model.Warehouse;
import com.group9.postal.repository.OrderRepository;
import com.group9.postal.repository.RouteRepository;
import com.group9.postal.repository.UserRepository;
import com.group9.postal.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.group9.postal.model.Address;
import com.group9.postal.controller.RouteController;

import java.sql.Driver;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

@Service
public class RouteOptimizationService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private RouteRepository routeRepository;

    private List<Address> optimizeRoute(Address warehouse, List<Address> stops) {
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

    private double clusterDistance(List<Order> cluster1, List<Order> cluster2) {
        double minDist = Double.MAX_VALUE;

        for (Order o1 : cluster1) {
            for (Order o2 : cluster2) {
                Address a1 = o1.getDropoffAddress();
                Address a2 = o2.getDropoffAddress();

                double d = distance(
                        a1.getLatitude(), a1.getLongitude(),
                        a2.getLatitude(), a2.getLongitude()
                );

                if (d < minDist) {
                    minDist = d;
                }
            }
        }

        return minDist;
    }

    private List<List<Order>> clusters(int cluCount, List<Order> orders) {
        List<List<Order>> clusters = new ArrayList<>();

        // Step 1: Put each order in its own cluster
        for (int i = 0; i < orders.size(); i++) {
            List<Order> cluster = new ArrayList<>();
            cluster.add(orders.get(i));
            clusters.add(cluster);
        }

        // Step 2: Merge close clusters until only cluCount remain
        while (clusters.size() > cluCount) {
            double minDist = Double.MAX_VALUE;
            int c1 = -1;
            int c2 = -1;

            for (int i = 0; i < clusters.size(); i++) {
                for (int j = i + 1; j < clusters.size(); j++) {
                    double d = clusterDistance(clusters.get(i), clusters.get(j));

                    if (d < minDist) {
                        minDist = d;
                        c1 = i;
                        c2 = j;
                    }
                }
            }

            // Merge cluster c2 into c1
            clusters.get(c1).addAll(clusters.get(c2));
            clusters.remove(c2);
        }

        return clusters;
    }

    public void createOptimizedRoutes(Warehouse warehouse, List<Order> orders, List<User> drivers) {
       int routeNum = drivers.size();
       List<List<Order>> clustered_orders = clusters(routeNum, orders);
       RouteController routeController = new RouteController(routeRepository);
       for (int i = 0; i < clustered_orders.size(); i++) {
           List<Order> route = optimizeRoute(clustered_orders[i]);
           routeController.createRoute(drivers[i], warehouse, null, null, "SCHEDULED", route);
       }
    }

}