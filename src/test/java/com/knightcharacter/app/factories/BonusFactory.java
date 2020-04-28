package com.knightcharacter.app.factories;

import com.knightcharacter.app.gateway.privatedb.representation.enums.Rarity;
import com.knightcharacter.app.rest.request.BonusRequestDto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BonusFactory {

    private static final String BONUS_NAME = "Some super bonus";
    private static final Rarity BONUS_RARITY = Rarity.LEGEND;
    private static final Integer BONUS_DAMAGE_BOOST = 96;
    private static final Integer BONUS_CRIT_CHANCE_BOOST = 97;
    private static final Integer BONUS_CRIT_DAMAGE_BOOST = 98;
    private static final Integer BONUS_SKILL_BOOST = 99;

    public static BonusRequestDto bonusRequestDtoInstance() {
        return BonusRequestDto.builder()
            .name(BONUS_NAME)
            .rarity(BONUS_RARITY.name())
            .damageBoost(BONUS_DAMAGE_BOOST)
            .critChanceBoost(BONUS_CRIT_CHANCE_BOOST)
            .critDamageBoost(BONUS_CRIT_DAMAGE_BOOST)
            .skillBoost(BONUS_SKILL_BOOST)
            .build();
    }

    public static BonusRequestDto bonusRequestDtoWithoutNameInstance() {
        return BonusRequestDto.builder()
            .rarity(BONUS_RARITY.name())
            .damageBoost(BONUS_DAMAGE_BOOST)
            .critChanceBoost(BONUS_CRIT_CHANCE_BOOST)
            .critDamageBoost(BONUS_CRIT_DAMAGE_BOOST)
            .skillBoost(BONUS_SKILL_BOOST)
            .build();
    }

    public static BonusRequestDto bonusRequestDtoWithNameConsistingOfSpacesInstance() {
        return BonusRequestDto.builder()
            .name("    ")
            .rarity(BONUS_RARITY.name())
            .damageBoost(BONUS_DAMAGE_BOOST)
            .critChanceBoost(BONUS_CRIT_CHANCE_BOOST)
            .critDamageBoost(BONUS_CRIT_DAMAGE_BOOST)
            .skillBoost(BONUS_SKILL_BOOST)
            .build();
    }

    public static BonusRequestDto bonusRequestDtoWithoutRarityInstance() {
        return BonusRequestDto.builder()
            .name(BONUS_NAME)
            .damageBoost(BONUS_DAMAGE_BOOST)
            .critChanceBoost(BONUS_CRIT_CHANCE_BOOST)
            .critDamageBoost(BONUS_CRIT_DAMAGE_BOOST)
            .skillBoost(BONUS_SKILL_BOOST)
            .build();
    }

    public static BonusRequestDto bonusRequestDtoWithoutDamageBoostInstance() {
        return BonusRequestDto.builder()
            .name(BONUS_NAME)
            .rarity(BONUS_RARITY.name())
            .critChanceBoost(BONUS_CRIT_CHANCE_BOOST)
            .critDamageBoost(BONUS_CRIT_DAMAGE_BOOST)
            .skillBoost(BONUS_SKILL_BOOST)
            .build();
    }

    public static BonusRequestDto bonusRequestDtoWithoutCritChanceBoostInstance() {
        return BonusRequestDto.builder()
            .name(BONUS_NAME)
            .rarity(BONUS_RARITY.name())
            .damageBoost(BONUS_DAMAGE_BOOST)
            .critDamageBoost(BONUS_CRIT_DAMAGE_BOOST)
            .skillBoost(BONUS_SKILL_BOOST)
            .build();
    }

    public static BonusRequestDto bonusRequestDtoWithoutCritDamageBoostInstance() {
        return BonusRequestDto.builder()
            .name(BONUS_NAME)
            .rarity(BONUS_RARITY.name())
            .damageBoost(BONUS_DAMAGE_BOOST)
            .critChanceBoost(BONUS_CRIT_CHANCE_BOOST)
            .skillBoost(BONUS_SKILL_BOOST)
            .build();
    }

    public static BonusRequestDto bonusRequestDtoWithoutSkillBoostInstance() {
        return BonusRequestDto.builder()
            .name(BONUS_NAME)
            .rarity(BONUS_RARITY.name())
            .damageBoost(BONUS_DAMAGE_BOOST)
            .critChanceBoost(BONUS_CRIT_CHANCE_BOOST)
            .critDamageBoost(BONUS_CRIT_DAMAGE_BOOST)
            .build();
    }
}
