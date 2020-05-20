package com.knightcharacter.app.rest.request;

import com.knightcharacter.app.gateway.privatedb.representation.enums.Rarity;
import com.knightcharacter.app.validation.annotation.ValidEnumValue;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BonusRequestDto {

    @NotBlank
    private String name;

    @ValidEnumValue(enumClass = Rarity.class)
    private String rarity;

    @NotNull
    private Integer damageBoost;

    @NotNull
    private Integer critChanceBoost;

    @NotNull
    private Integer critDamageBoost;

    @NotNull
    private Integer skillBoost;
}
