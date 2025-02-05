package com.project.mallapi.repository.search;

import com.project.mallapi.domain.Todo;
import org.springframework.data.domain.Page;

public interface TodoSearch {

    Page<Todo> search1();

}
