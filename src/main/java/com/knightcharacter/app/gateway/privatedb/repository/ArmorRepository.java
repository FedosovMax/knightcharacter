package com.knightcharacter.app.gateway.privatedb.repository;

import com.knightcharacter.app.gateway.privatedb.representation.Armor;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ArmorRepository extends JpaRepository<Armor, String> {
}
