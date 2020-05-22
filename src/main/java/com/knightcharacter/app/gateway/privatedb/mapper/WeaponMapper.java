package com.knightcharacter.app.gateway.privatedb.mapper;

import com.knightcharacter.app.domain.WeaponVO;
import com.knightcharacter.app.gateway.privatedb.representation.Weapon;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = BonusMapper.class)
public interface WeaponMapper {

    Weapon toWeapon(WeaponVO weaponVO);

    WeaponVO toWeaponVO(Weapon weapon);
}
