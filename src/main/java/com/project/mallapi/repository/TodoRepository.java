package com.project.mallapi.repository;

import com.project.mallapi.domain.Todo;
import com.project.mallapi.dto.TodoDTO;
import com.project.mallapi.repository.search.TodoSearch;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TodoRepository extends JpaRepository<Todo, Long>, TodoSearch {

    @EntityGraph(attributePaths = "imageList")
    @Query("select t from Todo t where t.tno = :tno")
    Optional<Todo> selectOneWithImageList(@Param("tno") Long tno);

    @Query("SELECT new com.project.mallapi.dto.TodoDTO(t.tno, t.title, t.content, t.member.email, t.complete, t.dueDate, ti.fileName) "
            + "FROM Todo t "
            + "LEFT JOIN t.imageList ti ON ti.ord = 0 "
            + "WHERE t.member.email = :email "
            + "and t.complete is false")
    Page<TodoDTO> selectListByMember(@Param("email") String email, Pageable pageable);



}
