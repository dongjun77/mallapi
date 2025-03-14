package com.project.mallapi.mongoRepository;

import static org.junit.jupiter.api.Assertions.*;

import com.project.mallapi.document.Todo;
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
class MongoTodoRepositoryTest {

    @Autowired
    private MongoTodoRepository todoRepository;

    String id = "67d38d80d27aa655f840646a";

    @Test
    public void test1() {

        Assertions.assertNotNull(todoRepository);

        log.info(todoRepository.getClass().getName());
    }

    @Test
    public void v1_testInsert() {

        for (int i = 0; i < 990001; i++) {

            Todo todo = Todo.builder()
                    .title("title"+i)
                    .content("Content..."+i)
                    .dueDate(LocalDate.of(2025,3,1))
                    .memberEmail("user3@aaa.com")
                    .build();
            todo.addImageString(UUID.randomUUID()+"_"+"TEST1.jpg");
            todo.addImageString(UUID.randomUUID()+"_"+"TEST2.jpg");

            Todo result = todoRepository.save(todo);

            log.info(result);
        }
    }

    @Test
    public void default_testRead() {


        Optional<Todo> result = todoRepository.findById(id);

        Todo todo = result.orElseThrow();

        log.info(todo);
    }

    @Test
    public void v1_testRead단건조회() {


        Optional<Todo> result = todoRepository.findById(id);

        Todo todo = result.orElseThrow();

        log.info(todo);
        log.info(todo.getImageList());
    }

    @Test
    public void testDelete() {

        todoRepository.deleteById(id);

    }

    @Test
    public void testUpdate() {

        // 먼저 로딩 하고 엔티티 객체를 변경 /setter

        Optional<Todo> result = todoRepository.findById(id);

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
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").descending());

        Page<Todo> result = todoRepository.findAll(pageable);

        log.info(result.getTotalElements());

        log.info(result.getContent());
    }

    @Test
    public void default_testAll() {

        List<Todo> result = todoRepository.findAllByMemberEmailAndCompleteIsFalse("user3@aaa.com");
        log.info("result size: {}", result.size());
        log.info("result: {}", result);

        result.forEach(todo -> log.info("Todo: {}", todo));

    }

    @Test
    public void v1_JPQL_testSelectListByMember() {

        String email = "user3@aaa.com";

        Pageable pageable = PageRequest.of(0, 5, Sort.by("tno").descending());

        Page<Todo> result = todoRepository.findByMemberEmailAndCompleteIsFalse(email, pageable);

        log.info(result.getTotalElements());
        log.info(result.getContent());
    }



}