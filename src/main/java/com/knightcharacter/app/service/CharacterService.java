package com.knightcharacter.app.service;

import com.knightcharacter.app.domain.CharacterVO;
import java.util.List;

public interface CharacterService {

    CharacterVO save(CharacterVO characterVO);

    List<CharacterVO> findAll();

    CharacterVO findById(String characterId);

    CharacterVO updateCharacter(CharacterVO changedCharacterVO, String characterId);

    void addExperience(long experience, String characterId);

    void deleteById(String characterId);
}
