package com.knightcharacter.app.integration;

import static com.knightcharacter.app.Constants.API_BASE_ITEMS;
import static com.knightcharacter.app.Constants.API_BASE_WEAPONS;
import static com.knightcharacter.app.TestConstants.buildJsonPathToBonuses;
import static com.knightcharacter.app.TestConstants.buildJsonPathToCritChanceBoostInBonusListByIndex;
import static com.knightcharacter.app.TestConstants.buildJsonPathToCritDamageBoostInBonusListByIndex;
import static com.knightcharacter.app.TestConstants.buildJsonPathToDamage;
import static com.knightcharacter.app.TestConstants.buildJsonPathToDamageBoostInBonusListByIndex;
import static com.knightcharacter.app.TestConstants.buildJsonPathToId;
import static com.knightcharacter.app.TestConstants.buildJsonPathToIdInBonusListByIndex;
import static com.knightcharacter.app.TestConstants.buildJsonPathToNameInBonusListByIndex;
import static com.knightcharacter.app.TestConstants.buildJsonPathToRarity;
import static com.knightcharacter.app.TestConstants.buildJsonPathToRarityInBonusListByIndex;
import static com.knightcharacter.app.TestConstants.buildJsonPathToRequiredAgility;
import static com.knightcharacter.app.TestConstants.buildJsonPathToRequiredIntelligence;
import static com.knightcharacter.app.TestConstants.buildJsonPathToRequiredLevel;
import static com.knightcharacter.app.TestConstants.buildJsonPathToRequiredStrength;
import static com.knightcharacter.app.TestConstants.buildJsonPathToSkillBoostInBonusListByIndex;
import static com.knightcharacter.app.TestConstants.buildJsonPathToWeaponType;

import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.knightcharacter.app.factories.BonusFactory;
import com.knightcharacter.app.factories.WeaponFactory;
import com.knightcharacter.app.gateway.privatedb.repository.BonusRepository;
import com.knightcharacter.app.gateway.privatedb.repository.WeaponRepository;
import com.knightcharacter.app.gateway.privatedb.representation.Bonus;
import com.knightcharacter.app.gateway.privatedb.representation.Weapon;
import com.knightcharacter.app.rest.request.WeaponRequestDto;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
class WeaponResourceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WeaponRepository weaponRepository;

    @Autowired
    private BonusRepository bonusRepository;

    @AfterEach
    void tearDown() {
        weaponRepository.deleteAll();
        bonusRepository.deleteAll();
    }

    @Test
    @Transactional
    void addWeapon_shouldAddWeaponAndReturnWeaponResponseDto_whenRequestIsCorrect() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        WeaponRequestDto requestDto = WeaponFactory.createWeaponRequestDto(List.of(bonus.getId()));

        mockMvc.perform(post(API_BASE_ITEMS + API_BASE_WEAPONS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isCreated())
            .andExpect(jsonPath(buildJsonPathToId()).exists())
            .andExpect(jsonPath(buildJsonPathToDamage()).value(requestDto.getDamage()))
            .andExpect(jsonPath(buildJsonPathToRarity()).value(requestDto.getRarity()))
            .andExpect(jsonPath(buildJsonPathToWeaponType()).value(requestDto.getWeaponType()))
            .andExpect(jsonPath(buildJsonPathToRequiredAgility()).value(requestDto.getRequiredAgility()))
            .andExpect(jsonPath(buildJsonPathToRequiredIntelligence()).value(requestDto.getRequiredIntelligence()))
            .andExpect(jsonPath(buildJsonPathToRequiredLevel()).value(requestDto.getRequiredLevel()))
            .andExpect(jsonPath(buildJsonPathToRequiredStrength()).value(requestDto.getRequiredStrength()))
            .andExpect(jsonPath(buildJsonPathToBonuses()).exists())
            .andExpect(jsonPath(buildJsonPathToIdInBonusListByIndex(0)).value(bonus.getId()))
            .andExpect(jsonPath(buildJsonPathToNameInBonusListByIndex(0)).value(bonus.getName()))
            .andExpect(jsonPath(buildJsonPathToRarityInBonusListByIndex(0)).value(bonus.getRarity().toString()))
            .andExpect(jsonPath(buildJsonPathToDamageBoostInBonusListByIndex(0)).value(bonus.getDamageBoost()))
            .andExpect(jsonPath(buildJsonPathToCritChanceBoostInBonusListByIndex(0)).value(bonus.getCritChanceBoost()))
            .andExpect(jsonPath(buildJsonPathToCritDamageBoostInBonusListByIndex(0)).value(bonus.getCritDamageBoost()))
            .andExpect(jsonPath(buildJsonPathToSkillBoostInBonusListByIndex(0)).value(bonus.getSkillBoost()));

        Weapon savedWeapon = weaponRepository.findAll().get(0);

        assertThat(savedWeapon.getBonuses()).contains(bonus);
        assertThat(savedWeapon.getDamage()).isEqualTo(requestDto.getDamage());
        assertThat(savedWeapon.getRarity().name()).isEqualTo(requestDto.getRarity());
        assertThat(savedWeapon.getWeaponType().name()).isEqualTo(requestDto.getWeaponType());
        assertThat(savedWeapon.getRequiredAgility()).isEqualTo(requestDto.getRequiredAgility());
        assertThat(savedWeapon.getRequiredIntelligence()).isEqualTo(requestDto.getRequiredIntelligence());
        assertThat(savedWeapon.getRequiredLevel()).isEqualTo(requestDto.getRequiredLevel());
        assertThat(savedWeapon.getRequiredStrength()).isEqualTo(requestDto.getRequiredStrength());
    }

    @Test
    void addWeapon_shouldRespondWithBadRequestStatus_whenDamageIsNull() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        WeaponRequestDto requestDto = WeaponFactory.createWeaponRequestDtoWithoutDamage(List.of(bonus.getId()));

        mockMvc.perform(post(API_BASE_ITEMS + API_BASE_WEAPONS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(weaponRepository.count()).isEqualTo(0);
    }

    @Test
    void addWeapon_shouldRespondWithBadRequestStatus_whenWeaponTypeIsNull() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        WeaponRequestDto requestDto = WeaponFactory.createWeaponRequestDtoWithoutWeaponType(List.of(bonus.getId()));

        mockMvc.perform(post(API_BASE_ITEMS + API_BASE_WEAPONS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(weaponRepository.count()).isEqualTo(0);
    }

    @Test
    void addWeapon_shouldRespondWithBadRequestStatus_whenWeaponTypeIsInvalid() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        WeaponRequestDto requestDto = WeaponFactory.createWeaponRequestDtoWithInvalidWeaponType(List.of(bonus.getId()));

        mockMvc.perform(post(API_BASE_ITEMS + API_BASE_WEAPONS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(weaponRepository.count()).isEqualTo(0);
    }

    @Test
    void addWeapon_shouldRespondWithBadRequestStatus_whenRequiredLevelIsNull() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        WeaponRequestDto requestDto = WeaponFactory.createWeaponRequestDtoWithoutRequiredLevel(List.of(bonus.getId()));

        mockMvc.perform(post(API_BASE_ITEMS + API_BASE_WEAPONS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(weaponRepository.count()).isEqualTo(0);
    }

    @Test
    void addWeapon_shouldRespondWithBadRequestStatus_whenRequiredAgilityIsNull() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        WeaponRequestDto requestDto = WeaponFactory
            .createWeaponRequestDtoWithoutRequiredAgility(List.of(bonus.getId()));

        mockMvc.perform(post(API_BASE_ITEMS + API_BASE_WEAPONS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(weaponRepository.count()).isEqualTo(0);
    }

    @Test
    void addWeapon_shouldRespondWithBadRequestStatus_whenRequiredIntelligenceIsNull() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        WeaponRequestDto requestDto = WeaponFactory
            .createWeaponRequestDtoWithoutRequiredIntelligence(List.of(bonus.getId()));

        mockMvc.perform(post(API_BASE_ITEMS + API_BASE_WEAPONS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(weaponRepository.count()).isEqualTo(0);
    }

    @Test
    void addWeapon_shouldRespondWithBadRequestStatus_whenRequiredStrengthIsNull() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        WeaponRequestDto requestDto = WeaponFactory
            .createWeaponRequestDtoWithoutRequiredStrength(List.of(bonus.getId()));

        mockMvc.perform(post(API_BASE_ITEMS + API_BASE_WEAPONS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(weaponRepository.count()).isEqualTo(0);
    }

    @Test
    void addWeapon_shouldRespondWithBadRequestStatus_whenRarityIsNull() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        WeaponRequestDto requestDto = WeaponFactory.createWeaponRequestDtoWithoutRarity(List.of(bonus.getId()));

        mockMvc.perform(post(API_BASE_ITEMS + API_BASE_WEAPONS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(weaponRepository.count()).isEqualTo(0);
    }

    @Test
    void addWeapon_shouldRespondWithBadRequestStatus_whenRarityIsInvalid() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        WeaponRequestDto requestDto = WeaponFactory.createWeaponRequestDtoWithInvalidRarity(List.of(bonus.getId()));

        mockMvc.perform(post(API_BASE_ITEMS + API_BASE_WEAPONS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(weaponRepository.count()).isEqualTo(0);
    }

    @Test
    void addWeapon_shouldRespondWithBadRequestStatus_whenBonusIdsIsNull() throws Exception {
        WeaponRequestDto requestDto = WeaponFactory.createWeaponRequestDtoWithoutBonusIds();

        mockMvc.perform(post(API_BASE_ITEMS + API_BASE_WEAPONS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(weaponRepository.count()).isEqualTo(0);
    }
}
