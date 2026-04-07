package com.group9.postal;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerOrderTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void customerCanPlaceOrder() throws Exception {
        ObjectNode request = objectMapper.createObjectNode();
        request.put("customerId", 1);
        request.put("contactName", "Alice Anderson");
        request.put("contactEmail", "alice@gmail.com");
        request.put("contactPhone", "123-456-0101");
        request.put("pickupAddressId", 20);
        request.put("dropoffAddressId", 1);
        request.put("totalCost", 29.99);

        MvcResult result = mockMvc.perform(
                        post("/orders")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request.toString())
                )
                .andExpect(status().isOk())
                .andReturn();

        JsonNode response = objectMapper.readTree(result.getResponse().getContentAsString());

        assertNotNull(response.get("orderId"), "Created order should have an orderId");
        assertEquals(1, response.get("customerId").asInt());
        assertEquals("Alice Anderson", response.get("contactName").asText());
        assertNotNull(response.get("orderStatus"), "Order should have a status");
    }

    @Test
    void customerCanViewOrderHistory() throws Exception {
        MvcResult result = mockMvc.perform(
                        get("/orders/customer/1")
                )
                .andExpect(status().isOk())
                .andReturn();

        JsonNode response = objectMapper.readTree(result.getResponse().getContentAsString());

        assertTrue(response.isArray(), "Expected array of orders");
        assertTrue(response.size() > 0, "Customer should have at least one order");

        for (JsonNode order : response) {
            assertEquals(1, order.get("customerId").asInt(),
                    "All orders should belong to customer 1");
        }
    }

    @Test
    void customerCanTrackPackage() throws Exception {
        String trackingNumber = "TRK-000001";

        MvcResult result = mockMvc.perform(
                        get("/shipment/track/" + trackingNumber)
                )
                .andExpect(status().isOk())
                .andReturn();

        JsonNode response = objectMapper.readTree(result.getResponse().getContentAsString());

        assertNotNull(response, "Tracking response should not be null");
        assertEquals(trackingNumber, response.get("trackingNumber").asText(),
                "Tracking number should match");
        assertNotNull(response.get("currentStatus"), "Shipment should have a status");
    }
}