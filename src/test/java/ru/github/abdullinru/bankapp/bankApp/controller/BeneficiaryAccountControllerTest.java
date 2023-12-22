package ru.github.abdullinru.bankapp.bankApp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import ru.github.abdullinru.bankapp.bankApp.dto.RequestBeneficiaryDto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(value = {"/beforeTest.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class BeneficiaryAccountControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Test
    public void createBeneficiaryTest() throws Exception {
        String name = "Ruslan";
        String pin = "1111";
        RequestBeneficiaryDto requestBeneficiaryDto = new RequestBeneficiaryDto(name, pin);
        String request = objectMapper.writeValueAsString(requestBeneficiaryDto);

        mvc
                .perform(MockMvcRequestBuilders
                                .post("/api/beneficiaries")
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.pin").value(pin))
                .andExpect(jsonPath("$.accounts").exists())
                .andExpect(jsonPath("$.accounts[0].balance").exists())
                .andExpect(jsonPath("$.accounts[0].balance").value(0));
    }
    @Test
    public void createAccountTest() throws Exception {
        Long id = 7L;
        String pin = "1111";
        String name = "Ruslan";
        RequestBeneficiaryDto requestBeneficiaryDto = new RequestBeneficiaryDto(name, pin);
        String request = objectMapper.writeValueAsString(requestBeneficiaryDto);

        mvc
                .perform(MockMvcRequestBuilders
                        .post("/api/beneficiaries")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print());
        mvc
                .perform(MockMvcRequestBuilders
                        .post("/api/beneficiaries/{id}/accounts", id)
                        .content(pin)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.pin").value(pin))
                .andExpect(jsonPath("$.accounts").exists())
                .andExpect(jsonPath("$.accounts").isArray())
                .andExpect(jsonPath("$.accounts[1]").exists())
                .andExpect(jsonPath("$.accounts[0]").exists())
                .andExpect(jsonPath("$.accounts[0].balance").value(0));

    }
    @Test
    public void getAllBeneficiaryTest() throws Exception {

        String pin1 = "1111";
        String name1 = "Ruslan";

        String pin2 = "2222";
        String name2 = "Dastan";

        String request1 = objectMapper.writeValueAsString(new RequestBeneficiaryDto(name1, pin1));
        String request2 = objectMapper.writeValueAsString(new RequestBeneficiaryDto(name2, pin2));

        mvc
                .perform(MockMvcRequestBuilders
                        .post("/api/beneficiaries")
                        .content(request1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));
        mvc
                .perform(MockMvcRequestBuilders
                        .post("/api/beneficiaries")
                        .content(request2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        mvc
                .perform(MockMvcRequestBuilders
                        .get("/api/beneficiaries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value(name1))
                .andExpect(jsonPath("$[0].pin").value(pin1))
                .andExpect(jsonPath("$[1].name").value(name2))
                .andExpect(jsonPath("$[1].pin").value(pin2));
    }

}