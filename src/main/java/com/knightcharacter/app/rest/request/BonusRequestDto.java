package com.knightcharacter.app.rest.request;

import com.knightcharacter.app.gateway.privatedb.representation.enums.Rarity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class BonusRequestDto {

    @NotBlank
    private String name;

    @NotNull
    private Rarity rarity;

    @NotNull
    private Integer damageBoost;

    @NotNull
    private Integer critChanceBoost;

    @NotNull
    private Integer critDamageBoost;

    @NotNull
    private Integer skillBoost;
}
