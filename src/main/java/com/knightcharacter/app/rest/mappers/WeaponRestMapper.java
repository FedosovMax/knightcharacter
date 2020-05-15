package com.knightcharacter.app.rest.mappers;

import com.knightcharacter.app.domain.WeaponVO;
import com.knightcharacter.app.rest.request.WeaponRequestDto;
import com.knightcharacter.app.rest.response.WeaponResponseDto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WeaponRestMapper {

    @Mapping(target = "bonuses", source = "bonusIds")
    WeaponVO toWeaponVO(WeaponRequestDto requestDto);

    WeaponResponseDto toWeaponResponseDto(WeaponVO weaponVO);
}
