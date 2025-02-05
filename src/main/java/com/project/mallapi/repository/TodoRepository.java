package com.project.mallapi.repository;

import com.project.mallapi.domain.Todo;
import com.project.mallapi.repository.search.TodoSearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long>, TodoSearch {

}
