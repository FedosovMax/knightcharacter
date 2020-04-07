package com.knightcharacter.app.rest.mappers;

import com.knightcharacter.app.domain.ExperienceVO;
import com.knightcharacter.app.rest.request.ExperienceRequestDto;
import com.knightcharacter.app.rest.response.ExperienceResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExperienceRestMapper {

    ExperienceVO toExperienceVO(ExperienceRequestDto experienceRequestDto);

    ExperienceResponseDto toExperienceResponseDto(ExperienceVO experienceVO);
}
