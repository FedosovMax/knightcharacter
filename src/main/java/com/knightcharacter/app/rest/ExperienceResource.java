package com.knightcharacter.app.rest;

import static com.knightcharacter.app.Constants.BASE_EXPERIENCE_URL;

import com.knightcharacter.app.domain.ExperienceVO;
import com.knightcharacter.app.rest.mappers.ExperienceRestMapper;
import com.knightcharacter.app.rest.request.ExperienceRequestDto;
import com.knightcharacter.app.rest.response.ExperienceResponseDto;
import com.knightcharacter.app.service.ExperienceService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(BASE_EXPERIENCE_URL)
@Slf4j
public class ExperienceResource {

    private final ExperienceService experienceService;
    private final ExperienceRestMapper experienceRestMapper;

    @PostMapping
    public ResponseEntity<ExperienceResponseDto> calculateTodo(@Valid @RequestBody ExperienceRequestDto experienceRequestDto) {
        ExperienceVO experienceVO = experienceRestMapper.toExperienceVO(experienceRequestDto);
        experienceVO = experienceService.calculateExperience(experienceVO);
        ExperienceResponseDto experienceResponseDto = experienceRestMapper.toExperienceResponseDto(experienceVO);
        return ResponseEntity.ok().body(experienceResponseDto);
    }
}
