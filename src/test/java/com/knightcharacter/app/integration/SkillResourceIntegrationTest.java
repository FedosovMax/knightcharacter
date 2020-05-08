package com.knightcharacter.app.integration;

import static com.knightcharacter.app.Constants.API_BASE_SKILLS;
import static com.knightcharacter.app.TestConstants.buildDeleteSkillByIdUrl;
import static com.knightcharacter.app.TestConstants.buildGetSkillByIdUrl;
import static com.knightcharacter.app.TestConstants.buildJsonPathToBonuses;
import static com.knightcharacter.app.TestConstants.buildJsonPathToBonusesInListByIndex;
import static com.knightcharacter.app.TestConstants.buildJsonPathToBonusesLength;
import static com.knightcharacter.app.TestConstants.buildJsonPathToCritChanceBoostInBonusListByIndex;
import static com.knightcharacter.app.TestConstants.buildJsonPathToCritChanceBoostInBonusListNestedInSkillListByIndexes;
import static com.knightcharacter.app.TestConstants.buildJsonPathToCritDamageBoostInBonusListByIndex;
import static com.knightcharacter.app.TestConstants.buildJsonPathToCritDamageBoostInBonusListNestedInSkillListByIndexes;
import static com.knightcharacter.app.TestConstants.buildJsonPathToDamageBoostInBonusListByIndex;
import static com.knightcharacter.app.TestConstants.buildJsonPathToDamageBoostInBonusListNestedInSkillListByIndexes;
import static com.knightcharacter.app.TestConstants.buildJsonPathToDescription;
import static com.knightcharacter.app.TestConstants.buildJsonPathToDescriptionInListByIndex;
import static com.knightcharacter.app.TestConstants.buildJsonPathToId;
import static com.knightcharacter.app.TestConstants.buildJsonPathToIdInBonusListByIndex;
import static com.knightcharacter.app.TestConstants.buildJsonPathToIdInBonusListNestedInSkillListByIndexes;
import static com.knightcharacter.app.TestConstants.buildJsonPathToIdInListByIndex;
import static com.knightcharacter.app.TestConstants.buildJsonPathToLength;
import static com.knightcharacter.app.TestConstants.buildJsonPathToName;
import static com.knightcharacter.app.TestConstants.buildJsonPathToNameInBonusListByIndex;
import static com.knightcharacter.app.TestConstants.buildJsonPathToNameInBonusListNestedInSkillListByIndexes;
import static com.knightcharacter.app.TestConstants.buildJsonPathToNameInListByIndex;
import static com.knightcharacter.app.TestConstants.buildJsonPathToRarityInBonusListByIndex;
import static com.knightcharacter.app.TestConstants.buildJsonPathToRarityInBonusListNestedInSkillListByIndexes;
import static com.knightcharacter.app.TestConstants.buildJsonPathToSkillBoostInBonusListByIndex;
import static com.knightcharacter.app.TestConstants.buildJsonPathToSkillBoostInBonusListNestedInSkillListByIndexes;
import static com.knightcharacter.app.TestConstants.buildPutSkillByIdUrl;

import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

import org.junit.jupiter.api.AfterEach;
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

    @AfterEach
    void tearDown() {
        skillRepository.deleteAll();
        bonusRepository.deleteAll();
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
    void getAllSkills_shouldReturnAllSkills() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        Skill firstSkill = skillRepository.save(SkillFactory.skillInstance(List.of(bonus)));
        Skill secondSkill = skillRepository.save(SkillFactory.skillInstance(List.of(bonus)));

        mockMvc.perform(get(API_BASE_SKILLS))
            .andExpect(status().isFound())
            .andExpect(jsonPath(buildJsonPathToLength()).value(2))
            .andExpect(jsonPath(buildJsonPathToIdInListByIndex(0)).value(firstSkill.getId()))
            .andExpect(jsonPath(buildJsonPathToNameInListByIndex(0)).value(firstSkill.getName()))
            .andExpect(jsonPath(buildJsonPathToDescriptionInListByIndex(0)).value(firstSkill.getDescription()))
            .andExpect(jsonPath(buildJsonPathToBonusesInListByIndex(0)).exists())
            .andExpect(jsonPath(buildJsonPathToIdInBonusListNestedInSkillListByIndexes(0, 0)).value(bonus.getId()))
            .andExpect(jsonPath(buildJsonPathToNameInBonusListNestedInSkillListByIndexes(0, 0)).value(bonus.getName()))
            .andExpect(jsonPath(buildJsonPathToRarityInBonusListNestedInSkillListByIndexes(0, 0))
                .value(bonus.getRarity().toString()))
            .andExpect(jsonPath(buildJsonPathToDamageBoostInBonusListNestedInSkillListByIndexes(0, 0))
                .value(bonus.getDamageBoost()))
            .andExpect(jsonPath(buildJsonPathToCritChanceBoostInBonusListNestedInSkillListByIndexes(0, 0))
                .value(bonus.getCritChanceBoost()))
            .andExpect(jsonPath(buildJsonPathToCritDamageBoostInBonusListNestedInSkillListByIndexes(0, 0))
                .value(bonus.getCritDamageBoost()))
            .andExpect(jsonPath(buildJsonPathToSkillBoostInBonusListNestedInSkillListByIndexes(0, 0))
                .value(bonus.getSkillBoost()))
            .andExpect(jsonPath(buildJsonPathToIdInListByIndex(1)).value(secondSkill.getId()))
            .andExpect(jsonPath(buildJsonPathToNameInListByIndex(1)).value(secondSkill.getName()))
            .andExpect(jsonPath(buildJsonPathToDescriptionInListByIndex(1)).value(secondSkill.getDescription()))
            .andExpect(jsonPath(buildJsonPathToBonusesInListByIndex(1)).exists())
            .andExpect(jsonPath(buildJsonPathToIdInBonusListNestedInSkillListByIndexes(1, 0)).value(bonus.getId()))
            .andExpect(jsonPath(buildJsonPathToNameInBonusListNestedInSkillListByIndexes(1, 0)).value(bonus.getName()))
            .andExpect(jsonPath(buildJsonPathToRarityInBonusListNestedInSkillListByIndexes(1, 0))
                .value(bonus.getRarity().toString()))
            .andExpect(jsonPath(buildJsonPathToDamageBoostInBonusListNestedInSkillListByIndexes(1, 0))
                .value(bonus.getDamageBoost()))
            .andExpect(jsonPath(buildJsonPathToCritChanceBoostInBonusListNestedInSkillListByIndexes(1, 0))
                .value(bonus.getCritChanceBoost()))
            .andExpect(jsonPath(buildJsonPathToCritDamageBoostInBonusListNestedInSkillListByIndexes(1, 0))
                .value(bonus.getCritDamageBoost()))
            .andExpect(jsonPath(buildJsonPathToSkillBoostInBonusListNestedInSkillListByIndexes(1, 0))
                .value(bonus.getSkillBoost()));
    }

    @Test
    void getSkillById_shouldReturnExistingSkill_whenIdIsCorrect() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        Skill skill = skillRepository.save(SkillFactory.skillInstance(List.of(bonus)));

        mockMvc.perform(get(buildGetSkillByIdUrl(skill.getId())))
            .andExpect(status().isFound())
            .andExpect(jsonPath(buildJsonPathToId()).value(skill.getId()))
            .andExpect(jsonPath(buildJsonPathToName()).value(skill.getName()))
            .andExpect(jsonPath(buildJsonPathToDescription()).value(skill.getDescription()))
            .andExpect(jsonPath(buildJsonPathToBonuses()).exists())
            .andExpect(jsonPath(buildJsonPathToIdInBonusListByIndex(0)).value(bonus.getId()))
            .andExpect(jsonPath(buildJsonPathToNameInBonusListByIndex(0)).value(bonus.getName()))
            .andExpect(jsonPath(buildJsonPathToRarityInBonusListByIndex(0)).value(bonus.getRarity().toString()))
            .andExpect(jsonPath(buildJsonPathToDamageBoostInBonusListByIndex(0)).value(bonus.getDamageBoost()))
            .andExpect(jsonPath(buildJsonPathToCritChanceBoostInBonusListByIndex(0)).value(bonus.getCritChanceBoost()))
            .andExpect(jsonPath(buildJsonPathToCritDamageBoostInBonusListByIndex(0)).value(bonus.getCritDamageBoost()))
            .andExpect(jsonPath(buildJsonPathToSkillBoostInBonusListByIndex(0)).value(bonus.getSkillBoost()));
    }

    @Test
    void updateSkill_shouldUpdateSkillAndReturnIt_whenRequestIsCorrect() throws Exception {
        Bonus firstBonus = bonusRepository.save(BonusFactory.bonusInstance());
        Bonus secondBonus = bonusRepository.save(BonusFactory.bonusInstance());
        Skill skill = skillRepository.save(SkillFactory.skillInstance(List.of(firstBonus)));

        SkillRequestDto requestDto = SkillFactory.updateSkillRequestDto(List.of(secondBonus.getId()));

        mockMvc.perform(put(buildPutSkillByIdUrl(skill.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(jsonPath(buildJsonPathToId()).value(skill.getId()))
            .andExpect(jsonPath(buildJsonPathToName()).value(requestDto.getName()))
            .andExpect(jsonPath(buildJsonPathToDescription()).value(requestDto.getDescription()))
            .andExpect(jsonPath(buildJsonPathToBonuses()).exists())
            .andExpect(jsonPath(buildJsonPathToBonusesLength()).value(1))
            .andExpect(jsonPath(buildJsonPathToIdInBonusListByIndex(0)).value(secondBonus.getId()))
            .andExpect(jsonPath(buildJsonPathToNameInBonusListByIndex(0)).value(secondBonus.getName()))
            .andExpect(jsonPath(buildJsonPathToRarityInBonusListByIndex(0)).value(secondBonus.getRarity().toString()))
            .andExpect(jsonPath(buildJsonPathToDamageBoostInBonusListByIndex(0)).value(secondBonus.getDamageBoost()))
            .andExpect(
                jsonPath(buildJsonPathToCritChanceBoostInBonusListByIndex(0)).value(secondBonus.getCritChanceBoost()))
            .andExpect(
                jsonPath(buildJsonPathToCritDamageBoostInBonusListByIndex(0)).value(secondBonus.getCritDamageBoost()))
            .andExpect(jsonPath(buildJsonPathToSkillBoostInBonusListByIndex(0)).value(secondBonus.getSkillBoost()));

        Skill updatedSkill = skillRepository.findById(skill.getId()).get();

        assertThat(updatedSkill.getName()).isEqualTo(requestDto.getName());
        assertThat(updatedSkill.getDescription()).isEqualTo(requestDto.getDescription());
    }

    @Test
    void updateSkill_shouldRespondWithBadRequestStatus_whenNameIsNull() throws Exception {
        Skill skill = skillRepository.save(SkillFactory.skillInstance());
        SkillRequestDto requestDto = SkillFactory.updateSkillRequestDtoWithoutName();

        mockMvc.perform(put(buildPutSkillByIdUrl(skill.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        skill = skillRepository.findById(skill.getId()).get();

        assertThat(skill.getName()).isNotEqualTo(requestDto.getName());
        assertThat(skill.getDescription()).isNotEqualTo(requestDto.getDescription());
    }

    @Test
    void updateSkill_shouldRespondWithBadRequestStatus_whenNameConsistsOfSpaces() throws Exception {
        Skill skill = skillRepository.save(SkillFactory.skillInstance());
        SkillRequestDto requestDto = SkillFactory.updateSkillRequestDtoWithNameConsistingOfSpaces();

        mockMvc.perform(put(buildPutSkillByIdUrl(skill.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        skill = skillRepository.findById(skill.getId()).get();

        assertThat(skill.getName()).isNotEqualTo(requestDto.getName());
        assertThat(skill.getDescription()).isNotEqualTo(requestDto.getDescription());
    }

    @Test
    void updateSkill_shouldRespondWithBadRequestStatus_whenDescriptionIsNull() throws Exception {
        Skill skill = skillRepository.save(SkillFactory.skillInstance());
        SkillRequestDto requestDto = SkillFactory.updateSkillRequestDtoWithoutDescription();

        mockMvc.perform(put(buildPutSkillByIdUrl(skill.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        skill = skillRepository.findById(skill.getId()).get();

        assertThat(skill.getName()).isNotEqualTo(requestDto.getName());
        assertThat(skill.getDescription()).isNotEqualTo(requestDto.getDescription());
    }

    @Test
    void updateSkill_shouldRespondWithBadRequestStatus_whenDescriptionConsistsOfSpaces() throws Exception {
        Skill skill = skillRepository.save(SkillFactory.skillInstance());
        SkillRequestDto requestDto = SkillFactory.updateSkillRequestDtoWithDescriptionConsistingOfSpaces();

        mockMvc.perform(put(buildPutSkillByIdUrl(skill.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        skill = skillRepository.findById(skill.getId()).get();

        assertThat(skill.getName()).isNotEqualTo(requestDto.getName());
        assertThat(skill.getDescription()).isNotEqualTo(requestDto.getDescription());
    }

    @Test
    void updateSkill_shouldRespondWithBadRequestStatus_whenBonusIdsIsNull() throws Exception {
        Skill skill = skillRepository.save(SkillFactory.skillInstance());
        SkillRequestDto requestDto = SkillFactory.updateSkillRequestDtoWithoutBonusIds();

        mockMvc.perform(put(buildPutSkillByIdUrl(skill.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        skill = skillRepository.findById(skill.getId()).get();

        assertThat(skill.getName()).isNotEqualTo(requestDto.getName());
        assertThat(skill.getDescription()).isNotEqualTo(requestDto.getDescription());
    }

    @Test
    void deleteSkill_shouldDeleteSkill_whenIdIsCorrect() throws Exception {
        Skill skill = skillRepository.save(SkillFactory.skillInstance());

        mockMvc.perform(delete(buildDeleteSkillByIdUrl(skill.getId())))
            .andExpect(status().isOk());

        assertThat(skillRepository.findById(skill.getId())).isEmpty();
    }
}
