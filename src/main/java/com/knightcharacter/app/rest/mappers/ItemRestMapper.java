package com.knightcharacter.app.rest.mappers;

import com.knightcharacter.app.domain.ArmorVO;
import com.knightcharacter.app.domain.ItemVO;
import com.knightcharacter.app.domain.WeaponVO;
import com.knightcharacter.app.exception.UnsupportedTypeException;
import com.knightcharacter.app.rest.response.ItemResponseDto;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ItemRestMapper {

    private final ArmorRestMapper armorRestMapper;
    private final WeaponRestMapper weaponRestMapper;

    public ItemResponseDto toItemVO(ItemVO itemVO) {
        if (itemVO instanceof WeaponVO) {
            return weaponRestMapper.toWeaponResponseDto((WeaponVO) itemVO);
        } else if (itemVO instanceof ArmorVO) {
            return armorRestMapper.toArmorResponseDto((ArmorVO) itemVO);
        }
        throw new UnsupportedTypeException(String.format("Unsupported itemVO type : %s", itemVO.getClass()));
    }
}
