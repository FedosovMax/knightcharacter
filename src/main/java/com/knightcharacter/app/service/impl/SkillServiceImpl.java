package com.knightcharacter.app.service.impl;

import com.knightcharacter.app.domain.BonusVO;
import com.knightcharacter.app.domain.SkillVO;
import com.knightcharacter.app.exception.SkillNotFoundException;
import com.knightcharacter.app.gateway.SkillGateway;
import com.knightcharacter.app.service.BonusService;
import com.knightcharacter.app.service.SkillService;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SkillServiceImpl implements SkillService {

    private final SkillGateway skillGateway;
    private final BonusService bonusService;

    @Override
    public SkillVO save(SkillVO skillVO) {
        skillVO.setBonuses(fetchBonusesByIds(extractBonusIds(skillVO)));
        return skillGateway.save(skillVO);
    }

    private List<BonusVO> fetchBonusesByIds(List<String> bonusIds) {
        return bonusIds.stream().map(bonusService::findById).collect(Collectors.toList());
    }

    private List<String> extractBonusIds(SkillVO skillVO) {
        return skillVO.getBonuses().stream().map(BonusVO::getId).collect(Collectors.toList());
    }

    @Override
    public List<SkillVO> findAll() {
        return skillGateway.findAll();
    }

    @Override
    public SkillVO findById(String skillId) {
        return skillGateway.findById(skillId).orElseThrow(
            () -> new SkillNotFoundException(String.format("Skill with such id:%s can't be found", skillId)));
    }

    @Override
    public SkillVO updateSkill(String skillId, SkillVO changedSkillVO) {
        SkillVO skillVO = findById(skillId);

        skillVO.setName(changedSkillVO.getName());
        skillVO.setDescription(changedSkillVO.getDescription());
        skillVO.setBonuses(fetchBonusesByIds(extractBonusIds(changedSkillVO)));

        return skillGateway.save(skillVO);
    }

    @Override
    public void deleteById(String skillId) {
        skillGateway.deleteById(skillId);
    }
}
