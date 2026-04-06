package com.group9.postal;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ObjectNode;
import com.group9.postal.model.Route;
import com.group9.postal.model.User;
import com.group9.postal.model.Warehouse;
import com.group9.postal.repository.RouteRepository;
import com.group9.postal.repository.UserRepository;
import com.group9.postal.repository.WarehouseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OptimizeTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private RouteRepository routeRepository;

    @Test
    void optimizeRoute_returnsEmptyList_whenWarehouseIdIsInvalid() throws Exception {
        ObjectNode requestJson = objectMapper.createObjectNode();
        requestJson.put("warehouseId", -1);
        requestJson.putArray("driverIds");
        requestJson.putArray("orderIds");

        MvcResult result = mockMvc.perform(
                        post("/route/optimize")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestJson.toString())
                )
                .andExpect(status().isOk())
                .andReturn();

        JsonNode response = objectMapper.readTree(result.getResponse().getContentAsString());

        assertTrue(response.isArray(), "Expected array response");
        assertEquals(0, response.size(), "Expected empty list for invalid warehouse");
    }

    @Test
    void optimizeRoute_returnsEmptyList_whenNoOrdersProvided() throws Exception {
        Warehouse warehouse = warehouseRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new AssertionError("No warehouse found in test data"));

        User driver = userRepository.findByRole(User.Role.DRIVER)
                .stream()
                .findFirst()
                .orElseThrow(() -> new AssertionError("No DRIVER user found in test data"));

        ObjectNode requestJson = objectMapper.createObjectNode();
        requestJson.put("warehouseId", warehouse.getWarehouseId());
        requestJson.putArray("orderIds");
        requestJson.putArray("driverIds").add(driver.getUserId());

        MvcResult result = mockMvc.perform(
                        post("/route/optimize")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestJson.toString())
                )
                .andExpect(status().isOk())
                .andReturn();

        JsonNode response = objectMapper.readTree(result.getResponse().getContentAsString());

        assertTrue(response.isArray(), "Expected array response");
        assertEquals(0, response.size(), "Expected empty list when no orders provided");
    }

    @Test
    void optimizeRoute_returnsEmptyList_whenNoDriversProvided() throws Exception {
        Warehouse warehouse = warehouseRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new AssertionError("No warehouse found in test data"));

        ObjectNode requestJson = objectMapper.createObjectNode();
        requestJson.put("warehouseId", warehouse.getWarehouseId());
        requestJson.putArray("driverIds");
        requestJson.putArray("orderIds").add(1).add(2);

        MvcResult result = mockMvc.perform(
                        post("/route/optimize")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestJson.toString())
                )
                .andExpect(status().isOk())
                .andReturn();

        JsonNode response = objectMapper.readTree(result.getResponse().getContentAsString());

        assertTrue(response.isArray(), "Expected array response");
        assertEquals(0, response.size(), "Expected empty list when no drivers provided");
    }

    @Test
    void optimizeRoute_createsAndSavesRoutes_whenValidInputProvided() throws Exception {
        // 1) Find seeded warehouse
        Warehouse warehouse = warehouseRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new AssertionError("No warehouse found in test data"));

        // 2) Find seeded drivers
        List<User> drivers = userRepository.findByRole(User.Role.DRIVER);
        assertTrue(drivers.size() >= 2, "Expected at least 2 DRIVER users in test data");

        // 3) Build request with warehouse, drivers, and seeded order IDs
        ObjectNode requestJson = objectMapper.createObjectNode();
        requestJson.put("warehouseId", warehouse.getWarehouseId());

        var driverIds = requestJson.putArray("driverIds");
        driverIds.add(drivers.get(0).getUserId());
        driverIds.add(drivers.get(1).getUserId());

        // Use order IDs seeded in the test database
        var orderIds = requestJson.putArray("orderIds");
        orderIds.add(1).add(2).add(3).add(4);

        // 4) Call optimize endpoint
        MvcResult result = mockMvc.perform(
                        post("/route/optimize")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestJson.toString())
                )
                .andExpect(status().isOk())
                .andReturn();

        JsonNode response = objectMapper.readTree(result.getResponse().getContentAsString());

        // 5) Assert response shape
        assertTrue(response.isArray(), "Expected array of routes");
        assertEquals(2, response.size(), "Expected one route per driver");

        // 6) Assert each route has required fields and is persisted in DB
        for (JsonNode routeNode : response) {
            assertTrue(routeNode.has("routeId"), "Route should have a routeId");
            assertTrue(routeNode.has("stops"), "Route should contain stops");
            assertTrue(routeNode.get("stops").isArray(), "Stops should be an array");
            assertTrue(routeNode.get("stops").size() > 0, "Route should have at least one stop");

            long routeId = routeNode.get("routeId").asLong();

            // 7) Double-check from database
            Route savedRoute = routeRepository.findById(routeId)
                    .orElseThrow(() -> new AssertionError("Route " + routeId + " not found in DB after optimization"));

            assertNotNull(savedRoute.getDriver(), "Saved route should have an assigned driver");
        }
    }
}