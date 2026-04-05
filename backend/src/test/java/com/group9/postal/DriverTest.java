package com.group9.postal;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.group9.postal.model.RouteStop;
import com.group9.postal.model.User;
import com.group9.postal.repository.RouteStopRepository;
import com.group9.postal.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class DriverTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RouteStopRepository routeStopRepository;

    @Test
    void driverCanViewRouteAndCompleteEveryStop() throws Exception {
        // 1) Find a seeded driver in the test database
        User driver = userRepository.findByRole(User.Role.DRIVER)
                .stream()
                .findFirst()
                .orElseThrow(() -> new AssertionError("No DRIVER user found in test data"));

        // IMPORTANT:
        // Your login code compares request password directly to passwordHash.
        // So for this project, use whatever is stored in passwordHash as the login password.
        String email = driver.getEmail();
        String password = driver.getPasswordHash();

        // 2) Login
        ObjectNode loginJson = objectMapper.createObjectNode();
        loginJson.put("email", email);
        loginJson.put("password", password);

        MvcResult loginResult = mockMvc.perform(
                        post("/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(loginJson.toString())
                )
                .andExpect(status().isOk())
                .andReturn();

        JsonNode loginResponse = objectMapper.readTree(
                loginResult.getResponse().getContentAsString()
        );

        long driverId = loginResponse.get("userId").asLong();
        assertEquals(driver.getUserId(), driverId);

        // 3) Get routes assigned to this driver
        MvcResult routesResult = mockMvc.perform(
                        get("/route/driver/" + driverId)
                )
                .andExpect(status().isOk())
                .andReturn();

        JsonNode routesJson = objectMapper.readTree(
                routesResult.getResponse().getContentAsString()
        );

        assertTrue(routesJson.isArray(), "Expected route list");
        assertTrue(routesJson.size() > 0, "Driver has no assigned routes in test data");

        long routeId = routesJson.get(0).get("routeId").asLong();

        // 4) Get full route details so we can access stops
        MvcResult routeResult = mockMvc.perform(
                        get("/route/" + routeId)
                )
                .andExpect(status().isOk())
                .andReturn();

        JsonNode routeJson = objectMapper.readTree(
                routeResult.getResponse().getContentAsString()
        );

        JsonNode stopsJson = routeJson.get("stops");
        assertNotNull(stopsJson, "Route response does not contain stops");
        assertTrue(stopsJson.isArray(), "Stops should be an array");
        assertTrue(stopsJson.size() > 0, "Route has no stops");

        // 5) Read stop ids in order
        List<StopInfo> stops = new ArrayList<>();
        for (JsonNode stopNode : stopsJson) {
            long stopId = stopNode.get("stopId").asLong();
            int stopSequence = stopNode.get("stopSequence").asInt();
            stops.add(new StopInfo(stopId, stopSequence));
        }

        stops.sort(Comparator.comparingInt(StopInfo::getStopSequence));

        // 6) Complete each stop one by one
        for (StopInfo stop : stops) {
            MvcResult completeResult = mockMvc.perform(
                            put("/routeStop/" + stop.getStopId() + "/complete")
                    )
                    .andExpect(status().isOk())
                    .andReturn();

            JsonNode completedStopJson = objectMapper.readTree(
                    completeResult.getResponse().getContentAsString()
            );

            assertEquals(stop.getStopId(), completedStopJson.get("stopId").asLong());
            assertNotNull(completedStopJson.get("completedTime"));
            assertFalse(completedStopJson.get("completedTime").isNull());

            // 7) Double-check from database
            RouteStop updatedStop = routeStopRepository.findById(stop.getStopId())
                    .orElseThrow(() -> new AssertionError("Updated stop not found in DB"));

            assertNotNull(
                    updatedStop.getCompletedTime(),
                    "Stop " + stop.getStopId() + " should have completedTime after completion"
            );
        }
    }

    private static class StopInfo {
        private final long stopId;
        private final int stopSequence;

        public StopInfo(long stopId, int stopSequence) {
            this.stopId = stopId;
            this.stopSequence = stopSequence;
        }

        public long getStopId() {
            return stopId;
        }

        public int getStopSequence() {
            return stopSequence;
        }
    }
}