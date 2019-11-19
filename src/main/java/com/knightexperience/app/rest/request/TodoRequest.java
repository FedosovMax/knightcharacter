package com.knightexperience.app.rest.request;

import com.knightexperience.app.domain.enums.Hardness;
import com.knightexperience.app.domain.enums.Scaryness;
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
