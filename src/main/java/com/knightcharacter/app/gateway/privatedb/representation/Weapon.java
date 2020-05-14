package com.knightcharacter.app.gateway.privatedb.representation;

import com.knightcharacter.app.gateway.privatedb.representation.enums.WeaponType;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Weapon extends Item {

    @Enumerated(EnumType.STRING)
    @Column(name = "weapon_type")
    private WeaponType weaponType;

    private Integer damage;
}
