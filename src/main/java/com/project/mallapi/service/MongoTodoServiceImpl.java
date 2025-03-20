package com.project.mallapi.service;

import com.project.mallapi.document.Todo;
import com.project.mallapi.dto.MongoTodoListDTO;
import com.project.mallapi.mongoRepository.MongoTodoRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class MongoTodoServiceImpl implements MongoTodoService {

    private final MongoTodoRepository mongoTodoRepository;

    @Override
    public List<Todo> findAllMongoTodoList(String email) {

        List<Todo> result =
                mongoTodoRepository.findAllByMemberEmailAndCompleteIsFalse(email);
        return result;

//        return result.stream().map(todo -> entityToMongoTodoListDTO(todo)).toList();
    }
}
