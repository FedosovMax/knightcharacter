package com.knightcharacter.app.gateway.privatedb.mapper;

import com.knightcharacter.app.domain.BonusVO;
import com.knightcharacter.app.gateway.privatedb.representation.Bonus;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BonusMapper {

    Bonus toBonus(BonusVO bonusVO);

    BonusVO toBonusVO(Bonus bonus);
}
