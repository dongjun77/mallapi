package com.project.mallapi.repository.search;

import com.project.mallapi.domain.QTodo;
import com.project.mallapi.domain.QTodoImage;
import com.project.mallapi.domain.Todo;
import com.project.mallapi.dto.PageRequestDTO;
import com.project.mallapi.dto.TodoDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Log4j2
@Repository
@RequiredArgsConstructor
public class TodoSearchImpl implements TodoSearch {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<TodoDTO> search(String email, PageRequestDTO pageRequestDTO) {

        log.info("search.......................");

        QTodo todo = QTodo.todo;
        QTodoImage todoImage = QTodoImage.todoImage;

        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() - 1 ,
                pageRequestDTO.getSize(),
                Sort.by("tno").descending());

        List<TodoDTO> list = queryFactory
                .select(Projections.constructor(
                        TodoDTO.class,
                        todo.tno,
                        todo.title,
                        todo.content,
                        todo.member.email,
                        todo.complete,
                        todo.dueDate,
                        todoImage.fileName
                ))
                .from(todo)
                .leftJoin(todo.imageList, todoImage)
                .on(todoImage.ord.eq(0))
                .where(todo.member.email.eq(email))
                .orderBy(todo.tno.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = Optional.ofNullable(queryFactory
                .select(todo.count())
                .from(todo)
                .where(todo.member.email.eq(email))
                .fetchOne()).orElse(0L);

        return new PageImpl<>(list, pageable, total);
    }
}
