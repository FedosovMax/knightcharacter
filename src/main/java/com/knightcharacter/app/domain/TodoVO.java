package com.knightcharacter.app.domain;

import com.knightcharacter.app.domain.enums.Hardness;
import com.knightcharacter.app.domain.enums.Scaryness;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TodoVO {

    private long id;

    private String todoName;

    private Scaryness scaryness;

    private Hardness hardness;

}
