package com.knightcharacter.app.gateway.privatedb.repository;

import com.knightcharacter.app.gateway.privatedb.representation.Skill;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill, String> {
}
