package com.knightcharacter.app.integration;

import static com.knightcharacter.app.Constants.API_BASE_ARMORS;
import static com.knightcharacter.app.Constants.API_BASE_ITEMS;
import static com.knightcharacter.app.TestConstants.buildDeleteArmorByIdUrl;
import static com.knightcharacter.app.TestConstants.buildGetArmorByIdUrl;
import static com.knightcharacter.app.TestConstants.buildJsonPathToArmorType;
import static com.knightcharacter.app.TestConstants.buildJsonPathToArmorTypeInListByIndex;
import static com.knightcharacter.app.TestConstants.buildJsonPathToBonuses;
import static com.knightcharacter.app.TestConstants.buildJsonPathToBonusesInListByIndex;
import static com.knightcharacter.app.TestConstants.buildJsonPathToBonusesLength;
import static com.knightcharacter.app.TestConstants.buildJsonPathToCritChanceBoostInBonusListByIndex;
import static com.knightcharacter.app.TestConstants.buildJsonPathToCritChanceBoostInBonusListNestedInSkillListByIndexes;
import static com.knightcharacter.app.TestConstants.buildJsonPathToCritDamageBoostInBonusListByIndex;
import static com.knightcharacter.app.TestConstants.buildJsonPathToCritDamageBoostInBonusListNestedInSkillListByIndexes;
import static com.knightcharacter.app.TestConstants.buildJsonPathToDamageBoostInBonusListByIndex;
import static com.knightcharacter.app.TestConstants.buildJsonPathToDamageBoostInBonusListNestedInSkillListByIndexes;
import static com.knightcharacter.app.TestConstants.buildJsonPathToDefence;
import static com.knightcharacter.app.TestConstants.buildJsonPathToDefenceInListByIndex;
import static com.knightcharacter.app.TestConstants.buildJsonPathToDescription;
import static com.knightcharacter.app.TestConstants.buildJsonPathToDescriptionInListByIndex;
import static com.knightcharacter.app.TestConstants.buildJsonPathToId;
import static com.knightcharacter.app.TestConstants.buildJsonPathToIdInBonusListByIndex;
import static com.knightcharacter.app.TestConstants.buildJsonPathToIdInBonusListNestedInSkillListByIndexes;
import static com.knightcharacter.app.TestConstants.buildJsonPathToIdInListByIndex;
import static com.knightcharacter.app.TestConstants.buildJsonPathToLength;
import static com.knightcharacter.app.TestConstants.buildJsonPathToName;
import static com.knightcharacter.app.TestConstants.buildJsonPathToNameInBonusListByIndex;
import static com.knightcharacter.app.TestConstants.buildJsonPathToNameInBonusListNestedInSkillListByIndexes;
import static com.knightcharacter.app.TestConstants.buildJsonPathToNameInListByIndex;
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
import static com.knightcharacter.app.TestConstants.buildPutArmorByIdUrl;

import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.knightcharacter.app.factories.ArmorFactory;
import com.knightcharacter.app.factories.BonusFactory;
import com.knightcharacter.app.gateway.privatedb.repository.ArmorRepository;
import com.knightcharacter.app.gateway.privatedb.repository.BonusRepository;
import com.knightcharacter.app.gateway.privatedb.representation.Armor;
import com.knightcharacter.app.gateway.privatedb.representation.Bonus;
import com.knightcharacter.app.rest.request.ArmorRequestDto;

import java.util.List;

import lombok.AccessLevel;
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
class ArmorResourceIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ArmorRepository armorRepository;

    @Autowired
    BonusRepository bonusRepository;

    @AfterEach
    void tearDown() {
        armorRepository.deleteAll();
        bonusRepository.deleteAll();
    }

    @Test
    @Transactional
    void addArmor_shouldAddArmorAndReturnArmorResponseDto_whenRequestIsCorrect() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        ArmorRequestDto requestDto = ArmorFactory.createArmorRequestDto(List.of(bonus.getId()));

        mockMvc.perform(post(API_BASE_ITEMS + API_BASE_ARMORS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isCreated())
            .andExpect(jsonPath(buildJsonPathToId()).exists())
            .andExpect(jsonPath(buildJsonPathToDefence()).value(requestDto.getDefence()))
            .andExpect(jsonPath(buildJsonPathToRarity()).value(requestDto.getRarity()))
            .andExpect(jsonPath(buildJsonPathToArmorType()).value(requestDto.getArmorType()))
            .andExpect(jsonPath(buildJsonPathToName()).value(requestDto.getName()))
            .andExpect(jsonPath(buildJsonPathToDescription()).value(requestDto.getDescription()))
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

        Armor savedArmor = armorRepository.findAll().get(0);

        assertThat(savedArmor.getBonuses()).contains(bonus);
        assertThat(savedArmor.getDefence()).isEqualTo(requestDto.getDefence());
        assertThat(savedArmor.getRarity().name()).isEqualTo(requestDto.getRarity());
        assertThat(savedArmor.getArmorType().name()).isEqualTo(requestDto.getArmorType());
        assertThat(savedArmor.getName()).isEqualTo(requestDto.getName());
        assertThat(savedArmor.getDescription()).isEqualTo(requestDto.getDescription());
        assertThat(savedArmor.getRequiredAgility()).isEqualTo(requestDto.getRequiredAgility());
        assertThat(savedArmor.getRequiredIntelligence()).isEqualTo(requestDto.getRequiredIntelligence());
        assertThat(savedArmor.getRequiredLevel()).isEqualTo(requestDto.getRequiredLevel());
        assertThat(savedArmor.getRequiredStrength()).isEqualTo(requestDto.getRequiredStrength());
    }

    @Test
    void addArmor_shouldRespondWithBadRequestStatus_whenDefenceIsNull() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        ArmorRequestDto requestDto = ArmorFactory.createArmorRequestDtoWithoutDefence(List.of(bonus.getId()));

        mockMvc.perform(post(API_BASE_ITEMS + API_BASE_ARMORS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(armorRepository.count()).isEqualTo(0);
    }

    @Test
    void addArmor_shouldRespondWithBadRequestStatus_whenArmorTypeIsNull() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        ArmorRequestDto requestDto = ArmorFactory.createArmorRequestDtoWithoutArmorType(List.of(bonus.getId()));

        mockMvc.perform(post(API_BASE_ITEMS + API_BASE_ARMORS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(armorRepository.count()).isEqualTo(0);
    }

    @Test
    void addArmor_shouldRespondWithBadRequestStatus_whenArmorTypeIsInvalid() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        ArmorRequestDto requestDto = ArmorFactory.createArmorRequestDtoWithInvalidArmorType(List.of(bonus.getId()));

        mockMvc.perform(post(API_BASE_ITEMS + API_BASE_ARMORS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(armorRepository.count()).isEqualTo(0);
    }

    @Test
    void addArmor_shouldRespondWithBadRequestStatus_whenNameIsNull() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        ArmorRequestDto requestDto = ArmorFactory.createArmorRequestDtoWithoutName(List.of(bonus.getId()));

        mockMvc.perform(post(API_BASE_ITEMS + API_BASE_ARMORS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(armorRepository.count()).isEqualTo(0);
    }

    @Test
    void addArmor_shouldRespondWithBadRequestStatus_whenNameConsistsOfSpaces() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        ArmorRequestDto requestDto = ArmorFactory
            .createArmorRequestDtoWithNameConsistingOfSpaces(List.of(bonus.getId()));

        mockMvc.perform(post(API_BASE_ITEMS + API_BASE_ARMORS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(armorRepository.count()).isEqualTo(0);
    }

    @Test
    void addArmor_shouldRespondWithBadRequestStatus_whenDescriptionIsNull() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        ArmorRequestDto requestDto = ArmorFactory.createArmorRequestDtoWithoutDescription(List.of(bonus.getId()));

        mockMvc.perform(post(API_BASE_ITEMS + API_BASE_ARMORS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(armorRepository.count()).isEqualTo(0);
    }

    @Test
    void addArmor_shouldRespondWithBadRequestStatus_whenDescriptionConsistsOfSpaces() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        ArmorRequestDto requestDto = ArmorFactory
            .createArmorRequestDtoWithDescriptionConsistingOfSpaces(List.of(bonus.getId()));

        mockMvc.perform(post(API_BASE_ITEMS + API_BASE_ARMORS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(armorRepository.count()).isEqualTo(0);
    }

    @Test
    void addArmor_shouldRespondWithBadRequestStatus_whenRequiredLevelIsNull() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        ArmorRequestDto requestDto = ArmorFactory.createArmorRequestDtoWithoutRequiredLevel(List.of(bonus.getId()));

        mockMvc.perform(post(API_BASE_ITEMS + API_BASE_ARMORS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(armorRepository.count()).isEqualTo(0);
    }

    @Test
    void addArmor_shouldRespondWithBadRequestStatus_whenRequiredAgilityIsNull() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        ArmorRequestDto requestDto = ArmorFactory.createArmorRequestDtoWithoutRequiredAgility(List.of(bonus.getId()));

        mockMvc.perform(post(API_BASE_ITEMS + API_BASE_ARMORS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(armorRepository.count()).isEqualTo(0);
    }

    @Test
    void addArmor_shouldRespondWithBadRequestStatus_whenRequiredIntelligenceIsNull() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        ArmorRequestDto requestDto = ArmorFactory
            .createArmorRequestDtoWithoutRequiredIntelligence(List.of(bonus.getId()));

        mockMvc.perform(post(API_BASE_ITEMS + API_BASE_ARMORS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(armorRepository.count()).isEqualTo(0);
    }

    @Test
    void addArmor_shouldRespondWithBadRequestStatus_whenRequiredStrengthIsNull() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        ArmorRequestDto requestDto = ArmorFactory.createArmorRequestDtoWithoutRequiredStrength(List.of(bonus.getId()));

        mockMvc.perform(post(API_BASE_ITEMS + API_BASE_ARMORS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(armorRepository.count()).isEqualTo(0);
    }

    @Test
    void addArmor_shouldRespondWithBadRequestStatus_whenRarityIsNull() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        ArmorRequestDto requestDto = ArmorFactory.createArmorRequestDtoWithoutRarity(List.of(bonus.getId()));

        mockMvc.perform(post(API_BASE_ITEMS + API_BASE_ARMORS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(armorRepository.count()).isEqualTo(0);
    }

    @Test
    void addArmor_shouldRespondWithBadRequestStatus_whenRarityIsInvalid() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        ArmorRequestDto requestDto = ArmorFactory.createArmorRequestDtoWithInvalidRarity(List.of(bonus.getId()));

        mockMvc.perform(post(API_BASE_ITEMS + API_BASE_ARMORS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(armorRepository.count()).isEqualTo(0);
    }

    @Test
    void addArmor_shouldRespondWithBadRequestStatus_whenBonusIdsIsNull() throws Exception {
        ArmorRequestDto requestDto = ArmorFactory.createArmorRequestDtoWithoutBonusIds();

        mockMvc.perform(post(API_BASE_ITEMS + API_BASE_ARMORS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(armorRepository.count()).isEqualTo(0);
    }

    @Test
    void findAllArmors_shouldReturnAllArmorResponseDtos() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        Armor firstArmor = armorRepository.save(ArmorFactory.armorInstance(List.of(bonus)));
        Armor secondArmor = armorRepository.save(ArmorFactory.armorInstance(List.of(bonus)));

        mockMvc.perform(get(API_BASE_ITEMS + API_BASE_ARMORS))
            .andExpect(status().isFound())
            .andExpect(jsonPath(buildJsonPathToLength()).value(2))
            .andExpect(jsonPath(buildJsonPathToIdInListByIndex(0)).value(firstArmor.getId()))
            .andExpect(jsonPath(buildJsonPathToDefenceInListByIndex(0)).value(firstArmor.getDefence()))
            .andExpect(jsonPath(buildJsonPathToRarityInListByIndex(0)).value(firstArmor.getRarity().name()))
            .andExpect(jsonPath(buildJsonPathToArmorTypeInListByIndex(0)).value(firstArmor.getArmorType().name()))
            .andExpect(jsonPath(buildJsonPathToNameInListByIndex(0)).value(firstArmor.getName()))
            .andExpect(jsonPath(buildJsonPathToDescriptionInListByIndex(0)).value(firstArmor.getDescription()))
            .andExpect(jsonPath(buildJsonPathToRequiredAgilityInListByIndex(0)).value(firstArmor.getRequiredAgility()))
            .andExpect(jsonPath(buildJsonPathToRequiredIntelligenceInListByIndex(0))
                .value(firstArmor.getRequiredIntelligence()))
            .andExpect(jsonPath(buildJsonPathToRequiredLevelInListByIndex(0)).value(firstArmor.getRequiredLevel()))
            .andExpect(jsonPath(buildJsonPathToRequiredStrengthInListByIndex(0))
                .value(firstArmor.getRequiredStrength()))
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
            .andExpect(jsonPath(buildJsonPathToIdInListByIndex(1)).value(secondArmor.getId()))
            .andExpect(jsonPath(buildJsonPathToDefenceInListByIndex(1)).value(secondArmor.getDefence()))
            .andExpect(jsonPath(buildJsonPathToRarityInListByIndex(1)).value(secondArmor.getRarity().name()))
            .andExpect(jsonPath(buildJsonPathToArmorTypeInListByIndex(1)).value(secondArmor.getArmorType().name()))
            .andExpect(jsonPath(buildJsonPathToNameInListByIndex(1)).value(secondArmor.getName()))
            .andExpect(jsonPath(buildJsonPathToDescriptionInListByIndex(1)).value(secondArmor.getDescription()))
            .andExpect(jsonPath(buildJsonPathToRequiredAgilityInListByIndex(1)).value(secondArmor.getRequiredAgility()))
            .andExpect(jsonPath(buildJsonPathToRequiredIntelligenceInListByIndex(1))
                .value(secondArmor.getRequiredIntelligence()))
            .andExpect(jsonPath(buildJsonPathToRequiredLevelInListByIndex(1)).value(secondArmor.getRequiredLevel()))
            .andExpect(jsonPath(buildJsonPathToRequiredStrengthInListByIndex(1))
                .value(secondArmor.getRequiredStrength()))
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
    void findArmorById_shouldReturnArmorResponseDto_whenIdIsCorrect() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        Armor armor = armorRepository.save(ArmorFactory.armorInstance(List.of(bonus)));

        mockMvc.perform(get(buildGetArmorByIdUrl(armor.getId())))
            .andExpect(status().isFound())
            .andExpect(jsonPath(buildJsonPathToId()).value(armor.getId()))
            .andExpect(jsonPath(buildJsonPathToDefence()).value(armor.getDefence()))
            .andExpect(jsonPath(buildJsonPathToRarity()).value(armor.getRarity().name()))
            .andExpect(jsonPath(buildJsonPathToArmorType()).value(armor.getArmorType().name()))
            .andExpect(jsonPath(buildJsonPathToName()).value(armor.getName()))
            .andExpect(jsonPath(buildJsonPathToDescription()).value(armor.getDescription()))
            .andExpect(jsonPath(buildJsonPathToRequiredAgility()).value(armor.getRequiredAgility()))
            .andExpect(jsonPath(buildJsonPathToRequiredIntelligence()).value(armor.getRequiredIntelligence()))
            .andExpect(jsonPath(buildJsonPathToRequiredLevel()).value(armor.getRequiredLevel()))
            .andExpect(jsonPath(buildJsonPathToRequiredStrength()).value(armor.getRequiredStrength()))
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
    void updateArmor_shouldUpdateArmorAndReturnArmorResponseDto_whenRequestIsCorrect() throws Exception {
        Bonus firstBonus = bonusRepository.save(BonusFactory.bonusInstance());
        Bonus secondBonus = bonusRepository.save(BonusFactory.bonusInstance());
        Armor armor = armorRepository.save(ArmorFactory.armorInstance(List.of(firstBonus)));

        ArmorRequestDto requestDto = ArmorFactory.updateArmorRequestDto(List.of(secondBonus.getId()));

        mockMvc.perform(put(buildPutArmorByIdUrl(armor.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(jsonPath(buildJsonPathToId()).value(armor.getId()))
            .andExpect(jsonPath(buildJsonPathToDefence()).value(requestDto.getDefence()))
            .andExpect(jsonPath(buildJsonPathToRarity()).value(requestDto.getRarity()))
            .andExpect(jsonPath(buildJsonPathToArmorType()).value(requestDto.getArmorType()))
            .andExpect(jsonPath(buildJsonPathToName()).value(requestDto.getName()))
            .andExpect(jsonPath(buildJsonPathToDescription()).value(requestDto.getDescription()))
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

        Armor updatedArmor = armorRepository.findById(armor.getId()).get();

        assertThat(updatedArmor.getDefence()).isEqualTo(requestDto.getDefence());
        assertThat(updatedArmor.getArmorType().name()).isEqualTo(requestDto.getArmorType());
        assertThat(updatedArmor.getName()).isEqualTo(requestDto.getName());
        assertThat(updatedArmor.getDescription()).isEqualTo(requestDto.getDescription());
        assertThat(updatedArmor.getRarity().name()).isEqualTo(requestDto.getRarity());
        assertThat(updatedArmor.getRequiredAgility()).isEqualTo(requestDto.getRequiredAgility());
        assertThat(updatedArmor.getRequiredIntelligence()).isEqualTo(requestDto.getRequiredIntelligence());
        assertThat(updatedArmor.getRequiredLevel()).isEqualTo(requestDto.getRequiredLevel());
        assertThat(updatedArmor.getRequiredStrength()).isEqualTo(requestDto.getRequiredStrength());
    }

    @Test
    void updateArmor_shouldRespondWithBadRequestStatus_whenDefenceIsNull() throws Exception {
        Armor armor = armorRepository.save(ArmorFactory.armorInstance());
        ArmorRequestDto requestDto = ArmorFactory.updateArmorRequestDtoWithoutDefence();

        mockMvc.perform(put(buildPutArmorByIdUrl(armor.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        armor = armorRepository.findById(armor.getId()).get();

        assertThat(armor.getDefence()).isNotEqualTo(requestDto.getDefence());
        assertThat(armor.getArmorType().name()).isNotEqualTo(requestDto.getArmorType());
        assertThat(armor.getName()).isNotEqualTo(requestDto.getName());
        assertThat(armor.getDescription()).isNotEqualTo(requestDto.getDescription());
        assertThat(armor.getRarity().name()).isNotEqualTo(requestDto.getRarity());
        assertThat(armor.getRequiredAgility()).isNotEqualTo(requestDto.getRequiredAgility());
        assertThat(armor.getRequiredIntelligence()).isNotEqualTo(requestDto.getRequiredIntelligence());
        assertThat(armor.getRequiredLevel()).isNotEqualTo(requestDto.getRequiredLevel());
        assertThat(armor.getRequiredStrength()).isNotEqualTo(requestDto.getRequiredStrength());
    }

    @Test
    void updateArmor_shouldRespondWithBadRequestStatus_whenRarityIsNull() throws Exception {
        Armor armor = armorRepository.save(ArmorFactory.armorInstance());
        ArmorRequestDto requestDto = ArmorFactory.updateArmorRequestDtoWithoutRarity();

        mockMvc.perform(put(buildPutArmorByIdUrl(armor.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        armor = armorRepository.findById(armor.getId()).get();

        assertThat(armor.getDefence()).isNotEqualTo(requestDto.getDefence());
        assertThat(armor.getArmorType().name()).isNotEqualTo(requestDto.getArmorType());
        assertThat(armor.getName()).isNotEqualTo(requestDto.getName());
        assertThat(armor.getDescription()).isNotEqualTo(requestDto.getDescription());
        assertThat(armor.getRarity().name()).isNotEqualTo(requestDto.getRarity());
        assertThat(armor.getRequiredAgility()).isNotEqualTo(requestDto.getRequiredAgility());
        assertThat(armor.getRequiredIntelligence()).isNotEqualTo(requestDto.getRequiredIntelligence());
        assertThat(armor.getRequiredLevel()).isNotEqualTo(requestDto.getRequiredLevel());
        assertThat(armor.getRequiredStrength()).isNotEqualTo(requestDto.getRequiredStrength());
    }

    @Test
    void updateArmor_shouldRespondWithBadRequestStatus_whenRarityIsInvalid() throws Exception {
        Armor armor = armorRepository.save(ArmorFactory.armorInstance());
        ArmorRequestDto requestDto = ArmorFactory.updateArmorRequestDtoWithInvalidRarity();

        mockMvc.perform(put(buildPutArmorByIdUrl(armor.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        armor = armorRepository.findById(armor.getId()).get();

        assertThat(armor.getDefence()).isNotEqualTo(requestDto.getDefence());
        assertThat(armor.getArmorType().name()).isNotEqualTo(requestDto.getArmorType());
        assertThat(armor.getName()).isNotEqualTo(requestDto.getName());
        assertThat(armor.getDescription()).isNotEqualTo(requestDto.getDescription());
        assertThat(armor.getRarity().name()).isNotEqualTo(requestDto.getRarity());
        assertThat(armor.getRequiredAgility()).isNotEqualTo(requestDto.getRequiredAgility());
        assertThat(armor.getRequiredIntelligence()).isNotEqualTo(requestDto.getRequiredIntelligence());
        assertThat(armor.getRequiredLevel()).isNotEqualTo(requestDto.getRequiredLevel());
        assertThat(armor.getRequiredStrength()).isNotEqualTo(requestDto.getRequiredStrength());
    }

    @Test
    void updateArmor_shouldRespondWithBadRequestStatus_whenArmorTypeIsNull() throws Exception {
        Armor armor = armorRepository.save(ArmorFactory.armorInstance());
        ArmorRequestDto requestDto = ArmorFactory.updateArmorRequestDtoWithoutArmorType();

        mockMvc.perform(put(buildPutArmorByIdUrl(armor.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        armor = armorRepository.findById(armor.getId()).get();

        assertThat(armor.getDefence()).isNotEqualTo(requestDto.getDefence());
        assertThat(armor.getArmorType().name()).isNotEqualTo(requestDto.getArmorType());
        assertThat(armor.getName()).isNotEqualTo(requestDto.getName());
        assertThat(armor.getDescription()).isNotEqualTo(requestDto.getDescription());
        assertThat(armor.getRarity().name()).isNotEqualTo(requestDto.getRarity());
        assertThat(armor.getRequiredAgility()).isNotEqualTo(requestDto.getRequiredAgility());
        assertThat(armor.getRequiredIntelligence()).isNotEqualTo(requestDto.getRequiredIntelligence());
        assertThat(armor.getRequiredLevel()).isNotEqualTo(requestDto.getRequiredLevel());
        assertThat(armor.getRequiredStrength()).isNotEqualTo(requestDto.getRequiredStrength());
    }

    @Test
    void updateArmor_shouldRespondWithBadRequestStatus_whenArmorTypeIsInvalid() throws Exception {
        Armor armor = armorRepository.save(ArmorFactory.armorInstance());
        ArmorRequestDto requestDto = ArmorFactory.updateArmorRequestDtoWithInvalidArmorType();

        mockMvc.perform(put(buildPutArmorByIdUrl(armor.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        armor = armorRepository.findById(armor.getId()).get();

        assertThat(armor.getDefence()).isNotEqualTo(requestDto.getDefence());
        assertThat(armor.getArmorType().name()).isNotEqualTo(requestDto.getArmorType());
        assertThat(armor.getName()).isNotEqualTo(requestDto.getName());
        assertThat(armor.getDescription()).isNotEqualTo(requestDto.getDescription());
        assertThat(armor.getRarity().name()).isNotEqualTo(requestDto.getRarity());
        assertThat(armor.getRequiredAgility()).isNotEqualTo(requestDto.getRequiredAgility());
        assertThat(armor.getRequiredIntelligence()).isNotEqualTo(requestDto.getRequiredIntelligence());
        assertThat(armor.getRequiredLevel()).isNotEqualTo(requestDto.getRequiredLevel());
        assertThat(armor.getRequiredStrength()).isNotEqualTo(requestDto.getRequiredStrength());
    }

    @Test
    void updateArmor_shouldRespondWithBadRequestStatus_whenNameIsNull() throws Exception {
        Armor armor = armorRepository.save(ArmorFactory.armorInstance());
        ArmorRequestDto requestDto = ArmorFactory.updateArmorRequestDtoWithoutName();

        mockMvc.perform(put(buildPutArmorByIdUrl(armor.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        armor = armorRepository.findById(armor.getId()).get();

        assertThat(armor.getDefence()).isNotEqualTo(requestDto.getDefence());
        assertThat(armor.getArmorType().name()).isNotEqualTo(requestDto.getArmorType());
        assertThat(armor.getName()).isNotEqualTo(requestDto.getName());
        assertThat(armor.getDescription()).isNotEqualTo(requestDto.getDescription());
        assertThat(armor.getRarity().name()).isNotEqualTo(requestDto.getRarity());
        assertThat(armor.getRequiredAgility()).isNotEqualTo(requestDto.getRequiredAgility());
        assertThat(armor.getRequiredIntelligence()).isNotEqualTo(requestDto.getRequiredIntelligence());
        assertThat(armor.getRequiredLevel()).isNotEqualTo(requestDto.getRequiredLevel());
        assertThat(armor.getRequiredStrength()).isNotEqualTo(requestDto.getRequiredStrength());
    }

    @Test
    void updateArmor_shouldRespondWithBadRequestStatus_whenNameConsistsOfSpaces() throws Exception {
        Armor armor = armorRepository.save(ArmorFactory.armorInstance());
        ArmorRequestDto requestDto = ArmorFactory.updateArmorRequestDtoWithNameConsistingOfSpaces();

        mockMvc.perform(put(buildPutArmorByIdUrl(armor.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        armor = armorRepository.findById(armor.getId()).get();

        assertThat(armor.getDefence()).isNotEqualTo(requestDto.getDefence());
        assertThat(armor.getArmorType().name()).isNotEqualTo(requestDto.getArmorType());
        assertThat(armor.getName()).isNotEqualTo(requestDto.getName());
        assertThat(armor.getDescription()).isNotEqualTo(requestDto.getDescription());
        assertThat(armor.getRarity().name()).isNotEqualTo(requestDto.getRarity());
        assertThat(armor.getRequiredAgility()).isNotEqualTo(requestDto.getRequiredAgility());
        assertThat(armor.getRequiredIntelligence()).isNotEqualTo(requestDto.getRequiredIntelligence());
        assertThat(armor.getRequiredLevel()).isNotEqualTo(requestDto.getRequiredLevel());
        assertThat(armor.getRequiredStrength()).isNotEqualTo(requestDto.getRequiredStrength());
    }

    @Test
    void updateArmor_shouldRespondWithBadRequestStatus_whenDescriptionIsNull() throws Exception {
        Armor armor = armorRepository.save(ArmorFactory.armorInstance());
        ArmorRequestDto requestDto = ArmorFactory.updateArmorRequestDtoWithoutDescription();

        mockMvc.perform(put(buildPutArmorByIdUrl(armor.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        armor = armorRepository.findById(armor.getId()).get();

        assertThat(armor.getDefence()).isNotEqualTo(requestDto.getDefence());
        assertThat(armor.getArmorType().name()).isNotEqualTo(requestDto.getArmorType());
        assertThat(armor.getName()).isNotEqualTo(requestDto.getName());
        assertThat(armor.getDescription()).isNotEqualTo(requestDto.getDescription());
        assertThat(armor.getRarity().name()).isNotEqualTo(requestDto.getRarity());
        assertThat(armor.getRequiredAgility()).isNotEqualTo(requestDto.getRequiredAgility());
        assertThat(armor.getRequiredIntelligence()).isNotEqualTo(requestDto.getRequiredIntelligence());
        assertThat(armor.getRequiredLevel()).isNotEqualTo(requestDto.getRequiredLevel());
        assertThat(armor.getRequiredStrength()).isNotEqualTo(requestDto.getRequiredStrength());
    }

    @Test
    void updateArmor_shouldRespondWithBadRequestStatus_whenDescriptionConsistsOfSpaces() throws Exception {
        Armor armor = armorRepository.save(ArmorFactory.armorInstance());
        ArmorRequestDto requestDto = ArmorFactory.updateArmorRequestDtoWithDescriptionConsistingOfSpaces();

        mockMvc.perform(put(buildPutArmorByIdUrl(armor.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        armor = armorRepository.findById(armor.getId()).get();

        assertThat(armor.getDefence()).isNotEqualTo(requestDto.getDefence());
        assertThat(armor.getArmorType().name()).isNotEqualTo(requestDto.getArmorType());
        assertThat(armor.getName()).isNotEqualTo(requestDto.getName());
        assertThat(armor.getDescription()).isNotEqualTo(requestDto.getDescription());
        assertThat(armor.getRarity().name()).isNotEqualTo(requestDto.getRarity());
        assertThat(armor.getRequiredAgility()).isNotEqualTo(requestDto.getRequiredAgility());
        assertThat(armor.getRequiredIntelligence()).isNotEqualTo(requestDto.getRequiredIntelligence());
        assertThat(armor.getRequiredLevel()).isNotEqualTo(requestDto.getRequiredLevel());
        assertThat(armor.getRequiredStrength()).isNotEqualTo(requestDto.getRequiredStrength());
    }

    @Test
    void updateArmor_shouldRespondWithBadRequestStatus_whenRequiredAgilityIsNull() throws Exception {
        Armor armor = armorRepository.save(ArmorFactory.armorInstance());
        ArmorRequestDto requestDto = ArmorFactory.updateArmorRequestDtoWithoutRequiredAgility();

        mockMvc.perform(put(buildPutArmorByIdUrl(armor.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        armor = armorRepository.findById(armor.getId()).get();

        assertThat(armor.getDefence()).isNotEqualTo(requestDto.getDefence());
        assertThat(armor.getArmorType().name()).isNotEqualTo(requestDto.getArmorType());
        assertThat(armor.getName()).isNotEqualTo(requestDto.getName());
        assertThat(armor.getDescription()).isNotEqualTo(requestDto.getDescription());
        assertThat(armor.getRarity().name()).isNotEqualTo(requestDto.getRarity());
        assertThat(armor.getRequiredAgility()).isNotEqualTo(requestDto.getRequiredAgility());
        assertThat(armor.getRequiredIntelligence()).isNotEqualTo(requestDto.getRequiredIntelligence());
        assertThat(armor.getRequiredLevel()).isNotEqualTo(requestDto.getRequiredLevel());
        assertThat(armor.getRequiredStrength()).isNotEqualTo(requestDto.getRequiredStrength());
    }

    @Test
    void updateArmor_shouldRespondWithBadRequestStatus_whenRequiredIntelligenceIsNull() throws Exception {
        Armor armor = armorRepository.save(ArmorFactory.armorInstance());
        ArmorRequestDto requestDto = ArmorFactory.updateArmorRequestDtoWithoutRequiredIntelligence();

        mockMvc.perform(put(buildPutArmorByIdUrl(armor.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        armor = armorRepository.findById(armor.getId()).get();

        assertThat(armor.getDefence()).isNotEqualTo(requestDto.getDefence());
        assertThat(armor.getArmorType().name()).isNotEqualTo(requestDto.getArmorType());
        assertThat(armor.getName()).isNotEqualTo(requestDto.getName());
        assertThat(armor.getDescription()).isNotEqualTo(requestDto.getDescription());
        assertThat(armor.getRarity().name()).isNotEqualTo(requestDto.getRarity());
        assertThat(armor.getRequiredAgility()).isNotEqualTo(requestDto.getRequiredAgility());
        assertThat(armor.getRequiredIntelligence()).isNotEqualTo(requestDto.getRequiredIntelligence());
        assertThat(armor.getRequiredLevel()).isNotEqualTo(requestDto.getRequiredLevel());
        assertThat(armor.getRequiredStrength()).isNotEqualTo(requestDto.getRequiredStrength());
    }

    @Test
    void updateArmor_shouldRespondWithBadRequestStatus_whenRequiredLevelIsNull() throws Exception {
        Armor armor = armorRepository.save(ArmorFactory.armorInstance());
        ArmorRequestDto requestDto = ArmorFactory.updateArmorRequestDtoWithoutRequiredLevel();

        mockMvc.perform(put(buildPutArmorByIdUrl(armor.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        armor = armorRepository.findById(armor.getId()).get();

        assertThat(armor.getDefence()).isNotEqualTo(requestDto.getDefence());
        assertThat(armor.getArmorType().name()).isNotEqualTo(requestDto.getArmorType());
        assertThat(armor.getName()).isNotEqualTo(requestDto.getName());
        assertThat(armor.getDescription()).isNotEqualTo(requestDto.getDescription());
        assertThat(armor.getRarity().name()).isNotEqualTo(requestDto.getRarity());
        assertThat(armor.getRequiredAgility()).isNotEqualTo(requestDto.getRequiredAgility());
        assertThat(armor.getRequiredIntelligence()).isNotEqualTo(requestDto.getRequiredIntelligence());
        assertThat(armor.getRequiredLevel()).isNotEqualTo(requestDto.getRequiredLevel());
        assertThat(armor.getRequiredStrength()).isNotEqualTo(requestDto.getRequiredStrength());
    }

    @Test
    void updateArmor_shouldRespondWithBadRequestStatus_whenRequiredStrengthIsNull() throws Exception {
        Armor armor = armorRepository.save(ArmorFactory.armorInstance());
        ArmorRequestDto requestDto = ArmorFactory.updateArmorRequestDtoWithoutRequiredStrength();

        mockMvc.perform(put(buildPutArmorByIdUrl(armor.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        armor = armorRepository.findById(armor.getId()).get();

        assertThat(armor.getDefence()).isNotEqualTo(requestDto.getDefence());
        assertThat(armor.getArmorType().name()).isNotEqualTo(requestDto.getArmorType());
        assertThat(armor.getName()).isNotEqualTo(requestDto.getName());
        assertThat(armor.getDescription()).isNotEqualTo(requestDto.getDescription());
        assertThat(armor.getRarity().name()).isNotEqualTo(requestDto.getRarity());
        assertThat(armor.getRequiredAgility()).isNotEqualTo(requestDto.getRequiredAgility());
        assertThat(armor.getRequiredIntelligence()).isNotEqualTo(requestDto.getRequiredIntelligence());
        assertThat(armor.getRequiredLevel()).isNotEqualTo(requestDto.getRequiredLevel());
        assertThat(armor.getRequiredStrength()).isNotEqualTo(requestDto.getRequiredStrength());
    }

    @Test
    void updateArmor_shouldRespondWithBadRequestStatus_whenBonusIdsIsNull() throws Exception {
        Armor armor = armorRepository.save(ArmorFactory.armorInstance());
        ArmorRequestDto requestDto = ArmorFactory.updateArmorRequestDtoWithoutBonusIds();

        mockMvc.perform(put(buildPutArmorByIdUrl(armor.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        armor = armorRepository.findById(armor.getId()).get();

        assertThat(armor.getDefence()).isNotEqualTo(requestDto.getDefence());
        assertThat(armor.getArmorType().name()).isNotEqualTo(requestDto.getArmorType());
        assertThat(armor.getName()).isNotEqualTo(requestDto.getName());
        assertThat(armor.getDescription()).isNotEqualTo(requestDto.getDescription());
        assertThat(armor.getRarity().name()).isNotEqualTo(requestDto.getRarity());
        assertThat(armor.getRequiredAgility()).isNotEqualTo(requestDto.getRequiredAgility());
        assertThat(armor.getRequiredIntelligence()).isNotEqualTo(requestDto.getRequiredIntelligence());
        assertThat(armor.getRequiredLevel()).isNotEqualTo(requestDto.getRequiredLevel());
        assertThat(armor.getRequiredStrength()).isNotEqualTo(requestDto.getRequiredStrength());
    }

    @Test
    void deleteArmor_shouldDeleteArmor_whenIdIsCorrect() throws Exception {
        Armor armor = armorRepository.save(ArmorFactory.armorInstance());

        mockMvc.perform(delete(buildDeleteArmorByIdUrl(armor.getId())))
            .andExpect(status().isOk());

        assertThat(armorRepository.findById(armor.getId())).isEmpty();
    }

    @Test
    void deleteArmor_shouldDeleteArmorRelatedToExistingBonus_whenIdIsCorrect() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        Armor armor = armorRepository.save(ArmorFactory.armorInstance(List.of(bonus)));

        mockMvc.perform(delete(buildDeleteArmorByIdUrl(armor.getId())))
            .andExpect(status().isOk());

        assertThat(armorRepository.findById(armor.getId())).isEmpty();
    }
}
