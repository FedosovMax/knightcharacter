package com.knightcharacter.app.gateway.privatedb.repository;

import com.knightcharacter.app.gateway.privatedb.representation.Character;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterRepository extends JpaRepository<Character, String> {

}
