package com.knightcharacter.app.integration;

import static com.knightcharacter.app.Constants.BASE_EXPERIENCE_URL;
import static com.knightcharacter.app.TestConstants.HARD_SCARY_EXPERIENCE_AMOUNT;
import static com.knightcharacter.app.TestConstants.PARAMETER_EXPERIENCE;
import static com.knightcharacter.app.TestConstants.PARAMETER_TODOID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.knightcharacter.app.factories.CharacterFactory;
import com.knightcharacter.app.factories.ExperienceFactory;
import com.knightcharacter.app.gateway.privatedb.repository.CharacterRepository;
import com.knightcharacter.app.rest.request.ExperienceRequestDto;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class ExperienceResourceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CharacterRepository characterRepository;

    @AfterEach
    public void tearDown() {
        characterRepository.deleteAll();
    }

    @Test
    public void calculateExperience_shouldCalculateExpAndReturnCalculatedExp_whenRequestIsCorrect() throws Exception {
        characterRepository.save(CharacterFactory.createCharacterInstance());
        ExperienceRequestDto requestDto = ExperienceFactory.experienceRequestDtoInstance();

        mockMvc.perform(post(BASE_EXPERIENCE_URL)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(jsonPath(PARAMETER_EXPERIENCE).value(HARD_SCARY_EXPERIENCE_AMOUNT))
            .andExpect(jsonPath(PARAMETER_TODOID).value(requestDto.getTodoId()));
    }

    @Test
    public void calculateExperience_shouldRespondWithBadRequestStatus_whenTodoIdIsNull() throws Exception {
        ExperienceRequestDto requestDto = ExperienceFactory.experienceRequestDtoWithoutTodoIdInstance();

        mockMvc.perform(post(BASE_EXPERIENCE_URL)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void addCharacter_shouldRespondWithBadRequestStatus_whenNameConsistsOfSpaces() throws Exception {
        ExperienceRequestDto requestDto = ExperienceFactory.experienceRequestDtoWithoutTodoIdInstance();

        mockMvc.perform(post(BASE_EXPERIENCE_URL)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());
    }
}
