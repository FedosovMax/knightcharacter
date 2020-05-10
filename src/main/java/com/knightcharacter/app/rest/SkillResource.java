package com.knightcharacter.app.rest;

import static com.knightcharacter.app.Constants.API_BASE_SKILLS;

import com.knightcharacter.app.domain.SkillVO;
import com.knightcharacter.app.rest.mappers.SkillRestMapper;
import com.knightcharacter.app.rest.request.SkillRequestDto;
import com.knightcharacter.app.rest.response.SkillResponseDto;
import com.knightcharacter.app.service.SkillService;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(API_BASE_SKILLS)
@Slf4j
public class SkillResource {

    private final SkillService skillService;
    private final SkillRestMapper skillRestMapper;

    @PostMapping
    public ResponseEntity<SkillResponseDto> addSkill(@Valid @RequestBody SkillRequestDto requestDto) {
        log.info("Request to add skill : {}", requestDto);
        SkillVO skillVO = skillRestMapper.toSkillVO(requestDto);
        SkillVO savedSkillVO = skillService.save(skillVO);
        return ResponseEntity.status(HttpStatus.CREATED).body(skillRestMapper.toSkillResponseDto(savedSkillVO));
    }

    @GetMapping
    public ResponseEntity<List<SkillResponseDto>> getAllSkills() {
        log.info("Request to get all skills");
        return ResponseEntity.status(HttpStatus.FOUND)
            .body(skillService.findAll()
                .stream()
                .map(skillRestMapper::toSkillResponseDto)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{skillId}")
    public ResponseEntity<SkillResponseDto> getSkillById(@PathVariable String skillId) {
        log.info("Request to get skill by id : {}", skillId);
        SkillVO skillVO = skillService.findById(skillId);
        return ResponseEntity.status(HttpStatus.FOUND).body(skillRestMapper.toSkillResponseDto(skillVO));
    }

    @PutMapping("/{skillId}")
    public ResponseEntity<SkillResponseDto> updateSkill(@PathVariable String skillId,
        @Valid @RequestBody SkillRequestDto requestDto) {
        log.info("Request to update skill : {}", requestDto);
        SkillVO skillVO = skillRestMapper.toSkillVO(requestDto);
        SkillVO updatedSkillVO = skillService.updateSkill(skillId, skillVO);
        return ResponseEntity.ok().body(skillRestMapper.toSkillResponseDto(updatedSkillVO));
    }

    @DeleteMapping("/{skillId}")
    public ResponseEntity<Void> deleteSkill(@PathVariable String skillId) {
        log.info("Request to delete skill by id : {}", skillId);
        skillService.deleteById(skillId);
        return ResponseEntity.ok().build();
    }
}
