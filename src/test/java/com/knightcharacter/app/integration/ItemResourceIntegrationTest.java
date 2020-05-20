package com.knightcharacter.app.integration;

import static com.knightcharacter.app.Constants.API_BASE_ITEMS;
import static com.knightcharacter.app.TestConstants.buildJsonPathToArmorTypeInListByIndex;
import static com.knightcharacter.app.TestConstants.buildJsonPathToBonusesInListByIndex;
import static com.knightcharacter.app.TestConstants.buildJsonPathToCritChanceBoostInBonusListNestedInSkillListByIndexes;
import static com.knightcharacter.app.TestConstants.buildJsonPathToCritDamageBoostInBonusListNestedInSkillListByIndexes;
import static com.knightcharacter.app.TestConstants.buildJsonPathToDamageBoostInBonusListNestedInSkillListByIndexes;
import static com.knightcharacter.app.TestConstants.buildJsonPathToDamageInListByIndex;
import static com.knightcharacter.app.TestConstants.buildJsonPathToDefenceInListByIndex;
import static com.knightcharacter.app.TestConstants.buildJsonPathToDescriptionInListByIndex;
import static com.knightcharacter.app.TestConstants.buildJsonPathToIdInBonusListNestedInSkillListByIndexes;
import static com.knightcharacter.app.TestConstants.buildJsonPathToIdInListByIndex;
import static com.knightcharacter.app.TestConstants.buildJsonPathToLength;
import static com.knightcharacter.app.TestConstants.buildJsonPathToNameInBonusListNestedInSkillListByIndexes;
import static com.knightcharacter.app.TestConstants.buildJsonPathToNameInListByIndex;
import static com.knightcharacter.app.TestConstants.buildJsonPathToRarityInBonusListNestedInSkillListByIndexes;
import static com.knightcharacter.app.TestConstants.buildJsonPathToRarityInListByIndex;
import static com.knightcharacter.app.TestConstants.buildJsonPathToRequiredAgilityInListByIndex;
import static com.knightcharacter.app.TestConstants.buildJsonPathToRequiredIntelligenceInListByIndex;
import static com.knightcharacter.app.TestConstants.buildJsonPathToRequiredLevelInListByIndex;
import static com.knightcharacter.app.TestConstants.buildJsonPathToRequiredStrengthInListByIndex;
import static com.knightcharacter.app.TestConstants.buildJsonPathToSkillBoostInBonusListNestedInSkillListByIndexes;
import static com.knightcharacter.app.TestConstants.buildJsonPathToWeaponTypeInListByIndex;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.knightcharacter.app.factories.ArmorFactory;
import com.knightcharacter.app.factories.BonusFactory;
import com.knightcharacter.app.factories.WeaponFactory;
import com.knightcharacter.app.gateway.privatedb.repository.ArmorRepository;
import com.knightcharacter.app.gateway.privatedb.repository.BonusRepository;
import com.knightcharacter.app.gateway.privatedb.repository.WeaponRepository;
import com.knightcharacter.app.gateway.privatedb.representation.Armor;
import com.knightcharacter.app.gateway.privatedb.representation.Bonus;
import com.knightcharacter.app.gateway.privatedb.representation.Weapon;

import java.util.List;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@FieldDefaults(level = AccessLevel.PRIVATE)
class ItemResourceIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ArmorRepository armorRepository;

    @Autowired
    WeaponRepository weaponRepository;

    @Autowired
    BonusRepository bonusRepository;

    @AfterEach
    void tearDown() {
        armorRepository.deleteAll();
        weaponRepository.deleteAll();
        bonusRepository.deleteAll();
    }

    @Test
    void getAllItems_shouldReturnAllItemResponseInheritorDtos() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        Armor armor = armorRepository.save(ArmorFactory.armorInstance(List.of(bonus)));
        Weapon weapon = weaponRepository.save(WeaponFactory.weaponInstance(List.of(bonus)));

        mockMvc.perform(get(API_BASE_ITEMS))
            .andExpect(status().isFound())
            .andExpect(jsonPath(buildJsonPathToLength()).value(2))
            .andExpect(jsonPath(buildJsonPathToIdInListByIndex(0)).value(armor.getId()))
            .andExpect(jsonPath(buildJsonPathToDefenceInListByIndex(0)).value(armor.getDefence()))
            .andExpect(jsonPath(buildJsonPathToRarityInListByIndex(0)).value(armor.getRarity().name()))
            .andExpect(jsonPath(buildJsonPathToArmorTypeInListByIndex(0)).value(armor.getArmorType().name()))
            .andExpect(jsonPath(buildJsonPathToNameInListByIndex(0)).value(armor.getName()))
            .andExpect(jsonPath(buildJsonPathToDescriptionInListByIndex(0)).value(armor.getDescription()))
            .andExpect(jsonPath(buildJsonPathToRequiredAgilityInListByIndex(0)).value(armor.getRequiredAgility()))
            .andExpect(jsonPath(buildJsonPathToRequiredIntelligenceInListByIndex(0))
                .value(armor.getRequiredIntelligence()))
            .andExpect(jsonPath(buildJsonPathToRequiredLevelInListByIndex(0)).value(armor.getRequiredLevel()))
            .andExpect(jsonPath(buildJsonPathToRequiredStrengthInListByIndex(0)).value(armor.getRequiredStrength()))
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
            .andExpect(jsonPath(buildJsonPathToIdInListByIndex(1)).value(weapon.getId()))
            .andExpect(jsonPath(buildJsonPathToDamageInListByIndex(1)).value(weapon.getDamage()))
            .andExpect(jsonPath(buildJsonPathToRarityInListByIndex(1)).value(weapon.getRarity().name()))
            .andExpect(jsonPath(buildJsonPathToWeaponTypeInListByIndex(1)).value(weapon.getWeaponType().name()))
            .andExpect(jsonPath(buildJsonPathToNameInListByIndex(1)).value(weapon.getName()))
            .andExpect(jsonPath(buildJsonPathToDescriptionInListByIndex(1)).value(weapon.getDescription()))
            .andExpect(jsonPath(buildJsonPathToRequiredAgilityInListByIndex(1)).value(weapon.getRequiredAgility()))
            .andExpect(jsonPath(buildJsonPathToRequiredIntelligenceInListByIndex(1))
                .value(weapon.getRequiredIntelligence()))
            .andExpect(jsonPath(buildJsonPathToRequiredLevelInListByIndex(1)).value(weapon.getRequiredLevel()))
            .andExpect(jsonPath(buildJsonPathToRequiredStrengthInListByIndex(1)).value(weapon.getRequiredStrength()))
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
}
