package com.knightcharacter.app.service.impl;

import com.knightcharacter.app.domain.BonusVO;
import com.knightcharacter.app.exception.BonusNotFoundException;
import com.knightcharacter.app.gateway.BonusGateway;
import com.knightcharacter.app.service.BonusService;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BonusServiceImpl implements BonusService {

    private final BonusGateway bonusGateway;

    @Override
    public BonusVO save(BonusVO bonusVO) {
        return bonusGateway.save(bonusVO);
    }

    @Override
    public List<BonusVO> findAll() {
        return bonusGateway.findAll();
    }

    @Override
    public BonusVO findById(String bonusId) {
        return bonusGateway.findById(bonusId)
            .orElseThrow(() -> new BonusNotFoundException(
                String.format("Bonus with such id:%s can't be found", bonusId)));
    }

    @Override
    public BonusVO updateBonus(String bonusId, BonusVO changedBonusVO) {
        BonusVO bonusVO = findById(bonusId);

        bonusVO.setName(changedBonusVO.getName());
        bonusVO.setRarity(changedBonusVO.getRarity());
        bonusVO.setDamageBoost(changedBonusVO.getDamageBoost());
        bonusVO.setCritChanceBoost(changedBonusVO.getCritChanceBoost());
        bonusVO.setCritDamageBoost(changedBonusVO.getCritDamageBoost());
        bonusVO.setSkillBoost(changedBonusVO.getSkillBoost());

        return bonusGateway.save(bonusVO);
    }

    @Override
    public void deleteById(String bonusId) {
        bonusGateway.deleteById(bonusId);
    }
}
