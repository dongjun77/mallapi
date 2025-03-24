package com.project.mallapi.service;

import com.project.mallapi.document.Todo;
import com.project.mallapi.dto.MongoTodoListDTO;
import com.project.mallapi.dto.PageRequestDTO;
import com.project.mallapi.dto.PageResponseDTO;
import jakarta.transaction.Transactional;
import java.util.List;

@Transactional
public interface MongoTodoService {

    List<MongoTodoListDTO> findAllMongoTodoList(String email);

    PageResponseDTO<MongoTodoListDTO> pageMongoTodoList(String email, PageRequestDTO pageRequestDTO);

    // 엔터티 -> DTO 변환 메서드 추가
    default MongoTodoListDTO entityToMongoTodoListDTO(Todo todo) {
        return MongoTodoListDTO.builder()
                .id(todo.getId())
                .title(todo.getTitle())
                .content(todo.getContent())
                .memberEmail(todo.getMemberEmail()) // Member 객체에서 Email 가져오기
                .complete(todo.isComplete())
                .dueDate(todo.getDueDate())
                .imageFile(todo.getImageList().get(0).getFileName()) // 이미지 파일 경로 또는 URL
                .build();
    }
}
