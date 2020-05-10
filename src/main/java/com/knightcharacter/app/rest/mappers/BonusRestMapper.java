package com.knightcharacter.app.rest.mappers;

import com.knightcharacter.app.domain.BonusVO;
import com.knightcharacter.app.rest.request.BonusRequestDto;
import com.knightcharacter.app.rest.response.BonusResponseDto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BonusRestMapper {

    BonusVO toBonusVO(BonusRequestDto requestDto);

    @Mapping(target = "id", source = "bonusId")
    BonusVO toBonusVO(String bonusId);

    BonusResponseDto toBonusResponseDto(BonusVO bonusVO);
}
