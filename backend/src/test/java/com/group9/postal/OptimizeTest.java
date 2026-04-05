package com.group9.postal;

import com.group9.postal.model.Address;
import com.group9.postal.model.Order;
import com.group9.postal.model.Route;
import com.group9.postal.model.User;
import com.group9.postal.model.Warehouse;
import com.group9.postal.repository.OrderRepository;
import com.group9.postal.repository.RouteRepository;
import com.group9.postal.repository.WarehouseRepository;
import com.group9.postal.service.RouteOptimizationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class OptimizeTest {

    private OrderRepository orderRepository;
    private WarehouseRepository warehouseRepository;
    private RouteRepository routeRepository;
    private RouteOptimizationService routeOptimizationService;

    @BeforeEach
    void setUp() {
        orderRepository = mock(OrderRepository.class);
        warehouseRepository = mock(WarehouseRepository.class);
        routeRepository = mock(RouteRepository.class);

        routeOptimizationService = new RouteOptimizationService();

        ReflectionTestUtils.setField(routeOptimizationService, "repository", orderRepository);
        ReflectionTestUtils.setField(routeOptimizationService, "warehouseRepository", warehouseRepository);
        ReflectionTestUtils.setField(routeOptimizationService, "routeRepository", routeRepository);
    }

    @Test
    void createOptimizedRoutes_returnsEmptyList_whenWarehouseIsNull() {
        List<Route> result = routeOptimizationService.createOptimizedRoutes(
                null,
                new ArrayList<>(),
                new ArrayList<>()
        );

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(routeRepository, never()).save(any(Route.class));
    }

    @Test
    void createOptimizedRoutes_returnsEmptyList_whenOrdersAreEmpty() {
        Warehouse warehouse = buildWarehouse(43.6532, -79.3832);

        List<Route> result = routeOptimizationService.createOptimizedRoutes(
                warehouse,
                new ArrayList<>(),
                List.of(buildDriver("driver1"))
        );

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(routeRepository, never()).save(any(Route.class));
    }

    @Test
    void createOptimizedRoutes_returnsEmptyList_whenDriversAreEmpty() {
        Warehouse warehouse = buildWarehouse(43.6532, -79.3832);

        List<Order> orders = List.of(
                buildPickupOrder(43.7000, -79.4000),
                buildDropoffOrder(43.7200, -79.4200)
        );

        List<Route> result = routeOptimizationService.createOptimizedRoutes(
                warehouse,
                orders,
                new ArrayList<>()
        );

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(routeRepository, never()).save(any(Route.class));
    }

    @Test
    void createOptimizedRoutes_savesRoutesSuccessfully() {
        Warehouse warehouse = buildWarehouse(43.6532, -79.3832);

        User driver1 = buildDriver("driver1@test.com");
        User driver2 = buildDriver("driver2@test.com");

        Order pickup1 = buildPickupOrder(43.7000, -79.4000);
        Order pickup2 = buildPickupOrder(43.7100, -79.4100);
        Order dropoff1 = buildDropoffOrder(43.7200, -79.4200);
        Order dropoff2 = buildDropoffOrder(43.7300, -79.4300);

        List<Order> orders = List.of(pickup1, pickup2, dropoff1, dropoff2);
        List<User> drivers = List.of(driver1, driver2);

        when(routeRepository.save(any(Route.class))).thenAnswer(invocation -> invocation.getArgument(0));

        List<Route> result = routeOptimizationService.createOptimizedRoutes(warehouse, orders, drivers);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(routeRepository, times(2)).save(any(Route.class));
    }

    private Warehouse buildWarehouse(double lat, double lon) {
        Address address = new Address();
        address.setLatitude(lat);
        address.setLongitude(lon);

        Warehouse warehouse = new Warehouse();
        warehouse.setAddress(address);

        return warehouse;
    }

    private User buildDriver(String email) {
        User u = new User();
        u.setEmail(email);
        return u;
    }

    private Order buildPickupOrder(double lat, double lon) {
        Address address = new Address();
        address.setLatitude(lat);
        address.setLongitude(lon);

        Order order = new Order();
        order.setDropoffAddress(address);
        order.setLocationStatus(Order.LocationStatus.FOR_PICKUP);
        return order;
    }

    private Order buildDropoffOrder(double lat, double lon) {
        Address address = new Address();
        address.setLatitude(lat);
        address.setLongitude(lon);

        Order order = new Order();
        order.setDropoffAddress(address);
        order.setLocationStatus(Order.LocationStatus.AT_WAREHOUSE);
        return order;
    }
}