package com.knightcharacter.app.rest;

import static com.knightcharacter.app.Constants.API_BASE_ITEMS;

import com.knightcharacter.app.rest.mappers.ItemRestMapper;
import com.knightcharacter.app.rest.response.ItemResponseDto;
import com.knightcharacter.app.service.ItemService;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(API_BASE_ITEMS)
@Slf4j
public class ItemResource {

    private final ItemService itemService;
    private final ItemRestMapper itemRestMapper;

    @GetMapping
    public ResponseEntity<List<ItemResponseDto>> findAllItems() {
        log.info("Request to find all items");
        return ResponseEntity.status(HttpStatus.FOUND)
            .body(itemService.findAll().stream().map(itemRestMapper::toItemVO).collect(Collectors.toList()));
    }
}
