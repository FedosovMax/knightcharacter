package com.knightcharacter.app.domain;

import java.util.ArrayList;
import java.util.List;

public class SkillVO {

    private String id;

    private String name;

    private String description;

    List<BonusVO> bonuses = new ArrayList<>();
}
