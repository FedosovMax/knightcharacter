package com.knightcharacter.app.rest.request;

import com.knightcharacter.app.validation.annotation.ValidRarity;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class ItemRequestDto {

    @NotNull
    private Integer requiredLevel;

    @NotNull
    private Integer requiredStrength;

    @NotNull
    private Integer requiredAgility;

    @NotNull
    private Integer requiredIntelligence;

    @ValidRarity
    private String rarity;

    @NotNull
    private List<String> bonusIds = new ArrayList<>();
}
