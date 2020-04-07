package com.knightexperience.app.factories;

import com.knightexperience.app.gateway.privatedb.representation.Character;
import com.knightexperience.app.rest.request.CharacterRequestDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CharacterFactory {

    public static final String CHARACTER_NAME = "Write integration tests";

    public static CharacterRequestDto characterRequestDtoInstance() {
        return CharacterRequestDto.builder().characterName(CHARACTER_NAME).build();
    }

    public static CharacterRequestDto characterRequestDtoWithNullNameInstance() {
        return CharacterRequestDto.builder().characterName(null).build();
    }

    public static CharacterRequestDto characterRequestDtoWithNameOfSpacesInstance() {
        return CharacterRequestDto.builder().characterName("   ").build();
    }

    public static Character createCharacterInstance() {
        return Character.builder().name(CHARACTER_NAME).build();
    }
}
