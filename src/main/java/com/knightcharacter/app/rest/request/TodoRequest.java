package com.knightcharacter.app.rest.request;

import com.knightcharacter.app.domain.enums.Hardness;
import com.knightcharacter.app.domain.enums.Scaryness;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TodoRequest {

    @NotBlank
    private long id;

    @NotBlank
    private Long userId;

    @NotBlank
    private Scaryness scaryness;

    @NotBlank
    private Hardness hardness;

}
