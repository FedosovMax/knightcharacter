package com.knightcharacter.app.rest.response;

import com.knightcharacter.app.gateway.privatedb.representation.enums.Rarity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BonusResponseDto {

    private String id;

    private String name;

    private Rarity rarity;

    private Integer damageBoost;

    private Integer critChanceBoost;

    private Integer critDamageBoost;

    private Integer skillBoost;
}
