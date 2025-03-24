package com.project.mallapi.service;

import com.project.mallapi.document.Todo;
import com.project.mallapi.dto.MongoTodoListDTO;
import com.project.mallapi.dto.PageRequestDTO;
import com.project.mallapi.dto.PageResponseDTO;
import com.project.mallapi.mongoRepository.MongoTodoRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class MongoTodoServiceImpl implements MongoTodoService {

    private final MongoTodoRepository mongoTodoRepository;

    @Override
    public List<MongoTodoListDTO> findAllMongoTodoList(String email) {

        List<Todo> result =
                mongoTodoRepository.findAllByMemberEmailAndCompleteIsFalse(email);

        return result.stream().map(todo -> entityToMongoTodoListDTO(todo)).toList();
    }

    @Override
    public PageResponseDTO<MongoTodoListDTO> pageMongoTodoList(String email, PageRequestDTO pageRequestDTO) {

        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage(),
                pageRequestDTO.getSize(),
                Sort.by("id").descending());

        Page<Todo> result =
                mongoTodoRepository.findAllByMemberEmailAndCompleteIsFalse(email, pageable);

        long totalCount = result.getTotalElements();

        List<MongoTodoListDTO> dtoList = result.getContent().stream()
                .map(todo -> entityToMongoTodoListDTO(todo)).toList();

        return PageResponseDTO.<MongoTodoListDTO>withAll()
                .dtoList(dtoList)
                .totalCount(totalCount)
                .pageRequestDTO(pageRequestDTO)
                .build();
    }
}
