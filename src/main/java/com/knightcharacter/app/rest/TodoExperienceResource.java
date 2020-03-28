package com.knightcharacter.app.rest;

import com.knightcharacter.app.domain.TodoVO;
import com.knightcharacter.app.rest.mappers.TodoMapper;
import com.knightcharacter.app.rest.request.TodoRequest;
import com.knightcharacter.app.rest.response.TodoResponse;
import com.knightcharacter.app.service.ExperienceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TodoExperienceResource {

    private final ExperienceService experienceService;
    private final TodoMapper todoMapper;

    @PostMapping
    public ResponseEntity<TodoResponse> calculateTodo(TodoRequest todoRequest) {
        TodoVO todoVO = todoMapper.toTodoVO(todoRequest);
        experienceService.calculateExperience(todoVO);
        TodoResponse todoResponse = todoMapper.toTodoResponse(todoVO);
        return ResponseEntity.ok().body(todoResponse);
    }
}
