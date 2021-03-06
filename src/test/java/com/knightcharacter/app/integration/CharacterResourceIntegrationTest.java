package com.knightcharacter.app.integration;

import static com.knightcharacter.app.Constants.BASE_CHARACTER;
import static com.knightcharacter.app.TestConstants.buildDeleteCharacterByIdUrl;
import static com.knightcharacter.app.TestConstants.buildGetCharacterByIdUrl;
import static com.knightcharacter.app.TestConstants.buildJsonPathToCharacterName;
import static com.knightcharacter.app.TestConstants.buildJsonPathToId;
import static com.knightcharacter.app.TestConstants.buildJsonPathToLength;
import static com.knightcharacter.app.TestConstants.buildPutCharacterByIdUrl;

import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.knightcharacter.app.factories.CharacterFactory;
import com.knightcharacter.app.gateway.privatedb.repository.CharacterRepository;
import com.knightcharacter.app.gateway.privatedb.representation.Character;
import com.knightcharacter.app.rest.request.CharacterRequestDto;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class CharacterResourceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CharacterRepository characterRepository;

    @AfterEach
    public void tearDown() {
        characterRepository.deleteAll();
    }

    @Test
    public void addCharacter_shouldAddCharacterAndReturnIt_whenRequestIsCorrect() throws Exception {
        CharacterRequestDto requestDto = CharacterFactory.characterRequestDtoInstance();

        mockMvc.perform(post(BASE_CHARACTER)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isCreated())
            .andExpect(jsonPath(buildJsonPathToId()).exists());

        assertThat(characterRepository.count()).isEqualTo(1);
    }

    @Test
    public void addCharacter_shouldRespondWithBadRequestStatus_whenNameIsNull() throws Exception {
        CharacterRequestDto requestDto = CharacterFactory.characterRequestDtoWithNullNameInstance();

        mockMvc.perform(post(BASE_CHARACTER)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(characterRepository.count()).isEqualTo(0);
    }

    @Test
    public void addCharacter_shouldRespondWithBadRequestStatus_whenNameConsistsOfSpaces() throws Exception {
        CharacterRequestDto requestDto = CharacterFactory.characterRequestDtoWithNameOfSpacesInstance();

        mockMvc.perform(post(BASE_CHARACTER)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(characterRepository.count()).isEqualTo(0);
    }

    @Test
    public void getAllCharacters_shouldReturnTwoCharacters() throws Exception {
        characterRepository.save(CharacterFactory.createCharacterInstance());
        characterRepository.save(CharacterFactory.createCharacterInstance());

        mockMvc.perform(get(BASE_CHARACTER))
            .andExpect(status().isFound())
            .andExpect(jsonPath(buildJsonPathToLength()).value(2));
    }

    @Test
    public void getCharacterById_shouldReturnExistingCharacter_whenIdIsCorrect() throws Exception {
        Character character = characterRepository.save(CharacterFactory.createCharacterInstance());

        mockMvc.perform(get(buildGetCharacterByIdUrl(character.getId())))
            .andExpect(status().isFound())
            .andExpect(jsonPath(buildJsonPathToId()).value(character.getId()));
    }

    @Test
    public void updateCharacter_shouldUpdateCharacterAndReturnIt_whenRequestIsCorrect() throws Exception {
        Character character = characterRepository.save(CharacterFactory.createCharacterInstance());
        CharacterRequestDto requestDto = CharacterFactory.characterRequestDtoInstance();

        mockMvc.perform(put(buildPutCharacterByIdUrl(character.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(jsonPath(buildJsonPathToCharacterName()).value(requestDto.getCharacterName()));

        assertThat(characterRepository.findById(character.getId()).get().getName())
            .isEqualTo(requestDto.getCharacterName());
    }

    @Test
    public void updateCharacter_shouldRespondWithBadRequestStatus_whenNameIsNull() throws Exception {
        Character character = characterRepository.save(CharacterFactory.createCharacterInstance());
        CharacterRequestDto requestDto = CharacterFactory.characterRequestDtoWithNullNameInstance();

        mockMvc.perform(put(buildPutCharacterByIdUrl(character.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void updateCharacter_shouldRespondWithBadRequestStatus_whenIdConsistsOfSpaces() throws Exception {
        Character character = characterRepository.save(CharacterFactory.createCharacterInstance());
        CharacterRequestDto requestDto = CharacterFactory.characterRequestDtoWithNameOfSpacesInstance();

        mockMvc.perform(put(buildPutCharacterByIdUrl(character.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteCharacter_shouldDeleteCharacter_whenIdIsCorrect() throws Exception {
        Character character = characterRepository.save(CharacterFactory.createCharacterInstance());

        mockMvc.perform(delete(buildDeleteCharacterByIdUrl(character.getId())))
            .andExpect(status().isOk());

        assertThat(characterRepository.findById(character.getId())).isEmpty();
    }
}
