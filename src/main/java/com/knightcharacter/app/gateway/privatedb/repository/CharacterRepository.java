package com.knightcharacter.app.gateway.privatedb.repository;

import com.knightcharacter.app.gateway.privatedb.representation.Character;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterRepository extends JpaRepository<Character, String> {

    Optional<Character> findByUserId(String userId);
}
