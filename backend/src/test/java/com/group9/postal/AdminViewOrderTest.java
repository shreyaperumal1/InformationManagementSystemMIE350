package com.group9.postal;

import com.group9.postal.controller.OrderController;
import com.group9.postal.controller.UserController;
import com.group9.postal.dto.OrderResponse;
import com.group9.postal.model.User;
import com.group9.postal.repository.ShipmentRepository;
import com.group9.postal.repository.UserRepository;
import com.group9.postal.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest({UserController.class, OrderController.class})
public class AdminViewOrderTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserRepository userRepository;

    @MockitoBean
    private OrderService orderService;

    @MockitoBean
    private ShipmentRepository shipmentRepository;

    @Test
    void adminCanViewPendingOrders() throws Exception {
        User admin = new User();
        admin.setUserId(5L);
        admin.setName("Emily Patrick");
        admin.setEmail("emily@postal.com");
        admin.setPhone("123-456-0505");
        admin.setPasswordHash("password123");
        admin.setRole(User.Role.ADMIN);

        when(userRepository.findByEmail("emily@postal.com"))
                .thenReturn(Optional.of(admin));

        OrderResponse pending1 = mock(OrderResponse.class);
        when(pending1.getOrderStatus()).thenReturn("Pending");

        OrderResponse pending2 = mock(OrderResponse.class);
        when(pending2.getOrderStatus()).thenReturn("Pending");

        when(orderService.getOrdersByStatus("Pending"))
                .thenReturn(List.of(pending1, pending2));

        String loginBody = """
                {
                  "email": "emily@postal.com",
                  "password": "password123"
                }
                """;

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(5))
                .andExpect(jsonPath("$.role").value("ADMIN"))
                .andExpect(jsonPath("$.message").value("Login successful"));

        mockMvc.perform(get("/orders/status/Pending"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].orderStatus").value("Pending"))
                .andExpect(jsonPath("$[1].orderStatus").value("Pending"));

        verify(orderService, times(1)).getOrdersByStatus("Pending");
    }
}