package com.knightcharacter.app.service;

import com.knightcharacter.app.domain.ItemVO;

import java.util.List;

public interface ItemService {

    List<ItemVO> findAll();
}
