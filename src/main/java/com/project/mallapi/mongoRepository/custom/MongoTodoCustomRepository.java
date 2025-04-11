package com.project.mallapi.mongoRepository.custom;

import com.project.mallapi.dto.MongoTodoListDTO;
import com.project.mallapi.dto.TodoListDTO;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MongoTodoCustomRepository {

    List<MongoTodoListDTO> findTodoListDTOByMemberEmail(String memberEmail);

    Page<MongoTodoListDTO> findTodoListDTOByMemberEmail(String memberEmail, Pageable pageable);

}
