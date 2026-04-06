package com.group9.postal;

import com.group9.postal.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LoginTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Test
    void customerLoginSuccess() throws Exception {
        ObjectNode loginJson = objectMapper.createObjectNode();
        loginJson.put("email", "alice@email.com");
        loginJson.put("password", "password123");

        MockHttpServletResponse response = mockMvc.perform(
                        post("/auth/login")
                                .contentType("application/json")
                                .content(loginJson.toString()))
                .andReturn().getResponse();


        assertEquals(200, response.getStatus());


        ObjectNode receivedJson = objectMapper.readValue(
                response.getContentAsString(), ObjectNode.class);

        assertEquals("CUSTOMER", receivedJson.get("role").textValue());
        assertEquals("Login successful", receivedJson.get("message").textValue());
        assertEquals("alice@email.com", receivedJson.get("email").textValue());
    }

    //Successful Admin Login Test
    @Test
    void adminLoginSuccess() throws Exception {
        ObjectNode loginJson = objectMapper.createObjectNode();
        loginJson.put("email", "emily@postal.com");
        loginJson.put("password", "pass123");

        MockHttpServletResponse response = mockMvc.perform(
                        post("/auth/login")
                                .contentType("application/json")
                                .content(loginJson.toString()))
                .andReturn().getResponse();

        assertEquals(200, response.getStatus());


        ObjectNode receivedJson = objectMapper.readValue(
                response.getContentAsString(), ObjectNode.class);

        assertEquals("ADMIN", receivedJson.get("role").textValue());
        assertEquals("Login successful", receivedJson.get("message").textValue());
    }

    //Successful Driver Login Test
    @Test
    void driverLoginSuccess() throws Exception {
        ObjectNode loginJson = objectMapper.createObjectNode();
        loginJson.put("email", "ilora@postal.com");
        loginJson.put("password", "password123");

        MockHttpServletResponse response = mockMvc.perform(
                        post("/auth/login")
                                .contentType("application/json")
                                .content(loginJson.toString()))
                .andReturn().getResponse();


        assertEquals(200, response.getStatus());

        ObjectNode receivedJson = objectMapper.readValue(
                response.getContentAsString(), ObjectNode.class);

        assertEquals("DRIVER", receivedJson.get("role").textValue());
        assertEquals("Login successful", receivedJson.get("message").textValue());
    }

    //Wrong Password
    @Test
    void loginWrongPassword() throws Exception {
        ObjectNode loginJson = objectMapper.createObjectNode();
        loginJson.put("email", "alice@email.com");
        loginJson.put("password", "wrongpassword");

        MockHttpServletResponse response = mockMvc.perform(
                        post("/auth/login")
                                .contentType("application/json")
                                .content(loginJson.toString()))
                .andReturn().getResponse();

        // Assert HTTP 401 UNAUTHORIZED
        assertEquals(401, response.getStatus());

        // Assert error message returned
        ObjectNode receivedJson = objectMapper.readValue(
                response.getContentAsString(), ObjectNode.class);

        assertEquals("Invalide email or password",
                receivedJson.get("message").textValue());
    }

    //Wrong Email Test
    @Test
    void loginNonExistentEmail() throws Exception {
        ObjectNode loginJson = objectMapper.createObjectNode();
        loginJson.put("email", "notreal@email.com");
        loginJson.put("password", "password123");

        MockHttpServletResponse response = mockMvc.perform(
                        post("/auth/login")
                                .contentType("application/json")
                                .content(loginJson.toString()))
                .andReturn().getResponse();

        // Assert HTTP 401 UNAUTHORIZED
        assertEquals(401, response.getStatus());

        // Assert error message returned
        ObjectNode receivedJson = objectMapper.readValue(
                response.getContentAsString(), ObjectNode.class);

        assertEquals("Invalide email or password",
                receivedJson.get("message").textValue());
    }

    //Email Not Found Test
    @Test
    void loginEmptyEmail() throws Exception {
        ObjectNode loginJson = objectMapper.createObjectNode();
        loginJson.put("email", "");
        loginJson.put("password", "password123");

        MockHttpServletResponse response = mockMvc.perform(
                        post("/auth/login")
                                .contentType("application/json")
                                .content(loginJson.toString()))
                .andReturn().getResponse();


        assertEquals(401, response.getStatus());
    }

    //Password Not Input Test
    @Test
    void loginEmptyPassword() throws Exception {
        ObjectNode loginJson = objectMapper.createObjectNode();
        loginJson.put("email", "alice@email.com");
        loginJson.put("password", "");

        MockHttpServletResponse response = mockMvc.perform(
                        post("/auth/login")
                                .contentType("application/json")
                                .content(loginJson.toString()))
                .andReturn().getResponse();

        assertEquals(401, response.getStatus());
    }

}
