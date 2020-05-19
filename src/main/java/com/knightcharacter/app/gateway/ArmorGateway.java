package com.knightcharacter.app.gateway;

import com.knightcharacter.app.domain.ArmorVO;

import java.util.List;
import java.util.Optional;

public interface ArmorGateway {

    ArmorVO save(ArmorVO armorVO);

    List<ArmorVO> findAll();

    Optional<ArmorVO> findById(String armorId);

    void deleteById(String armorId);
}
