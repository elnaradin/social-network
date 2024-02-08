package ru.itgroup.intouch.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ru.itgroup.intouch.dto.AccountDto;

import java.text.MessageFormat;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AccountControllerTests extends BaseIT {

    @Test
    void getAccountById() throws Exception {
        mockMvc.perform(get("/api/v1/account/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("lsaltmarsh0@slashdot.org"));
    }

    @Test
    void accounts() throws Exception {
        mockMvc.perform(post("/api/v1/account/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(List.of(1, 2, 3, 4, 5))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(5)));
    }

    @Test
    void currentUser() throws Exception {
        mockMvc.perform(get("/api/v1/account/me")
                        .param("email", "lsaltmarsh0@slashdot.org"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("lsaltmarsh0@slashdot.org"));
    }

    @Test
    void changeProfileInfo() throws Exception {
        String newName = "Matilda";
        AccountDto accountDto = AccountDto.builder()
                .email("lsaltmarsh0@slashdot.org")
                .firstName(newName)
                .build();
        mockMvc.perform(put("/api/v1/account/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(newName));
    }

    @Test
    void deleteAccount() throws Exception {
        String emailToDelete = "lsaltmarsh0@slashdot.org";
        mockMvc.perform(delete("/api/v1/account/me")
                        .param("email", emailToDelete))
                .andExpect(status().isOk());
        mockMvc.perform(get("/api/v1/account/me")
                        .param("email", emailToDelete))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error_description").value(
                        MessageFormat.format("<br/>Аккаунт с адресом \"{0}\" не найден", emailToDelete)
                ));
    }

    @Test
    void search() throws Exception {
        String queryName = "Garret";

        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.set("firstName", queryName);
        mockMvc.perform(get("/api/v1/account/search")
                        .queryParams(multiValueMap))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].firstName").value(queryName));
    }
}