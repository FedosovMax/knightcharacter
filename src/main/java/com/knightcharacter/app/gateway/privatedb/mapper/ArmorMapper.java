package com.knightcharacter.app.gateway.privatedb.mapper;

import com.knightcharacter.app.domain.ArmorVO;
import com.knightcharacter.app.gateway.privatedb.representation.Armor;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = BonusMapper.class)
public interface ArmorMapper {

    Armor toArmor(ArmorVO armorVO);

    ArmorVO toArmorVO(Armor armor);
}
