package com.knightexperience.app.gateway.privatedb.mapper;

import com.knightexperience.app.domain.CharacterVO;
import com.knightexperience.app.gateway.privatedb.representation.Character;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CharacterMapper {

    Character toCharacter(CharacterVO characterVO);

    CharacterVO toCharacterVO(Character character);
}
