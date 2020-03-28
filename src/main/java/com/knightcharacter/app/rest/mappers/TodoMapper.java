package com.knightcharacter.app.rest.mappers;

import com.knightcharacter.app.domain.TodoVO;
import com.knightcharacter.app.rest.request.TodoRequest;
import com.knightcharacter.app.rest.response.TodoResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TodoMapper {

    TodoVO toTodoVO(TodoRequest todoRequest);

    TodoResponse toTodoResponse(TodoVO todoVO);
}
