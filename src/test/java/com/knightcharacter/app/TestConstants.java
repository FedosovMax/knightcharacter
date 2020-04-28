package com.knightcharacter.app;

import static com.knightcharacter.app.Constants.BASE_CHARACTER;

public class TestConstants {

    public static final String JSON_ROOT = "$.";
    public static final String PARAMETER_ID = "id";
    public static final String PARAMETER_LENGTH = "length()";
    public static final String PARAMETER_NAME = "name";
    public static final String PARAMETER_RARITY = "rarity";
    public static final String PARAMETER_DAMAGE_BOOST = "damageBoost";
    public static final String PARAMETER_CRIT_CHANCE_BOOST = "critChanceBoost";
    public static final String PARAMETER_CRIT_DAMAGE_BOOST = "critDamageBoost";
    public static final String PARAMETER_SKILL_BOOST = "skillBoost";
    public static final String PARAMETER_CHARACTER_NAME = "characterName";
    public static final String PARAMETER_EXPERIENCE = "experience";
    public static final String PARAMETER_TODOID = "todoId";

    public static final Integer HARD_SCARY_EXPERIENCE_AMOUNT = 13;

    public static String buildGetCharacterByIdUrl(String characterId) {
        return BASE_CHARACTER + "/" + characterId;
    }

    public static String buildPutCharacterByIdUrl(String characterId) {
        return BASE_CHARACTER + "/" + characterId;
    }

    public static String buildDeleteCharacterByIdUrl(String characterId) {
        return BASE_CHARACTER + "/" + characterId;
    }

    public static String buildJsonPathToId() {
        return JSON_ROOT + PARAMETER_ID;
    }

    public static String buildJsonPathToLength() {
        return JSON_ROOT + PARAMETER_LENGTH;
    }

    public static String buildJsonPathToCharacterName() {
        return JSON_ROOT + PARAMETER_CHARACTER_NAME;
    }

    public static String buildJsonPathToName() {
        return JSON_ROOT + PARAMETER_NAME;
    }

    public static String buildJsonPathToRarity() {
        return JSON_ROOT + PARAMETER_RARITY;
    }

    public static String buildJsonPathToDamageBoost() {
        return JSON_ROOT + PARAMETER_DAMAGE_BOOST;
    }

    public static String buildJsonPathToCritChanceBoost() {
        return JSON_ROOT + PARAMETER_CRIT_CHANCE_BOOST;
    }

    public static String buildJsonPathToCritDamageBoost() {
        return JSON_ROOT + PARAMETER_CRIT_DAMAGE_BOOST;
    }

    public static String buildJsonPathToSkillBoost() {
        return JSON_ROOT + PARAMETER_SKILL_BOOST;
    }
}
