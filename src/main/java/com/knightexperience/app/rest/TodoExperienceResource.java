package com.knightexperience.app.rest;

import com.knightexperience.app.domain.TodoVO;
import com.knightexperience.app.rest.mappers.TodoMapper;
import com.knightexperience.app.rest.request.TodoRequest;
import com.knightexperience.app.rest.response.TodoResponse;
import com.knightexperience.app.service.ExperienceService;
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
