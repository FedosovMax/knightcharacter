package com.knightcharacter.app.gateway;

import com.knightcharacter.app.domain.CharacterVO;
import java.util.List;
import java.util.Optional;

public interface CharacterGateway {

    CharacterVO save(CharacterVO characterVO);

    List<CharacterVO> findAll();

    Optional<CharacterVO> findById(String characterId);

    Optional<CharacterVO> findByUserId(String userId);

    void deleteById(String characterId);
}
