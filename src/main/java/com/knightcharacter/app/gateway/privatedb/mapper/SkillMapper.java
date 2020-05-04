package com.knightcharacter.app.gateway.privatedb.mapper;

import com.knightcharacter.app.domain.SkillVO;
import com.knightcharacter.app.gateway.privatedb.representation.Skill;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = BonusMapper.class)
public interface SkillMapper {

    Skill toSkill(SkillVO skillVO);

    SkillVO toSkillVO(Skill skill);
}
