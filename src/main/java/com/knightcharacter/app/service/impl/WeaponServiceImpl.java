package com.knightcharacter.app.service.impl;

import com.knightcharacter.app.domain.BonusVO;
import com.knightcharacter.app.domain.WeaponVO;
import com.knightcharacter.app.exception.WeaponNotFoundException;
import com.knightcharacter.app.gateway.WeaponGateway;
import com.knightcharacter.app.service.BonusService;
import com.knightcharacter.app.service.WeaponService;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class WeaponServiceImpl implements WeaponService {

    private final WeaponGateway weaponGateway;
    private final BonusService bonusService;

    @Override
    public WeaponVO save(WeaponVO weaponVO) {
        weaponVO.setBonuses(fetchBonusesForWeapon(weaponVO));
        return weaponGateway.save(weaponVO);
    }

    private List<BonusVO> fetchBonusesForWeapon(WeaponVO weaponVO) {
        return weaponVO.getBonuses().stream()
            .map(BonusVO::getId)
            .map(bonusService::findById)
            .collect(Collectors.toList());
    }

    @Override
    public List<WeaponVO> findAll() {
        return weaponGateway.findAll();
    }

    @Override
    public WeaponVO findById(String weaponId) {
        return weaponGateway.findById(weaponId).orElseThrow(
            () -> new WeaponNotFoundException(String.format("Weapon with such id:%s can't be found", weaponId)));
    }

    @Override
    public WeaponVO updateWeapon(String weaponId, WeaponVO changedWeaponVO) {
        WeaponVO weaponVO = findById(weaponId);

        weaponVO.setDamage(changedWeaponVO.getDamage());
        weaponVO.setWeaponType(changedWeaponVO.getWeaponType());
        weaponVO.setName(changedWeaponVO.getName());
        weaponVO.setDescription(changedWeaponVO.getDescription());
        weaponVO.setRarity(changedWeaponVO.getRarity());
        weaponVO.setRequiredAgility(changedWeaponVO.getRequiredAgility());
        weaponVO.setRequiredIntelligence(changedWeaponVO.getRequiredIntelligence());
        weaponVO.setRequiredLevel(changedWeaponVO.getRequiredLevel());
        weaponVO.setRequiredStrength(changedWeaponVO.getRequiredStrength());
        weaponVO.setBonuses(fetchBonusesForWeapon(changedWeaponVO));

        return weaponGateway.save(weaponVO);
    }

    @Override
    public void deleteById(String weaponId) {
        weaponGateway.deleteById(weaponId);
    }
}
