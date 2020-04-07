package com.knightcharacter.app.gateway.privatedb.mapper;

import com.knightcharacter.app.domain.CharacterVO;
import com.knightcharacter.app.gateway.privatedb.representation.Character;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CharacterMapper {

    Character toCharacter(CharacterVO characterVO);

    CharacterVO toCharacterVO(Character character);
}
