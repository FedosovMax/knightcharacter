package com.knightcharacter.app.service.impl;

import com.knightcharacter.app.domain.ItemVO;
import com.knightcharacter.app.gateway.ItemGateway;
import com.knightcharacter.app.service.ItemService;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ItemServiceImpl implements ItemService {

    private final ItemGateway itemGateway;

    @Override
    public List<ItemVO> findAll() {
        return itemGateway.findAll();
    }
}
