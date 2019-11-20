package com.knightexperience.app.rest.mappers;

import com.knightexperience.app.domain.TodoVO;
import com.knightexperience.app.rest.request.TodoRequest;
import com.knightexperience.app.rest.response.TodoResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TodoMapper {

    TodoVO toTodoVO(TodoRequest todoRequest);

    TodoResponse toTodoResponse(TodoVO todoVO);
}
