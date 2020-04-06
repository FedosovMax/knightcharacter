package com.knightexperience.app.rest.mappers;

import com.knightexperience.app.domain.CharacterVO;
import com.knightexperience.app.rest.request.CharacterRequestDto;
import com.knightexperience.app.rest.response.CharacterResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CharacterRestMapper {

    @Mapping(target = "name", source = "characterName")
    CharacterVO toCharacterVO(CharacterRequestDto requestDto);

    @Mapping(target = "characterName", source = "name")
    CharacterResponseDto toCharacterResponseDto(CharacterVO characterVO);
}
