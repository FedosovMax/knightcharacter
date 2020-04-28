package com.knightcharacter.app.gateway;

import com.knightcharacter.app.domain.BonusVO;

import java.util.List;
import java.util.Optional;

public interface BonusGateway {

    BonusVO save(BonusVO bonusVO);

    List<BonusVO> findAll();

    Optional<BonusVO> findById(String bonusId);

    void deleteById(String bonusId);
}
