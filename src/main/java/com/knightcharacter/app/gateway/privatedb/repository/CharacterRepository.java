package com.knightexperience.app.gateway.privatedb.repository;

import com.knightexperience.app.gateway.privatedb.representation.Character;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterRepository extends JpaRepository<Character, String> {

}
