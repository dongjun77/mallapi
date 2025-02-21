package com.project.mallapi.repository.search;

import com.project.mallapi.domain.Todo;
import com.project.mallapi.dto.PageRequestDTO;
import org.springframework.data.domain.Page;

public interface TodoSearch {

    Page<Todo> search(PageRequestDTO pageRequestDTO);

}
