package com.knightcharacter.app.rest.request;

import com.knightcharacter.app.gateway.privatedb.representation.enums.ArmorType;
import com.knightcharacter.app.validation.annotation.ValidEnumValue;

import javax.validation.constraints.NotNull;

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
public class ArmorRequestDto extends ItemRequestDto {

    @ValidEnumValue(enumClass = ArmorType.class)
    private String armorType;

    @NotNull
    private Integer defence;
}
