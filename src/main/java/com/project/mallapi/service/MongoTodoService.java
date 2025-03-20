package com.project.mallapi.service;

import com.project.mallapi.document.Todo;
import com.project.mallapi.dto.MongoTodoListDTO;
import jakarta.transaction.Transactional;
import java.util.List;

@Transactional
public interface MongoTodoService {

    List<Todo> findAllMongoTodoList(String email);

//    default MongoTodoListDTO entityToMongoTodoListDTO(Todo todo){
//        MongoTodoListDTO mongoTodoListDTO = MongoTodoListDTO.builder()
//                .id(todo.getId())
//                .title(todo.getTitle())
//                .content(todo.getContent())
//                .member(todo.getMemberEmail())
//                .complete(todo.isComplete())
//                .dueDate(todo.getDueDate())
//                .imageFile(  // ✅ 이미지가 있는 경우 첫 번째 파일명을 설정
//                        todo.getImageList().isEmpty() ? null : todo.getImageList().get(0).getFileName()
//                )
//                .build();
//        return mongoTodoListDTO;
//    }

}
