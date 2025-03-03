package com.project.mallapi.repository.search;

import com.project.mallapi.domain.Todo;
import com.project.mallapi.dto.PageRequestDTO;
import com.project.mallapi.dto.TodoDTO;
import com.project.mallapi.dto.TodoListDTO;
import java.util.List;
import org.springframework.data.domain.Page;

public interface TodoSearch {

    Page<TodoListDTO> search(String email, PageRequestDTO pageRequestDTO);

    List<TodoListDTO> searchAll(String email);

}
