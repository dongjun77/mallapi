package com.project.mallapi.repository.search;

import com.project.mallapi.domain.Todo;
import com.project.mallapi.dto.PageRequestDTO;
import com.project.mallapi.dto.TodoDTO;
import org.springframework.data.domain.Page;

public interface TodoSearch {

    Page<TodoDTO> search(String email, PageRequestDTO pageRequestDTO);

}
