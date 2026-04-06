package com.group9.postal.service;

import com.group9.postal.model.*;
import com.group9.postal.repository.OrderRepository;
import com.group9.postal.repository.RouteRepository;
import com.group9.postal.repository.WarehouseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class RouteOptimizationService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private RouteRepository routeRepository;

    private List<List<Order>> splitPickupDelivery(List<Order> currentOrders) {
        int order_count = currentOrders.size();
        List<Order> pickups = new ArrayList<>();
        List<Order> deliveries = new ArrayList<>();
        for (Order order : currentOrders) {
            if (order.getLocationStatus().equals(Order.LocationStatus.FOR_PICKUP)) {
                pickups.add(order);
            }
            else if (order.getLocationStatus().equals(Order.LocationStatus.AT_WAREHOUSE)) {
                deliveries.add(order);
            }
        }

        return List.of(pickups, deliveries);
    }

    private List<Order> optimizeDropoffRoute(Address warehouse, List<Order> stops) {
        List<Order> unvisited = new ArrayList<>(stops);
        List<Order> optimized = new ArrayList<>();
        Address current = warehouse;

        while (!unvisited.isEmpty()) {
            Order nearest = findNearestOrder(current, unvisited);
            optimized.add(nearest);
            unvisited.remove(nearest);
            current = nearest.getDropoffAddress();
        }

        return optimized;
    }

    private List<Order> optimizePickupRoute(Address warehouse, List<Order> stops) {
        List<Order> optimized = optimizeDropoffRoute(warehouse, stops);
        List<Order> reversed = new ArrayList<>(optimized);
        Collections.reverse(reversed);
        return reversed;
    }

    private Order findNearestOrder(Address from, List<Order> candidates) {
        return candidates.stream()
                .min(Comparator.comparingDouble(order ->
                        distance(
                                from.getLatitude(),
                                from.getLongitude(),
                                order.getDropoffAddress().getLatitude(),
                                order.getDropoffAddress().getLongitude()
                        )))
                .orElseThrow();
    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double R = 6371.0;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        return R * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
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

        for (Order order : orders) {
            List<Order> cluster = new ArrayList<>();
            cluster.add(order);
            clusters.add(cluster);
        }

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

            if (c1 == -1 || c2 == -1) {
                break;
            }

            clusters.get(c1).addAll(clusters.get(c2));
            clusters.remove(c2);
        }

        return clusters;
    }

    public List<Route> createOptimizedRoutes(Warehouse warehouse, List<Order> orders, List<User> drivers) {
        List<Route> createdRoutes = new ArrayList<>();

        if (warehouse == null || warehouse.getAddress() == null || orders == null || orders.isEmpty()
                || drivers == null || drivers.isEmpty()) {
            return createdRoutes;
        }

        int routeNum = Math.min(drivers.size(), orders.size());

        List<List<Order>> split = splitPickupDelivery(orders);
        List<Order> pickups = split.get(0);
        List<Order> dropoffs = split.get(1);

        int totalPicks = pickups.size();
        int totalDrops = dropoffs.size();

        int pickupNum = (int) Math.round(
                ((double) totalPicks / (totalDrops + totalPicks)) * routeNum
        );
        int dropoffNum = routeNum - pickupNum;

        List<List<Order>> clusteredPickupOrders = clusters(pickupNum, pickups);
        List<List<Order>> clusteredDropoffOrders = clusters(dropoffNum, dropoffs);

        int driverIndex = 0;

        // 🔹 PICKUP ROUTES
        for (int i = 0; i < clusteredPickupOrders.size() && driverIndex < drivers.size(); i++) {
            List<Order> cluster = clusteredPickupOrders.get(i);
            List<Order> optimizedOrders = optimizePickupRoute(warehouse.getAddress(), cluster);

            User driver = drivers.get(driverIndex++);

            Route route = new Route();
            route.setWarehouse(warehouse);
            route.setRouteStatus(Route.Status.SCHEDULED);
            route.setDriver(driver);
            route.setStops(optimizedOrders);
            route.setPlannedStartTime(null);
            route.setPlannedEndTime(null);

            Route savedRoute = routeRepository.save(route);
            createdRoutes.add(savedRoute);
        }

        // 🔹 DROPOFF ROUTES
        for (int i = 0; i < clusteredDropoffOrders.size() && driverIndex < drivers.size(); i++) {
            List<Order> cluster = clusteredDropoffOrders.get(i);
            List<Order> optimizedOrders = optimizeDropoffRoute(warehouse.getAddress(), cluster);

            User driver = drivers.get(driverIndex++);

            Route route = new Route();
            route.setWarehouse(warehouse);
            route.setRouteStatus(Route.Status.SCHEDULED);
            route.setDriver(driver);
            route.setStops(optimizedOrders);
            route.setPlannedStartTime(null);
            route.setPlannedEndTime(null);

            Route savedRoute = routeRepository.save(route);
            createdRoutes.add(savedRoute);
        }

        return createdRoutes;
    }
}