package com.knightcharacter.app;


import static com.knightcharacter.app.Constants.BASE_CHARACTER;

public class TestConstants {

    public static final String JSON_ROOT = "$.";
    public static final String PARAMETER_ID = "id";
    public static final String PARAMETER_LENGTH = "length()";
    public static final String PARAMETER_CHARACTER_NAME = "characterName";
    public static final String PARAMETER_EXPERIENCE = "experience";
    public static final String PARAMETER_TODOID = "todoId";

    public static final Integer HARD_SCARRY_EXPERIENCE_AMOUNT = 13;

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
}
