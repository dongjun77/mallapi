package com.project.mallapi.repository;

import com.project.mallapi.domain.Member;
import com.project.mallapi.domain.Todo;
import com.project.mallapi.dto.PageRequestDTO;
import com.project.mallapi.dto.TodoListDTO;
import java.time.LocalDate;
import java.util.List;
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
    public void dbtest_testInsert() {

        long startTime = System.currentTimeMillis(); // 시작 시간 기록

        Member member = memberRepository.findById("user1@aaa.com")
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        for (int i = 1; i <= 10000; i++) {

            Todo todo = Todo.builder()
                    .title("title"+i)
                    .content("Content..."+i)
                    .dueDate(LocalDate.of(2025,3,1))
                    .member(member)
                    .complete(false)
                    .build();
            todo.addImageString(UUID.randomUUID()+"_"+"TEST1.jpg");
            todo.addImageString(UUID.randomUUID()+"_"+"TEST2.jpg");

            Todo result = todoRepository.save(todo);

            log.info(result);
        }

        long endTime = System.currentTimeMillis(); // 종료 시간 기록
        long duration = endTime - startTime; // 실행 시간 계산

        log.info("걸린시간 : {} ms", duration);
    }

    @Test
    public void dbtest_update() {

        long startTime = System.currentTimeMillis(); // 시작 시간 기록

        // 먼저 로딩 하고 엔티티 객체를 변경 /setter
        Long tno = 2L;

        Optional<Todo> result = todoRepository.selectOneWithImageList(tno);

        Todo todo = result.get();

        todo.changeComplete(false);

        log.info(todoRepository.save(todo));

        long endTime = System.currentTimeMillis(); // 종료 시간 기록
        long duration = endTime - startTime; // 실행 시간 계산

        log.info(todo);
        log.info("걸린시간 : {} ms", duration);
    }

    @Test
    public void dbtest_testRead() {

        Long tno = 2L;

        long startTime = System.currentTimeMillis(); // 시작 시간 기록

        Optional<Todo> result = todoRepository.findById(tno);
        Todo todo = result.orElseThrow();

        long endTime = System.currentTimeMillis(); // 종료 시간 기록
        long duration = endTime - startTime; // 실행 시간 계산

        log.info(todo);
        log.info("걸린시간 : {} ms", duration);
    }

    @Test
    public void dbtest_testfindAll() {

        long startTime = System.currentTimeMillis(); // 시작 시간 기록

        List<Todo> result = todoRepository.findAll();

        long endTime = System.currentTimeMillis(); // 종료 시간 기록
        long duration = endTime - startTime; // 실행 시간 계산

        log.info(result);
        log.info(result.size());
        log.info("걸린시간 : {} ms", duration);
    }

    @Test
    public void dbtest_testReadComplete() {

        long startTime = System.currentTimeMillis(); // 시작 시간 기록

        List<Todo> result = todoRepository.findByComplete(true);

        long endTime = System.currentTimeMillis(); // 종료 시간 기록
        long duration = endTime - startTime; // 실행 시간 계산

        log.info(result);
        log.info(result.size());
        log.info("걸린시간 : {} ms", duration);
    }

    @Test
    public void dbtest_testReadMember() {

        long startTime = System.currentTimeMillis(); // 시작 시간 기록

        List<Todo> result = todoRepository.findByMember("user4@aaa.com");

        long endTime = System.currentTimeMillis(); // 종료 시간 기록
        long duration = endTime - startTime; // 실행 시간 계산

        log.info(result);
        log.info(result.size());
        log.info("걸린시간 : {} ms", duration);
    }

    @Test
    public void dbtest_testReadPaging() {

        long startTime = System.currentTimeMillis(); // 시작 시간 기록
        // 페이지 번호는 0부터
        Pageable pageable = PageRequest.of(0, 10, Sort.by("tno").descending());

        Page<Todo> result = todoRepository.getAll(pageable);

        long endTime = System.currentTimeMillis(); // 종료 시간 기록
        long duration = endTime - startTime; // 실행 시간 계산

        log.info(result.getSize());
        log.info(result.getContent());

        log.info("걸린시간 : {} ms", duration);
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
    public void default_testAll() {
        long startTime = System.currentTimeMillis(); // 시작 시간 기록

//        List<TodoListDTO> result = todoRepository.findAllTodoListDTOByEmailComplete("user5@aaa.com");
        List<Todo> result = todoRepository.findAll();

        long endTime = System.currentTimeMillis(); // 종료 시간 기록
        long duration = endTime - startTime; // 실행 시간 계산

        log.info("result size: {}", result.size());
        log.info("result: {}", result);
        log.info("걸린시간 : {} ms", duration);

//        result.forEach(todo -> log.info("Todo: {}", todo));
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

        String email = "user4@aaa.com";

        Pageable pageable = PageRequest.of(0, 10000, Sort.by("tno").descending());

        Page<TodoListDTO> result = todoRepository.getItemsOfTodoListDTOByEmailComplete(email, pageable);

        log.info(result.getTotalElements());
        log.info(result.getContent());
    }
}