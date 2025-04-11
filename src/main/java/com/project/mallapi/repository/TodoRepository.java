package com.project.mallapi.repository;

import com.project.mallapi.domain.Todo;
import com.project.mallapi.dto.TodoListDTO;
import com.project.mallapi.repository.search.TodoSearch;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TodoRepository extends JpaRepository<Todo, Long>, TodoSearch {

    @EntityGraph(attributePaths = {"member","imageList"})
    @Query("select t from Todo t where t.tno = :tno")
    Optional<Todo> selectOneWithImageList(@Param("tno") Long tno);

    @Query("SELECT new com.project.mallapi.dto.TodoListDTO(t.tno, t.title, t.content, m.email, t.complete, t.dueDate, ti.fileName) "
            + "FROM Todo t "
            + "LEFT JOIN t.imageList ti ON ti.ord = 0 "
            + "LEFT JOIN t.member m "
            + "WHERE m.email = :email "
            + "and t.complete is false")
    Page<TodoListDTO> getItemsOfTodoListDTOByEmailComplete(@Param("email") String email, Pageable pageable);

    @Query("SELECT new com.project.mallapi.dto.TodoListDTO("
            + "t.tno, t.title, t.content, m.email, t.complete, t.dueDate, "
            + "(SELECT ti.fileName FROM Todo t2 JOIN t2.imageList ti WHERE t2 = t AND ti.ord = 0)"
            + ") "
            + "FROM Todo t "
            + "LEFT JOIN t.member m "
            + "WHERE m.email = :email "
            + "AND t.complete = false")
    Page<TodoListDTO> getItemsWithSubquery(@Param("email") String email, Pageable pageable);

    @Query("SELECT new com.project.mallapi.dto.TodoListDTO(t.tno, t.title, t.content, m.email, t.complete, t.dueDate, ti.fileName) "
            + "FROM Todo t "
            + "LEFT JOIN t.imageList ti ON ti.ord = 0 "
            + "LEFT JOIN t.member m "
            + "WHERE m.email = :email "
            + "and t.complete is false ")
    List<TodoListDTO> findAllTodoListDTOByEmailComplete(@Param("email") String email);

    @Query("SELECT t.member.email FROM Todo t WHERE t.tno = :tno")
    String getMemberEmailByTodoId(@Param("tno") Long tno);

    @Query("SELECT new com.project.mallapi.dto.TodoListDTO(t.tno, t.title, t.content, t.member.email, t.complete, t.dueDate, ti.fileName) "
            + "FROM Todo t "
            + "LEFT JOIN t.imageList ti ON ti.ord = 0 "
            + "WHERE t.complete is false "
            + "ORDER BY t.tno DESC "
            + "LIMIT 3")
    List<TodoListDTO> getRecentTodoList();

    @Query("SELECT new com.project.mallapi.dto.TodoListDTO(t.tno, t.title, t.content, t.member.email, t.complete, t.dueDate, ti.fileName) "
            + "FROM Todo t "
            + "LEFT JOIN t.imageList ti ON ti.ord = 0 "
            + "WHERE t.complete is false "
            + "ORDER BY t.dueDate DESC, t.tno DESC "
            + "LIMIT 3")
    List<TodoListDTO> getDeadlineTodoList();

//    @EntityGraph(attributePaths = {"member","imageList"})
//    @Query("select t from Todo t")
//    List<Todo> findAll();

    @EntityGraph(attributePaths = {"member","imageList"})
    @Query("select t from Todo t where t.tno = :tno")
    Optional<Todo> findById(@Param("tno") Long tno);

    @EntityGraph(attributePaths = {"member","imageList"})
    @Query("select t from Todo t where t.complete = :b")
    List<Todo> findByComplete(@Param("b") boolean b);

    @EntityGraph(attributePaths = {"member","imageList"})
    @Query("select t from Todo t")
    Page<Todo> getAll(Pageable pageable);

    @EntityGraph(attributePaths = {"imageList", "member"})
    @Query("select t from Todo t")
    List<Todo> findAll();

    @EntityGraph(attributePaths = {"member","imageList"})
    @Query("select t from Todo t where t.member.email = :email")
    List<Todo> findAllByMemberEmail(@Param("email") String email);

    @Query("SELECT new com.project.mallapi.dto.TodoListDTO(t.tno, t.title, t.content, m.email, t.complete, t.dueDate, ti.fileName) "
            + "FROM Todo t "
            + "LEFT JOIN t.imageList ti ON ti.ord = 0 "
            + "LEFT JOIN t.member m "
            + "WHERE m.email = :email "
            + "and t.complete is false")
    List<TodoListDTO> getTodoListDTOByJoin(@Param("email") String email);

    @Query("""
        SELECT new com.project.mallapi.dto.TodoListDTO(
            t.tno,
            t.title,
            t.content,
            (
                SELECT m.email FROM Member m
                WHERE m = t.member
            ),
            t.complete,
            t.dueDate,
            (
                SELECT ti.fileName FROM Todo t2 JOIN t2.imageList ti
                WHERE t2 = t AND ti.ord = 0
            )
        ) 
        FROM Todo t 
        WHERE t.member.email = :email 
        AND t.complete = false """)
    List<TodoListDTO> getTodoListDTOBySubquery(@Param("email") String email);

    @Query("SELECT new com.project.mallapi.dto.TodoListDTO(t.tno, t.title, t.content, m.email, t.complete, t.dueDate, ti.fileName) "
            + "FROM Todo t "
            + "LEFT JOIN t.imageList ti ON ti.ord = 0 "
            + "LEFT JOIN t.member m "
            + "WHERE m.email = :email "
            + "and t.complete is false")
    Page<TodoListDTO> getTodoListDTOByJoin(@Param("email") String email, Pageable pageable);

    @Query("""
        SELECT new com.project.mallapi.dto.TodoListDTO(
            t.tno,
            t.title,
            t.content,
            (
                SELECT m.email FROM Member m
                WHERE m = t.member
            ),
            t.complete,
            t.dueDate,
            (
                SELECT ti.fileName FROM Todo t2 JOIN t2.imageList ti
                WHERE t2 = t AND ti.ord = 0
            )
        ) 
        FROM Todo t 
        WHERE t.member.email = :email 
        AND t.complete = false """)
    Page<TodoListDTO> getTodoListDTOBySubquery(@Param("email") String email, Pageable pageable);

}
