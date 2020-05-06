package com.knightcharacter.app.rest.mappers;

import com.knightcharacter.app.domain.SkillVO;
import com.knightcharacter.app.rest.request.SkillRequestDto;
import com.knightcharacter.app.rest.response.SkillResponseDto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = BonusRestMapper.class)
public interface SkillRestMapper {

    @Mapping(target = "bonuses", source = "bonusIds")
    SkillVO toSkillVO(SkillRequestDto requestDto);

    SkillResponseDto toSkillResponseDto(SkillVO skillVO);
}
