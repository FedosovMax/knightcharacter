package com.knightexperience.app.rest;

import com.knightexperience.app.rest.request.TodoRequest;
import com.knightexperience.app.service.ExperienceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ExperienceResource {

    private final ExperienceService experienceService;

    @PostMapping
    public ResponseEntity<Void> calculateTodo(TodoRequest todoRequest) {

        experienceService.calculateExperience(todoRequest);

        return ResponseEntity.ok().build();
    }
}
