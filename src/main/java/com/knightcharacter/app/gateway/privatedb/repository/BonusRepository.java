package com.knightcharacter.app.gateway.privatedb.repository;

import com.knightcharacter.app.gateway.privatedb.representation.Bonus;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BonusRepository extends JpaRepository<Bonus, String> {
}
