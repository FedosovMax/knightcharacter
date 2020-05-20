package com.knightcharacter.app.gateway.privatedb.repository;

import com.knightcharacter.app.gateway.privatedb.representation.Item;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, String> {
}
