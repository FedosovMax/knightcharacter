package com.knightexperience.app.gateway;

import com.knightexperience.app.domain.CharacterVO;
import com.knightexperience.app.gateway.privatedb.mapper.CharacterMapper;
import com.knightexperience.app.gateway.privatedb.repository.CharacterRepository;
import com.knightexperience.app.gateway.privatedb.representation.Character;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CharacterGatewayImpl implements CharacterGateway {

    private final CharacterRepository characterRepository;
    private final CharacterMapper characterMapper;

    @Override
    public CharacterVO save(CharacterVO characterVO) {
        Character savedCharacter = characterRepository.save(characterMapper.toCharacter(characterVO));
        return characterMapper.toCharacterVO(savedCharacter);
    }

    @Override
    public List<CharacterVO> findAll() {
        return characterRepository.findAll().stream().map(characterMapper::toCharacterVO).collect(Collectors.toList());
    }

    @Override
    public Optional<CharacterVO> findById(String characterId) {
        return characterRepository.findById(characterId).map(characterMapper::toCharacterVO);
    }

    @Override
    public void deleteById(String characterId) {
        characterRepository.deleteById(characterId);
    }
}
