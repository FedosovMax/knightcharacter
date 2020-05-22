package com.knightcharacter.app.service.impl;

import com.knightcharacter.app.domain.ArmorVO;
import com.knightcharacter.app.domain.BonusVO;
import com.knightcharacter.app.exception.ArmorNotFoundException;
import com.knightcharacter.app.gateway.ArmorGateway;
import com.knightcharacter.app.service.ArmorService;
import com.knightcharacter.app.service.BonusService;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ArmorServiceImpl implements ArmorService {

    private final ArmorGateway armorGateway;
    private final BonusService bonusService;

    @Override
    public ArmorVO save(ArmorVO armorVO) {
        armorVO.setBonuses(fetchBonusesForArmor(armorVO));
        return armorGateway.save(armorVO);
    }

    private List<BonusVO> fetchBonusesForArmor(ArmorVO armorVO) {
        return armorVO.getBonuses().stream()
            .map(BonusVO::getId)
            .map(bonusService::findById)
            .collect(Collectors.toList());
    }

    @Override
    public List<ArmorVO> findAll() {
        return armorGateway.findAll();
    }

    @Override
    public ArmorVO findById(String armorId) {
        return armorGateway.findById(armorId).orElseThrow(
            () -> new ArmorNotFoundException(String.format("Armor with such id:%s can't be found", armorId)));
    }

    @Override
    public ArmorVO updateArmor(String armorId, ArmorVO changedArmorVO) {
        ArmorVO armorVO = findById(armorId);

        armorVO.setDefence(changedArmorVO.getDefence());
        armorVO.setArmorType(changedArmorVO.getArmorType());
        armorVO.setName(changedArmorVO.getName());
        armorVO.setDescription(changedArmorVO.getDescription());
        armorVO.setRarity(changedArmorVO.getRarity());
        armorVO.setRequiredAgility(changedArmorVO.getRequiredAgility());
        armorVO.setRequiredIntelligence(changedArmorVO.getRequiredIntelligence());
        armorVO.setRequiredLevel(changedArmorVO.getRequiredLevel());
        armorVO.setRequiredStrength(changedArmorVO.getRequiredStrength());
        armorVO.setBonuses(fetchBonusesForArmor(changedArmorVO));

        return armorGateway.save(armorVO);
    }

    @Override
    public void deleteById(String armorId) {
        armorGateway.deleteById(armorId);
    }
}
