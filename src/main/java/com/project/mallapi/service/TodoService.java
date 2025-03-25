package com.project.mallapi.service;

import com.project.mallapi.domain.Member;
import com.project.mallapi.domain.Todo;
import com.project.mallapi.domain.TodoImage;
import com.project.mallapi.dto.PageRequestDTO;
import com.project.mallapi.dto.PageResponseDTO;
import com.project.mallapi.dto.TodoDTO;
import com.project.mallapi.dto.TodoListDTO;
import jakarta.transaction.Transactional;
import java.util.List;

@Transactional
public interface TodoService {

    TodoDTO get(Long tno);

    Long register(TodoDTO dto);

    void modify(TodoDTO dto);

    void todoComplete(Long tno);

    void remove(Long tno,String email);

    String getTodoWriter(Long tno);

    PageResponseDTO<TodoListDTO> getList(PageRequestDTO pageRequestDTO, String email);

    List<TodoListDTO> findAllTodoList(String email);

    List<TodoListDTO> searchAllTodoList(String email);

    default TodoDTO entityToDTO(Todo todo) {
        TodoDTO todoDTO = TodoDTO.builder()
                .tno(todo.getTno())
                .title(todo.getTitle())
                .content(todo.getContent())
                .complete(todo.isComplete())
                .dueDate(todo.getDueDate())
                .memberEmail(todo.getMember().getEmail())
                .build();

        List<TodoImage> imageList = todo.getImageList();

        if (imageList == null && imageList.isEmpty()) {
            return todoDTO;
        }

        List<String> fileNameList = imageList.stream().map(todoImage ->
                todoImage.getFileName()).toList();

        todoDTO.setUploadFileNames(fileNameList);

        return todoDTO;
    }

    default Todo dtoToEntity(TodoDTO todoDTO, Member member) {

        Todo todo = Todo.builder()
                .tno(todoDTO.getTno())
                .title(todoDTO.getTitle())
                .content(todoDTO.getContent())
                .complete(todoDTO.isComplete())
                .dueDate(todoDTO.getDueDate())
                .build();

        if (member != null) {
            todo.addMember(member);
        }

        List<String> uploadFileNames = todoDTO.getUploadFileNames();

        if (uploadFileNames == null || uploadFileNames.size() == 0) {
            return todo;
        }

        uploadFileNames.forEach(fileName -> {
            todo.addImageString(fileName);
        });

        return todo;
    }

}
