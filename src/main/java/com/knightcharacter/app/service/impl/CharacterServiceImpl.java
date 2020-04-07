package com.knightexperience.app.service.impl;

import com.knightexperience.app.domain.CharacterVO;
import com.knightexperience.app.exception.CharacterNotFoundException;
import com.knightexperience.app.gateway.CharacterGateway;
import com.knightexperience.app.service.CharacterService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CharacterServiceImpl implements CharacterService {

    private final CharacterGateway characterGateway;

    @Override
    public CharacterVO save(CharacterVO characterVO) {
        return characterGateway.save(characterVO);
    }

    @Override
    public List<CharacterVO> findAll() {
        return characterGateway.findAll();
    }

    @Override
    public CharacterVO findById(String characterId) {
        return characterGateway.findById(characterId)
            .orElseThrow(() -> new CharacterNotFoundException(
                String.format("Character with such id:%s can't be " + "found", characterId)));
    }

    @Override
    public CharacterVO updateCharacter(CharacterVO changedCharacterVO, String characterId) {
        CharacterVO characterVO = findById(characterId);
        characterVO.setName(changedCharacterVO.getName());
        return characterGateway.save(characterVO);
    }

    @Override
    public void deleteById(String characterId) {
        characterGateway.deleteById(characterId);
    }
}
