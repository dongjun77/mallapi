package com.project.mallapi.mongoRepository;

import com.project.mallapi.document.Member;
import com.project.mallapi.document.Todo;
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
class MongoTodoRepositoryTest {

    @Autowired
    private MongoTodoRepository mongoTodoRepository;

    @Autowired
    private MongoMemberRepository mongoMemberRepository;

    String id = "user9@aaa.com";

    @Test
    public void test1() {

        Assertions.assertNotNull(mongoTodoRepository);

        log.info(mongoTodoRepository.getClass().getName());
    }

    @Test
    public void v1_testInsert() {

        long startTime = System.currentTimeMillis(); // 시작 시간 기록
        int testNum = 10;

        Member member = mongoMemberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        for (int i = 0; i <= testNum; i++) {

            Todo todo = Todo.builder()
                    .title("title"+i)
                    .content("Content..."+i)
                    .dueDate(LocalDate.of(2025,3,19))
                    .member(member)
                    .complete(true)
                    .build();
            todo.addImageString(UUID.randomUUID()+"_"+"TEST1.jpg");
            todo.addImageString(UUID.randomUUID()+"_"+"TEST2.jpg");

            Todo result = mongoTodoRepository.save(todo);

            log.info(result);
        }
        long endTime = System.currentTimeMillis(); // 종료 시간 기록
        long duration = endTime - startTime; // 실행 시간 계산

        log.info("유저 {}의 데이터 {}개 삽입 ",member.getNickname(), testNum);
        log.info("걸린시간 : {} ms", duration);

    }

    @Test
    public void default_testRead() {

        String id = "67da3f572124016d060eb325";

        long startTime = System.currentTimeMillis(); // 시작 시간 기록

        Optional<Todo> result = mongoTodoRepository.findById(id);
        Todo todo = result.orElseThrow();

        long endTime = System.currentTimeMillis(); // 종료 시간 기록
        long duration = endTime - startTime; // 실행 시간 계산

        log.info(todo);
        log.info(todo.getImageList());
        log.info("걸린시간 : {} ms", duration);
    }

    @Test
    public void default_test_complete() {

        String id = "67da3f572124016d060eb325";

        long startTime = System.currentTimeMillis(); // 시작 시간 기록

        List<Todo> result = mongoTodoRepository.findAllByComplete(true);

        long endTime = System.currentTimeMillis(); // 종료 시간 기록
        long duration = endTime - startTime; // 실행 시간 계산

        log.info(result);
        log.info(result.size());
        log.info("걸린시간 : {} ms", duration);
    }

    @Test
    public void default_test_member() {

        long startTime = System.currentTimeMillis(); // 시작 시간 기록

        List<Todo> result = mongoTodoRepository.findAllByMember("user9@aaa.com");

        long endTime = System.currentTimeMillis(); // 종료 시간 기록
        long duration = endTime - startTime; // 실행 시간 계산

        log.info(result);
        log.info(result.size());
        log.info("걸린시간 : {} ms", duration);
    }

    @Test
    public void v1_testRead단건조회() {


        Optional<Todo> result = mongoTodoRepository.findById(id);

        Todo todo = result.get();

        log.info(todo);
        log.info(todo.getImageList());
    }

    @Test
    public void testDelete() {

        mongoTodoRepository.deleteById(id);

    }

    @Test
    public void testUpdate() {

        // 먼저 로딩 하고 엔티티 객체를 변경 /setter

        Optional<Todo> result = mongoTodoRepository.findById(id);

        Todo todo = result.get();

        log.info(todo);

        todo.changeTitle("Up");
        todo.changeContent("up C");
        todo.changeComplete(true);

        todo.addImageString(UUID.randomUUID()+"_"+"IMAGE1.jpg");

        log.info(mongoTodoRepository.save(todo));
    }

    @Test
    public void default_testPaging() {

        long startTime = System.currentTimeMillis(); // 시작 시간 기록
        // 페이지 번호는 0부터
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").descending());

        Page<Todo> result = mongoTodoRepository.findAll(pageable);

        long endTime = System.currentTimeMillis(); // 종료 시간 기록
        long duration = endTime - startTime; // 실행 시간 계산

        log.info(result.getTotalElements());
        log.info(result.getContent());
        log.info("걸린시간 : {} ms", duration);
    }

    @Test
    public void default_testAll() {
        long startTime = System.currentTimeMillis(); // 시작 시간 기록

//        List<Todo> result = todoRepository.findAllByMemberEmailAndCompleteIsFalse("user5@aaa.com");
        List<Todo> result = mongoTodoRepository.findAll();

        long endTime = System.currentTimeMillis(); // 종료 시간 기록
        long duration = endTime - startTime; // 실행 시간 계산

        log.info("result size: {}", result.size());
        log.info("result: {}", result);
        log.info("걸린시간 : {} ms", duration);

//        result.forEach(todo -> log.info("Todo: {}", todo));
    }

    @Test
    public void v1_JPQL_testSelectListByMember() {

        String email = "user3@aaa.com";

        Pageable pageable = PageRequest.of(0, 5, Sort.by("tno").descending());

        Page<Todo> result = mongoTodoRepository.findByMemberEmailAndCompleteIsFalse(email, pageable);

        log.info(result.getTotalElements());
        log.info(result.getContent());
    }



}