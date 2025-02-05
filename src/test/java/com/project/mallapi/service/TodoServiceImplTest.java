package com.project.mallapi.service;

import static org.junit.jupiter.api.Assertions.*;

import com.project.mallapi.dto.PageRequestDTO;
import com.project.mallapi.dto.TodoDTO;
import java.time.LocalDate;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
class TodoServiceImplTest {

    @Autowired
    TodoService todoService;

    @Test
    public void testGet() {

        Long tno = 50L;

        log.info(todoService.get(tno));

    }

    @Test
    public void testRegister() {
        TodoDTO todoDTO = TodoDTO.builder()
                .title("Title...")
                .content("Content....")
                .dueDate(LocalDate.of(2023,12,31))
                .build();

        log.info(todoService.register(todoDTO));
    }

    @Test
    public void testGetList() {

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(11).build();

        log.info(todoService.getList(pageRequestDTO));

    }
}