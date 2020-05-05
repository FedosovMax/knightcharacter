package com.knightcharacter.app.service;

import com.knightcharacter.app.domain.SkillVO;

import java.util.List;

public interface SkillService {

    SkillVO save(SkillVO skillVO);

    List<SkillVO> findAll();

    SkillVO findById(String skillId);

    SkillVO updateSkill(String skillId, SkillVO changedSkillVO);

    void deleteById(String skillId);
}
