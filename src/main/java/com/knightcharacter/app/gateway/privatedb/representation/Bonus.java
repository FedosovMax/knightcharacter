package com.knightcharacter.app.gateway.privatedb.representation;

import com.knightcharacter.app.gateway.privatedb.representation.enums.Rarity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "bonus")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Bonus {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @ColumnDefault("random_uuid()")
    @Column(name = "id")
    private String id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Rarity rarity;

    @Column(name = "damage_boost")
    private Integer damageBoost;

    @Column(name = "crit_chance_boost")
    private Integer critChanceBoost;

    @Column(name = "crit_damage_boost")
    private Integer critDamageBoost;

    @Column(name = "skill_boost")
    private Integer skillBoost;
}
