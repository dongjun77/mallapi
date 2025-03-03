package com.project.mallapi.repository;

import com.project.mallapi.domain.Member;
import com.project.mallapi.domain.Todo;
import com.project.mallapi.dto.PageRequestDTO;
import com.project.mallapi.dto.TodoDTO;
import com.project.mallapi.dto.TodoListDTO;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@SpringBootTest
@Log4j2
class TodoRepositoryTest {

    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void test1() {

        Assertions.assertNotNull(todoRepository);

        log.info(todoRepository.getClass().getName());
    }

    @Test
    public void v1_testInsert() {

        Member member = memberRepository.findById("user3@aaa.com")
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        for (int i = 0; i < 10000; i++) {

            Todo todo = Todo.builder()
                    .title("title"+i)
                    .content("Content..."+i)
                    .dueDate(LocalDate.of(2025,3,1))
                    .member(member)
                    .build();
            todo.addImageString(UUID.randomUUID()+"_"+"TEST1.jpg");
            todo.addImageString(UUID.randomUUID()+"_"+"TEST2.jpg");

            Todo result = todoRepository.save(todo);

            log.info(result);
        }
    }

    @Test
    public void default_testRead() {

        Long tno = 1L;

        Optional<Todo> result = todoRepository.findById(tno);

        Todo todo = result.orElseThrow();

        log.info(todo);
    }
    @Test
    public void v1_testRead단건조회() {

        Long tno = 1L;

        Optional<Todo> result = todoRepository.selectOneWithImageList(tno);

        Todo todo = result.orElseThrow();

        log.info(todo);
        log.info(todo.getImageList());
    }

    @Test
    public void testDelete() {

        Long tno = 11L;

        todoRepository.deleteById(tno);

    }

    @Test
    public void testUpdate() {

        // 먼저 로딩 하고 엔티티 객체를 변경 /setter

        Long tno = 2L;

        Optional<Todo> result = todoRepository.selectOneWithImageList(tno);

        Todo todo = result.get();

        log.info(todo);

        todo.changeTitle("Up");
        todo.changeContent("up C");
        todo.changeComplete(true);
        todo.addImageString(UUID.randomUUID()+"_"+"IMAGE1.jpg");

        log.info(todoRepository.save(todo));
    }

    @Test
    public void default_testPaging() {

        // 페이지 번호는 0부터
        Pageable pageable = PageRequest.of(0, 10, Sort.by("tno").descending());

        Page<Todo> result = todoRepository.findAll(pageable);

        log.info(result.getTotalElements());

        log.info(result.getContent());
    }

    @Test
    public void v1_querydsl_testSearch() {
        String email = "user1@aaa.com";

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .size(10)
                .page(1)
                .build();
        Page<TodoListDTO> result = todoRepository.search(email, pageRequestDTO);

        log.info(result.getTotalElements());
        log.info(result.getContent());
    }

    @Test
    public void v1_JPQL_testSelectListByMember() {

        String email = "user1@aaa.com";

        Pageable pageable = PageRequest.of(0, 5, Sort.by("tno").descending());

        Page<TodoListDTO> result = todoRepository.getItemsOfTodoListDTOByEmailComplete(email, pageable);

        log.info(result.getTotalElements());
        log.info(result.getContent());
    }
}