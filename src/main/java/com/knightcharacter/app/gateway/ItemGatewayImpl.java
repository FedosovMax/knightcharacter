package com.knightcharacter.app.gateway;

import com.knightcharacter.app.domain.ItemVO;
import com.knightcharacter.app.gateway.privatedb.mapper.ItemMapper;
import com.knightcharacter.app.gateway.privatedb.repository.ItemRepository;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ItemGatewayImpl implements ItemGateway {

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    @Override
    public List<ItemVO> findAll() {
        return itemRepository.findAll().stream().map(itemMapper::toItemVO).collect(Collectors.toList());
    }
}
