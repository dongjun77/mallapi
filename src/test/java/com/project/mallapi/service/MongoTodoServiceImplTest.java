package com.project.mallapi.service;

import static org.junit.jupiter.api.Assertions.*;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
class MongoTodoServiceImplTest {

    @Autowired
    private MongoTodoServiceImpl mongoTodoService;

    @Test
    public void testGetList() {

        mongoTodoService.findAllMongoTodoList("user8@aaa.com");

    }

}