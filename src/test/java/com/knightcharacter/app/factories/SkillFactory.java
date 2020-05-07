package com.knightcharacter.app.factories;

import com.knightcharacter.app.rest.request.SkillRequestDto;

import java.util.List;

public class SkillFactory {

    private static final String SKILL_NAME = "Some super skill";
    private static final String SKILL_DESCRIPTION = "Some super skill description";

    public static SkillRequestDto createSkillRequestDto(List<String> bonusIds) {
        return SkillRequestDto.builder()
            .name(SKILL_NAME)
            .description(SKILL_DESCRIPTION)
            .bonusIds(bonusIds)
            .build();
    }

    public static SkillRequestDto createSkillRequestDtoWithoutName(List<String> bonusIds) {
        return SkillRequestDto.builder()
            .description(SKILL_DESCRIPTION)
            .bonusIds(bonusIds)
            .build();
    }

    public static SkillRequestDto createSkillRequestDtoWithNameConsistingOfSpaces(List<String> bonusIds) {
        return SkillRequestDto.builder()
            .name("    ")
            .description(SKILL_DESCRIPTION)
            .bonusIds(bonusIds)
            .build();
    }

    public static SkillRequestDto createSkillRequestDtoWithoutDescription(List<String> bonusIds) {
        return SkillRequestDto.builder()
            .name(SKILL_NAME)
            .bonusIds(bonusIds)
            .build();
    }

    public static SkillRequestDto createSkillRequestDtoWithDescriptionConsistingOfSpaces(List<String> bonusIds) {
        return SkillRequestDto.builder()
            .name(SKILL_NAME)
            .description("    ")
            .bonusIds(bonusIds)
            .build();
    }

    public static SkillRequestDto createSkillRequestDtoWithoutBonusIds() {
        return SkillRequestDto.builder()
            .name(SKILL_NAME)
            .description(SKILL_DESCRIPTION)
            .build();
    }
}
