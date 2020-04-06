package com.knightexperience.app.gateway.privatedb.repository;

import com.knightexperience.app.gateway.privatedb.representation.Character;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterRepository extends JpaRepository<Character, Long> {

    Optional<Character> findById(String characterId);

    void deleteById(String characterId);
}
