package com.knightcharacter.app.integration;

import static com.knightcharacter.app.Constants.API_BASE_BONUSES;
import static com.knightcharacter.app.TestConstants.buildDeleteBonusByIdUrl;
import static com.knightcharacter.app.TestConstants.buildGetBonusByIdUrl;
import static com.knightcharacter.app.TestConstants.buildJsonPathToCritChanceBoost;
import static com.knightcharacter.app.TestConstants.buildJsonPathToCritChanceBoostInListByIndex;
import static com.knightcharacter.app.TestConstants.buildJsonPathToCritDamageBoost;
import static com.knightcharacter.app.TestConstants.buildJsonPathToCritDamageBoostInListByIndex;
import static com.knightcharacter.app.TestConstants.buildJsonPathToDamageBoost;
import static com.knightcharacter.app.TestConstants.buildJsonPathToDamageBoostInListByIndex;
import static com.knightcharacter.app.TestConstants.buildJsonPathToId;
import static com.knightcharacter.app.TestConstants.buildJsonPathToIdInListByIndex;
import static com.knightcharacter.app.TestConstants.buildJsonPathToLength;
import static com.knightcharacter.app.TestConstants.buildJsonPathToName;
import static com.knightcharacter.app.TestConstants.buildJsonPathToNameInListByIndex;
import static com.knightcharacter.app.TestConstants.buildJsonPathToRarity;
import static com.knightcharacter.app.TestConstants.buildJsonPathToRarityInListByIndex;
import static com.knightcharacter.app.TestConstants.buildJsonPathToSkillBoost;
import static com.knightcharacter.app.TestConstants.buildJsonPathToSkillBoostInListByIndex;
import static com.knightcharacter.app.TestConstants.buildPutBonusByIdUrl;

import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.knightcharacter.app.factories.BonusFactory;
import com.knightcharacter.app.gateway.privatedb.repository.BonusRepository;
import com.knightcharacter.app.gateway.privatedb.representation.Bonus;
import com.knightcharacter.app.rest.request.BonusRequestDto;

import org.junit.jupiter.api.AfterEach;
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

    @AfterEach
    void tearDown() {
        bonusRepository.deleteAll();
    }

    @Test
    void addBonus_shouldAddBonusAndReturnIt_whenRequestIsCorrect() throws Exception {
        BonusRequestDto requestDto = BonusFactory.createBonusRequestDtoInstance();

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
        BonusRequestDto requestDto = BonusFactory.createBonusRequestDtoWithoutNameInstance();

        mockMvc.perform(post(API_BASE_BONUSES)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(bonusRepository.count()).isEqualTo(0);
    }

    @Test
    void addBonus_shouldRespondWithBadRequestStatus_whenNameConsistsOfSpaces() throws Exception {
        BonusRequestDto requestDto = BonusFactory.createBonusRequestDtoWithNameConsistingOfSpacesInstance();

        mockMvc.perform(post(API_BASE_BONUSES)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(bonusRepository.count()).isEqualTo(0);
    }

    @Test
    void addBonus_shouldRespondWithBadRequestStatus_whenRarityIsNull() throws Exception {
        BonusRequestDto requestDto = BonusFactory.createBonusRequestDtoWithoutRarityInstance();

        mockMvc.perform(post(API_BASE_BONUSES)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(bonusRepository.count()).isEqualTo(0);
    }

    @Test
    void addBonus_shouldRespondWithBadRequestStatus_whenRarityIsNotValid() throws Exception {
        BonusRequestDto requestDto = BonusFactory.createBonusRequestDtoInstance();
        String request = objectMapper.writeValueAsString(requestDto).replace(requestDto.getRarity(), "MYSTIC ORANGE");

        mockMvc.perform(post(API_BASE_BONUSES)
            .content(request)
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(bonusRepository.count()).isEqualTo(0);
    }

    @Test
    void addBonus_shouldRespondWithBadRequestStatus_whenDamageBoostIsNull() throws Exception {
        BonusRequestDto requestDto = BonusFactory.createBonusRequestDtoWithoutDamageBoostInstance();

        mockMvc.perform(post(API_BASE_BONUSES)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(bonusRepository.count()).isEqualTo(0);
    }

    @Test
    void addBonus_shouldRespondWithBadRequestStatus_whenCritChanceBoostIsNull() throws Exception {
        BonusRequestDto requestDto = BonusFactory.createBonusRequestDtoWithoutCritChanceBoostInstance();

        mockMvc.perform(post(API_BASE_BONUSES)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(bonusRepository.count()).isEqualTo(0);
    }

    @Test
    void addBonus_shouldRespondWithBadRequestStatus_whenCritDamageBoostIsNull() throws Exception {
        BonusRequestDto requestDto = BonusFactory.createBonusRequestDtoWithoutCritDamageBoostInstance();

        mockMvc.perform(post(API_BASE_BONUSES)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(bonusRepository.count()).isEqualTo(0);
    }

    @Test
    void addBonus_shouldRespondWithBadRequestStatus_whenSkillBoostIsNull() throws Exception {
        BonusRequestDto requestDto = BonusFactory.createBonusRequestDtoWithoutSkillBoostInstance();

        mockMvc.perform(post(API_BASE_BONUSES)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(bonusRepository.count()).isEqualTo(0);
    }

    @Test
    void getAllBonuses_shouldReturnAllBonuses() throws Exception {
        Bonus firstBonus = bonusRepository.save(BonusFactory.bonusInstance());
        Bonus secondBonus = bonusRepository.save(BonusFactory.bonusInstance());

        mockMvc.perform(get(API_BASE_BONUSES))
            .andExpect(status().isFound())
            .andExpect(jsonPath(buildJsonPathToLength()).value(2))
            .andExpect(jsonPath(buildJsonPathToIdInListByIndex(0)).value(firstBonus.getId()))
            .andExpect(jsonPath(buildJsonPathToNameInListByIndex(0)).value(firstBonus.getName()))
            .andExpect(jsonPath(buildJsonPathToRarityInListByIndex(0)).value(firstBonus.getRarity().name()))
            .andExpect(jsonPath(buildJsonPathToDamageBoostInListByIndex(0)).value(firstBonus.getDamageBoost()))
            .andExpect(jsonPath(buildJsonPathToCritChanceBoostInListByIndex(0)).value(firstBonus.getCritChanceBoost()))
            .andExpect(jsonPath(buildJsonPathToCritDamageBoostInListByIndex(0)).value(firstBonus.getCritDamageBoost()))
            .andExpect(jsonPath(buildJsonPathToSkillBoostInListByIndex(0)).value(firstBonus.getSkillBoost()))
            .andExpect(jsonPath(buildJsonPathToIdInListByIndex(1)).value(secondBonus.getId()))
            .andExpect(jsonPath(buildJsonPathToNameInListByIndex(1)).value(secondBonus.getName()))
            .andExpect(jsonPath(buildJsonPathToRarityInListByIndex(1)).value(secondBonus.getRarity().name()))
            .andExpect(jsonPath(buildJsonPathToDamageBoostInListByIndex(1)).value(secondBonus.getDamageBoost()))
            .andExpect(jsonPath(buildJsonPathToCritChanceBoostInListByIndex(1)).value(secondBonus.getCritChanceBoost()))
            .andExpect(jsonPath(buildJsonPathToCritDamageBoostInListByIndex(1)).value(secondBonus.getCritDamageBoost()))
            .andExpect(jsonPath(buildJsonPathToSkillBoostInListByIndex(1)).value(secondBonus.getSkillBoost()));
    }

    @Test
    void getBonusById_shouldReturnExistingBonus_whenIdIsCorrect() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());

        mockMvc.perform(get(buildGetBonusByIdUrl(bonus.getId())))
            .andExpect(status().isFound())
            .andExpect(jsonPath(buildJsonPathToId()).value(bonus.getId()))
            .andExpect(jsonPath(buildJsonPathToName()).value(bonus.getName()))
            .andExpect(jsonPath(buildJsonPathToRarity()).value(bonus.getRarity().name()))
            .andExpect(jsonPath(buildJsonPathToDamageBoost()).value(bonus.getDamageBoost()))
            .andExpect(jsonPath(buildJsonPathToCritChanceBoost()).value(bonus.getCritChanceBoost()))
            .andExpect(jsonPath(buildJsonPathToCritDamageBoost()).value(bonus.getCritDamageBoost()))
            .andExpect(jsonPath(buildJsonPathToSkillBoost()).value(bonus.getSkillBoost()));
    }

    @Test
    void updateBonus_shouldUpdateBonusAndReturnIt_whenRequestIsCorrect() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        BonusRequestDto requestDto = BonusFactory.updateBonusRequestDtoInstance();

        mockMvc.perform(put(buildPutBonusByIdUrl(bonus.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(jsonPath(buildJsonPathToId()).value(bonus.getId()))
            .andExpect(jsonPath(buildJsonPathToName()).value(requestDto.getName()))
            .andExpect(jsonPath(buildJsonPathToRarity()).value(requestDto.getRarity()))
            .andExpect(jsonPath(buildJsonPathToDamageBoost()).value(requestDto.getDamageBoost()))
            .andExpect(jsonPath(buildJsonPathToCritChanceBoost()).value(requestDto.getCritChanceBoost()))
            .andExpect(jsonPath(buildJsonPathToCritDamageBoost()).value(requestDto.getCritDamageBoost()))
            .andExpect(jsonPath(buildJsonPathToSkillBoost()).value(requestDto.getSkillBoost()));

        Bonus updatedBonus = bonusRepository.findById(bonus.getId()).get();

        assertThat(updatedBonus.getName()).isEqualTo(requestDto.getName());
        assertThat(updatedBonus.getRarity().name()).isEqualTo(requestDto.getRarity());
        assertThat(updatedBonus.getDamageBoost()).isEqualTo(requestDto.getDamageBoost());
        assertThat(updatedBonus.getCritChanceBoost()).isEqualTo(requestDto.getCritChanceBoost());
        assertThat(updatedBonus.getCritDamageBoost()).isEqualTo(requestDto.getCritDamageBoost());
        assertThat(updatedBonus.getSkillBoost()).isEqualTo(requestDto.getSkillBoost());
    }

    @Test
    void updateBonus_shouldRespondWithBadRequestStatus_whenNameIsNull() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        BonusRequestDto requestDto = BonusFactory.updateBonusRequestDtoWithoutNameInstance();

        mockMvc.perform(put(buildPutBonusByIdUrl(bonus.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        bonus = bonusRepository.findById(bonus.getId()).get();

        assertThat(bonus.getName()).isNotEqualTo(requestDto.getName());
        assertThat(bonus.getRarity().name()).isNotEqualTo(requestDto.getRarity());
        assertThat(bonus.getDamageBoost()).isNotEqualTo(requestDto.getDamageBoost());
        assertThat(bonus.getCritChanceBoost()).isNotEqualTo(requestDto.getCritChanceBoost());
        assertThat(bonus.getCritDamageBoost()).isNotEqualTo(requestDto.getCritDamageBoost());
        assertThat(bonus.getSkillBoost()).isNotEqualTo(requestDto.getSkillBoost());
    }

    @Test
    void updateBonus_shouldRespondWithBadRequestStatus_whenNameConsistsOfSpaces() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        BonusRequestDto requestDto = BonusFactory.updateBonusRequestDtoWithNameConsistingOfSpacesInstance();

        mockMvc.perform(put(buildPutBonusByIdUrl(bonus.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        bonus = bonusRepository.findById(bonus.getId()).get();

        assertThat(bonus.getName()).isNotEqualTo(requestDto.getName());
        assertThat(bonus.getRarity().name()).isNotEqualTo(requestDto.getRarity());
        assertThat(bonus.getDamageBoost()).isNotEqualTo(requestDto.getDamageBoost());
        assertThat(bonus.getCritChanceBoost()).isNotEqualTo(requestDto.getCritChanceBoost());
        assertThat(bonus.getCritDamageBoost()).isNotEqualTo(requestDto.getCritDamageBoost());
        assertThat(bonus.getSkillBoost()).isNotEqualTo(requestDto.getSkillBoost());
    }

    @Test
    void updateBonus_shouldRespondWithBadRequestStatus_whenRarityIsNull() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        BonusRequestDto requestDto = BonusFactory.updateBonusRequestDtoWithoutRarityInstance();

        mockMvc.perform(put(buildPutBonusByIdUrl(bonus.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        bonus = bonusRepository.findById(bonus.getId()).get();

        assertThat(bonus.getName()).isNotEqualTo(requestDto.getName());
        assertThat(bonus.getRarity().name()).isNotEqualTo(requestDto.getRarity());
        assertThat(bonus.getDamageBoost()).isNotEqualTo(requestDto.getDamageBoost());
        assertThat(bonus.getCritChanceBoost()).isNotEqualTo(requestDto.getCritChanceBoost());
        assertThat(bonus.getCritDamageBoost()).isNotEqualTo(requestDto.getCritDamageBoost());
        assertThat(bonus.getSkillBoost()).isNotEqualTo(requestDto.getSkillBoost());
    }

    @Test
    void updateBonus_shouldRespondWithBadRequestStatus_whenRarityIsNotValid() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        BonusRequestDto requestDto = BonusFactory.updateBonusRequestDtoInstance();
        String request = objectMapper.writeValueAsString(requestDto).replace(requestDto.getRarity(), "MYSTIC ORANGE");

        mockMvc.perform(put(buildPutBonusByIdUrl(bonus.getId()))
            .content(request)
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        bonus = bonusRepository.findById(bonus.getId()).get();

        assertThat(bonus.getName()).isNotEqualTo(requestDto.getName());
        assertThat(bonus.getRarity().name()).isNotEqualTo(requestDto.getRarity());
        assertThat(bonus.getDamageBoost()).isNotEqualTo(requestDto.getDamageBoost());
        assertThat(bonus.getCritChanceBoost()).isNotEqualTo(requestDto.getCritChanceBoost());
        assertThat(bonus.getCritDamageBoost()).isNotEqualTo(requestDto.getCritDamageBoost());
        assertThat(bonus.getSkillBoost()).isNotEqualTo(requestDto.getSkillBoost());
    }

    @Test
    void updateBonus_shouldRespondWithBadRequestStatus_whenDamageBoostIsNull() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        BonusRequestDto requestDto = BonusFactory.updateBonusRequestDtoWithoutDamageBoostInstance();

        mockMvc.perform(put(buildPutBonusByIdUrl(bonus.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        bonus = bonusRepository.findById(bonus.getId()).get();

        assertThat(bonus.getName()).isNotEqualTo(requestDto.getName());
        assertThat(bonus.getRarity().name()).isNotEqualTo(requestDto.getRarity());
        assertThat(bonus.getDamageBoost()).isNotEqualTo(requestDto.getDamageBoost());
        assertThat(bonus.getCritChanceBoost()).isNotEqualTo(requestDto.getCritChanceBoost());
        assertThat(bonus.getCritDamageBoost()).isNotEqualTo(requestDto.getCritDamageBoost());
        assertThat(bonus.getSkillBoost()).isNotEqualTo(requestDto.getSkillBoost());
    }

    @Test
    void updateBonus_shouldRespondWithBadRequestStatus_whenCritChanceBoostIsNull() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        BonusRequestDto requestDto = BonusFactory.updateBonusRequestDtoWithoutCritChanceBoostInstance();

        mockMvc.perform(put(buildPutBonusByIdUrl(bonus.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        bonus = bonusRepository.findById(bonus.getId()).get();

        assertThat(bonus.getName()).isNotEqualTo(requestDto.getName());
        assertThat(bonus.getRarity().name()).isNotEqualTo(requestDto.getRarity());
        assertThat(bonus.getDamageBoost()).isNotEqualTo(requestDto.getDamageBoost());
        assertThat(bonus.getCritChanceBoost()).isNotEqualTo(requestDto.getCritChanceBoost());
        assertThat(bonus.getCritDamageBoost()).isNotEqualTo(requestDto.getCritDamageBoost());
        assertThat(bonus.getSkillBoost()).isNotEqualTo(requestDto.getSkillBoost());
    }

    @Test
    void updateBonus_shouldRespondWithBadRequestStatus_whenCritDamageBoostIsNull() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        BonusRequestDto requestDto = BonusFactory.updateBonusRequestDtoWithoutCritDamageBoostInstance();

        mockMvc.perform(put(buildPutBonusByIdUrl(bonus.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        bonus = bonusRepository.findById(bonus.getId()).get();

        assertThat(bonus.getName()).isNotEqualTo(requestDto.getName());
        assertThat(bonus.getRarity().name()).isNotEqualTo(requestDto.getRarity());
        assertThat(bonus.getDamageBoost()).isNotEqualTo(requestDto.getDamageBoost());
        assertThat(bonus.getCritChanceBoost()).isNotEqualTo(requestDto.getCritChanceBoost());
        assertThat(bonus.getCritDamageBoost()).isNotEqualTo(requestDto.getCritDamageBoost());
        assertThat(bonus.getSkillBoost()).isNotEqualTo(requestDto.getSkillBoost());
    }

    @Test
    void updateBonus_shouldRespondWithBadRequestStatus_whenSkillBoostIsNull() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        BonusRequestDto requestDto = BonusFactory.updateBonusRequestDtoWithoutSkillBoostInstance();

        mockMvc.perform(put(buildPutBonusByIdUrl(bonus.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        bonus = bonusRepository.findById(bonus.getId()).get();

        assertThat(bonus.getName()).isNotEqualTo(requestDto.getName());
        assertThat(bonus.getRarity().name()).isNotEqualTo(requestDto.getRarity());
        assertThat(bonus.getDamageBoost()).isNotEqualTo(requestDto.getDamageBoost());
        assertThat(bonus.getCritChanceBoost()).isNotEqualTo(requestDto.getCritChanceBoost());
        assertThat(bonus.getCritDamageBoost()).isNotEqualTo(requestDto.getCritDamageBoost());
        assertThat(bonus.getSkillBoost()).isNotEqualTo(requestDto.getSkillBoost());
    }

    @Test
    void deleteBonus_shouldDeleteBonus_whenIdIsCorrect() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());

        mockMvc.perform(delete(buildDeleteBonusByIdUrl(bonus.getId())))
            .andExpect(status().isOk());

        assertThat(bonusRepository.findById(bonus.getId())).isEmpty();
    }
}
