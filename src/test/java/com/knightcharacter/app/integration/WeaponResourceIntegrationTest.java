package com.knightcharacter.app.integration;

import static com.knightcharacter.app.Constants.API_BASE_ITEMS;
import static com.knightcharacter.app.Constants.API_BASE_WEAPONS;
import static com.knightcharacter.app.TestConstants.buildDeleteWeaponByIdUrl;
import static com.knightcharacter.app.TestConstants.buildGetWeaponByIdUrl;
import static com.knightcharacter.app.TestConstants.buildJsonPathToBonuses;
import static com.knightcharacter.app.TestConstants.buildJsonPathToBonusesInListByIndex;
import static com.knightcharacter.app.TestConstants.buildJsonPathToBonusesLength;
import static com.knightcharacter.app.TestConstants.buildJsonPathToCritChanceBoostInBonusListByIndex;
import static com.knightcharacter.app.TestConstants.buildJsonPathToCritChanceBoostInBonusListNestedInSkillListByIndexes;
import static com.knightcharacter.app.TestConstants.buildJsonPathToCritDamageBoostInBonusListByIndex;
import static com.knightcharacter.app.TestConstants.buildJsonPathToCritDamageBoostInBonusListNestedInSkillListByIndexes;
import static com.knightcharacter.app.TestConstants.buildJsonPathToDamage;
import static com.knightcharacter.app.TestConstants.buildJsonPathToDamageBoostInBonusListByIndex;
import static com.knightcharacter.app.TestConstants.buildJsonPathToDamageBoostInBonusListNestedInSkillListByIndexes;
import static com.knightcharacter.app.TestConstants.buildJsonPathToDamageInListByIndex;
import static com.knightcharacter.app.TestConstants.buildJsonPathToId;
import static com.knightcharacter.app.TestConstants.buildJsonPathToIdInBonusListByIndex;
import static com.knightcharacter.app.TestConstants.buildJsonPathToIdInBonusListNestedInSkillListByIndexes;
import static com.knightcharacter.app.TestConstants.buildJsonPathToIdInListByIndex;
import static com.knightcharacter.app.TestConstants.buildJsonPathToLength;
import static com.knightcharacter.app.TestConstants.buildJsonPathToNameInBonusListByIndex;
import static com.knightcharacter.app.TestConstants.buildJsonPathToNameInBonusListNestedInSkillListByIndexes;
import static com.knightcharacter.app.TestConstants.buildJsonPathToRarity;
import static com.knightcharacter.app.TestConstants.buildJsonPathToRarityInBonusListByIndex;
import static com.knightcharacter.app.TestConstants.buildJsonPathToRarityInBonusListNestedInSkillListByIndexes;
import static com.knightcharacter.app.TestConstants.buildJsonPathToRarityInListByIndex;
import static com.knightcharacter.app.TestConstants.buildJsonPathToRequiredAgility;
import static com.knightcharacter.app.TestConstants.buildJsonPathToRequiredAgilityInListByIndex;
import static com.knightcharacter.app.TestConstants.buildJsonPathToRequiredIntelligence;
import static com.knightcharacter.app.TestConstants.buildJsonPathToRequiredIntelligenceInListByIndex;
import static com.knightcharacter.app.TestConstants.buildJsonPathToRequiredLevel;
import static com.knightcharacter.app.TestConstants.buildJsonPathToRequiredLevelInListByIndex;
import static com.knightcharacter.app.TestConstants.buildJsonPathToRequiredStrength;
import static com.knightcharacter.app.TestConstants.buildJsonPathToRequiredStrengthInListByIndex;
import static com.knightcharacter.app.TestConstants.buildJsonPathToSkillBoostInBonusListByIndex;
import static com.knightcharacter.app.TestConstants.buildJsonPathToSkillBoostInBonusListNestedInSkillListByIndexes;
import static com.knightcharacter.app.TestConstants.buildJsonPathToWeaponType;
import static com.knightcharacter.app.TestConstants.buildJsonPathToWeaponTypeInListByIndex;
import static com.knightcharacter.app.TestConstants.buildPutWeaponByIdUrl;

import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;

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
@FieldDefaults(level = AccessLevel.PRIVATE)
class WeaponResourceIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    WeaponRepository weaponRepository;

    @Autowired
    BonusRepository bonusRepository;

    @AfterEach
    void tearDown() {
        weaponRepository.deleteAll();
        bonusRepository.deleteAll();
    }

    @Test
    @Transactional
    @SneakyThrows
    void addWeapon_shouldAddWeaponAndReturnWeaponResponseDto_whenRequestIsCorrect() {
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
    @SneakyThrows
    void addWeapon_shouldRespondWithBadRequestStatus_whenDamageIsNull() {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        WeaponRequestDto requestDto = WeaponFactory.createWeaponRequestDtoWithoutDamage(List.of(bonus.getId()));

        mockMvc.perform(post(API_BASE_ITEMS + API_BASE_WEAPONS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(weaponRepository.count()).isEqualTo(0);
    }

    @Test
    @SneakyThrows
    void addWeapon_shouldRespondWithBadRequestStatus_whenWeaponTypeIsNull() {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        WeaponRequestDto requestDto = WeaponFactory.createWeaponRequestDtoWithoutWeaponType(List.of(bonus.getId()));

        mockMvc.perform(post(API_BASE_ITEMS + API_BASE_WEAPONS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(weaponRepository.count()).isEqualTo(0);
    }

    @Test
    @SneakyThrows
    void addWeapon_shouldRespondWithBadRequestStatus_whenWeaponTypeIsInvalid() {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        WeaponRequestDto requestDto = WeaponFactory.createWeaponRequestDtoWithInvalidWeaponType(List.of(bonus.getId()));

        mockMvc.perform(post(API_BASE_ITEMS + API_BASE_WEAPONS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(weaponRepository.count()).isEqualTo(0);
    }

    @Test
    @SneakyThrows
    void addWeapon_shouldRespondWithBadRequestStatus_whenRequiredLevelIsNull() {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        WeaponRequestDto requestDto = WeaponFactory.createWeaponRequestDtoWithoutRequiredLevel(List.of(bonus.getId()));

        mockMvc.perform(post(API_BASE_ITEMS + API_BASE_WEAPONS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(weaponRepository.count()).isEqualTo(0);
    }

    @Test
    @SneakyThrows
    void addWeapon_shouldRespondWithBadRequestStatus_whenRequiredAgilityIsNull() {
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
    @SneakyThrows
    void addWeapon_shouldRespondWithBadRequestStatus_whenRequiredIntelligenceIsNull() {
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
    @SneakyThrows
    void addWeapon_shouldRespondWithBadRequestStatus_whenRequiredStrengthIsNull() {
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
    @SneakyThrows
    void addWeapon_shouldRespondWithBadRequestStatus_whenRarityIsNull() {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        WeaponRequestDto requestDto = WeaponFactory.createWeaponRequestDtoWithoutRarity(List.of(bonus.getId()));

        mockMvc.perform(post(API_BASE_ITEMS + API_BASE_WEAPONS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(weaponRepository.count()).isEqualTo(0);
    }

    @Test
    @SneakyThrows
    void addWeapon_shouldRespondWithBadRequestStatus_whenRarityIsInvalid() {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        WeaponRequestDto requestDto = WeaponFactory.createWeaponRequestDtoWithInvalidRarity(List.of(bonus.getId()));

        mockMvc.perform(post(API_BASE_ITEMS + API_BASE_WEAPONS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(weaponRepository.count()).isEqualTo(0);
    }

    @Test
    @SneakyThrows
    void addWeapon_shouldRespondWithBadRequestStatus_whenBonusIdsIsNull() {
        WeaponRequestDto requestDto = WeaponFactory.createWeaponRequestDtoWithoutBonusIds();

        mockMvc.perform(post(API_BASE_ITEMS + API_BASE_WEAPONS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(weaponRepository.count()).isEqualTo(0);
    }

    @Test
    @SneakyThrows
    void getAllWeapons_shouldReturnAllWeaponResponseDtos() {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        Weapon firstWeapon = weaponRepository.save(WeaponFactory.weaponInstance(List.of(bonus)));
        Weapon secondWeapon = weaponRepository.save(WeaponFactory.weaponInstance(List.of(bonus)));

        mockMvc.perform(get(API_BASE_ITEMS + API_BASE_WEAPONS))
            .andExpect(status().isFound())
            .andExpect(jsonPath(buildJsonPathToLength()).value(2))
            .andExpect(jsonPath(buildJsonPathToIdInListByIndex(0)).value(firstWeapon.getId()))
            .andExpect(jsonPath(buildJsonPathToDamageInListByIndex(0)).value(firstWeapon.getDamage()))
            .andExpect(jsonPath(buildJsonPathToRarityInListByIndex(0)).value(firstWeapon.getRarity().name()))
            .andExpect(jsonPath(buildJsonPathToWeaponTypeInListByIndex(0)).value(firstWeapon.getWeaponType().name()))
            .andExpect(jsonPath(buildJsonPathToRequiredAgilityInListByIndex(0)).value(firstWeapon.getRequiredAgility()))
            .andExpect(jsonPath(buildJsonPathToRequiredIntelligenceInListByIndex(0))
                .value(firstWeapon.getRequiredIntelligence()))
            .andExpect(jsonPath(buildJsonPathToRequiredLevelInListByIndex(0)).value(firstWeapon.getRequiredLevel()))
            .andExpect(
                jsonPath(buildJsonPathToRequiredStrengthInListByIndex(0)).value(firstWeapon.getRequiredStrength()))
            .andExpect(jsonPath(buildJsonPathToBonusesInListByIndex(0)).exists())
            .andExpect(jsonPath(buildJsonPathToIdInBonusListNestedInSkillListByIndexes(0, 0)).value(bonus.getId()))
            .andExpect(jsonPath(buildJsonPathToNameInBonusListNestedInSkillListByIndexes(0, 0)).value(bonus.getName()))
            .andExpect(jsonPath(buildJsonPathToRarityInBonusListNestedInSkillListByIndexes(0, 0))
                .value(bonus.getRarity().toString()))
            .andExpect(jsonPath(buildJsonPathToDamageBoostInBonusListNestedInSkillListByIndexes(0, 0))
                .value(bonus.getDamageBoost()))
            .andExpect(jsonPath(buildJsonPathToCritChanceBoostInBonusListNestedInSkillListByIndexes(0, 0))
                .value(bonus.getCritChanceBoost()))
            .andExpect(jsonPath(buildJsonPathToCritDamageBoostInBonusListNestedInSkillListByIndexes(0, 0))
                .value(bonus.getCritDamageBoost()))
            .andExpect(jsonPath(buildJsonPathToSkillBoostInBonusListNestedInSkillListByIndexes(0, 0))
                .value(bonus.getSkillBoost()))
            .andExpect(jsonPath(buildJsonPathToIdInListByIndex(1)).value(secondWeapon.getId()))
            .andExpect(jsonPath(buildJsonPathToDamageInListByIndex(1)).value(secondWeapon.getDamage()))
            .andExpect(jsonPath(buildJsonPathToRarityInListByIndex(1)).value(secondWeapon.getRarity().name()))
            .andExpect(jsonPath(buildJsonPathToWeaponTypeInListByIndex(1)).value(secondWeapon.getWeaponType().name()))
            .andExpect(
                jsonPath(buildJsonPathToRequiredAgilityInListByIndex(1)).value(secondWeapon.getRequiredAgility()))
            .andExpect(jsonPath(buildJsonPathToRequiredIntelligenceInListByIndex(1))
                .value(secondWeapon.getRequiredIntelligence()))
            .andExpect(jsonPath(buildJsonPathToRequiredLevelInListByIndex(1)).value(secondWeapon.getRequiredLevel()))
            .andExpect(
                jsonPath(buildJsonPathToRequiredStrengthInListByIndex(1)).value(secondWeapon.getRequiredStrength()))
            .andExpect(jsonPath(buildJsonPathToBonusesInListByIndex(1)).exists())
            .andExpect(jsonPath(buildJsonPathToIdInBonusListNestedInSkillListByIndexes(1, 0)).value(bonus.getId()))
            .andExpect(jsonPath(buildJsonPathToNameInBonusListNestedInSkillListByIndexes(1, 0)).value(bonus.getName()))
            .andExpect(jsonPath(buildJsonPathToRarityInBonusListNestedInSkillListByIndexes(1, 0))
                .value(bonus.getRarity().toString()))
            .andExpect(jsonPath(buildJsonPathToDamageBoostInBonusListNestedInSkillListByIndexes(1, 0))
                .value(bonus.getDamageBoost()))
            .andExpect(jsonPath(buildJsonPathToCritChanceBoostInBonusListNestedInSkillListByIndexes(1, 0))
                .value(bonus.getCritChanceBoost()))
            .andExpect(jsonPath(buildJsonPathToCritDamageBoostInBonusListNestedInSkillListByIndexes(1, 0))
                .value(bonus.getCritDamageBoost()))
            .andExpect(jsonPath(buildJsonPathToSkillBoostInBonusListNestedInSkillListByIndexes(1, 0))
                .value(bonus.getSkillBoost()));
    }

    @Test
    @SneakyThrows
    void getWeaponById_shouldReturnWeaponResponseDto_whenIdIsCorrect() {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        Weapon weapon = weaponRepository.save(WeaponFactory.weaponInstance(List.of(bonus)));

        mockMvc.perform(get(buildGetWeaponByIdUrl(weapon.getId())))
            .andExpect(status().isFound())
            .andExpect(jsonPath(buildJsonPathToId()).value(weapon.getId()))
            .andExpect(jsonPath(buildJsonPathToDamage()).value(weapon.getDamage()))
            .andExpect(jsonPath(buildJsonPathToRarity()).value(weapon.getRarity().name()))
            .andExpect(jsonPath(buildJsonPathToWeaponType()).value(weapon.getWeaponType().name()))
            .andExpect(jsonPath(buildJsonPathToRequiredAgility()).value(weapon.getRequiredAgility()))
            .andExpect(jsonPath(buildJsonPathToRequiredIntelligence()).value(weapon.getRequiredIntelligence()))
            .andExpect(jsonPath(buildJsonPathToRequiredLevel()).value(weapon.getRequiredLevel()))
            .andExpect(jsonPath(buildJsonPathToRequiredStrength()).value(weapon.getRequiredStrength()))
            .andExpect(jsonPath(buildJsonPathToBonuses()).exists())
            .andExpect(jsonPath(buildJsonPathToBonusesLength()).value(1))
            .andExpect(jsonPath(buildJsonPathToIdInBonusListByIndex(0)).value(bonus.getId()))
            .andExpect(jsonPath(buildJsonPathToNameInBonusListByIndex(0)).value(bonus.getName()))
            .andExpect(jsonPath(buildJsonPathToRarityInBonusListByIndex(0)).value(bonus.getRarity().toString()))
            .andExpect(jsonPath(buildJsonPathToDamageBoostInBonusListByIndex(0)).value(bonus.getDamageBoost()))
            .andExpect(jsonPath(buildJsonPathToCritChanceBoostInBonusListByIndex(0)).value(bonus.getCritChanceBoost()))
            .andExpect(jsonPath(buildJsonPathToCritDamageBoostInBonusListByIndex(0)).value(bonus.getCritDamageBoost()))
            .andExpect(jsonPath(buildJsonPathToSkillBoostInBonusListByIndex(0)).value(bonus.getSkillBoost()));
    }

    @Test
    @SneakyThrows
    void updateWeapon_shouldUpdateWeaponAndReturnWeaponResponseDto_whenRequestIsCorrect() {
        Bonus firstBonus = bonusRepository.save(BonusFactory.bonusInstance());
        Bonus secondBonus = bonusRepository.save(BonusFactory.bonusInstance());
        Weapon weapon = weaponRepository.save(WeaponFactory.weaponInstance(List.of(firstBonus)));

        WeaponRequestDto requestDto = WeaponFactory.updateWeaponRequestDto(List.of(secondBonus.getId()));

        mockMvc.perform(put(buildPutWeaponByIdUrl(weapon.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(jsonPath(buildJsonPathToId()).value(weapon.getId()))
            .andExpect(jsonPath(buildJsonPathToDamage()).value(requestDto.getDamage()))
            .andExpect(jsonPath(buildJsonPathToRarity()).value(requestDto.getRarity()))
            .andExpect(jsonPath(buildJsonPathToWeaponType()).value(requestDto.getWeaponType()))
            .andExpect(jsonPath(buildJsonPathToRequiredAgility()).value(requestDto.getRequiredAgility()))
            .andExpect(jsonPath(buildJsonPathToRequiredIntelligence()).value(requestDto.getRequiredIntelligence()))
            .andExpect(jsonPath(buildJsonPathToRequiredLevel()).value(requestDto.getRequiredLevel()))
            .andExpect(jsonPath(buildJsonPathToRequiredStrength()).value(requestDto.getRequiredStrength()))
            .andExpect(jsonPath(buildJsonPathToBonuses()).exists())
            .andExpect(jsonPath(buildJsonPathToBonusesLength()).value(1))
            .andExpect(jsonPath(buildJsonPathToIdInBonusListByIndex(0)).value(secondBonus.getId()))
            .andExpect(jsonPath(buildJsonPathToNameInBonusListByIndex(0)).value(secondBonus.getName()))
            .andExpect(jsonPath(buildJsonPathToRarityInBonusListByIndex(0)).value(secondBonus.getRarity().toString()))
            .andExpect(jsonPath(buildJsonPathToDamageBoostInBonusListByIndex(0)).value(secondBonus.getDamageBoost()))
            .andExpect(
                jsonPath(buildJsonPathToCritChanceBoostInBonusListByIndex(0)).value(secondBonus.getCritChanceBoost()))
            .andExpect(
                jsonPath(buildJsonPathToCritDamageBoostInBonusListByIndex(0)).value(secondBonus.getCritDamageBoost()))
            .andExpect(jsonPath(buildJsonPathToSkillBoostInBonusListByIndex(0)).value(secondBonus.getSkillBoost()));

        Weapon updatedWeapon = weaponRepository.findById(weapon.getId()).get();

        assertThat(updatedWeapon.getDamage()).isEqualTo(requestDto.getDamage());
        assertThat(updatedWeapon.getWeaponType().name()).isEqualTo(requestDto.getWeaponType());
        assertThat(updatedWeapon.getRarity().name()).isEqualTo(requestDto.getRarity());
        assertThat(updatedWeapon.getRequiredAgility()).isEqualTo(requestDto.getRequiredAgility());
        assertThat(updatedWeapon.getRequiredIntelligence()).isEqualTo(requestDto.getRequiredIntelligence());
        assertThat(updatedWeapon.getRequiredLevel()).isEqualTo(requestDto.getRequiredLevel());
        assertThat(updatedWeapon.getRequiredStrength()).isEqualTo(requestDto.getRequiredStrength());
    }

    @Test
    @SneakyThrows
    void updateWeapon_shouldRespondWithBadRequestStatus_whenDamageIsNull() {
        Weapon weapon = weaponRepository.save(WeaponFactory.weaponInstance());
        WeaponRequestDto requestDto = WeaponFactory.updateWeaponRequestDtoWithoutDamage();

        mockMvc.perform(put(buildPutWeaponByIdUrl(weapon.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        weapon = weaponRepository.findById(weapon.getId()).get();

        assertThat(weapon.getDamage()).isNotEqualTo(requestDto.getDamage());
        assertThat(weapon.getWeaponType().name()).isNotEqualTo(requestDto.getWeaponType());
        assertThat(weapon.getRarity().name()).isNotEqualTo(requestDto.getRarity());
        assertThat(weapon.getRequiredAgility()).isNotEqualTo(requestDto.getRequiredAgility());
        assertThat(weapon.getRequiredIntelligence()).isNotEqualTo(requestDto.getRequiredIntelligence());
        assertThat(weapon.getRequiredLevel()).isNotEqualTo(requestDto.getRequiredLevel());
        assertThat(weapon.getRequiredStrength()).isNotEqualTo(requestDto.getRequiredStrength());
    }

    @Test
    @SneakyThrows
    void updateWeapon_shouldRespondWithBadRequestStatus_whenRarityIsNull() {
        Weapon weapon = weaponRepository.save(WeaponFactory.weaponInstance());
        WeaponRequestDto requestDto = WeaponFactory.updateWeaponRequestDtoWithoutRarity();

        mockMvc.perform(put(buildPutWeaponByIdUrl(weapon.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        weapon = weaponRepository.findById(weapon.getId()).get();

        assertThat(weapon.getDamage()).isNotEqualTo(requestDto.getDamage());
        assertThat(weapon.getWeaponType().name()).isNotEqualTo(requestDto.getWeaponType());
        assertThat(weapon.getRarity().name()).isNotEqualTo(requestDto.getRarity());
        assertThat(weapon.getRequiredAgility()).isNotEqualTo(requestDto.getRequiredAgility());
        assertThat(weapon.getRequiredIntelligence()).isNotEqualTo(requestDto.getRequiredIntelligence());
        assertThat(weapon.getRequiredLevel()).isNotEqualTo(requestDto.getRequiredLevel());
        assertThat(weapon.getRequiredStrength()).isNotEqualTo(requestDto.getRequiredStrength());
    }

    @Test
    @SneakyThrows
    void updateWeapon_shouldRespondWithBadRequestStatus_whenRarityIsInvalid() {
        Weapon weapon = weaponRepository.save(WeaponFactory.weaponInstance());
        WeaponRequestDto requestDto = WeaponFactory.updateWeaponRequestDtoWithInvalidRarity();

        mockMvc.perform(put(buildPutWeaponByIdUrl(weapon.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        weapon = weaponRepository.findById(weapon.getId()).get();

        assertThat(weapon.getDamage()).isNotEqualTo(requestDto.getDamage());
        assertThat(weapon.getWeaponType().name()).isNotEqualTo(requestDto.getWeaponType());
        assertThat(weapon.getRarity().name()).isNotEqualTo(requestDto.getRarity());
        assertThat(weapon.getRequiredAgility()).isNotEqualTo(requestDto.getRequiredAgility());
        assertThat(weapon.getRequiredIntelligence()).isNotEqualTo(requestDto.getRequiredIntelligence());
        assertThat(weapon.getRequiredLevel()).isNotEqualTo(requestDto.getRequiredLevel());
        assertThat(weapon.getRequiredStrength()).isNotEqualTo(requestDto.getRequiredStrength());
    }

    @Test
    @SneakyThrows
    void updateWeapon_shouldRespondWithBadRequestStatus_whenWeaponTypeIsNull() {
        Weapon weapon = weaponRepository.save(WeaponFactory.weaponInstance());
        WeaponRequestDto requestDto = WeaponFactory.updateWeaponRequestDtoWithoutWeaponType();

        mockMvc.perform(put(buildPutWeaponByIdUrl(weapon.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        weapon = weaponRepository.findById(weapon.getId()).get();

        assertThat(weapon.getDamage()).isNotEqualTo(requestDto.getDamage());
        assertThat(weapon.getWeaponType().name()).isNotEqualTo(requestDto.getWeaponType());
        assertThat(weapon.getRarity().name()).isNotEqualTo(requestDto.getRarity());
        assertThat(weapon.getRequiredAgility()).isNotEqualTo(requestDto.getRequiredAgility());
        assertThat(weapon.getRequiredIntelligence()).isNotEqualTo(requestDto.getRequiredIntelligence());
        assertThat(weapon.getRequiredLevel()).isNotEqualTo(requestDto.getRequiredLevel());
        assertThat(weapon.getRequiredStrength()).isNotEqualTo(requestDto.getRequiredStrength());
    }

    @Test
    @SneakyThrows
    void updateWeapon_shouldRespondWithBadRequestStatus_whenWeaponTypeIsInvalid() {
        Weapon weapon = weaponRepository.save(WeaponFactory.weaponInstance());
        WeaponRequestDto requestDto = WeaponFactory.updateWeaponRequestDtoWithInvalidWeaponType();

        mockMvc.perform(put(buildPutWeaponByIdUrl(weapon.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        weapon = weaponRepository.findById(weapon.getId()).get();

        assertThat(weapon.getDamage()).isNotEqualTo(requestDto.getDamage());
        assertThat(weapon.getWeaponType().name()).isNotEqualTo(requestDto.getWeaponType());
        assertThat(weapon.getRarity().name()).isNotEqualTo(requestDto.getRarity());
        assertThat(weapon.getRequiredAgility()).isNotEqualTo(requestDto.getRequiredAgility());
        assertThat(weapon.getRequiredIntelligence()).isNotEqualTo(requestDto.getRequiredIntelligence());
        assertThat(weapon.getRequiredLevel()).isNotEqualTo(requestDto.getRequiredLevel());
        assertThat(weapon.getRequiredStrength()).isNotEqualTo(requestDto.getRequiredStrength());
    }

    @Test
    @SneakyThrows
    void updateWeapon_shouldRespondWithBadRequestStatus_whenRequiredAgilityIsNull() {
        Weapon weapon = weaponRepository.save(WeaponFactory.weaponInstance());
        WeaponRequestDto requestDto = WeaponFactory.updateWeaponRequestDtoWithoutRequiredAgility();

        mockMvc.perform(put(buildPutWeaponByIdUrl(weapon.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        weapon = weaponRepository.findById(weapon.getId()).get();

        assertThat(weapon.getDamage()).isNotEqualTo(requestDto.getDamage());
        assertThat(weapon.getWeaponType().name()).isNotEqualTo(requestDto.getWeaponType());
        assertThat(weapon.getRarity().name()).isNotEqualTo(requestDto.getRarity());
        assertThat(weapon.getRequiredAgility()).isNotEqualTo(requestDto.getRequiredAgility());
        assertThat(weapon.getRequiredIntelligence()).isNotEqualTo(requestDto.getRequiredIntelligence());
        assertThat(weapon.getRequiredLevel()).isNotEqualTo(requestDto.getRequiredLevel());
        assertThat(weapon.getRequiredStrength()).isNotEqualTo(requestDto.getRequiredStrength());
    }

    @Test
    @SneakyThrows
    void updateWeapon_shouldRespondWithBadRequestStatus_whenRequiredIntelligenceIsNull() {
        Weapon weapon = weaponRepository.save(WeaponFactory.weaponInstance());
        WeaponRequestDto requestDto = WeaponFactory.updateWeaponRequestDtoWithoutRequiredIntelligence();

        mockMvc.perform(put(buildPutWeaponByIdUrl(weapon.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        weapon = weaponRepository.findById(weapon.getId()).get();

        assertThat(weapon.getDamage()).isNotEqualTo(requestDto.getDamage());
        assertThat(weapon.getWeaponType().name()).isNotEqualTo(requestDto.getWeaponType());
        assertThat(weapon.getRarity().name()).isNotEqualTo(requestDto.getRarity());
        assertThat(weapon.getRequiredAgility()).isNotEqualTo(requestDto.getRequiredAgility());
        assertThat(weapon.getRequiredIntelligence()).isNotEqualTo(requestDto.getRequiredIntelligence());
        assertThat(weapon.getRequiredLevel()).isNotEqualTo(requestDto.getRequiredLevel());
        assertThat(weapon.getRequiredStrength()).isNotEqualTo(requestDto.getRequiredStrength());
    }

    @Test
    @SneakyThrows
    void updateWeapon_shouldRespondWithBadRequestStatus_whenRequiredLevelIsNull() {
        Weapon weapon = weaponRepository.save(WeaponFactory.weaponInstance());
        WeaponRequestDto requestDto = WeaponFactory.updateWeaponRequestDtoWithoutRequiredLevel();

        mockMvc.perform(put(buildPutWeaponByIdUrl(weapon.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        weapon = weaponRepository.findById(weapon.getId()).get();

        assertThat(weapon.getDamage()).isNotEqualTo(requestDto.getDamage());
        assertThat(weapon.getWeaponType().name()).isNotEqualTo(requestDto.getWeaponType());
        assertThat(weapon.getRarity().name()).isNotEqualTo(requestDto.getRarity());
        assertThat(weapon.getRequiredAgility()).isNotEqualTo(requestDto.getRequiredAgility());
        assertThat(weapon.getRequiredIntelligence()).isNotEqualTo(requestDto.getRequiredIntelligence());
        assertThat(weapon.getRequiredLevel()).isNotEqualTo(requestDto.getRequiredLevel());
        assertThat(weapon.getRequiredStrength()).isNotEqualTo(requestDto.getRequiredStrength());
    }

    @Test
    @SneakyThrows
    void updateWeapon_shouldRespondWithBadRequestStatus_whenRequiredStrengthIsNull() {
        Weapon weapon = weaponRepository.save(WeaponFactory.weaponInstance());
        WeaponRequestDto requestDto = WeaponFactory.updateWeaponRequestDtoWithoutRequiredStrength();

        mockMvc.perform(put(buildPutWeaponByIdUrl(weapon.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        weapon = weaponRepository.findById(weapon.getId()).get();

        assertThat(weapon.getDamage()).isNotEqualTo(requestDto.getDamage());
        assertThat(weapon.getWeaponType().name()).isNotEqualTo(requestDto.getWeaponType());
        assertThat(weapon.getRarity().name()).isNotEqualTo(requestDto.getRarity());
        assertThat(weapon.getRequiredAgility()).isNotEqualTo(requestDto.getRequiredAgility());
        assertThat(weapon.getRequiredIntelligence()).isNotEqualTo(requestDto.getRequiredIntelligence());
        assertThat(weapon.getRequiredLevel()).isNotEqualTo(requestDto.getRequiredLevel());
        assertThat(weapon.getRequiredStrength()).isNotEqualTo(requestDto.getRequiredStrength());
    }

    @Test
    @SneakyThrows
    void updateWeapon_shouldRespondWithBadRequestStatus_whenBonusIdsIsNull() {
        Weapon weapon = weaponRepository.save(WeaponFactory.weaponInstance());
        WeaponRequestDto requestDto = WeaponFactory.updateWeaponRequestDtoWithoutBonusIds();

        mockMvc.perform(put(buildPutWeaponByIdUrl(weapon.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        weapon = weaponRepository.findById(weapon.getId()).get();

        assertThat(weapon.getDamage()).isNotEqualTo(requestDto.getDamage());
        assertThat(weapon.getWeaponType().name()).isNotEqualTo(requestDto.getWeaponType());
        assertThat(weapon.getRarity().name()).isNotEqualTo(requestDto.getRarity());
        assertThat(weapon.getRequiredAgility()).isNotEqualTo(requestDto.getRequiredAgility());
        assertThat(weapon.getRequiredIntelligence()).isNotEqualTo(requestDto.getRequiredIntelligence());
        assertThat(weapon.getRequiredLevel()).isNotEqualTo(requestDto.getRequiredLevel());
        assertThat(weapon.getRequiredStrength()).isNotEqualTo(requestDto.getRequiredStrength());
    }

    @Test
    @SneakyThrows
    void deleteWeapon_shouldDeleteWeapon_whenIdIsCorrect() {
        Weapon weapon = weaponRepository.save(WeaponFactory.weaponInstance());

        mockMvc.perform(delete(buildDeleteWeaponByIdUrl(weapon.getId())))
            .andExpect(status().isOk());

        assertThat(weaponRepository.findById(weapon.getId())).isEmpty();
    }

    @Test
    @SneakyThrows
    void deleteWeapon_shouldDeleteWeaponRelatedToExistingBonus_whenIdIsCorrect() {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        Weapon weapon = weaponRepository.save(WeaponFactory.weaponInstance(List.of(bonus)));

        mockMvc.perform(delete(buildDeleteWeaponByIdUrl(weapon.getId())))
            .andExpect(status().isOk());

        assertThat(weaponRepository.findById(weapon.getId())).isEmpty();
    }
}
