package com.knightcharacter.app.integration;

import static com.knightcharacter.app.Constants.API_BASE_BONUSES;
import static com.knightcharacter.app.TestConstants.buildJsonPathToCritChanceBoost;
import static com.knightcharacter.app.TestConstants.buildJsonPathToCritDamageBoost;
import static com.knightcharacter.app.TestConstants.buildJsonPathToDamageBoost;
import static com.knightcharacter.app.TestConstants.buildJsonPathToId;
import static com.knightcharacter.app.TestConstants.buildJsonPathToName;
import static com.knightcharacter.app.TestConstants.buildJsonPathToRarity;
import static com.knightcharacter.app.TestConstants.buildJsonPathToSkillBoost;

import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.knightcharacter.app.factories.BonusFactory;
import com.knightcharacter.app.gateway.privatedb.repository.BonusRepository;
import com.knightcharacter.app.rest.request.BonusRequestDto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class BonusResourceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BonusRepository bonusRepository;

    @BeforeEach
    void setUp() {
        bonusRepository.deleteAll();
    }

    @Test
    void addBonus_shouldAddBonusAndReturnIt_whenRequestIsCorrect() throws Exception {
        BonusRequestDto requestDto = BonusFactory.bonusRequestDtoInstance();

        mockMvc.perform(post(API_BASE_BONUSES)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isCreated())
            .andExpect(jsonPath(buildJsonPathToId()).exists())
            .andExpect(jsonPath(buildJsonPathToName()).value(requestDto.getName()))
            .andExpect(jsonPath(buildJsonPathToRarity()).value(requestDto.getRarity()))
            .andExpect(jsonPath(buildJsonPathToDamageBoost()).value(requestDto.getDamageBoost()))
            .andExpect(jsonPath(buildJsonPathToCritChanceBoost()).value(requestDto.getCritChanceBoost()))
            .andExpect(jsonPath(buildJsonPathToCritDamageBoost()).value(requestDto.getCritDamageBoost()))
            .andExpect(jsonPath(buildJsonPathToSkillBoost()).value(requestDto.getSkillBoost()));

        assertThat(bonusRepository.count()).isEqualTo(1);
    }

    @Test
    void addBonus_shouldRespondWithBadRequestStatus_whenNameIsNull() throws Exception {
        BonusRequestDto requestDto = BonusFactory.bonusRequestDtoWithoutNameInstance();

        mockMvc.perform(post(API_BASE_BONUSES)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(bonusRepository.count()).isEqualTo(0);
    }

    @Test
    void addBonus_shouldRespondWithBadRequestStatus_whenNameConsistsOfSpaces() throws Exception {
        BonusRequestDto requestDto = BonusFactory.bonusRequestDtoWithNameConsistingOfSpacesInstance();

        mockMvc.perform(post(API_BASE_BONUSES)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(bonusRepository.count()).isEqualTo(0);
    }

    @Test
    void addBonus_shouldRespondWithBadRequestStatus_whenRarityIsNull() throws Exception {
        BonusRequestDto requestDto = BonusFactory.bonusRequestDtoWithoutRarityInstance();

        mockMvc.perform(post(API_BASE_BONUSES)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(bonusRepository.count()).isEqualTo(0);
    }

    @Test
    void addBonus_shouldRespondWithBadRequestStatus_whenRarityIsNotValid() throws Exception {
        BonusRequestDto requestDto = BonusFactory.bonusRequestDtoInstance();
        String request = objectMapper.writeValueAsString(requestDto).replace(requestDto.getRarity(), "MYSTIC ORANGE");

        mockMvc.perform(post(API_BASE_BONUSES)
            .content(request)
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(bonusRepository.count()).isEqualTo(0);
    }

    @Test
    void addBonus_shouldRespondWithBadRequestStatus_whenDamageBoostIsNull() throws Exception {
        BonusRequestDto requestDto = BonusFactory.bonusRequestDtoWithoutDamageBoostInstance();

        mockMvc.perform(post(API_BASE_BONUSES)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(bonusRepository.count()).isEqualTo(0);
    }

    @Test
    void addBonus_shouldRespondWithBadRequestStatus_whenCritChanceBoostIsNull() throws Exception {
        BonusRequestDto requestDto = BonusFactory.bonusRequestDtoWithoutCritChanceBoostInstance();

        mockMvc.perform(post(API_BASE_BONUSES)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(bonusRepository.count()).isEqualTo(0);
    }

    @Test
    void addBonus_shouldRespondWithBadRequestStatus_whenCritDamageBoostIsNull() throws Exception {
        BonusRequestDto requestDto = BonusFactory.bonusRequestDtoWithoutCritDamageBoostInstance();

        mockMvc.perform(post(API_BASE_BONUSES)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(bonusRepository.count()).isEqualTo(0);
    }

    @Test
    void addBonus_shouldRespondWithBadRequestStatus_whenSkillBoostIsNull() throws Exception {
        BonusRequestDto requestDto = BonusFactory.bonusRequestDtoWithoutSkillBoostInstance();

        mockMvc.perform(post(API_BASE_BONUSES)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(bonusRepository.count()).isEqualTo(0);
    }
}
