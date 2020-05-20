package com.knightcharacter.app.gateway;

import com.knightcharacter.app.domain.ItemVO;

import java.util.List;

public interface ItemGateway {

    List<ItemVO> findAll();
}
