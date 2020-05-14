package com.knightcharacter.app.domain;

import com.knightcharacter.app.gateway.privatedb.representation.enums.Rarity;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class ItemVO {

        private String id;

        private Integer requiredLevel;

        private Integer requiredStrength;

        private Integer requiredAgility;

        private Integer requiredIntelligence;

        private Rarity rarity;

        private List<BonusVO> bonuses = new ArrayList<>();
}
