package com.knightcharacter.app.integration;

import static com.knightcharacter.app.Constants.API_BASE_SKILLS;
import static com.knightcharacter.app.TestConstants.buildJsonPathToBonuses;
import static com.knightcharacter.app.TestConstants.buildJsonPathToCritChanceBoostInBonusListByIndex;
import static com.knightcharacter.app.TestConstants.buildJsonPathToCritDamageBoostInBonusListByIndex;
import static com.knightcharacter.app.TestConstants.buildJsonPathToDamageBoostInBonusListByIndex;
import static com.knightcharacter.app.TestConstants.buildJsonPathToDescription;
import static com.knightcharacter.app.TestConstants.buildJsonPathToId;
import static com.knightcharacter.app.TestConstants.buildJsonPathToIdInBonusListByIndex;
import static com.knightcharacter.app.TestConstants.buildJsonPathToName;
import static com.knightcharacter.app.TestConstants.buildJsonPathToNameInBonusListByIndex;
import static com.knightcharacter.app.TestConstants.buildJsonPathToRarityInBonusListByIndex;
import static com.knightcharacter.app.TestConstants.buildJsonPathToSkillBoostInBonusListByIndex;

import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.knightcharacter.app.factories.BonusFactory;
import com.knightcharacter.app.factories.SkillFactory;
import com.knightcharacter.app.gateway.privatedb.repository.BonusRepository;
import com.knightcharacter.app.gateway.privatedb.repository.SkillRepository;
import com.knightcharacter.app.gateway.privatedb.representation.Bonus;
import com.knightcharacter.app.gateway.privatedb.representation.Skill;
import com.knightcharacter.app.rest.request.SkillRequestDto;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
class SkillResourceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private BonusRepository bonusRepository;

    @BeforeEach
    void setUp() {
        bonusRepository.deleteAll();
        skillRepository.deleteAll();
    }

    @Test
    @Transactional
    void addSkill_shouldAddSkillAndReturnIt_whenRequestIsCorrect() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        SkillRequestDto requestDto = SkillFactory.createSkillRequestDto(List.of(bonus.getId()));

        mockMvc.perform(post(API_BASE_SKILLS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isCreated())
            .andExpect(jsonPath(buildJsonPathToId()).exists())
            .andExpect(jsonPath(buildJsonPathToName()).value(requestDto.getName()))
            .andExpect(jsonPath(buildJsonPathToDescription()).value(requestDto.getDescription()))
            .andExpect(jsonPath(buildJsonPathToBonuses()).exists())
            .andExpect(jsonPath(buildJsonPathToIdInBonusListByIndex(0)).value(bonus.getId()))
            .andExpect(jsonPath(buildJsonPathToNameInBonusListByIndex(0)).value(bonus.getName()))
            .andExpect(jsonPath(buildJsonPathToRarityInBonusListByIndex(0)).value(bonus.getRarity().toString()))
            .andExpect(jsonPath(buildJsonPathToDamageBoostInBonusListByIndex(0)).value(bonus.getDamageBoost()))
            .andExpect(jsonPath(buildJsonPathToCritChanceBoostInBonusListByIndex(0)).value(bonus.getCritChanceBoost()))
            .andExpect(jsonPath(buildJsonPathToCritDamageBoostInBonusListByIndex(0)).value(bonus.getCritDamageBoost()))
            .andExpect(jsonPath(buildJsonPathToSkillBoostInBonusListByIndex(0)).value(bonus.getSkillBoost()));

        Skill savedSkill = skillRepository.findAll().get(0);

        assertThat(savedSkill.getBonuses()).contains(bonus);
        assertThat(savedSkill.getName()).isEqualTo(requestDto.getName());
        assertThat(savedSkill.getDescription()).isEqualTo(requestDto.getDescription());
    }

    @Test
    void addSkill_shouldRespondWithBadRequestStatus_whenNameIsNull() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        SkillRequestDto requestDto = SkillFactory.createSkillRequestDtoWithoutName(List.of(bonus.getId()));

        mockMvc.perform(post(API_BASE_SKILLS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(skillRepository.count()).isEqualTo(0);
    }

    @Test
    void addSkill_shouldRespondWithBadRequestStatus_whenNameConsistsOfSpaces() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        SkillRequestDto requestDto = SkillFactory
            .createSkillRequestDtoWithNameConsistingOfSpaces(List.of(bonus.getId()));

        mockMvc.perform(post(API_BASE_SKILLS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(skillRepository.count()).isEqualTo(0);
    }

    @Test
    void addSkill_shouldRespondWithBadRequestStatus_whenDescriptionIsNull() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        SkillRequestDto requestDto = SkillFactory.createSkillRequestDtoWithoutDescription(List.of(bonus.getId()));

        mockMvc.perform(post(API_BASE_SKILLS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(skillRepository.count()).isEqualTo(0);
    }

    @Test
    void addSkill_shouldRespondWithBadRequestStatus_whenDescriptionConsistsOfSpaces() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        SkillRequestDto requestDto = SkillFactory
            .createSkillRequestDtoWithDescriptionConsistingOfSpaces(List.of(bonus.getId()));

        mockMvc.perform(post(API_BASE_SKILLS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(skillRepository.count()).isEqualTo(0);
    }

    @Test
    void addSkill_shouldRespondWithBadRequestStatus_whenBonusIdsIsNull() throws Exception {
        SkillRequestDto requestDto = SkillFactory.createSkillRequestDtoWithoutBonusIds();

        mockMvc.perform(post(API_BASE_SKILLS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(skillRepository.count()).isEqualTo(0);
    }

    @Test
    void getAllSkills() {
    }

    @Test
    void getSkillById() {
    }

    @Test
    void updateSkill() {
    }

    @Test
    void deleteSkill() {
    }
}
