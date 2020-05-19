package com.knightcharacter.app.rest.mappers;

import com.knightcharacter.app.domain.ArmorVO;
import com.knightcharacter.app.rest.request.ArmorRequestDto;
import com.knightcharacter.app.rest.response.ArmorResponseDto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = BonusRestMapper.class)
public interface ArmorRestMapper {

    @Mapping(target = "bonuses", source = "bonusIds")
    ArmorVO toArmorVO(ArmorRequestDto requestDto);

    ArmorResponseDto toArmorResponseDto(ArmorVO armorVO);
}
