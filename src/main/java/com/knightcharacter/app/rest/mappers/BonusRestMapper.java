package com.knightcharacter.app.rest.mappers;

import com.knightcharacter.app.domain.BonusVO;
import com.knightcharacter.app.rest.request.BonusRequestDto;
import com.knightcharacter.app.rest.response.BonusResponseDto;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BonusRestMapper {

    BonusVO toBonusVO(BonusRequestDto requestDto);

    BonusResponseDto toBonusResponseDto(BonusVO bonusVO);
}
