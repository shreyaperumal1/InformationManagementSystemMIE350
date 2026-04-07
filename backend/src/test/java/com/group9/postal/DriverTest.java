package com.group9.postal;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ObjectNode;
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
    void driverCanViewRoutesAndCompleteStopsWhenPresent() throws Exception {
        User driver = userRepository.findByRole(User.Role.DRIVER)
                .stream()
                .findFirst()
                .orElseThrow(() -> new AssertionError("No DRIVER user found in test data"));

        String email = driver.getEmail();
        String password = driver.getPasswordHash();

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

        boolean completedAtLeastOneStop = false;

        for (JsonNode routeSummary : routesJson) {
            long routeId = routeSummary.get("routeId").asLong();

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

            if (stopsJson.size() == 0) {
                continue;
            }

            List<StopInfo> stops = new ArrayList<>();
            for (JsonNode stopNode : stopsJson) {
                long stopId = stopNode.get("stopId").asLong();
                int stopSequence = stopNode.get("stopSequence").asInt();
                stops.add(new StopInfo(stopId, stopSequence));
            }

            stops.sort(Comparator.comparingInt(StopInfo::getStopSequence));

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

                RouteStop updatedStop = routeStopRepository.findById(stop.getStopId())
                        .orElseThrow(() -> new AssertionError("Updated stop not found in DB"));

                assertNotNull(
                        updatedStop.getCompletedTime(),
                        "Stop " + stop.getStopId() + " should have completedTime after completion"
                );
            }

            completedAtLeastOneStop = true;
            break;
        }

        if (!completedAtLeastOneStop) {
            System.out.println("No assigned routes with stops were found, so the test verified route viewing only.");
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