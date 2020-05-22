package com.knightcharacter.app.domain;

import com.knightcharacter.app.gateway.privatedb.representation.enums.ArmorType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class ArmorVO extends ItemVO {

    private ArmorType armorType;

    private Integer defence;
}
