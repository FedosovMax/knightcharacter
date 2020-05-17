package com.knightcharacter.app.factories;

import com.knightcharacter.app.gateway.privatedb.representation.Bonus;
import com.knightcharacter.app.gateway.privatedb.representation.Weapon;
import com.knightcharacter.app.gateway.privatedb.representation.enums.Rarity;
import com.knightcharacter.app.gateway.privatedb.representation.enums.WeaponType;
import com.knightcharacter.app.rest.request.WeaponRequestDto;

import java.util.List;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WeaponFactory {

    private static final Integer WEAPON_DAMAGE = 91;
    private static final WeaponType WEAPON_TYPE = WeaponType.AXE;
    private static final Rarity WEAPON_RARITY = Rarity.LEGEND;
    private static final Integer WEAPON_REQUIRED_AGILITY = 92;
    private static final Integer WEAPON_REQUIRED_INTELLIGENCE = 93;
    private static final Integer WEAPON_REQUIRED_LEVEL = 94;
    private static final Integer WEAPON_REQUIRED_STRENGTH = 95;
    private static final String WEAPON_INVALID_TYPE = "SUPER WEAPON";
    private static final String WEAPON_INVALID_RARITY = "MYSTIC ORANGE";

    public static WeaponRequestDto createWeaponRequestDto(List<String> bonusIds) {
        return WeaponRequestDto.builder()
            .damage(WEAPON_DAMAGE)
            .bonusIds(bonusIds)
            .weaponType(WEAPON_TYPE.name())
            .rarity(WEAPON_RARITY.name())
            .requiredAgility(WEAPON_REQUIRED_AGILITY)
            .requiredIntelligence(WEAPON_REQUIRED_INTELLIGENCE)
            .requiredLevel(WEAPON_REQUIRED_LEVEL)
            .requiredStrength(WEAPON_REQUIRED_STRENGTH)
            .build();
    }

    public static WeaponRequestDto createWeaponRequestDtoWithoutDamage(List<String> bonusIds) {
        return WeaponRequestDto.builder()
            .bonusIds(bonusIds)
            .weaponType(WEAPON_TYPE.name())
            .rarity(WEAPON_RARITY.name())
            .requiredAgility(WEAPON_REQUIRED_AGILITY)
            .requiredIntelligence(WEAPON_REQUIRED_INTELLIGENCE)
            .requiredLevel(WEAPON_REQUIRED_LEVEL)
            .requiredStrength(WEAPON_REQUIRED_STRENGTH)
            .build();
    }

    public static WeaponRequestDto createWeaponRequestDtoWithoutWeaponType(List<String> bonusIds) {
        return WeaponRequestDto.builder()
            .damage(WEAPON_DAMAGE)
            .bonusIds(bonusIds)
            .rarity(WEAPON_RARITY.name())
            .requiredAgility(WEAPON_REQUIRED_AGILITY)
            .requiredIntelligence(WEAPON_REQUIRED_INTELLIGENCE)
            .requiredLevel(WEAPON_REQUIRED_LEVEL)
            .requiredStrength(WEAPON_REQUIRED_STRENGTH)
            .build();
    }

    public static WeaponRequestDto createWeaponRequestDtoWithInvalidWeaponType(List<String> bonusIds) {
        return WeaponRequestDto.builder()
            .damage(WEAPON_DAMAGE)
            .bonusIds(bonusIds)
            .weaponType(WEAPON_INVALID_TYPE)
            .rarity(WEAPON_RARITY.name())
            .requiredAgility(WEAPON_REQUIRED_AGILITY)
            .requiredIntelligence(WEAPON_REQUIRED_INTELLIGENCE)
            .requiredLevel(WEAPON_REQUIRED_LEVEL)
            .requiredStrength(WEAPON_REQUIRED_STRENGTH)
            .build();
    }

    public static WeaponRequestDto createWeaponRequestDtoWithoutRequiredLevel(List<String> bonusIds) {
        return WeaponRequestDto.builder()
            .damage(WEAPON_DAMAGE)
            .bonusIds(bonusIds)
            .weaponType(WEAPON_TYPE.name())
            .rarity(WEAPON_RARITY.name())
            .requiredAgility(WEAPON_REQUIRED_AGILITY)
            .requiredIntelligence(WEAPON_REQUIRED_INTELLIGENCE)
            .requiredStrength(WEAPON_REQUIRED_STRENGTH)
            .build();
    }

    public static WeaponRequestDto createWeaponRequestDtoWithoutRequiredAgility(List<String> bonusIds) {
        return WeaponRequestDto.builder()
            .damage(WEAPON_DAMAGE)
            .bonusIds(bonusIds)
            .weaponType(WEAPON_TYPE.name())
            .rarity(WEAPON_RARITY.name())
            .requiredIntelligence(WEAPON_REQUIRED_INTELLIGENCE)
            .requiredLevel(WEAPON_REQUIRED_LEVEL)
            .requiredStrength(WEAPON_REQUIRED_STRENGTH)
            .build();
    }

    public static WeaponRequestDto createWeaponRequestDtoWithoutRequiredIntelligence(List<String> bonusIds) {
        return WeaponRequestDto.builder()
            .damage(WEAPON_DAMAGE)
            .bonusIds(bonusIds)
            .weaponType(WEAPON_TYPE.name())
            .rarity(WEAPON_RARITY.name())
            .requiredAgility(WEAPON_REQUIRED_AGILITY)
            .requiredLevel(WEAPON_REQUIRED_LEVEL)
            .requiredStrength(WEAPON_REQUIRED_STRENGTH)
            .build();
    }

    public static WeaponRequestDto createWeaponRequestDtoWithoutRequiredStrength(List<String> bonusIds) {
        return WeaponRequestDto.builder()
            .damage(WEAPON_DAMAGE)
            .bonusIds(bonusIds)
            .weaponType(WEAPON_TYPE.name())
            .rarity(WEAPON_RARITY.name())
            .requiredAgility(WEAPON_REQUIRED_AGILITY)
            .requiredIntelligence(WEAPON_REQUIRED_INTELLIGENCE)
            .requiredLevel(WEAPON_REQUIRED_LEVEL)
            .build();
    }

    public static WeaponRequestDto createWeaponRequestDtoWithoutRarity(List<String> bonusIds) {
        return WeaponRequestDto.builder()
            .damage(WEAPON_DAMAGE)
            .bonusIds(bonusIds)
            .weaponType(WEAPON_TYPE.name())
            .requiredAgility(WEAPON_REQUIRED_AGILITY)
            .requiredIntelligence(WEAPON_REQUIRED_INTELLIGENCE)
            .requiredLevel(WEAPON_REQUIRED_LEVEL)
            .requiredStrength(WEAPON_REQUIRED_STRENGTH)
            .build();
    }

    public static WeaponRequestDto createWeaponRequestDtoWithInvalidRarity(List<String> bonusIds) {
        return WeaponRequestDto.builder()
            .damage(WEAPON_DAMAGE)
            .bonusIds(bonusIds)
            .weaponType(WEAPON_TYPE.name())
            .rarity(WEAPON_INVALID_RARITY)
            .requiredAgility(WEAPON_REQUIRED_AGILITY)
            .requiredIntelligence(WEAPON_REQUIRED_INTELLIGENCE)
            .requiredLevel(WEAPON_REQUIRED_LEVEL)
            .requiredStrength(WEAPON_REQUIRED_STRENGTH)
            .build();
    }

    public static WeaponRequestDto createWeaponRequestDtoWithoutBonusIds() {
        return WeaponRequestDto.builder()
            .damage(WEAPON_DAMAGE)
            .weaponType(WEAPON_TYPE.name())
            .rarity(WEAPON_RARITY.name())
            .requiredAgility(WEAPON_REQUIRED_AGILITY)
            .requiredIntelligence(WEAPON_REQUIRED_INTELLIGENCE)
            .requiredLevel(WEAPON_REQUIRED_LEVEL)
            .requiredStrength(WEAPON_REQUIRED_STRENGTH)
            .build();
    }

    public static Weapon weaponInstance(List<Bonus> bonuses) {
        return Weapon.builder()
            .damage(WEAPON_DAMAGE)
            .bonuses(bonuses)
            .weaponType(WEAPON_TYPE)
            .rarity(WEAPON_RARITY)
            .requiredAgility(WEAPON_REQUIRED_AGILITY)
            .requiredIntelligence(WEAPON_REQUIRED_INTELLIGENCE)
            .requiredLevel(WEAPON_REQUIRED_LEVEL)
            .requiredStrength(WEAPON_REQUIRED_STRENGTH)
            .build();
    }
}
